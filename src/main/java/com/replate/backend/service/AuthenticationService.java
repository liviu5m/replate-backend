package com.replate.backend.service;

import com.replate.backend.dto.LoginUserDto;
import com.replate.backend.dto.RegisterUserDto;
import com.replate.backend.dto.VerifyUserDto;
import com.replate.backend.model.User;
import com.replate.backend.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public User signup(RegisterUserDto input) {
        try {
            if(userRepository.findByUsername(input.getUsername()).isPresent()) throw new RuntimeException("Username already exists");
            if(userRepository.findByUsername(input.getEmail()).isPresent()) throw new RuntimeException("Email already exists");
            if(!input.getPassword().equals(input.getPasswordConfirmation())) throw new RuntimeException("Passwords do not match");
            User user = new User(input.getFullName(), input.getUsername(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(5));
            user.setEnabled(false);
            userRepository.save(user);
            return user;
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!user.isEnabled()) throw new RuntimeException("User is disabled");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return user;
    }

    public void verifyUser(VerifyUserDto input) {
        Optional<User> optionalUser = userRepository.findById(input.getUserId());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("The verification code has expired");
            }
            if(user.getVerificationCode().equals(input.getCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpiresAt(null);
                userRepository.save(user);
            }else {
                throw new RuntimeException("Invalid verification code");
            }
        }else {
            throw new RuntimeException("User not found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(5));
            emailService.sendVerificationEmailTemplate(user);
            userRepository.save(user);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
