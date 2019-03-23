package com.waio.test;

import java.util.TreeMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.waio.controller.PaymentController;
import com.waio.paytm.model.TxnRequest;
import com.waio.paytm.pg.checksumKit.ChecksumGeneration;
import com.waio.paytm.pg.checksumKit.ChecksumVerification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SprotsApplicationTests {

	@Autowired
	ChecksumGeneration csg;// = new checksumGeneration();
	
	@Autowired
	ChecksumVerification cv;
	
	@Autowired
	PaymentController paytmController;
	
	@Test
	public void contextLoads() {
		TxnRequest txnRequest = new TxnRequest();
		paytmController.generateChecksum(txnRequest);
	}

}
