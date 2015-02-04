package myWebServices;

import javax.jws.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

// for testing http://localhost:7001/ws_utc

@WebService(portName = "MyWebServicePort", serviceName = "MyWebServiceService", targetNamespace = "http://myWebServices/", endpointInterface = "myWebServices.MyWebService")
public class MyWebServiceImpl implements MyWebService {

	public int add(int p1, int p2)
	{
		return p1 + p2;
	}
	
	public String getCount(String connPool, String table) 
	{
		
		try {
			String sql = "select GETCOUNTFORTABLEGETCOUNT('" + table + "') ret from dual";
			Context ic = new InitialContext();
			DataSource dataSource = 
			  (DataSource) ic.lookup(connPool);
			System.out.println("lookup dataSource returned " + dataSource);
			Connection connection = dataSource.getConnection();
			System.out.println("Got connection: " + connection);
 
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			return rs.getString("ret") + " <- from getCount webservice on WLS ";
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure in webservice execution " + e.toString();
		} 
        

	}
}
