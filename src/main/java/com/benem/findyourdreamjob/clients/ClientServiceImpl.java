package com.benem.findyourdreamjob.clients;

import com.benem.findyourdreamjob.exceptions.InvalidApiKeyException;
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
    public void validateApiKey(String apiKey) {
       boolean validApiKey = clientRepository.existsClientByApiKey(apiKey);

       if(!validApiKey){
           throw new InvalidApiKeyException("Invalid api key");
       }

    }


}
