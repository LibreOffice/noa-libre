/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                     						*
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2006 by IOn AG                                            *
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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa.text;

import ag.ion.bion.officelayer.text.ITextComponent;
import ag.ion.bion.officelayer.text.ITextContent;
import ag.ion.bion.officelayer.text.ITextRange;

/**
 * Annotation of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public interface IAnnotation extends ITextContent, ITextComponent {

  //----------------------------------------------------------------------------
  /**
   * Returns the text content of the annotation.
   * 
   * @return the text content of the annotation
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public String getText();
  //----------------------------------------------------------------------------
  /**
   * Jumps to the annotation.
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public void jumpTo();
  //----------------------------------------------------------------------------
  /**
   * Returns text range of the annotation.
   * 
   * @return text range of the annotation
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public ITextRange getTextRange();
  //----------------------------------------------------------------------------
  
}