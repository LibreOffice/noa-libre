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
package ag.ion.bion.officelayer.internal.application;

import ag.ion.bion.officelayer.application.IApplicationProperties;

import java.util.Properties;

/**
 * Properties of an office application.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class ApplicationProperties implements IApplicationProperties {

  private Properties properties = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new ApplicationProperties.
   * 
   * @param properties properties to be used
   * 
   * @throws IllegalArgumentException if the submitted properties are not valid
   * 
   * @author Andreas Bröker
   */
  public ApplicationProperties(Properties properties) throws IllegalArgumentException {
    if(properties == null)
      throw new IllegalArgumentException("The submitted properties are not valid.");
    this.properties = properties;
  }    
  //----------------------------------------------------------------------------
  /**
   * Returns property value with the submitted name. Returns null
   * if the property is not available.
   * 
   * @param name name of the property
   * 
   * @return property value with the submitted name or null
   * if the property is not available
   * 
   * @author Andreas Bröker
   */
  public String getPropertyValue(String name) {
   return properties.getProperty(name);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns available property names.
   * 
   * @return available property names
   * 
   * @author Andreas Bröker
   */
  public String[] getPropertyNames() {
    return (String[])properties.keySet().toArray(new String[properties.size()]);
  }
  //----------------------------------------------------------------------------
  
}