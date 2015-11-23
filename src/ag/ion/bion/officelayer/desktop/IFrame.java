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
 * Last changes made by $Author: markus $, $Date: 2007-07-09 12:36:22 +0200 (Mo, 09 Jul 2007) $
 */
package ag.ion.bion.officelayer.desktop;

import ag.ion.noa.NOAException;

import ag.ion.noa.frame.IDispatch;
import ag.ion.noa.frame.IDispatchDelegate;
import ag.ion.noa.frame.ILayoutManager;

import com.sun.star.frame.XFrame;

/**
 * OpenOffice.org frame.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11506 $
 */
public interface IFrame {
  
  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XFrame interface. This method 
   * is not part of the public API.
   * 
   * @return OpenOffice.org XFrame interface
   * 
   * @author Andreas Bröker
   */
  public XFrame getXFrame();
  //----------------------------------------------------------------------------
  /**
   * Returns layout manager of the frame. Returns null if a layout manager
   * is not available.
   * 
   * @return layout manager of the frame or null if a layout manager
   * is not available
   * 
   * @throws NOAException if the layout manager can not be requested
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public ILayoutManager getLayoutManager() throws NOAException; 
  //----------------------------------------------------------------------------
  /**
   * Disables the command dispatch related to the submitted command URL.
   * In order to activate the dispatches you need to call {@link IFrame#updateDispatches()}.
   * 
   * @param commandURL URL of the command
   * 
   * @see GlobalCommands
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   * @date 07.07.2006
   */
  public void disableDispatch(String commandURL);  
  //----------------------------------------------------------------------------
  /**
   * Adds a dispatch delegate for the submitted command URL.
   * In order to activate the dispatches you need to call {@link IFrame#updateDispatches()}.
   * 
   * @param commandURL command URL to be used
   * @param dispatchDelegate dispatch delegate to be added
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   * @date 07.07.2006
   */
  public void addDispatchDelegate(String commandURL, IDispatchDelegate dispatchDelegate);
  //----------------------------------------------------------------------------
  /**
   * Removes the dispatch delegate for the submitted command URL.
   * In order to activate the dispatches you need to call {@link IFrame#updateDispatches()}.
   * 
   * @param commandURL command URL to be used
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   * @date 07.07.2006
   */
  public void removeDispatchDelegate(String commandURL);
  //----------------------------------------------------------------------------
  /**
   * Returns dispatch for the submitted command URL.
   * 
   * @param commandURL command URL of the dispatch
   * 
   * @return dispatch for the submitted command URL
   * 
   * @throws NOAException if a dispatch for the submitted command URL
   * can not be provided
   * 
   * @author Andreas Bröker
   * @date 14.06.2006
   */
  public IDispatch getDispatch(String commandURL) throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Closes the frame.
   * 
   * @author Andreas Bröker
   */
  public void close();  
  //----------------------------------------------------------------------------
  /**
   * Focuses the frame.
   * 
   * @author Markus Krüger
   */
  public void setFocus();
  //---------------------------------------------------------------------------- 
  /**
   * Updates the current dispatches. 
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public void updateDispatches();
  //----------------------------------------------------------------------------
  /**
   * Aktivates the print preview for the frame.
   * Default columns and rows is set to one.
   * 
   * @throws NOAException if there is an error showing the preview
   * 
   * @author Markus Krüger
   * @date 09.07.2007
   */
  public void showPreview() throws NOAException;
  //---------------------------------------------------------------------------- 
  /**
   * Aktivates the print preview for the frame with the given columns and rows.
   * 
   * @param columns the number of columns to display
   * @param rows the number of rows to display
   * 
   * @throws NOAException if there is an error showing the preview
   * 
   * @author Markus Krüger
   * @date 09.07.2007
   */
  public void showPreview(int columns, int rows) throws NOAException;
  //---------------------------------------------------------------------------- 
  
}