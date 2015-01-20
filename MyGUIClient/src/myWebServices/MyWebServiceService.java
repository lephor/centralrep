/**
 * MyWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package myWebServices;

public interface MyWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getMyWebServicePortAddress();

    public myWebServices.MyWebService getMyWebServicePort() throws javax.xml.rpc.ServiceException;

    public myWebServices.MyWebService getMyWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
