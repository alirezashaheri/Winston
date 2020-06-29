package io.shaheri.Winston.logger.model;

import java.io.Serializable;

public class LogModel implements Serializable {
    private String echo;
    private Long timestamp;
    private String input;
    private Status status;
    private String output;
    private String identifier;


    public LogModel(String echo, Long timestamp, String input, Status status, String output, String identifier) {
        this.echo = echo;
        this.timestamp = timestamp;
        this.input = input;
        this.status = status;
        this.output = output;
        this.identifier = identifier;
    }

    public LogModel() {
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
