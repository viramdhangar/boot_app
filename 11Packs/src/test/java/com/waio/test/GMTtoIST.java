package com.waio.test;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.squareup.okhttp.OkHttpClient;
import com.waio.email.api.EmailService;
import com.waio.instamojo.model.TokenResponse;

public class GMTtoIST {
	public static void main(String args[]) throws ParseException {

		GMTtoIST dmd = new GMTtoIST();

		
		/*
		 * OkHttpClient client = new OkHttpClient();
		 * 
		 * MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		 * RequestBody body = RequestBody.create(mediaType,
		 * "grant_type=client_credentials&client_id=test&client_secret=test"); Request
		 * request = new Request.Builder()
		 * .url("https://test.instamojo.com/oauth2/token/") .post(body)
		 * .addHeader("content-type", "application/x-www-form-urlencoded")
		 * .addHeader("cache-control", "no-cache") .build();
		 * 
		 * Response response = client.newCall(request).execute();
		 */
	}

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
		TokenResponse garima = new ObjectMapper().readValue(json, TokenResponse.class);
		return garima;
	}
}