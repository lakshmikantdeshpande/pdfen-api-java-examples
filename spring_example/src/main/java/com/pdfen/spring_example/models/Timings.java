package com.pdfen.spring_example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Timings {

    @JsonProperty("process_time")
    public Double processTime;

    @JsonProperty("convert_time")
    public Double convertTime;

    @JsonProperty("download_time")
    public Integer downloadTime;

    @JsonProperty("webpages_time")
    public Integer webpagesTime;

    @JsonProperty("encrypt_time")
    public Integer encryptTime;
}
