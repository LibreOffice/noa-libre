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

/**
 * Abstract properties.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public abstract class AbstractProperties implements IProperties {
  
	private XPropertySet xPropertySet = null;
  
  //----------------------------------------------------------------------------
  /**
   * Returns the id of the property.
   * 
   * @return the id of the property
   * 
   * @author Markus Krüger
   */
  public abstract String getTypeID();
  //----------------------------------------------------------------------------
  /**
   * Constructs new AbstractProperties.
   * 
   * @param xPropertySet OpenOffice.org XPropertySet interface
   * 
   * @throws IllegalArgumentException if the OpenOffice.org interface is not valid
   * 
   * @author Markus Krüger
   */
  public AbstractProperties(XPropertySet xPropertySet) throws IllegalArgumentException {
    if(xPropertySet == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org interface is not valid.");
    this.xPropertySet = xPropertySet;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the XPropertySet used for this properties.
   * 
   * @return the XPropertySet used for this properties
   * 
   * @author Markus Krüger
   */
  public XPropertySet getXPropertySet() {
    return xPropertySet;
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
   * @author Markus Krüger
   */
  public void copyTo(String[] propertyKeys, IProperties properties) throws OfficeException {
    XPropertySet foreignPropertySet = properties.getXPropertySet();
    for(int i = 0; i < propertyKeys.length; i++) {
      try {  
        String key = propertyKeys[i];      
        foreignPropertySet.setPropertyValue(key,xPropertySet.getPropertyValue(key));
      }
      catch(Exception exception) {
        OfficeException officeException = new OfficeException(exception.getMessage());
        officeException.initCause(exception);
        throw officeException;
      }
    }
  }
  //----------------------------------------------------------------------------
}
