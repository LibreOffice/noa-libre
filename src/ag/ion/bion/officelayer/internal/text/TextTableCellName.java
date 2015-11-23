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

import ag.ion.bion.officelayer.text.ITextTableCellName;

/**
 * Name of a text table cell.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class TextTableCellName implements ITextTableCellName {

  private String cellName = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableCellName.
   * 
   * @param cellName name of the cell
   * 
   * @throws IllegalArgumentException if the submitted cell name is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTableCellName(String cellName) throws IllegalArgumentException {
    if(cellName == null)
      throw new IllegalArgumentException("The submitted cell name is not valid.");
    if(cellName.startsWith("<"))
      cellName = cellName.substring(1);
    if(cellName.endsWith(">"))
      cellName = cellName.substring(0, cellName.length() -1);
    this.cellName = cellName;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns name.
   * 
   * @return name
   * 
   * @author Andreas Bröker
   */
  public String getName() {
    return cellName;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns row index. The first row has the index 0.
   * 
   * @return row index
   * 
   * @author Andreas Bröker
   */
  public int getRowIndex() {
    char chars[] = cellName.toCharArray();
    StringBuffer stringBuffer = new StringBuffer();
    for(int i=0; i<chars.length; i++) {
      if(Character.isDigit(chars[i])) {
        stringBuffer.append(chars[i]);
      }
    }
    
    try {
      return Integer.parseInt(stringBuffer.toString()) -1;
    }
    catch(Exception exception) {
      //do nothing 
      return -1;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns column index. The first column has the index 0.
   * 
   * Does not work with merged cells.
   * 
   * @return column index
   * 
   * @author Andreas Bröker
   */
  public int getColumnIndex() {
    char chars[] = cellName.toCharArray();
    StringBuffer stringBuffer = new StringBuffer();
    for(int i=0; i<chars.length; i++) {      
      if(Character.isDigit(chars[i])) 
        break; 
      stringBuffer.append(chars[i]);
    }
    
    if(stringBuffer.length() == 1) {
      char character = stringBuffer.charAt(0);
      if(character < 97) 
        return character - 65;
      else 
        return character - 71;      
    }
    else {
      char firstCharacter = stringBuffer.charAt(0);
      char secondCharacter = stringBuffer.charAt(1);
      
      int index = (firstCharacter - 64) * 50;
      if(secondCharacter < 97) 
        index+= secondCharacter - 65;
      else
        index+= secondCharacter - 71;
      return index;
    }
  }
  //----------------------------------------------------------------------------
}