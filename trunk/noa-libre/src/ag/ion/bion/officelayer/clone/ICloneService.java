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
package ag.ion.bion.officelayer.clone;

import ag.ion.bion.officelayer.beans.PropertyKeysContainer;

/**
 * The ICloneService interface, should be implemented by all coleable structures
 * like tables and else.
 * 
 * @author Sebastian Rösgen
 * @author Markus Krüger
 * 
 * @version $Revision: 10398 $
 */
public interface ICloneService {

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
  public IClonedObject cloneToPosition(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException;
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
  public void cloneToPositionNoReturn(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException;
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given positions. Does not return a reference.
   * 
   * @param positions the positions the object is to be cloned to
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneToPositions(IDestinationPosition[] positions, PropertyKeysContainer propertyKeysContainer) throws CloneException;
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
   * @author Sebastian Rösgen
   */
  public IClonedObject cloneToPosition(IDestinationPosition position , boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException;
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
  public void cloneToPositionNoReturn(IDestinationPosition position , boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException;
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given positions. Does not return a reference.
   * This method also  enables to  adopts the content of
   * the object (the default is to adopt, otherwise the paramter has
   * to be set to false) .
   * 
   * @param positions the positions the object is to be cloned to
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneToPositions(IDestinationPosition[] positions, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException;
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
   * @author Sebastian Rösgen
   */
  public IClonedObject cloneAfterThisPosition(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException;
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
  public void cloneAfterThisPositionNoReturn(IDestinationPosition position, PropertyKeysContainer propertyKeysContainer) throws CloneException;
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object to the given positions. Does not return a reference.
   * Between the given positions and the newly created object
   * there will be a paragraph to add some space betwwen them. So the 
   * object WILL NOT be merged together.
   * 
   * This method is optional because it does not make sense to all possible
   * implementors of the interface. So it can happen that this method does
   * nothing more or less than the cloneToPosition method.
   * 
   * This method always adopts the content
   * 
   * @param positions the positions the object is to be cloned after
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneAfterThisPositions(IDestinationPosition[] positions, PropertyKeysContainer propertyKeysContainer) throws CloneException;
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
   * @author Sebastian Rösgen
   */  
  public IClonedObject cloneAfterThisPosition(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException;
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
  public void cloneAfterThisPositionNoReturn(IDestinationPosition position, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException;
  //----------------------------------------------------------------------------
  /**
   * Clones the chosen object after the given positions. Does not return a reference.
   * Between the given position and the newly created object
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
   * @param positions the positions the object is to be cloned after
   * @param adoptContent indicated if the content of the object should be adopted
   * @param propertyKeysContainer container for property keys used for cloning style, my be null
   * 
   * @throws CloneException if the object could not be cloned.
   * 
   * @author Markus Krüger
   */
  public void cloneAfterThisPositions(IDestinationPosition[] positions, boolean adoptContent, PropertyKeysContainer propertyKeysContainer) throws CloneException;
  //----------------------------------------------------------------------------
  
}
