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
package ag.ion.bion.officelayer.internal.text.table;

import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

/**
 * Reference to a text table cell or a range of text table cells.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableCellReference {   
  
  private static final char RANGE_DELIMITER = ':';
  private static final char START_REFERENCE = '<';
  private static final char END_REFERENCE   = '>';
  private static final char TABLE_DELIMITER = '.';
  
  private String startCellReference = null;
  private String endCellReference   = null;  
  private String tableName          = null;
  
  private boolean modified = false;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableCellReference.
   * 
   * @param cellReferenceExpression text table cell reference expression
   * 
   * @throws IllegalArgumentException if the submitted reference expression is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTableCellReference(String cellReferenceExpression) throws IllegalArgumentException {
    if(cellReferenceExpression == null)
      throw new IllegalArgumentException("The submitted cell reference is not valid.");
    startCellReference = cellReferenceExpression;
    int index = -1;
    if((index = cellReferenceExpression.indexOf(RANGE_DELIMITER)) != -1) {
      startCellReference = cellReferenceExpression.substring(0, index);
      endCellReference = cellReferenceExpression.substring(index + 1);
    }
    
    if(startCellReference.startsWith(String.valueOf(START_REFERENCE)))
      if(endCellReference == null)
        startCellReference = startCellReference.substring(1, startCellReference.length() -1);
      else
        startCellReference = startCellReference.substring(1, startCellReference.length());
    if(endCellReference != null) {
      if(endCellReference.endsWith(String.valueOf(END_REFERENCE)))
        endCellReference = endCellReference.substring(0, endCellReference.length() -1);
    }
    
    int pointIndex = startCellReference.indexOf(TABLE_DELIMITER);
    if(pointIndex != -1) {
      char character = startCellReference.charAt(pointIndex + 1);
      if(Character.isLetter(character)) {
        tableName = startCellReference.substring(0, pointIndex);
        startCellReference = startCellReference.substring(pointIndex + 1, startCellReference.length());
      }
    }
    
    init();
  }
  //----------------------------------------------------------------------------
  /**
   * Inits all variables.
   * 
   * @author Andreas Bröker
   */
  private void init() {
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the text table cell reference is a range reference.
   * 
   * @return information whether the text table cell reference is a range reference
   * 
   * @author Andreas Bröker
   */
  public boolean isRangeReference() {
    if(endCellReference == null)
      return false;
    return true;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns name of the table to which the text table cell reference is bounded. Returns
   * null if no table is defined.
   * 
   * @return name of the table to which the text table cell reference is bounded
   * 
   * @author Andreas Bröker
   */
  public String getTableName() {
    return tableName;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains a cell with
   * the submitted name.
   * 
   * @param cellName name of a text table cell
   * 
   * @return informations whether this text table cell reference contains a cell with
   * the submitted name
   * 
   * @author Andreas Bröker
   */
  public boolean containsCell(String cellName) {   
    int columnIndex = TextTableCellNameHelper.getColumnIndex(cellName);
    int rowIndex = TextTableCellNameHelper.getRowIndex(cellName);
    return containsCell(columnIndex, rowIndex);       
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains a cell with
   * the submitted column and row index.
   * 
   * @param columnIndex column index of the text table cell
   * @param rowIndex row index of the text table cell
   * 
   * @return informations whether this text table cell reference contains a cell with
   * the submitted column and row index
   * 
   * @author Andreas Bröker
   */
  public boolean containsCell(int columnIndex, int rowIndex) {
    int startRowIndex = getStartRowIndex();
    int startColumnIndex = getStartColumnIndex();
    
    int endRowIndex = getEndRowIndex();
    int endColumnIndex = getEndColumnIndex();
    
    if(rowIndex >= startRowIndex && rowIndex <= endRowIndex && columnIndex >= startColumnIndex && columnIndex <= endColumnIndex) 
      return true;
    return false; 
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains the column of
   * the submitted text table cell name.
   * 
   * @param cellName name of a text table cell
   * 
   * @return informations whether this text table cell reference contains the column of
   * the submitted text table cell name
   * 
   * @author Andreas Bröker
   */
  public boolean containsColumn(String cellName) {
    int columnIndex = TextTableCellNameHelper.getColumnIndex(cellName);
    return containsColumn(columnIndex);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains the submitted
   * column index
   * 
   * @param columnIndex index of a text table column
   * 
   * @return informations whether this text table cell reference contains the submitted
   * column index
   * 
   * @author Andreas Bröker
   */
  public boolean containsColumn(int columnIndex) {
    int startColumnIndex = getStartColumnIndex();    
    int endColumnIndex = getEndColumnIndex();
    
    if(columnIndex >= startColumnIndex && columnIndex <= endColumnIndex) 
      return true;
    return false; 
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains the 
   * row of the submitted text table cell name.
   * 
   * @param cellName name of a text table cell
   * 
   * @return information whether this text table cell reference contains the row 
   * of the submitted text table cell name
   * 
   * @author Andreas Bröker
   */
  public boolean containsRow(String cellName) {
    int rowIndex = TextTableCellNameHelper.getRowIndex(cellName);
    return containsRow(rowIndex);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains a row 
   * with the submtted index.
   * 
   * @param rowIndex index of a row
   * 
   * @return information whether this text table cell reference contains a row 
   * with the submtted index
   * 
   * @author Andreas Bröker
   */
  public boolean containsRow(int rowIndex) {
    int startRowIndex = getStartRowIndex();
    int endRowIndex = getEndRowIndex();
    
    if(rowIndex >= startRowIndex && rowIndex <= endRowIndex) 
      return true;
    return false; 
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains a column
   * after the submitted column of the text table cell name.
   * 
   * @param cellName name of a text table cell
   * 
   * @return information whether this text table cell reference contains a column
   * after the submitted column of the text table cell name
   * 
   * @author Andreas Bröker
   */
  public boolean containsColumnAfter(String cellName) {
    int columnIndex = TextTableCellNameHelper.getColumnIndex(cellName);
    return containsColumnAfter(columnIndex);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains a column
   * after the submitted column index.
   * 
   * @param columnIndex index of column
   * 
   * @return information whether this text table cell reference contains a column
   * after the submitted column index
   * 
   * @author Andreas Bröker
   */
  public boolean containsColumnAfter(int columnIndex) {
    int endColumnIndex = getEndColumnIndex();
    if(endColumnIndex >= columnIndex)
      return true;
    return false;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains a row after 
   * the row index of the submitted text table cell name.
   * 
   * @param cellName name of a text table cell
   * 
   * @return information whether this text table cell reference contains a row after 
   * the row index of the submitted text table cell name
   * 
   * @author Andreas Bröker
   */
  public boolean containsRowAfter(String cellName) {
    int rowIndex = TextTableCellNameHelper.getRowIndex(cellName);
    return containsRowAfter(rowIndex);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference contains a row after 
   * the submitted row index.
   * 
   * @param rowIndex index of a row
   * 
   * @return information whether this text table cell reference contains a row after 
   * the submitted row index
   * 
   * @author Andreas Bröker
   */
  public boolean containsRowAfter(int rowIndex) {
    int endRowIndex = getEndRowIndex();
    if(endRowIndex >= rowIndex) 
      return true;
    return false;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns start column index.
   * 
   * @return start column index
   * 
   * @author Andreas Bröker
   */
  public int getStartColumnIndex() {
    return TextTableCellNameHelper.getColumnIndex(startCellReference);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns start column character.
   * 
   * @return start column character
   * 
   * @author Markus Krüger
   */
  public String getStartColumnCharacter() {
    return TextTableCellNameHelper.getColumnCharacter(startCellReference);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns end column index. If the text table cell reference is not a
   * range the method will return the start column index.
   * 
   * @return end column index
   * 
   * @author Andreas Bröker
   */
  public int getEndColumnIndex() {
    if(endCellReference == null)
      return getStartColumnIndex();
    return TextTableCellNameHelper.getColumnIndex(endCellReference);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns start row index.
   * 
   * @return start row index
   * 
   * @author Andreas Bröker
   */
  public int getStartRowIndex() {
    return TextTableCellNameHelper.getRowIndex(startCellReference);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns start row index. If the text table cell reference is not a
   * range the method will return the start row index.
   * 
   * @return start row index
   * 
   * @author Andreas Bröker
   */
  public int getEndRowIndex() {
    if(endCellReference == null)
      return getStartRowIndex();
    return TextTableCellNameHelper.getRowIndex(endCellReference);
  }
  //----------------------------------------------------------------------------
  /**
   * Bounds text table cell reference to the table with the submitted name.
   * 
   * @param tableName name of a table to be used
   * 
   * @author Andreas Bröker
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
    modified = true;
  }  
  //----------------------------------------------------------------------------
  /**
   * Moves column index.
   * 
   * @param count difference to be moved
   * 
   * @throws TextException if the column index can not be moved
   * 
   * @author Andreas Bröker
   */
  public void moveColumnIndex(int count) throws TextException {
    startCellReference = TextTableCellNameHelper.moveColumnIndex(count, startCellReference);
    if(endCellReference != null)
      endCellReference = TextTableCellNameHelper.moveColumnIndex(count, endCellReference);
    modified = true;
  }
  //----------------------------------------------------------------------------
  /**
   * Moves row index.
   * 
   * @param count difference to be moved
   * 
   * @throws TextException if the row index can not be moved
   * 
   * @author Andreas Bröker
   */
  public void moveRowIndex(int count) throws TextException {
    startCellReference = TextTableCellNameHelper.moveRowCounterValue(count, startCellReference);
    if(endCellReference != null)
      endCellReference = TextTableCellNameHelper.moveRowCounterValue(count, endCellReference);
    modified = true;
  }
  //----------------------------------------------------------------------------
  /**
   * Extends text table cell reference to text table cell range reference on 
   * the basis of the submitted index informations. The column range will be completely
   * redefined.
   * 
   * @param startColumnIndex start column index to be used
   * @param endColumnIndex end column index to be used
   * 
   * @throws TextException if the text table cell reference can not be extended
   * 
   * @author Andreas Bröker
   */
  public void toColumnRange(int startColumnIndex, int endColumnIndex) throws TextException {
    startCellReference = TextTableCellNameHelper.moveColumnIndexTo(startColumnIndex, startCellReference);
    endCellReference = TextTableCellNameHelper.moveColumnIndexTo(endColumnIndex, startCellReference);
    modified = true;
  }
  //----------------------------------------------------------------------------
  /**
   * Extends text table cell range reference to submitted end column index. 
   * 
   * @param endColumnIndex end column index to be used
   * 
   * @throws TextException if the column range can not be extended
   * 
   * @author Andreas Bröker
   */
  public void extendColumnRangeTo(int endColumnIndex) throws TextException {
    if(endColumnIndex < TextTableCellNameHelper.getColumnIndex(startCellReference))
      return;
    if(endCellReference == null) {
      endCellReference = TextTableCellNameHelper.moveColumnIndexTo(endColumnIndex, startCellReference);
    }
    else {
      if(TextTableCellNameHelper.getColumnIndex(endCellReference) < endColumnIndex)
        endCellReference = TextTableCellNameHelper.moveColumnIndexTo(endColumnIndex, endCellReference);
    }
    modified = true;
  }
  //----------------------------------------------------------------------------
  /**
   * Extends text table cell reference range. If the text table cell reference is not a 
   * range, a new one will be constructed.
   * 
   * @param count column count to be used
   * 
   * @throws TextException if the column range can not be extended
   * 
   * @author Andreas Bröker
   */
  public void extendColumnRange(int count) throws TextException {
    if(count < 1)
      return;
    if(endCellReference == null)
      endCellReference = TextTableCellNameHelper.moveColumnIndex(count, startCellReference);
    else
      endCellReference = TextTableCellNameHelper.moveColumnIndex(count, endCellReference);
    modified = true;
  }
  //----------------------------------------------------------------------------
  /**
   * Extends text table cell reference range.
   * 
   * @param rowIndexTo row index where the range has to end
   * 
   * @throws TextException if the row range can not be extended
   * 
   * @author Markus Krüger
   */
  public void extendRowRangeTo(int rowIndexTo) throws TextException {
    if(rowIndexTo <= getStartRowIndex()) {
      endCellReference = null;
      return;
    }
    if(endCellReference == null)
      endCellReference = TextTableCellNameHelper.moveRowCounterValueTo(rowIndexTo + 1, startCellReference);
    else
      endCellReference = TextTableCellNameHelper.moveRowCounterValueTo(rowIndexTo + 1, endCellReference);
    modified = true;
  }  
  //----------------------------------------------------------------------------
  /**
   * Extends text table cell reference range. If the text table cell reference is not a range,
   * a new one will be constructed.
   * 
   * @param count row count to be used
   * 
   * @throws TextException if the row range can not be extended
   * 
   * @author Andreas Bröker
   */
  public void extendRowRange(int count) throws TextException {
    if(count < 1)
      return;
    if(endCellReference == null)
      endCellReference = TextTableCellNameHelper.moveRowCounterValue(count, startCellReference);
    else
      endCellReference = TextTableCellNameHelper.moveRowCounterValue(count, endCellReference);
    modified = true;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this text table cell reference was modified after the last
   * toString() call.
   * 
   * @return information whether this text table cell reference was modified
   * 
   * @author Andreas Bröker
   */
  public boolean isModified() {
    return modified;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns cell reference expression and resets the modified status.
   * 
   * @return cell reference expression
   * 
   * @author Andreas Bröker
   */
  public String toString() {
    modified = false;
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(START_REFERENCE);
    if(tableName != null)
      stringBuffer.append(tableName).append(TABLE_DELIMITER);
    if(endCellReference == null)
      return stringBuffer.append(startCellReference).append(END_REFERENCE).toString();
    return stringBuffer.append(startCellReference).append(RANGE_DELIMITER).append(endCellReference).append(END_REFERENCE).toString();
  }
  //----------------------------------------------------------------------------
}