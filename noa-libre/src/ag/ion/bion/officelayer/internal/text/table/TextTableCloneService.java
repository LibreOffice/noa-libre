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
import ag.ion.bion.officelayer.clone.CloneDestinationPosition;
import ag.ion.bion.officelayer.clone.ICloneService;
import ag.ion.bion.officelayer.clone.IClonedObject;
import ag.ion.bion.officelayer.clone.IDestinationPosition;

import ag.ion.bion.officelayer.internal.clone.AbstractCloneService;
import ag.ion.bion.officelayer.internal.text.TextTableProperties;

import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.ITextContentService;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableProperties;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.ITextTablePropertyStore;

import com.sun.star.lang.XMultiServiceFactory;

import com.sun.star.text.XTextDocument;

/**
 * Implementation of the ICloneService for text tables.
 * 
 * @author Sebastian Rösgen
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableCloneService extends AbstractCloneService {
	
	private ITextTable     textTable    = null;
	private XTextDocument  textDocument = null;
	
	private XMultiServiceFactory serviceFactory = null;
	private ICloneService cellCloneServices[][] = null;
	private ITextTablePropertyStore tablePropertyStore = null;
	
  //----------------------------------------------------------------------------	
	/**
	 * Constructs the TextTableCloneService
	 * 
	 * @param table the table to be cloned
   * @param textDocument text document to be used
   * 
   * @throws CloneException TODO: add comment
	 */
	public TextTableCloneService (ITextTable table, XTextDocument textDocument) throws CloneException {
		this.textTable = table;
		this.textDocument = textDocument;
		this.serviceFactory = (XMultiServiceFactory)com.sun.star.uno.UnoRuntime.queryInterface(XMultiServiceFactory.class, textDocument);
		getTableInfo(table);
	}
  //----------------------------------------------------------------------------
	/**
	 * Clones the table to the given position and then returns
	 * a reference. If the position is null, then the clone is 
	 * placed at the end of the document.
	 * 
	 * @param append this variable indicates if the clone shiuld be appended or not
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @return a reference to the newly cloned element
	 */
	public IClonedObject cloneToPosition(IDestinationPosition range, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return clonePreprocessor(range, true, false, true,propertyKeysContainer);
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
    clonePreprocessor(position, true, false, false,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
	/**
	 * Clones the chosen table to the given position and then returns
	 * a reference This method also  enables to  adopts the content of
	 * the table (the default is to adopt, otherwise the paramter has
	 * to be set to false) 
	 * 
	 * @param append this variable indicates if the clone should be appended or not
	 * 
	 * @param adopContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
	 * 
	 * @return a reference to the newly cloned element
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Sebastian Rösgen
	 */
	public IClonedObject cloneToPosition(IDestinationPosition range, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		return clonePreprocessor(range, adoptContent, false, true,propertyKeysContainer);
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
    clonePreprocessor(position, adoptContent, false, false,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
	/**
	 * This is the real method to clone the table.
	 * 
   * @param position the position to append to
   * @param adoptContent inidicates if all content and properties should 
   *        be cloned, too
   * @param createSpace indicates if a new paragraph should be constructed 
   *        to create some space between the old and the new textElement  
   * @param generateReturnValue indicates weahter the return value will be generate or be null
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
	 * @return the new cloned object
	 * 
	 * @throws CloneException if the object could not be cloned.
	 * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger
	 */
	private IClonedObject clonePreprocessor (IDestinationPosition position, boolean adoptContent, boolean createSpace, boolean generateReturnValue, PropertyKeysContainer propertyKeysContainer) throws CloneException {
		try {
			ITextRange range = null;
			if (position.getType() != null && ITextRange.class.isAssignableFrom(position.getType())) {
				range = (ITextRange)position.getDestinationObject();
			}
			ITextDocument textDocument = textTable.getTextDocument();
      ITextContentService textContentService = textDocument.getTextService().getTextContentService();
			ITextTable newTable = textDocument.getTextTableService().constructTextTable(tablePropertyStore.getRows(), tablePropertyStore.getColumns());
			      
  		if (range != null) {
        textContentService.insertTextContent(range,newTable);
  		}
  		else {
        textContentService.insertTextContentAfter(newTable,textTable);
 			}
      
      String[] propertyKeysToCopy = null;      
      if(propertyKeysContainer != null) {
        propertyKeysToCopy = propertyKeysContainer.getPropertyKeys(ITextTableProperties.TYPE_ID);        
      }
      else {
        //use default
        propertyKeysToCopy = TextTableProperties.getDefaultPropertyKeys();
      }
      if(propertyKeysToCopy != null) {
        ITextTableProperties newTableProperties = newTable.getProperties();
        ((IPropertyStore)tablePropertyStore).getProperties().copyTo(propertyKeysToCopy, newTableProperties);
      }
			
			int rowArrayLength = cellCloneServices.length;
			for (int rows = 0; rows < rowArrayLength; rows++) {
        //set row height
        newTable.getRow(rows).setHeight(textTable.getRow(rows).getHeight());
        newTable.getRow(rows).setAutoHeight(textTable.getRow(rows).getAutoHeight());
        
				int columnArrayLength = cellCloneServices[rows].length;
				for (int columns = 0; columns < columnArrayLength ; columns++) {
					ICloneService cellClone = cellCloneServices[rows][columns];
          ITextTableCell textTableCell = newTable.getCell(columns, rows);
					CloneDestinationPosition destinationCell = new CloneDestinationPosition(textTableCell, textTableCell.getClass());
					cellClone.cloneToPositionNoReturn(destinationCell, adoptContent,propertyKeysContainer);		            
				}
			}
      
      if (createSpace) {
        IParagraph paragraph = textContentService.constructNewParagraph();
        textContentService.insertTextContentBefore(paragraph,newTable);
      }
      if(generateReturnValue)
        return new ClonedObject(newTable, newTable.getClass());
      else
        return null;
		} 
		catch (Exception exception) {
			CloneException cloneException = new CloneException(exception.getMessage());
			cloneException.initCause(exception);
			throw cloneException;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Gets the properties form the table and the cells and then 
	 * stores them in the apropriate objects designed for them.
	 * 
	 * @param textTable the table that should be analysed
	 * 
	 * @throws CloneException if any error occurs
	 * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger
	 */
	private void getTableInfo(ITextTable textTable) throws CloneException {
		// lets get all cells and see what's in 
		try {		
      int rowCount = textTable.getRowCount();
      int columnCount = textTable.getColumnCount();
			tablePropertyStore = new TextTablePropertyStore(textTable);
			cellCloneServices = new ICloneService[rowCount][columnCount];
			
			for (int i=0;i<rowCount;i++) {
				for (int h=0;h<columnCount;h++){	
					ITextTableCell currentTableCell = textTable.getCell(h,i);
					cellCloneServices[i][h] = currentTableCell.getCloneService();
				}
			}
		}
		catch(TextException exception) {
			CloneException cloneException =  new CloneException(exception.getMessage());
			cloneException.initCause(exception);
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
    return clonePreprocessor(position, true, true, true,propertyKeysContainer);
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
    clonePreprocessor(position, true, true, false,propertyKeysContainer);
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
    return clonePreprocessor(position, adoptContent, true, true,propertyKeysContainer);
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
    clonePreprocessor(position, adoptContent, true, false,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
 
}