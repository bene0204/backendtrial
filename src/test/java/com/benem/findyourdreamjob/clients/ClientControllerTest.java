package com.benem.findyourdreamjob.clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ClientController.class)
class ClientControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void whenEndpointCalled_thenShouldReturnOkStatus() throws Exception {
        var client = Client.builder()
                .email("email@nofluff.com")
                .name("NoFluff")
                .build();

        Mockito.when(clientService.registerClient(client)).thenReturn(UUID.randomUUID().toString());

        this.mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\" : \"NoFluff\",\n" +
                        "    \"email\" : \"email@nofluff.com\"\n" +
                        "}")

        ).andExpect(status().isOk());

    }
}