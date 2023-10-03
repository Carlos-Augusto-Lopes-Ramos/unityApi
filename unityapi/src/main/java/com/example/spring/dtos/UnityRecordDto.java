package com.example.spring.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnityRecordDto(@NotBlank String name, @NotNull String ip) {
}
