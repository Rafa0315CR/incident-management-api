package com.rafaelgonzalez.incidents.incident;

import com.rafaelgonzalez.incidents.common.ResourceNotFoundException;
import com.rafaelgonzalez.incidents.incident.dto.CreateIncidentRequest;
import com.rafaelgonzalez.incidents.incident.dto.IncidentResponse;
import com.rafaelgonzalez.incidents.incident.dto.UpdateIncidentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class IncidentService {

    private final IncidentRepository repository;

    public IncidentService(IncidentRepository repository) {
        this.repository = repository;
    }

    public Page<IncidentResponse> findAll(IncidentStatus status, IncidentPriority priority, Pageable pageable) {
        Page<Incident> incidents;
        if (status != null && priority != null) {
            incidents = repository.findByStatusAndPriority(status, priority, pageable);
        } else if (status != null) {
            incidents = repository.findByStatus(status, pageable);
        } else if (priority != null) {
            incidents = repository.findByPriority(priority, pageable);
        } else {
            incidents = repository.findAll(pageable);
        }
        return incidents.map(IncidentResponse::from);
    }

    public IncidentResponse findById(Long id) {
        return IncidentResponse.from(getIncident(id));
    }

    @Transactional
    public IncidentResponse create(CreateIncidentRequest request) {
        Incident incident = new Incident(
                request.title().trim(), request.description().trim(), request.priority(),
                request.reporter().trim(), normalize(request.assignee())
        );
        return IncidentResponse.from(repository.save(incident));
    }

    @Transactional
    public IncidentResponse update(Long id, UpdateIncidentRequest request) {
        Incident incident = getIncident(id);
        incident.update(request.title().trim(), request.description().trim(), request.priority(), normalize(request.assignee()));
        return IncidentResponse.from(incident);
    }

    @Transactional
    public IncidentResponse changeStatus(Long id, IncidentStatus status) {
        Incident incident = getIncident(id);
        incident.changeStatus(status);
        return IncidentResponse.from(incident);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getIncident(id));
    }

    private Incident getIncident(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id " + id));
    }

    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
