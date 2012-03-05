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
 * Last changes made by $Author: markus $, $Date: 2010-05-06 14:18:12 +0200 (Do, 06 Mai 2010) $
 */
package ag.ion.bion.officelayer.text;

/**
 * Visible cursor of a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11732 $
 */
public interface IViewCursor {

  //----------------------------------------------------------------------------
  /**
   * Returns start text range of the cursor.
   * 
   * @return start text range of the cursor
   * 
   * @author Andreas Bröker
   */
  public ITextRange getStartTextRange();

  //----------------------------------------------------------------------------
  /**
   * Returns new text cursor from the start position of the view cursor.
   * 
   * @return new text cursor from the start position of the view cursor
   * 
   * @author Andreas Bröker
   */
  public ITextCursor getTextCursorFromStart();

  //----------------------------------------------------------------------------
  /**
   * Returns new text cursor from the end position of the view cursor.
   * 
   * @return new text cursor from the end position of the view cursor
   * 
   * @author Andreas Bröker
   */
  public ITextCursor getTextCursorFromEnd();

  //----------------------------------------------------------------------------
  /**
   * Moves to the given text range.
   * 
   * @param textRange the text range to go to
   * @param select if to extend the selection
   * 
   * @author Markus Krüger
   */
  public void goToRange(ITextRange textRange, boolean select);

  //----------------------------------------------------------------------------
  /**
   * Returns the page cursor for the view cursor, can be null if no page cursor is available.
   * 
   * @return the page cursor for the view cursor, can be null
   * 
   * @author Markus Krüger
   */
  public IPageCursor getPageCursor();

  //----------------------------------------------------------------------------
  /**
   * Returns the line cursor for the view cursor, can be null if no line cursor is available.
   * 
   * @return the line cursor for the view cursor, can be null
   * 
   * @author Markus Krüger
   */
  public ILineCursor getLineCursor();
  //----------------------------------------------------------------------------

}