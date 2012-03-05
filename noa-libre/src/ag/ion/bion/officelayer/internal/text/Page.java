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

import ag.ion.bion.officelayer.text.IPage;
import ag.ion.bion.officelayer.text.IPageStyle;
import ag.ion.bion.officelayer.text.ITextDocument;

import ag.ion.bion.officelayer.text.TextException;

/**
 * Page of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class Page implements IPage {
  
  //private ITextDocument textDocument = null;
  
  private PagePosition startPagePosition  = null;
  //private PagePosition endPagePosition    = null;
  
  //----------------------------------------------------------------------------  
  /**
   * Constructs new Page.
   * 
   * @param textDocument text document to be used
   * @param startPagePosition start position of the page
   * @param endPagePosition end position of the page
   * 
   * @throws IllegalArgumentException if the submitted text document, startposition or end position is not valid
   * 
   * @author Andreas Bröker
   */
  public Page(ITextDocument textDocument, PagePosition startPagePosition, PagePosition endPagePosition) throws IllegalArgumentException {
    //if(textDocument == null)
      //throw new IllegalArgumentException("Submitted text document is not valid.");
    //this.textDocument = textDocument;
    
    if(startPagePosition == null)
      throw new IllegalArgumentException("Submitted start position is not valid.");
    this.startPagePosition = startPagePosition;
    
    //if(endPagePosition == null)
      //throw new IllegalArgumentException("Submitted end position is not valid.");
    //this.endPagePosition = endPagePosition;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns style of the page.
   * 
   * @return style of the page
   * 
   * @throws TextException if the page style is not available
   * 
   * @author Andreas Bröker
   */
  public IPageStyle getPageStyle() throws TextException {
    if(startPagePosition.getContentType() == PagePosition.TEXT_TABLE_CELL) {
      return startPagePosition.getTextTableCell().getPageStyle();
    }
    else {
      return startPagePosition.getTextRange().getPageStyle();
    }
  }
  //----------------------------------------------------------------------------
  
}