package com.javahelps.loadbaln;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JOptionPane;

public class UpdateBusy {
	public void UpdateBusyTrue(String methodPara, String ipv4, String port) throws Exception{
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
					sql = "update distributedSystemDesign.servicesStatus set busy=1 where method = ? and ipv4 = ? and port = ? ";
					pst = c.prepareStatement(sql);
					pst.setString(1, methodPara);
					pst.setString(2, ipv4);
					pst.setString(3, port);
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
	
	
	public void UpdateBusyFalse(String methodPara, String ipv4, String port) throws Exception{
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
					sql = "update distributedSystemDesign.servicesStatus set busy=0 where method = ? and ipv4 = ? and port = ? ";
					pst = c.prepareStatement(sql);
					pst.setString(1, methodPara);
					pst.setString(2, ipv4);
					pst.setString(3, port);
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
