package com.santhi.divinecornerweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Column(unique = true, nullable = false)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER; // Enum for role (USER or ADMIN)


}
