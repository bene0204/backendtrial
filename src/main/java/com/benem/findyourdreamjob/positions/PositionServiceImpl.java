package com.benem.findyourdreamjob.positions;

import com.benem.findyourdreamjob.clients.ClientService;
import com.benem.findyourdreamjob.exceptions.InvalidApiKeyException;
import com.benem.findyourdreamjob.positions.response_models.muse_api.MuseResponse;
import com.benem.findyourdreamjob.positions.response_models.muse_api.MuseResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
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
    public String addPosition(Position position, String apiKey){

        this.checkApiKeyValidity(apiKey);

        String positionUrl = "https://www.dreamjob.com/" + position.getName().replace(" ", "_").toLowerCase();
        position.setUrl(positionUrl);
        this.positionRepository.save(position);

        return positionUrl;
    }

    @Override
    public List<Position> findMatchingPositions(String name, String location,String apiKey) throws UnsupportedEncodingException {
        this.checkApiKeyValidity(apiKey);

        List<Position> matchingPositions = this.positionRepository.findPositionByNameIgnoreCaseAndLocationIgnoreCase(name,location);

        String nameUrlEncoded = this.encode(name);
        String locationUrlEncoded = this.encode(location);

        RestTemplate restTemplate = new RestTemplate();

        int pageNumber = 0;

        String url = this.getUrl(nameUrlEncoded, locationUrlEncoded,pageNumber);

        int pageCount = restTemplate.getForObject(url,MuseResponse.class).getPageCount();

        for(pageNumber = 0; pageNumber < pageCount; pageNumber++) {
            url = this.getUrl(nameUrlEncoded, locationUrlEncoded,pageNumber);
            MuseResponse museResponse = restTemplate.getForObject(url, MuseResponse.class);

            for (MuseResults result : museResponse.getResults()) {

                Position newPosition = Position.builder()
                        .name(result.getName())
                        .location(result.getLocations())
                        .url(result.getRefs().getLandingPage())
                        .build();

                matchingPositions.add(newPosition);
            }
        }
        return matchingPositions;

    }

    private void checkApiKeyValidity(String apiKey) {
        boolean existsByApiKey = this.clientService.validateApiKey(apiKey);

        if(!existsByApiKey){
            throw new InvalidApiKeyException("Invalid Api Key " + apiKey);
        }
    }

    private String encode(String param) throws UnsupportedEncodingException {
        String charset = StandardCharsets.UTF_8.toString();
        String encodedParam = URLEncoder.encode(param, charset);

        return encodedParam;
    }

    private String getUrl(String name, String location, int page) {
        String baseUrl = "https://www.themuse.com/api/public/jobs";

        String url = baseUrl +
                "?category=" + name +
                "&location=" + location +
                "&page=" + page;

        return url;
    }


}
