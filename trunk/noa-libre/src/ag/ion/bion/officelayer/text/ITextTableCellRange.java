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
 * Last changes made by $Author: markus $, $Date: 2007-08-03 14:10:03 +0200 (Fr, 03 Aug 2007) $
 */
package ag.ion.bion.officelayer.text;

import ag.ion.bion.officelayer.clone.ICloneServiceProvider;

import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;

import com.sun.star.table.XCellRange;

/**
 * Cell range of a text table. 
 * 
 * @author Andreas Bröker
 * @version $Revision: 11547 $
 */
public interface ITextTableCellRange extends ICloneServiceProvider {
  
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XCellRange interface.
   * 
   * @return the OpenOffice.org XCellRange interface
   * 
   * @author Markus Krüger
   * @date 01.08.2007
   */
  public XCellRange getXCellRange();
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
   * @author Andreas Bröker
   */
  public void setData(Object[][] values) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns data of the cell range.
   * 
   * @return data of the cell range
   * 
   * @author Andreas Bröker
   */
  public Object[][] getData();
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell properties.
   * 
   * @return text table cell properties
   * 
   * @author Andreas Bröker
   */
  public ITextTableCellProperties getCellProperties();
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
   * 
   * @return cell with the submitted name
   * 
   * @throws TextException if the cell is not available
   * 
   * @author Miriam Sutter
   */
  public ITextTableCell getCell(String name) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns cell with the submitted column and row index.
   * 
   * @param columnIndex column index of the cell
   * @param rowIndex row index of the cell
   * 
   * @return cell with the submitted column and row index
   * 
   * @throws TextException if the cell is not available
   * 
   * @author Miriam Sutter
   */
  public ITextTableCell getCell(int columnIndex, int rowIndex) throws TextException;
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
  public ITextTableCell[][] getCells();
  //----------------------------------------------------------------------------
}