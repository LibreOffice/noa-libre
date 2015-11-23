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
package ag.ion.bion.officelayer.text.table;

import ag.ion.bion.officelayer.text.ITextTableProperties;

/**
 * Propertystore for tablecells.
 * 
 * @author Sebastian Rösgen
 * 
 * @version $Revision: 10398 $
 */
public interface ITextTablePropertyStore extends ITextTableProperties {

  //----------------------------------------------------------------------------
	 /**
	  * Sets the cell width , this overwrites the currently 
	  * stored value.
	  * 
	  * @param width
	  * 
	  * @throws TextException
	  * 
	  * @author Sebastian Rösgen
	  */
  public void setCellWidths(int[] width);
  //----------------------------------------------------------------------------
	  /**
	   * Sets the width of the table, this overwrites the currently
	   * stored value.
	   * 
	   * @param width
	   * 
	   * @throws TextException
	   * 
	   * @author Sebastian Rösgen
	   */
  public void setWidth(long width);
  //----------------------------------------------------------------------------
  /**
   * Sets the numbers of rows in the table.
   * 
   * @param number
   * 
   * @author Sebastian Rösgen
   */
  public void setRows(int number);
  //----------------------------------------------------------------------------
  /**
   * Sets the number of columns of the table.
   * 
   * @param number
   * 
   * @author Sebastian Rösgen
   */
  public void setColumns(int number);
  //----------------------------------------------------------------------------
  /**
   * Returns a count (the number) of the columns in the table.
   * 
   * @return the count of the columns of the table
   * 
   * @author Sebastian Rösgen
   */
  public int getColumns();
  //----------------------------------------------------------------------------  
  /**
   * Get number (count) of rows in the table, "mirrored" by the 
   * property store.
   * 
   * @return the count of the rows of the table
   * 
   * @author Sebastian Rösgen
   */
  public int getRows() ; 
  //----------------------------------------------------------------------------
}
