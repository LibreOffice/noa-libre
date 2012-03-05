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
 * Last changes made by $Author: markus $, $Date: 2010-06-21 13:13:01 +0200 (Mo, 21 Jun 2010) $
 */
package ag.ion.bion.officelayer.text;

import ag.ion.bion.officelayer.clone.ICloneServiceProvider;

import com.sun.star.text.XTextContent;

/**
 * Paragraph of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11738 $
 */
public interface IParagraph extends ITextContent, ICloneServiceProvider {

  //----------------------------------------------------------------------------
  /**
   * Sets the OpenOffice.org XTextContent interface after paragraph was inserted.
   * 
   * @param xTextContent OpenOffice.org XTextContent interface
   * 
   * @author Markus Krüger
   * @date 21.06.2010
   */
  public void setXTextContent(XTextContent xTextContent);

  //----------------------------------------------------------------------------
  /**
   * Returns properties of the paragraph.
   * 
   * @return properties of the paragraph
   * 
   * @author Andreas Bröker
   */
  public IParagraphProperties getParagraphProperties();

  //----------------------------------------------------------------------------
  /**
   * Returns text range of the text table.
   * 
   * @return text range of the text table
   * 
   * @author Markus Krüger
   * @date 06.08.2007
   */
  public ITextRange getTextRange();

  //----------------------------------------------------------------------------
  /**
   * Returns character properties belonging to the paragraph
   * 
   * @return characterproperties of the paragraph
   * 
   * @author Sebastian Rösgen
   */
  public ICharacterProperties getCharacterProperties();

  //----------------------------------------------------------------------------
  /**
   * Gets the property store of the paragraph
   * 
   * @return the paragprah property store
   * 
   * @author Sebastian Rösgen
   */
  public IParagraphPropertyStore getParagraphPropertyStore() throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Gets the character property store of the paragraph
   * 
   * @return the paragprah's character property store
   * 
   * @author Sebastian Rösgen
   */
  public ICharacterPropertyStore getCharacterPropertyStore() throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Gets the text contained in this pragraph
   * 
   * @return the paragraph text
   * 
   * @author Sebastian Rösgen 
   */
  public String getParagraphText() throws TextException;

  //---------------------------------------------------------------------------- 
  /**
   * Sets new text to the paragraph.
   * 
   * @param text the text that should be placed
   * 
   * @author Sebastian Rösgen
   */
  public void setParagraphText(String text);
  //----------------------------------------------------------------------------
}