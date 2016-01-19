package com.elmo.spring.persistence.helpers;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionManager  {
	String userid = "ELMS";
	String password = "pass123"; 
	String jobURL ="jdbc:oracle:thin:@localhost:1521:xe";
	
	private static ConnectionManager instance = null;
	protected ConnectionManager() {
		
		
	}
	
	public static ConnectionManager getInstance()  {
		
		synchronized (ConnectionManager.class) {
			
		if(instance==null){
			
			instance= new ConnectionManager();
		}
		}
		return instance;
	}
	
	public Connection getConnection()  {
		
		OracleDataSource ds;
	    try {
			ds = new OracleDataSource();
			ds.setURL(this.jobURL);
		    return ds.getConnection(this.userid,this.password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
