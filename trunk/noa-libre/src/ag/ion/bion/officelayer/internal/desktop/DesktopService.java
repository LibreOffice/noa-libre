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
 * Last changes made by $Author: markus $, $Date: 2006-12-08 13:07:44 +0100 (Fr, 08 Dez 2006) $
 */
package ag.ion.bion.officelayer.internal.desktop;

import java.awt.Container;
import java.util.Hashtable;

import ag.ion.bion.officelayer.NativeView;
import ag.ion.bion.officelayer.application.connection.IOfficeConnection;

import ag.ion.bion.officelayer.desktop.DesktopException;
import ag.ion.bion.officelayer.desktop.IDesktopService;
import ag.ion.bion.officelayer.desktop.IFrame;
import ag.ion.bion.officelayer.event.IDocumentListener;
import ag.ion.bion.officelayer.event.ITerminateListener;
import ag.ion.bion.officelayer.event.VetoTerminateListener;

import ag.ion.bion.officelayer.internal.application.connection.LocalOfficeConnection;
import ag.ion.bion.officelayer.internal.event.DocumentListenerWrapper;
import ag.ion.bion.officelayer.internal.event.TerminateListenerWrapper;
import ag.ion.noa.NOAException;
import ag.ion.noa.internal.service.ServiceProvider;

import com.sun.star.document.XEventBroadcaster;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XFrame;
import com.sun.star.frame.XFrames;

import com.sun.star.uno.UnoRuntime;

/**
 * Desktop service of OpenOffice.org.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11158 $
 */
public class DesktopService implements IDesktopService {

  private XDesktop          xDesktop          = null;
  private IOfficeConnection officeConnection  = null;
  private XEventBroadcaster eventBroadcaster  = null;
  
  private Hashtable terminateListeners = null;
  private Hashtable documentListeners = null;
  
  private VetoTerminateListener vetoTerminateListener = null;
  private boolean               preventTermination    = false;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new DesktopService.
   * 
   * @param xDesktop OpenOffice.org XDesktop interface to be used
   * @param officeConnection connection to OpenOffice.org to be used
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org XDesktop interface or the connection to OpenOffice.org
   * is not valid
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public DesktopService(XDesktop xDesktop, IOfficeConnection officeConnection) throws IllegalArgumentException {
    this(xDesktop,officeConnection,false);
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new DesktopService.
   * 
   * @param xDesktop OpenOffice.org XDesktop interface to be used
   * @param officeConnection connection to OpenOffice.org to be used
   * @param preventTermination indicates if the termination should be prevented
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org XDesktop interface or the connection to OpenOffice.org
   * is not valid
   * 
   * @author Markus Krüger
   */
  public DesktopService(XDesktop xDesktop, IOfficeConnection officeConnection, boolean preventTermination) throws IllegalArgumentException {
    if(xDesktop == null)
      throw new IllegalArgumentException("The submitted OpenOffice.org XDesktop interface is not valid.");
    this.xDesktop = xDesktop;
    
    if(officeConnection == null)
      throw new IllegalArgumentException("The submitted connection to OpenOffice.org is not valid.");
    this.officeConnection = officeConnection; 
    if(preventTermination)
      activateTerminationPrevention();
  }
  //----------------------------------------------------------------------------
	/**
	 * Terminates the related OpenOffice.org process.
	 * 
	 * @throws NOAException if the termination can not be done
	 * 
	 * @author Andreas Bröker
	 * @date 14.03.2006
	 */
	public void terminate() throws NOAException {
		try {
			xDesktop.terminate();
		}
		catch(Throwable throwable) {
			throw new NOAException(throwable);
		}
	}
  //----------------------------------------------------------------------------
  /**
   * Activates the prevention of the termination.
   * 
   * @author Markus Krüger
   */
  public void activateTerminationPrevention() {
    if(!preventTermination) {
      if(vetoTerminateListener == null)
        vetoTerminateListener = new VetoTerminateListener();
      addTerminateListener(vetoTerminateListener);
      preventTermination = true;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Deactivates the prevention of the termination.
   * 
   * @author Markus Krüger
   */
  public void deactivateTerminationPrevention() {
    if(preventTermination) {
      if(vetoTerminateListener != null) {
        removeTerminateListener(vetoTerminateListener);
      }
      preventTermination = false;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Adds new terminate listener.
   * 
   * @param terminateListener new terminate listener
   * 
   * @author Andreas Bröker
   */
  public void addTerminateListener(ITerminateListener terminateListener) {
    if(terminateListeners == null)
      terminateListeners = new Hashtable();

    TerminateListenerWrapper terminateListenerWrapper = new TerminateListenerWrapper(terminateListener,
        new ServiceProvider(officeConnection));
    xDesktop.addTerminateListener(terminateListenerWrapper);
    terminateListeners.put(terminateListener, terminateListenerWrapper);
  }
  //----------------------------------------------------------------------------
  /**
   * Removes terminate listener.
   * 
   * @param terminateListener terminate listener to be removed
   * 
   * @author Andreas Bröker
   */
  public void removeTerminateListener(ITerminateListener terminateListener) {
    if(terminateListeners == null)
      return;
    
    TerminateListenerWrapper terminateListenerWrapper = (TerminateListenerWrapper)terminateListeners.get(terminateListener);
    if(terminateListenerWrapper != null)
      xDesktop.removeTerminateListener(terminateListenerWrapper);    
  }
  //----------------------------------------------------------------------------
  /**
   * Adds new document listener. Uses GlobalEventBroadcaster to listen to any events on any document.
   * 
   * @param documentListener new document listener
   * 
   * @throws DesktopException if document listener can not be registered
   * 
   * @author Markus Krüger
   */
  public void addDocumentListener(IDocumentListener documentListener) throws DesktopException {      
    try {
      if(documentListeners == null)
        documentListeners = new Hashtable();      
      if(eventBroadcaster == null) {
	      Object globalEventBroadcaster = officeConnection.getXMultiServiceFactory().createInstance( "com.sun.star.frame.GlobalEventBroadcaster" );
	      eventBroadcaster = (XEventBroadcaster) UnoRuntime.queryInterface(XEventBroadcaster.class, globalEventBroadcaster);
      }
      DocumentListenerWrapper documentListenerWrapper = new DocumentListenerWrapper(documentListener,
          new ServiceProvider(officeConnection));
      eventBroadcaster.addEventListener(documentListenerWrapper);
      documentListeners.put(documentListener, documentListenerWrapper);
    } 
    catch (Exception exception) {
      throw new DesktopException(exception);
    }   
  } 
  //----------------------------------------------------------------------------
  /**
   * Removes document listener.
   * 
   * @param documentListener document listener to be removed
   * 
   * @author Markus Krüger
   */
  public void removeDocumentListener(IDocumentListener documentListener) {
    if(documentListener == null)
      return;
    
    DocumentListenerWrapper documentListenerWrapper = (DocumentListenerWrapper)documentListeners.get(documentListener);
    if(documentListenerWrapper != null)
      eventBroadcaster.removeEventListener(documentListenerWrapper);
  }  
  //----------------------------------------------------------------------------
  /**
   * Constructs new OpenOffice.org frame which is integrated into the 
   * submitted AWT container. This method works only on local OpenOffice.org
   * applications.
   * 
   * @param container AWT container to be used
   * 
   * @return new OpenOffice.org frame which is integrated into the 
   * submitted AWT container
   * 
   * @throws DesktopException if the frame can not be constructed
   * 
   * @author Andreas Bröker
   */
  public IFrame constructNewOfficeFrame(Container container) throws DesktopException {
    if(officeConnection instanceof LocalOfficeConnection) {
      XFrame xFrame = ((LocalOfficeConnection)officeConnection).getOfficeFrame(container);
      Frame frame = new Frame(xFrame, officeConnection);
      return frame;
    }
    throw new DesktopException("New frames can only constructed for local OpenOffice.org applications.");
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new OpenOffice.org frame which uses the submitted native view. 
   * This method works only on local OpenOffice.org applications.
   * 
   * @param nativeView native view to be used
   * 
   * @return new OpenOffice.org frame which uses the submitted native view
   * 
   * @throws DesktopException if the frame can not be constructed
   * 
   * @author Markus Krüger
   * @date 08.12.2006
   */
  public IFrame constructNewOfficeFrame(NativeView nativeView) throws DesktopException {
    if(officeConnection instanceof LocalOfficeConnection) {
      XFrame xFrame = ((LocalOfficeConnection)officeConnection).getOfficeFrame(nativeView);
      Frame frame = new Frame(xFrame, officeConnection);
      return frame;
    }
    throw new DesktopException("New frames can only constructed for local OpenOffice.org applications.");
  }
  //----------------------------------------------------------------------------
  /**
   * Disposes all allocated resources.
   * 
   * @author Markus Krüger
   */
  public void dispose() {
    terminateListeners = null;
    documentListeners = null;
    deactivateTerminationPrevention();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the current number of frames, or -1 if an error occured returning it.
   * 
   * @return the current number of frames, or -1 if an error occured returning it
   * 
   * @author Markus Krüger
   */
  public int getFramesCount() {
    if(xDesktop == null)
      return -1;
    XFrames xFrames = ((com.sun.star.frame.XFramesSupplier)UnoRuntime.queryInterface(com.sun.star.frame.XFramesSupplier.class, xDesktop ) ).getFrames();
    if(xFrames == null)
      return -1;
    return xFrames.getCount();
  }
  //----------------------------------------------------------------------------
  
}