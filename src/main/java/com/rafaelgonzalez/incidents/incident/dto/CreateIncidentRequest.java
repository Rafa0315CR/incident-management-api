package com.rafaelgonzalez.incidents.incident.dto;

import com.rafaelgonzalez.incidents.incident.IncidentPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIncidentRequest(
        @NotBlank @Size(max = 120) String title,
        @NotBlank @Size(max = 2000) String description,
        @NotNull IncidentPriority priority,
        @NotBlank @Size(max = 120) String reporter,
        @Size(max = 120) String assignee
) {
}
