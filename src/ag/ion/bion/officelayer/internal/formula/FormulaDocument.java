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
package ag.ion.bion.officelayer.internal.formula;

import ag.ion.bion.officelayer.document.AbstractDocument;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.formula.IFormulaDocument;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;

/**
 * OpenOffice.org formula document representation.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11637 $
 */
public class FormulaDocument extends AbstractDocument implements IFormulaDocument {

  private XPropertySet xPropertySet = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new OpenOffice.org formula document.
   * 
   * @param xPropertySet OpenOffice.org API interface of a formula document
   * @param intitialProperties the properties that were used loading the document
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org interface is not valid
   * 
   * @author Andreas Bröker
   */
  public FormulaDocument(XPropertySet xPropertySet, PropertyValue[] initialProperties)
      throws IllegalArgumentException {
    super((XComponent) UnoRuntime.queryInterface(XComponent.class, xPropertySet), initialProperties);
    this.xPropertySet = xPropertySet;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XPropertySet interface.
   * 
   * @return OpenOffice.org XPropertySet interface
   * 
   * @author Andreas Bröker
   */
  public XPropertySet getPropertySet() {
    return xPropertySet;
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
    return IDocument.MATH;
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

}