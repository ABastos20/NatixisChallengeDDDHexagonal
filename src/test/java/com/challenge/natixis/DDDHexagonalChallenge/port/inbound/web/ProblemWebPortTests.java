package com.challenge.natixis.DDDHexagonalChallenge.port.inbound.web;

import com.challenge.natixis.DDDHexagonalChallenge.application.dto.ProblemDto;
import com.challenge.natixis.DDDHexagonalChallenge.application.service.ProblemService;
import com.challenge.natixis.DDDHexagonalChallenge.port.mapper.ProblemMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProblemWebPort.class)
public class ProblemWebPortTests {

    @MockBean
    private ProblemService problemService;

    @MockBean
    private ProblemMapper problemMapper;

    @InjectMocks
    private ProblemWebPort problemWebPort;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProblemWebPort(problemService, problemMapper)).build();
        objectMapper.registerModule(new JavaTimeModule()); // Register JSR-310 Java time module

    }

    @Test
    public void testOpenProblem() throws Exception {
        ProblemDto problemDto = new ProblemDto("New Problem");
        String jsonPayload = objectMapper.writeValueAsString(problemDto);

        mockMvc.perform(post("/api/problems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());

        verify(problemService).openProblem("New Problem");
    }


    @Test
    public void testAddComment() throws Exception {
        String problemName = "Existing Problem";
        String comment = "This is a comment.";

        mockMvc.perform(post("/api/problems/{problemName}/comments", problemName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comment))
                .andExpect(status().isOk());

        verify(problemService).addComment(eq(problemName), eq(comment));
    }

    @Test
    public void testAddProblemToCommittee() throws Exception {
        String problemName = "Existing Problem";
        LocalDate committeeDate = LocalDate.now().plusDays(1);
        String dateStr = committeeDate.toString();

        mockMvc.perform(put("/api/problems/{problemName}/committees/{committeeDate}", problemName, dateStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(problemService).addProblemToCommittee(eq(problemName), eq(committeeDate));
    }
}
