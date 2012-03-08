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

import ag.ion.bion.officelayer.event.ICloseListener;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.lang.EventObject;

import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseListener;

/**
 * Wrapper in order to register a close listener in OpenOffice.org.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class CloseListenerWrapper extends EventListenerWrapper implements XCloseListener {

  private ICloseListener closeListener = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new CloseListenerWrapper.
   * 
   * @param closeListener close listener to be wrapped
   * @param serviceProvider the service provider to be used
   *  
   * @throws IllegalArgumentException if the submitted close listener is not valid
   * 
   * @author Andreas Bröker
   */
  public CloseListenerWrapper(ICloseListener closeListener, IServiceProvider serviceProvider)
      throws IllegalArgumentException {
    super(closeListener, serviceProvider);
    this.closeListener = closeListener;
  }

  //----------------------------------------------------------------------------
  /**
   * Is called when somewhere tries to close listened object.
   * 
   * @param eventObject source event object
   * @param getsOwnership information about the ownership
   * 
   * @throws CloseVetoException close veto exception
   * 
   * @author Andreas Bröker
   */
  public void queryClosing(EventObject eventObject, boolean getsOwnership)
      throws CloseVetoException {
    CloseEvent closeEvent = new CloseEvent(eventObject, getServiceProvider());
    closeListener.queryClosing(closeEvent, getsOwnership);
    if(closeEvent.getVeto())
      throw new CloseVetoException();    
  }
  //----------------------------------------------------------------------------
  /**
   * Is called when the listened object is closed really.
   * 
   * @param eventObject event object
   * 
   * @author Andreas Bröker
   */
  public void notifyClosing(EventObject eventObject) {
    closeListener.notifyClosing(new CloseEvent(eventObject, getServiceProvider()));
  }
  //----------------------------------------------------------------------------
  
}