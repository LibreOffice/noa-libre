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
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellProperties;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.text.table.ITextTableCellPropertyStore;

/**
 * "Construct" to store the properties of a table cell, so 
 * that even when deleteing the original cell (and thus loosing
 * the reference), we keep the cell information.  
 * 
 * @author Sebastian Rösgen
 * @author Markus Krüger
 * 
 * @version $Revision: 10398 $
 */
public class TextTableCellPropertyStore extends AbstractPropertyStore implements ITextTableCellPropertyStore{
	
	private String cellStyle = null;
	private Integer numberFormat = null;
	private Integer backColor = null;
	private Short verticalAlignment = null; 
	
	private int horizontalPosition = -1;
	private int verticalPosition = -1;
  
  private IProperties properties = null;
	
	//----------------------------------------------------------------------------
	/**
	 * Constructs the TextTableCellPropertyStore 
	 * 
	 * @param cellXPropertySet the propertyset of the cell whose properties should be stored
	 * 
	 * @param verticalPosition the column in which teh cell is positioned
	 * 
	 * @param horizontalPosition the row in which the cell is positioned
	 * 
	 * @author Sebastian Rösgen 
	 */
	public TextTableCellPropertyStore(ITextTableCell cell) throws TextException {
		this.verticalPosition = cell.getName().getColumnIndex();
		this.horizontalPosition = cell.getName().getRowIndex();
		construct(cell);
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
   * Sets the CellBackColor, this overwrites previously stored
   * values.
   * 
   * @param cell background color
   * 
   * @throws TextException if the property is not available
   * 
   * @author Sebastian Rösgen
   */
	public void setBackColor(int color) throws TextException {
		backColor = new Integer(color);
	}
  //----------------------------------------------------------------------------
  /**
   * Sets the vertical alignment in the cell, this overwrites previously
   * stored values.
   * 
   * @param vertical alignment
   * 
   * @throws TextException if the property is not available
   * 
   * @author Sebastian Rösgen
   */
	public void setVertOrient(short align) throws TextException {
		verticalAlignment = new Short(align);
	}
  //----------------------------------------------------------------------------
  /**
   * Sets style of the cell.
   * 
   * @param cellStyle style of the cell
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Sebastian Rösgen
   */
	public void setCellStyle(String cellStyle) throws TextException {
		this.cellStyle = cellStyle;
	}
	//----------------------------------------------------------------------------
  /**
   * Returns style of the cell.
   * 
   * @return style of the cell
   * 
   * @throws TextException if the property is not available
   * 
   * @author Sebastian Rösgen
   */
	public String getCellStyle() throws TextException {
		return cellStyle;
	}
	//----------------------------------------------------------------------------
  /**
   * Sets number format.
   * 
   * @param numberFormat number format
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Sebastian Rösgen
   */
	public void setNumberFormat(int numberFormat) throws TextException {
		this.numberFormat = new Integer(numberFormat);
	}
	//----------------------------------------------------------------------------
  /**
   * Returns number format.
   * 
   * @return number format
   * 
   * @throws TextException if the property is not available
   * 
   * @author Sebastian Rösgen
   */
	public int getNumberFormat() throws TextException {
		return numberFormat.intValue();
	}
	//----------------------------------------------------------------------------
  /**
   * Returns the cell background color.
   * 
   * @return cell background color
   * 
   * @throws TextException if the property is not available
   * 
   * @author Sebastian Rösgen
   */
	public int getBackColor() throws TextException {
		return backColor.intValue();
	}
	//----------------------------------------------------------------------------
  /**
   * Returns the vertical alignment.
   * 
   * @return vertical alignment
   * 
   * @throws TextException if the property is not available.
   * 
   * @author Sebastian Rösgen
   */
	public short getVertOrient() throws TextException {
		return verticalAlignment.shortValue();
	}
	//----------------------------------------------------------------------------	
	/**
	 * Reads out the properties and stores them.
	 * 
	 * @param xPropertySet the properties that shuld be analysed and stored. 
	 * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger
	 */
	private void construct(ITextTableCell cell) throws TextException{
		//cellStyle = (String)xPropertySet.getPropertyValue("CellStyle").toString(); // not existent
		ITextTableCellProperties properties = cell.getProperties();
    this.properties = properties;
		numberFormat = new Integer(properties.getNumberFormat()); 
		backColor = new Integer(properties.getBackColor());
		verticalAlignment = new Short(properties.getVertOrient());
	}
	//----------------------------------------------------------------------------
}
