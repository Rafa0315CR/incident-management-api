package com.rafaelgonzalez.incidents.incident.dto;

import com.rafaelgonzalez.incidents.incident.IncidentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(@NotNull IncidentStatus status) {
}
