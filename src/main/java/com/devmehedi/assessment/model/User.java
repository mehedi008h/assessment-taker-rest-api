package com.devmehedi.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(updatable = false, nullable = false, name = "user_identifier")
    private String userIdentifier;
    @NotBlank(message = "First Name is required!")
    private String firstName;
    @NotBlank(message = "Last Name is required!")
    private String lastName;
    @NotBlank(message = "Username is required!")
    private String username;
    @NotBlank(message = "Password is required!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 20, message = "Please use 8 to 20 characters")
    private String password;
    @Email
    @NotBlank(message = "Email is required!")
    @Column(unique = true)
    private String email;
    private String phone;
    @Column(length = 5000)
    private String description;
    private String work;
    private int diamond;
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    @Column(name = "last_login_date")
    private Date lastLoginDate;
    @Column(name = "last_login_date_display")
    private Date lastLoginDateDisplay;
    @Column(name = "join_date")
    private Date joinDate;
    private String role;
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;
}
