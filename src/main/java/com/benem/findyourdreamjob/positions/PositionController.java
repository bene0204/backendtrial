package com.benem.findyourdreamjob.positions;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@Validated
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping("/positions")
    public String addPosition(
            @Valid @RequestBody Position position,
            @RequestParam("apiKey") String apiKey){
        return this.positionService.addPosition(position, apiKey);
    }

    @GetMapping("/positions")
    public List<Position> findMatchingPositions(
            @RequestParam("name") @Length(max = 50)  String name,
           @RequestParam("location") @Length(max = 50)  String location,
            @RequestParam("apiKey") String apiKey) throws UnsupportedEncodingException {

        return this.positionService.findMatchingPositions(name, location, apiKey);
    }
}
