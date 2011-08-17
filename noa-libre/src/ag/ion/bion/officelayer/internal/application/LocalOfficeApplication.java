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

import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.IOfficeApplicationConfiguration;
import ag.ion.bion.officelayer.application.LocalOfficeApplicationConfiguration;
import ag.ion.bion.officelayer.application.OfficeApplicationException;

import ag.ion.bion.officelayer.internal.application.connection.LocalOfficeConnection;

import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;

import java.awt.Toolkit;
import java.util.Map;

/**
 * Local OpenOffice.org application.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class LocalOfficeApplication extends AbstractOfficeApplication implements IOfficeApplication {

  private LocalOfficeApplicationConfiguration localOfficeApplicationConfiguration = null;
  
  private String home = null;
     
  private boolean isConfigured = false;
  
  static {
    try {
      Toolkit.getDefaultToolkit();
      System.loadLibrary("jawt"); 
    }
    catch(Throwable throwable) {
      //do not consume
    }
  }
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new LocalOfficeApplication.
   * 
   * @param map configuration map to be used (can be null)
   * 
   * @author Andreas Bröker
   */
  public LocalOfficeApplication(Map map) {
    if(map != null) {
      try {
        initConfiguration(map);
      }
      catch(Throwable throwable) {
        //do not consume
      }
    }
  }  
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
  public void setConfiguration(IOfficeApplicationConfiguration officeApplicationConfiguration) throws OfficeApplicationException {
    if(officeApplicationConfiguration instanceof LocalOfficeApplicationConfiguration) {
      localOfficeApplicationConfiguration = (LocalOfficeApplicationConfiguration)officeApplicationConfiguration;
      setOfficeApplicationConfiguration(localOfficeApplicationConfiguration);
      home = localOfficeApplicationConfiguration.getApplicationHomePath();
      isConfigured = true;
    }
    else
      throw new OfficeApplicationException("The submitted configuration is not valid for a local office application.");
  }  
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
  public void setConfiguration(Map configuration) throws OfficeApplicationException {
    initConfiguration(configuration);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the office application is configured or not.
   * 
   * @return true, if the office application is configured
   * 
   * @author Miriam Sutter
   */
  public boolean isConfigured() {
    return isConfigured;
  }
  //----------------------------------------------------------------------------
  /**
   * Opens connection to OpenOffice.org. 
   * 
   * @param officeProgressMonitor office progress monitor to be used (can be null)
   * 
   * @throws OfficeApplicationException if the connection can not be established
   * 
   * @author Andreas Bröker
   */
  protected void openConnection(IOfficeProgressMonitor officeProgressMonitor) throws OfficeApplicationException {
    try {
      LocalOfficeConnection localOfficeConnection = new LocalOfficeConnection();
      localOfficeConnection.setOfficePath(home);
      localOfficeConnection.setHost("localhost");
      localOfficeConnection.setUseBridge(true);
      localOfficeConnection.openConnection(officeProgressMonitor);
      setOfficeConnection(localOfficeConnection);
    }
    catch(Throwable throwable) {
      OfficeApplicationException officeApplicationException = new OfficeApplicationException(throwable.getMessage());
      officeApplicationException.initCause(throwable);
      throw officeApplicationException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Inits the submitted configuration.
   * 
   * @param configuration configuration to be used
   * 
   * @throws OfficeApplicationException if the configuration is not complete
   * 
   * @author Andreas Bröker
   */
  private void initConfiguration(Map configuration) throws OfficeApplicationException {
    if(configuration == null)
      throw new OfficeApplicationException("The submitted configuration is not valid.");
    Object home = configuration.get(IOfficeApplication.APPLICATION_HOME_KEY);
    if(home != null) {
      this.home = home.toString();      
    }
    isConfigured = true;
    //else
      //throw new OfficeApplicationException("The home path to the office application is missing.");
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the application type.
   * 
   * @return application type
   * 
   * @author Miriam Sutter
   */
  public String getApplicationType() {
    return IOfficeApplication.LOCAL_APPLICATION;
  }
  //----------------------------------------------------------------------------

}