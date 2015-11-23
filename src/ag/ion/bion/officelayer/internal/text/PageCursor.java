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

import ag.ion.bion.officelayer.text.IPageCursor;

import com.sun.star.text.XPageCursor;

/**
 * Page cursor of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class PageCursor implements IPageCursor {
  
  private XPageCursor xPageCursor = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new PageCursor.
   * 
   * @param xPageCursor OpenOffice.org XPageCursor interface to be used
   *  
   * @throws IllegalArgumentException if the submitted OpenOffice.org XPageCursor 
   * interface is not valid
   * 
   * @author Markus Krüger
   */
  public PageCursor(XPageCursor xPageCursor) throws IllegalArgumentException {    
    if(xPageCursor == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XPageCursor interface is not valid.");
    this.xPageCursor = xPageCursor;
  }
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the first page.
   * 
   * @author Markus Krüger
   */
  public void jumpToFirstPage() {
    xPageCursor.jumpToFirstPage();
  }  
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the last page.
   * 
   * @author Markus Krüger
   */
  public void jumpToLastPage() {
    xPageCursor.jumpToLastPage();
  }  
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the specified page.
   * 
   * @param pageNumber the page number to jump to
   * 
   * @author Markus Krüger
   */
  public void jumpToPage(short pageNumber) {
    xPageCursor.jumpToPage(pageNumber);
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the number of the page within the document of this cursor.
   * 
   * @return the number of the page within the document of this cursor
   * 
   * @author Markus Krüger
   */
  public short getPage() {
    return xPageCursor.getPage();
  }  
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the next page.
   * 
   * @author Markus Krüger
   */
  public void jumpToNextPage() {
    xPageCursor.jumpToNextPage();
  }  
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the previous page.
   * 
   * @author Markus Krüger
   */
  public void jumpToPreviousPage() {
    xPageCursor.jumpToPreviousPage();
  }  
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the end of the current page.
   * 
   * @author Markus Krüger
   */
  public void jumpToEndOfPage() {
    xPageCursor.jumpToEndOfPage();
  }  
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the start of the current page.
   * 
   * @author Markus Krüger
   */
  public void jumpToStartOfPage() {
    xPageCursor.jumpToStartOfPage();
  }  
  //----------------------------------------------------------------------------
  
}