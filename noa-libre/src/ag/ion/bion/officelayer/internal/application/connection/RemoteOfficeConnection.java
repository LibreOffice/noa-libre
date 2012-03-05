/****************************************************************************
 * ubion.ORS - The Open Report Suite                                        *
 *                                                                          *
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * Subproject: NOA (Nice Office Access)                                     *
 *                                                                          *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2005 by IOn AG                                            *
 *                                                                          *
 * This library is free software; you can redistribute it and/or            *
 * modify it under the terms of the GNU Lesser General Public               *
 * License version 2.1, as published by the Free Software Foundation.       *
 *                                                                          *
 * This library is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of           *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU        *
 * Lesser General Public License for more details.                          *
 *                                                                          *
 * You should have received a copy of the GNU Lesser General Public         *
 * License along with this library; if not, write to the Free Software      *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,                    *
 * MA  02111-1307  USA                                                      *
 *                                                                          *
 * Contact us:                                                              *
 *  http://www.ion.ag                                                       *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.bion.officelayer.internal.application.connection;

import ag.ion.bion.officelayer.application.connection.AbstractOfficeConnection;

import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;

import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XComponent;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

import com.sun.star.beans.XPropertySet;

import com.sun.star.comp.helper.Bootstrap;

import com.sun.star.connection.XConnector;
import com.sun.star.connection.XConnection;

import com.sun.star.bridge.XBridgeFactory;

/**
 * Connection in order to communicate with remote OpenOffice.org 
 * application.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class RemoteOfficeConnection extends AbstractOfficeConnection {  
    
  private XMultiComponentFactory  xMultiComponentFactory  = null;
  private XMultiServiceFactory    xMultiServiceFactory    = null;
  private XComponentContext       xRemoteContext          = null;
  
  private String host = null;
  private String port = null;
  
  private boolean isConnectionEstablished = false;
  //----------------------------------------------------------------------------
  /**
   * Constructs new RemoteOfficeConnection.
   */
  public RemoteOfficeConnection () {
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new RemoteOfficeConnection with host and port.
   * 
   * @param host host where Office instance is listening 
   * @param port port where Office instance is listening
   */
  public RemoteOfficeConnection(String host, String port) {
    this.host = host;
    this.port = port;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets host where Office instance is listening.
   * 
   * @param host host where Office instance is listening 
   */  
  public void setHost(String host) {
    this.host = host;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets port where Office instance is listening
   * 
   * @param port port where Office instance is listening
   */  
  public void setPort(String port) {
    this.port = port;
  }
  //----------------------------------------------------------------------------
  /**
   * Opens connection to OpenOffice.org.
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * 
   * @return information whether the connection is available
   * 
   * @throws Exception if any error occurs
   */
  public boolean openConnection(IOfficeProgressMonitor officeProgressMonitor) throws Exception {
    return openConnection();
  }
  //----------------------------------------------------------------------------
  /**
   * Opens connection to OpenOffice.org.
   * 
   * @return information whether the connection is available
   * 
   * @throws Exception if any error occurs
   */
  public boolean openConnection() throws Exception {
    String unoUrl = "uno:socket,host=" + host + ",port=" + port +";urp;StarOffice.ServiceManager";
    XComponentContext xLocalContext = Bootstrap.createInitialComponentContext(null);
    Object connector = xLocalContext.getServiceManager().createInstanceWithContext("com.sun.star.connection.Connector", xLocalContext);
    XConnector xConnector = (XConnector) UnoRuntime.queryInterface(XConnector.class, connector);
    
    String url[] = parseUnoUrl(unoUrl);
    if (null == url) {
      throw new com.sun.star.uno.Exception("Couldn't parse UNO URL "+ unoUrl);
    }
    
    XConnection connection = xConnector.connect(url[0]);
    Object bridgeFactory = xLocalContext.getServiceManager().createInstanceWithContext("com.sun.star.bridge.BridgeFactory", xLocalContext);
    XBridgeFactory xBridgeFactory = (XBridgeFactory) UnoRuntime.queryInterface(XBridgeFactory.class, bridgeFactory);
    xBridge = xBridgeFactory.createBridge("", url[1], connection ,null);
    bridgeFactory = xBridge.getInstance(url[2]);
    xMultiComponentFactory = (XMultiComponentFactory)UnoRuntime.queryInterface(XMultiComponentFactory.class, bridgeFactory);
    XPropertySet xProperySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xMultiComponentFactory);
    Object remoteContext = xProperySet.getPropertyValue("DefaultContext");
    xRemoteContext = (XComponentContext) UnoRuntime.queryInterface(XComponentContext.class, remoteContext);
    xMultiServiceFactory = (XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, xMultiComponentFactory);
    isConnectionEstablished = true;
    return true;      
  }
  //----------------------------------------------------------------------------
  /**
   * Closes connection to OpenOffice.org.
   */
  public void closeConnection() {
    xMultiComponentFactory  = null;
    xMultiServiceFactory    = null;
    xRemoteContext          = null;
    XComponent xComponent = (XComponent)UnoRuntime.queryInterface(XComponent.class, xBridge);
    if(xComponent != null) {    
      try {
        xComponent.dispose();
        isConnectionEstablished = false;
      }
      catch(Exception exception) {
        //do nothing
      }
    }    
    xBridge = null; 
  }
  //----------------------------------------------------------------------------
  /**
   * Returns XComponentContext.
   * 
   * @return XComponentContext
   */
  public XComponentContext getXComponentContext() {
    return xRemoteContext;
  }  
  //----------------------------------------------------------------------------
  /**
   *  Returns true if connection is established.
   *  
   * @return isConnectionEstablished
   */
  public boolean isConnected () {
     return isConnectionEstablished; 
   }
  //----------------------------------------------------------------------------
  /**
   * Returns XMultiComponentFactory.
   * 
   * @return XMultiComponentFactory
   */
  public XMultiComponentFactory getXMultiComponentFactory() {
    return xMultiComponentFactory;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns XMultiServiceFactory.
   * 
   * @return XMultiServiceFactory
   */
  public XMultiServiceFactory getXMultiServiceFactory() {
    return xMultiServiceFactory;
  } 
  //----------------------------------------------------------------------------
  /**
   * Returns host of the connection
   * 
   * @return host of the connection
   */
  public String getHost() {
  	return host;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns port of the connection.
   * 
   * @return port of the connection.
   */
  public String getPort() {
  	return port;
  }  
  //----------------------------------------------------------------------------
  /**
   * Converts UNO url to string array.
   * 
   * @param unoUrl UNO URL to be parsed
   * 
   * @return converted UNO url 
   */
  private String[] parseUnoUrl(String unoUrl) {
    String [] aRet = new String [3];
    if(!unoUrl.startsWith( "uno:" )) {
      return null;
    }

    int semicolon = unoUrl.indexOf(';');
    if( semicolon == -1 )
      return null;
    
    aRet[0] = unoUrl.substring(4, semicolon );
    int nextSemicolon = unoUrl.indexOf(';', semicolon+1);

    if( semicolon == -1 )
      return null;
    
    aRet[1] = unoUrl.substring( semicolon+1, nextSemicolon );
    aRet[2] = unoUrl.substring( nextSemicolon+1);
    return aRet;
  }
  //----------------------------------------------------------------------------
}
