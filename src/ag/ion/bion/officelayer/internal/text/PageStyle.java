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
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.IPageStyle;
import ag.ion.bion.officelayer.text.IPageStyleProperties;

import com.sun.star.beans.XPropertySet;

import com.sun.star.style.XStyle;

import com.sun.star.uno.UnoRuntime;

/**
 * Style of page of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class PageStyle implements IPageStyle {

  private XStyle xStyle = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new PageStyle.
   * 
   * @param xStyle OpenOffice.org XStyle interface
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org XStyle interface is not valid
   * 
   * @author Andreas Bröker
   */
  public PageStyle(XStyle xStyle) throws IllegalArgumentException {
    if(xStyle == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XStyle interface is not valid.");
    this.xStyle = xStyle;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns name of the page style. 
   * 
   * @return name of the page style
   * 
   * @author Andreas Bröker
   */
  public String getName() {
    return xStyle.getName();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns page style properties.
   * 
   * @return page style properties
   * 
   * @author Andreas Bröker
   */
  public IPageStyleProperties getProperties() {
    XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xStyle);
    return new PageStyleProperties(xPropertySet);
  }
  //----------------------------------------------------------------------------
}