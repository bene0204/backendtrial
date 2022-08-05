package com.benem.findyourdreamjob.positions;

import com.benem.findyourdreamjob.clients.ClientService;
import com.benem.findyourdreamjob.positions.response_models.muse_api.MuseResponse;
import com.benem.findyourdreamjob.positions.response_models.muse_api.MuseResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private ClientService clientService;

    @Override
    public String addPosition(Position position, String apiKey) throws Exception {

        this.checkApiKeyValidity(apiKey);

        String positionUrl = "https://www.dreamjob.com/" + position.getName().replace(" ", "_").toLowerCase();
        position.setUrl(positionUrl);
        this.positionRepository.save(position);

        return positionUrl;
    }

    @Override
    public List<Position> findMatchingPositions(String name, String location,String apiKey) throws Exception {
        this.checkApiKeyValidity(apiKey);

        List<Position> matchingPositions = this.positionRepository.findPositionByNameIgnoreCaseAndLocationIgnoreCase(name,location);

        String baseUrl = "https://www.themuse.com/api/public/jobs";

        String charset = StandardCharsets.UTF_8.toString();

        String nameUrlEncoded = URLEncoder.encode(name, charset);
        String locationUrlEncoded = URLEncoder.encode(location,charset);

        String url = baseUrl +
                "?category=" + nameUrlEncoded +
                "&location=" + locationUrlEncoded +
                "&page=0";

        RestTemplate restTemplate = new RestTemplate();

        MuseResponse museResponse = restTemplate.getForObject(url, MuseResponse.class);

        for (MuseResults result : museResponse.getResults() ) {

            Position newPosition = Position.builder()
                    .name(result.getName())
                    .location(result.getLocations().get(0).getName())
                    .url(result.getRefs().getLandingPage())
                    .build();

            matchingPositions.add(newPosition);
        }

        return matchingPositions;

    }

    private void checkApiKeyValidity(String apiKey) throws Exception {
        boolean existsByApiKey = this.clientService.validateApiKey(apiKey);

        if(!existsByApiKey){
            throw new Exception("Invalid Api key.");
        }
    }
}
