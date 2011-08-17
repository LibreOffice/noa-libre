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

import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

/**
 * Implementation of ITextTableRangeName
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class TextTableCellRangeName implements ITextTableCellRangeName {
	
	private String	rangeName 				= null;
	private int			startColumnIndex	= -1;
	private int			endColumnIndex		= -1;
	private int			startRowIndex			= -1;
	private int			endRowIndex				= -1;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs a new text table cell range name.
	 * 
	 * @param rangeName name of the range
	 * 
	 * @author Miriam Sutter
	 */
	public TextTableCellRangeName(String rangeName) {
		this.rangeName = rangeName;
		this.startColumnIndex = TextTableCellNameHelper.getCellRangeStartColumnIndex(rangeName);
		this.startRowIndex = TextTableCellNameHelper.getCellRangeStartRowIndex(rangeName);
		this.endColumnIndex = TextTableCellNameHelper.getCellRangeEndColumnIndex(rangeName);
		this.endRowIndex = TextTableCellNameHelper.getCellRangeEndRowIndex(rangeName);
	}
  //----------------------------------------------------------------------------
	/**
	 * Constructs an new text table cell range name.
	 * 
	 * @param startColumnIndex index of the start column
	 * @param startRowIndex index of the start row
	 * @param endColumnIndex index of the end column
	 * @param endRowIndex index of the end row
	 * 
	 * @author Miriam Sutter
	 */
	public TextTableCellRangeName(int startColumnIndex, int startRowIndex, int endColumnIndex, int endRowIndex) {
		this.startColumnIndex = startColumnIndex;
		this.startRowIndex = startRowIndex;
		this.endColumnIndex = endColumnIndex;
		this.endRowIndex = endRowIndex;
		this.rangeName = TextTableCellNameHelper.getRangeName(startColumnIndex, startRowIndex, endColumnIndex, endRowIndex);
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the range name.
	 * 
	 * @return range name
	 * 
	 * @author Miriam Sutter
	 */
	public String getRangeName() {
		return rangeName;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the range start index of the column.
	 * 
	 * @return range start index of the column
	 * 
	 * @author Miriam Sutter
	 */
	public int getRangeStartColumnIndex() {
		return startColumnIndex;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the range start index of the row.
	 * 
	 * @return range start index of the row
	 * 
	 * @author Miriam Sutter
	 */
	public int getRangeStartRowIndex() {
		return startRowIndex;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the range end index of the column.
	 * 
	 * @return range end index of the column
	 * 
	 * @author Miriam Sutter
	 */
	public int getRangeEndColumnIndex() {
		return endColumnIndex;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the range end index of the row.
	 * 
	 * @return range end index of the row
	 * 
	 * @author Miriam Sutter
	 */
	public int getRangeEndRowIndex() {
		return endRowIndex;
	}
  //----------------------------------------------------------------------------
}
