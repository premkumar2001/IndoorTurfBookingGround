package com.example.IndoorTurfBookingGround.response;

public class BooleanResponseModel {

    private boolean response;

    public BooleanResponseModel(boolean response) {
        this.response = response;
    }

    public BooleanResponseModel() {
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
