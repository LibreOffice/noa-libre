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
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ICloneService;

import ag.ion.bion.officelayer.internal.text.table.TextTableRowCloneService;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.ITextTableRow;

import com.sun.star.beans.XPropertySet;
import com.sun.star.table.XTableRows;
import com.sun.star.text.XTextTable;
import com.sun.star.uno.UnoRuntime;

/**
 * Row of a text table.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableRow implements ITextTableRow {

  private ITextDocument 				textDocument 			= null;
  private TextTableCellRange 	textTableCellRange	= null;  
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableRow.
   * 
   * @param textTableCellRange the range of the row
   * 
   * @throws IllegalArgumentException if the range is not valid
   */
  public TextTableRow(TextTableCellRange textTableCellRange) throws IllegalArgumentException {
    if(textTableCellRange == null)
      throw new IllegalArgumentException("Submitted range is not valid.");
    textDocument = textTableCellRange.getTextDocument();
    this.textTableCellRange = textTableCellRange;
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
   * Returns cells of the text table row.
   * 
   * @return cells of the text table row
   * 
   * @author Andreas Bröker
   */
  public ITextTableCell[] getCells() {
    ITextTableCell[] textTableCells = textTableCellRange.getCells()[0];
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
		return new TextTableRowCloneService(this);
	}
  //----------------------------------------------------------------------------
  /**
   * Returns the text table cell range.
   * 
   * @return text table cell range
   * 
   * @author Miriam Sutter
   */
  public ITextTableCellRange getCellRange() {
  	return textTableCellRange;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the row height.
   * 
   * @return the row height
   * 
   * @author Markus Krüger
   */
  public int getHeight() {
    if(textTableCellRange == null)
      return 0;
    try {
      
      XTextTable xTextTable = (XTextTable)textTableCellRange.getCell(0,0).getTextTable().getXTextContent();
      XTableRows tableRows = xTextTable.getRows();
      Object row = tableRows.getByIndex(textTableCellRange.getRangeName().getRangeStartRowIndex());
      XPropertySet propertySetRow = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, row);
      Integer rowHeight = (Integer)propertySetRow.getPropertyValue("Height");
      return rowHeight.intValue();
    }
    catch (Exception exception) {
      return 0;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Sets the row height.
   * 
   * @param height the row height to be set
   * 
   * @author Markus Krüger
   */
  public void setHeight(int height) {
    if(textTableCellRange == null)
      return;
    try {      
      XTextTable xTextTable = (XTextTable)textTableCellRange.getCell(0,0).getTextTable().getXTextContent();
      XTableRows tableRows = xTextTable.getRows();
      Object row = tableRows.getByIndex(textTableCellRange.getRangeName().getRangeStartRowIndex());
      XPropertySet propertySetRow = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, row);
      propertySetRow.setPropertyValue("Height",new Integer(height));
    }
    catch (Exception exception) {
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns if the row height is set to automatically be adjusted or not.
   * 
   * @return if the row height is set to automatically be adjusted or not
   * 
   * @author Markus Krüger
   */
  public boolean getAutoHeight() {
    if(textTableCellRange == null)
      return false;
    try {      
      XTextTable xTextTable = (XTextTable)textTableCellRange.getCell(0,0).getTextTable().getXTextContent();
      XTableRows tableRows = xTextTable.getRows();
      int rowIndex = textTableCellRange.getRangeName().getRangeStartRowIndex();
      Object row = tableRows.getByIndex(rowIndex);
      XPropertySet propertySetRow = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, row);
      Boolean rowAutoHeight = (Boolean)propertySetRow.getPropertyValue("IsAutoHeight");      
      return rowAutoHeight.booleanValue();
    }
    catch (Exception exception) {
      return false;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Sets if the row height is set to automatically be adjusted or not.
   * 
   * @param autoHeight if the row height is set to automatically be adjusted or not
   * 
   * @author Markus Krüger
   */
  public void setAutoHeight(boolean autoHeight) {
    if(textTableCellRange == null)
      return;
    try {      
      XTextTable xTextTable = (XTextTable)textTableCellRange.getCell(0,0).getTextTable().getXTextContent();
      XTableRows tableRows = xTextTable.getRows();
      Object row = tableRows.getByIndex(textTableCellRange.getRangeName().getRangeStartRowIndex());
      XPropertySet propertySetRow = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, row);
      propertySetRow.setPropertyValue("IsAutoHeight",new Boolean(autoHeight));
    }
    catch (Exception exception) {
    }
  }
  //----------------------------------------------------------------------------
  
}