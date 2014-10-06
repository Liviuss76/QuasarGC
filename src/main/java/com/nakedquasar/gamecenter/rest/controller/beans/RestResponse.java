package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nakedquasar.gamecenter.rest.controller.errors.ErrorInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponse {
	@JsonProperty("objectContainer")
	private Object objectContainer;
	@JsonProperty("ErrorInfo")
	private ErrorInfo errorInfo;
	
	public RestResponse(){
		this.setObjectContainer(null);
		this.errorInfo = null;
	}
	
	public RestResponse(Object objectContainer, ErrorInfo errorInfo){
		this.setObjectContainer(objectContainer);
		this.errorInfo = errorInfo;
	}



	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Object getObjectContainer() {
		return objectContainer;
	}

	public void setObjectContainer(Object objectContainer) {
		this.objectContainer = objectContainer;
	}
} 
