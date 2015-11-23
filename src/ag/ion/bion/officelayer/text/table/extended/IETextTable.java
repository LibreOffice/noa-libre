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
import ag.ion.bion.officelayer.text.ITextTableProperties;
import ag.ion.bion.officelayer.text.TextException;

/**
 * Interface for extended tables.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public interface IETextTable extends ICloneServiceProvider {

  //----------------------------------------------------------------------------
  /**
   * Adds row(s) to the table.
   * 
   * @param count number of rows to be added
   * 
   * @throws TextException if the row(s) can not be added
   * 
   * @author Miriam Sutter
   */
	public void addRows(int count) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Adds row(s) at submitted index to the table.
   * 
   * @param index index to be used
   * @param count number of rows to be added
   * 
   * @throws TextException if the row(s) can not be added
   * 
   * @author Miriam Sutter
   */
	public void addRows(int index, int count) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Adds row(s) before or after submitted index to the table.
   * 
   * @param index index to be used
   * @param count number of rows to be added
   * @param after true, if the rows shoud be addes after submitted index
   * 
   * @throws TextException if the row(s) can not be added
   * 
   * @author Miriam Sutter
   */
  public void addRows(int index, int count, boolean after) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns number of available rows.
   * 
   * @return number of available rows.
   * 
   * @author Miriam Sutter
   */
	public int getRowCount();
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
	public IETextTableCell getCell(int rowIndex, int columIndex) throws TextException;
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
	public IETextTableCell getCell(String cellName) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns cell range on the basis submitted index informations.
   * 
   * @param fistColumnIndex index of first column inside the range
   * @param firstRowIndex index of first row inside the range
   * @param lastColumnIndex index of last column inside the range
   * @param lastRowIndex index of last row inside the range
   * 
   * @return cell range on the basis submitted index informations
   * 
   * @throws TextException if the cell range is not available
   * 
   * @author Miriam Sutter
   */
	public IETextTableCellRange getCellRange(int fistColumnIndex, int firstRowIndex, int lastColumnIndex, int lastRowIndex) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns cell range on the basis of the submitted cell range name.
   * 
   * @param cellRangeName name of the cell range
   * 
   * @return cell range on the basis of the submitted cell range name
   * 
   * @throws TextException if the cell range is not available
   * 
   * @author Miriam Sutter
   */
	public IETextTableCellRange getCellRange(String rangeName) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns a column at submitted index the table.
   * 
   * @param columnIndex index of the column
   * 
   * @return column of a table
   * 
   * @throws TextException if any error occurs
   * 
   * @author Miriam Sutter
   */
	public IETextTableColumn getColumn(int columnIndex) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns number of available columns.
   * 
   * @return number of available columns
   * 
   * @author Miriam Sutter
   */
	public int getColumnCount();
  //----------------------------------------------------------------------------
  /**
   * Returns all columns of a table.
   * 
   * @return columns of a table
   * 
   * @throws TextException if any error occurs
   * 
   * @author Miriam Sutter
   */
	public IETextTableColumn[] getColumns() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns properties of the text table.
   * 
   * @return properties of the text table
   * 
   * @author Miriam Sutter
   */
	public ITextTableProperties[] getProperties();
  //----------------------------------------------------------------------------
  /**
   * Spread all columns evenly.
   * 
   * @throws TextException if necessary properties are not available
   * 
   * @author Miriam Sutter
   */
	public void spreadColumnsEvenly() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Spread columns in range evenly.
   * 
   * @param startIndex index of the first column in range
   * @param endIndex   index of the last column in range
   * 
   * @throws TextException if necessary properties are not available
   * 
   * @author Miriam Sutter
   */
	public void spreadColumnsEvenly(int startIndex, int endIndex) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns text table rows.
   * 
   * @return text table rows
   * 
   * @throws TextException if any error occurs
   * 
   * @author Miriam Sutter
   */
  public IETextTableRow[] getRows() throws TextException;  
  //----------------------------------------------------------------------------
  /**
   * Returns text table row.
   * 
   * @param index the row index
   * 
   * @return text table row
   * 
   * @throws TextException if any error occurs
   * 
   * @author Miriam Sutter
   */
  public IETextTableRow getRow(int index) throws TextException;  
  //----------------------------------------------------------------------------
  /**
   * Removes a specified row.
   * 
   * @param index index of the row
   * 
   * @throws TextException if the row could not removed
   * 
   * @author Miriam Sutter
   */
  public void removeRow(int index) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Removes rows.
   * 
   * @param index index of the first row
   * @param count number of rows to remove
   * 
   * @throws TextException if the rows could not removed
   * 
   * @author Miriam Sutter
   */
  public void removeRows(int index, int count) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Removes rows.
   * 
   * @param index index of the first row
   * 
   * @throws TextException if the rows could not removed
   * 
   * @author Miriam Sutter
   */
  public void removeRows(int index) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Removes the table.
   * 
   * @throws TextException if the table could not be removed.
   * 
   * @author Miriam Sutter
   */
  public void remove() throws TextException;
  //----------------------------------------------------------------------------
}
