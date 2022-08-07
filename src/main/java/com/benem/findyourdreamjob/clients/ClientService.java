package com.benem.findyourdreamjob.clients;

public interface ClientService {
    String registerClient(Client client);

    void validateApiKey(String apiKey);
}
