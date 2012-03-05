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
package ag.ion.bion.officelayer.util;

import com.sun.star.beans.XPropertySet;
import com.sun.star.beans.XPropertySetInfo;
import com.sun.star.beans.Property;

import com.sun.star.uno.UnoRuntime;

import com.sun.star.i18n.NumberFormatIndex;

import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * Collection for OpenOffice.org Uno object properties. 
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class PropertyCollection {
	
  private HashMap collection = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new PropertyCollection.
   */
  public PropertyCollection() {
    collection = new HashMap();
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new PropertyCollection with the submitted initial capacity.
   * 
   * @param initialCapacity initial capacity of the collection
   */
  public PropertyCollection(int initialCapacity) {
    collection = new HashMap(initialCapacity);
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new PropertyCollection with the submitted initial capacity and load factor.
   * 
   * @param initialCapacity initial capacity of the collection
   * @param loadFactor load factor of the collection
   */
  public PropertyCollection(int initialCapacity, float loadFactor) {
    collection = new HashMap(initialCapacity, loadFactor);
  }
  //----------------------------------------------------------------------------
  /**
   * Adds new property to the collection.
   * 
   * @param name name of the property
   * @param value value of the property
   */
  public void addProperty(String name, Object value) {
    collection.put(name, value);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns value of the property with the submitted name.
   * 
   * @param name name of the property.
   * 
   * @return value of the property with the submitted name
   */
  public Object getPropertyValue(String name) {
  	return collection.get(name);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns property names.
   * 
   * @return property names
   */
  public Set getPropertyNames() {
  	return collection.keySet();
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the property with the submitted name is availble in this
   * collection.
   * 
   * @param name name of the property
   * 
   * @return information whether the property with the submitted name is availble in this
   * collection
   */
  public boolean containesProperty(String name) {
  	return collection.containsKey(name);
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns property collection of the submitted OpenOffice.org Uno object.
   * 
   * @param object OpenOffice.org Uno object
   * 
   * @return property collection of the submitted OpenOffice.org Uno object
   */
  public static PropertyCollection getPropertyCollection(Object object) {
  	try {
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, object);
      if(xPropertySet != null) {
        return getPropertyCollection(xPropertySet);
      }
      else {
      	return null; 
      }
    }
    catch(Exception exception) {
    	return null;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns property collection of the submitted OpenOffice.org XPropertySet.
   * 
   * @param xPropertySet OpenOffice.org XPropertySet
   * 
   * @return property collection of the submitted OpenOffice.org XPropertySet
   */
  public static PropertyCollection getPropertyCollection(XPropertySet xPropertySet) {
    try {
      XPropertySetInfo xPropertySetInfo = xPropertySet.getPropertySetInfo();
      Property[] property = xPropertySetInfo.getProperties();
      PropertyCollection propertyCollection = new PropertyCollection(property.length);      
      for(int i=0; i<property.length; i++) {
        String name = property[i].Name; 
        try {
        	propertyCollection.addProperty(name, xPropertySet.getPropertyValue(name));
        }
        catch(Exception exception) {
          //ignore
        }
      }
      return propertyCollection;
    }
    catch(Exception exception){
      return null;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns property collection of the submitted OpenOffice.org XPropertySet.
   * 
   * @param xPropertySet OpenOffice.org XPropertySet
   * @param names names to properties to be used
   * 
   * @return property collection of the submitted OpenOffice.org XPropertySet
   */
  public static PropertyCollection getPropertyCollection(XPropertySet xPropertySet, String[] names) {
    try {
      if(names != null) {
        
        PropertyCollection propertyCollection = new PropertyCollection(names.length);      
        for(int i=0; i<names.length; i++) {
          String name = names[i]; 
          try {
            propertyCollection.addProperty(name, xPropertySet.getPropertyValue(name));
          }
          catch(Exception exception) {
            //ignore
          }
        }
        return propertyCollection;
      }
      return null;
    }
    catch(Exception exception){
      return null;
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Sets property collection to submitted OpenOffice.org Uno object.   
   * 
   * @param propertyCollection property collection
   * @param object OpenOffice.org Uno object
   */
  public static void setPropertyCollection(PropertyCollection propertyCollection, Object object) {
    try {
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, object);
      if(xPropertySet != null) {
      	setPropertyCollection(propertyCollection, xPropertySet, null, null);
      }
    }
    catch(Exception exception) {
    	//ignore
    }
  }
  //----------------------------------------------------------------------------  
  /**
   * Sets property collection to submitted OpenOffice.org Uno object.   
   * 
   * @param propertyCollection property collection
   * @param object OpenOffice.org Uno object
   * @param orderProperties property names to be set with the submitted order
   */
  public static void setPropertyCollection(PropertyCollection propertyCollection, Object object, String[] orderProperties) {
    try {
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, object);
      if(xPropertySet != null) {
        setPropertyCollection(propertyCollection, xPropertySet, orderProperties, null);
      }
    }
    catch(Exception exception) {
      //ignore
    }
  }
  //----------------------------------------------------------------------------  
  /**
   * Sets property collection to submitted OpenOffice.org Uno object.   
   * 
   * @param propertyCollection property collection
   * @param object OpenOffice.org Uno object
   * @param orderProperties property names to be set with the submitted order
   * @param excludeProperties property names to be excluded
   */
  public static void setPropertyCollection(PropertyCollection propertyCollection, Object object, String[] orderProperties, String[] excludeProperties) {
    try {
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, object);
      if(xPropertySet != null) {
        setPropertyCollection(propertyCollection, xPropertySet, orderProperties, excludeProperties);
      }
    }
    catch(Exception exception) {
      //ignore
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Sets property collection to submitted OpenOffice.org XPropertySet.
   * 
   * @param propertyCollection property collection
   * @param xPropertySet OpenOffice.org XPropertySet
   * @param orderProperties property names to be set with the submitted order
   * @param excludeProperties property names to be excluded
   */
  public static void setPropertyCollection(PropertyCollection propertyCollection, XPropertySet xPropertySet, String[] orderProperties, String[] excludeProperties) {
    try {
      XPropertySetInfo xPropertySetInfo = xPropertySet.getPropertySetInfo();
      ArrayList order   = null;
      ArrayList exclude = null;
      
      if(excludeProperties != null) {
      	exclude = new ArrayList(excludeProperties.length);
        for(int i=0; i<excludeProperties.length; i++) {
        	exclude.add(excludeProperties[i]);
        }
      }      
      
      if(orderProperties != null) {
        order = new ArrayList(orderProperties.length);
      	for(int i=0; i<orderProperties.length; i++) {
          try {
          	xPropertySet.setPropertyValue(orderProperties[i], propertyCollection.getPropertyValue(orderProperties[i]));
            order.add(orderProperties[i]);
          }
          catch(Exception exception) {
          	//ignore
          }
        }
      }      
      
      Property[] property = xPropertySetInfo.getProperties();      
      for(int i=0; i<property.length; i++) {
        boolean useProperty = true;
        String name = property[i].Name;          
        if(order != null) {
        	if(order.contains(name)) {
        		useProperty = false;
          }
        } 
        
        if(exclude != null) {
        	if(exclude.contains(name)) {
            useProperty = false;
          }
        }
        
        if(useProperty) {
          try {
            if(propertyCollection.containesProperty(name)) {
              if(name.equalsIgnoreCase("NumberFormat")) {
              	if(((Integer)propertyCollection.getPropertyValue("NumberFormat")).intValue() == 256) {
                  /** 
                   * Hack - OpenOffice.org uses the constant 256 for text format in table cells. But in order
                   * to set the text format in a table cell OpenOffice.org expected the contstant NumberFormatIndex.TEXT.
                   */
                  xPropertySet.setPropertyValue(name, new Integer(NumberFormatIndex.TEXT));
                }
                else {
                  xPropertySet.setPropertyValue(name, propertyCollection.getPropertyValue(name)); 
                }
              }
              else {
              	xPropertySet.setPropertyValue(name, propertyCollection.getPropertyValue(name)); 
              }
            }
          }
          catch(Exception exception) {
            //ignore
          }
        }
      }
    }
    catch(Exception exception) {
      //ignore
    }
  }
  //----------------------------------------------------------------------------
}
