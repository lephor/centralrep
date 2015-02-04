package javaStoredProcProj;

import java.sql.*;

//import oracle.jdbc.*;

public class getCountForTable {

   //Add an employee to the database.
   public static String getCount(String table_name)
   {
	  String output = "";

      System.out.println("Querying table count...");

      try {
         Connection conn =
            DriverManager.getConnection("jdbc:default:connection:");

         String sql = "select count(*) cnt from " + table_name;
         
 		 Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			// Run the query, creating a ResultSet
 		 ResultSet r = statement.executeQuery(sql);
 		 r.next();
 		 
 		 output = r.getString("cnt");
 		 
 		 statement.close();
 		 
 		 return table_name + "'s count = " + output + " from javaSP";
         
         }
      catch(SQLException e) {
         System.err.println("ERROR! Querying table " + table_name +" count: " 
           + e.getMessage());
         return e.getMessage();
         }
   }
}