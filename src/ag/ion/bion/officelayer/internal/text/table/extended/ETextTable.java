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
import ag.ion.bion.officelayer.clone.DestinationPosition;
import ag.ion.bion.officelayer.clone.ICloneService;
import ag.ion.bion.officelayer.clone.IDestinationPosition;

import ag.ion.bion.officelayer.internal.text.table.TextTableCellRangeName;
import ag.ion.bion.officelayer.internal.text.table.TextTablePropertyStore;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.ITextTableProperties;
import ag.ion.bion.officelayer.text.ITextTableRow;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

import ag.ion.bion.officelayer.text.table.extended.IETextTable;
import ag.ion.bion.officelayer.text.table.extended.IETextTableCell;
import ag.ion.bion.officelayer.text.table.extended.IETextTableCellRange;
import ag.ion.bion.officelayer.text.table.extended.IETextTableColumn;
import ag.ion.bion.officelayer.text.table.extended.IETextTableRow;

import ag.ion.bion.officelayer.util.ArrayUtils;

import java.util.ArrayList;

/**
 * Implementation for extended tables.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class ETextTable implements IETextTable {

	private ETextTableManagement 	textTableManagement = null;
	private ITextDocument 				textDocument				= null;
	
	private int										columnCount					= 0;
	private int										maxRowsInTable			= 0;
	
//----------------------------------------------------------------------------
	/**
	 * Constructs new extended text table.
	 * 
   * @param textDocument the text document
	 * @param textTable the first text table
	 * 
	 * @throws IllegalArgumentException if one of the parameter is not valid
   * 
   * @author Miriam Sutter
	 */
	public ETextTable(ITextDocument textDocument, ITextTable textTable) throws IllegalArgumentException {
		if(textDocument == null)
      throw new IllegalArgumentException("Submitted textDocument is not valid.");
		if(textTable== null)
      throw new IllegalArgumentException("Submitted table is not valid.");
		textTableManagement = new ETextTableManagement();
		this.textDocument = textDocument;
		columnCount = textTable.getColumnCount();
		textTableManagement.addTextTable(textTable);
		maxRowsInTable = ITextTable.MAX_CELLS_IN_TABLE/columnCount;
	}
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
	public void addRows(int count) throws TextException {
		ITextTableCellRange textTableCellRangeClone = textTableManagement.getLastTextTable().getCellRange(0,textTableManagement.getLastTextTable().getRowCount()-1,columnCount-1,textTableManagement.getLastTextTable().getRowCount()-1);
		addRowsProperty(count,textTableCellRangeClone);
	}
  //----------------------------------------------------------------------------
	/**
	 * Adds row(s) to the table
	 * 
	 * @param count number of rows to be added
	 * @param propertyRow number of the row to clone the properties
	 * 
	 * @throws TextException if the row(s) can not be added 
   * 
   * @author ???
   * @author Markus Krüger
	 */
	private void addRowsProperty(int count, ITextTableCellRange textTableCellRangeClone) throws TextException {
		try {
			ITextTable textTableLast = textTableManagement.getLastTextTable();
      int singleTableRowCount = textTableLast.getRowCount();
			if((singleTableRowCount + count) * columnCount <= ITextTable.MAX_CELLS_IN_TABLE) {
        textTableLast.addRow(count);
			}
			else {
				ITextTableCellRange textTableCellRangeHeaderClone = textTableLast.getCellRange(0,0,columnCount-1,0);
				int helpRows = maxRowsInTable-singleTableRowCount;
				if(helpRows > 0) {
          textTableLast.addRow(helpRows);
				}
				int helpCount = count-helpRows;
				while(helpCount > 0) {
					int insert = helpCount;
					TextTablePropertyStore tablePropertyStore = new TextTablePropertyStore(textTableManagement.getFirstTextTable());
					if(tablePropertyStore.repeatHeadline()) {
						insert = insert + 1;
					}
					if(helpCount * columnCount > ITextTable.MAX_CELLS_IN_TABLE) {
						insert = maxRowsInTable;
					}
					ITextTable textTable = textDocument.getTextTableService().constructTextTable(1,columnCount);
          ITextTableProperties properties = textTable.getProperties();
          properties.setRepeatHeadline(tablePropertyStore.repeatHeadline());
					textDocument.getTextService().getTextContentService().insertTextContentAfter(textTable,textTableLast);
          properties.setTableColumnSeparators(tablePropertyStore.getTableColumnSeparators());
					IDestinationPosition destinationPosition = new DestinationPosition(textTable.getCell("A1"));
					if(tablePropertyStore.repeatHeadline()) {
						textTableCellRangeHeaderClone.getCloneService().cloneToPositionNoReturn(destinationPosition,null);
						destinationPosition = new DestinationPosition(textTable.getCell("A2"));
						textTableCellRangeClone.getCloneService().cloneToPositionNoReturn(destinationPosition,false,null);
					}
					else {
						textTableCellRangeClone.getCloneService().cloneToPositionNoReturn(destinationPosition,false,null);
					}
					textTable.addRow(insert-1);
					textTableManagement.addTextTable(textTable);
					helpCount = helpCount-insert;
				}
			}
		}
		catch(Exception exception) {
			TextException textException =  new TextException("The row(s) could not be added.");
			textException.initCause(exception);
			throw textException;
		}
	}
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
	public void addRows(int index, int count) throws TextException {
		if(textTableManagement.getTextTables().length == 1 && textTableManagement.getFirstTextTable().getRowCount() + count < maxRowsInTable) {
			textTableManagement.getFirstTextTable().addRow(index,count);
			return;
		}
		IETextTableCellRange textTableCellRange = getCellRange(0,index,columnCount-1,getRowCount()-1);
		ITextTable textTable = textTableManagement.getLastTextTable();
		IDestinationPosition destinationPosition = new DestinationPosition(textTable);
		
		try {
			ETextTableCellRange tableCellRange = (ETextTableCellRange)textTableCellRange.getCloneService().cloneToPosition(destinationPosition,null).getClonedObject();
			ITextTableCellRange[] textTableCellRanges = tableCellRange.getRanges();
			int tableNumber = getTableNumber(index);
			ITextTable table = textTableManagement.getTextTable(tableNumber);
			
			int rowCount = table.getRowCount()-1;
			int rowIndex = getRowIndexInTable(tableNumber,index);
			
			removeTables(tableNumber+1,textTableManagement.getTextTables().length-tableNumber-1);
			
			ITextTableCellRange textTableCellRangeClone = table.getCellRange(0,rowIndex,columnCount-1,rowIndex);
			
			int help = rowIndex + count - (rowCount+1);
			if(help < 0) {
				for(int i = rowIndex; i <= count; i++) {
					ITextTableCellRange cellRange = table.getCellRange(0,i,columnCount-1,i);
					destinationPosition = new DestinationPosition(cellRange);
					textTableCellRangeClone.getCloneService().cloneToPosition(destinationPosition,null);
				}
				IETextTableCellRange cellRange = getCellRange(0,index,columnCount-1,index+count-1);
				clearTableRange(cellRange);
				table.removeRows(rowIndex+count,rowCount-(rowIndex+count)+1);
			}
			else {
				for(int i = rowIndex; i <= rowCount; i++) {
					ITextTableCellRange cellRange = table.getCellRange(0,i,columnCount-1,i);
					destinationPosition = new DestinationPosition(cellRange);
					textTableCellRangeClone.getCloneService().cloneToPosition(destinationPosition,null);
				}
				IETextTableCellRange cellRange = getCellRange(0,index,columnCount-1,index + (rowCount-rowIndex));
				clearTableRange(cellRange);
				addRowsProperty(help,textTableCellRangeClone);
			}
			for(int i = 0; i < textTableCellRanges.length; i++) {
				textTableManagement.addTextTable(textTableCellRanges[i].getCell(0,0).getTextTable());
			}
		}
		catch(CloneException cloneException) {
			TextException textException = new TextException("Error while cloning range");
			textException.initCause(cloneException);
			throw textException;
		}
	}
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
  public void addRows(int index, int count, boolean after) throws TextException {
  	addRows(index-1,count);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns number of available rows.
   * 
   * @return number of available rows.
   * 
   * @author Miriam Sutter
   */
	public int getRowCount() {
		int rowCount = 0;
		ITextTable[] textTables = textTableManagement.getTextTables(); 
		for(int i = 0; i < textTables.length; i++) {
			rowCount = rowCount + textTables[i].getRowCount();
		}
		return rowCount;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns cell with the submitted column and row index.
   * 
   * @param rowIndex row index of the cell
   * @param columnIndex column index of the cell
   *  
   * @return cell with the submitted column and row index
   * 
   * @throws TextException if the cell is not available
   * 
   * @author Miriam Sutter
   */
	public IETextTableCell getCell(int rowIndex, int columnIndex) throws TextException {
		String cellName = TextTableCellNameHelper.getColumnCharacter(columnIndex) + TextTableCellNameHelper.getRowCounterValue(rowIndex);
		return getCell(cellName);
	}
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
	public IETextTableCell getCell(String cellName) throws TextException {
		int rowNumber = TextTableCellNameHelper.getRowIndex(cellName);
		int tableNumber = getTableNumber(rowNumber);
		int help = 0;
		for(int i = 0; i < tableNumber; i++) {
			help = help + textTableManagement.getTextTable(i).getRowCount();
		}
		rowNumber = rowNumber - help+1; 
		
		ITextTableCell textTableCell = textTableManagement.getTextTable(tableNumber).getCell(TextTableCellNameHelper.getColumnCharacter(cellName) + rowNumber);
		ETextTableCell tableCell = new ETextTableCell(textTableCell,cellName, this);
		return tableCell;
	}
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
	public IETextTableCellRange getCellRange(int firstColumnIndex,int firstRowIndex, int lastColumnIndex, int lastRowIndex) throws TextException {
		int firstTableNumber = getTableNumber(firstRowIndex);
		int lastTableNumber = getTableNumber(lastRowIndex);
		ArrayList textTableCellRanges = new ArrayList();
		if(firstTableNumber == lastTableNumber) {
			textTableCellRanges.add(textTableManagement.getTextTable(firstTableNumber).getCellRange(firstColumnIndex,getRowIndexInTable(firstTableNumber,firstRowIndex), lastColumnIndex, getRowIndexInTable(firstTableNumber,lastRowIndex)));
		}
		else {
			ITextTable textTable = textTableManagement.getTextTable(firstTableNumber);
			textTableCellRanges.add(textTable.getCellRange(firstColumnIndex,getRowIndexInTable(firstTableNumber,firstRowIndex), lastColumnIndex, textTable.getRowCount()-1));
			for(int i = firstTableNumber+1; i < lastTableNumber; i++) {
				textTable = textTableManagement.getTextTable(i);
				textTableCellRanges.add(textTable.getCellRange(firstColumnIndex,0, lastColumnIndex, textTable.getRowCount()-1));
			}
			textTable = textTableManagement.getTextTable(lastTableNumber);
			textTableCellRanges.add(textTable.getCellRange(firstColumnIndex,0, lastColumnIndex, getRowIndexInTable(lastTableNumber,lastRowIndex)));
		}
		ITextTableCellRange[] tableCellRanges = new ITextTableCellRange[textTableCellRanges.size()];
		tableCellRanges = (ITextTableCellRange[])textTableCellRanges.toArray(tableCellRanges);
		ITextTableCellRangeName tableCellRangeName = new TextTableCellRangeName(firstColumnIndex,firstRowIndex,lastColumnIndex,lastRowIndex);
		IETextTableCellRange tableCellRange = new ETextTableCellRange(tableCellRanges,tableCellRangeName,this);
		return tableCellRange;
	}
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
	public IETextTableCellRange getCellRange(String rangeName) throws TextException {
		int firstColumnIndex = TextTableCellNameHelper.getCellRangeStartColumnIndex(rangeName);
		int firstRowIndex = TextTableCellNameHelper.getCellRangeStartRowIndex(rangeName);
		int lastColumnIndex = TextTableCellNameHelper.getCellRangeEndColumnIndex(rangeName);
		int lastRowIndex = TextTableCellNameHelper.getCellRangeEndRowIndex(rangeName);
		
		return getCellRange(firstColumnIndex, firstRowIndex, lastColumnIndex, lastRowIndex);
	}
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
	public IETextTableColumn getColumn(int columnIndex) throws TextException{
		ITextTable[] textTables = textTableManagement.getTextTables();
		if(textTables.length > 0) {
			ETextTableColumn  textTableColumn = new ETextTableColumn(this);
			for(int j = 0; j < textTables.length; j++) {
				ITextTable textTable = textTables[j];
				if(textTableColumn == null) {
					textTableColumn = new ETextTableColumn(this);
				}
				textTableColumn.addTextTableColum(textTable.getColumn(columnIndex));
			}
			return textTableColumn;
		}
		return null;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns number of available columns.
   * 
   * @return number of available columns
   * 
   * @author Miriam Sutter
   */
	public int getColumnCount() {
		if(textTableManagement.getFirstTextTable() == null) {
			return 0;
		}
		return textTableManagement.getFirstTextTable().getColumnCount();
	}
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
	public IETextTableColumn[] getColumns() throws TextException{
		ITextTable[] textTables = textTableManagement.getTextTables();
		if(textTables.length > 0) {
			ETextTableColumn [] textTableColumns = new ETextTableColumn[textTables[0].getColumnCount()];
			for(int j = 0; j < textTables.length; j++) {
				ITextTable textTable = textTables[j];
				for(int i = 0; i < textTable.getColumnCount(); i++) {
					if(textTableColumns[i] == null) {
						textTableColumns[i] = new ETextTableColumn(this);
					}
					textTableColumns[i].addTextTableColum(textTable.getColumn(i));
				}
			}
			return textTableColumns;
		}
		return null;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns properties of the text table.
   * 
   * @return properties of the text table
   * 
   * @author Miriam Sutter
   */
	public ITextTableProperties[] getProperties() {
		ITextTableProperties[] textTableProperties = new ITextTableProperties[getRowCount()];
		ITextTable[] textTables = textTableManagement.getTextTables(); 
		for(int i = 0; i < textTables.length; i++) {
			textTableProperties[i] = textTables[i].getProperties();
		}
		
		return textTableProperties;
	}
  //----------------------------------------------------------------------------
  /**
   * Spread all columns evenly.
   *
   * @throws TextException if necessary properties are not available
   * 
   * @author Miriam Sutter
   */
	public void spreadColumnsEvenly() throws TextException {
		ITextTable[] textTables = textTableManagement.getTextTables();
		for(int i = 0; i < textTables.length; i++) {
			textTables[i].spreadColumnsEvenly();
		}
	}
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
	public void spreadColumnsEvenly(int startIndex, int endIndex) throws TextException {
		ITextTable[] textTables = textTableManagement.getTextTables();
		for(int i = 0; i < textTables.length; i++) {
			textTables[i].spreadColumnsEvenly(startIndex,endIndex);
		}
	}
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
	public IETextTableRow[] getRows() throws TextException{
		IETextTableRow[] textTableRows =  new IETextTableRow[0];;
		ITextTable[] textTables = textTableManagement.getTextTables(); 
		for(int i = 0; i < textTables.length; i++) {
			ITextTableRow[] textTableRow = textTables[i].getRows();
			IETextTableRow[] rows = new IETextTableRow[textTableRow.length];
			for(int j = 0; j < textTableRow.length-1; j++) {
				IETextTableCellRange textTableCellRange = getCellRange(0,j,columnCount-1,j);
				rows[j] = new ETextTableRow(textTableCellRange,textTableRows.length+2,this);
			}
			try {
				textTableRows = (IETextTableRow[])ArrayUtils.appendArray(textTableRows,rows, IETextTableRow.class);
			}
			catch(Exception exception) {
				TextException textException = new TextException("The submitted type is not valid");
				textException.initCause(exception);
				throw textException;
			}
		}
		
		return textTableRows;
	}
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
	public IETextTableRow getRow(int index) throws TextException{
		IETextTableCellRange textTableCellRange = getCellRange(0,index, columnCount-1, index);
		IETextTableRow textTableRow = new ETextTableRow(textTableCellRange,index,this);
		return textTableRow;
	}
  //----------------------------------------------------------------------------
	/**
	 * Clones a range.
	 * 
	 * @param textTable the text table in which the range clones
	 * @param textTableCellRange the range to clone
	 * @param count the position to clone, if first 1
	 * @param adopt true if the content should be clonded
	 * 
	 * @throws CloneException if any error occurs
	 * @throws TextException if any error occurs
	 * 
	 * @author Miriam Sutter
	 */
	private void cloneRange(ITextTable textTable, ITextTableCellRange textTableCellRange, int count, boolean adoptContent) throws CloneException, TextException {
		ITextTableCell textTableCell = textTable.getCell(0,count);
		IDestinationPosition position = new DestinationPosition(textTableCell);
		textTableCellRange.getCloneService().cloneToPosition(position,adoptContent,null);
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns a specified text table.
	 * 
	 * @param textTable text table
	 * 
	 * @return the index of a text table
	 * 
	 * @author Miriam Sutter
	 */
	public int getTextTableIndex(ITextTable textTable) {
		return textTableManagement.getTextTableIndex(textTable);
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns a specified text table.
	 * 
	 * @param index the index of the text table
	 * 
	 * @return the text table
	 * 
	 * @author Miriam Sutter
	 */
	public ITextTable getTextTable(int index) {
		return textTableManagement.getTextTable(index);
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the text table management.
	 * 
	 * @return the text table management.
	 * 
	 * @author Miriam Sutter
	 */
	public ETextTableManagement getTextTableManagement() {
		return textTableManagement;
	}
  //----------------------------------------------------------------------------
	/**
	 * Clears a text table cell range.
	 * 
	 * @param textTableCellRange text table cell range to clear
	 * 
	 * @throws TextException if any error occurs
	 * 
	 * @author Miriam Sutter
	 */
	private void clearTableRange(IETextTableCellRange textTableCellRange) throws TextException {
		Object[][] clearData = textTableCellRange.getData();
			for(int i = 0; i < clearData.length; i++) {
				for(int j = 0; j < clearData[i].length; j++) {
					clearData[i][j] = "";
				}
			}
			textTableCellRange.setData(clearData);		
	}
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
  public void removeRow(int index) throws TextException {
  	int tableNumber = getTableNumber(index);
  	textTableManagement.getTextTable(tableNumber).removeRow(getRowIndexInTable(tableNumber,index));
  }
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
  public void removeRows(int index, int count) throws TextException {
  	int tableNumber = getTableNumber(index);
  	int help = count/maxRowsInTable;
  	
		if(help == 0) {
			textTableManagement.getTextTable(tableNumber).removeRows(getRowIndexInTable(tableNumber,index),count);
		}
		else {
			int removed = textTableManagement.getTextTable(tableNumber).getRowCount()-1;
	  	textTableManagement.getTextTable(tableNumber).removeRows(getRowIndexInTable(tableNumber,index),textTableManagement.getTextTable(tableNumber).getRowCount()-1);
		  for(int i = 1; i < help; i++) {
		  	removed = removed + textTableManagement.getTextTable(tableNumber+1).getRowCount();
		  	textTableManagement.getTextTable(tableNumber+1).removeRows(0,textTableManagement.getTextTable(tableNumber).getRowCount()-1);;
		  	textTableManagement.removeTextTable(tableNumber+1, textDocument);
		  }
		  textTableManagement.getTextTable(tableNumber).removeRows(getRowIndexInTable(tableNumber,index),count-removed);
		}
  }
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
  public void removeRows(int index) throws TextException {
  	int tableNumber = getTableNumber(index);
		int help = textTableManagement.getTextTables().length-tableNumber;
		textTableManagement.getTextTable(tableNumber).removeRows(getRowIndexInTable(tableNumber,index),textTableManagement.getTextTable(tableNumber).getRowCount()-1);
	  for(int i = 1; i < help; i++) {
	  	textTableManagement.removeTextTable(tableNumber+1,textDocument);
  	}
  }
  //----------------------------------------------------------------------------
  /**
   * Removes tables.
   * 
   * @param index index of the first row
   * @param count number of the tables to remove
   * 
   * @throws TextException if the rows could not removed
   * 
   * @author Miriam Sutter
   */
  public void removeTables(int index, int count) throws TextException {
	  for(int i = index; i < index + count; i++) {
	  	textTableManagement.removeTextTable(index,textDocument);
  	}
  }
  //----------------------------------------------------------------------------
  /**
   * Removes the table.
   * 
   * @throws TextException if the table could not be removed.
   * 
   * @author Miriam Sutter
   */
  public void remove() throws TextException {
  	try {
  		ITextTable[] textTables = textTableManagement.getTextTables();
  		for(int i = 0; i < textTables.length; i++) {
  			textTables[i].remove();
  		}
  		textTableManagement.removeTextTables();
  	}
  	catch(Exception exception) {
  		TextException textException = new TextException("The specified table could not be removed");
  		textException.initCause(exception);
  	}
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
  	return new ETextTableCloneService(this,textDocument);
  }
  //----------------------------------------------------------------------------
  /**
   * Addes a new text table.
   * 
   * @param textTable new text table
   * 
   * @author Miriam Sutter
   */
  public void addTable(ITextTable textTable) throws TextException {
  	if(textTable.getColumnCount() != columnCount) {
  		throw new TextException("The subimitted table is not valid");
  	}
  	textTableManagement.addTextTable(textTable);
  }
  //----------------------------------------------------------------------------
  /**
   * Retunrs the number of the table which contains a specified row.
   * 
   * @param index the index of the row
   * 
   * @return the table number
   * 
   * @author Miriam Sutter
   */
  private int getTableNumber(int index) {
  	int tableNumber = 0;
  	int helpCount = 0;
  	ITextTable[] textTables = textTableManagement.getTextTables();
  	
  	for(int i = 0; i < textTables.length; i++) {
  		helpCount = helpCount + textTables[i].getRowCount(); 
  		if(helpCount > index) {
  			tableNumber = i;
  			break;
  		}
  	}
  	return tableNumber;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the real index of a row in a text table.
   * 
   * @param tableNumber the number of the text table
   * @param rowIndex the row index in the extended text table
   * 
   * @return the real row index
   * 
   * @author Miriam Sutter
   */
  private int getRowIndexInTable(int tableNumber, int rowIndex) {
  	int rowInTableIndex = rowIndex;
  	ITextTable[] textTables = textTableManagement.getTextTables();
  	
  	for(int i = 0; i < tableNumber; i++) {
  		rowInTableIndex = rowInTableIndex - textTables[i].getRowCount();
  	}
  	return rowInTableIndex;
  }
  //----------------------------------------------------------------------------
}
