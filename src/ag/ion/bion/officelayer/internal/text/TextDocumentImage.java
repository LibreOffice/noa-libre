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
 * Copyright 2003-2007 by IOn AG                                            *
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

import ag.ion.bion.officelayer.text.AbstractTextComponent;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextDocumentImage;
import ag.ion.noa.graphic.GraphicInfo;

import com.sun.star.container.XNamed;
import com.sun.star.text.XTextContent;
import com.sun.star.uno.UnoRuntime;

/**
 * Image of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextDocumentImage extends AbstractTextComponent implements ITextDocumentImage {

  private GraphicInfo  graphicInfo  = null;
  private XTextContent xTextContent = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new TextDocumentImage.
   * 
   * @param textDocument text document to be used
   * @param xTextContent OpenOffice.org XTextContent interface
   * @param graphicInfo the graphic iInfo the image was created with
   *  
   * @throws IllegalArgumentException if the OpenOffice.org interface or the document 
   * or grpahic info is not valid
   * 
   * @author Markus Krüger
   * @date 09.07.2007
   */
  public TextDocumentImage(ITextDocument textDocument, XTextContent xTextContent,
      GraphicInfo graphicInfo) throws IllegalArgumentException {
    super(textDocument);
    if (xTextContent == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XTextContent interface is not valid.");
    if (graphicInfo == null)
      throw new IllegalArgumentException("Submitted graphic information is not valid.");
    this.xTextContent = xTextContent;
    this.graphicInfo = graphicInfo;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextContent interface.
   * 
   * @return OpenOffice.org XTextContent interface
   * 
   * @author Markus Krüger
   * @date 09.07.2007
   */
  public XTextContent getXTextContent() {
    return xTextContent;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns graphic information of the image.
   * 
   * @return graphic information of the image
   * 
   * @author Markus Krüger
   * @date 09.07.2007
   */
  public GraphicInfo getGraphicInfo() {
    return graphicInfo;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the name of the image.
   * 
   * @return the name of the image
   * 
   * @author Markus Krüger
   * @date 02.11.2009
   */
  public String getName() {
    XNamed xNamed = (XNamed) UnoRuntime.queryInterface(XNamed.class, getXTextContent());
    return xNamed.getName();
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the name of the image.
   * 
   * @param name the name of the image
   * 
   * @author Markus Krüger
   * @date 02.11.2009
   */
  public void setName(String name) {
    XNamed xNamed = (XNamed) UnoRuntime.queryInterface(XNamed.class, getXTextContent());
    xNamed.setName(name);
  }
  //----------------------------------------------------------------------------

}