package com.replate.backend.handlers;

import com.replate.backend.enums.UserRole;
import com.replate.backend.model.User;
import com.replate.backend.repository.UserRepository;
import com.replate.backend.service.JwtService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final ObjectProvider<AuthenticationManager> authenticationManagerProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication successfull took place" + oauth2User);
        System.out.println(oauth2User.getAttributes());

        Optional<User> optionalUser = userRepository.findByEmail(oauth2User.getAttribute("email"));
        User user;
        if (!optionalUser.isPresent()) {
            System.out.println(oauth2User);
            user = new User(oauth2User.getAttribute("email"), "google");
            user.setPassword(passwordEncoder.encode("google"));
            user.setUsername(user.getEmail());
            user.setFullName(oauth2User.getAttribute("name"));
            user.setImage(oauth2User.getAttribute("picture"));
            user.setEnabled(true);
            user.setRole(UserRole.GUEST);
            userRepository.save(user);
        }else user = optionalUser.get();
        System.out.println(user);
        try {
            authenticationManagerProvider.getObject()
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), "google"));
            String jwtToken = jwtService.generateToken(user);
            Cookie jwtCookie = new Cookie("jwt", jwtToken);
            System.out.println();
            jwtCookie.setHttpOnly(false);
            jwtCookie.setSecure(false);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(86400);
            response.addCookie(jwtCookie);
            response.sendRedirect("http://localhost:5173/auth/google");

        }catch(Exception e) {
            response.sendRedirect("http://localhost:5173/auth/login?error=true");
        }
    }
}