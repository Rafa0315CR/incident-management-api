package com.rafaelgonzalez.incidents.incident;

import com.rafaelgonzalez.incidents.common.ResourceNotFoundException;
import com.rafaelgonzalez.incidents.incident.dto.CreateIncidentRequest;
import com.rafaelgonzalez.incidents.incident.dto.IncidentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock
    private IncidentRepository repository;

    @InjectMocks
    private IncidentService service;

    @Test
    void createsAnOpenIncident() {
        when(repository.save(any(Incident.class))).thenAnswer(invocation -> invocation.getArgument(0));

        IncidentResponse result = service.create(new CreateIncidentRequest(
                "API unavailable", "The customer API is returning errors", IncidentPriority.HIGH,
                "Operations", "Backend team"
        ));

        assertThat(result.status()).isEqualTo(IncidentStatus.OPEN);
        assertThat(result.priority()).isEqualTo(IncidentPriority.HIGH);
        assertThat(result.title()).isEqualTo("API unavailable");
    }

    @Test
    void reportsWhenIncidentDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
