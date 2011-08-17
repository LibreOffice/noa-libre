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
 * Last changes made by $Author: jan $, $Date: 2007-07-09 18:22:59 +0200 (Mo, 09 Jul 2007) $
 */
package ag.ion.noa.graphic;

import com.sun.star.text.HoriOrientation;
import com.sun.star.text.TextContentAnchorType;
import com.sun.star.text.VertOrientation;

/**
 * Info class for text shapes in a text document. 
 * It contains information like height and width of the shape
 * and the containing text.
 * 
 * @author Jan Reimann
 * @version $Revision: 11508 $
 */
public class TextInfo {

  private static final double   PIXEL_RATIO         = 26.45;

  private String                name                = null;
  private int                   minimumWidth        = -1;
  private boolean               autoWidth           = true;
  private int                   minimumHeight       = -1;
  private boolean               autoHeight          = true;
  private short                 verticalAlignment   = VertOrientation.TOP;
  private short                 horizontalAlignment = HoriOrientation.CENTER;
  private TextContentAnchorType anchor              = null;
  private int                   backColor           = -1;

  //----------------------------------------------------------------------------
  /**
   * Constructs new TextInfo.
   * 
   * @param name the name for the text shape
   * @param minimumWidth the minimum width
   * @param widthPixel if the width is described as pixels, otherwise it is 
   * assumed that the width is in thenth millimeter
   * @param minimumHeight the minimum height
   * @param heightPixel if the height is described as pixels, otherwise it is 
   * assumed that the height is in thenth millimeter
   * @param verticalAlignment the vertical alignment. 
   * Possible values are described in {@link VertOrientation}
   * @param horizontalAlignment the horizontal alignment. 
   * Possible values are described in {@link HoriOrientation}
   * @param backColor the color of the background for the text shape, or -1 to be transparent
   * @param anchor the anchor type of the image.
   * Possible values are described in {@link TextContentAnchorType}
   *  
   * @throws IllegalArgumentException if the OpenOffice.org interface or the document 
   * or text info is not valid
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public TextInfo(String name, short verticalAlignment, short horizontalAlignment,
      int minimumWidth, boolean widthPixel, boolean autoWidth, int minimumHeight,
      boolean heightPixel, boolean autoHeight, int backColor, TextContentAnchorType anchor) {
    this.name = name;
    if (widthPixel)
      minimumWidth = (int) Math.round(Math.floor(minimumWidth * PIXEL_RATIO));
    this.minimumWidth = minimumWidth;
    this.autoWidth = autoWidth;
    if (heightPixel)
      minimumHeight = (int) Math.round(Math.floor(minimumHeight * PIXEL_RATIO));
    this.minimumHeight = minimumHeight;
    this.autoHeight = autoHeight;
    this.horizontalAlignment = horizontalAlignment;
    this.verticalAlignment = verticalAlignment;
    this.backColor = backColor;
    this.anchor = anchor;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the name of the text shape.
   * 
   * @return the iname of the text shape
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public String getName() {
    return name;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the minimum width of the text shape in tenth millimeter.
   * 
   * @return the minimum width of the text shape in tenth millimeter
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public int getMinimumWidth() {
    return minimumWidth;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the minimum height of the text shape in tenth millimeter.
   * 
   * @return the minimum height of the text shape in tenth millimeter
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public int getMinimumHeight() {
    return minimumHeight;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns if auto width should be used.
   * 
   * @return if auto width should be used
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public boolean isAutoWidth() {
    return autoWidth;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns if auto height should be used.
   * 
   * @return if auto height should be used
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public boolean isAutoHeight() {
    return autoHeight;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the vertical alignment of the text shape.
   * Possible values are described in {@link VertOrientation}
   * 
   * @return the vertical alignment of the text shape
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public short getVerticalAlignment() {
    return verticalAlignment;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the horizontal alignment of the text shape.
   * Possible values are described in {@link HoriOrientation}
   * 
   * @return the horizontal alignment of the text shape
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public short getHorizontalAlignment() {
    return horizontalAlignment;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the background color of the text shape, or -1 if not set (in this case
   * it will be transparent).
   * 
   * @return the background color of the text shape, or -1 if not set (in this case
   * it will be transparent)
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public int getBackColor() {
    return backColor;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the anchor of the text shape, or null.
   * Possible values are described in {@link TextContentAnchorType}
   * 
   * @return the anchor of the text shape, or null
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public TextContentAnchorType getAnchor() {
    return anchor;
  }
  //----------------------------------------------------------------------------

}
