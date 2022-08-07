package com.benem.findyourdreamjob.clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {

        clientRepository.deleteAll();

        var client = Client.builder()
                .email("email@nofluff.com")
                .name("NoFluff")
                .apiKey(uuid)
                .build();

        clientRepository.save(client);
    }

    @Test
    void ShouldSaveClient()  {
        var client = Client.builder()
                .email("email@profession.com")
                .name("Profession")
                .apiKey(UUID.randomUUID().toString())
                .build();

        clientRepository.save(client);

        List<Client> clients = clientRepository.findAll();

        assertTrue(clients.size() == 2);
    }

    @Test
    void IfValidApiKey_thenShouldReturnTrue() {
        boolean existsByApiKey =  clientRepository.existsClientByApiKey(uuid);

        assertTrue(existsByApiKey);
    }

    @Test
    void IfInValidApiKey_thenShouldReturnFalse() {
        boolean existsByApiKey =  clientRepository.existsClientByApiKey("some invalid id");

        assertFalse(existsByApiKey);
    }
}