package com.waio.cricapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class FileRead {

	public static void main(String[] args) throws IOException {

		Set<String> set = new HashSet<String>();
        try {

            File f = new File("G:\\Logfile.txt");

            BufferedReader br = new BufferedReader(new FileReader(f));

            String readLine = "";

            System.out.println("Reading file using Buffered Reader");

            while ((readLine = br.readLine()) != null) {
            	if(readLine.contains("KEYABC")) {
            		System.out.println(readLine.indexOf("KEYABC")+"KEYABC".length());
            		String subStr = readLine.substring(readLine.indexOf("KEYABC")+"KEYABC".length());
                    set.add(subStr.trim());
            	}
            }

            for(String s : set) {
            	System.out.println(s); 
            }
            
            // ----------------------------------
            try{  
            	Class.forName("com.mysql.jdbc.Driver");  
            	Connection con=DriverManager.getConnection(  
            	"jdbc:mysql://localhost:3306/waio","root","root");  
            	//here sonoo is database name, root is username and password  
            	Statement stmt=con.createStatement();  
            	ResultSet rs=stmt.executeQuery("select * from value_string");  
            	while(rs.next())  
            	System.out.println(rs.getInt(1)+"  "+rs.getString(2));  
            	con.close();  
            	}catch(Exception e)
            	{
            		System.out.println(e);
            	}       
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
