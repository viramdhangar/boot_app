package com.waio.paytm.pg.checksumKit;

import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.paytm.merchant.CheckSumServiceHelper;
import com.waio.paytm.model.TxnRequest;
import com.waio.util.PaytmConstants;

@Service("ChecksumGeneration")
public class ChecksumGeneration {
	
	//Below parameters provided by Paytm
	private static String MercahntKey = PaytmConstants.STG_MERCHANT;
	
	public TxnRequest checksum(TxnRequest txnRequest){
		
		// create txn request
		createTxnRequest(txnRequest);
		
		TreeMap<String,String> paramMap = new TreeMap<String,String>();
		paramMap.put("MID" , txnRequest.getMID());
		paramMap.put("ORDER_ID" , txnRequest.getORDER_ID());
		paramMap.put("CUST_ID" , txnRequest.getCUST_ID());
		paramMap.put("INDUSTRY_TYPE_ID" , txnRequest.getINDUSTRY_TYPE_ID());
		paramMap.put("CHANNEL_ID" , txnRequest.getCHANNEL_ID());
		paramMap.put("TXN_AMOUNT" , txnRequest.getTXN_AMOUNT());
		paramMap.put("WEBSITE" , txnRequest.getWEBSITE());
		paramMap.put("EMAIL" , txnRequest.getEMAIL());
		paramMap.put("MOBILE_NO" , txnRequest.getMOBILE_NO());
		paramMap.put("CALLBACK_URL" , txnRequest.getCALLBACK_URL());
		
		try{
		String checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(MercahntKey, paramMap);
		paramMap.put("CHECKSUMHASH" , checkSum);
		txnRequest.setCHECKSUMHASH(checkSum);
		txnRequest.setENVIRONMENT(PaytmConstants.STG_ENVIRONMENT);
		System.out.println("Paytm Payload: "+ paramMap);
		return txnRequest;
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private TxnRequest createTxnRequest(TxnRequest txnRequest) {
		
		txnRequest.setMID(PaytmConstants.STG_MERCHANT_ID);
		txnRequest.setORDER_ID(getOrderId());
		txnRequest.setCUST_ID("7977743584");
		txnRequest.setINDUSTRY_TYPE_ID(PaytmConstants.STG_INDUSTRY_TYPE_ID);
		txnRequest.setCHANNEL_ID("WAP");
		txnRequest.setTXN_AMOUNT("102");
		txnRequest.setWEBSITE(PaytmConstants.STG_WEBSITE);
		txnRequest.setEMAIL("viram.dhangar@gmail.com");
		txnRequest.setMOBILE_NO("7377743584s");
		txnRequest.setCALLBACK_URL(PaytmConstants.STG_CALLBACK_URL+getOrderId());
		
		return txnRequest;
	}
	
	public String getOrderId() {
		return "ORDER001";
	}
}