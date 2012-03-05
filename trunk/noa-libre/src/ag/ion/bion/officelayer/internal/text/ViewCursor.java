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
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.ILineCursor;
import ag.ion.bion.officelayer.text.IPageCursor;
import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.IViewCursor;

import com.sun.star.text.XPageCursor;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextViewCursor;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.view.XLineCursor;

/**
 * Visible cursor implementation of a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11732 $
 */
public class ViewCursor implements IViewCursor {

  private ITextDocument   textDocument    = null;

  private IPageCursor     pageCursor      = null;

  private ILineCursor     lineCursor      = null;

  private XTextViewCursor xTextViewCursor = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new ViewCursor.
   * 
   * @param textDocument text document to be used
   * @param xTextViewCursor OpenOffice.org XTextViewCursor interface to be used
   *  
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XTextViewCursor 
   * interface is not valid
   * 
   * @author Andreas Bröker
   */
  public ViewCursor(ITextDocument textDocument, XTextViewCursor xTextViewCursor)
      throws IllegalArgumentException {
    if (textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    this.textDocument = textDocument;

    if (xTextViewCursor == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XTextViewCursor interface is not valid.");
    this.xTextViewCursor = xTextViewCursor;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns start text range of the cursor.
   * 
   * @return start text range of the cursor
   * 
   * @author Andreas Bröker
   */
  public ITextRange getStartTextRange() {
    return new TextRange(textDocument, xTextViewCursor.getStart());
  }

  //----------------------------------------------------------------------------
  /**
   * Returns new text cursor from the start position of the view cursor.
   * 
   * @return new text cursor from the start position of the view cursor
   * 
   * @author Andreas Bröker
   */
  public ITextCursor getTextCursorFromStart() {
    XTextCursor xTextCursor = xTextViewCursor.getText().createTextCursorByRange(xTextViewCursor.getStart());
    return new TextCursor(textDocument, xTextCursor);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns new text cursor from the end position of the view cursor.
   * 
   * @return new text cursor from the end position of the view cursor
   * 
   * @author Andreas Bröker
   */
  public ITextCursor getTextCursorFromEnd() {
    XTextCursor xTextCursor = xTextViewCursor.getText().createTextCursorByRange(xTextViewCursor.getEnd());
    return new TextCursor(textDocument, xTextCursor);
  }

  //----------------------------------------------------------------------------
  /**
   * Moves to the given text range.
   * 
   * @param textRange the text range to go to
   * @param select if to extend the selection
   * 
   * @author Markus Krüger
   */
  public void goToRange(ITextRange textRange, boolean select) {
    XTextRange xTextRange = textRange.getXTextRange();
    xTextViewCursor.gotoRange(xTextRange, select);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the page cursor for the view cursor, can be null if no page cursor is available.
   * 
   * @return the page cursor for the view cursor, can be null
   * 
   * @author Markus Krüger
   */
  public IPageCursor getPageCursor() {
    if (pageCursor == null) {
      XPageCursor xPageCursor = (XPageCursor) UnoRuntime.queryInterface(XPageCursor.class,
          xTextViewCursor);
      if (xPageCursor != null) {
        pageCursor = new PageCursor(xPageCursor);
      }
    }
    return pageCursor;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the line cursor for the view cursor, can be null if no line cursor is available.
   * 
   * @return the line cursor for the view cursor, can be null
   * 
   * @author Markus Krüger
   */
  public ILineCursor getLineCursor() {
    if (lineCursor == null) {
      XLineCursor xLineCursor = (XLineCursor) UnoRuntime.queryInterface(XLineCursor.class,
          xTextViewCursor);
      if (xLineCursor != null) {
        lineCursor = new LineCursor(xLineCursor);
      }
    }
    return lineCursor;
  }
  //----------------------------------------------------------------------------

}