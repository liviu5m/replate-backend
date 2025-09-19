package com.replate.backend.service;

import com.replate.backend.dto.ProfileDataDto;
import com.replate.backend.dto.ProfileImageDto;
import com.replate.backend.dto.UserRoleDto;
import com.replate.backend.enums.UserRole;
import com.replate.backend.model.User;
import com.replate.backend.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public List<User> allUsersWithoutAuthenticated(Long userId, String username) {
        System.out.println(username);
        if(!username.equals("")) return userRepository.findAllByIdNotAndUsernameContainingIgnoreCase(userId, username);
        return userRepository.findAllByIdNot(userId);
    }

    public User setUpUserRole(UserRoleDto userRoleDto) {
        Optional<User> optionalUser = userRepository.findById(userRoleDto.getUserId());
        if (!optionalUser.isPresent()) throw new RuntimeException("User not found");
        User user = optionalUser.get();
        switch (userRoleDto.getRole()) {
            case "DONOR":
                user.setRole(UserRole.DONOR);
                break;
            case "NGO":
                user.setRole(UserRole.NGO);
                break;
            case "DRIVER":
                user.setRole(UserRole.DRIVER);
                break;
        }
        user.setAddress(userRoleDto.getAddress());
        user.setPhone(userRoleDto.getPhone());
        if(userRoleDto.getType().equals("creating-account")) {
            emailService.sendVerificationEmailTemplate(user);
        }
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(ProfileDataDto profileDataDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Optional<User> userUsername = userRepository.findByUsername(profileDataDto.getUsername());
        if (userUsername.isPresent() && !user.getUsername().equals(profileDataDto.getUsername()))
            throw new RuntimeException("Username already exists");
        user.setFullName(profileDataDto.getFullName());
        user.setUsername(profileDataDto.getUsername());
        user.setPhone(profileDataDto.getPhone());
        user.setAddress(profileDataDto.getAddress());
        switch (profileDataDto.getRole()) {
            case "DONOR":
                user.setRole(UserRole.DONOR);
                break; case "NGO":
                    user.setRole(UserRole.NGO);
                    break;
                case "DRIVER":
                    user.setRole(UserRole.DRIVER);
                    break;
        }
        user.setImage(profileDataDto.getImage());
        user.setCity(profileDataDto.getCity());
        user.setZip(profileDataDto.getZip());
        user.setCountry(profileDataDto.getCountry());

        if (!profileDataDto.getCurrentPassword().equals("") || !profileDataDto.getNewPassword().equals("") || !profileDataDto.getPasswordConfirmation().equals("")) {
            if (!passwordEncoder.matches(profileDataDto.getCurrentPassword(), user.getPassword()))
                throw new RuntimeException("Incorrect password entered");
            if (!profileDataDto.getNewPassword().equals(profileDataDto.getPasswordConfirmation()))
                throw new RuntimeException("Password Confirmation is wrong");

            user.setPassword(passwordEncoder.encode(profileDataDto.getNewPassword()));
        }
        userRepository.save(user);
        return user;
    }

    public User updateProfileImage(ProfileImageDto profileImageDto, Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setImage(profileImageDto.getImage());
            return userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
