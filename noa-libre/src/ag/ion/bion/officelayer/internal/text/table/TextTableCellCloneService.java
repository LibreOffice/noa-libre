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

import ag.ion.bion.officelayer.beans.IPropertyStore;
import ag.ion.bion.officelayer.beans.PropertyKeysContainer;
import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ClonedObject;
import ag.ion.bion.officelayer.clone.IClonedObject;
import ag.ion.bion.officelayer.clone.IDestinationPosition;

import ag.ion.bion.officelayer.internal.clone.AbstractCloneService;
import ag.ion.bion.officelayer.internal.text.CharacterProperties;
import ag.ion.bion.officelayer.internal.text.TextTable;
import ag.ion.bion.officelayer.internal.text.TextTableCellProperties;

import ag.ion.bion.officelayer.text.ICharacterProperties;
import ag.ion.bion.officelayer.text.ICharacterPropertyStore;
import ag.ion.bion.officelayer.text.IText;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellName;
import ag.ion.bion.officelayer.text.ITextTableCellProperties;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.text.table.IFormula;
import ag.ion.bion.officelayer.text.table.IFormulaService;
import ag.ion.bion.officelayer.text.table.ITextTableCellPropertyStore;

/**
 * Clone service for table cells.
 * 
 * @author Sebastian Rösgen
 * @author Markus Krüger 
 * @version $Revision: 10398 $
 */
public class TextTableCellCloneService extends AbstractCloneService {
	
	private String cellValue = null;
	private String cellFormula = null;
	
	private int verticalPosition = -1;
	private int horizontalPosition = -1;
	
  private ITextTableCell cellToClone = null;
	private ITextTableCellPropertyStore cellPropertyStore = null;
  private ICharacterPropertyStore     firstCellCharacterPropertyStore = null;
	private ITextTable parentTable = null;
	private String cellName = null; 
  private ITextTableCellName textTableCellName = null;
  
  private ITextTableCellRange textTableCellRange = null;
  private TextTableCellRangeCloneService rangeCloneService = null;
	
  //----------------------------------------------------------------------------	
	/** 
	 * Constructor of the TextTableCellCloneService this does nothing mmore tha 
	 * 
	 * @param cell the cell from which to build a clone service
	 * 
   * @throws CloneException if anything fails
   * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger 
	 */
	public TextTableCellCloneService (ITextTableCell cell) throws CloneException {
    cellToClone = cell;	
		analyseCell(cell);
	}
  //----------------------------------------------------------------------------
	/**
	 * Clones  the cell to the specified position, the boolean value indicates 
	 * if the value is set to true, the cell is appended to the specified position
	 * otherwise it is set before the position.
	 * 
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
	 * @throws CloneException if the object could not be cloned.
	 */
	public IClonedObject cloneToPosition(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return cloneToPosition(position, true,propertyKeysContainer);
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
    cloneToPositionNoReturn(position, true,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
	/**
	 * Clones the chosen cell to the given position and then returns
	 * a reference This method also  enables to  adopts the content of
	 * the cell (the default is to adopt, otherwise the paramter has
	 * to be set to false) 
	 * 
	 * @param adoptContent indicated if the content of the object should be adopted (this includes formulas)
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @return a reference to the newly cloned element
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger 
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
   * @author Markus Krüger
   */
  private IClonedObject clonePreprocessor (IDestinationPosition position, boolean adoptContent, boolean generateReturnValue, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    if (position.getType() != null && ITextTableCell.class.isAssignableFrom(position.getType())) {
       ITextTableCell cell = (ITextTableCell)position.getDestinationObject();
       return recreateCell(cell, adoptContent, generateReturnValue, propertyKeysContainer);   
    }
    else if (ITextRange.class.isAssignableFrom(position.getType())) {
      try {
       if(textTableCellRange == null) {
         if(parentTable == null)
           parentTable = cellToClone.getTextTable();
         if(textTableCellName == null)      
           textTableCellName = cellToClone.getName();
         if(cellName == null)
           cellName = textTableCellName.getName();
         textTableCellRange = parentTable.getCellRange(cellName+":" +cellName);
       }
       if(rangeCloneService == null)
         rangeCloneService =  new TextTableCellRangeCloneService(textTableCellRange);
       if(generateReturnValue)
         return rangeCloneService.cloneToPosition(position, adoptContent,propertyKeysContainer);
       else {
         rangeCloneService.cloneToPositionNoReturn(position, adoptContent,propertyKeysContainer);
         return null;         
       }
      }
      catch(TextException exception) {
        CloneException cloneException = new CloneException(exception.getMessage());
        cloneException.initCause(exception);
        throw cloneException;
      }
    }
    else {
      CloneException cloneException =  new CloneException("Error cloning cell. No target selected or another error occred.");
      throw cloneException;
    }
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns the stored value of the cell. This is done in the form of a Double object
	 * cause thus we got to know if we actually had any kind of value in the array, and 
	 * if we haven't we can return null instead of any value.
	 * 
	 * @return the cell value, or null if there is none
	 * 
	 * @author Sebastian Rösgen   
	 */
	public String getCellValue () {
		return cellValue;			
	}
  //----------------------------------------------------------------------------	
	/**
	 * Sets a new value for the cell in the store.
	 * 
	 * @param cellValue the value to be set
	 */
	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the formula placed in the store of this
	 * cell (if there is actually any).
	 * 
	 * @return the cell's formula
	 * 
	 * @author Sebastian Rösgen
	 */
	public String getCellFormula() {
		return cellFormula;
	}
  //----------------------------------------------------------------------------	
	/**
	 * Sets a new formula for this cell in the store.
	 * 
	 * @param cellFormula the new formula to be set.
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setCellFormula(String cellFormula) {
		this.cellFormula = cellFormula;
	}
  //----------------------------------------------------------------------------	
	/**
	 * Alayses the whole cell, stores its formula and value in the clone
	 * service and creates a new store object, which contains the important properties.
	 * 
	 * @param cell the cell which should be analysed
	 * 
   * @throws CloneException if the object could not be cloned.
   * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger
	 */
	private void analyseCell(ITextTableCell cell) throws CloneException {
    try {
      IText text = cell.getTextService().getText();
      ITextField fields[] = text.getTextContentEnumeration().getTextFields();
      String textCell = text.getText();        
      if(textCell != null) {
        //check for fields and replace their labels
        for(int i = 0; i < fields.length; i++) {
          String displayText = fields[i].getDisplayText();
          if(displayText != null) {
            displayText = displayText.replaceAll("\\[","\\\\["); //$NON-NLS-1$ //$NON-NLS-2$
            displayText = displayText.replaceAll("\\]","\\\\]"); //$NON-NLS-1$ //$NON-NLS-2$
            textCell = textCell.replaceAll(displayText,""); //$NON-NLS-1$
          }
        }
        if(textCell.length() > 0) {
          this.cellValue = textCell;
        }
      }
      if (fields.length == 0) { // since we do not clone fields
        
        //TODO : a cell with fields is ignored for deep cloning
        //       this leads to problems with fields containing mixed
        //       content fields and formulas, so we have to fix this
        
        IFormulaService formulaService = cell.getFormulaService();
        if(formulaService != null) {
          IFormula formula = formulaService.getFormula();
          if(formula != null) {
            this.cellFormula = formula.getExpression();
          }
        }
      }
      
    		
      cellPropertyStore = cell.getCellPropertyStore(0,0);
      firstCellCharacterPropertyStore = cell.getCharacterPropertyStore();
		}
		catch(TextException excep) {
			CloneException cloneException =  new CloneException(excep.getMessage());
			cloneException.initCause(excep);
			throw cloneException;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Uses the stored information to create a new cell from it
	 * 
   * @param table the text table
   * @param adoptContent adoptContent indicates if the content should be adopted to the new cell
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger
	 */
	private void getCellFromStore(TextTable table, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		try {
			if(textTableCellName == null)      
			  textTableCellName = cellToClone.getName();
      if(verticalPosition == -1)
        verticalPosition = textTableCellName.getColumnIndex();
      if(horizontalPosition == -1)
        horizontalPosition = textTableCellName.getRowIndex();      
      
      if (cellPropertyStore != null && verticalPosition != -1 &&  horizontalPosition != -1) {
				ITextTableCell tableCell = table.getCell(verticalPosition, horizontalPosition);
				recreateCell(tableCell, adoptContent, false, propertyKeysContainer);
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
	 * Recreate the given cell with the stored properties.
	 * 
	 * @param tableCell the cell to be cloned
	 * @param adoptContent indicates if the content should be adopted to the new cell (this includes formulas)
   * @param generateReturnValue indicates weahter the return value will be generate or be null
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
   * @return the cloned recreated object
   * 
	 * @throws CloneException if any error occurs 
	 * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger
	 */
	private IClonedObject recreateCell (ITextTableCell tableCell, boolean adoptContent, boolean generateReturnValue, PropertyKeysContainer propertyKeysContainer) throws CloneException{
		try {
			if (adoptContent) { // there could be text content to be set
        if (cellFormula != null) { // otherwise there could be a formula
          tableCell.getFormulaService().setFormula(cellFormula);
        }
        else if (cellValue != null) {
          IText cellText = tableCell.getTextService().getText();
          String cellTextString = cellText.getText();
          //no overwrite allowed
          if(cellTextString == null || cellTextString.length() == 0)
            cellText.setText(cellValue);
				}
			}
      
      //copy cell properties
      String[] propertyKeysToCopy = null;      
      if(propertyKeysContainer != null) {
        propertyKeysToCopy = propertyKeysContainer.getPropertyKeys(ITextTableCellProperties.TYPE_ID);        
      }
      else {
        //use default
        propertyKeysToCopy = TextTableCellProperties.getDefaultPropertyKeys();
      }
      if(propertyKeysToCopy != null) {
        ITextTableCellProperties newProperties = tableCell.getProperties();
        ((IPropertyStore)cellPropertyStore).getProperties().copyTo(propertyKeysToCopy, newProperties);
      }
      //copy character properties
      propertyKeysToCopy = null;      
      if(propertyKeysContainer != null) {
        propertyKeysToCopy = propertyKeysContainer.getPropertyKeys(ICharacterProperties.TYPE_ID);        
      }
      else {
        //use default
        propertyKeysToCopy = CharacterProperties.getDefaultPropertyKeys();
      }
      if(propertyKeysToCopy != null) {
        ICharacterProperties newProperties = tableCell.getCharacterProperties();
        ((IPropertyStore)firstCellCharacterPropertyStore).getProperties().copyTo(propertyKeysToCopy, newProperties);
      }      
      
			if(generateReturnValue)
			  return new ClonedObject(tableCell, tableCell.getClass());
      else
        return null;
		}
		catch(Exception excep) {
			CloneException cloneException =  new CloneException(excep.getMessage());
			cloneException.initCause(excep);
			throw cloneException;
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
   * @param position the destination position of the cloning
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
   * @param position the destination position of the cloning
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