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

import ag.ion.bion.officelayer.event.IEvent;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.lang.EventObject;

/**
 * Base event.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class Event implements IEvent {

  private EventObject      eventOject      = null;
  private IServiceProvider serviceProvider = null;

  //----------------------------------------------------------------------------  
  /**
   * Constructs new Event.
   * 
   * @param eventOject OpenOffice.org EventObject to be used
   * @param serviceProvider the service provider to be used
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org EventObject is not valid
   * 
   * @author Andreas Bröker
   */
  public Event(EventObject eventOject, IServiceProvider serviceProvider)
      throws IllegalArgumentException {
    if (eventOject == null)
      throw new IllegalArgumentException("The submitted OpenOffice.org EventObject is not valid.");
    this.eventOject = eventOject;
    this.serviceProvider = serviceProvider;
  }
  //----------------------------------------------------------------------------  
  /**
   * Returns source object of the event.
   * 
   * @return source object of the event
   * 
   * @author Andreas Bröker
   */
  public Object getSourceObject() {
    return eventOject.Source;
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
  
}