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

import ag.ion.bion.officelayer.internal.text.table.TextTableCellRangeName;

import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.ITextTableColumn;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;

import ag.ion.bion.officelayer.text.table.extended.IETextTableCellRange;
import ag.ion.bion.officelayer.text.table.extended.IETextTableColumn;

import java.util.ArrayList;

/**
 * Implementation for extended table columns.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class ETextTableColumn implements IETextTableColumn {

	private ETextTable					 	textTable					 	= null;
	
	private ArrayList 						textTableColumns		= new ArrayList();
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs a new extended text table column.
	 * 
	 * @param textTable the text table 
	 * 
	 * @author Miriam Sutter
	 */
	public ETextTableColumn(ETextTable textTable) {
		this.textTable = textTable;
	}
  //----------------------------------------------------------------------------
	/**
	 * Addes a text table column.
	 * 
	 * @param textTableColumn the text table columns
	 * 
	 * @author Miriam Sutter
	 */
	public void addTextTableColum(ITextTableColumn textTableColumn) {
		textTableColumns.add(textTableColumn);
	}
  //----------------------------------------------------------------------------
  /**
   * Sets column width.
   * 
   * @param width desired width of the column
   * 
   * @throws TextException if the cell is not available
   * 
   * @author Miriam Sutter
   */
	public void setWidth(short width) throws TextException {
		for(int i = 0, n = textTableColumns.size(); i < n; i++) {
			((ITextTableColumn)textTableColumns.get(i)).setWidth(width);
		}
	}
  //----------------------------------------------------------------------------
  /**
   * Returns column width.
   * 
   * @throws TextException if the cell is not available
   * 
   * @author Miriam Sutter
   */
	public short getWidth() throws TextException {
		if(textTableColumns.size()>0) {
			return ((ITextTableColumn)textTableColumns.get(0)).getWidth();
		}
		return 0;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns the text table cell range.
   * 
   * @return text table cell range
   * 
   * @throws Exception if any error occurs
   * 
   * @author Miriam Sutter
   */
	public IETextTableCellRange getCellRange() throws Exception {
		ITextTableCellRange[] textTableCellRanges = new ITextTableCellRange[textTableColumns.size()];
		int endRow = 0;
		for(int i = 0; i < textTableCellRanges.length; i++) {
			textTableCellRanges[i] = ((ITextTableColumn)textTableColumns.get(i)).getCellRange();
			endRow = endRow + ((ITextTableColumn)textTableColumns.get(i)).getCellRange().getRowCount();
		}
		
		if(textTableCellRanges.length > 0) {
			ITextTableCellRangeName tableCellRangeName = new TextTableCellRangeName(textTableCellRanges[0].getRangeName().getRangeStartColumnIndex(),
					textTableCellRanges[0].getRangeName().getRangeStartRowIndex(),
					textTableCellRanges[textTableCellRanges.length-1].getRangeName().getRangeEndColumnIndex(),
					endRow-1);
			
			ETextTableCellRange textTableCellRange = new ETextTableCellRange(textTableCellRanges,tableCellRangeName,textTable);
			return textTableCellRange;
		}
		
		return null;
	}
  //----------------------------------------------------------------------------
}
