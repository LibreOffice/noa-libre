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
package ag.ion.bion.officelayer.internal.application;

import ag.ion.bion.officelayer.application.IApplicationInfo;
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.IOfficeApplicationConfiguration;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.application.connection.IOfficeConnection;
import ag.ion.bion.officelayer.desktop.IDesktopService;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.event.IEvent;
import ag.ion.bion.officelayer.event.IEventListener;
import ag.ion.bion.officelayer.internal.desktop.DesktopService;
import ag.ion.bion.officelayer.internal.document.DocumentService;
import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;
import ag.ion.noa.internal.service.ServiceProvider;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.frame.XDesktop;
import com.sun.star.uno.UnoRuntime;

/**
 * Abstract OpenOffice.org application.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11684 $
 */
public abstract class AbstractOfficeApplication implements IOfficeApplication {

  private IOfficeConnection               officeConnection               = null;
  private IOfficeApplicationConfiguration officeApplicationConfiguration = null;

  private DocumentService                 documentService                = null;
  private DesktopService                  desktopService                 = null;

  private boolean                         active                         = false;

  //----------------------------------------------------------------------------
  /**
   * Internal class in order to listen on the connection to OpenOffice.org.
   * 
   * @author Andreas Bröker
   */
  private class ConnectionListener implements IEventListener {

    //----------------------------------------------------------------------------
    /**
     * Is called when the broadcaster is about to be disposed. 
     * 
     * @param event source event
     * 
     * @author Andreas Bröker
     */
    public void disposing(IEvent event) {
      active = false;
      documentService = null;
      desktopService = null;
    }
    //----------------------------------------------------------------------------

  }

  //----------------------------------------------------------------------------

  //----------------------------------------------------------------------------
  /**
   * Activates office application. 
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * 
   * @throws OfficeApplicationException if the office application can not be activated
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public void activate(IOfficeProgressMonitor officeProgressMonitor)
      throws OfficeApplicationException {
    if (isConfigured()) {
      openConnection(officeProgressMonitor);
      active = true;

      //added these two variables to always construct new document and desktop service if an activation is done,
      //this way the remote bridge error will not appear
      documentService = null;
      desktopService = null;

      officeConnection.addBridgeEventListener(new ConnectionListener());
    }
    else
      throw new OfficeApplicationException("Configuration for office application is missing.");
  }

  //----------------------------------------------------------------------------
  /**
   * Activates office application. 
   * 
   * @throws OfficeApplicationException if the office application can not be activated
   * 
   * @author Andreas Bröker
   */
  public void activate() throws OfficeApplicationException {
    activate(null);
  }

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
  public void deactivate() throws OfficeApplicationException {
    if (officeConnection != null)
      officeConnection.closeConnection();
    active = false;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the office application is active or not. This information
   * is not an indicator for a running native OpenOffice.org process.
   * 
   * @return whether the office application is active or not
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public boolean isActive() {
    //TODO needs to be changed in later version as the dispose listener can be used.
    if (officeConnection == null || !officeConnection.isConnected())
      active = false;

    return active;
  }

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
  public IDocumentService getDocumentService() throws OfficeApplicationException {
    if (officeConnection == null)
      throw new OfficeApplicationException("Application is not active.");
    if (documentService == null)
      documentService = new DocumentService(officeConnection, getServiceProvider());
    return documentService;
  }

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
  public IDesktopService getDesktopService() throws OfficeApplicationException {
    try {
      if (officeConnection == null)
        throw new OfficeApplicationException("Application is not active.");

      if (desktopService == null) {
        Object service = officeConnection.createService("com.sun.star.frame.Desktop");
        XDesktop desktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, service);
        desktopService = new DesktopService(desktop, officeConnection);
      }
      return desktopService;
    }
    catch (Exception exception) {
      OfficeApplicationException officeApplicationException = new OfficeApplicationException(exception.getMessage());
      officeApplicationException.initCause(exception);
      throw officeApplicationException;
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Disposes all allocated resources.
   * 
   * @author Markus Krüger
   */
  public void dispose() {
    try {
      deactivate();
    }
    catch (OfficeApplicationException exception) {
      exception.printStackTrace();
    }
    if (desktopService != null) {
      desktopService.dispose();
      desktopService = null;
    }
    if (documentService != null) {
      documentService.dispose();
      documentService = null;
    }
  }

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
  public IServiceProvider getServiceProvider() {
    if (!active || officeConnection == null)
      return null;

    return new ServiceProvider(officeConnection);
  }

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
  public IApplicationInfo getApplicationInfo() throws Exception {
    IServiceProvider serviceProvider = getServiceProvider();
    if (serviceProvider != null)
      return new ApplicationInfo(serviceProvider);
    return null;
  }

  //----------------------------------------------------------------------------
  /**
   * Opens connection to OpenOffice.org. Subclasses must implement this method in order
   * to activate the application and they must supply the constructed connection to the 
   * base class.
   * 
   * @param officeProgressMonitor office progress monitor to be used (can be null)
   * 
   * @throws OfficeApplicationException if the connection can not be established
   * 
   * @author Andreas Bröker
   */
  protected abstract void openConnection(IOfficeProgressMonitor officeProgressMonitor)
      throws OfficeApplicationException;

  //----------------------------------------------------------------------------
  /**
   * Sets office application configuration configuration.
   * 
   * @param officeApplicationConfiguration office application configuration configuration
   * 
   * @author Andreas Bröker
   */
  protected void setOfficeApplicationConfiguration(
      IOfficeApplicationConfiguration officeApplicationConfiguration) {
    this.officeApplicationConfiguration = officeApplicationConfiguration;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets office connection. 
   * 
   * @param officeConnection office connection
   * 
   * @author Andreas Bröker
   */
  protected void setOfficeConnection(IOfficeConnection officeConnection) {
    this.officeConnection = officeConnection;
  }
  //----------------------------------------------------------------------------

}