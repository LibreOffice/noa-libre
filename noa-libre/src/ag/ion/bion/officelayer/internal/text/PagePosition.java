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
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextTableCell;

import com.sun.star.table.XCell;

import com.sun.star.text.XTextRange;

import com.sun.star.uno.UnoRuntime;

/**
 * Position on a text page.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class PagePosition {
  
  /** The content of the page position is text. */
  public static final short PLAIN_TEXT      = 99;
  /** The content of the page position is a text table cell. */
  public static final short TEXT_TABLE_CELL = 100;

  private ITextDocument textDocument = null;
  
  private XTextRange    xTextRange    = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new PagePosition.
   * 
   * @param textDocument text document to be used
   * @param xTextRange OpenOffice.org XTextRange interface
   * 
   * @throws IllegalArgumentException if one of the OpenOffice.org interfaces is not valid
   * 
   * @author Andreas Bröker
   */
  public PagePosition(ITextDocument textDocument, XTextRange xTextRange) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    this.textDocument = textDocument;
    
    if(xTextRange == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XTextRange interface is not valid.");
    this.xTextRange = xTextRange;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns content type of the page position.
   * 
   * @return content type of the page position
   * 
   * @author Andreas Bröker
   */
  public short getContentType() {
    XCell xCell = (XCell)UnoRuntime.queryInterface(XCell.class, xTextRange.getText());
    if(xCell == null) {
      return PLAIN_TEXT;
    }
    return TEXT_TABLE_CELL;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell. Returns null if the content of the page
   * position is not a text table cell.
   * 
   * @return text table cell
   * 
   * @author Andreas Bröker
   */
  public ITextTableCell getTextTableCell() {
    if(getContentType() == TEXT_TABLE_CELL) {
      TextTableCell textTableCell = new TextTableCell(textDocument, (XCell)UnoRuntime.queryInterface(XCell.class, xTextRange.getText()));
      return textTableCell;
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text range. Returns null if the content of the page
   * position is not a text range.
   * 
   * @return text range
   * 
   * @author Andreas Bröker
   */
  public ITextRange getTextRange() {
    if(getContentType() == PLAIN_TEXT) {
      TextRange textRange = new TextRange(textDocument, xTextRange);
      return textRange;
    }
    return null;
  }
  //----------------------------------------------------------------------------
  
}