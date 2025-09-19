package com.replate.backend.model;

import com.replate.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String password;

    private String image;
    private boolean enabled;
    @Column(name="verification_code")
    private String verificationCode;
    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;

    private String address;
    private String phone;
    private UserRole role;
    private boolean verified;
    private String provider = "email";
    private String city;
    private String zip;
    private String country;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public User(String fullName, String username, String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String email, String provider) {
        this.email = email;
        this.provider = provider;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", verificationCode='" + verificationCode + '\'' +
                ", verificationCodeExpiresAt=" + verificationCodeExpiresAt +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", verified=" + verified +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
