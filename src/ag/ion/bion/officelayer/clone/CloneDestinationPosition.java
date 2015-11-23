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

/**
 * Concrete implementation of the IDestinationPosition.So
 * this class is used to store the target position for a 
 * newly created clone.
 * 
 * @author Sebastian Rösgen 
 * @version $Revision: 10398 $
 */
public class CloneDestinationPosition implements IDestinationPosition {

	private Object storeObject = null;
	private Class clazz = null;
	
  //----------------------------------------------------------------------------	
	/**
	 * Constructor for the DestinationPosition.
	 * 
	 * @param storeObject the class to be put into the store
	 * @param clazz the class type of the class to be stored
	 * 
	 * @author Sebastian Rösgen
	 */
	public CloneDestinationPosition (Object storeObject, Class clazz) {
		this.storeObject = storeObject;
		this.clazz = clazz;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the class of the stored object.
	 * 
	 * @return the type of the stored class
	 * 
	 * @author Sebastian Rösgen
	 */
	public Class getType() {
		return clazz;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the stored object.
	 * 
	 * @return the stored destiantion object.
	 * 
	 * @author Sebastian Rösgen
	 */
	public Object getDestinationObject() {
		return storeObject;
	}
  //----------------------------------------------------------------------------
  
}