/**
 * 
 */
package com.waio.util;

import org.apache.commons.lang3.StringUtils;

import com.waio.cricapi.MatchesDTO;

/**
 * @author Viramm
 * 
 * will contain reusable methods
 *
 */
public class DataUtils {

	/**
	 * @param matches
	 */
	public static String getShortForm(String teamName) {
		String[] words = teamName.split("\\W+");
		StringBuffer sb = new StringBuffer();
		if(words.length == 1) {
			return teamName.substring(0,  3).toUpperCase();
		}
		for(String str : words) {
			//if(sb.length()<1) {
				//sb.append(str.substring(0,3));
			//}else {
				sb.append(str.substring(0,1));	
			//}
		}
		return sb.toString().toUpperCase();
	}
	

	/**
	 * @param matches
	 */
	public static void getMatchTypeShort(MatchesDTO matches) {
		if(matches.getType().contains("20")) {
			matches.setTypeShort("T20");
		} else if (matches.getType().contains("10")) {
			matches.setTypeShort("T10");
		} else if (matches.getType().contains("ODI")) {
			matches.setTypeShort("ODI");
		} else if (matches.getType().contains("Test")) {
			matches.setTypeShort("Test");
		} else {
			matches.setTypeShort(matches.getType());
		}
	}
	public static double valueInDouble(String value) {
		if(StringUtils.isNotEmpty(value)){
			return Double.parseDouble(value);
		}else {
			return 0.0;
		}
	}
	
	public static double valueInDoubleFromObj(Object value) {
		if(StringUtils.isNotEmpty(value.toString())){
			return Double.parseDouble(value.toString());
		}else {
			return 0.0;
		}
	}
}
