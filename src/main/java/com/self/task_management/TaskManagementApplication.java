package com.self.task_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.self.task_management")
public class TaskManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }
}

// for Master REST, JPA, JWT security.

//Once you finish the core version, you can extend it:
//
//Priority field (LOW, MEDIUM, HIGH).
//
//Soft delete (mark as deleted instead of removing from DB).
//
//Email reminders for due tasks (Spring Scheduler).
//
//Export tasks to CSV/Excel.
//
//Dockerize the application.
