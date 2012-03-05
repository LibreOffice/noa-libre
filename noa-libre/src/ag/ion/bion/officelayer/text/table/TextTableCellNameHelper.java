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
 * Last changes made by $Author: markus $, $Date: 2008-10-06 12:02:29 +0200 (Mo, 06 Okt 2008) $
 */
package ag.ion.bion.officelayer.text.table;

import ag.ion.bion.officelayer.text.TextException;

/**
 * Helper in order to work with names of text table cells.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11661 $
 */
public class TextTableCellNameHelper {

  //---------------------------------------------------------------------------  
  /**
   * Prevents instantiation.
   * 
   * @author Miriam Sutter
   */
  private TextTableCellNameHelper() {

  }

  //----------------------------------------------------------------------------
  /**
   * Returns row index. The first row has the index 0.
   * 
   * @param cellName name of a text table cell
   * 
   * @return row index
   * 
   * @author Andreas Bröker
   */
  public static int getRowIndex(String cellName) {
    if (cellName == null)
      return -1;
    char chars[] = cellName.toCharArray();
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < chars.length; i++) {
      if (Character.isDigit(chars[i])) {
        stringBuffer.append(chars[i]);
      }
    }

    try {
      return Integer.parseInt(stringBuffer.toString()) - 1;
    }
    catch (Exception exception) {
      //do nothing 
      return -1;
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Returns row counter value. The first row has the row counter value 1.
   * 
   * @param cellName name of a text table cell
   * 
   * @return row counter value
   * 
   * @author Andreas Bröker
   */
  public static int getRowCounterValue(String cellName) {
    if (cellName == null)
      return -1;
    return getRowIndex(cellName) + 1;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns row counter value.
   * 
   * @param rowIndex row index to be used
   * 
   * @return row counter value
   * 
   * @author Andreas Bröker
   */
  public static int getRowCounterValue(int rowIndex) {
    return rowIndex + 1;
  }

  //----------------------------------------------------------------------------
  /**
   * Moves row counter value. Use positive count values in order to move the row 
   * counter value forward and negative values to move it backward.
   * 
   * @param count difference value to be moved
   * @param cellName name of a text table cell
   * 
   * @return modified cell name
   * 
   * @throws TextException if the new calculated row counter value is not valid
   * 
   * @author Andreas Bröker
   */
  public static String moveRowCounterValue(int count, String cellName) throws TextException {
    int newRowCounterValue = getRowCounterValue(cellName) + count;
    if (newRowCounterValue < 1)
      throw new TextException("The new row counter value is not valid.");
    return getColumnCharacter(cellName) + newRowCounterValue;
  }

  //----------------------------------------------------------------------------
  /**
   * Moves row counter value to submitted new row counter value.
   * 
   * @param newRowCounterValue new row counter value
   * @param cellName name of a text table cell
   * 
   * @return modified cell name
   * 
   * @throws TextException if the submitted row counter value is not valid
   * 
   * @author Andreas Bröker
   */
  public static String moveRowCounterValueTo(int newRowCounterValue, String cellName)
      throws TextException {
    if (newRowCounterValue < 0)
      throw new TextException("The submitted row counter value is not valid.");
    return getColumnCharacter(cellName) + newRowCounterValue;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns column index of the submitted cell name.
   * 
   * @param cellName name of a text table cell
   * 
   * @return column index of the submitted cell name
   * 
   * @author Sebastian Rösgen
   */
  public static int getColumnIndex(String cellName) {
    if (cellName == null)
      return -1;
    if (cellName.startsWith("<"))
      cellName = cellName.substring(1);
    if (cellName.endsWith(">"))
      cellName = cellName.substring(0, cellName.length() - 1);

    cellName = cellName.replaceAll("[0-9]*", ""); // would otherwise fudge our wanted number

    int columnNumber = 0;

    for (int i = 0; i < cellName.length(); i++) {
      int charAsValue = (byte) cellName.charAt(i) - 64;
      if (charAsValue < 0) { // something else, not A-Za-z
        break;
      }
      else if (charAsValue > 26) { // could be a-z
        charAsValue -= 6;
        if (charAsValue >= 1 && charAsValue < 53) {
          columnNumber += int4Letter(cellName.length() - (i + 1), charAsValue);
        }
        else {
          break;
        }
      }
      else {
        columnNumber += int4Letter(cellName.length() - (i + 1), charAsValue);
      }
    }

    return (columnNumber - 1);

  }

  //----------------------------------------------------------------------------
  /**
   * Calculates the concrete int representation of the
   * column. 
   * 
   * @param level the column letter position (for example <GHU12> would
   *              have 'G' as 2, 'H' as 1 and 'U' as 0
   * @param character the character value as known in ascii notation
   * 
   * @author Sebastian Rösgen
   */
  private static int int4Letter(int level, int character) {
    int multiplyer = (int) Math.pow(52, level);
    character *= multiplyer;
    return character;
  }

  //----------------------------------------------------------------------
  /**
   * Returns column character(s) of the submitted index.
   * 
   * @param columnIndex index of the column to be converted
   * 
   * @return column character(s) of the submitted index
   * 
   * @author Andreas Bröker
   */
  public static String getColumnCharacter(int columnIndex) {
    if (columnIndex < 26) {
      return String.valueOf((char) (columnIndex + 65));
    }
    else if (columnIndex < 52) {
      return String.valueOf((char) (columnIndex + 71));
    }
    else {
      char chars[] = new char[2];
      int firstIndex = (int) (columnIndex / 52);
      chars[0] = (char) (firstIndex + 64);
      int secondIndex = columnIndex - (firstIndex * 52);
      if (secondIndex < 26)
        chars[1] = (char) (secondIndex + 65);
      else
        chars[1] = (char) (secondIndex + 71);
      return String.copyValueOf(chars);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Returns column characters of the submitted cell name.
   * 
   * @param cellName name of a text table cell
   * 
   * @return column characters of the submitted cell name
   * 
   * @author Andreas Bröker
   */
  public static String getColumnCharacter(String cellName) {
    if (cellName == null)
      return null;
    char chars[] = cellName.toCharArray();
    String cellCharacters = "";
    for (int i = 0; i < chars.length; i++) {
      if (Character.isDigit(chars[i]))
        break;
      cellCharacters += chars[i];
    }
    return cellCharacters;
  }

  //----------------------------------------------------------------------------
  /**
   * Moves column index. Use positive count values in order to move the column index
   * forward and negative values to move it backward.
   * 
   * @param count difference to be moved
   * @param cellName name of a text table cell
   * 
   * @return modified cell name
   * 
   * @throws TextException if the new calculated column index is not valid
   * 
   * @author Andreas Bröker
   */
  public static String moveColumnIndex(int count, String cellName) throws TextException {
    int newColumnIndex = getColumnIndex(cellName) + count;
    if (newColumnIndex < 0)
      throw new TextException("The new column index is not valid.");
    return getColumnCharacter(newColumnIndex) + getRowCounterValue(cellName);
  }

  //----------------------------------------------------------------------------
  /**
   * Moves column to new submitted index.
   * 
   * @param newColumnIndex new column index to be used
   * @param cellName name of a text table cell
   * 
   * @return modified cell name
   * 
   * @throws TextException if the submitted column index is not valid
   * 
   * @author Andreas Bröker
   */
  public static String moveColumnIndexTo(int newColumnIndex, String cellName) throws TextException {
    if (newColumnIndex < 0)
      throw new TextException("The submitted column index is not valid.");
    int rowIndex = getRowCounterValue(cellName);
    return getColumnCharacter(newColumnIndex) + rowIndex;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the start row index of a cell range.
   * 
   * @param range the cell range
   * 
   * @return the index of the start row
   * 
   * @author Miriam Sutter
   */
  public static int getCellRangeStartRowIndex(String range) {
    int indexRangeIndicator = range.indexOf(":");
    String cellName = range;
    if (indexRangeIndicator != -1)
      cellName = range.substring(0, range.indexOf(":"));
    return getRowIndex(cellName);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the start column index of a cell range.
   * 
   * @param range the cell range
   * 
   * @return the index of the start column
   * 
   * @author Miriam Sutter
   */
  public static int getCellRangeStartColumnIndex(String range) {
    int indexRangeIndicator = range.indexOf(":");
    String cellName = range;
    if (indexRangeIndicator != -1)
      cellName = range.substring(0, indexRangeIndicator);
    return getColumnIndex(cellName);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the end row index of a cell range.
   * 
   * @param range the cell range
   * 
   * @return the index of the end row
   * 
   * @author Miriam Sutter
   */
  public static int getCellRangeEndRowIndex(String range) {
    int indexRangeIndicator = range.indexOf(":");
    String cellName = range;
    if (indexRangeIndicator != -1)
      cellName = range.substring(range.indexOf(":") + 1);
    return getRowIndex(cellName);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the end column index of a cell range.
   * 
   * @param range the cell range
   * 
   * @return the index of the end column
   * 
   * @author Miriam Sutter
   */
  public static int getCellRangeEndColumnIndex(String range) {
    int indexRangeIndicator = range.indexOf(":");
    String cellName = range;
    if (indexRangeIndicator != -1)
      cellName = range.substring(range.indexOf(":") + 1);
    return getColumnIndex(cellName);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns next column name on the basis of the submitted name of 
   * a column.
   * 
   * @param columnName name of the column to be used
   * 
   * @return next column name on the basis of the submitted name of 
   * a column
   * 
   * @author Andreas Bröker
   */
  public static String getNextColumnName(String columnName) {
    if (columnName.length() == 1) {
      char column = columnName.charAt(0);
      column++;
      if (column == 91) {
        return "a";
      }
      else if (column == 123) {
        return "AA";
      }
      return String.valueOf(column);
    }
    else if (columnName.length() == 2) {
      char columnPartOne = columnName.charAt(0);
      char columnPartTwo = columnName.charAt(1);
      columnPartTwo++;
      if (columnPartTwo == 91) {
        columnPartTwo = 97;
      }
      else if (columnPartTwo == 123) {
        columnPartTwo = 65;
        columnPartOne++;
      }
      return String.valueOf(columnPartOne) + String.valueOf(columnPartTwo);
    }
    return null;
  }

  //----------------------------------------------------------------------------
  /**
   * Creates the range name.
   * 
   * @param startColumnIndex index of the start column
   * @param startRowIndex index of the start row
   * @param endColumnIndex index of the end column
   * @param endRowIndex index of the end row
   * 
   * @return the range name
   * 
   * @author Miriam Sutter
   */
  public static String getRangeName(int startColumnIndex, int startRowIndex, int endColumnIndex,
      int endRowIndex) {
    return TextTableCellNameHelper.getColumnCharacter(startColumnIndex) + (startRowIndex + 1)
        + ":"
        + TextTableCellNameHelper.getColumnCharacter(endColumnIndex)
        + (endRowIndex + 1);
  }
  //----------------------------------------------------------------------------
}