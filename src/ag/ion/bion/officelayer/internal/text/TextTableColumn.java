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
 * Last changes made by $Author: markus $, $Date: 2007-08-03 14:15:30 +0200 (Fr, 03 Aug 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ICloneService;
import ag.ion.bion.officelayer.internal.text.table.TextTableColumnCloneService;

import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.ITextTableColumn;
import ag.ion.bion.officelayer.text.ITextTableProperties;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

/**
 * Column of a text table.
 * 
 * @author Markus Krüger
 * @version $Revision: 11553 $
 */
public class TextTableColumn implements ITextTableColumn {
  
  private static final int MIN_COLUMN_WIDTH = 50; // !!! Do not change to a lower value !!!
  
  private ITextTableCellRange	textTableCellRange	= null;
  private ITextTable    			textTable    				= null;
  
  private int 								index 			 				= 0;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableRow.
   * 
   * @param textTable table of the text document
   * @param index index of a column
   * 
   * @throws IllegalArgumentException if one the OpenOffice.org interfaces is not valid
   * @throws TextException
   * 
   * @author Markus Krüger 
   */
  public TextTableColumn(ITextTable textTable, int index) throws IllegalArgumentException, TextException {
    if(textTable == null)
      throw new IllegalArgumentException("Submitted textTable is not valid.");
    this.textTable = textTable;
    
    if(index < 0)
      throw new IllegalArgumentException("Submitted index is not valid.");
    
    String[] cellNames = textTable.getXTextTable().getCellNames();
    String oldCellName = null;
    String cellRange = null;
    for(int i = 0; i < cellNames.length; i++) {
      if(TextTableCellNameHelper.getColumnIndex(cellNames[i]) == index) {
        if(cellRange == null)
          cellRange = cellNames[i];
        oldCellName = cellNames[i];
      }
    }
    if(!cellRange.equals(oldCellName))
      cellRange = cellRange + ":" + oldCellName;
    this.textTableCellRange = textTable.getCellRange(cellRange);
    this.index = index;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets column width.
   * 
   * @param width desired width of the column
   * 
   * @throws TextException if necessary properties are not available
   * 
   * @author Markus Krüger
   */
  public void setWidth(short width) throws TextException {
    //get text table properties
    ITextTableProperties textTableProperties = textTable.getProperties();  
    
    //get column separators
    TextTableColumnsSeparator[] textTableColumnsSeparators = textTableProperties.getTableColumnSeparators();
    
    int columnsSeparatorsLength = textTableColumnsSeparators.length;
    long textTableWidth = textTableProperties.getWidth();
    
    //only if there is more than one column
    if(columnsSeparatorsLength > 0 && index <= columnsSeparatorsLength) {
	    //sets separator if index is 0
	    if(index == 0) {
	      //for a table with only two columns
	      if(columnsSeparatorsLength == 1) {      
	        //set new position only if the width does not exceed table width
	        if(width < textTableWidth - MIN_COLUMN_WIDTH) {
	          //set new position of separator
	          textTableColumnsSeparators[index].setPosition(width);
	        }
	        else {
	          //set new position of separator to table width
	          textTableColumnsSeparators[index].setPosition( (short)(textTableWidth - MIN_COLUMN_WIDTH) );
	        }
	      }
	      //for a table with at least three columns
	      else {
	        //set new position only if the width does not exceed the position of the next column separator
	        if(width < textTableColumnsSeparators[index+1].getPosition() - MIN_COLUMN_WIDTH) {
	          //set new position of separator
	          textTableColumnsSeparators[index].setPosition(width);
	        }
	        else {
	          //set new position of separator right next to the position of the next column separator
	          textTableColumnsSeparators[index].setPosition( (short)(textTableColumnsSeparators[index+1].getPosition() - MIN_COLUMN_WIDTH) );
	        }
	      }	          
	    }
	    //sets separator the column is not the first one or the last one
	    else if(index < columnsSeparatorsLength) {
	      //if the column is the last but one
	      if(index == columnsSeparatorsLength-1) {      
	        //set new position only if the width does not exceed table width
	        if(textTableColumnsSeparators[index-1].getPosition() + width < textTableWidth - MIN_COLUMN_WIDTH) {
	          //set new position of separator
	          textTableColumnsSeparators[index].setPosition( (short) (textTableColumnsSeparators[index-1].getPosition() + width ) );
	        }
	        else {
	          //set new position of separator to table width
	          textTableColumnsSeparators[index].setPosition( (short)(textTableWidth - MIN_COLUMN_WIDTH) );
	        }
	      }
	      //if the column is not the last but one
	      else {
	        //set new position only if the width does not exceed the position of the next column separator
	        if(textTableColumnsSeparators[index-1].getPosition() + width < textTableColumnsSeparators[index+1].getPosition() - MIN_COLUMN_WIDTH) {
	          //set new position of separator
	          textTableColumnsSeparators[index].setPosition( (short) (textTableColumnsSeparators[index-1].getPosition() + width ) );
	        }
	        else {
	          //set new position of separator right next to the position of the next column separator
	          textTableColumnsSeparators[index].setPosition( (short)(textTableColumnsSeparators[index+1].getPosition() - MIN_COLUMN_WIDTH) );
	        }
	      }
	    }
	    //sets the last column width
	    else {
	      //if there are only two columns
	      if(columnsSeparatorsLength == 1) {
	        //new position is not smaller than 0
	        if(textTableWidth - width > MIN_COLUMN_WIDTH) {
		        //set new position of the only separator
	          textTableColumnsSeparators[index-1].setPosition( (short) (textTableWidth - width) );
	        }
	        //new position is smaller than MIN_COLUMN_WIDTH, then set it to MIN_COLUMN_WIDTH
	        else {
		        //set new position of the only separator
	          textTableColumnsSeparators[index-1].setPosition( (short) MIN_COLUMN_WIDTH);
	        }
	      }
	      //if there are more than two columns
	      else {
	        //new position is not smaller than the position of the last separator
	        if(textTableWidth - width > textTableColumnsSeparators[index-2].getPosition() + MIN_COLUMN_WIDTH) {
		        //set new position of the separator
	          textTableColumnsSeparators[index-1].setPosition( (short) (textTableWidth - width) );
	        }
	        //new position is smaller than the position of the last separator, then set it right next to it
	        else {
		        //set new position of the separator
	          textTableColumnsSeparators[index-1].setPosition( (short) (textTableColumnsSeparators[index-2].getPosition() + MIN_COLUMN_WIDTH) );
	        }
	      }
	    }	    
    }
    
    //sets new separators
    textTableProperties.setTableColumnSeparators(textTableColumnsSeparators);
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns column width.
   * 
   * @throws TextException if necessary properties are not available
   * 
   * @return column width
   * 
   * @author Markus Krüger
   */
  public short getWidth() throws TextException {
    //get text table properties
    ITextTableProperties textTableProperties = textTable.getProperties();    
    //get column separators
    TextTableColumnsSeparator[] textTableColumnsSeparators = textTableProperties.getTableColumnSeparators();
    
    int columnsSeparatorsLength = textTableColumnsSeparators.length;
    
    if(index <= columnsSeparatorsLength) {
	    //return column width
	    if(index == 0)
	      //for the first column the position of the first separator is the width
	      return textTableColumnsSeparators[index].getPosition();
	    else if(index < columnsSeparatorsLength) 
	      //for columns between the first and the lastone the width is the position of the next separator minus the 
	      //position of the actual column separator
	      return (short) (textTableColumnsSeparators[index].getPosition()-textTableColumnsSeparators[index-1].getPosition());
	    else 
	      //for the last column the distance between last separator and tabelend is the width
	      return (short) (textTableProperties.getWidth()-textTableColumnsSeparators[index-1].getPosition());
    }
    return 0;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the text table cell range.
   * 
   * @return text table cell range
   * 
   * @author Miriam Sutter
   */
  public ITextTableCellRange getCellRange() {
  	return textTableCellRange;
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the clone service of the element.
   * 
   * @return the clone service
   * 
   * @throws CloneException if the clone service could not be returned
   * 
   * @author Markus Krüger
   */
  public ICloneService getCloneService() throws CloneException {
		return new TextTableColumnCloneService(this);
	}
  //----------------------------------------------------------------------------
}