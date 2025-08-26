package com.self.task_management.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;

    @JsonCreator
    public static TaskStatus fromValue(String value) {
        return TaskStatus.valueOf(value.toUpperCase());
    }
}
