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
package ag.ion.bion.officelayer.internal.presentation;

import ag.ion.bion.officelayer.document.AbstractDocument;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.presentation.IPageService;
import ag.ion.bion.officelayer.presentation.IPresentationDocument;

import com.sun.star.beans.PropertyValue;
import com.sun.star.lang.XComponent;
import com.sun.star.presentation.XPresentationSupplier;
import com.sun.star.uno.UnoRuntime;

/**
 * OpenOffice.org presentation document representation.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11637 $
 */
public class PresentationDocument extends AbstractDocument implements IPresentationDocument {

  private XPresentationSupplier xPresentationSupplier = null;
  private IPageService          pageService           = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new OpenOffice.org presentation document.
   * 
   * @param xPresentationSupplier OpenOffice.org interface of a presentation document
   * @param intitialProperties the properties that were used loading the document
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org interface is not valid
   * 
   * @author Andreas Bröker
   */
  public PresentationDocument(XPresentationSupplier xPresentationSupplier,
      PropertyValue[] initialProperties) throws IllegalArgumentException {
    super((XComponent) UnoRuntime.queryInterface(XComponent.class, xPresentationSupplier),
        initialProperties);
    this.xPresentationSupplier = xPresentationSupplier;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XPresentationSupplier interface.
   * 
   * @return OpenOffice.org XPresentationSupplier interface
   * 
   * @author Andreas Bröker
   */
  public XPresentationSupplier getPresentationSupplier() {
    return xPresentationSupplier;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns type of the document.
   * 
   * @return type of the document
   * 
   * @author Andreas Bröker
   */
  public String getDocumentType() {
    return IDocument.IMPRESS;
  }

  //----------------------------------------------------------------------------
  /**
   * Reformats the document.
   * 
   * @author Markus Krüger
   */
  public void reformat() {
    //TODO fill with logic
  }

  //----------------------------------------------------------------------------
  /**
   * Returns page service of the document.
   * 
   * @return page service of the document
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public IPageService getPageService() {
    if (pageService == null)
      pageService = new PageService(this);
    return pageService;
  }
  //----------------------------------------------------------------------------

}