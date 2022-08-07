package com.benem.findyourdreamjob.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients")
    public String registerClient(@Valid @RequestBody Client client) {
       return this.clientService.registerClient(client);
    }
}
