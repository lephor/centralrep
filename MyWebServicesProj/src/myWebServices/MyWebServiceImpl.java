package myWebServices;

import javax.jws.*;

// http://localhost:7001/ws_utc

@WebService(portName = "MyWebServicePort", serviceName = "MyWebServiceService", targetNamespace = "http://myWebServices/", endpointInterface = "myWebServices.MyWebService")
public class MyWebServiceImpl implements MyWebService {

	public int add(int p1, int p2)
	{
		return p1 + p2;
	}
}
