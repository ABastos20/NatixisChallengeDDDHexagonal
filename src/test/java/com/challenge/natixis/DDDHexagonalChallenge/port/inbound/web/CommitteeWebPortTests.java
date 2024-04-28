package com.challenge.natixis.DDDHexagonalChallenge.port.inbound.web;

import com.challenge.natixis.DDDHexagonalChallenge.application.dto.CommitteeDto;
import com.challenge.natixis.DDDHexagonalChallenge.application.service.CommitteeService;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.CommitteeRepository;
import com.challenge.natixis.DDDHexagonalChallenge.port.mapper.CommitteeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CommitteeWebPort.class)
public class CommitteeWebPortTests {

    @MockBean
    private CommitteeService committeeService;

    @MockBean
    private CommitteeMapper committeeMapper;

    @Mock
    private CommitteeRepository committeeRepository;

    @InjectMocks
    private CommitteeWebPort committeeWebPort;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Captor
    ArgumentCaptor<LocalDate> dateCaptor;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CommitteeWebPort(committeeService, new CommitteeMapper())).build();
        objectMapper.registerModule(new JavaTimeModule()); // Register JSR-310 Java time module

    }

    @Test
    public void testScheduleCommitteeValid() throws Exception {
        LocalDate futureDate = LocalDate.now().plusDays(10);
        CommitteeDto committeeDto = new CommitteeDto(futureDate);
        String jsonPayload = objectMapper.writeValueAsString(committeeDto);

        mockMvc.perform(post("/api/committees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());

        verify(committeeService, times(1)).scheduleCommittee(dateCaptor.capture());
        assertThat(dateCaptor.getValue(), is(futureDate));
    }


    @Test
    public void testStartCommittee() throws Exception {
        LocalDate date = LocalDate.now().plusDays(1);
        String dateString = date.format(dtf);

        mockMvc.perform(put("/api/committees/start")
                        .param("date", dateString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<LocalDate> argument = ArgumentCaptor.forClass(LocalDate.class);
        verify(committeeService).startCommittee(argument.capture());
        assertEquals(date, argument.getValue());
    }

    @Test
    public void testCloseCommittee() throws Exception {
        LocalDate date = LocalDate.now().plusDays(1);
        String dateString = date.format(dtf);

        mockMvc.perform(put("/api/committees/close")
                        .param("date", dateString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<LocalDate> argument = ArgumentCaptor.forClass(LocalDate.class);
        verify(committeeService).closeCommittee(argument.capture());
        assertEquals(date, argument.getValue());
    }

}