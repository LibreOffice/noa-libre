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
import ag.ion.bion.officelayer.application.RemoteOfficeApplicationConfiguration;
import ag.ion.bion.officelayer.application.OfficeApplicationException;

import ag.ion.bion.officelayer.internal.application.connection.RemoteOfficeConnection;

import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;

import java.util.Map;

/**
 * Remote OpenOffice.org application.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class RemoteOfficeApplication extends AbstractOfficeApplication implements IOfficeApplication {
 
  private RemoteOfficeApplicationConfiguration remoteOfficeApplicationConfiguration = null;
  
  private String host = null;
  private String port = null;
  
  private boolean isConfigured = false;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new RemoteOfficeApplication.
   * 
   * @param map configuration map to be used (can be null)
   * 
   * @author Andreas Bröker
   */
  public RemoteOfficeApplication(Map map) {
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
   if(officeApplicationConfiguration instanceof RemoteOfficeApplicationConfiguration) {
     remoteOfficeApplicationConfiguration = (RemoteOfficeApplicationConfiguration)officeApplicationConfiguration;
     setOfficeApplicationConfiguration(remoteOfficeApplicationConfiguration);
     host = remoteOfficeApplicationConfiguration.getHost();
     port = remoteOfficeApplicationConfiguration.getPort();
     isConfigured = true;
   }
   else
     throw new OfficeApplicationException("The submitted configuration is not valid for a remote office application.");
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
     RemoteOfficeConnection remoteOfficeConnection = new RemoteOfficeConnection();
     remoteOfficeConnection.setPort(port);
     remoteOfficeConnection.setHost(host);
     remoteOfficeConnection.openConnection(officeProgressMonitor);
     setOfficeConnection(remoteOfficeConnection);
   }
   catch(Exception exception) {
     OfficeApplicationException officeApplicationException = new OfficeApplicationException(exception.getMessage());
     officeApplicationException.initCause(exception);
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
    Object host = configuration.get(IOfficeApplication.APPLICATION_HOST_KEY);
    Object port = configuration.get(IOfficeApplication.APPLICATION_PORT_KEY);
    if(host != null)
      this.host = host.toString();      
    else
      throw new OfficeApplicationException("The host of the office application is missing.");
    
    if(port != null)
      this.port = port.toString();
    else
      throw new OfficeApplicationException("The port of the office application is missing.");
    
    isConfigured = true;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the application type.
   * 
   * @return application type
   * 
   * @author Miriam Sutter
   * @author Andreas Bröker
   */
  public String getApplicationType() {
    return IOfficeApplication.REMOTE_APPLICATION;
  }
  //----------------------------------------------------------------------------

}