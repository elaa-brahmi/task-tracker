package com.example.task_tracker.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
        Integer id,
        @NotNull(message="100")
        @NotEmpty(message="100")
        String title,
        @NotNull(message="101")
        @NotEmpty(message="101")
        String description,
        @NotNull(message="102")
        @NotEmpty(message="102")
        String status,
        @NotNull(message="103")
        @NotEmpty(message="103")
        String dueDate,

        Importance importance
) {
}
