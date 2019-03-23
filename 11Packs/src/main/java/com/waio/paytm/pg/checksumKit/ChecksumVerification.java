package com.waio.paytm.pg.checksumKit;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.paytm.merchant.CheckSumServiceHelper;
import com.waio.util.PaytmConstants;

@Service("ChecksumVerification")
public class ChecksumVerification {
	
	private static String MercahntKey = PaytmConstants.STG_MERCHANT;
	
	public boolean verifyCheckSum(Map<String, String> mapData){
		
		String paytmChecksum = "";
		
		TreeMap<String, String> paytmParams = new  TreeMap<String,String>();
		
		for (Map.Entry<String, String> entry : mapData.entrySet())
		{   
		    if(entry.getKey().equals("CHECKSUMHASH")){
				paytmChecksum = entry.getKey();
			}else{
				paytmParams.put(entry.getKey(), entry.getValue());
			}
		}
		
		
		boolean isValideChecksum = false;
		try{
			
			isValideChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(MercahntKey, paytmParams, paytmChecksum);
			
			System.out.println(isValideChecksum);
			
			// if checksum is validated Kindly verify the amount and status 
			// if transaction is successful 
			// kindly call Paytm Transaction Status API and verify the transaction amount and status.
			// If everything is fine then mark that transaction as successful into your DB.
		}catch(Exception e){
			e.printStackTrace();
		}
		return isValideChecksum;
	}
}