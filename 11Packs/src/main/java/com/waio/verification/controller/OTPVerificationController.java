/**
 * 
 */
package com.waio.verification.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.waio.email.api.IEmailService;
import com.waio.email.api.MailResponse;
import com.waio.verification.model.OTPSystem;

/**
 * @author Viramm
 *
 */

@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RestController
@RequestMapping({ "/verification" })
public class OTPVerificationController {
	
	@Autowired
	IEmailService emailService;

	private Map<String, OTPSystem> otpData = new HashMap<>();
	private final static String ACCOUNT_SID= "AC49760cfa045068324aa0cf3fce89bc62";
	private final static String AUTH_TOKEN= "1f6a835b5f9859d7cf5feefea879b00a";
	
	static {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}
	
	@PostMapping(value="/sendMobileOTP/{mobileNumber}/otp")
	public ResponseEntity<Object> sendMobileOTP(@PathVariable("mobileNumber") String mobileNumber){
		OTPSystem otpSystem = new OTPSystem();
		otpSystem.setMobileNumber(mobileNumber);
		otpSystem.setOtp(String.valueOf(((int)(Math.random()*(10000 - 1000))) + 1000));
		otpSystem.setExpiryTime(System.currentTimeMillis() + 60000);
		otpData.put(mobileNumber, otpSystem);
		Message.creator(new PhoneNumber("+91"+mobileNumber+""), new PhoneNumber("+13342316476"), "Your OTP is "+otpSystem.getOtp()).create();
		return new ResponseEntity<>("OTP is send successfully", HttpStatus.OK);
	}
	
	@PostMapping(value="/verifyMobileOTP/{mobileNumber}/otp")
	public ResponseEntity<Object> verifyMobileOTP(@PathVariable("mobileNumber") String mobileNumber, @RequestBody OTPSystem requestOTPSystem){
		
		if(StringUtils.isEmpty(requestOTPSystem.getOtp())){
			return new ResponseEntity<>("Please provide OTP", HttpStatus.BAD_REQUEST);
		}
		if (otpData.containsKey(mobileNumber)) {
			OTPSystem otpSystem = otpData.get(mobileNumber);
			if (otpSystem != null) {
				if(otpSystem.getExpiryTime() >= System.currentTimeMillis()) {
					if(requestOTPSystem.getOtp().equalsIgnoreCase(otpSystem.getOtp())) {
						return new ResponseEntity<>("OTP is verified successfully", HttpStatus.OK);
					}
					return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>("OTP is expired", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>("Somthing went wrong..!!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Mobile number not found", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/sendEmailOTP/{email}/otp")
	public MailResponse sendEmailOTP(@PathVariable String email) {
		Map<String, Object> model = new HashMap<>();
		//model.put("name", request.getName());
		//model.put("location", "Mumbai, India");
		
		OTPSystem otpSystem = new OTPSystem();
		otpSystem.setEmail(email);
		otpSystem.setOtp(String.valueOf(((int)(Math.random()*(10000 - 1000))) + 1000));
		otpSystem.setExpiryTime(System.currentTimeMillis() + 30000);
		otpSystem.setEmailBody("Please use this OTP for verification "+otpSystem.getOtp());
		otpSystem.setSubject("Your OTP for Packs11 email verification");
		otpData.put(email, otpSystem);
		return emailService.sendResetEmail(otpSystem, model, "emailotp");
	}
	
	@PostMapping(value="/verifyEmailOTP/{email}/otp")
	public ResponseEntity<Object> verifyEmailOTP(@PathVariable("email") String email, @RequestBody OTPSystem requestOTPSystem){
		
		if(StringUtils.isEmpty(requestOTPSystem.getOtp())){
			return new ResponseEntity<>("Please provide OTP", HttpStatus.BAD_REQUEST);
		}
		if (otpData.containsKey(email)) {
			OTPSystem otpSystem = otpData.get(email);
			if (otpSystem != null) {
				if(otpSystem.getExpiryTime() >= System.currentTimeMillis()) {
					if(requestOTPSystem.getOtp().equalsIgnoreCase(otpSystem.getOtp())) {
						return new ResponseEntity<>("OTP is verified successfully", HttpStatus.OK);
					}
					return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>("OTP is expired", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>("Somthing went wrong..!!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
	}
}
