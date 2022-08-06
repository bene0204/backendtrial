package com.benem.findyourdreamjob.positions.response_models.muse_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor

public class MuseResults {

    @JsonProperty("name")
    private String name;

    @JsonProperty("locations")
    private List<Location> locations;

    @JsonProperty("refs")
    private Ref refs;

    public String getName() {
        return name;
    }

    public String getLocations() {
        String allLocations = "";

        for (Location location : locations ) {
            allLocations += location.getName() + " - ";
        }

        return allLocations;

    }

    public Ref getRefs() {
        return refs;
    }
}
