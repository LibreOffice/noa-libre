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
package ag.ion.bion.officelayer.internal.event;

import ag.ion.bion.officelayer.event.IEventListener;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.lang.XEventListener;
import com.sun.star.lang.EventObject;

/**
 * Wrapper in order to register an event listener in OpenOffice.org.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class EventListenerWrapper implements XEventListener {

  private IEventListener   eventListener   = null;
  private IServiceProvider serviceProvider = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new EventListenerWrapper.
   * 
   * @param eventListener event listener to be wrapped
   * @param serviceProvider the service provider to be used
   * 
   * @throws IllegalArgumentException if the submitted event listener is not valid
   * 
   * @author Andreas Bröker
   */
  public EventListenerWrapper(IEventListener eventListener, IServiceProvider serviceProvider)
      throws IllegalArgumentException {
    if (eventListener == null)
      throw new IllegalArgumentException("The submitted event listener is not valid.");
    this.eventListener = eventListener;
    this.serviceProvider = serviceProvider;
  }

  //----------------------------------------------------------------------------
	/**
	 * Gets called when the broadcaster is about to be disposed. 
	 * 
	 * @param eventObject source event object
	 * 
     * @author Andreas Bröker
   */
  public void disposing(EventObject eventObject) {
    eventListener.disposing(new Event(eventObject, serviceProvider));
  }

  //---------------------------------------------------------------------------- 
  /**
   * Returns the service provider of the event.
   * 
   * @return the service provider of the event
   * 
   * @author Markus Krüger
   * @date 06.09.2010
   */
  public IServiceProvider getServiceProvider() {
    return serviceProvider;
  }

  //----------------------------------------------------------------------------  
  /**
   * Sets the service provider of the event.
   * 
   * @param serviceProvider the service provider of the event
   * 
   * @author Markus Krüger
   * @date 06.09.2010
   */
  public void setServiceProvider(IServiceProvider serviceProvider) {
    this.serviceProvider = serviceProvider;
  }
  //----------------------------------------------------------------------------    

}