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
package ag.ion.bion.officelayer.text;

import ag.ion.noa.NOAException;

/**
 * Master of a textfield of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public interface ITextFieldMaster {
  
  //----------------------------------------------------------------------------
  /**
   * Returns name of the master of a textfield. Returns null if a name is
   * not available.
   * 
   * @return name of the master of a textfield or null if a name is
   * not available
   * 
   * @author Andreas Bröker
   */
  public String getName();
  //----------------------------------------------------------------------------
  /**
   * Returns content of the master of a textfield. Returns null if a content
   * is not available.
   * 
   * @return content of the master of a textfield or null if a content
   * is not available
   * 
   * @author Andreas Bröker
   */
  public String getContent();
  //----------------------------------------------------------------------------
  /**
   * Sets content of the master of a textfield.
   * 
   * @param content content to be used
   * 
   * @throws TextException if the new content can not be set
   *  
   * @author Andreas Bröker
   */
  public void setContent(String content) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns all related textfields of this textfield master.
   * 
   * @return all related textfields of this textfield master
   * 
   * @throws TextException if the textfields can not be fetched
   * 
   * @author Andreas Bröker
   */
  public ITextField[] getTextFields() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Constructs new textfield on the basis of this textfield master.
   * 
   * @return new constructed textfield on the basis of this textfield master
   * 
   * @throws NOAException if the new textfield can not be constructed
   * 
   * @author Andreas Bröker
   * @date 16.02.2006
   */
  public ITextField constructNewTextField() throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Removes the master of a textfield from the document.
   *
   * @author Andreas Bröker
   */
  public void remove();
  //----------------------------------------------------------------------------
  
}