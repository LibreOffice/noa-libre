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
package ag.ion.noa.frame;

import com.sun.star.frame.XLayoutManager;

/**
 * Layout manager for frames.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11734 $
 * @date 2006/02/05
 */
public interface ILayoutManager {

  /** Resource URL prefix. */
  public static final String URL_PREFIX                     = "private:resource";
  /** Resource URL prefix toolbar. */
  public static final String URL_PREFIX_TOOLBAR             = URL_PREFIX + "/toolbar";

  /** Resource URL of the menubar. */
  public static final String URL_MENUBAR                    = URL_PREFIX + "/menubar/menubar";
  /** Resource URL of the statusbar. */
  public static final String URL_STATUSBAR                  = URL_PREFIX + "/statusbar/statusbar";
  /** Resource URL of the toolbar. */
  public static final String URL_TOOLBAR                    = URL_PREFIX_TOOLBAR + "/toolbar";
  /** Resource URL of the toolbar alignmentbar. */
  public static final String URL_TOOLBAR_ALIGNMENTBAR       = URL_PREFIX_TOOLBAR + "/alignmentbar";
  /** Resource URL of the toolbar arrowshapes. */
  public static final String URL_TOOLBAR_ARROWSHAPES        = URL_PREFIX_TOOLBAR + "/arrowshapes";
  /** Resource URL of the toolbar basicshapes. */
  public static final String URL_TOOLBAR_BASICSHAPES        = URL_PREFIX_TOOLBAR + "/basicshapes";
  /** Resource URL of the toolbar calloutshapes. */
  public static final String URL_TOOLBAR_CALLOUTSHAPES      = URL_PREFIX_TOOLBAR + "/calloutshapes";
  /** Resource URL of the toolbar drawbar. */
  public static final String URL_TOOLBAR_DRAWBAR            = URL_PREFIX_TOOLBAR + "/drawbar";
  /** Resource URL of the toolbar drawobjectbar. */
  public static final String URL_TOOLBAR_DRAWOBJECTBAR      = URL_PREFIX_TOOLBAR + "/drawobjectbar";
  /** Resource URL of the toolbar extrusionobjectbar. */
  public static final String URL_TOOLBAR_EXTRUSIONOBJECTBAR = URL_PREFIX_TOOLBAR + "/extrusionobjectbar";
  /** Resource URL of the toolbar fontworkobjectbar. */
  public static final String URL_TOOLBAR_FONTWORKOBJECTBAR  = URL_PREFIX_TOOLBAR + "/fontworkobjectbar";
  /** Resource URL of the toolbar fontworkshapetypes. */
  public static final String URL_TOOLBAR_FONTWORKSHAPETYPES = URL_PREFIX_TOOLBAR + "/fontworkshapetypes";
  /** Resource URL of the toolbar formatobjectbar. */
  public static final String URL_TOOLBAR_FORMATOBJECTBAR    = URL_PREFIX_TOOLBAR + "/formatobjectbar";
  /** Resource URL of the toolbar formcontrols. */
  public static final String URL_TOOLBAR_FORMCONTROLS       = URL_PREFIX_TOOLBAR + "/formcontrols";
  /** Resource URL of the toolbar formdesign. */
  public static final String URL_TOOLBAR_FORMDESIGN         = URL_PREFIX_TOOLBAR + "/formdesign";
  /** Resource URL of the toolbar formsfilterbar. */
  public static final String URL_TOOLBAR_FORMFILTERBAR      = URL_PREFIX_TOOLBAR + "/formsfilterbar";
  /** Resource URL of the toolbar formsnavigationbar. */
  public static final String URL_TOOLBAR_FORMSNAVIGATIONBAR = URL_PREFIX_TOOLBAR + "/formsnavigationbar";
  /** Resource URL of the toolbar formsobjectbar. */
  public static final String URL_TOOLBAR_FORMSOBJECTBAR     = URL_PREFIX_TOOLBAR + "/formsobjectbar";
  /** Resource URL of the toolbar formtextobjectbar. */
  public static final String URL_TOOLBAR_FORMSTEXTOBJECTBAR = URL_PREFIX_TOOLBAR + "/formtextobjectbar";
  /** Resource URL of the toolbar fullscreenbar. */
  public static final String URL_TOOLBAR_FULLSCREENBAR      = URL_PREFIX_TOOLBAR + "/fullscreenbar";
  /** Resource URL of the toolbar graphicobjectbar. */
  public static final String URL_TOOLBAR_GRAPHICOBJECTBAR   = URL_PREFIX_TOOLBAR + "/graphicobjectbar";
  /** Resource URL of the toolbar insertbar. */
  public static final String URL_TOOLBAR_INSERTBAR          = URL_PREFIX_TOOLBAR + "/insertbar";
  /** Resource URL of the toolbar insertcellsbar. */
  public static final String URL_TOOLBAR_INSERTCELLSBAR     = URL_PREFIX_TOOLBAR + "/insertcellsbar";
  /** Resource URL of the toolbar insertobjectbar. */
  public static final String URL_TOOLBAR_INSERTOBJECTSBAR   = URL_PREFIX_TOOLBAR + "/insertobjectbar";
  /** Resource URL of the toolbar mediaobjectbar. */
  public static final String URL_TOOLBAR_MEDIAOBJECTSBAR    = URL_PREFIX_TOOLBAR + "/mediaobjectbar";
  /** Resource URL of the toolbar moreformcontrols. */
  public static final String URL_TOOLBAR_MOREFORMCONTROLS   = URL_PREFIX_TOOLBAR + "/moreformcontrols";
  /** Resource URL of the toolbar previewbar. */
  public static final String URL_TOOLBAR_PREVIEWBAR         = URL_PREFIX_TOOLBAR + "/previewbar";
  /** Resource URL of the toolbar standardbar. */
  public static final String URL_TOOLBAR_STANDARDBAR        = URL_PREFIX_TOOLBAR + "/standardbar";
  /** Resource URL of the toolbar starshapes. */
  public static final String URL_TOOLBAR_STARSHAPES         = URL_PREFIX_TOOLBAR + "/starshapes";
  /** Resource URL of the toolbar symbolshapes. */
  public static final String URL_TOOLBAR_SYMBOLSHAPES       = URL_PREFIX_TOOLBAR + "/symbolshapes";
  /** Resource URL of the toolbar textobjectbar. */
  public static final String URL_TOOLBAR_TEXTOBJECTBAR      = URL_PREFIX_TOOLBAR + "/textobjectbar";
  /** Resource URL of the toolbar tableobjectbar. */
  public static final String URL_TOOLBAR_TABLEOBJECTBAR     = URL_PREFIX_TOOLBAR + "/tableobjectbar";
  /** Resource URL of the toolbar viewerbar. */
  public static final String URL_TOOLBAR_VIEWERBAR          = URL_PREFIX_TOOLBAR + "/viewerbar";

  /** All resource URLs of all bars. */
  public static String[]     ALL_BARS_URLS                  = new String[] { URL_MENUBAR,
      URL_STATUSBAR, URL_TOOLBAR, URL_TOOLBAR_ALIGNMENTBAR, URL_TOOLBAR_ARROWSHAPES,
      URL_TOOLBAR_BASICSHAPES, URL_TOOLBAR_CALLOUTSHAPES, URL_TOOLBAR_DRAWBAR,
      URL_TOOLBAR_DRAWOBJECTBAR, URL_TOOLBAR_EXTRUSIONOBJECTBAR, URL_TOOLBAR_FONTWORKOBJECTBAR,
      URL_TOOLBAR_FONTWORKSHAPETYPES, URL_TOOLBAR_FORMATOBJECTBAR, URL_TOOLBAR_FORMCONTROLS,
      URL_TOOLBAR_FORMDESIGN, URL_TOOLBAR_FORMFILTERBAR, URL_TOOLBAR_FORMSNAVIGATIONBAR,
      URL_TOOLBAR_FORMSOBJECTBAR, URL_TOOLBAR_FORMSTEXTOBJECTBAR, URL_TOOLBAR_FULLSCREENBAR,
      URL_TOOLBAR_GRAPHICOBJECTBAR, URL_TOOLBAR_INSERTBAR, URL_TOOLBAR_INSERTCELLSBAR,
      URL_TOOLBAR_INSERTOBJECTSBAR, URL_TOOLBAR_MEDIAOBJECTSBAR, URL_TOOLBAR_MOREFORMCONTROLS,
      URL_TOOLBAR_PREVIEWBAR, URL_TOOLBAR_STANDARDBAR, URL_TOOLBAR_STARSHAPES,
      URL_TOOLBAR_SYMBOLSHAPES, URL_TOOLBAR_TEXTOBJECTBAR, URL_TOOLBAR_VIEWERBAR };

  //----------------------------------------------------------------------------
  /**
   * Returns the office XLayoutManager interface.
   * 
   * @return office XLayoutManager interface
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public XLayoutManager getXLayoutManager();

  //----------------------------------------------------------------------------
  /**
   * Hide UI element with the submitted resource URL.
   * Automatically perist changes.
   * 
   * @param resourceURL URL of the UI resource to be hidden
   * 
   * @return information whether the UI resource is hidden after method call
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public boolean hideElement(String resourceURL);

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
  public boolean hideElement(String resourceURL, boolean persistent);

  //----------------------------------------------------------------------------
  /**
   * Hides all bars.
   * Automatically perist changes.
   * 
   * @author Markus Krüger
   * @date 08.12.2006
   */
  public void hideAll();

  //----------------------------------------------------------------------------
  /**
   * Hides all bars.
   * 
   * @param if changes should be persistent
   * 
   * @author Markus Krüger
   * @date 06.05.2010
   */
  public void hideAll(boolean persistent);

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
  public boolean showElement(String resourceURL);

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
  public boolean showElement(String resourceURL, boolean persistent);

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
  public void setVisible(boolean visible);
  //----------------------------------------------------------------------------	

}