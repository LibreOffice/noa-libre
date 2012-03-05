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
package ag.ion.bion.officelayer.desktop;

import ag.ion.bion.officelayer.IDisposeable;
import ag.ion.bion.officelayer.NativeView;

import ag.ion.bion.officelayer.event.IDocumentListener;
import ag.ion.bion.officelayer.event.ITerminateListener;
import ag.ion.noa.NOAException;

import java.awt.Container;

/**
 * Desktop service of OpenOffice.org.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11158 $
 */
public interface IDesktopService extends IDisposeable {
	
  //----------------------------------------------------------------------------
	/**
	 * Terminates the related OpenOffice.org process.
	 * 
	 * @throws NOAException if the termination can not be done
	 * 
	 * @author Andreas Bröker
	 * @date 14.03.2006
	 */
	public void terminate() throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Adds new terminate listener.
   * 
   * @param terminateListener new terminate listener
   * 
   * @author Andreas Bröker
   */
  public void addTerminateListener(ITerminateListener terminateListener);
  //----------------------------------------------------------------------------
  /**
   * Removes terminate listener.
   * 
   * @param terminateListener terminate listener to be removed
   * 
   * @author Andreas Bröker
   */
  public void removeTerminateListener(ITerminateListener terminateListener);  
  //----------------------------------------------------------------------------
  /**
   * Adds new document listener.
   * 
   * @param documentListener new document listener
   * 
   * @throws DesktopException if document listener can not be registered
   * 
   * @author Markus Krüger
   */
  public void addDocumentListener(IDocumentListener documentListener) throws DesktopException;  
  //----------------------------------------------------------------------------
  /**
   * Removes document listener.
   * 
   * @param documentListener document listener to be removed
   * 
   * @author Markus Krüger
   */
  public void removeDocumentListener(IDocumentListener documentListener);  
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
  public IFrame constructNewOfficeFrame(Container container) throws DesktopException;
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
  public IFrame constructNewOfficeFrame(NativeView nativeView) throws DesktopException;
  //----------------------------------------------------------------------------
  /**
   * Returns the current number of frames, or -1 if an error occured returning it.
   * 
   * @return the current number of frames, or -1 if an error occured returning it
   * 
   * @author Markus Krüger
   */
  public int getFramesCount();
  //----------------------------------------------------------------------------
  /**
   * Activates the prevention of the termination.
   * 
   * @author Markus Krüger
   */
  public void activateTerminationPrevention();
  //----------------------------------------------------------------------------
  /**
   * Deactivates the prevention of the termination.
   * 
   * @author Markus Krüger
   */
  public void deactivateTerminationPrevention();
  //----------------------------------------------------------------------------
  
}