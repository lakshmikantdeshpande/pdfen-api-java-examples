package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateFileRequest {

    @JsonProperty("file_settings")
    private FileSettings fileSettings;
}

