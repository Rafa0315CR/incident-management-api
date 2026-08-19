package com.rafaelgonzalez.incidents.incident;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IncidentPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IncidentStatus status;

    @Column(nullable = false, length = 120)
    private String reporter;

    @Column(length = 120)
    private String assignee;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected Incident() {
    }

    public Incident(String title, String description, IncidentPriority priority, String reporter, String assignee) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.reporter = reporter;
        this.assignee = assignee;
        this.status = IncidentStatus.OPEN;
    }

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public void update(String title, String description, IncidentPriority priority, String assignee) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.assignee = assignee;
    }

    public void changeStatus(IncidentStatus status) {
        this.status = status;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public IncidentPriority getPriority() { return priority; }
    public IncidentStatus getStatus() { return status; }
    public String getReporter() { return reporter; }
    public String getAssignee() { return assignee; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
