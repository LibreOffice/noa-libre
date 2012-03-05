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
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ICloneService;

import ag.ion.bion.officelayer.internal.text.table.TextTableCellRangeCloneService;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellProperties;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

import com.sun.star.beans.XPropertySet;

import com.sun.star.lang.IndexOutOfBoundsException;
import com.sun.star.sheet.XCellRangeData;

import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;

import com.sun.star.uno.UnoRuntime;

/**
 * Cell range of a text table. 
 * 
 * @author Andreas Bröker
 * @version $Revision: 11547 $
 */
public class TextTableCellRange implements ITextTableCellRange {
  
  private ITextDocument           textDocument            = null;
  private ITextTableCellRangeName textTableCellRangeName  = null;
  
  private XCellRange xCellRange = null;
    
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableCellRange.
   * 
   * @param textDocument text document to be used
   * @param xCellRange OpenOffice.org XCellRange interface
   * @param textTableCellRangeName the test table cell range name
   * 
   * @throws IllegalArgumentException if the submitted text document, OpenOffice.org XCellRange interface or
   * text table range name is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTableCellRange(ITextDocument textDocument, XCellRange xCellRange, ITextTableCellRangeName textTableCellRangeName) throws IllegalArgumentException {
  	if(textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    if(xCellRange == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XCellRange interface is not valid.");
    if(textTableCellRangeName == null)
      throw new IllegalArgumentException("Submitted text table range name is not valid.");
    if(textTableCellRangeName.getRangeStartColumnIndex() > ITextTable.MAX_COLUMNS_IN_TABLE) {
    	throw new IllegalArgumentException("The submitted range is not valid");
    }
    this.textDocument = textDocument;
    this.xCellRange = xCellRange;
    this.textTableCellRangeName = textTableCellRangeName;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XCellRange interface.
   * 
   * @return the OpenOffice.org XCellRange interface
   * 
   * @author Markus Krüger
   * @date 01.08.2007
   */
  public XCellRange getXCellRange() {
    return xCellRange;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text document.
   * 
   * @return text document
   * 
   * @author Miriam Sutter
   * @author Andreas Bröker 
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
   * @throws TextException if the submitted array is not suitably
   * 
   * @author Andreas Bröker
   */
  public void setData(Object[][] values) throws TextException {
    try {
      XCellRangeData xCellRangeData = (XCellRangeData)UnoRuntime.queryInterface(XCellRangeData.class, xCellRange);
      xCellRangeData.setDataArray(values);
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns data of the cell range.
   * 
   * @return data of the cell range
   * 
   * @author Andreas Bröker
   */
  public Object[][] getData() {
    XCellRangeData xCellRangeData = (XCellRangeData)UnoRuntime.queryInterface(XCellRangeData.class, xCellRange);
    return xCellRangeData.getDataArray();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell properties.
   * 
   * @return text table cell properties
   * 
   * @author Andreas Bröker
   */
  public ITextTableCellProperties getCellProperties() {
    XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xCellRange);
    return new TextTableCellProperties(xPropertySet);
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
  	return textTableCellRangeName.getRangeEndRowIndex()+1 - textTableCellRangeName.getRangeStartRowIndex();
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
  	return textTableCellRangeName.getRangeEndColumnIndex()+1 - textTableCellRangeName.getRangeStartColumnIndex();
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
  public ITextTableCell getCell(String name) throws TextException {
  	int columnIndex = TextTableCellNameHelper.getColumnIndex(name);
  	int rowIndex = TextTableCellNameHelper.getRowIndex(name);
  	try {
			return new TextTableCell(textDocument,xCellRange.getCellByPosition(columnIndex,rowIndex));
		}catch(IllegalArgumentException illegalArgumentException) {
  		throw new TextException("A column with the name " + name + " is not available.");
		} catch(IndexOutOfBoundsException indexOutOfBoundsException) {
  		throw new TextException("A column with the name " + name + " is not available.");
  	}
  }
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
  public ITextTableCell getCell(int columnIndex, int rowIndex) throws TextException {
  	String columnCharacters = TextTableCellNameHelper.getColumnCharacter(columnIndex);
    int rowCounter = TextTableCellNameHelper.getRowCounterValue(rowIndex);
    return getCell(columnCharacters + rowCounter);
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
  	return textTableCellRangeName;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all cells.
   * 
   * @return all cells
   * 
   * @author Miriam Sutter
   */
  public ITextTableCell[][] getCells() {
  	int columnCount = getColumnCount();
  	int rowCount = getRowCount();
  	
  	ITextTableCell[][] textTableCells = new ITextTableCell[rowCount][columnCount];
  	try {      
      for(int i=0; i<rowCount; i++) {
      	ITextTableCell[] currentRow = new ITextTableCell[columnCount];
      	for(int j=0;j<columnCount;j++) {
          XCell xCell = xCellRange.getCellByPosition(j, i);
          currentRow[j] = new TextTableCell(textDocument, xCell);
        }
      	textTableCells[i] = currentRow;
      }     
    }
    catch(Exception exception) {
      //do nothing
    }
    return textTableCells;
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
  public ICloneService getCloneService() throws CloneException {
		return new TextTableCellRangeCloneService(this); 
	}
  //----------------------------------------------------------------------------
  
}