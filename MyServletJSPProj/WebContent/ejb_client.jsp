<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="myBeans.MyBEanRemote, javax.naming.* , java.util.Properties" %>

<%!
  private MyBEanRemote hel=null;
  public void jspInit()
  {
    try
    {
      Properties p=new Properties();
      p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
      p.put(Context.PROVIDER_URL,"t3://localhost:7001"); //"t3://localhost:7001"
      InitialContext ctx=new InitialContext(p);
      hel=(MyBEanRemote)ctx.lookup("java:global.MyWLSApp.MyEJBProj.MyBEan!myBeans.MyBEanRemote");
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
%>
<%
  String result=null;
  String name=null;
  try 
  {
    name=request.getParameter("name");
    if(name!=null)
      result=hel.Hello(name);
  }
  catch(Exception e)
  {
    result="Not Valid";
  }
%>

<html>
<head>
<title>Example of Stateless Session Bean</title>
</head>
<body>
<h1>
Using Stateless Session Bean</h1>

<form action="ejb_client.jsp" method="POST">
Enter Your Name : <input type="text" name="name">
<input type="submit" value="Submit"><br><br>
</form>
<%
if(result!=null)
out.println("<b>"+result+"</b>");
%>
</body>
</html>