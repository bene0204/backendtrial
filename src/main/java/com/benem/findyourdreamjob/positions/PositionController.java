package com.benem.findyourdreamjob.positions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping("/positions")
    public String addPosition(@RequestBody Position position, @Param("apiKey") String apiKey) throws Exception {
        return this.positionService.addPosition(position, apiKey);
    }
}
