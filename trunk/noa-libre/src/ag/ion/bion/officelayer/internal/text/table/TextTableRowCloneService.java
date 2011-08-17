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
import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ICloneService;
import ag.ion.bion.officelayer.clone.IClonedObject;
import ag.ion.bion.officelayer.clone.IDestinationPosition;

import ag.ion.bion.officelayer.internal.clone.AbstractCloneService;

import ag.ion.bion.officelayer.text.ITextTableRow;

/**
 * CloneService for TextTableRows
 *  
 * @author Sebastian R�sgen
 * @author Markus Kr�ger
 * @version $Revision: 10398 $
 */
public class TextTableRowCloneService extends AbstractCloneService {

	private ICloneService cloneService = null;
  
  //----------------------------------------------------------------------------	
	/**
	 * Constructor of the TextTableRowloneService. 
	 *
	 * @param rowObject the row from which the service is created
   * 
   * @throws CloneException if no clone service could be used
	 *
	 * @author Miriam Sutter
	 * @author Markus Kr�ger
	 */
	public TextTableRowCloneService (ITextTableRow rowObject) throws CloneException {
    cloneService = rowObject.getCellRange().getCloneService();
	}
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position and then returns
   * a reference 
   * 
   * @param position the positions the object is to be cloned to
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @return a reference to the newly cloned element
   */
  public IClonedObject cloneToPosition(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException{
  	return cloneService.cloneToPosition(position,propertyKeysContainer); 
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
   * @author Markus Kr�ger
   */
  public void cloneToPositionNoReturn(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    cloneService.cloneToPositionNoReturn(position,propertyKeysContainer); 
  }
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given position and then returns
   * a reference This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) 
   * 
   * @param position the positions the object is to be cloned to
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @return a reference to the newly cloned element
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Sebastian R�sgen
   */
  public IClonedObject cloneToPosition(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException{
  	return cloneService.cloneToPosition(position, adoptContent,propertyKeysContainer);
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
   * @author Markus Kr�ger
   */
  public void cloneToPositionNoReturn(IDestinationPosition position , boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    cloneService.cloneToPositionNoReturn(position, adoptContent,propertyKeysContainer);
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
   * @param position the position the object is to be cloned after
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @return a reference to the newly cloned element
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Sebastian R�sgen
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
   * @author Markus Kr�ger
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
   *  This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) 
   * 
   * @param position the position the object is to be cloned after
   * @param adoptContent indicated if the content of the object should be adopted
   * 
   * @return a reference to the newly cloned element
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Sebastian R�sgen
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
   * @author Markus Kr�ger
   */  
  public void cloneAfterThisPositionNoReturn(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException {
    cloneToPositionNoReturn(position, adoptContent,propertyKeysContainer);
  }
  //----------------------------------------------------------------------------
 
}
