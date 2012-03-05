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
 * Last changes made by $Author: markus $, $Date: 2008-11-18 11:22:42 +0100 (Di, 18 Nov 2008) $
 */
package ag.ion.bion.officelayer.application;

/**
 * Lazy information provider of an office application.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11681 $
 */
public interface ILazyApplicationInfo {

  /** Empty array of lazy application info objects. */
  public static final ILazyApplicationInfo[] EMPTY_LAZY_APPLICATION_INFOS_ARRAY = new ILazyApplicationInfo[0];

  //----------------------------------------------------------------------------
  /**
   * Returns home of the office application.
   * 
   * @return home of the office application
   * 
   * @author Andreas Bröker
   */
  public String getHome();

  //----------------------------------------------------------------------------
  /**
   * Returns properties of the office application. The properties
   * will be invetigated from the bootstrap.ini file. Returns null
   * if the properties are not available.
   * 
   * @return properties of the office application or null
   * if the properties are not available
   * 
   * @author Andreas Bröker
   */
  public IApplicationProperties getProperties();

  //----------------------------------------------------------------------------  
  /**
   * Returns major version of the office application. Returns <code>-1</code>
   * if the major version is not available.
   * 
   * @return major version of the office application or <code>-1</code>
   * if the major version is not available
   * 
   * @author Andreas Bröker
   */
  public int getMajorVersion();

  //----------------------------------------------------------------------------  
  /**
   * Returns minor version of the office application. Returns <code>-1</code>
   * if the minor version is not available.
   * 
   * @return minor version of the office application or <code>-1</code>
   * if the minor version is not available
   * 
   * @author Andreas Bröker
   */
  public int getMinorVersion();

  //----------------------------------------------------------------------------  
  /**
   * Returns update version of the office application. Returns <code>-1</code>
   * if the update version is not available.
   * 
   * @return update version of the office application or <code>-1</code>
   * if the update version is not available
   * 
   * @author Andreas Bröker
   */
  public int getUpdateVersion();
  //----------------------------------------------------------------------------  

}