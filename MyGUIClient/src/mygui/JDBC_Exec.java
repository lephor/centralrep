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
	
	
	public JDBC_Exec(String in_conn_pool, String WLS_URL, String direct_url, String fork) throws ClassNotFoundException, SQLException // test git comment
	{
		switch (fork)
		{
		case "connection_pool":
			try
			{
			Context ctx = null;
		    Hashtable ht = new Hashtable();
		    ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		    ht.put(Context.PROVIDER_URL, "t3://"+WLS_URL);
			
		    ctx = new InitialContext(ht);
		    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup (in_conn_pool);
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
			connection=DriverManager.getConnection("jdbc:oracle:thin:@"+direct_url,"SYSTEM","oracle"); 
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
	


}
