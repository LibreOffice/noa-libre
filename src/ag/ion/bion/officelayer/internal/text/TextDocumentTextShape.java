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
 * Last changes made by $Author: jan $, $Date: 2007-07-09 18:22:59 +0200 (Mo, 09 Jul 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.AbstractTextComponent;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextDocumentTextShape;
import ag.ion.noa.graphic.TextInfo;

import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;

/**
 * Text shape of a text document.
 * 
 * @author Jan Reimann
 * @version $Revision: 11508 $
 */
public class TextDocumentTextShape extends AbstractTextComponent implements ITextDocumentTextShape {

  private TextInfo     textInfo     = null;
  private XTextContent xTextContent = null;
  private XText        xText;

  //----------------------------------------------------------------------------
  /**
   * Constructs new TextDocumentTextShape.
   * 
   * @param textDocument text document to be used
   * @param xTextContent OpenOffice.org XTextContent interface
   * @param textInfo the text info the text shape was created with
   *  
   * @throws IllegalArgumentException if the OpenOffice.org interface or the document 
   * or text info is not valid
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  public TextDocumentTextShape(ITextDocument textDocument, XTextContent xTextContent,
      TextInfo textInfo) throws IllegalArgumentException {
    super(textDocument);
    if (xTextContent == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XTextContent interface is not valid.");
    if (textInfo == null)
      throw new IllegalArgumentException("Submitted graphic information is not valid.");
    this.xTextContent = xTextContent;
    this.textInfo = textInfo;
  }

  //----------------------------------------------------------------------------
  /**
   * {@inheritDoc}
   */
  public TextInfo getTextInfo() {
    return textInfo;
  }

  //----------------------------------------------------------------------------
  /**
   * {@inheritDoc}
   */
  public XTextContent getXTextContent() {
    return xTextContent;
  }

  //----------------------------------------------------------------------------
  /**
   * {@inheritDoc}
   */
  public XText getXText() {
    return xText;
  }

  //----------------------------------------------------------------------------
  /**
   * {@inheritDoc}
   */
  public void setXText(XText xText) {
    this.xText = xText;
  }
  //----------------------------------------------------------------------------

}
