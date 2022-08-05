package com.benem.findyourdreamjob.positions.response_models.muse_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {

    @JsonProperty("name")
    private String name;
}
