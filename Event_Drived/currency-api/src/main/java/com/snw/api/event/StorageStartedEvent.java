package com.snw.api.event;

public class StorageStartedEvent {
    private String position;

    public StorageStartedEvent(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
