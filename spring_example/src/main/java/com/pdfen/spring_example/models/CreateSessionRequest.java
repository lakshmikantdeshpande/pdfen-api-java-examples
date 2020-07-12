package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSessionRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
