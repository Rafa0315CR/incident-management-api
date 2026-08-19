package com.rafaelgonzalez.incidents.incident;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    Page<Incident> findByStatus(IncidentStatus status, Pageable pageable);
    Page<Incident> findByPriority(IncidentPriority priority, Pageable pageable);
    Page<Incident> findByStatusAndPriority(IncidentStatus status, IncidentPriority priority, Pageable pageable);
}
