package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateFileResponse {

    @JsonProperty("file_id")
    private String fileId;
}
