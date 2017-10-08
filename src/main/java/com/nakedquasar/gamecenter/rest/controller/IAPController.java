package com.nakedquasar.gamecenter.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nakedquasar.gamecenter.rest.controller.beans.IapSubmit;
import com.nakedquasar.gamecenter.rest.controller.beans.RestResponse;
import com.nakedquasar.gamecenter.rest.controller.errors.ErrorInfo;
import com.nakedquasar.gamecenter.security.Security;

@Controller
@RequestMapping("/iap")
public class IAPController {
	final static Logger logger = Logger.getLogger(IAPController.class);
	@Value("${key1}")
	private String key1;
	@Value("${key2}")
	private String key2;
	@Value("${key3}")
	private String key3;
	@Value("${key4}")
	private String key4;
	@Value("${key5}")
	private String key5;
	@Value("${key6}")
	private String key6;
	@Value("${key7}")
	private String key7;
	@Value("${key8}")
	private String key8;
	@Value("${key9}")
	private String key9;
	@Value("${key10}")
	private String key10;

	private List<String> keys;

	@RequestMapping(method = RequestMethod.POST, value = "/iapcheck")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RestResponse submitScore(@RequestBody String iap, HttpServletRequest request) {
		keys = new ArrayList<String>();
		keys.add(key1);
		keys.add(key2);
		keys.add(key3);
		keys.add(key4);
		keys.add(key5);
		keys.add(key6);
		keys.add(key7);
		keys.add(key8);
		keys.add(key9);
		keys.add(key10);
		
		ObjectMapper mapper = new ObjectMapper();
		RestResponse rre = new RestResponse();
		boolean state = false;
		// byte[] bytesEncoded = Base64.encodeBase64(str .getBytes());
		// System.out.println("ecncoded value is " + new String(bytesEncoded ));

		try {
			byte[] valueDecoded = Base64.decodeBase64(iap.getBytes());

			String decoded = encryptDecrypt(new String(valueDecoded));
			IapSubmit iapSubmit = mapper.readValue(decoded, IapSubmit.class);
			
			for (String key : keys) {
				state = Security.verifyPurchase(key, iapSubmit.getData(), iapSubmit.getSignature());
				if (state)
					break;
			}

			logger.info("IAP valid => " + state);

			iapSubmit.setValid(state);
			
			String serializedObj = mapper.writeValueAsString(iapSubmit) ; 
			String encoded = encryptDecrypt(serializedObj);
			byte[] bytesEncoded = Base64.encodeBase64(encoded.getBytes());
			
			rre.setObjectContainer(new String(bytesEncoded));

		} catch (Exception e) {
			rre.setObjectContainer(null);
			rre.setErrorInfo(new ErrorInfo(e));
		}

		return rre;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	RestResponse handleBadRequest(HttpServletRequest req, Exception ex) {
		return new RestResponse(null, new ErrorInfo(ex));
	}

	private String encryptDecrypt(String input) {
		String keyS = "nqcheck";
		char[] key = keyS.toCharArray();
		StringBuilder output = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			output.append((char) (input.charAt(i) ^ key[i % key.length]));
		}

		return output.toString();
	}

}
