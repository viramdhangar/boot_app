package com.waio.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mashape.unirest.http.Unirest;
import com.waio.fast2sms.model.OTPResponse;
import com.waio.fast2sms.model.QuickTemplatesAPI;
import com.waio.fast2sms.model.WalletApi;
import com.waio.service.IFast2sms;

@Service("Fast2sms")
public class Fast2sms implements IFast2sms {

	public WalletApi walletBalance() {
		final String uri = "https://www.fast2sms.com/dev/wallet?authorization=laQAvpsMn2yf3okhPH8igEBCLXdxrTwquYDcJRK7Ie45Z61FzmrK3vwg7VHILcjNtPaX1QC28y96S5zJ";
		RestTemplate restTemplate = new RestTemplate();
		try {
			WalletApi result = restTemplate.getForObject(uri, WalletApi.class);
			System.out.println(result);
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public QuickTemplatesAPI quickTemplates() {
		final String uri = "https://www.fast2sms.com/dev/quick-templates?authorization=laQAvpsMn2yf3okhPH8igEBCLXdxrTwquYDcJRK7Ie45Z61FzmrK3vwg7VHILcjNtPaX1QC28y96S5zJ";
		RestTemplate restTemplate = new RestTemplate();
		try {
			QuickTemplatesAPI result = restTemplate.getForObject(uri, QuickTemplatesAPI.class);
			System.out.println(result);
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public OTPResponse sendOTPGET(String numbers) {
		String authorization = "laQAvpsMn2yf3okhPH8igEBCLXdxrTwquYDcJRK7Ie45Z61FzmrK3vwg7VHILcjNtPaX1QC28y96S5zJ";
		String sender_id = "FSTSMS";
		String language = "english";
		String route = "qt";
		String message = "8187";
		String variables = "{BB}";
		String variables_values = "245666";
		final String uri = "https://www.fast2sms.com/dev/bulk?authorization=" + authorization + "&sender_id="
				+ sender_id + "&language=" + language + "&route=" + route + "&numbers=" + numbers + "&message="
				+ message + "&variables=" + variables + "&variables_values=" + variables_values + "";
		RestTemplate restTemplate = new RestTemplate();
		try {
			OTPResponse result = restTemplate.getForObject(uri, OTPResponse.class);
			System.out.println(result);
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public String sendOTP(String numbers, String otp) {
		try {
			com.mashape.unirest.http.HttpResponse<String> response = Unirest.post("https://www.fast2sms.com/dev/bulk")
					.header("authorization",
							"laQAvpsMn2yf3okhPH8igEBCLXdxrTwquYDcJRK7Ie45Z61FzmrK3vwg7VHILcjNtPaX1QC28y96S5zJ")
					.header("cache-control", "no-cache").header("content-type", "application/x-www-form-urlencoded")
					.body("sender_id=FSTSMS&language=english&route=qt&numbers="+numbers+"&message=8187&variables={#BB#}&variables_values="+otp+"")
					.asString();

			System.out.println(response.getBody());
			return response.getBody();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
