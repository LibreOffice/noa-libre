/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                     						*
 * ------------------------------------------------------------------------ *
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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/

/*
 * Last changes made by $Author: markus $, $Date: 2010-05-06 15:17:34 +0200 (Do, 06 Mai 2010) $
 */
package ag.ion.noa.internal.frame;

import ag.ion.bion.officelayer.util.Assert;
import ag.ion.noa.frame.ILayoutManager;

import com.sun.star.beans.XPropertySet;
import com.sun.star.frame.XLayoutManager;
import com.sun.star.ui.XUIElement;
import com.sun.star.uno.UnoRuntime;

/**
 * Layout manager for frames.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11734 $
 * @date 2006/02/05
 */
public class LayoutManager implements ILayoutManager {

  private XLayoutManager xLayoutManager = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new LayoutManager.
   * 
   * @param xLayoutManager office XLayoutManager interface
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public LayoutManager(XLayoutManager xLayoutManager) {
    Assert.isNotNull(xLayoutManager, XLayoutManager.class, this);
    this.xLayoutManager = xLayoutManager;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the office XLayoutManager interface.
   * 
   * @return office XLayoutManager interface
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public XLayoutManager getXLayoutManager() {
    return xLayoutManager;
  }

  //----------------------------------------------------------------------------
  /**
   * Shows UI element with the submitted resource URL.
   * Automatically perist changes.
   * 
   * @param resourceURL URL of the UI resource to be shown
   * 
   * @return information whether the UI resource is visible after method call
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public boolean hideElement(String resourceURL) {
    return hideElement(resourceURL, true);
  }

  //----------------------------------------------------------------------------
  /**
   * Hide UI element with the submitted resource URL.
   * 
   * @param resourceURL URL of the UI resource to be hidden
   * @param if changes should be persistent
   * 
   * @return information whether the UI resource is hidden after method call
   * 
   * @author Markus Krüger
   * @date 06.05.2010
   */
  public boolean hideElement(String resourceURL, boolean persistent) {
    if (resourceURL != null) {
      try {
        XUIElement element = xLayoutManager.getElement(resourceURL);
        if (element != null) {
          XPropertySet xps = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, element);
          xps.setPropertyValue("Persistent", new Boolean(persistent));
          return xLayoutManager.hideElement(resourceURL);
        }
      }
      catch (Exception e) {
        //ignore and return false
      }
    }
    return false;

  }

  //----------------------------------------------------------------------------
  /**
   * Hides all bars.
   * Automatically perist changes.
   * 
   * @author Markus Krüger
   * @date 08.12.2006
   */
  public void hideAll() {
    hideAll(true);
  }

  //----------------------------------------------------------------------------
  /**
   * Hides all bars.
   * 
   * @param if changes should be persistent
   * 
   * @author Markus Krüger
   * @date 06.05.2010
   */
  public void hideAll(boolean persistent) {
    for (int i = 0; i < ALL_BARS_URLS.length; i++) {
      hideElement(ALL_BARS_URLS[i], persistent);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Shows UI element with the submitted resource URL.
   * Automatically perist changes.
   * 
   * @param resourceURL URL of the UI resource to be shown
   * 
   * @return information whether the UI resource is visible after method call
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public boolean showElement(String resourceURL) {
    return showElement(resourceURL, true);
  }

  //----------------------------------------------------------------------------
  /**
   * Shows UI element with the submitted resource URL.
   * 
   * @param resourceURL URL of the UI resource to be shown
   * @param if changes should be persistent
   * 
   * @return information whether the UI resource is visible after method call
   * 
   * @author Markus Krüger
   * @date 06.05.2010
   */
  public boolean showElement(String resourceURL, boolean persistent) {
    if (resourceURL != null) {
      try {
        XUIElement element = xLayoutManager.getElement(resourceURL);
        if (element == null) {
          xLayoutManager.createElement(resourceURL);
        }
        element = xLayoutManager.getElement(resourceURL);
        if (element != null) {
          XPropertySet xps = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, element);
          xps.setPropertyValue("Persistent", new Boolean(persistent));
          return xLayoutManager.showElement(resourceURL);
        }
      }
      catch (Exception e) {
        //ignore and return false
      }
    }
    return false;
  }

  //----------------------------------------------------------------------------
  /**
   * Switches the visibility of all UI elements managed by the 
   * layout manager.
   * 
   * @param visible new visibility state
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public void setVisible(boolean visible) {
    xLayoutManager.setVisible(visible);
  }
  //----------------------------------------------------------------------------

}