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
 * Last changes made by $Author: markus $, $Date: 2007-06-19 15:50:33 +0200 (Di, 19 Jun 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.IPage;
import ag.ion.bion.officelayer.text.IPageCursor;
import ag.ion.bion.officelayer.text.IPageService;
import ag.ion.bion.officelayer.text.ITextDocument;

import ag.ion.bion.officelayer.text.TextException;

import com.sun.star.frame.XController;

import com.sun.star.text.XPageCursor;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextViewCursor;
import com.sun.star.text.XTextViewCursorSupplier;

import com.sun.star.uno.UnoRuntime;

/**
 * Page service of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11494 $
 */
public class PageService implements IPageService {

  private ITextDocument textDocument = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new PageService.
   * 
   * @param textDocument text document to be used
   * 
   * @throws IllegalArgumentException if the submitted text document is not valid
   * 
   * @author Andreas Bröker
   */
  public PageService(ITextDocument textDocument) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    this.textDocument = textDocument;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns number of available pages.
   * 
   * @return number of available pages
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public int getPageCount() {
    IPageCursor pageCursor = textDocument.getViewCursorService().getViewCursor().getPageCursor();
    short currentPage = pageCursor.getPage();
    pageCursor.jumpToLastPage();
    short lastPage = pageCursor.getPage();
    pageCursor.jumpToPage(currentPage);  
    return lastPage;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns page with the submitted index. The first page has the index 0.
   * 
   * @param index index of the page
   *  
   * @return page with the submitted index
   * 
   * @throws TextException if the page is not available
   * 
   * @author Andreas Bröker
   */
  public IPage getPage(int index) throws TextException {
    try {
      XController xController = textDocument.getXTextDocument().getCurrentController();
      XTextViewCursorSupplier xTextViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, xController);
      XTextViewCursor textViewCursor = xTextViewCursorSupplier.getViewCursor();
      XPageCursor pageCursor = (XPageCursor)UnoRuntime.queryInterface(XPageCursor.class, textViewCursor);
      pageCursor.jumpToPage((short)index);
      pageCursor.jumpToStartOfPage();
      XTextRange pageStart = textViewCursor.getStart();   
      PagePosition startPagePosition = new PagePosition(textDocument, pageStart);       
      pageCursor.jumpToEndOfPage();    
      XTextRange pageEnd = textViewCursor.getStart();
      PagePosition endPagePosition = new PagePosition(textDocument, pageEnd);
      return new Page(textDocument, startPagePosition, endPagePosition); 
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------

}