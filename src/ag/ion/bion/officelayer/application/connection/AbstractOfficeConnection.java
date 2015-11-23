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

import ag.ion.bion.officelayer.internal.event.EventListenerWrapper;

import com.sun.star.bridge.XBridge;

import com.sun.star.lang.XComponent;

import com.sun.star.uno.UnoRuntime;

/**
 * Abstract connection in order to communicate with an OpenOffice.org 
 * application.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public abstract class AbstractOfficeConnection implements IOfficeConnection {

	protected XBridge xBridge = null;
	
  //----------------------------------------------------------------------------
  /**
   * Constructs service with the XMultiServiceFactory.
   * 
   * @param serviceName name of the service
   * 
   * @return service object
   * 
   * @throws Exception if any error occurs
   */
  public Object createService(String serviceName) throws Exception {
  	if(getXComponentContext() != null) {
  		Object service = getXMultiServiceFactory().createInstance(serviceName);
  		return service;
  	}
  	else {
  		return null;
  	}
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs service with the XMultiComponentFactory.
   * 
   * @param serviceName name of the service
   * 
   * @return service object
   * 
   * @throws Exception if any error occurs
   */
  public Object createServiceWithContext(String serviceName) throws Exception {
  	if(getXComponentContext() != null) {
  		Object service = this.getXMultiComponentFactory().createInstanceWithContext(serviceName, getXComponentContext());
  		return service;
  	}
  	else {
  		return null;
  	}
  }
  //----------------------------------------------------------------------------
  /**
   * Adds new listener for the internal XBridge of OpenOffice.org.
   * 
   * @param eventListener new event listener
   * 
   * @author Andreas Bröker
   */
  public void addBridgeEventListener(IEventListener eventListener) {
    if (xBridge != null) {
      if (eventListener != null) {
        EventListenerWrapper eventListenerWrapper = new EventListenerWrapper(eventListener, null);
        XComponent xComponent = (XComponent) UnoRuntime.queryInterface(XComponent.class, xBridge);
        xComponent.addEventListener(eventListenerWrapper);
      }
    }
  }
  //----------------------------------------------------------------------------
}