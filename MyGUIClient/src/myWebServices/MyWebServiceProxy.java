package myWebServices;

public class MyWebServiceProxy implements myWebServices.MyWebService {
  private String _endpoint = null;
  private myWebServices.MyWebService myWebService = null;
  
  public MyWebServiceProxy() {
    _initMyWebServiceProxy();
  }
  
  public MyWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initMyWebServiceProxy();
  }
  
  private void _initMyWebServiceProxy() {
    try {
      myWebService = (new myWebServices.MyWebServiceServiceLocator()).getMyWebServicePort();
      if (myWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)myWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)myWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (myWebService != null)
      ((javax.xml.rpc.Stub)myWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public myWebServices.MyWebService getMyWebService() {
    if (myWebService == null)
      _initMyWebServiceProxy();
    return myWebService;
  }
  
  public int add(int arg0, int arg1) throws java.rmi.RemoteException{
    if (myWebService == null)
      _initMyWebServiceProxy();
    return myWebService.add(arg0, arg1);
  }
  
  
}