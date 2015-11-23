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
package ag.ion.bion.officelayer.internal.application;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import ag.ion.bion.officelayer.application.IApplicationInfo;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.beans.PropertyValue;
import com.sun.star.container.XNameAccess;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.uno.Any;
import com.sun.star.uno.UnoRuntime;

/**
 * Information provider of an office application.
 * 
 * @author Markus Krüger
 * @version $Revision: 11681 $
 */
public class ApplicationInfo implements IApplicationInfo {

  private IServiceProvider serviceProvider = null;

  //----------------------------------------------------------------------------  
  /**
   * Constructs new ApplicationInfo.
   * 
   * @param serviceProvider the service provieder to be used
   * 
   * @throws Exception if serviceProvider is null
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public ApplicationInfo(IServiceProvider serviceProvider) throws Exception {
    if (serviceProvider == null)
      throw new Exception("A service provider is needed for the ApplicationInfo. This should not happen.");
    this.serviceProvider = serviceProvider;
  }

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
  public String getName() throws Exception {
    Object info = getInfo(NODE_ROOT + NODE_PRODUCT, KEY_PRODUCT_NAME);
    if (info != null && info instanceof String) {
      String sInfo = (String) info;
      if (sInfo.length() > 0) {
        return sInfo;
      }
    }
    return null;
  }

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
  public String getVersion() throws Exception {
    Object info = getInfo(NODE_ROOT + NODE_PRODUCT, KEY_PRODUCT_ABOUT_VERSION);
    if (info != null && info instanceof String) {
      String sInfo = (String) info;
      if (sInfo.length() > 0) {
        return sInfo;
      }
    }
    return null;
  }

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
  public int getMajorVersion() throws Exception {
    Object info = getInfo(NODE_ROOT + NODE_PRODUCT, KEY_PRODUCT_ABOUT_VERSION);
    if (info != null && info instanceof String) {
      String sInfo = (String) info;
      if (sInfo.length() > 0) {
        return Integer.valueOf(sInfo.split("\\.")[0]);
      }
    }
    return -1;
  }

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
  public int getMinorVersion() throws Exception {
    Object info = getInfo(NODE_ROOT + NODE_PRODUCT, KEY_PRODUCT_ABOUT_VERSION);
    if (info != null && info instanceof String) {
      String sInfo = (String) info;
      if (sInfo.length() > 0) {
        return Integer.valueOf(sInfo.split("\\.")[1]);
      }
    }
    return -1;
  }

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
  public int getUpdateVersion() throws Exception {
    Object info = getInfo(NODE_ROOT + NODE_PRODUCT, KEY_PRODUCT_ABOUT_VERSION);
    if (info != null && info instanceof String) {
      String sInfo = (String) info;
      if (sInfo.length() > 0) {
        return Integer.valueOf(sInfo.split("\\.")[2]);
      }
    }
    return -1;
  }

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
  public String getLocale() throws Exception {
    Object info = getInfo(NODE_ROOT + NODE_L10N, KEY_L10N_LOCALE);
    if (info != null && info instanceof String) {
      String sInfo = (String) info;
      if (sInfo.length() > 0) {
        return sInfo;
      }
    }
    return null;
  }

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
  public Date getLicenseAcceptDate() throws Exception {
    Object info = getInfo(NODE_ROOT + NODE_OFFICE, KEY_OFFICE_LICENSE_ACCEPTED);
    if (info != null && info instanceof String) {
      String sInfo = (String) info;
      if (sInfo.length() > 0) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(sInfo);
      }
    }
    return null;
  }

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
  public Boolean getFirstStartWizardCompleted() throws Exception {
    Object info = getInfo(NODE_ROOT + NODE_OFFICE, KEY_OFFICE_FIRST_WIZARD);
    if (info != null && info instanceof Boolean)
      return (Boolean) info;
    return null;
  }

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
  public Object getInfo(String path, String key) throws Exception {
    Object configProviderObject = serviceProvider.createService("com.sun.star.comp.configuration.ConfigurationProvider");
    XMultiServiceFactory xConfigServiceFactory = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class,
        configProviderObject);
    String readConfAccess = "com.sun.star.configuration.ConfigurationAccess";
    PropertyValue[] properties = new PropertyValue[1];
    properties[0] = new PropertyValue();
    properties[0].Name = "nodepath";
    properties[0].Value = path;
    Object configReadAccessObject = xConfigServiceFactory.createInstanceWithArguments(readConfAccess,
        properties);
    XNameAccess xConfigNameAccess = (XNameAccess) UnoRuntime.queryInterface(XNameAccess.class,
        configReadAccessObject);
    return xConfigNameAccess.getByName(key);
  }

  //----------------------------------------------------------------------------
  /**
   * This method dumps all info from the application.
   * 
   * @throws Exception if dumping info fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public void dumpInfo() throws Exception {
    dumpInfo(null);
  }

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
  public void dumpInfo(String path) throws Exception {
    if (path == null || path.length() == 0)
      path = NODE_ROOT;
    Object configProviderObject = serviceProvider.createService("com.sun.star.comp.configuration.ConfigurationProvider");
    XMultiServiceFactory xConfigServiceFactory = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class,
        configProviderObject);
    String readConfAccess = "com.sun.star.configuration.ConfigurationAccess";
    PropertyValue[] properties = new PropertyValue[1];
    properties[0] = new PropertyValue();
    properties[0].Name = "nodepath";
    properties[0].Value = path;
    Object configReadAccessObject = xConfigServiceFactory.createInstanceWithArguments(readConfAccess,
        properties);
    XNameAccess xConfigNameAccess = (XNameAccess) UnoRuntime.queryInterface(XNameAccess.class,
        configReadAccessObject);
    String[] names = xConfigNameAccess.getElementNames();
    System.out.println(path);
    System.out.println("=======================================");
    for (int i = 0; i < names.length; i++) {
      Object element = xConfigNameAccess.getByName(names[i]);
      if (element instanceof String || element instanceof Boolean
          || element instanceof Number
          || element instanceof Character
          || element instanceof CharSequence) {
        System.out.println(names[i] + ": "
            + element);
      }
      else if (element instanceof String[]) {
        System.out.println(names[i] + ": "
            + Arrays.asList((String[]) element).toString());
      }
      else if (!(element instanceof Any)) {
        dumpInfo(path + "/"
            + names[i]);
      }
    }
  }
  //----------------------------------------------------------------------------

}