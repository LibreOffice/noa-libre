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
package ag.ion.bion.officelayer.internal.text;

import java.util.ArrayList;
import java.util.List;

import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ICloneService;
import ag.ion.bion.officelayer.internal.text.table.TextTableCellRangeName;
import ag.ion.bion.officelayer.internal.text.table.TextTableCloneService;
import ag.ion.bion.officelayer.internal.text.table.TextTablePropertyStore;
import ag.ion.bion.officelayer.text.AbstractTextComponent;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.ITextTableColumn;
import ag.ion.bion.officelayer.text.ITextTableProperties;
import ag.ion.bion.officelayer.text.ITextTableRow;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.text.table.IFormula;
import ag.ion.bion.officelayer.text.table.IFormulaService;
import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;
import ag.ion.bion.officelayer.text.table.ITextTablePropertyStore;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;
import ag.ion.noa.text.XInterfaceObjectSelection;

import com.sun.star.beans.XPropertySet;
import com.sun.star.container.XNamed;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextSection;
import com.sun.star.text.XTextTable;
import com.sun.star.text.XTextTableCursor;
import com.sun.star.text.XTextViewCursorSupplier;
import com.sun.star.uno.UnoRuntime;

/**
 * Table of an OpenOffice.org text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11717 $
 */
public class TextTable extends AbstractTextComponent implements ITextTable {

  private XTextTable xTextTable   = null;
  private XCellRange xCellRange   = null;
  private List       formulaCells = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTable.
   * 
   * @param textDocument text document to be used
   * @param xTextTable OpenOffice.org XTextTable interface
   * 
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XTextTable interface 
   * is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTable(ITextDocument textDocument, XTextTable xTextTable)
      throws IllegalArgumentException {
    super(textDocument);
    if (xTextTable == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XTextTable interface is not valid.");
    this.xTextTable = xTextTable;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextContent interface.
   * 
   * @return OpenOffice.org XTextContent interface
   * 
   * @author Andreas Bröker
   */
  public XTextContent getXTextContent() {
    return xTextTable;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextTable interface.
   * 
   * @return OpenOffice.org XTextTable interface
   * 
   * @author Andreas Bröker
   */
  public XTextTable getXTextTable() {
    return xTextTable;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns text range of the text table.
   * 
   * @return text range of the text table
   * 
   * @author Markus Krüger
   * @date 31.07.2007
   */
  public ITextRange getTextRange() throws Exception {
    XTextContent textContent = getXTextContent();
    textDocument.setSelection(new XInterfaceObjectSelection(textContent));
    XTextViewCursorSupplier xTextViewCursorSupplier = (XTextViewCursorSupplier) UnoRuntime.queryInterface(XTextViewCursorSupplier.class,
        textDocument.getXTextDocument().getCurrentController());
    xTextViewCursorSupplier.getViewCursor().goLeft((short) 1, false);
    return textDocument.getViewCursorService().getViewCursor().getTextCursorFromEnd().getEnd();
  }

  //----------------------------------------------------------------------------
  /**
   * Returns properties of the text table.
   * 
   * @return properties of the text table
   * 
   * @author Andreas Bröker
   */
  public ITextTableProperties getProperties() {
    XPropertySet xPropertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,
        xTextTable);
    return new TextTableProperties(xPropertySet);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns name of the table.
   * 
   * @return name of the table
   * 
   * @author Andreas Bröker
   */
  public String getName() {
    XNamed xNamed = (XNamed) UnoRuntime.queryInterface(XNamed.class, xTextTable);
    return xNamed.getName();
  }

  //----------------------------------------------------------------------------
  /**
   * Sets data of cell with the submitted name.
   * 
   * @param cellName name of the cell
   * @param xTextContent content to be inserted
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public void setCellData(String cellName, XTextContent xTextContent) throws Exception {
    XCell xCell = xTextTable.getCellByName(cellName);
    XText xText = (XText) UnoRuntime.queryInterface(XText.class, xCell);
    xText.setString("");
    xText.insertTextContent(xText.getStart(), xTextContent, true);
  }

  //----------------------------------------------------------------------------
  /**
   * Adds submitted text content to the cell with the submitted name.
   * 
   * @param cellName name of the cell
   * @param xTextContent text content to be added
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public void addCellData(String cellName, XTextContent xTextContent) throws Exception {
    XCell xCell = xTextTable.getCellByName(cellName);
    XText xText = (XText) UnoRuntime.queryInterface(XText.class, xCell);
    xText.insertTextContent(xText.getEnd(), xTextContent, true);
  }

  //----------------------------------------------------------------------------
  /**
   * Sets data of the cell with the submitted name.
   * 
   * @param cellName name of the cell
   * @param content content to be inserted
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public void setCellData(String cellName, String content) throws Exception {
    XCell xCell = xTextTable.getCellByName(cellName);
    XText xText = (XText) UnoRuntime.queryInterface(XText.class, xCell);
    xText.setString(content);
  }

  //----------------------------------------------------------------------------
  /**
   * Adds submitted content to the cell with the submitted name.
   * 
   * @param cellName name of the cell
   * @param content content to be added
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public void addCellData(String cellName, String content) throws Exception {
    XCell xCell = xTextTable.getCellByName(cellName);
    XText xText = (XText) UnoRuntime.queryInterface(XText.class, xCell);
    xText.getEnd().setString(content);
  }

  //----------------------------------------------------------------------------
  /**
   * Sets data of the cell with the submitted name.
   * 
   * @param cellName name of the cell
   * @param content content to be inserted
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public void setCellData(String cellName, double content) throws Exception {
    XCell xCell = xTextTable.getCellByName(cellName);
    xCell.setValue(content);
  }

  //----------------------------------------------------------------------------
  /**
   * Sets formula into the cell with the submitted name.
   * 
   * @param cellName name of the cell
   * @param formula formula to be inserted
   * 
   * @throws Exception if any error occurs
   * 
   * @author Andreas Bröker
   */
  public void setCellFormula(String cellName, String formula) throws Exception {
    XCell xCell = xTextTable.getCellByName(cellName);
    xCell.setFormula(formula);
  }

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
  public void addRow(int count) throws TextException {
    if (count > 0)
      xTextTable.getRows().insertByIndex(getRowCount(), count);
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
   * @author Andreas Bröker
   */
  public void addRow(int index, int count) throws TextException {
    if (index > -1 && count > 0)
      xTextTable.getRows().insertByIndex(index, count);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns number of available rows.
   * 
   * @return number of available rows.
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public int getRowCount() {
    String[] cellNames = xTextTable.getCellNames();
    int rows = 0;
    for (int i = 0; i < cellNames.length; i++) {
      int row = TextTableCellNameHelper.getRowIndex(cellNames[i]);
      row = row + 1;
      if (row > rows)
        rows = row;
    }
    return rows;
  }

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
  public void addColumn(int count) throws TextException {
    if (count > 0)
      xTextTable.getColumns().insertByIndex(getColumnCount(), count);
  }

  //----------------------------------------------------------------------------
  /**
   * Adds column(s) at submitted index the table.
   * 
   * @param index index to be used
   * @param count number of columns to be added
   * 
   * @throws TextException if the column(s) can not be added
   * 
   * @author Andreas Bröker
   */
  public void addColumn(int index, int count) throws TextException {
    if (index > -1 && count > 0)
      xTextTable.getColumns().insertByIndex(index, count);
  }

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
  public void addColumn(int index, int count, boolean after) throws TextException {
    if (index > -1 && count > 0) {
      if (after) {
        xTextTable.getColumns().insertByIndex(index, count);
      }
      else {
        xTextTable.getColumns().insertByIndex(index - 1, count);
      }
    }
  }

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
  public ITextTableColumn getColumn(int index) throws TextException {
    return getColumns()[index];
  }

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
  public ITextTableColumn[] getColumns() throws TextException {
    TextTableColumn[] textTableColumns = new TextTableColumn[getColumnCount()];
    for (int i = 0; i < textTableColumns.length; i++) {
      textTableColumns[i] = new TextTableColumn(this, i);
    }
    return textTableColumns;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns number of available columns.
   * 
   * @return number of available columns
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public int getColumnCount() {
    String[] cellNames = xTextTable.getCellNames();
    int cols = 0;
    for (int i = 0; i < cellNames.length; i++) {
      int col = TextTableCellNameHelper.getColumnIndex(cellNames[i]);
      col = col + 1;
      if (col > cols)
        cols = col;
    }
    return cols;
  }

  //----------------------------------------------------------------------------
  /**
   * Spread all columns evenly.
   * 
   * @throws TextException if necessary properties are not available
   * 
   * @author Markus Krüger
   */
  public void spreadColumnsEvenly() throws TextException {
    spreadColumnsEvenly(0, getColumnCount() - 1);
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
   * @author Markus Krüger
   */
  public void spreadColumnsEvenly(int startIndex, int endIndex) throws TextException {
    if (endIndex > startIndex && startIndex > -1) {
      long tableWidth = getProperties().getWidth();
      long rangeWidth = 0;
      TextTableColumn textTableColumn = null;

      //get the width of the give range
      for (int i = startIndex; i <= endIndex; i++) {
        textTableColumn = new TextTableColumn(this, i);
        rangeWidth = rangeWidth + textTableColumn.getWidth();
      }

      //determine the evenly column width, so that all have the same size
      long evenColumnWidth = (rangeWidth / (endIndex - startIndex + 1));

      //set the first column almost as wide as the whole space for the column range
      //- this needs to be done in a loop, because that way the other collumns are on the right side of the
      //  column with the enIndex with a minimal width (needed to set the right width in the next loop)
      //- the last column gets its width automaticly
      for (int i = endIndex - 1; i >= startIndex; i--) {
        textTableColumn = new TextTableColumn(this, i);
        textTableColumn.setWidth((short) tableWidth);
      }
      //set the width for each column
      //the last column gets its width automaticly
      for (int i = startIndex; i <= endIndex; i++) {
        textTableColumn = new TextTableColumn(this, i);
        textTableColumn.setWidth((short) evenColumnWidth);
      }
    }
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
   * @author Andreas Bröker
   */
  public ITextTableCell getCell(String name) throws TextException {
    try {
      XCell xCell = xTextTable.getCellByName(name);
      if (xCell != null)
        return new TextTableCell(textDocument, xCell);
      else
        throw new TextException("A column with the name " + name
            + " is not available.");
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Returns all cells with formulas.
   * 
   * @return cells with formulas
   * 
   * @author Markus Krüger
   */
  public ITextTableCell[] getCellsWithFormula() {
    if (formulaCells == null)
      analyseTableFormulas();
    return (ITextTableCell[]) formulaCells.toArray(new ITextTableCell[formulaCells.size()]);
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
   * @author Andreas Bröker
   */
  public ITextTableCell getCell(int columnIndex, int rowIndex) throws TextException {
    String columnCharacters = TextTableCellNameHelper.getColumnCharacter(columnIndex);
    int rowCounter = TextTableCellNameHelper.getRowCounterValue(rowIndex);
    return getCell(columnCharacters + rowCounter);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns cell range on the basis submitted index informations.
   * 
   * @param firstColumnIndex index of first column inside the range
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
  public ITextTableCellRange getCellRange(int firstColumnIndex, int firstRowIndex,
      int lastColumnIndex, int lastRowIndex) throws TextException {
    String cellRangeName = TextTableCellNameHelper.getRangeName(firstColumnIndex,
        firstRowIndex,
        lastColumnIndex,
        lastRowIndex);
    try {
      if (xCellRange == null)
        xCellRange = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, xTextTable);
      XCellRange newXCellRange = xCellRange.getCellRangeByPosition(firstColumnIndex,
          firstRowIndex,
          lastColumnIndex,
          lastRowIndex);
      TextTableCellRangeName textTableCellRangeName = new TextTableCellRangeName(cellRangeName);
      TextTableCellRange textTableCellRange = new TextTableCellRange(textDocument,
          newXCellRange,
          textTableCellRangeName);
      return textTableCellRange;
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
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
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public ITextTableCellRange getCellRange(String cellRangeName) throws TextException {
    /*try {
      if(xCellRange == null)
        xCellRange = (XCellRange)UnoRuntime.queryInterface(XCellRange.class, xTextTable);
      XCellRange newXCellRange = xCellRange.getCellRangeByName(cellRangeName);
      TextTableCellRangeName textTableCellRangeName = new TextTableCellRangeName(cellRangeName);
      TextTableCellRange textTableCellRange = new TextTableCellRange(textDocument,newXCellRange, textTableCellRangeName);
      return textTableCellRange;
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    } */
    return getCellRange(TextTableCellNameHelper.getCellRangeStartColumnIndex(cellRangeName),
        TextTableCellNameHelper.getCellRangeStartRowIndex(cellRangeName),
        TextTableCellNameHelper.getCellRangeEndColumnIndex(cellRangeName),
        TextTableCellNameHelper.getCellRangeEndRowIndex(cellRangeName));
  }

  //----------------------------------------------------------------------------  
  /**
   * Returns text table row.
   * 
   * @return text table row
   *  
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public ITextTableRow[] getRows() {
    ITextTableRow[] textTableRow = new ITextTableRow[getRowCount()];
    String[] cellNames = xTextTable.getCellNames();
    int lastRowIndex = 0;
    String rangeName = "A1";
    String oldCellName = rangeName;
    if (xCellRange == null)
      xCellRange = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, xTextTable);
    for (int i = 0; i < cellNames.length; i++) {
      int thisRowIndex = TextTableCellNameHelper.getRowIndex(cellNames[i]);
      if (thisRowIndex != lastRowIndex) {
        rangeName = rangeName + ":"
            + oldCellName;
        TextTableCellRangeName textTableCellRangeName = new TextTableCellRangeName(rangeName);
        TextTableCellRange textTableCellRange = new TextTableCellRange(textDocument,
            xCellRange.getCellRangeByName(rangeName),
            textTableCellRangeName);
        textTableRow[lastRowIndex] = new TextTableRow(textTableCellRange);
        rangeName = cellNames[i];
        lastRowIndex = thisRowIndex;
      }
      oldCellName = cellNames[i];
    }
    rangeName = rangeName + ":"
        + oldCellName;
    TextTableCellRangeName textTableCellRangeName = new TextTableCellRangeName(rangeName);
    TextTableCellRange textTableCellRange = new TextTableCellRange(textDocument,
        xCellRange.getCellRangeByName(rangeName),
        textTableCellRangeName);
    textTableRow[lastRowIndex] = new TextTableRow(textTableCellRange);

    return textTableRow;
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
    return new TextTableCloneService(this, textDocument.getXTextDocument());
  }

  //----------------------------------------------------------------------------  
  /**
   * Returns text table row.
   * 
   * @param index index to be used
   * 
   * @return text table row
   *  
   * @author Miriam Sutter
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public ITextTableRow getRow(int index) {
    if (xCellRange == null)
      xCellRange = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, xTextTable);
    ITextTableRow textTableRow = null;
    try {
      String[] cellNames = xTextTable.getCellNames();
      String endCellName = "A" + (index + 1);
      for (int i = 0; i < cellNames.length; i++) {
        if (TextTableCellNameHelper.getRowIndex(cellNames[i]) == index) {
          endCellName = cellNames[i];
        }
      }
      String rangeName = "A" + (index + 1)
          + ":"
          + endCellName;
      TextTableCellRangeName textTableCellRangeName = new TextTableCellRangeName(rangeName);
      TextTableCellRange textTableCellRange = new TextTableCellRange(textDocument,
          xCellRange.getCellRangeByName(rangeName),
          textTableCellRangeName);
      textTableRow = new TextTableRow(textTableCellRange);
    }
    catch (Exception exception) {
      //do nothing      
    }
    return textTableRow;
  }

  //----------------------------------------------------------------------------  
  /**
   * Returns the property store of the table.
   * 
   * @return store a property store of the table 
   * 
   * @throws TextException if the property store is not available
   * 
   * @author Sebastian Rösgen
   */
  public ITextTablePropertyStore getPropertyStore() throws TextException {
    return new TextTablePropertyStore(this);
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
    try {
      xTextTable.getRows().removeByIndex(index, 1);
    }
    catch (Exception exception) {
      TextException textException = new TextException("The specified row could not be removed");
      textException.initCause(exception);
    }
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
    try {
      xTextTable.getRows().removeByIndex(index, count);
    }
    catch (Exception exception) {
      TextException textException = new TextException("The specified row could not be removed");
      textException.initCause(exception);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Removes the table.
   * 
   * @throws TextException if the table can not be removed
   * 
   * @author Miriam Sutter
   * @author Andreas Bröker 
   */
  public void remove() throws TextException {
    try {
      textDocument.getTextService().getTextContentService().removeTextContent(this);
    }
    catch (Exception exception) {
      TextException textException = new TextException("The specified table can not be removed");
      textException.initCause(exception);
    }
  }

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
  public short getTableStartPageNumber() {
    String[] cellNames = xTextTable.getCellNames();
    if (cellNames.length < 1)
      return -1;
    try {
      return getCell(cellNames[0]).getPageNumber();
    }
    catch (TextException exception) {
      return -1;
    }
  }

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
  public void setHeaderRows(int headerRows) throws TextException {
    if (headerRows > 0) {
      int rows = getRowCount();
      if (headerRows > rows)
        headerRows = rows;
      String[] cellNames = xTextTable.getCellNames();
      for (int i = 0; i < cellNames.length; i++) {
        int row = TextTableCellNameHelper.getRowIndex(cellNames[i]);
        if (row < headerRows)
          getCell(cellNames[i]).setCellParagraphStyle(ITextTableCell.STYLE_TABLE_HEADER);
      }
    }
  }

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
  public short getTableEndPageNumber() {
    String[] cellNames = xTextTable.getCellNames();
    if (cellNames.length < 1)
      return -1;
    try {
      return getCell(cellNames[cellNames.length - 1]).getPageNumber();
    }
    catch (TextException exception) {
      return -1;
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Marks the table.
   * 
   * @author Markus Krüger
   * @date 06.08.2007
   */
  public void markTable() {
    try {
      String firstCell = "A1";
      String range = firstCell + ":";
      ITextTableRow[] rows = getRows();
      if (rows.length > 0) {
        ITextTableCell[] cells = rows[rows.length - 1].getCells();
        String lastCellName = cells[cells.length - 1].getName().getName();
        range = range + TextTableCellNameHelper.getColumnCharacter(lastCellName)
            + TextTableCellNameHelper.getRowCounterValue(lastCellName);
        ITextTableCellRange cellRange = getCellRange(range);
        ITextDocument textDocument = getTextDocument();
        if (textDocument.isOpen()) {
          XCell cell = getXTextTable().getCellByName(firstCell);
          XPropertySet xPropertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,
              cell);
          if (xPropertySet != null) {
            Object value = xPropertySet.getPropertyValue("TextSection");
            boolean select = true;
            XTextSection xTextSection = (XTextSection) UnoRuntime.queryInterface(XTextSection.class,
                value);
            if (xTextSection != null) {
              XPropertySet xTextSectionPropertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,
                  xTextSection);
              if (xTextSectionPropertySet != null) {
                Boolean visible = (Boolean) xTextSectionPropertySet.getPropertyValue("IsVisible");
                select = visible.booleanValue();
              }
            }
            if (select)
              textDocument.setSelection(new XInterfaceObjectSelection(cellRange.getXCellRange()));
          }
        }
      }
    }
    catch (Throwable throwable) {
      //no marking possible
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Merges the cells defined by the range.
   * 
   * @param textTableCellRange the cell range to merge
   * 
   * @author Markus Krüger
   * @date 16.12.2009
   */
  public void merge(ITextTableCellRange textTableCellRange) {
    ITextTableCellRangeName cellRangeName = textTableCellRange.getRangeName();
    String startCell = TextTableCellNameHelper.getColumnCharacter(cellRangeName.getRangeStartColumnIndex()) + (cellRangeName.getRangeStartRowIndex() + 1);
    String endCell = TextTableCellNameHelper.getColumnCharacter(cellRangeName.getRangeEndColumnIndex()) + (cellRangeName.getRangeEndRowIndex() + 1);
    XTextTableCursor cur = xTextTable.createCursorByCellName(startCell);
    cur.gotoCellByName(endCell, true);
    cur.mergeRange();
  }

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
  public void split(ITextTableCellRange textTableCellRange, int cellCount, boolean vertical) {
    ITextTableCellRangeName cellRangeName = textTableCellRange.getRangeName();
    String startCell = TextTableCellNameHelper.getColumnCharacter(cellRangeName.getRangeStartColumnIndex()) + (cellRangeName.getRangeStartRowIndex() + 1);
    String endCell = TextTableCellNameHelper.getColumnCharacter(cellRangeName.getRangeEndColumnIndex()) + (cellRangeName.getRangeEndRowIndex() + 1);
    XTextTableCursor cur = xTextTable.createCursorByCellName(startCell);
    cur.gotoCellByName(endCell, true);
    cur.splitRange((short) cellCount, vertical);
  }

  //----------------------------------------------------------------------------
  /**
   * Analyses the table for cells with formulas and put them into a list.
   * 
   * @author Markus Krüger
   */
  private void analyseTableFormulas() {
    formulaCells = new ArrayList();
    ITextTableRow[] tableRows = getRows();
    for (int i = 0; i < tableRows.length; i++) {
      ITextTableRow tableRow = tableRows[i];
      if (tableRow != null) {
        ITextTableCell[] cells = tableRow.getCells();
        for (int j = 0; j < cells.length; j++) {
          ITextTableCell cell = cells[j];
          if (cell != null) {
            IFormulaService formulaService = cell.getFormulaService();
            if (formulaService != null) {
              IFormula formula = formulaService.getFormula();
              if (formula != null) {
                formulaCells.add(cell);
              }
            }
          }
        }
      }
    }
  }
  //----------------------------------------------------------------------------

}