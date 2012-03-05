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
 * Last changes made by $Author: markus $, $Date: 2007-01-25 12:09:04 +0100 (Do, 25 Jan 2007) $
 */
package ag.ion.bion.officelayer.text;

import ag.ion.bion.officelayer.document.IDocument;

import com.sun.star.text.XTextRange;

/**
 * Range of text in a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11361 $
 */
public interface ITextRange {
  
  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextRange interface. 
   * 
   * This method is not part of the public API. It should
   * never used from outside.
   * 
   * @return OpenOffice.org XTextRange interface
   * 
   * @author Andreas Bröker
   */
  public XTextRange getXTextRange();
  //----------------------------------------------------------------------------
  /**
   * Sets text of the text range.
   * 
   * @param text text to be used
   * 
   * @author Andreas Bröker
   */
  public void setText(String text);
  //----------------------------------------------------------------------------
  /**
   * Returns related page style of the text range.
   * 
   * @return page style of the text range
   * 
   * @throws TextException 
   * 
   * @author Andreas Bröker
   */
  public IPageStyle getPageStyle() throws TextException;  
  //----------------------------------------------------------------------------
  /**
   * Returns cell of the texttable if the text range is part of cell. 
   * 
   * @return cell of the texttable if the text range is part of cell or null if
   * the text range is not part of a cell
   * 
   * @author Andreas Bröker
   */
  public ITextTableCell getCell();  
  //----------------------------------------------------------------------------
  /**
   * Compares this text range with the given text range. Returns 1 if this range
   * starts before the given text range, 0 if the text ranges start at the same position
   * and -1 if this text range starts behin the given text range.
   * 
   * @param textRangeToCompare the text range to compare
   * 
   * @return 1 if this range starts before the given text range, 0 if the text ranges 
   *         start at the same position and -1 if this text range starts behin the given text range
   * 
   * @throws TextException if the text ranges to compare are not within the same text. 
   * 
   * @author Markus Krüger
   */
  public short compareRange(ITextRange textRangeToCompare) throws TextException;  
  //----------------------------------------------------------------------------
  /**
   * Sets the document of the text range. This makes sense if the text range
   * was generated as the document was unknown.
   * 
   * @param document document to be used
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public void setDocument(IDocument document);
  //----------------------------------------------------------------------------


}