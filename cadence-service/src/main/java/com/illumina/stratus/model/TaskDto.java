package com.illumina.stratus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {

    @JsonProperty("TaskToken")
    String taskToken;

    @JsonProperty("Input")
    String input;

    @JsonProperty("Output")
    String output;

    public String toInputString() {
        return String.format("{\"TaskToken\":%s, \"Input\":%s}", getTaskToken(), getInput());
    }

    public String toOutputString() {
        return String.format("{\"TaskToken\":%s, \"Output\":%s}", getTaskToken(), getOutput());
    }
}
