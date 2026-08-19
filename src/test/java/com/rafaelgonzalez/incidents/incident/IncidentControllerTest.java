package com.rafaelgonzalez.incidents.incident;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelgonzalez.incidents.incident.dto.CreateIncidentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createsAndRetrievesIncident() throws Exception {
        CreateIncidentRequest request = new CreateIncidentRequest(
                "File processing failed", "A scheduled file could not be processed",
                IncidentPriority.CRITICAL, "Monitoring", null
        );

        mockMvc.perform(post("/api/v1/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.status").value("OPEN"));

        mockMvc.perform(get("/api/v1/incidents").param("priority", "CRITICAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("File processing failed"));
    }

    @Test
    void rejectsInvalidIncident() throws Exception {
        CreateIncidentRequest request = new CreateIncidentRequest("", "", null, "", null);

        mockMvc.perform(post("/api/v1/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.title").exists())
                .andExpect(jsonPath("$.validationErrors.priority").exists());
    }
}
