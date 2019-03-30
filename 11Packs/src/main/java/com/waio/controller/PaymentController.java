package com.waio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waio.instamojo.Token;
import com.waio.instamojo.model.TokenResponse;
import com.waio.paytm.model.TxnRequest;
import com.waio.paytm.pg.checksumKit.ChecksumGeneration;

@CrossOrigin(origins = { "*" }, maxAge = 3600)
@RestController
@RequestMapping({ "/paytm" })
public class PaymentController {

	@Autowired
	ChecksumGeneration csg;

	@Autowired
	Token token;
	
	@PostMapping(value = "/generate/checksum")
	public ResponseEntity<Object> generateChecksum(@RequestBody TxnRequest txnRequest) {
		try {
			txnRequest = csg.checksum(txnRequest);
			return new ResponseEntity<>(txnRequest, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Checksum not generated", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/access/token")
	public ResponseEntity<String> accessToken() {
		try {
			TokenResponse tokenResponse = token.getTokenAccess();
			return new ResponseEntity<String>("production"+tokenResponse.getAccess_token(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Token not generated", HttpStatus.BAD_REQUEST);
		}
	}
}
