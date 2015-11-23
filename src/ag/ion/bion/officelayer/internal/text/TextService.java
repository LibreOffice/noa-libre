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
 * Last changes made by $Author: markus $, $Date: 2007-11-08 12:25:20 +0100 (Do, 08 Nov 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.IBookmarkService;
import ag.ion.bion.officelayer.text.ITextCursorService;
import ag.ion.bion.officelayer.text.IText;
import ag.ion.bion.officelayer.text.ITextContentService;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextService;

import ag.ion.noa.internal.text.AnnotationService;

import ag.ion.noa.text.IAnnotationService;

import com.sun.star.lang.XMultiServiceFactory;

import com.sun.star.text.XText;

/**
 * Text service implementation of a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11583 $
 */
public class TextService implements ITextService {

  private ITextDocument textDocument = null;
  
  private XText                   xText                   = null;  
  private TextContentService      textContentService      = null;
  private ITextCursorService      cursorService           = null;  
  private IBookmarkService        bookmarkService         = null;
  private IAnnotationService      annotationService       = null;
  private Text                    text                    = null;
  private XMultiServiceFactory    xMultiServiceFactory    = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextService.
   * 
   * @param textDocument text document to be used
   * @param xMultiServiceFactory OpenOffice.org XMultiServiceFactory interface
   * @param xText OpenOffice.org XText interface
   *  
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XText interface is not valid
   * 
   * @author Andreas Bröker
   * @author Sebastian Rösgen
   * @author Markus Krüger
   */
  public TextService(ITextDocument textDocument, XMultiServiceFactory xMultiServiceFactory, XText xText) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("The submitted text document is not valid.");
    
    if(xText == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org interface is not valid.");

    if(xMultiServiceFactory == null)
      throw new IllegalArgumentException("Submitted multi service factory is not valid.");
    
    this.xText = xText;
    this.xMultiServiceFactory = xMultiServiceFactory;
    this.textDocument = textDocument;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text content service. 
   * 
   * @return text content service
   * 
   * @author Andreas Bröker
   */
  public ITextContentService getTextContentService() {
    if(textContentService == null)
      textContentService = new TextContentService(textDocument, xMultiServiceFactory, xText);
    return textContentService;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns cursor service.
   * 
   * @return cursor service
   * 
   * @author Andreas Bröker
   */
  public ITextCursorService getCursorService() {
    if(cursorService == null) 
      cursorService = new TextCursorService(textDocument, xText);
    return cursorService;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns bookmark service.
   * 
   * @return bookmark service
   * 
   * @author Markus Krüger
   */
  public IBookmarkService getBookmarkService() {
    if(bookmarkService == null) 
      bookmarkService = new BookmarkService(textDocument);
    return bookmarkService;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns annotation service.
   * 
   * @return annotation service
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public IAnnotationService getAnnotationService() {
    if(annotationService == null) 
      annotationService = new AnnotationService(textDocument);
    return annotationService;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text.
   * 
   * @return text
   * 
   * @author Andreas Bröker
   */
  public IText getText() {
    if(text == null)
      text = new Text(textDocument, xText);
    return text;
  }
  //----------------------------------------------------------------------------
  
}