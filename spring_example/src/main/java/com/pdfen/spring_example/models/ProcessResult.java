package com.pdfen.spring_example.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProcessResult {

    @JsonProperty("id")
    public String id;

    @JsonProperty("datetime_created")
    public String datetimeCreated;

    @JsonProperty("url")
    public String url;

    @JsonProperty("type_of_action")
    public String typeOfAction;

    @JsonProperty("type_of_output_file")
    public String typeOfOutputFile;

    @JsonProperty("file_size_mb")
    public Integer fileSizeMb;

    @JsonProperty("pages")
    public Integer pages;

    @JsonProperty("credits")
    public Integer credits;

    @JsonProperty("credits_left")
    public Integer creditsLeft;

    @JsonProperty("timings")
    public Timings timings;

    @JsonProperty("status")
    public String status;

    @JsonProperty("messages")
    public List<String> messages;
}
