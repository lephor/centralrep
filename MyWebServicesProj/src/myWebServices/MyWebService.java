package myWebServices;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "MyWebService", targetNamespace = "http://myWebServices/")
public interface MyWebService {

	
	@WebMethod(operationName = "add")
	public int add(int p1, int p2);
}