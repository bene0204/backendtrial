package com.benem.findyourdreamjob.positions.response_models.muse_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MuseResponse {

    @JsonProperty("page_count")
    private int pageCount;
    @JsonProperty("results")
    private List<MuseResults> results;
}
