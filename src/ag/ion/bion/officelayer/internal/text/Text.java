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
 * Last changes made by $Author: markus $, $Date: 2007-08-03 14:13:25 +0200 (Fr, 03 Aug 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.IText;
import ag.ion.bion.officelayer.text.ITextContentEnumeration;
import ag.ion.bion.officelayer.text.ITextCursorService;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.TextException;

import com.sun.star.text.XText;

/**
 * Text of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11550 $
 */
public class Text implements IText {

  private ITextDocument textDocument = null;
  
  private XText xText = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new Text.
   * 
   * @param textDocument text document to be used
   * @param xText OpenOffice.org XText interface
   *  
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XText 
   * interface is not valid
   * 
   * @author Andreas Bröker
   * @author Sebastian Rösgen
   */
  public Text(ITextDocument textDocument, XText xText) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("The submitted text document is not valid.");
    this.textDocument = textDocument;
    
    if(xText == null)
      throw new IllegalArgumentException("The submitted OpenOffice.org XText interface is not valid.");
    this.xText = xText;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns XText interface.
   * 
   * @return XText interface
   * 
   * @author Andreas Bröker
   * @date 19.09.2006
   */
  public XText getXText() {
    return xText;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text content.
   * 
   * @return text content
   * 
   * @author Andreas Bröker
   */
  public String getText() {
    return xText.getString();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns content enumeration.
   * 
   * @return content enumeration
   * 
   * @throws TextException if the content enumeration is not available
   * 
   * @author Andreas Bröker
   * @author Sebastian Rösgen
   * @author Markus Krüger
   */
  public ITextContentEnumeration getTextContentEnumeration() throws TextException {
    try {
      return new TextContentEnumeration(textDocument, xText);
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text cursor service.
   * 
   * @return text cursor service
   * 
   * @throws TextException if the content enumeration is not available
   * 
   * @author Miriam Sutter
   */
  public ITextCursorService getTextCursorService() throws TextException {
  	try {
      return new TextCursorService(textDocument, xText);
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Sets text content.
   * 
   * @param text text to be set
   * 
   * @author Markus Krüger
   */
  public void setText(String text) {
    xText.setString(text);
  }
  //----------------------------------------------------------------------------
  
}