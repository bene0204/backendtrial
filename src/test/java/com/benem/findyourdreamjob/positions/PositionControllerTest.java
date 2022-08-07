package com.benem.findyourdreamjob.positions;

import com.benem.findyourdreamjob.exceptions.InvalidApiKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PositionController.class)
class PositionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PositionService positionService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void whenPostEndpointCalled_thenShouldReturnOkStatus() throws Exception {

        var position = Position.builder()
                .name("Accounting")
                .location("Berlin")
                .build();

        Mockito.when(positionService.addPosition(position,"apiKey")).thenReturn("url");

        this.mockMvc.perform(post("/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\" : \"Accounting\",\n" +
                        "    \"location\" : \"Berlin\"\n" +
                        "}").queryParam("apiKey", "key")

        ).andExpect(status().isOk());
    }

    @Test
    void whenPostEndpointCalled_thenShouldReturnForbiddenStatus() throws Exception {

        var position = Position.builder()
                .name("Accounting")
                .location("Berlin")
                .build();

        Mockito.when(positionService.addPosition(position,"key")).thenThrow(new InvalidApiKeyException("invalid api key"));

        this.mockMvc.perform(post("/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\" : \"Accounting\",\n" +
                        "    \"location\" : \"Berlin\"\n" +
                        "}").queryParam("apiKey", "key")

        ).andExpect(status().isForbidden());
    }

    @Test
    void whenGetEndpointCalled_thenShouldReturnOkStatus() throws Exception {
        var position = Position.builder()
                .name("Accounting")
                .location("Berlin")
                .url("url")
                .build();

        List<Position> positions = new ArrayList<>();
        positions.add(position);

        Mockito.when(positionService.findMatchingPositions("Finance","London","key")).thenReturn(positions);

        this.mockMvc.perform(get("/positions")
                .queryParam("name","Finance")
                .queryParam("location", "London")
                .queryParam("apiKey", "key")
        ).andExpect(status().isOk());
    }

    @Test
    void whenGetEndpointCalled_thenShouldThrowInvalidApiKeyException() throws Exception {
        var position = Position.builder()
                .name("Accounting")
                .location("Berlin")
                .url("url")
                .build();

        List<Position> positions = new ArrayList<>();
        positions.add(position);

        Mockito.when(positionService.findMatchingPositions("Finance","London","key")).thenThrow(new InvalidApiKeyException("Invalid api key"));

        this.mockMvc.perform(get("/positions")
                .queryParam("name","Finance")
                .queryParam("location", "London")
                .queryParam("apiKey", "key")
        ).andExpect(status().isForbidden());
    }
}