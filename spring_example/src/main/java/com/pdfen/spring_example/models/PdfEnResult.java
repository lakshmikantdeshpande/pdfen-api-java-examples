package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PdfEnResult {

    @JsonProperty("process_id")
    public String processId;

    @JsonProperty("process_result")
    public ProcessResult processResult;

    @JsonProperty("process_progress")
    public List<String> processProgress;
}
