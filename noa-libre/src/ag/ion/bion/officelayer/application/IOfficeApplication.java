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
 * Last changes made by $Author: markus $, $Date: 2008-11-18 12:45:12 +0100 (Di, 18 Nov 2008) $
 */
package ag.ion.bion.officelayer.application;

import java.util.Map;

import ooo.connector.server.OOoServer;

import ag.ion.bion.officelayer.IDisposeable;
import ag.ion.bion.officelayer.desktop.IDesktopService;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;
import ag.ion.noa.service.IServiceProvider;

/**
 * OpenOffice.org application.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11684 $
 */
public interface IOfficeApplication extends IDisposeable {

  /** Configuration key for the application type. */
  public static final String APPLICATION_TYPE_KEY = "type";
  /** Configuration key for the application host (only for remote applications). */
  public static final String APPLICATION_HOST_KEY = "host";
  /** Configuration key for the application port (only for remote applications). */
  public static final String APPLICATION_PORT_KEY = "port";
  /** Configuration key for the application home path (only for local applications). */
  public static final String APPLICATION_HOME_KEY = "home";
  /** 
   * Configuration key for the application arguments (only for local applications). <br>
   * Value must be of type string array String[]!<br><br>
   * 
   * If not set the default options will be user see {@link OOoServer#getDefaultOOoOptions()}.<br>
   * You can also use the default list of {@link OOoServer#getDefaultOOoOptions()} and change it to<br>
   * your needs, for example:<br><br>
   * List list = OOoServer.getDefaultOOoOptions();<br>
   * list.remove("-nologo");<br>
   * list.add("-nofirststartwizard ");<br>
   * configuration.put(IOfficeApplication.APPLICATION_ARGUMENTS_KEY, list.toArray(new String[list.size()]));<br>
   * 
   */
  public static final String APPLICATION_ARGUMENTS_KEY = "arguments";

  /** Configuration value for a remote application. */
  public static final String REMOTE_APPLICATION   = "remote";
  /** Configuration value for a local application. */
  public static final String LOCAL_APPLICATION    = "local";

  /**Java system property key for NOA natvie library path */
  public static final String NOA_NATIVE_LIB_PATH  = "noa.native.lib.path";

  //----------------------------------------------------------------------------
  /**
   * Sets configuration of the office application.
   * 
   * @param officeApplicationConfiguration configuration of the office application
   * 
   * @throws OfficeApplicationException if the submitted configuration is not valid
   * 
   * @author Andreas Bröker
   * 
   * @deprecated Use setConfiguration(Map configuration) instead.
   */
  public void setConfiguration(IOfficeApplicationConfiguration officeApplicationConfiguration)
      throws OfficeApplicationException;

  //----------------------------------------------------------------------------
  /**
   * Sets configuration of the office application.
   * 
   * @param configuration configuration map to be used
   * 
   * @throws OfficeApplicationException if the configuration is not complete
   * 
   * @author Andreas Bröker
   */
  public void setConfiguration(Map configuration) throws OfficeApplicationException;

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the office application is configured or not.
   * 
   * @return true, if the office application is configured
   * 
   * @author Miriam Sutter
   */
  public boolean isConfigured();

  //----------------------------------------------------------------------------
  /**
   * Activates office application. 
   * 
   * @throws OfficeApplicationException if the office application can not be activated
   * 
   * @author Andreas Bröker
   */
  public void activate() throws OfficeApplicationException;

  //----------------------------------------------------------------------------
  /**
   * Activates office application. 
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * 
   * @throws OfficeApplicationException if the office application can not be activated
   * 
   * @author Andreas Bröker
   */
  public void activate(IOfficeProgressMonitor officeProgressMonitor)
      throws OfficeApplicationException;

  //----------------------------------------------------------------------------
  /**
   * Deactivates the office application. This call will not terminate
   * the OpenOffice.org process - it will only dispose the internal
   * communication bridge to OpenOffice.org.
   * 
   * @throws OfficeApplicationException if the office application can not be deactivated
   * 
   * @author Andreas Bröker
   */
  public void deactivate() throws OfficeApplicationException;

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the office application is active or not. This information
   * is not an indicator for a running native OpenOffice.org process.
   * 
   * @return whether the office application is active or not
   * 
   * @author Andreas Bröker
   */
  public boolean isActive();

  //----------------------------------------------------------------------------
  /**
   * Returns document service. Return null if the office application 
   * is not running.
   * 
   * @return document service or null if the office application is not
   * running
   * 
   * @throws OfficeApplicationException if the document service is not available
   * 
   * @author Andreas Bröker
   */
  public IDocumentService getDocumentService() throws OfficeApplicationException;

  //----------------------------------------------------------------------------
  /**
   * Returns desktop service of the application.
   * 
   * @return desktop service of the application
   * 
   * @throws OfficeApplicationException if the desktop service is not available
   * 
   * @author Andreas Bröker
   */
  public IDesktopService getDesktopService() throws OfficeApplicationException;

  //----------------------------------------------------------------------------
  /**
   * Returns the application type.
   * 
   * @return application type
   * 
   * @author Miriam Sutter
   */
  public String getApplicationType();

  //----------------------------------------------------------------------------
  /**
   * Returns service provider. Returns null if the application is not
   * active.
   * 
   * @return service provider null if the application is not
   * active
   * 
   * @author Andreas Bröker
   * @date 15.08.2006
   */
  public IServiceProvider getServiceProvider();

  //----------------------------------------------------------------------------
  /**
   * Returns application info. Returns null if the application is not
   * active.
   * 
   * @return application info
   * 
   * @throws Exception if return fails
   * 
   * @author Markus Krüger
   * @date 18.11.2008
   */
  public IApplicationInfo getApplicationInfo() throws Exception;
  //----------------------------------------------------------------------------

}