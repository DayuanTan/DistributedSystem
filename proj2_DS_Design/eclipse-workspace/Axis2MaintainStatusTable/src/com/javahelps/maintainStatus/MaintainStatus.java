package com.javahelps.maintainStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.*;

public class MaintainStatus {
	public static void main(String[] args) throws Exception{
		int rowcount = 0;
		String[] wsdls = new String[rowcount];
		String[] namespaces = new String[rowcount];
		
		Connection c;
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
			String sql = "select wsdl,namespace from distributedSystemDesign.servicesStatus";
			PreparedStatement pst = c.prepareStatement(sql);
			//exe the query and get result
			ResultSet rs = pst.executeQuery();
			//iterate through the sql result
			if (rs.last()) {
				rowcount = rs.getRow();
				rs.beforeFirst();
			}
			wsdls = new String[rowcount];
			namespaces = new String[rowcount];
			System.out.println("We have " + rowcount + " services totally.");
			System.out.println("They are:"); 
			for (int i =0;i<rowcount;i++) {
				rs.next();
				wsdls[i] = rs.getString("wsdl");
				namespaces[i] = rs.getString("namespace");
				System.out.println(wsdls[i]); 
			}
			System.out.println("------ ------ ------\n"); 
			
			
			
			c.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} 
		
		
		
		while(1 == 1) {
			//check whether active every half minute
			TimeUnit.SECONDS.sleep(30);
			
			for (int i=0;i<wsdls.length;i++) {
				System.out.println("Checking " + wsdls[i]); 
				
				//call webService using RPC
				RPCServiceClient serviceClient = new RPCServiceClient();
				Options options = serviceClient.getOptions();
			
				//Call webService URL
				EndpointReference targetEPR = new EndpointReference(wsdls[i]);
				options.setTo(targetEPR);
			
				//set parameters
				Object[] opAddEntryArgs	= new Object[] {};
				//set return's type's class object
				Class[] classes = new Class[] {float.class};
				//set method we want to call, parameters are (targetNamespace, targetMethod)
				QName opAddEntry = new QName(namespaces[i],"active");
				//call
				float r = (float)(serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs,classes)[0]);
				System.out.println("Status of " + wsdls[i] + " is " + r + " (1.0 means active).");
				System.out.println("------ ------ ------\n");
				
				//update service status table
				UpdateActive(wsdls[i], r);
			}
		}
		
	}
	
	public static void UpdateActive(String wsdl, float activeStatus) throws Exception{
		Connection c;
	
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
			String sql = "set SQL_SAFE_UPDATES=0";
			PreparedStatement pst = c.prepareStatement(sql);
			//exe the query and get result
			ResultSet rs = pst.executeQuery();
			//sql query 
			sql = "update distributedSystemDesign.servicesStatus set active= ? where wsdl = ? ";
			pst = c.prepareStatement(sql);
			float tmp=1;
			if ( activeStatus==tmp){
				pst.setInt(1, 1);//service active
			}else {
				pst.setInt(1, 0);//service dead
			}
			pst.setString(2, wsdl);
			//exe the query and get result
			pst.executeUpdate();
			//sql query 
			sql = "set SQL_SAFE_UPDATES=1";
			pst = c.prepareStatement(sql);
			//exe the query and get result
			pst.executeQuery();
			
			
			c.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} 
	}
}
