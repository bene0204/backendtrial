package com.benem.findyourdreamjob.clients;

import com.benem.findyourdreamjob.exceptions.InvalidApiKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
    }


    @Test
    void shouldReturnUUID() {
        String uuid = UUID.randomUUID().toString();

        var client = Client.builder()
                .email("email@profession.com")
                .name("Profession")
                .build();

        var savedClient = Client.builder()
                .id(1L)
                .email("email@profession.com")
                .name("Profession")
                .apiKey(uuid)
                .build();

        Mockito.when(clientRepository.save(client)).thenReturn(savedClient);

        String returnedApiKey = clientService.registerClient(client);

        assertEquals(UUID.randomUUID().toString().length(), returnedApiKey.length());

    }

    @Test
    void ifInvalidApiKey_thenShouldThrowInvalidApiKeyException() {

        Mockito.when(clientRepository.existsClientByApiKey("invalid api key")).thenReturn(false);

        assertThrows(InvalidApiKeyException.class, () -> clientService.validateApiKey("invalid api key"));
    }
}