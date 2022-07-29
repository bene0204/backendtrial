package com.benem.findyourdreamjob.clients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsClientByApiKey(String apiKey);
}
