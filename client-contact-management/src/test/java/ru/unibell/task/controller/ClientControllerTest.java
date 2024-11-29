package ru.unibell.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.unibell.task.dto.ClientDto;
import ru.unibell.task.service.ClientService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @InjectMocks
    private ClientController clientController;
    @Mock
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"sd", "РуИмя"})
    void WhenCreateClientThenStatus200(String name) throws Exception {
        when(clientService.create(any(ClientDto.class))).thenAnswer((Answer<ClientDto>) invocation -> {
            ClientDto inputClient = invocation.getArgument(0);
            return new ClientDto(inputClient.getId(), inputClient.getName(), inputClient.getContacts());
        });

        performClientPostRequest("/client", ClientDto.builder().name(name).build())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  "})
    void WhenCreateClientThenErrorString(String name) throws Exception {
        performClientPostRequest("/client", ClientDto.builder().name(name).build())
                .andExpect(status().isBadRequest());
    }

    private ResultActions performClientPostRequest(String uri, ClientDto clientDto) throws Exception {
        String json = clientDtoToJson(clientDto);

        return mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    private String clientDtoToJson(ClientDto clientDto) throws Exception {
        return objectMapper.writeValueAsString(clientDto);
    }
}