package com.javahelps.loadbaln;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JOptionPane;

public class LoadBaln {
	public String[] whereServices(String serviceName) {
		String methodPara = "", wsdl = "", namespace = "", ipv4 = "", port = "";
		Connection c;
		
		if (serviceName.equals("sum")){
			methodPara = "getSum";
		}else if (serviceName.equals("subtract")){
			methodPara = "getDiff";
		}else if (serviceName.equals("multiply")){
			methodPara = "getProduct";
		}
		
		//find service WSDL form table
		//the JDBC connector class
		final String dbClassName = "com.mysql.cj.jdbc.Driver";
		//connection string
		final String CONNECTION = "jdbc:mysql://127.0.0.1/distributedSystemDesign";
		try {
			//load JDBC class
			Class.forName(dbClassName).newInstance();
			Properties p = new Properties();
			p.put("user", "dayuan");
			p.put("password", "1111");
			//connect
			c = DriverManager.getConnection(CONNECTION, p);
			//sql query 
			String sql = "Select wsdl,namespace,method,ipv4,port from distributedSystemDesign.servicesStatus where method = ? and active=1 and busy=0 limit 1";
		
			PreparedStatement pst = c.prepareStatement(sql);
			pst.setString(1, methodPara);
			//exe the query and get result
			ResultSet rs = pst.executeQuery();
			//iterate through the sql result
			while(rs.next()) {
				wsdl = rs.getString("wsdl");
				namespace = rs.getString("namespace");
				ipv4 = rs.getString("ipv4");
				port = rs.getString("port");
				
			}
			

			/*
			System.out.println("The assigned wsdl is:"  + wsdl);	
			System.out.println("The assigned nameSpace is:"  + namespace);	
			System.out.println("The assigned method is:"  + methodPara);	
			System.out.println("The assigned ipv4 is:"  + ipv4);
			System.out.println("The assigned port is:"  + port);
			*/
			
			c.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} 
		
		String[] str = new String[5];
		str[0] = wsdl;
		str[1] = namespace;
		str[2] = methodPara;
		str[3] = ipv4;
		str[4] = port;
		return str;
	}
}
