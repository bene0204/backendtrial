package com.benem.findyourdreamjob.positions;

import com.benem.findyourdreamjob.clients.Client;
import com.benem.findyourdreamjob.clients.ClientRepository;
import com.benem.findyourdreamjob.exceptions.InvalidApiKeyException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PositionServiceTest {

    @Autowired
    private PositionService positionService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private PositionRepository positionRepository;

    @Test
    void shouldFindMatchingPositionsFromDatabaseAndApi() throws UnsupportedEncodingException {
        var position = Position.builder()
                .name("Retail")
                .location("Berlin")
                .url("url")
                .build();

        List<Position> positions = new ArrayList<>();
        positions.add(position);

        Mockito.when(positionRepository.findPositionByNameIgnoreCaseAndLocationIgnoreCase("Retail","Berlin")).thenReturn(positions);
        Mockito.when(clientRepository.existsClientByApiKey("key")).thenReturn(true);

    List<Position> matchingPositions = positionService.findMatchingPositions("Retail","Berlin", "key");

    assertNotNull(matchingPositions);
    assertTrue(matchingPositions.size() > 20);

    }

    @Test
    void shouldThrowErrorIfInvalidApiKey() throws UnsupportedEncodingException {
        var position = Position.builder()
                .name("Retail")
                .location("Berlin")
                .url("url")
                .build();

        List<Position> positions = new ArrayList<>();
        positions.add(position);

        Mockito.when(positionRepository.findPositionByNameIgnoreCaseAndLocationIgnoreCase("Retail","Berlin")).thenReturn(positions);
        Mockito.when(clientRepository.existsClientByApiKey("key")).thenReturn(false);



        assertThrows(InvalidApiKeyException.class, () -> {
            List<Position> matchingPositions =  positionService.findMatchingPositions("Retail","Berlin", "key");
        });

    }

    @Test
    void shouldReturnUrl(){
        var position = Position.builder()
                .name("Finance")
                .location("London")
                .build();

        var savedPosition = Position.builder()
                .name("Finance")
                .location("London")
                .url("url")
                .id(1L)
                .build();

        Mockito.when(positionRepository.save(position)).thenReturn(savedPosition);
        Mockito.when(clientRepository.existsClientByApiKey("key")).thenReturn(true);

        String returnedUrl = positionService.addPosition(position, "key");

        assertTrue(returnedUrl.length() > 10);


    }

    @Test
    void shouldThrowInvalidApiKeyException(){
        var position = Position.builder()
                .name("Finance")
                .location("London")
                .build();

        var savedPosition = Position.builder()
                .name("Finance")
                .location("London")
                .url("url")
                .id(1L)
                .build();

        Mockito.when(positionRepository.save(position)).thenReturn(savedPosition);
        Mockito.when(clientRepository.existsClientByApiKey("key")).thenReturn(false);



        assertThrows(InvalidApiKeyException.class, () -> {
            String returnedUrl = positionService.addPosition(position, "key");
        });


    }
}