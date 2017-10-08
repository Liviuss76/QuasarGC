package com.nakedquasar.gamecenter.rest.controller.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IapSubmit {
	@JsonProperty("Signature")
	private String Signature;
	
	@JsonProperty("SKU")
	private String SKU;
	
	@JsonProperty("Data")
	private String Data;
	
	@JsonProperty("Valid")
	private boolean Valid;

	public boolean isValid() {
		return Valid;
	}

	public void setValid(boolean valid) {
		Valid = valid;
	}

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}
}
