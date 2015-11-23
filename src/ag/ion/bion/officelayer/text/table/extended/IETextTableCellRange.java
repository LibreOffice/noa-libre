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
package ag.ion.bion.officelayer.text.table.extended;

import ag.ion.bion.officelayer.clone.ICloneServiceProvider;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTableCellProperties;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;

/**
 * Cell range of a text table. 
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public interface IETextTableCellRange extends ICloneServiceProvider {
  
  //----------------------------------------------------------------------------
  /**
   * Returns text document.
   * 
   * @return text document
   * 
   * @author Miriam Sutter
   */
  public ITextDocument getTextDocument();
  //----------------------------------------------------------------------------
  /**
   * Sets data to the cell range.
   * 
   * @param values values to  be used
   * 
   * @throws TextException if the submitted array is not suitable
   * 
   * @author Miriam Sutter
   */
  public void setData(Object[][] values) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns data of the cell range.
   * 
   * @return data of the cell range
   * 
   * @author Miriam Sutter
   */
  public Object[][] getData();
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell properties.
   * 
   * @return text table cell properties
   * 
   * @author Miriam Sutter
   */
  public ITextTableCellProperties[] getCellProperties();
  //----------------------------------------------------------------------------
  /**
   * Returns the number of rows in the range.
   * 
   * @return number of rows in the range
   * 
   * @author Miriam Sutter
   */
  public int getRowCount();
  //----------------------------------------------------------------------------
  /**
   * Returns the number of columns in the range.
   * 
   * @return number of rows in the range
   * 
   * @author Miriam Sutter
   */
  public int getColumnCount();
  //----------------------------------------------------------------------------
  /**
   * Returns cell with the submitted name.
   * 
   * @param name name of the cell
   * @param columnCount column count of the table
   * 
   * @return cell with the submitted name
   * 
   * @throws TextException if the cell is not available
   * 
   * @author Miriam Sutter
   */
  public IETextTableCell getCell(String name, int columnCount) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns cell with the submitted column and row index.
   * 
   * @param columnIndex column index of the cell
   * @param rowIndex row index of the cell
   * @param columnCount column count of the table
   * 
   * @return cell with the submitted column and row index
   * 
   * @throws TextException if the cell is not available
   * 
   * @author Miriam Sutter
   */
  public IETextTableCell getCell(int columnIndex, int rowIndex, int columnCount) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns the name of the range.
   * 
   * @return name of the range
   * 
   * @author Miriam Sutter
   */
  public ITextTableCellRangeName getRangeName();
  //----------------------------------------------------------------------------
  /**
   * Returns all cells.
   * 
   * @return all cells
   * 
   * @author Miriam Sutter
   */
  public IETextTableCell[] getCells();
  //----------------------------------------------------------------------------
}