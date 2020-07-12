package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FileSettings {

    @JsonProperty("title")
    private String title;

    @JsonProperty("extension")
    private String extension;

    @JsonProperty("keep_extension")
    private boolean keepExtension = true;
}
