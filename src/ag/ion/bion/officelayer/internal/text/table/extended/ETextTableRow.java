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

import ag.ion.bion.officelayer.text.ITextDocument;

import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

import ag.ion.bion.officelayer.text.table.extended.IETextTableCell;
import ag.ion.bion.officelayer.text.table.extended.IETextTableCellRange;
import ag.ion.bion.officelayer.text.table.extended.IETextTableRow;

/**
 * Implementation for extended table rows.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class ETextTableRow implements IETextTableRow {

  private ITextDocument					textDocument 				= null;
  private IETextTableCellRange 	textTableCellRange	= null;  
  private IETextTableCell[]			textTableCells 			= null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableRow.
   * 
   * @param textTableCellRange the range of the row
   * @param rowNumber the number of the row
   * @param textTable the text table 
   * 
   * @throws IllegalArgumentException if the range is not valid
   */
  public ETextTableRow(IETextTableCellRange textTableCellRange, int rowNumber, ETextTable textTable) throws IllegalArgumentException {
    if(textTableCellRange == null)
      throw new IllegalArgumentException("Submitted range is not valid.");
    if(textTable == null) 
      throw new IllegalArgumentException("The submitted table is not valid.");
    this.textDocument = textTableCellRange.getTextDocument();
    this.textTableCellRange = textTableCellRange;
    IETextTableCell[] tableCells = textTableCellRange.getCells();
    textTableCells =  new IETextTableCell[tableCells.length];
		for(int i = 0; i < tableCells.length; i++) {
			textTableCells[i] = new ETextTableCell(tableCells[i].getTableCell(),TextTableCellNameHelper.getColumnCharacter(tableCells[i].getName().getName() + rowNumber),textTable);
		}
  }
  //----------------------------------------------------------------------------
  /**
   * Returns cells of the text table row.
   * 
   * @return cells of the text table row
   * 
   * @author Miriam Sutter
   */
	public IETextTableCell[] getCells() {
		return textTableCells;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns the text table cell range.
   * 
   * @return text table cell range
   * 
   * @author Miriam Sutter
   */
	public IETextTableCellRange getCellRange() {
		return textTableCellRange;
	}
  //----------------------------------------------------------------------------
}
