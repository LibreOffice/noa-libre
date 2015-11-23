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
package ag.ion.bion.officelayer.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * Container that can hold property keys for different properties.
 * Is used for cloning cell formats and to decide wich properties to clone, for example.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class PropertyKeysContainer {
  
  private Map propertyKeys = null;
  
  //----------------------------------------------------------------------------
  /**
   * Adds property keys for the property with the given id to this container.
   * If the properties id is already in this container it will be overwritten.
   * 
   * @param propertiesID the id of the properties object (i.e. #ICharacterProperties.TYPE_ID)
   * @param propertyStringKeys the keys of the properties
   * 
   * @author Markus Krüger
   */
  public void addPropertyKeys(String propertiesID, String[] propertyStringKeys) {
    if(propertiesID == null || propertyStringKeys == null)
      return;
    if(propertyKeys == null)
      propertyKeys = new HashMap();
    propertyKeys.put(propertiesID, propertyStringKeys);
  }
  //----------------------------------------------------------------------------
  /**
   * Removes property keys for the property with the given id from this container.
   * 
   * @param propertiesID the id of the properties object (i.e. #ICharacterProperties.TYPE_ID)
   * 
   * @author Markus Krüger
   */
  public void removePropertyKeys(String propertiesID) {
    if(propertiesID == null || propertyKeys == null)
      return;
    propertyKeys.remove(propertiesID);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns property keys for the property with the given id, or null, if key is not in container.
   * 
   * @param propertiesID the id of the properties object (i.e. #ICharacterProperties.TYPE_ID)
   * 
   * @return property keys for the property with the given id, or null, if key is not in container
   * 
   * @author Markus Krüger
   */
  public String[] getPropertyKeys(String propertiesID) {
    if(propertiesID == null || propertyKeys == null)
      return null;
    String[] keys = (String[])propertyKeys.get(propertiesID);
    return keys;
  }
  //----------------------------------------------------------------------------
}