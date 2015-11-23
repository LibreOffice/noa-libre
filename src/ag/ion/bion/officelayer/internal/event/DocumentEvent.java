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
 * Last changes made by $Author: markus $, $Date: 2008-08-18 10:30:49 +0200 (Mo, 18 Aug 2008) $
 */
package ag.ion.bion.officelayer.internal.event;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.event.IDocumentEvent;
import ag.ion.bion.officelayer.internal.document.DocumentLoader;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.document.EventObject;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;

/**
 * Document event.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11637 $
 */
public class DocumentEvent extends Event implements IDocumentEvent {

  //----------------------------------------------------------------------------
  /**
   * Constructs new DocumentEvent.
   * 
   * @param eventOject OpenOffice.org EventObject to be used
   * @param serviceProvider the service provider to be used
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org event object is not valid
   * 
   * @author Andreas Bröker
   */
  public DocumentEvent(EventObject eventOject, IServiceProvider serviceProvider)
      throws IllegalArgumentException {
    super(eventOject, serviceProvider);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the document of the event.
   * 
   * @return the document of the event
   * 
   * @author Markus Krüger
   */
  public IDocument getDocument() {
    Object object = getSourceObject();
    XComponent xComponent = (XComponent) UnoRuntime.queryInterface(XComponent.class, object);
    return DocumentLoader.getDocument(xComponent, getServiceProvider(), null);
  }
  //---------------------------------------------------------------------------- 
}