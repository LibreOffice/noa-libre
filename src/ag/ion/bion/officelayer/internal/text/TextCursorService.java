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

import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextCursorService;
import ag.ion.bion.officelayer.text.ITextDocument;

import com.sun.star.text.XText;

/**
 * Cursor service of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class TextCursorService implements ITextCursorService {
  
  private ITextDocument textDocument = null;
  
  private XText         xText         = null;
    
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextCursorService.
   * 
   * @param textDocument text document to be used
   * @param xText OpenOffice.org XText interface to be used
   * 
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XText 
   * interface is not valid
   * 
   * @author Andreas Bröker
   */
  public TextCursorService(ITextDocument textDocument, XText xText) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("The submitted text document is not valid.");
    this.textDocument = textDocument;
    
    if(xText == null)
      throw new IllegalArgumentException("The submitted OpenOffice.org XText interface is not valid.");
    this.xText = xText;
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns the text cursor.
	 * 
	 * @return text cursor
	 * 
	 * @author Miriam Sutter
   * @author Andreas Bröker
	 */
  public ITextCursor getTextCursor() {
  	return new TextCursor(textDocument, xText.createTextCursor());
	}
  //----------------------------------------------------------------------------
  
}