/**
 * MyWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package myWebServices;

public class MyWebServiceServiceLocator extends org.apache.axis.client.Service implements myWebServices.MyWebServiceService {

    public MyWebServiceServiceLocator() {
    }


    public MyWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MyWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MyWebServicePort
    private java.lang.String MyWebServicePort_address = "http://192.168.1.25:7001/MyWebServicesProj/MyWebServiceService";

    public java.lang.String getMyWebServicePortAddress() {
        return MyWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MyWebServicePortWSDDServiceName = "MyWebServicePort";

    public java.lang.String getMyWebServicePortWSDDServiceName() {
        return MyWebServicePortWSDDServiceName;
    }

    public void setMyWebServicePortWSDDServiceName(java.lang.String name) {
        MyWebServicePortWSDDServiceName = name;
    }

    public myWebServices.MyWebService getMyWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MyWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMyWebServicePort(endpoint);
    }

    public myWebServices.MyWebService getMyWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            myWebServices.MyWebServicePortBindingStub _stub = new myWebServices.MyWebServicePortBindingStub(portAddress, this);
            _stub.setPortName(getMyWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMyWebServicePortEndpointAddress(java.lang.String address) {
        MyWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (myWebServices.MyWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                myWebServices.MyWebServicePortBindingStub _stub = new myWebServices.MyWebServicePortBindingStub(new java.net.URL(MyWebServicePort_address), this);
                _stub.setPortName(getMyWebServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MyWebServicePort".equals(inputPortName)) {
            return getMyWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://myWebServices/", "MyWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://myWebServices/", "MyWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MyWebServicePort".equals(portName)) {
            setMyWebServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
