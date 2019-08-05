package com.illumina.stratus.cadence.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {

    @JsonProperty("TaskToken")
    HashMap<String, Object> taskToken;

    @JsonProperty("Input")
    HashMap<String, Object> input;

    @JsonProperty("Output")
    HashMap<String, Object> output;

    public TaskDto(byte[] taskToken, String input) {
        this.taskToken = byteToObjectNode(taskToken);
        this.input = stringToObjectNode(input);
    }

    public String getTaskToken() {
        return jsonObjectToStringDefaultEmptyNode(this.taskToken);
    }

    public String getInput() {
        return jsonObjectToStringDefaultEmptyNode(this.input);
    }

    public String getOutput() {
        return jsonObjectToStringDefaultEmptyNode(this.output);
    }


    public void setInput(String input) {
        this.input = stringToObjectNode(input);
    }

    public void setOutput(String output) {
        this.output = stringToObjectNode(output);
    }

    public void setTaskToken(String taskToken) {
        this.taskToken = stringToObjectNode(taskToken);
    }

    public void setTaskToken(byte[] taskToken) {
        this.taskToken = byteToObjectNode(taskToken);
    }


    private String jsonObjectToStringDefaultEmptyNode(Object object) {
        String taskTokenString = "{}";
        try {
            taskTokenString = new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return taskTokenString;
    }

    private HashMap<String, Object> stringToObjectNode(String string) {
        HashMap map = new LinkedHashMap<>();
        try {
             map = new ObjectMapper().readValue(string, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private HashMap<String, Object> byteToObjectNode(byte[] bytes) {
        HashMap map = new LinkedHashMap<>();
        try {
            map = new ObjectMapper().readValue(bytes, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String toInputString() {
        return String.format("{\"TaskToken\":%s, \"Input\":%s}", getTaskToken(), getInput());
    }

    public String toOutputString() {
        return String.format("{\"TaskToken\":%s, \"Output\":%s}", getTaskToken(), getOutput());
    }

    @Override
    public String toString() {
        return jsonObjectToStringDefaultEmptyNode(this);
    }
}
