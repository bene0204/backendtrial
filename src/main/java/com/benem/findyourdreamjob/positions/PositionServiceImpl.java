package com.benem.findyourdreamjob.positions;

import com.benem.findyourdreamjob.clients.ClientRepository;
import com.benem.findyourdreamjob.clients.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private ClientService clientService;

    @Override
    public String addPosition(Position position, String apiKey) throws Exception {
        boolean existsByApiKey = this.clientService.validateApiKey(apiKey);

        if(existsByApiKey == false){
            throw new Exception("Invalid Api key.");
        }

        String positionUrl = "https://www.dreamjob.com/" + position.getName();
        position.setUrl(positionUrl);
        this.positionRepository.save(position);

        return positionUrl;
    }
}
