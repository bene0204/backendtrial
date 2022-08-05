package com.benem.findyourdreamjob.positions.response_models.muse_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MuseResults {

    @JsonProperty("name")
    private String name;

    @JsonProperty("locations")
    private List<Location> locations;

    @JsonProperty("refs")
    private Ref refs;


}
