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
 * Last changes made by $Author: markus $, $Date: 2008-10-01 14:50:27 +0200 (Mi, 01 Okt 2008) $
 */
package ag.ion.bion.officelayer.internal.application.connection;

import ag.ion.bion.officelayer.NativeView;
import ag.ion.bion.officelayer.application.connection.AbstractOfficeConnection;
import ag.ion.bion.officelayer.internal.desktop.Frame;

import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;

import com.sun.star.frame.XFrame;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;

import com.sun.star.awt.XSystemChildFactory;
import com.sun.star.awt.XToolkit;
import com.sun.star.awt.XWindow;
import com.sun.star.awt.XWindowPeer;

import com.sun.star.comp.beans.OfficeWindow;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connection in order to communicate with local OpenOffice.org 
 * application.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11651 $
 */
public class LocalOfficeConnection extends AbstractOfficeConnection {

  private static Logger              LOGGER           = Logger
                                                          .getLogger(LocalOfficeConnection.class
                                                              .getName());

  private LocalOfficeConnectionGhost officeConnection = null;

  private String                     officePath       = null;
  private String[]                     officeArguments       = null;

  private String                     host             = null;
  private String                     port             = null;

  private boolean                    useBridge        = false;

  //----------------------------------------------------------------------------
  /**
   * Sets path to OpenOffice.org installation.
   * 
   * @param officePath path to OpenOffice.org installation
   * 
   * @author Andreas Bröker
   */
  public void setOfficePath(String officePath) {
    this.officePath = officePath;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets arguments for OpenOffice.org.
   * 
   * @param arguments path to OpenOffice.org installation
   * 
   * @author Markus Krüger
   * @date 09.08.2010
   */
  public void setOfficeArguments(String[] arguments) {
    this.officeArguments = arguments;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets host where Office instance is listening.
   * 
   * @param host host where Office instance is listening 
   * 
   * @author Andreas Bröker
   */
  public void setHost(String host) {
    this.host = host;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets port where Office instance is listening
   * 
   * @param port port where Office instance is listening
   * 
   * @author Andreas Bröker
   */
  public void setPort(String port) {
    this.port = port;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets information whether the connection uses a bridge.
   * 
   * @param useBridge information whether the connection uses a bridge
   * 
   * @author Andreas Bröker
   */
  public void setUseBridge(boolean useBridge) {
    this.useBridge = useBridge;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the connection uses a bridge.
   * 
   * @return information whether the connection uses a bridge
   * 
   * @author Andreas Bröker
   */
  public boolean usesBridge() {
    return useBridge;
  }
  //----------------------------------------------------------------------------
  /**
   * Opens connection to OpenOffice.org.
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * 
   * @return information whether the connection is available
   * 
   * @throws Exception if any error occurs
   */
  public boolean openConnection(IOfficeProgressMonitor officeProgressMonitor)
      throws Exception {
    LOGGER.info("Opening local OpenOffice.org connection.");
    /**
     * This is only a hook, on the basis of modified OpenOffice.org
     * Java libraries jurt.jar and officebean.jar, in order to use a specified 
     * path to load native OpenOffice.org libraries.
     * 
     * Due to the BETA status of OpenOffice.org 2.0 it is not necessary to construct
     * a final solution. We will wait for the final release ... 
     */
    try {
      if(officeProgressMonitor != null) {
        officeProgressMonitor
            .beginTask(
                Messages
                    .getString("LocalOfficeConnection_monitor_office_application_message"), 5); //$NON-NLS-1$
        officeProgressMonitor.worked(1);
      }

      if(officePath != null) {
        File file = new File(officePath);
        if(!file.canRead())
          throw new Exception(
              "The home path of the office application does not exist.");
      }

      if(officePath != null)
        System.setProperty("office.home", officePath); //$NON-NLS-1$
      if(officeProgressMonitor != null)
        officeProgressMonitor
            .beginSubTask(Messages
                .getString("LocalOfficeConnection_monitor_loading_libraries_message")); //$NON-NLS-1$
      officeConnection = new LocalOfficeConnectionGhost(officeProgressMonitor);
      if(officeArguments != null && officeArguments.length > 0)
    	  officeConnection.setOfficeArguments(officeArguments); //$NON-NLS-1$
      if(officeProgressMonitor != null)
        officeProgressMonitor.worked(1);
      officeConnection.getComponentContext();

      if(officeProgressMonitor != null) {
        if(officeProgressMonitor.needsDone())
          officeProgressMonitor.done();
      }
      return isConnected();
    }
    catch(Exception exception) {
      if(officeProgressMonitor != null) {
        if(officeProgressMonitor.needsDone())
          officeProgressMonitor.done();
      }
      throw exception;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Opens connection to OpenOffice.org.
   * 
   * @return information whether the connection is available
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public boolean openConnection() throws Exception {
    return openConnection(null);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the connection is active.
   * 
   * @return information whether the connection is active
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public boolean isConnected() {
    if(officeConnection.getCurrentComponentContext() == null) {
      return false;
    }
    try {
      officeConnection.getCurrentComponentContext().getServiceManager();
      return true;
    }
    catch(Exception exception) {
      return false;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Closes connection to OpenOffice.org.
   * 
   * @author Andreas Bröker
   */
  public void closeConnection() {
    try {
      officeConnection.dispose();
    }
    catch(Exception exception) {
      exception.printStackTrace();
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns XComponentContext.
   * 
   * @return XComponentContext
   * 
   * @author Andreas Bröker
   */
  public XComponentContext getXComponentContext() {
    return officeConnection.getComponentContext();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns XMultiServiceFactory.
   * 
   * @return XMultiServiceFactory
   * 
   * @throws Exception if anything fails
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public XMultiServiceFactory getXMultiServiceFactory() throws Exception {
    return (XMultiServiceFactory) UnoRuntime.queryInterface(
        XMultiServiceFactory.class, getXMultiComponentFactory());
  }
  //----------------------------------------------------------------------------
  /**
   * Returns XMultiComponentFactory.
   * 
   * @return XMultiComponentFactory
   * 
   * @throws Exception if the closed connection could not be opened
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public XMultiComponentFactory getXMultiComponentFactory() throws Exception {
    if(!isConnected())
      openConnection();
    return officeConnection.getComponentContext().getServiceManager();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns host of the connection
   * 
   * @return host of the connection
   * 
   * @author Andreas Bröker
   */
  public String getHost() {
    if(host == null)
      return "localhost"; //$NON-NLS-1$
    else
      return host;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns port of the connection.
   * 
   * @return port of the connection.
   * 
   * @author Andreas Bröker
   */
  public String getPort() {
    if(port == null)
      return "local"; //$NON-NLS-1$
    else
      return port;
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new local window for OpenOffice.org.
   * 
   * @param container java AWT container
   * 
   * @return new new local window for OpenOffice.org
   * 
   * @author Andreas Bröker
   */
  public OfficeWindow createLocalOfficeWindow(Container container) {
    return officeConnection.createOfficeWindow(container);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org frame integrated into the submitted Java AWT container. 
   * 
   * @param container java AWT container
   * 
   * @return OpenOffice.org frame integrated into the submitted Java AWT container
   * 
   * @author Andreas Bröker
   */
  public XFrame getOfficeFrame(final Container container) {
    if(officeConnection != null) {      
      try {
        //TODO needs to be changed in later version as the dispose listener can be used.
        if(!isConnected())
          openConnection();  
        
        if(LOGGER.isLoggable(Level.FINEST))
          LOGGER.finest("Creating local office window.");        
        
        final NativeView nativeView = new NativeView(System.getProperty("user.dir")+"/lib");
        container.add(nativeView);
        return getOfficeFrame(nativeView);    
      }
      catch(Exception exception) {
        LOGGER.throwing(this.getClass().getName(), "getOfficeFrame", exception);
        //exception.printStackTrace();
        return null;
      }
    }
    else {
      return null;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org frame integrated into the submitted native view. 
   * 
   * @param nativeView native view
   * 
   * @return OpenOffice.org frame integrated into the submitted Java AWT container
   * 
   * @author Markus Krüger
   * @date 08.12.2006
   */
  public XFrame getOfficeFrame(NativeView nativeView) {
    if(officeConnection != null) {
      try {
        //TODO needs to be changed in later version as the dispose listener can be used.
        if(!isConnected())
          openConnection();

        if(LOGGER.isLoggable(Level.FINEST))
          LOGGER.finest("Creating local office window.");
        XComponentContext xComponentContext = getXComponentContext();
        Object object = null;
        // Create the document frame from UNO window. (<= 6.0 => Task, >= 6.1 => Frame)
        if(LOGGER.isLoggable(Level.FINEST))
          LOGGER.finest("Creating UNO XWindow interface.");

        XToolkit xToolkit = (XToolkit) UnoRuntime.queryInterface(
            XToolkit.class, getXMultiServiceFactory().createInstance(
                "com.sun.star.awt.Toolkit"));

        //      initialise le xChildFactory
        XSystemChildFactory xChildFactory = (XSystemChildFactory) UnoRuntime
            .queryInterface(XSystemChildFactory.class, xToolkit);

        Integer handle = nativeView.getHWND();
        short systeme = (short) nativeView.getNativeWindowSystemType();
        byte[] procID = new byte[0];

        XWindowPeer xWindowPeer = xChildFactory.createSystemChild(
            (Object) handle, procID, systeme);

        XWindow xWindow = (XWindow) UnoRuntime.queryInterface(XWindow.class,
            xWindowPeer);

        object = getXMultiServiceFactory().createInstance(
            "com.sun.star.frame.Task"); //$NON-NLS-1$
        if(object == null)
          object = getXMultiServiceFactory().createInstance(
              "com.sun.star.frame.Frame"); //$NON-NLS-1$
        if(LOGGER.isLoggable(Level.FINEST))
          LOGGER.finest("Creating UNO XFrame interface.");
        XFrame xFrame = (XFrame) UnoRuntime
            .queryInterface(XFrame.class, object);
        xFrame.getContainerWindow();
        xFrame.initialize(xWindow);
        xFrame.setName(xFrame.toString());
        if(LOGGER.isLoggable(Level.FINEST))
          LOGGER.finest("Creating desktop service.");
        Object desktop = getXMultiServiceFactory().createInstance(
            "com.sun.star.frame.Desktop"); //$NON-NLS-1$
        com.sun.star.frame.XFrames xFrames = ((com.sun.star.frame.XFramesSupplier) UnoRuntime
            .queryInterface(com.sun.star.frame.XFramesSupplier.class, desktop))
            .getFrames();
        xFrames.append(xFrame);
        return xFrame;
      }
      catch(Exception exception) {
        LOGGER.throwing(this.getClass().getName(), "getOfficeFrame", exception);
        //exception.printStackTrace();
        return null;
      }
    }
    else {
      return null;
    }
  }
  //----------------------------------------------------------------------------

}