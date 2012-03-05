/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                     						*
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2006 by IOn AG                                            *
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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/

/*
 * Last changes made by $Author: markus $, $Date: 2008-08-18 10:30:49 +0200 (Mo, 18 Aug 2008) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.text.IGlobalTextDocument;

import com.sun.star.beans.PropertyValue;
import com.sun.star.text.XTextDocument;

/**
 * OpenOffice.org global text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11637 $
 * @date 16.03.2006
 */
public class GlobalTextDocument extends TextDocument implements IGlobalTextDocument {

  //----------------------------------------------------------------------------
  /**
   * Constructs new GlobalTextDocument.
   * 
   * @param xTextDocument XTextDocument OpenOffice.org interface to be used
   * @param intitialProperties the properties that were used loading the document
   * 
   * @author Andreas Bröker
   * @date 16.03.2006
   */
  public GlobalTextDocument(XTextDocument xTextDocument, PropertyValue[] initialProperties) {
    super(xTextDocument, initialProperties);
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
    return IDocument.GLOBAL;
  }
  //----------------------------------------------------------------------------

}