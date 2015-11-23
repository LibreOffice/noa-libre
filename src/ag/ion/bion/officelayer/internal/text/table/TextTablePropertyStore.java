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

import ag.ion.bion.officelayer.beans.IProperties;
import ag.ion.bion.officelayer.internal.beans.AbstractPropertyStore;
import ag.ion.bion.officelayer.internal.text.TextTableColumnsSeparator;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableProperties;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.text.table.ITextTablePropertyStore;


/**
 * The TextTablePropertyStore. Actually does nothing more than that :
 * get a table and stores its properties (noteably : the proeprties 
 * are not changned
 * 
 * @author Sebastian Rösgen
 * 
 * @version $Revision: 10398 $
 */
public class TextTablePropertyStore extends AbstractPropertyStore implements ITextTablePropertyStore {
	
	private int[] cellWidth = null;
	private long tableWidth = -1;
	private int columnCount = -1;
	private int rowCount = -1;
	private boolean  repeatHeadline = false;
	private TextTableColumnsSeparator[] textTableColumnsSeparators = null;
  private IProperties properties = null;
	
  //----------------------------------------------------------------------------
	/**
	 * Construcs the texttable property store.
	 * 
	 * @param table the table from which to gain the properties
	 * 
	 * @author Sebastian Rösgen
	 */
	public TextTablePropertyStore (ITextTable table) throws TextException {
		getTableAnalyse(table);
	}
  //----------------------------------------------------------------------------	
  /**
   * Returns the properties.
   * 
   * @return the properties
   * 
   * @author Markus Krüger
   */
  public IProperties getProperties() {
    return properties;
  }
  //----------------------------------------------------------------------------
	/**
	 * Sets the cell width (this method overwrites previous settings)
	 * 
	 * @param width an array containing the individual cell width
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setCellWidths(int[] width) {
		this.cellWidth = width;
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the table width (this method overwrites previous settings).
	 * 
	 * @param width the table width to be set
	 */
	public void setWidth(long width) {
		tableWidth = width;
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the number of rows of this table.
	 * 
	 * @param rows the number of rows
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setRows(int number) {
		rowCount = number;
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the number of columns in the tables.
	 * 
	 * @param number the number of columns to be set to the table
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setColumns(int number) {
		columnCount = number;
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the repeat haedline propertie (so this indicates if the headline is
	 * to be repeated over serveral pages).
	 * 
	 * @param repeatHeadline if set to true the headline is repeated otherwise not
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setRepeatHeadline(boolean repeatHeadline) throws TextException {
		this.repeatHeadline = repeatHeadline;
	}
  //----------------------------------------------------------------------------
	/**
	 * Checks if the headline is to be repeated.
	 * 
	 * @return the indicator if the headline is to be repeated
	 * 
	 * @author Sebastian Rösgen
	 */
	public boolean repeatHeadline() throws TextException {
		return this.repeatHeadline;
	}
  //----------------------------------------------------------------------------
	/**
	 * Gets the width of the cells.
	 * 
	 * @return the cell-width set currently to the table. 
	 *
 	 * @author Sebastian Rösgen
	 */
	public int[] getCellWidths() throws TextException {
		return cellWidth;
	}
  //----------------------------------------------------------------------------
	/**
	 * Gets the currently set width of the table,
	 * 
	 * @return the width currently set to the table
	 * 
	 * @author Sebastian Rösgen 
	 */
	public long getWidth() throws TextException {
		return tableWidth;
	}
  //----------------------------------------------------------------------------
	/**
	 * Gets the column seperator of the table
	 * 
	 * @return the column seperators
	 * 
	 * @author Sebastian Rösgen
	 */
	public TextTableColumnsSeparator[] getTableColumnSeparators()
			throws TextException {
		return textTableColumnsSeparators;
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the column seperators of the table
	 * 
	 * @param columnseperators the columnseperators to be set to the table
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setTableColumnSeparators(
			TextTableColumnsSeparator[] textTableColumnsSeparators)
			throws TextException {
		this.textTableColumnsSeparators = textTableColumnsSeparators;
	}

  //----------------------------------------------------------------------------
  /**
   * Returns a count (the number) of the columns in the table.
   * 
   * @return the count of the columns of the table if value is -1 then the table can't be build
   * 
   * @author Sebastian Rösgen
   */
  public int getColumns() {
  	return this.columnCount;
  }
  //----------------------------------------------------------------------------  
  /**
   * Get number (count) of rows in the table, "mirrored" by the 
   * property store.
   * 
   * @return the count of the rows of the table if value is -1 then the table can't be build
   * 
   * @author Sebastian Rösgen
   */
  public int getRows() {
  	return this.rowCount;
  }
  //----------------------------------------------------------------------------
	/**
	 * Analyses the table and fills up the store while doing this.
	 * The information are actually being placed in element variables
	 * of this class.
	 * This method is only called once (its part of the creation
	 * process beein invoked by the constructor call).
	 * 
	 * @param table the ITextTable that should be analysed
	 * 
	 * @author Sebastian Rösgen   
	 */
	private void getTableAnalyse (ITextTable table) throws TextException {
		ITextTableProperties props = table.getProperties();
    this.properties = props;
		this.columnCount = table.getColumnCount();
		this.rowCount = table.getRowCount();
		this.tableWidth = props.getWidth();
		this.cellWidth = props.getCellWidths();
		this.repeatHeadline = props.repeatHeadline();
		this.textTableColumnsSeparators = props.getTableColumnSeparators();
			
	}
  //----------------------------------------------------------------------------
}
