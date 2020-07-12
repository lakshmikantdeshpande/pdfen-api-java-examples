package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProcessRequest {

    @JsonProperty("process_settings")
    private ProcessSettings processSettings = new ProcessSettings();
}
