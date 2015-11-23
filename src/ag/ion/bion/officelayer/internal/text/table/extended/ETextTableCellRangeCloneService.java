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
package ag.ion.bion.officelayer.internal.text.table.extended;

import ag.ion.bion.officelayer.beans.PropertyKeysContainer;
import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ClonedObject;
import ag.ion.bion.officelayer.clone.DestinationPosition;
import ag.ion.bion.officelayer.clone.IClonedObject;
import ag.ion.bion.officelayer.clone.IDestinationPosition;

import ag.ion.bion.officelayer.internal.clone.AbstractCloneService;
import ag.ion.bion.officelayer.internal.text.table.TextTableCellRangeName;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellRange;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.ITextTableCellRangeName;

import ag.ion.bion.officelayer.text.table.extended.IETextTableCellRange;

/**
 * Implementation for extended text table cell ranges.
 * 
 * @author Miriam Sutter
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class ETextTableCellRangeCloneService extends AbstractCloneService {

	private ETextTableCellRange textTableCellRange 	= null;
	private ITextDocument				textDocument				= null;
	
	//----------------------------------------------------------------------------	
	/**
	 * Constructs the extended text table clone service.
	 * 
	 * @param textTableCellRange the extended text table cell range to be cloned
	 * 
	 * @author Miriam Sutter
	 */
	public ETextTableCellRangeCloneService(IETextTableCellRange textTableCellRange, ITextDocument textDocument) throws CloneException{
		if(textTableCellRange == null) {
			throw new CloneException("The submitted range is not valid");
		}
		if(!(textTableCellRange instanceof ETextTableCellRange)) {
			throw new CloneException("The submitted range is not valid");
		}
		if(textDocument == null) {
			throw new CloneException("The submitted text document is not valid");
		}
		this.textTableCellRange = (ETextTableCellRange)textTableCellRange;
		this.textDocument = textDocument;
	}
  //----------------------------------------------------------------------------
	/**
	 * Clones the chosen object to the given position and then returns
	 * a reference 
	 * 
	 * @param append this variable indicates if the clone should be appended or not
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @return a reference to the newly cloned element
	 * 
	 * @author Miriam Sutter
	 */
	public IClonedObject cloneToPosition(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return cloneToPosition(position,true,propertyKeysContainer);
	}
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position.
   * 
   * @param position the positions the object is to be cloned to
   * 
   * @throws CloneException if the object could not be cloned.
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @author Markus Krüger
   */
  public void cloneToPositionNoReturn(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    cloneToPositionNoReturn(position, true,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
	/**
	 * Clones the chosen object to the given position and then returns
	 * a reference This method also  enables to  adopts the content of
	 * the object (the default is to adopt, otherwise the paramter has
	 * to be set to false) 
	 * 
	 * @param append this variable indicates if the clone shiuld be appended or not
	 * 
	 * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @return a reference to the newly cloned element
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Miriam Sutter
	 * @author Markus Krüger
	 */
	public IClonedObject cloneToPosition(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return clonePreprocessor(position,adoptContent, true,propertyKeysContainer);
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
    try {
      ITextTableCellRange[] textTableCellRanges = textTableCellRange.getRanges();
      ITextTableCellRange[] range = new ITextTableCellRange[textTableCellRanges.length];
      range[0] = (ITextTableCellRange)textTableCellRanges[0].getCloneService().cloneToPosition(position,adoptContent,propertyKeysContainer).getClonedObject();
      ITextTable table = range[0].getCell(0,0).getTextTable();
      ETextTable eTextTable = new ETextTable(textDocument,table);
      for(int i = 1; i < textTableCellRanges.length; i++) {
        IDestinationPosition destinationPosition = new DestinationPosition(table);
        range[i] = (ITextTableCellRange)textTableCellRanges[i].getCloneService().cloneToPosition(destinationPosition,propertyKeysContainer).getClonedObject();
        table = range[i].getCell(0,0).getTextTable();
        eTextTable.addTable(table);
      }
      if(generateReturnValue) {
        ITextTableCellRangeName textTableCellRangeName = new TextTableCellRangeName(0,0,range[0].getColumnCount()-1,range[0].getRowCount());
        IETextTableCellRange cellRange = new ETextTableCellRange(range,textTableCellRangeName,eTextTable); 
        return new ClonedObject(cellRange,cellRange.getClass());
      }
      else
        return null;
    }
    catch(TextException textException) {
      CloneException cloneException = new CloneException("Error while cloning table");
      cloneException.initCause(textException);
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
