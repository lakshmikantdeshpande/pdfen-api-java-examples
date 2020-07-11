package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProcessSettings {

    @JsonProperty("process_synchronous")
    private boolean processSynchronous = true;

    @JsonProperty("immediate")
    private boolean immediate = true;
}
