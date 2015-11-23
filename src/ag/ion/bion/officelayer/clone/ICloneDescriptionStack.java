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

import ag.ion.bion.officelayer.text.IPropertyDescriptionElement;

/**
 * The ICloneDescriptionStack, should be implemented by all coleable structures
 * like tables and else.
 * 
 * @author Sebastian Rösgen 
 * @version $Revision: 10398 $
 */
public interface ICloneDescriptionStack {
  
  //----------------------------------------------------------------------------
	/**
	 * Adds a new element to the stack (this weill be placed
	 * on top automagically, (this is pretty obvious cause 
	 * we're working with a stack, I know, I know).
	 *
     * @param propertyDescriptionElement property description element to be used
	 */
	public void addToElementStack(IPropertyDescriptionElement propertyDescriptionElement);
  //----------------------------------------------------------------------------
	/**
	 * Removes the top element from the stack and returns it.
	 *
	 * @return the top element of the stack
	 */
	public IPropertyDescriptionElement popFromElementStack();
  //----------------------------------------------------------------------------
	/**
	 * Gets the top element from the stack without deleting it.
	 *
	 * @return the top element of the stack
	 */
	public IPropertyDescriptionElement getFromElementStack();
  //----------------------------------------------------------------------------

}
