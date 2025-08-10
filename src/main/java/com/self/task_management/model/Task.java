package com.self.task_management.model;

import com.self.task_management.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"TASKS\"")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"TASK_ID\"")
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"USER_ID\"", nullable = false)
    private User user;

    @Column(name = "\"TITLE\"", nullable = false)
    private String title;

    @Column(name = "\"DESCRIPTION\"")
    private String description;

    @Column(name = "\"DUE_DATE\"")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"STATUS\"")
    private TaskStatus status = TaskStatus.PENDING;

    @Column(name = "\"CREATED_AT\"", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "\"UPDATED_AT\"", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
