package javaStoredProcProj;

import java.sql.*;
import java.io.*;
import oracle.jdbc.*;

public class getCountForTable {


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
 		 
 		 return table_name + "'s count = " + output + " <- from javaSP ";
         
         }
      catch(SQLException e) {
         System.err.println("ERROR! Querying table " + table_name +" count: " 
           + e.getMessage());
         return e.getMessage();
         }
   }
}


/*

java name "getCount" by source upload

CREATE OR REPLACE FUNCTION getCountForTablegetCount (table_name VARCHAR2)
   RETURN VARCHAR2 AS LANGUAGE JAVA
   NAME 'javaStoredProcProj.getCountForTable.getCount(String) return String';


drop java class getCountForTable
drop java source GETCOUNTFORTABLE

DECLARE
  EMP_NO VARCHAR2(200);

 BEGIN
  EMP_NO:= GETCOUNTFORTABLEGETCOUNT('scott.emp');
  DBMS_OUTPUT.PUT_LINE(EMP_NO);
 END;
 
 select GETCOUNTFORTABLEGETCOUNT('scott.emp') from dual;

*/



