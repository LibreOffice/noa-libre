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
 * Copyright 2003-2008 by IOn AG                                            *
 *                                                                          *
 * This library is free software throws Exception; you can redistribute it and/or            *
 * modify it under the terms of the GNU Lesser General Public               *
 * License version 2.1, as published by the Free Software Foundation.       *
 *                                                                          *
 * This library is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY throws Exception; without even the implied warranty of           *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU        *
 * Lesser General Public License for more details.                          *
 *                                                                          *
 * You should have received a copy of the GNU Lesser General Public         *
 * License along with this library throws Exception; if not, write to the Free Software      *
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

import java.util.Date;

/**
 * Information provider of an office application.
 * 
 * @author Markus Krüger
 * @version $Revision: 11681 $
 */
public interface IApplicationInfo {

  /** The root node for application information **/
  public static final String NODE_ROOT                   = "/org.openoffice.Setup";
  /** The node for office information from root **/
  public static final String NODE_OFFICE                 = "/Office";
  /** The node for product information from root **/
  public static final String NODE_PRODUCT                = "/Product";
  /** The node for L10N information from root **/
  public static final String NODE_L10N                   = "/L10N";

  /** The key for first wizard completed in office node **/
  public static final String KEY_OFFICE_FIRST_WIZARD     = "FirstStartWizardCompleted";
  /** The key for license accepted date in office node **/
  public static final String KEY_OFFICE_LICENSE_ACCEPTED = "LicenseAcceptDate";

  /** The key for locale in L10N node **/
  public static final String KEY_L10N_LOCALE             = "ooLocale";

  /** The key for application name in product node **/
  public static final String KEY_PRODUCT_NAME            = "ooName";
  /** The key for version in product node **/
  public static final String KEY_PRODUCT_VERSION         = "ooSetupVersion";
  /** The key for about box version in product node **/
  public static final String KEY_PRODUCT_ABOUT_VERSION   = "ooSetupVersionAboutBox";

  //----------------------------------------------------------------------------  
  /**
   * Returns the name of the office application, or <code>null</code> if not available.
   * 
   * @return the name of the office application, or <code>null</code> if not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public String getName() throws Exception;

  //----------------------------------------------------------------------------  
  /**
   * Returns the version of the office application. Returns <code>null</code>
   * if the version is not available.
   * 
   * @return the version of the office application or <code>null</code>
   * if the version is not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public String getVersion() throws Exception;

  //----------------------------------------------------------------------------  
  /**
   * Returns major version of the office application. Returns <code>-1</code>
   * if the major version is not available.
   * 
   * @return major version of the office application or <code>-1</code>
   * if the major version is not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public int getMajorVersion() throws Exception;

  //----------------------------------------------------------------------------  
  /**
   * Returns minor version of the office application. Returns <code>-1</code>
   * if the minor version is not available.
   * 
   * @return minor version of the office application or <code>-1</code>
   * if the minor version is not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public int getMinorVersion() throws Exception;

  //----------------------------------------------------------------------------  
  /**
   * Returns update version of the office application. Returns <code>-1</code>
   * if the update version is not available.
   * 
   * @return update version of the office application or <code>-1</code>
   * if the update version is not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public int getUpdateVersion() throws Exception;

  //----------------------------------------------------------------------------  
  /**
   * Returns the locale of the office application, or <code>null</code> if not available.
   * 
   * @return the locale of the office application, or <code>null</code> if not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public String getLocale() throws Exception;

  //----------------------------------------------------------------------------  
  /**
   * Returns the date when the license of the office application was accepted, or <code>null</code> if not available.
   * 
   * @return the date when the license of the office application was accepted, or <code>null</code> if not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public Date getLicenseAcceptDate() throws Exception;

  //----------------------------------------------------------------------------  
  /**
   * Returns if the first start wizard is already completed as Boolean, or <code>null</code> if not available.
   * 
   * @return if the first start wizard is already completed as Boolean, or <code>null</code> if not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public Boolean getFirstStartWizardCompleted() throws Exception;

  //----------------------------------------------------------------------------
  /**
   * This method can be used to get any info from the application that is available.
   * (Possible paths and keys can be retrieved with the dumpInfo method).
   * Returns the object described by the path and key, or <code>null</code> if not available.
   * 
   * @param path the path to the key information
   * @param key the key to get the value for
   * 
   * @return the object described by the path and key, or <code>null</code> if not available
   * 
   * @throws Exception if retreiving the value fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public Object getInfo(String path, String key) throws Exception;

  //----------------------------------------------------------------------------
  /**
   * This method dumps all info from the application.
   * 
   * @throws Exception if dumping info fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public void dumpInfo() throws Exception;

  //----------------------------------------------------------------------------
  /**
   * This method dumps info from the application described by the path.
   * 
   * @param path the path to be dumped, or null to dump from root
   * 
   * @throws Exception if dumping info fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public void dumpInfo(String path) throws Exception;
  //----------------------------------------------------------------------------

}