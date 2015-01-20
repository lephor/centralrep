package mygui;

import java.sql.*;  
import javax.swing.table.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.*;


public class JDBC_Exec {
	
	public Connection connection;
	public Statement stmt;
	public ResultSet rs;
	
	public JDBC_Exec(String fork) throws ClassNotFoundException, SQLException // test git comment
	{
		switch (fork)
		{
		case "connection_pool":
			try
			{
			Context ctx = null;
		    Hashtable ht = new Hashtable();
		    ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		    ht.put(Context.PROVIDER_URL, "t3://192.168.1.25:7001");
			
		    ctx = new InitialContext(ht);
		    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup ("JMS-JDBC-Data-Source-0");
		    connection = ds.getConnection();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("connection_pool");
			break;
		default:
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			connection=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.25:1522:orc","SYSTEM","oracle"); 
			System.out.println("direct");
		}
	}
	
	public ResultSetTableModel getResultSetTableModel(String query ) throws SQLException
	{
		if (connection == null)
		    throw new IllegalStateException("Connection already closed.");

		// Create a Statement object that will be used to excecute the query.
		// The arguments specify that the returned ResultSet will be 
		// scrollable, read-only, and insensitive to changes in the db.
		Statement statement =
		    connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		// Run the query, creating a ResultSet
		ResultSet r = statement.executeQuery(query);
		// Create and return a TableModel for the ResultSet
		return new ResultSetTableModel(r);
	}
	
	public void CreateConnection()
	{
		try
			{  
			//step1 load the driver class  

			  
			//step3 create the statement object  
			stmt=connection.createStatement();  
			  
			//step4 execute query  
			rs=stmt.executeQuery("select * from scott.emp");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			  
			//step5 close the connection object  
			connection.close();  
			  
			}
		catch(Exception e)
		{ System.out.println(e);}  
	}

}
