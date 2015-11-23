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

import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.ITextTableCellReferencesService;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

/**
 * Text table cell references service implementation.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableCellReferencesService implements ITextTableCellReferencesService {
  
  protected TextTableFormulaExpression  textTableFormulaExpression  = null;  
  protected TextTableFormulaModel       textTableFormulaModel       = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableCellReferencesService.
   * 
   * @param textTableFormulaExpression text table formua expression to be used
   * 
   * @throws IllegalArgumentException if the submitted text table formula expression is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTableCellReferencesService(TextTableFormulaExpression textTableFormulaExpression) throws IllegalArgumentException {
    if(textTableFormulaExpression == null) 
      throw new IllegalArgumentException("The submitted formula expression is not valid.");
    this.textTableFormulaExpression = textTableFormulaExpression;
    this.textTableFormulaModel = new TextTableFormulaModel(textTableFormulaExpression);
  } 
  //----------------------------------------------------------------------------
  /**
   * Returns informations whether this formula has a reference to a text table cell
   * with the submitted name.
   * 
   * @param cellName name of a text table cell
   * 
   * @return informations whether this formula has a reference to a text table cell
   * with the submitted name
   * 
   * @author Andreas Bröker
   */
  public boolean hasCellReferenceTo(String cellName) {    
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        boolean value = textTableCellReference.containsCell(cellName);
        if(value)
          return true;
      }
    }
    return false;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this formula has a reference to a column with the 
   * submitted index.
   * 
   * @param columnIndex index of a column
   * 
   * @return information whether this formula has a reference to a column with the 
   * submitted index
   * 
   * @author Andreas Bröker
   */
  public boolean hasColumnReferenceTo(int columnIndex) {   
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        boolean value = textTableCellReference.containsColumn(columnIndex);
        if(value)
          return true;
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this formula has a reference to a column after the 
   * submitted column index.
   * 
   * @param columnIndex column index to be used
   * 
   * @return information whether this formula has a reference to a column after the 
   * submitted column index
   * 
   * @author Andreas Bröker
   */
  public boolean hasColumnReferenceAfter(int columnIndex) {   
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        boolean value = textTableCellReference.containsColumnAfter(columnIndex);
        if(value)
          return true;
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this formula has a reference to the submitted row 
   * index.
   * 
   * @param rowIndex index of row
   * 
   * @return information whether this formula has a reference to the submitted row 
   * index
   * 
   * @author Andreas Bröker
   */
  public boolean hasRowReferenceTo(int rowIndex) {    
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        boolean value = textTableCellReference.containsRow(rowIndex);
        if(value)
          return true;
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether this formula has a reference to a row after the 
   * submitted row index.
   * 
   * @param rowIndex row index to be used
   * 
   * @return information whether this formula has a reference to a row after the 
   * submitted row index
   * 
   * @author Andreas Bröker
   */
  public boolean hasRowReferenceAfter(int rowIndex) {   
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        boolean value = textTableCellReference.containsRowAfter(rowIndex);
        if(value)
          return true;
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------
  /**
   * Moves column references. Use positive values in order to move the column references
   * forward and negative values to move them backward.
   * 
   * @param columnCount column index difference to be moved
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Andreas Bröker
   */
  public void moveColumnReferences(int columnCount) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {       
        textTableCellReference.moveColumnIndex(columnCount);        
      }
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Moves column references which have a reference to the column of the submitted
   * name of a text table cell. Use positive values in order to move the column 
   * references forward and negative values to move them backward.
   * 
   * @param cellName name of a text table cell
   * @param columnCount column index difference to be moved
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Andreas Bröker
   */
  public void moveColumnReferences(String cellName, int columnCount) throws TextException {
    moveColumnReferences(TextTableCellNameHelper.getColumnIndex(cellName), columnCount);
  }
  //----------------------------------------------------------------------------
  /**
   * Moves column references which have a reference to the column with the submitted 
   * index. Use positive values in order to move the column references forward 
   * and negative values to move them backward.
   * 
   * @param columnIndex index of a column
   * @param columnCount column index difference to be moved
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public void moveColumnReferences(int columnIndex, int columnCount) throws TextException {
    moveColumnReferences(columnIndex, columnCount, new int[0]);
  }
  //----------------------------------------------------------------------------
  /**
   * Moves column references which have a reference to the column with the submitted 
   * index except references with the given row indices. Use positive values 
   * in order to move the column references forward and negative values to move them 
   * backward.
   * 
   * @param columnIndex index of a column
   * @param columnCount column index difference to be moved
   * @param exceptRows references to rows that are ignored
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Markus Krüger
   */
  public void moveColumnReferences(int columnIndex, int columnCount, int[] exceptRows) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsColumn(columnIndex)) {
          boolean except = false;
          for(int j = 0; j < exceptRows.length; j++) {
            if(textTableCellReference.containsRow(exceptRows[j])) {
              except = true;
              break;
            }
          }
          if(!except)
            textTableCellReference.moveColumnIndex(columnCount);
        }
      }
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Moves row references. Use positive values in order to move the row references forward 
   * and negative values to move them backward.
   * 
   * @param rowCount row index difference to be moved
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Andreas Bröker
   */
  public void moveRowReferences(int rowCount) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {        
        textTableCellReference.moveRowIndex(rowCount);        
      }
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Moves row references which have a reference to the row index of the submitted 
   * name of a text table cell. Use positive values in order to move the row references forward 
   * and negative values to move them backward.
   * 
   * @param cellName name of a text table cell
   * @param rowCount row index difference to be moved
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Andreas Bröker
   */  
  public void moveRowReferences(String cellName, int rowCount) throws TextException {
    moveRowReferences(TextTableCellNameHelper.getRowIndex(cellName), rowCount);
  }
  //----------------------------------------------------------------------------
  /**
   * Moves row references which have a reference to the submitted row index. Use 
   * positive values in order to move the row references forward and negative 
   * values to move them backward.
   * 
   * @param rowIndex row index to be used
   * @param rowCount row index difference to be moved
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public void moveRowReferences(int rowIndex, int rowCount) throws TextException {
    moveRowReferences(rowIndex, rowCount, new int[0]);
  }
  //----------------------------------------------------------------------------
  /**
   * Moves row references which have a reference to the submitted row index except 
   * references with the given column indices. Use 
   * positive values in order to move the row references forward and negative 
   * values to move them backward.
   * 
   * @param rowIndex row index to be used
   * @param rowCount row index difference to be moved
   * @param exceptCols references to columns that are ignored
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Markus Krüger
   */
  public void moveRowReferences(int rowIndex, int rowCount, int[] exceptCols) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsRow(rowIndex)) {
          boolean except = false;
          for(int j = 0; j < exceptCols.length; j++) {
            if(textTableCellReference.containsColumn(exceptCols[j])) {
              except = true;
              break;
            }
          }          
          if(!except)
            textTableCellReference.moveRowIndex(rowCount);
        }
      }
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Moves column references which have a reference to a column after the submitted
   * index. Use positive values in order to move the column references forward 
   * and negative values to move them backward.
   * 
   * @param columnIndex column index to be used
   * @param columnCount column index difference to be moved
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Andreas Bröker
   */
  public void moveColumnReferencesAfter(int columnIndex, int columnCount) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsColumnAfter(columnIndex)) {
          textTableCellReference.moveColumnIndex(columnCount);
        }
      }
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Moves row references which have a reference to a row after the submitted index. Use 
   * positive values in order to move the row references forward and negative 
   * values to move them backward.
   * 
   * @param rowIndex row index to be used
   * @param rowCount row index difference to be moved
   * 
   * @throws TextException if the text table cell references can not be moved
   * 
   * @author Andreas Bröker
   */
  public void moveRowReferencesAfter(int rowIndex, int rowCount) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsRowAfter(rowIndex)) {
          textTableCellReference.moveRowIndex(rowCount);
        }
      }
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Extends column references which have a reference to the column index of the submitted 
   * name of a text table cell.
   * 
   * @param cellName name of a text table cell
   * @param columnCount column index difference to be used
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Andreas Bröker
   */ 
  public void extendColumnReferences(String cellName, int columnCount) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsColumn(cellName)) {
          textTableCellReference.extendColumnRange(columnCount);        
        }
      }
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Extends column references which have a reference to the submitted column index
   * of a text table cell.
   * 
   * @param columnIndex column index of a text table cell
   * @param columnCount column index difference to be used
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Markus Krüger
   */
  public void extendColumnReferences(int columnIndex, int columnCount) throws TextException {
    extendColumnReferences(columnIndex, columnCount, new int[0]);
  }
  //----------------------------------------------------------------------------
  /**
   * Extends column references which have a reference to the submitted column index 
   * of a text table cell except references with the given row indices.
   * 
   * @param columnIndex column index of a text table cell
   * @param columnCount column index difference to be used
   * @param exceptRows references to rows that are ignored
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Markus Krüger
   */
  public void extendColumnReferences(int columnIndex, int columnCount, int[] exceptRows) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsColumn(columnIndex)) {
          boolean except = false;
          for(int j = 0; j < exceptRows.length; j++) {
            if(textTableCellReference.containsRow(exceptRows[j])) {
              except = true;
              break;
            }
          }
          if(!except)
            textTableCellReference.extendColumnRange(columnCount);        
        }
      }
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Extends row references which have a reference to the row index of the submitted name 
   * of a text table cell.
   * 
   * @param cellName name of a text table cell
   * @param rowCount row index difference to be used
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Andreas Bröker
   */
  public void extendRowReferences(String cellName, int rowCount) throws TextException {   
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsRow(cellName)) {
          textTableCellReference.extendRowRange(rowCount);        
        }
      }
    }    
  }
  //----------------------------------------------------------------------------  
  /**
   * Extends row references which have a reference to the submitted row index
   * of a text table cell.
   * 
   * @param rowIndex row index of a text table cell
   * @param rowCount row index difference to be used
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Markus Krüger
   */
  public void extendRowReferences(int rowIndex, int rowCount) throws TextException {   
    extendRowReferences(rowIndex, rowCount, new int[0]);   
  }
  //----------------------------------------------------------------------------    
  /**
   * Extends row references which have a reference to the submitted row index
   * of a text table cell except references with the given column indices.
   * 
   * @param rowIndex row index of a text table cell
   * @param rowCount row index difference to be used
   * @param exceptCols references to columns that are ignored
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Markus Krüger
   */
  public void extendRowReferences(int rowIndex, int rowCount, int[] exceptCols) throws TextException {   
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsRow(rowIndex)) {
          boolean except = false;
          for(int j = 0; j < exceptCols.length; j++) {
            if(textTableCellReference.containsColumn(exceptCols[j])) {
              except = true;
              break;
            }
          }
          if(!except)
            textTableCellReference.extendRowRange(rowCount);        
        }
      }
    }    
  }
  //----------------------------------------------------------------------------  
  /**
   * Extends row references which have a reference to the submitted row index
   * of a text table cell.
   * 
   * @param rowIndex row index of a text table cell
   * @param rowIndexTo the index of the row where the extension has to end
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Markus Krüger
   */
  public void extendRowReferencesTo(int rowIndex, int rowIndexTo) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsRow(rowIndex)) {
          textTableCellReference.extendRowRangeTo(rowIndexTo);        
        }
      }
    }  
  }
  //----------------------------------------------------------------------------   
  /**
   * Extends row references which have a reference to the submitted row index
   * of a text table cell.
   * 
   * @param rowIndex row index of a text table cell
   * @param rowIndicesTo the indices of the rows that the extension has to contain
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Markus Krüger
   */
  public void extendRowReferences(int rowIndex, int[] rowIndicesTo) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i = 0; i < textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        //TODO only allows this kind of modification is the reference to be modified is not a range
        if(textTableCellReference.containsRow(rowIndex) && !textTableCellReference.isRangeReference()) {
          String startColumnCharacter = textTableCellReference.getStartColumnCharacter();
          TextTableCellReference[] tmpTextTableCellReferences = new TextTableCellReference[rowIndicesTo.length];
          for(int j = 0; j < rowIndicesTo.length; j++) {
            tmpTextTableCellReferences[j] = new TextTableCellReference(startColumnCharacter + (rowIndicesTo[j]+1));
          }
          textTableFormulaModel.replaceCellReference(textTableCellReference, tmpTextTableCellReferences);
        }
      }
    } 
  }
  //----------------------------------------------------------------------------  
  /**
   * Reverts the model to the state before replacing cell references with method extendRowReferences().
   * 
   * @author Markus Krüger
   */
  public void revertModelToOriginal() {
    textTableFormulaModel.revertToOriginal();
  }
  //---------------------------------------------------------------------------- 
  /**
   * Extends column and row references which have a reference to the submitted name of a text
   * table cell. 
   * 
   * @param cellName name of a text table cell
   * @param columnCount column index difference to be used
   * @param rowCount row index difference to be used
   * 
   * @throws TextException if the text table cell references can not be extended
   * 
   * @author Andreas Bröker
   */
  public void extendColumnAndRowReferences(String cellName, int columnCount, int rowCount) throws TextException {  
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(!textTableCellReference.isModified()) {
        if(textTableCellReference.containsCell(cellName)) {
          textTableCellReference.extendColumnRange(columnCount);
          textTableCellReference.extendRowRange(rowCount);
        }
      }
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Modifies all cell references on the basis of the submitted table expansion informations.
   * 
   * @param newColumnsStartIndex start index of the new columns 
   * @param newColumnsCount number of new columns
   * @param newRowsStartIndex start index of the new rows
   * @param newRowsCount number of new rows
   * 
   * @throws TextException if the text table cell references can not be modified
   * 
   * @author Andreas Bröker
   */
  public void modifyCellReferences(int newColumnsStartIndex, int newColumnsCount, int newRowsStartIndex, int newRowsCount) throws TextException {
    TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
    for(int i=0; i<textTableCellReferences.length; i++) {
      TextTableCellReference textTableCellReference = textTableCellReferences[i];
      if(textTableCellReference.containsColumn(newColumnsStartIndex -1)) {
        textTableCellReference.extendColumnRange(newColumnsCount);
      }
      else if(textTableCellReference.containsColumnAfter(newColumnsStartIndex -1)) {
        textTableCellReference.moveColumnIndex(newColumnsCount);
      }
      
      if(textTableCellReference.containsRow(newRowsStartIndex -1)) {
        textTableCellReference.extendRowRange(newRowsCount);
      }
      else if(textTableCellReference.containsRowAfter(newRowsStartIndex -1)) {
        textTableCellReference.moveRowIndex(newRowsCount);
      }
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Applies cell reference modifications to the text table cell formula.
   * 
   * @throws TextException if any error occurs.
   * 
   * @author Andreas Bröker
   */
  public void applyModifications() throws TextException {
    textTableFormulaExpression.setExpression(textTableFormulaModel.getExpression());
  }
  //----------------------------------------------------------------------------
}