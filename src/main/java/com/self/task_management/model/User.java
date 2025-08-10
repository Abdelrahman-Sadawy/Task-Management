package com.self.task_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"USERS\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"USER_ID\"")
    private Long userId;

    @Column(name = "\"USER_NAME\"", nullable = false)
    private String username;

    @Column(name = "\"EMAIL\"", nullable = false, unique = true)
    private String email;

    @Column(name = "\"PASSWORD\"", nullable = false)
    @ToString.Exclude
    private String password;

    @Column(name = "\"CREATED_AT\"", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "\"UPDATED_AT\"", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();
}
