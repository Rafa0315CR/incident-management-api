package com.rafaelgonzalez.incidents.incident.dto;

import com.rafaelgonzalez.incidents.incident.Incident;
import com.rafaelgonzalez.incidents.incident.IncidentPriority;
import com.rafaelgonzalez.incidents.incident.IncidentStatus;

import java.time.Instant;

public record IncidentResponse(
        Long id,
        String title,
        String description,
        IncidentPriority priority,
        IncidentStatus status,
        String reporter,
        String assignee,
        Instant createdAt,
        Instant updatedAt
) {
    public static IncidentResponse from(Incident incident) {
        return new IncidentResponse(
                incident.getId(), incident.getTitle(), incident.getDescription(),
                incident.getPriority(), incident.getStatus(), incident.getReporter(),
                incident.getAssignee(), incident.getCreatedAt(), incident.getUpdatedAt()
        );
    }
}
