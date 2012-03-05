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

import ag.ion.bion.officelayer.beans.PropertyKeysContainer;
import ag.ion.bion.officelayer.clone.CloneDestinationPosition;
import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ClonedObject;
import ag.ion.bion.officelayer.clone.ICloneService;
import ag.ion.bion.officelayer.clone.IClonedObject;
import ag.ion.bion.officelayer.clone.IDestinationPosition;

import ag.ion.bion.officelayer.text.ITextContentService;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellName;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.ITextTableService;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;
import ag.ion.bion.officelayer.text.table.ITextTablePropertyStore;

import ag.ion.bion.officelayer.internal.clone.AbstractCloneService;
import ag.ion.bion.officelayer.internal.text.TextTableCell;
import ag.ion.bion.officelayer.internal.text.TextTableCellRange;
import ag.ion.bion.officelayer.internal.text.TextTableColumn;
import ag.ion.bion.officelayer.internal.text.TextTableRow;
import ag.ion.bion.officelayer.internal.text.TextTableService;

/**
 * Implementation of the ICloneService for text table ranges.
 * 
 * @author Miriam Sutter
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableCellRangeCloneService extends AbstractCloneService {
	
	private ITextTableCellRange     textTableCellRange 		  = null;
	private ITextDocument 			    textDocument					  = null;
	private ICloneService 			    cellCloneServices[][]   = null;
  private ITextTablePropertyStore textTablePropertyStore  = null;
  private ITextTableService       textTableService        = null;
  private ITextContentService     textContentService      = null;
  private ITextTable              oldTable                = null;
  private int                     rowCount                = 0;
  private int                     columnCount             = 0;
	
  //----------------------------------------------------------------------------	
	/**
	 * Constructs the TextTableCellRangeCloneService
	 * 
	 * @param textTableCellRange the range to clone
	 * 
	 * @author Miriam Sutter
	 */
	public TextTableCellRangeCloneService (ITextTableCellRange textTableCellRange) throws CloneException {		
		this.textDocument = textTableCellRange.getTextDocument();
    this.textTableCellRange = textTableCellRange; 
    rowCount = textTableCellRange.getRowCount();
    columnCount = textTableCellRange.getColumnCount();
	}
  //----------------------------------------------------------------------------
	/**
	 * Clones the table to the given position and then returns
	 * a reference. If the position is null, then the clone is 
	 * placed at the end of the document.
	 * 
	 * @param position the position the clone should be placed at
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @return a reference to the newly cloned element
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Miriam Sutter
	 */
	public IClonedObject cloneToPosition(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return clonePreprocessor(position, true, true,propertyKeysContainer);
	}
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position.
   * 
   * @param position the positions the object is to be cloned to
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneToPositionNoReturn(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    clonePreprocessor(position, true, false,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
	/**
	 * Clones the chosen table to the given position and then returns
	 * a reference This method also  enables to  adopts the content of
	 * the table (the default is to adopt, otherwise the paramter has
	 * to be set to false) 
	 * 
	 * @param position the position the clone should be placed at
	 * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @return a reference to the newly cloned element
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Miriam Sutter
	 */
	public IClonedObject cloneToPosition(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return clonePreprocessor(position, adoptContent, true,propertyKeysContainer);
	}
	 //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position.
   * This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) 
   * 
   * @param position the positions the object is to be cloned to
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneToPositionNoReturn(IDestinationPosition position , boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    clonePreprocessor(position, adoptContent, false,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
	/**
	 * This is the real method to clone the table.
	 * 
   * @param position the position to clone to 
   * @param adoptContent if the content should be adapted or not
   * @param generateReturnValue indicates weahter the return value will be generate or be null
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
	 * @return the new cloned object
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Miriam Sutter
   * @author Markus Krüger
	 */
	private IClonedObject clonePreprocessor (IDestinationPosition position, boolean adoptContent, boolean generateReturnValue, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		try {
      if(cellCloneServices == null)
        getCloneCells();
			ITextTable newTable = null;
      if(oldTable == null)
        oldTable = textTableCellRange.getCell(0,0).getTextTable();
      if(textTablePropertyStore == null) {
  			textTablePropertyStore = oldTable.getPropertyStore();
      }
			int columnStart = 0;
			int rowStart = 0;
			if(cellCloneServices.length == 0 ) {
				CloneException cloneException =  new CloneException("No range selected.");
				throw cloneException;
			}
			int columnArrayLength = cellCloneServices[0].length;
      
      if(position.getType() == null) {
        CloneException cloneException =  new CloneException("No valid type for the position.");
        throw cloneException;
      }
      
			if(ITextRange.class.isAssignableFrom(position.getType())) {
				ITextRange range = (ITextRange)position.getDestinationObject();
				if(textTableService == null)
				  textTableService = textDocument.getTextTableService();
				newTable = (ITextTable)textTableService.constructTextTable(rowCount, columnCount);
				newTable.getProperties().setRepeatHeadline(textTablePropertyStore.repeatHeadline());
        if(textContentService == null)
          textContentService = textDocument.getTextService().getTextContentService();
				if (range != null) {
          textContentService.insertTextContent(range,newTable);
          newTable.getProperties().setTableColumnSeparators(textTablePropertyStore.getTableColumnSeparators());
				}
				else {
					CloneException cloneException =  new CloneException("No destination selected..");
					throw cloneException;
				}
			}
			else if(ITextTable.class.isAssignableFrom(position.getType())) {
				ITextTable range = (ITextTable)position.getDestinationObject();
				TextTableService tableService = new TextTableService(textDocument);

				newTable = (ITextTable)tableService.constructTextTable(rowCount, columnCount);
				newTable.getProperties().setRepeatHeadline(textTablePropertyStore.repeatHeadline());
				
				if (range != null) {
          if(textContentService == null)
            textContentService = textDocument.getTextService().getTextContentService();
          textContentService.insertTextContentAfter(newTable,range);
					newTable.getProperties().setTableColumnSeparators(textTablePropertyStore.getTableColumnSeparators());
				}
				else {
					CloneException cloneException =  new CloneException("No destination selected..");
					throw cloneException;
				}
			}
			else if(TextTableCell.class.isAssignableFrom(position.getType())) {
        TextTableCell textTableCell = ((TextTableCell)position.getDestinationObject());
				newTable = textTableCell.getTextTable();
        ITextTableCellName cellName = textTableCell.getName();
				columnStart = cellName.getColumnIndex();
				rowStart = cellName.getRowIndex();
        int newTableColumnCount = newTable.getColumnCount();
        int newTableRowCount = newTable.getRowCount();
				if(columnStart + columnArrayLength > newTableColumnCount) {
					CloneException cloneException =  new CloneException("Too much columns selected.");
					throw cloneException;
				}
				if((rowStart + rowCount)*newTableColumnCount > ITextTable.MAX_CELLS_IN_TABLE) {
					CloneException cloneException =  new CloneException("The sumbitted range is too big.");
					throw cloneException;
				}
				if(rowStart + rowCount > newTableRowCount) {
					newTable.addRow(rowStart, (rowStart + rowCount)-newTableRowCount);
				}
			}
			else if(TextTableCellRange.class.isAssignableFrom(position.getType())) {
				TextTableCellRange tableCellRange = ((TextTableCellRange)position.getDestinationObject());
				int tmpColumnCount = tableCellRange.getColumnCount();
        int tmpRowCount = tableCellRange.getRowCount();
        if(tmpColumnCount == 0 || tmpRowCount==0) {
					CloneException cloneException =  new CloneException("No range selected.");
					throw cloneException;
				}
				newTable = tableCellRange.getCell(0,0).getTextTable();;
        ITextTableCellRangeName rangeName = tableCellRange.getRangeName();
				columnStart = rangeName.getRangeStartColumnIndex();
				rowStart = rangeName.getRangeStartRowIndex();
				if(tmpColumnCount != columnCount || tmpRowCount != rowCount) {
					CloneException cloneException =  new CloneException("The selected range is not valid.");
					throw cloneException;
				}
			}
      else if(TextTableRow.class.isAssignableFrom(position.getType())) {
        TextTableRow textTableRow = ((TextTableRow)position.getDestinationObject());        
        ITextTableCellRange tableCellRange = textTableRow.getCellRange();
        int tmpColumnCount = tableCellRange.getColumnCount();
        int tmpRowCount = tableCellRange.getRowCount();
        if(tmpColumnCount == 0 || tmpRowCount==0) {
          CloneException cloneException =  new CloneException("No range selected.");
          throw cloneException;
        }
        newTable = tableCellRange.getCell(0,0).getTextTable();;
        ITextTableCellRangeName rangeName = tableCellRange.getRangeName();
        columnStart = rangeName.getRangeStartColumnIndex();
        rowStart = rangeName.getRangeStartRowIndex();
        if(tmpColumnCount != columnCount || tmpRowCount != rowCount) {
          CloneException cloneException =  new CloneException("The selected range is not valid.");
          throw cloneException;
        }
      }
      else if(TextTableColumn.class.isAssignableFrom(position.getType())) {
        //TODO fill with logic
      }
			else {
				CloneException cloneException =  new CloneException("No range selected.");
				throw cloneException;
			}
			if(newTable != null) {
				int rowArrayLength = cellCloneServices.length;
				for (int rows = 0; rows < rowArrayLength; rows++) {
					for (int columns = 0; columns < columnArrayLength ; columns++) {
						ICloneService cellClone = cellCloneServices[rows][columns];
            ITextTableCell textTableCell = newTable.getCell(columnStart + columns, rowStart + rows);
						CloneDestinationPosition destinationCell = new CloneDestinationPosition(textTableCell, textTableCell.getClass());
						cellClone.cloneToPositionNoReturn(destinationCell, adoptContent,propertyKeysContainer);
					}
				}
        if(generateReturnValue) {
          ITextTableCellRange textTableCellRange = newTable.getCellRange(columnStart,rowStart,columnArrayLength,rowArrayLength);
          return new ClonedObject(textTableCellRange, textTableCellRange.getClass());
        }
        else
          return null;
			}
			else {
				CloneException cloneException =  new CloneException("No range selected.");
				throw cloneException;
			}
		} 
		catch (Exception exception) {
			CloneException cloneException =  new CloneException(exception.getMessage());
			cloneException.initCause(exception);
			throw cloneException;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Gets the properties form the table and the cells and then 
	 * stores them in the apropriate objects designed for them.
   * 
   * @throws CloneException if anything fails
	 * 
	 * @author Miriam Sutter
   * @author Markus Krüger
	 */
	private void getCloneCells() throws CloneException {    
		cellCloneServices = new ICloneService[rowCount][columnCount];		
		for (int i=0;i<rowCount;i++) {
			for (int h=0;h<columnCount;h++){
				try {
					ITextTableCell currentTableCell = textTableCellRange.getCell(h,i);
					cellCloneServices[i][h] = currentTableCell.getCloneService();
				}
				catch(TextException excep) {
					CloneException cloneException =  new CloneException(excep.getMessage());
					cloneException.initCause(excep);
					throw cloneException;

				}
			}
		}
	}
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position and then returns
   * a reference 
   * Between the given position and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   * This method always adopts the content
   * 
   * @param append this variable indicates if the clone shiuld be appended or not
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @return a reference to the newly cloned element
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Sebastian Rösgen
   */
  public IClonedObject cloneAfterThisPosition(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    return cloneToPosition(position, true,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position.
   * Between the given position and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   * This method always adopts the content
   * 
   * @param position the position the object is to be cloned after
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneAfterThisPositionNoReturn(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    cloneToPositionNoReturn(position, true,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object after the given position and then returns
   * a reference. Between the given position and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   * This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) 
   * 
   * @param append this variable indicates if the clone shiuld be appended or not
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @return a reference to the newly cloned element
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Sebastian Rösgen
   */  
  public IClonedObject cloneAfterThisPosition(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    return cloneToPosition(position, adoptContent,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object after the given position. 
   * Between the given position and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   *  This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) 
   * 
   * @param position the position the object is to be cloned after
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */  
  public void cloneAfterThisPositionNoReturn(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    cloneToPositionNoReturn(position, adoptContent,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
 
}