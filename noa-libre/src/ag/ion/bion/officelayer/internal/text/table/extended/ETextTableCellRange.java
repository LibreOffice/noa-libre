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
package ag.ion.bion.officelayer.internal.text.table.extended;

import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ICloneService;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellProperties;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;
import ag.ion.bion.officelayer.text.table.extended.IETextTable;
import ag.ion.bion.officelayer.text.table.extended.IETextTableCell;
import ag.ion.bion.officelayer.text.table.extended.IETextTableCellRange;

import ag.ion.bion.officelayer.util.ArrayUtils;

import java.util.ArrayList;

/**
 * Implementation of ITextTableCellRange for extended tables.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class ETextTableCellRange implements IETextTableCellRange {

	private ITextTableCellRange[] 			textTableCellRange			= null;
	private ITextDocument								textDocument						= null;
	private IETextTableCell[]						textTableCells					= null;
	private ITextTableCellRangeName 		textTableCellRangeName	= null;
	private ETextTable									textTable								= null;
	private ITextTableCellProperties[] 	textTableCellProperties	= null;
				
  //----------------------------------------------------------------------------
	/**
	 * Constructs a new ETextTableCellRange.
	 * 
	 * @param textTableCellRanges the text table cell ranges
	 * @param textTableCellRangeName the test table cell range name
	 * @param textTable the text table
	 * 
	 * @throws TextException if any error occurs
	 * @throws IllegalArgumentException if the submitted parameter are not valid
	 * 
	 * @author Miriam Sutter
	 */
	public ETextTableCellRange(ITextTableCellRange[] textTableCellRanges, ITextTableCellRangeName textTableCellRangeName, IETextTable textTable) throws IllegalArgumentException,TextException {
		if(textTableCellRanges.length == 0)
      throw new IllegalArgumentException("Submitted table range is not valid");
		if(textTableCellRangeName == null)
      throw new IllegalArgumentException("Submitted text table range name is not valid.");
		if(textTable == null) 
      throw new IllegalArgumentException("The submitted table is not valid.");
		if(!(textTable instanceof ETextTable)) 
      throw new IllegalArgumentException("The submitted table is not valid.");
		this.textDocument = textTableCellRanges[0].getTextDocument();
    this.textTableCellRange = textTableCellRanges;
    this.textTableCellRangeName = textTableCellRangeName;
    this.textTableCells = new IETextTableCell[0];
    this.textTable = (ETextTable)textTable;
    int rowNumber = textTableCellRangeName.getRangeStartRowIndex()+1;
    for(int j = 0; j < textTableCellRange.length; j++) {
	    ITextTableCell[] tableCells = textTableCellRange[j].getCells()[0];
	    IETextTableCell[] textTableCells =  new IETextTableCell[tableCells.length];
			for(int i = 0; i < tableCells.length; i++) {
				textTableCells[i] = new ETextTableCell(tableCells[i],TextTableCellNameHelper.getColumnCharacter(tableCells[i].getName().getName()) + rowNumber, textTable);
			}
			rowNumber++;
			try {
				this.textTableCells = (IETextTableCell[])ArrayUtils.appendArray(this.textTableCells,textTableCells,IETextTableCell.class);
			}
			catch(Exception exception) {
				TextException textException = new TextException("The submitted type is not valid");
				textException.initCause(exception);
				throw textException;
			}
    }
    textTableCellProperties = new ITextTableCellProperties[textTableCellRange.length];
		for(int i = 0; i < textTableCellProperties.length; i++) {
		 textTableCellProperties[i] = textTableCellRange[i].getCellProperties();
		}
	}
  //----------------------------------------------------------------------------
  /**
   * Returns text document.
   * 
   * @return text document
   * 
   * @author Miriam Sutter
   */
  public ITextDocument getTextDocument() {
    return textDocument;
  }
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
	public void setData(Object[][] values) throws TextException {
		int lastIndex = 0;
		if(textTableCellRange.length == 1) {
			textTableCellRange[0].setData(values);
			return;
		}
		for(int i = 0; i < textTableCellRange.length; i++) {
			Object[][] help = textTableCellRange[i].getData();
			int count = 0;
			for(int j = lastIndex; j < lastIndex + help.length; j++) {
				help[count] = values[j];
				count++;
			}
			lastIndex = lastIndex + count;
			textTableCellRange[i].setData(help);
		}

	}
  //----------------------------------------------------------------------------
  /**
   * Returns data of the cell range.
   * 
   * @return data of the cell range
   * 
   * @author Miriam Sutter
   */
	public Object[][] getData() {
		Object[][] data = null;
		ArrayList helpList = new ArrayList();
		int columns = 0;
		for(int i = 0; i < textTableCellRange.length; i++) {
			Object[][] help = textTableCellRange[i].getData();
			for(int j = 0; j < help.length; j++) {
				Object[] helpArray = help[j];
				columns = helpArray.length;
				helpList.add(helpArray);
			}
		}
		Object[][] objects = new Object[helpList.size()][columns];
		data = (Object[][])helpList.toArray(objects);
		return data;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell properties.
   * 
   * @return text table cell properties
   * 
   * @author Miriam Sutter
   */
	public ITextTableCellProperties[] getCellProperties() {
		return textTableCellProperties;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns the number of rows in the range.
   * 
   * @return number of rows in the range
   * 
   * @author Miriam Sutter
   */
  public int getRowCount() {
  	int rowCount = -1;
  	for(int i = 0; i < textTableCellRange.length; i++) {
  		rowCount = rowCount + textTableCellRange[i].getRowCount();
  	}
  	return rowCount;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the number of columns in the range.
   * 
   * @return number of rows in the range
   * 
   * @author Miriam Sutter
   */
  public int getColumnCount() {
  	int columnCount = -1;
  	for(int i = 0; i < textTableCellRange.length; i++) {
  		columnCount = columnCount + textTableCellRange[i].getColumnCount();
  	}
  	return columnCount;
  }
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
  public IETextTableCell getCell(String name, int columnCount) throws TextException {
  	int rowIndex = TextTableCellNameHelper.getRowIndex(name);
		int columnIndex = TextTableCellNameHelper.getColumnIndex(name);
		
  	return getCell(columnIndex,rowIndex,columnCount);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns cell with the submitted column and row index.
   * 
   * @param columnIndex column index of the cell
   * @param rowIndex row index of the cell
   * @param columnCount colum count of the table
   * 
   * @return cell with the submitted column and row index
   * 
   * @throws TextException if the cell is not available
   * 
   * @author Miriam Sutter
   */
  public IETextTableCell getCell(int columnIndex, int rowIndex, int columnCount) throws TextException {
  	int tableNumber = rowIndex/(ITextTable.MAX_CELLS_IN_TABLE/columnCount);
		int rowNumber = rowIndex - (tableNumber*(ITextTable.MAX_CELLS_IN_TABLE/columnCount))-1;
  	
  	ITextTableCell textTableCell = textTableCellRange[tableNumber].getCell(columnIndex,rowNumber);
  	String cellName = TextTableCellNameHelper.getColumnCharacter(columnIndex) + TextTableCellNameHelper.getRowCounterValue(rowIndex);
		ETextTableCell tableCell = new ETextTableCell(textTableCell,cellName,textTable);
		
		return tableCell;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the name of the range.
   * 
   * @return name of the range
   * 
   * @author Miriam Sutter
   */
  public ITextTableCellRangeName getRangeName() {
  	return this.textTableCellRangeName;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all cells.
   * 
   * @return all cells
   * 
   * @author Miriam Sutter
   */
  public IETextTableCell[] getCells() {
  	return textTableCells;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all text table cell ranges.
   * 
   * @return all text table cell ranges
   */
  public ITextTableCellRange[] getRanges() {
  	return textTableCellRange;
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the clone service of the element.
   * 
   * @return the clone service
   * 
   * @throws CloneException if the clone service could not be returned
   * 
   * @author Markus Krüger
   */
  public ICloneService getCloneService() throws CloneException  {
  	return new ETextTableCellRangeCloneService(this,textDocument);
  }
  //----------------------------------------------------------------------------
}
