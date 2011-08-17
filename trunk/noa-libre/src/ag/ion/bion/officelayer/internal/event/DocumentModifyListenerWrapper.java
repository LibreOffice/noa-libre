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

import ag.ion.bion.officelayer.event.IDocumentModifyListener;

import com.sun.star.lang.EventObject;
import com.sun.star.util.XModifyListener;

/**
 * A listener beeing implemented in an documenten to get
 * notified whenever there occurs a change.
 * 
 * @author Sebastian Rösgen
 */
public class DocumentModifyListenerWrapper implements XModifyListener  {

  IDocumentModifyListener  documentListener = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new DocumentListenerWrapper.
   * 
   * @param documentListener document listener to be wrapped
   * 
   * @throws IllegalArgumentException if the submitted document listener is not valid
   * 
   * @author Sebastian Rösgen
   */
  public DocumentModifyListenerWrapper(IDocumentModifyListener documentListener) throws IllegalArgumentException {
    if (documentListener == null) 
      throw new IllegalArgumentException("No listener specified, listener is null");
    this.documentListener = documentListener;
  }    
  // -----------------------------------------------------------------------
  /**
   * This will get called whenever the document is beeing disposed, 
   * or the listener itself gets disposed.
   * 
   * @param eventObject event object to be used
   * 
   * @author Sebastian Rösgen
   */
  public void disposing(EventObject eventObject) {
    documentListener.disposing(new Event(eventObject));
  }
  // -----------------------------------------------------------------------
  /**
   * Will be called if any change occurs in the document.
   * 
   * @param eventObject event object to be used
   * 
   * @author Sebastian Rösgen
   */
  public void modified(EventObject eventObject) {
    documentListener.reactOnUnspecificEvent(new Event(eventObject));
  }
  // -----------------------------------------------------------------------
  
}
