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
package ag.ion.bion.officelayer.internal.document;

import com.sun.star.beans.PropertyValue;

import java.util.ArrayList;

/**
 * Class to set ServiceMediaDescriptor properties.  
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 */
public class PropertyDescriptor {
  
  private ArrayList arrayList = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new PropertyDescriptor. 
   */
  public PropertyDescriptor () {
    init ();
  }
  //----------------------------------------------------------------------------
  /**
   * Inits all private variables.  
   */
  private void init () {
    arrayList = new ArrayList();
  }
  //----------------------------------------------------------------------------
  /**
   * Sets property value.
   *  
   * @param name the property name attribute.
   * @param object the property value attribute.
   */
  public void setProperty(String name, Object object) {
    PropertyValue tempPValue []= new PropertyValue [1];
    tempPValue[0] = new PropertyValue ();
    tempPValue[0].Name = name;
    tempPValue[0].Value = object;
    arrayList.add(tempPValue[0]); 
  }
  //----------------------------------------------------------------------------
  /**
   * Activates the "AsTemplate" property.
   */
  public void activatePropertyAsTemplate () {
   PropertyValue tempPValue []= new PropertyValue [1];
    tempPValue[0] = new PropertyValue ();
    tempPValue[0].Name = "AsTemplate";
    tempPValue[0].Value = new Boolean (true);
    arrayList.add(tempPValue[0]); 
  }
  //----------------------------------------------------------------------------
  /**
   * Activates the "Hidden" property. 
   *
   */
   public void activatePropertyHidden () {
      PropertyValue tempPValue []= new PropertyValue [1];
      tempPValue[0] = new PropertyValue ();
      tempPValue[0].Name = "Hidden";
      tempPValue[0].Value = new Boolean (true);
      arrayList.add(tempPValue[0]);
   }
  //----------------------------------------------------------------------------  
  /**
   * Method to return the array of PropertyValues comprised in the object. 
   * 
   * @return propertyValue Array of property Values
   */
  public PropertyValue[] getProperties() {
    PropertyValue propertyValue[] = new PropertyValue[ arrayList.size() ];
    for (int i = 0; i<arrayList.size();i++) {
      propertyValue[i] = (PropertyValue) arrayList.get(i);   
    }
    return propertyValue; 
  }
  //----------------------------------------------------------------------------
}
