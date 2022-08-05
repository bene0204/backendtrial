package com.benem.findyourdreamjob.positions;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping("/positions")
    public String addPosition(
            @RequestBody Position position,
            @Param("apiKey") String apiKey) throws Exception {
        return this.positionService.addPosition(position, apiKey);
    }

    @GetMapping("/positions")
    public List<Position> findMatchingPositions(
            @Length(max = 50) @Param("name") String name,
            @Length(max = 50) @Param("location") String location,
            @Param("apiKey") String apiKey) throws Exception {

        return this.positionService.findMatchingPositions(name, location, apiKey);
    }
}
