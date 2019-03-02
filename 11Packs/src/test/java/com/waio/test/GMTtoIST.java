package com.waio.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GMTtoIST{
  public static void main(String args[]) throws ParseException{
  
  Date today = new Date();
  
  SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
	DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	
	
	String strToday= formatter.format(today);  
	Date todayDate = format.parse(strToday);
	
  
  System.out.println("GMT Time: " + todayDate);
  }
}