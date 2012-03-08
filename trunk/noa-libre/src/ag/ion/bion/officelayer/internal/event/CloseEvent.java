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

import ag.ion.bion.officelayer.event.ICloseEvent;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.lang.EventObject;

/**
 * Close event.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class CloseEvent extends Event implements ICloseEvent {

  private boolean veto = false;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new CloseEvent.
   * 
   * @param eventOject OpenOffice.org EventObject to be used
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org EventObject is not valid
   * 
   * @author Andreas Bröker
   */
  public CloseEvent(EventObject eventOject, IServiceProvider serviceProvider)
      throws IllegalArgumentException {
    super(eventOject, serviceProvider);
  }
  //----------------------------------------------------------------------------
  /**
   * Sets veto information in order to stop the close event.
   * 
   * @param veto veto information
   * 
   * @author Andreas Bröker 
   */
  public void setVeto(boolean veto) {
    this.veto = veto;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the close event should be stopped.
   * 
   * @return information whether the close event should be stopped
   * 
   * @author Andreas Bröker
   */
  public boolean getVeto() {
    return veto;
  }
  //----------------------------------------------------------------------------
 
}