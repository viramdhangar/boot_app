package com.waio.instamojo;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.waio.instamojo.model.TokenResponse;

@Service("Token")
public class Token {
	public TokenResponse getTokenAccess() {
		try {
			com.mashape.unirest.http.HttpResponse<String> response = Unirest
					.post("https://api.instamojo.com/oauth2/token/")
					.header("authorization", "9d5214a700be2b358d36c0e3b4a6e363").header("cache-control", "no-cache")
					.header("content-type", "application/x-www-form-urlencoded")
					.body("grant_type=client_credentials&client_id=WDjwEckQq1od93cxLq1eEQOFD7AR4xGWXbFhRkpQ&client_secret=0wI3AbXdG78K89GMQOohu5kTIL98VmrKlLSxaeBzTV6r3c1awBCRP8tkJBy8BHhIFCp4MO0XbAr6OQMtQnHCYm4021Mj6eEPLHJOWnJDcORZwNQGc07jlPBqNy5mDyEQ")
					.asString();

			System.out.println(response.getBody());
			TokenResponse tr = fromJson(response.getBody());
			return tr;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public TokenResponse fromJson(String json) throws JsonParseException, JsonMappingException, IOException {
		TokenResponse tokenRes = new ObjectMapper().readValue(json, TokenResponse.class);
		return tokenRes;
	}
}
