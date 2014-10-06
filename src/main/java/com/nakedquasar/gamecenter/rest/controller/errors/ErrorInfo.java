package com.nakedquasar.gamecenter.rest.controller.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorInfo {
	@JsonProperty("ErrorMessage")
    public final String message;

    public ErrorInfo(Exception ex) {
        this.message = ex.getMessage();
    }
    
    public ErrorInfo(String ex) {
        this.message = ex;
    }
}
