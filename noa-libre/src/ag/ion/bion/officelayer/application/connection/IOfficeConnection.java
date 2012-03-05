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
package ag.ion.bion.officelayer.application.connection;

import ag.ion.bion.officelayer.event.IEventListener;

import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;

import com.sun.star.uno.XComponentContext;

import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;

/**
 * Connection in order to communicate with an OpenOffice.org 
 * application.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public interface IOfficeConnection {
  
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
  public boolean openConnection(IOfficeProgressMonitor officeProgressMonitor) throws Exception;	
  //----------------------------------------------------------------------------
  /**
   * Opens connection to OpenOffice.org.
   * 
   * @return information whether the connection is available
   * 
   * @throws Exception if any error occurs
   */
  public boolean openConnection() throws Exception;
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the connection is active.
   * 
   * @return information whether the connection is active
   * 
   * @author Andreas Bröker
   */
  public boolean isConnected();  
  //----------------------------------------------------------------------------
  /**
   * Closes connection to OpenOffice.org.
   * 
   * @author Andreas Bröker
   */
  public void closeConnection();
  //----------------------------------------------------------------------------
  /**
   * Returns XComponentContext.
   * 
   * @return XComponentContext
   * 
   * @author Andreas Bröker
   */
  public XComponentContext getXComponentContext();
  //----------------------------------------------------------------------------
  /**
   * Returns XMultiServiceFactory.
   * 
   * @return XMultiServiceFactory
   * 
   * @throws Exception if anything fails
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public XMultiServiceFactory getXMultiServiceFactory() throws Exception;
  //----------------------------------------------------------------------------
  /**
   * Returns XMultiComponentFactory.
   * 
   * @return XMultiComponentFactory
   * 
   * @throws Exception if the closed connection could not be opened
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public XMultiComponentFactory getXMultiComponentFactory() throws Exception;
  //----------------------------------------------------------------------------
  /**
   * Returns host of the connection
   * 
   * @return host of the connection
   * 
   * @author Andreas Bröker
   */
  public String getHost();
  //----------------------------------------------------------------------------
  /**
   * Returns port of the connection.
   * 
   * @return port of the connection.
   * 
   * @author Andreas Bröker
   */
  public String getPort();  
  //----------------------------------------------------------------------------
  /**
   * Constructs service with the XMultiServiceFactory.
   * 
   * @param serviceName name of the service
   * 
   * @return service object
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public Object createService(String serviceName) throws Exception;
  //----------------------------------------------------------------------------
  /**
   * Constructs service with the XMultiComponentFactory.
   * 
   * @param serviceName name of the service
   * 
   * @return service object
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public Object createServiceWithContext(String serviceName) throws Exception;
  //----------------------------------------------------------------------------
  /**
   * Adds new listener for the internal XBridge of OpenOffice.org.
   * 
   * @param eventListener new event listener
   * 
   * @author Andreas Bröker
   */
  public void addBridgeEventListener(IEventListener eventListener);
  //----------------------------------------------------------------------------

}