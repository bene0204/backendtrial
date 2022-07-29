package com.benem.findyourdreamjob.clients;

import java.util.UUID;

public interface ClientService {
    String registerClient(Client client);

    Boolean validateApiKey(String apiKey);
}
