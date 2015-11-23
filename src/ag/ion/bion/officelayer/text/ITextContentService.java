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
 * Last changes made by $Author: markus $, $Date: 2009-09-07 10:46:59 +0200 (Mo, 07 Sep 2009) $
 */
package ag.ion.bion.officelayer.text;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.noa.graphic.GraphicInfo;
import ag.ion.noa.graphic.TextInfo;

import com.sun.star.text.XTextRange;

/**
 * Content service of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11708 $
 */
public interface ITextContentService {

  // ----------------------------------------------------------------------------
  /**
   * Constructs new paragraph.
   * 
   * @return new paragraph
   * 
   * @throws TextException if the paragraph can not be constructed
   * 
   * @author Andreas Bröker
   */
  public IParagraph constructNewParagraph() throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Constructs new image.
   * 
   * @param graphicInfo the graphic information to construct image with
   * 
   * @return new image
   * 
   * @throws TextException if the image can not be constructed
   * 
   * @author Markus Krüger
   * @date 09.07.2007
   */
  public ITextDocumentImage constructNewImage(GraphicInfo graphicInfo) throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Constructs a new TextShape drawn into a text document.
   * 
   * @param textInfo the textual information to construct the text shape with
   * 
   * @return new text shape
   * 
   * @throws TextException if the text shape cannot be constructed
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public ITextDocumentTextShape constructNewTextShape(TextInfo textInfo) throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Constructs a new TextRange.
   * 
   * @param doc the document to construct text range for
   * @param xTextRange the xTextRange to be used
   * 
   * @return new text range
   * 
   * @throws TextException if the text range cannot be constructed
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public ITextRange constructNewTextRange(IDocument doc, XTextRange xTextRange)
      throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Inserts content.
   * 
   * @param textContent text content to be inserted
   * 
   * @throws TextException if the text content can not be inserted
   * 
   * @author Andreas Bröker
   */
  public void insertTextContent(ITextContent textContent) throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Inserts content at submitted position.
   * 
   * @param textRange position to be used
   * @param textContent text content to be inserted
   * 
   * @throws TextException if the text content can not be inserted
   * 
   * @author Andreas Bröker
   */
  public void insertTextContent(ITextRange textRange, ITextContent textContent)
      throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Inserts new text content before other text content.
   * 
   * @param newTextContent text content to be inserted
   * @param textContent available text content
   * 
   * @throws TextException if the text content can not be inserted
   * 
   * @author Andreas Bröker
   */
  public void insertTextContentBefore(ITextContent newTextContent, ITextContent textContent)
      throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Inserts new text content after other text content.
   * 
   * @param newTextContent text content to be inserted
   * @param textContent available text content
   * 
   * @throws TextException if the text content can not be inserted
   * 
   * @author Andreas Bröker
   */
  public void insertTextContentAfter(ITextContent newTextContent, ITextContent textContent)
      throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Removes content.
   * 
   * @param textContent text content to be removed
   * 
   * @throws TextException if the text content can not be removed
   * 
   * @author Miriam Sutter
   */
  public void removeTextContent(ITextContent textContent) throws TextException;

  // ----------------------------------------------------------------------------
  /**
   * Convert linked text images to embedded images.
   * 
   * @throws TextException if conversion fails
   * 
   * @author Markus Krüger
   * @date 07.09.2009
   */
  public void convertLinkedImagesToEmbeded() throws TextException;

  // ----------------------------------------------------------------------------
}