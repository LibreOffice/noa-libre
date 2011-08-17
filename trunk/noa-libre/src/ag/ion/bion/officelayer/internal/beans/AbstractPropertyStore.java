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
package ag.ion.bion.officelayer.internal.beans;

import com.sun.star.beans.XPropertySet;

import ag.ion.bion.officelayer.OfficeException;
import ag.ion.bion.officelayer.beans.IProperties;
import ag.ion.bion.officelayer.beans.IPropertyKey;
import ag.ion.bion.officelayer.beans.IPropertyStore;

/**
 * Abstract class for property stores.
 * 
 * @author Markus Kr�ger
 * @version $Revision: 10398 $
 */
public abstract class AbstractPropertyStore implements IProperties, IPropertyStore {
  
  //----------------------------------------------------------------------------
  /**
   * Returns the id of the property.
   * 
   * @return the id of the property
   * 
   * @author Markus Kr�ger
   */
  public String getTypeID() {
    //TODO implementation needed
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the properties.
   * 
   * @return the properties
   * 
   * @author Markus Kr�ger
   */
  public abstract IProperties getProperties();
  //----------------------------------------------------------------------------
  /**
   * Returns the XPropertySet used for this properties.
   * 
   * @return the XPropertySet used for this properties
   * 
   * @author Markus Kr�ger
   */
  public XPropertySet getXPropertySet() {
    //TODO implementation needed
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Copies the properties for the given keys of this properties into the given properties.
   * 
   * @param propertyKeys the keys of the properties to be copied
   * @param properties the properties to copy properties into
   * 
   * @throws OfficeException if copy failed
   * 
   * @author Markus Kr�ger
   */
  public void copyTo(String[] propertyKeys, IProperties properties) throws OfficeException {
    //TODO implementation needed
  }
  //----------------------------------------------------------------------------
  
}
