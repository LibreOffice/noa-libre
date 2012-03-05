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

import ag.ion.bion.officelayer.beans.IPropertyKey;
import ag.ion.bion.officelayer.internal.beans.AbstractProperties;
import ag.ion.bion.officelayer.internal.beans.PropertyKey;
import ag.ion.bion.officelayer.text.IPageStyleProperties;
import ag.ion.bion.officelayer.text.TextException;

import com.sun.star.beans.XPropertySet;

/**
 * Properties of a page style.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class PageStyleProperties extends AbstractProperties implements IPageStyleProperties {

  private static String[] DEFAULT_PROPERTY_KEYS = null;
  
  private static PropertyKey[] PROPERTY_KEYS = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new PageStyleProperties.
   * 
   * @param xPropertySet OpenOffice.org XPropertySet interface
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org XPropertySet interface is not valid
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public PageStyleProperties(XPropertySet xPropertySet) throws IllegalArgumentException {
    super(xPropertySet);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the possible property keys.
   * 
   * @return the possible property keys
   * 
   * @author Markus Krüger
   */
  public static IPropertyKey[] getPossiblyPropertyKeys() {
    if(PROPERTY_KEYS == null) {
      PROPERTY_KEYS = new PropertyKey[] {
          new PropertyKey("IsLandscape",null,null),
      };
    }
    return PROPERTY_KEYS;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the default property keys.
   * 
   * @return the default property keys
   * 
   * @author Markus Krüger
   */
  public static String[] getDefaultPropertyKeys() {
    if(DEFAULT_PROPERTY_KEYS == null) {
      DEFAULT_PROPERTY_KEYS = new String[] {
          "IsLandscape",
      };
    }
    return DEFAULT_PROPERTY_KEYS;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the id of the property.
   * 
   * @return the id of the property
   * 
   * @author Markus Krüger
   */
  public String getTypeID() {
    return TYPE_ID;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets property value for the page format.
   * 
   * @param isLandscape value for the page format
   * 
   * @throws TextException if the property can not be set
   * 
   * @author Andreas Bröker
   */
  public void setIsLandscape(boolean isLandscape) throws TextException {
    try {
      getXPropertySet().setPropertyValue("IsLandscape", new Boolean(isLandscape));
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns property value of the page format.
   * 
   * @return property value of the page format
   * 
   * @throws TextException if the property is not available
   * 
   * @author Andreas Bröker
   */
  public boolean getIsLandscape() throws TextException {
    try {
      return ((Boolean)getXPropertySet().getPropertyValue("IsLandscape")).booleanValue();
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  
}