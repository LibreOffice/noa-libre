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
package ag.ion.bion.officelayer.text;

import ag.ion.noa.graphic.TextInfo;

import com.sun.star.text.XText;

/**
 * Text shape of a text document.
 * 
 * @author Jan Reimann
 * @version $Revision: 11508 $
 */
public interface ITextDocumentTextShape extends ITextContent {

  //----------------------------------------------------------------------------
  /**
   * Returns graphic information of the displayed text.
   * 
   * @return graphic information of the displayed text
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public TextInfo getTextInfo();

  //----------------------------------------------------------------------------
  /**
   * Returns the XText object for setting the text after it was inserted into the doc.
   * 
   * @return the XText object
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public XText getXText();

  //----------------------------------------------------------------------------
  /**
   * Sets the XText object
   * 
   * @param xText the XText object
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public void setXText(XText xText);
  //----------------------------------------------------------------------------

}
