package com.replate.backend.controller;

import com.replate.backend.dto.ProfileDataDto;
import com.replate.backend.dto.ProfileImageDto;
import com.replate.backend.dto.UserRoleDto;
import com.replate.backend.model.User;
import com.replate.backend.repository.UserRepository;
import com.replate.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.getPrincipal() instanceof DefaultOidcUser) {

                DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                String email = oidcUser.getEmail();
                User user = userService.findUserByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                return ResponseEntity.ok(user);

            } else if (authentication.getPrincipal() instanceof User) {
                User currentUser = (User) authentication.getPrincipal();
                User user = userService.findUserByUsername(currentUser.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                return ResponseEntity.ok(user);

            } else {
                return ResponseEntity.badRequest().body("Unsupported authentication type");
            }
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/available")
    public ResponseEntity<List<User>> allUsersWithoutAuthenticated(@RequestParam("userId") Long userId,@RequestParam("username") String username) {
        List<User> users = userService.allUsersWithoutAuthenticated(userId, username);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/role")
    public ResponseEntity<?> setUpUserRole(@Valid @RequestBody UserRoleDto userRoleDto, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            return ResponseEntity.ok(userService.setUpUserRole(userRoleDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfileData(@Valid @RequestBody ProfileDataDto profileDataDto,BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            System.out.println(errors);
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            User user = userService.updateUser(profileDataDto, id);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/image/{id}")
    public ResponseEntity<?> updateProfileImage(@RequestBody ProfileImageDto profileImageDto, @PathVariable Long id) {
        System.out.println(id);
        try {
            User user = userService.updateProfileImage(profileImageDto, id);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}