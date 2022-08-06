package com.benem.findyourdreamjob.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public String registerClient(Client client) {
        String apiKey = UUID.randomUUID().toString();

        client.setEmail(client.getEmail().toLowerCase());
        client.setApiKey(apiKey);
        this.clientRepository.save(client);
        return apiKey;
    }

    @Override
    public Boolean validateApiKey(String apiKey) {
       return this.clientRepository.existsClientByApiKey(apiKey);
    }


}
