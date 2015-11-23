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

/**
 * Properties of cell of a text table.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public interface ITextTableCellProperties extends IPropertyDescriptionElement, IProperties {
  
  /** type id of this property **/
  public static final String TYPE_ID = "ag.ion.bion.officelayer.text.TextTableCellProperties";
  
	public static final short ALIGN_UNDEFINED	= 0;
	public static final short ALIGN_CENTER		= 1;
	public static final short ALIGN_TOP				= 2;
	public static final short ALIGN_BOTTOM		= 3;
	
  //----------------------------------------------------------------------------
  /**
   * Sets style of the cell.
   * 
   * @param cellStyle style of the cell
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Andreas Bröker
   */
  public void setCellStyle(String cellStyle) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns style of the cell.
   * 
   * @return style of the cell
   * 
   * @throws TextException if the property is not available
   */
  public String getCellStyle() throws TextException;  
  //----------------------------------------------------------------------------
  /**
   * Sets number format.
   * 
   * @param numberFormat number format
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Andreas Bröker
   */
  public void setNumberFormat(int numberFormat) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns number format.
   * 
   * @return number format
   * 
   * @throws TextException if the property is not available
   * 
   * @author Andreas Bröker
   */
  public int getNumberFormat() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns the cell background color.
   * 
   * @return cell background color
   * 
   * @throws TextException if the property is not available
   * 
   * @author Miriam Sutter
   */
  public int getBackColor() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns the vertical alignment.
   * 
   * @return vertical alignment
   * 
   * @throws TextException if the property is not available
   * 
   * @author Miriam Sutter
   */
  public short getVertOrient() throws TextException;
  //----------------------------------------------------------------------------

  /**
   * Sets the CellBackColor, this overwrites previously stored
   * values.
   * 
   * @param cell background color
   * 
   * @throws TextException if the property is not available
   * 
   * @author Sebastian Rösgen
   */
  public void setBackColor(int color) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Sets the vertical alignment in the cell, this overwrites previously
   * stored values.
   * 
   * @param vertical alignment
   * 
   * @throws TextException if the property is not available
   * 
   * @author Sebastian Rösgen
   */
  public void setVertOrient(short align) throws TextException;
  //----------------------------------------------------------------------------
}