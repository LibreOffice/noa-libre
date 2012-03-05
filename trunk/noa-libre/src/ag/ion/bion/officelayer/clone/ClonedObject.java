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

//----------------------------------------------------------------------------
/**
 * Concrete implementation of the cloned object. This class
 * serves as store for cloned objects. hcih are used in several instances
 * of the cloneservice as return type.
 * 
 * @author Sebastian Rösgen
 * 
 * @version $Revision: 10398 $
 */
public class ClonedObject implements IClonedObject {

	private Object storedObject = null;
  private Class clazz = null;
  
  //----------------------------------------------------------------------------
	/**
	 * Constructs a new ClonedObject,
	 * 
	 * @param storeableObject the object that should be placed in the store
	 * 
	 * @param clazz the class-type of the stored object  
	 */
	public ClonedObject (Object storeableObject, Class clazz) {
		storedObject = storeableObject;
		this.clazz = clazz;
	}
  //----------------------------------------------------------------------------
	/**
	 * The cloned object.
	 * 
	 * @return the object stored.
	 * 
	 * @author Sebastian Rösgen
	 */
	public Object getClonedObject() {
		return storedObject;
	}
	//----------------------------------------------------------------------------
	/**
	 * The class of the cloned object (importatn if
	 * you want to cast it to its original type. 
	 * 
	 * @return the original class of the stored object
	 * 
	 * @author Sebastian Rösgen
	 */
	public Class getCloneClassType() {
		return clazz;
	}
  //----------------------------------------------------------------------------
}
