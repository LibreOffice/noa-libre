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
 * Last changes made by $Author: markus $, $Date: 2009-12-16 13:06:49 +0100 (Mi, 16 Dez 2009) $
 */
package ag.ion.bion.officelayer.text;

import ag.ion.bion.officelayer.clone.ICloneServiceProvider;
import ag.ion.bion.officelayer.text.table.ITextTablePropertyStore;

import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextTable;

/**
 * Table in a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11717 $
 */
public interface ITextTable extends ITextContent, ICloneServiceProvider {

  /** Maximum number of cells in a table. */
  public final static int MAX_CELLS_IN_TABLE   = 16384;

  /**
  * The function getCellRangeByPosition of the interface XCellRange works not
  * properly. A column index over 51 causes a dead lock in OpenOffice.org. Therefore
  * we do not support tables which have more than 52 columns.
  */
  public final static int MAX_COLUMNS_IN_TABLE = 52;

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextContent interface.
   * 
   * @return OpenOffice.org XTextContent interface
   * 
   * @author Markus Krüger
   * @date 31.07.2007
   */
  public XTextContent getXTextContent();

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextTable interface.
   * 
   * @return OpenOffice.org XTextTable interface
   * 
   * @author Markus Krüger
   * @date 31.07.2007
   */
  public XTextTable getXTextTable();

  //----------------------------------------------------------------------------
  /**
   * Returns text range of the text table.
   * 
   * @return text range of the text table
   * 
   * @author Markus Krüger
   * @date 31.07.2007
   */
  public ITextRange getTextRange() throws Exception;

  //----------------------------------------------------------------------------
  /**
   * Returns properties of the text table.
   * 
   * @return properties of the text table
   * 
   * @author Andreas Bröker
   */
  public ITextTableProperties getProperties();

  //----------------------------------------------------------------------------
  /**
   * Returns name of the table.
   * 
   * @return name of the table
   * 
   * @author Andreas Bröker
   */
  public String getName();

  //----------------------------------------------------------------------------
  /**
   * Adds row(s) to the table.
   * 
   * @param count number of rows to be added
   * 
   * @throws TextException if the row(s) can not be added
   * 
   * @author Andreas Bröker
   */
  public void addRow(int count) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Adds row(s) at submitted index to the table.
   * 
   * @param index index to be used
   * @param count number of rows to be added
   * 
   * @throws TextException if the row(s) can not be added
   * 
   * @author Andreas Bröker
   */
  public void addRow(int index, int count) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Returns number of available rows.
   * 
   * @return number of available rows.
   * 
   * @author Andreas Bröker
   */
  public int getRowCount();

  //----------------------------------------------------------------------------
  /**
   * Adds column(s) to the table.
   * 
   * @param count number of columns to be added
   * 
   * @throws TextException if the column(s) can not be added
   * 
   * @author Andreas Bröker
   */
  public void addColumn(int count) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Adds column(s) at submitted index the table.
   * 
   * @param index index to be used
   * @param count number of columns to be added
   * @param after true, if the rows shoud be addes after submitted index
   * 
   * @throws TextException if the column(s) can not be added
   * 
   * @author Andreas Bröker
   */
  public void addColumn(int index, int count, boolean after) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Adds column(s) before or after submitted index to the table.
   * 
   * @param index index to be used
   * @param count number of columns to be added
   * 
   * @throws TextException if the column(s) can not be added
   * 
   * @author Andreas Bröker
   */
  public void addColumn(int index, int count) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Returns a column at submitted index the table.
   * 
   * @param index index to be used
   * 
   * @return column of a table
   * 
   * @throws TextException if any error occurs
   * 
   * @author Markus Krüger
   */
  public ITextTableColumn getColumn(int index) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Returns all columns of a table.
   * 
   * @return columns of a table
   * 
   * @throws TextException if any error occurs
   * 
   * @author Markus Krüger
   */
  public ITextTableColumn[] getColumns() throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Returns number of available columns.
   * 
   * @return number of available columns
   * 
   * @author Andreas Bröker
   */
  public int getColumnCount();

  //----------------------------------------------------------------------------
  /**
   * Spread all columns evenly.
   * 
   * @throws TextException if necessary properties are not available
   * 
   * @author Markus Krüger
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
   * @author Markus Krüger
   */
  public void spreadColumnsEvenly(int startIndex, int endIndex) throws TextException;

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
   * @author Andreas Bröker
   */
  public ITextTableCell getCell(String name) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Returns all cells with formulas.
   * 
   * @return cells with formulas
   * 
   * @author Markus Krüger
   */
  public ITextTableCell[] getCellsWithFormula();

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
   * @author Andreas Bröker
   */
  public ITextTableCell getCell(int columnIndex, int rowIndex) throws TextException;

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
   * @author Andreas Bröker
   */
  public ITextTableCellRange getCellRange(int fistColumnIndex, int firstRowIndex,
      int lastColumnIndex, int lastRowIndex) throws TextException;

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
   * @author Andreas Bröker
   */
  public ITextTableCellRange getCellRange(String cellRangeName) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Returns text table row.
   * 
   * @return text table row
   * 
   * @author Andreas Bröker
   */
  public ITextTableRow[] getRows();

  //----------------------------------------------------------------------------
  /**
   * Returns text table row.
   * 
   * @param index the row index
   * 
   * @return text table row
   * 
   * @author Miriam Sutter
   */
  public ITextTableRow getRow(int index);

  //----------------------------------------------------------------------------
  /**
   * Returns the property store of the table.
   *  
   * @author sebastianr
   */
  public ITextTablePropertyStore getPropertyStore() throws TextException;

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
   * Removes the table.
   * 
   * @throws TextException if the table could not be removed.
   * 
   * @author Miriam Sutter
   */
  public void remove() throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Returns the page number where the table starts, returns -1 if page number
   * could not be determined.
   * 
   * @return the page number where the table starts, returns -1 if page number
   * could not be determined
   * 
   * @author Markus Krüger
   */
  public short getTableStartPageNumber();

  //----------------------------------------------------------------------------
  /**
   * Returns the page number where the table ends, returns -1 if page number
   * could not be determined.
   * 
   * @return the page number where the table ends, returns -1 if page number
   * could not be determined
   * 
   * @author Markus Krüger
   */
  public short getTableEndPageNumber();

  //----------------------------------------------------------------------------
  /**
   * Sets the number of header rows to apply header style for.
   * NOTE: Table must already be inserted before calling this method.
   * 
   * @param headerRows number of header rows
   * 
   * @throws TextException if the header rows could not be set
   * 
   * @author Markus Krüger
   * @date 21.03.2007
   */
  public void setHeaderRows(int headerRows) throws TextException;

  //----------------------------------------------------------------------------
  /**
   * Marks the table.
   * 
   * @author Markus Krüger
   * @date 06.08.2007
   */
  public void markTable();

  //----------------------------------------------------------------------------
  /**
   * Merges the cells defined by the range.
   * 
   * @param textTableCellRange the cell range to merge
   * 
   * @author Markus Krüger
   * @date 16.12.2009
   */
  public void merge(ITextTableCellRange textTableCellRange);

  //----------------------------------------------------------------------------
  /**
   * Splits the cells defined by the range.
   * 
   * @param textTableCellRange the cell range to split
   * @param cellCount specifies the number of new cells that will be created for each cell contained in the range. 
   * @param vertical true if the range should be split vertically. Otherwise it will be split horizontally. 
   * 
   * @author Markus Krüger
   * @date 16.12.2009
   */
  public void split(ITextTableCellRange textTableCellRange, int cellCount, boolean vertical);
  //----------------------------------------------------------------------------

}