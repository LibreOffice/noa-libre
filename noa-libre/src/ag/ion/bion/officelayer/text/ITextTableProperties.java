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

import ag.ion.bion.officelayer.beans.IProperties;

import ag.ion.bion.officelayer.internal.text.TextTableColumnsSeparator;

/**
 * Properties of a text table.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public interface ITextTableProperties extends IPropertyDescriptionElement, IProperties {
  
  /** type id of this property **/
  public static final String TYPE_ID = "ag.ion.bion.officelayer.text.TextTableProperties";

  //----------------------------------------------------------------------------
  /**
   * Sets information whether the first row is repeated on every page
   * as headline.
   * 
   * @param repeatHeadline information whether the first row is repeated on every page
   * as headline
   * 
   * @throws TextException if the property can not be set
   * 
   * @author Andreas Bröker
   */
  public void setRepeatHeadline(boolean repeatHeadline) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the first row is repeated on every page
   * as headline.
   * 
   * @return information whether the first row is repeated on every page
   * as headline
   * 
   * @throws TextException if the property is not available
   * 
   * @author Andreas Bröker
   */
  public boolean repeatHeadline() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns the widths of the cells.
   * 
   * @return widths of the cells
   * 
   * @throws TextException if the property is not available
   * 
   * @author Miriam sutter
   */
  public int[] getCellWidths() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns the widths of the table.
   * 
   * @return widths of the table
   * 
   * @throws TextException if the property is not available
   * 
   * @author Miriam sutter
   */
  public long getWidth() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns the table column separators.
   * 
   * @return separators of the table columns
   * 
   * @throws TextException if the property is not available
   * 
   * @author Markus Krüger
   */
  public TextTableColumnsSeparator[] getTableColumnSeparators() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Sets the table column separators.
   * 
   * @throws TextException if the property is not available
   * 
   * @author Markus Krüger
   */
  public void setTableColumnSeparators(TextTableColumnsSeparator[] textTableColumnsSeparators) throws TextException;
  //----------------------------------------------------------------------------
}
