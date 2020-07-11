package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateSessionResponse {

    @JsonProperty("session_id")
    private String sessionId;
}