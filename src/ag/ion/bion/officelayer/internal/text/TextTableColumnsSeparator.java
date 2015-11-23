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

import com.sun.star.text.TableColumnSeparator;

import ag.ion.bion.officelayer.text.ITextTableColumnSeparator;

/**
 * Seperator of a text table column.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableColumnsSeparator implements ITextTableColumnSeparator {
  
  private TableColumnSeparator tableColumnSeparator = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableColumnsSeparator.
   * 
   * @param tableColumnSeparator OpenOffice.org TableColumnSeparator
   * 
   * @throws IllegalArgumentException if the OpenOffice.org TableColumnSeparator struct is not valid
   * 
   * @author Markus Krüger
   */
  public TextTableColumnsSeparator(TableColumnSeparator tableColumnSeparator) throws IllegalArgumentException {
    if(tableColumnSeparator == null)
      throw new IllegalArgumentException("The submitted table column separator is not vaild.");
    
    this.tableColumnSeparator = tableColumnSeparator;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets the position of a column separator
   * 
   * @param position of the column separator
   * 
   * @author Markus Krüger
   */
  public void setPosition(short position){
    tableColumnSeparator.Position = position;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the position of a column separator
   * 
   * @return position of the column separator
   * 
   * @author Markus Krüger
   */
  public short getPosition() {
    return tableColumnSeparator.Position;
  }	
  //----------------------------------------------------------------------------  
  /**
   * Sets the visibility of a column separator
   * 
   * @param visibility of the column separator
   * 
   * @author Markus Krüger
   */
  public void setIsVisible(boolean visibility){
    tableColumnSeparator.IsVisible = visibility;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the visibility of a column separator
   * 
   * @return visibility of the column separator
   * 
   * @author Markus Krüger
   */
  public boolean getIsVisible() {
    return tableColumnSeparator.IsVisible;
  }	
  //----------------------------------------------------------------------------  
}