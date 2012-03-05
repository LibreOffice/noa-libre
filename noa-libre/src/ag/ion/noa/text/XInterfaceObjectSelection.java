/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                     						*
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2006 by IOn AG                                            *
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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa.text;

import ag.ion.bion.officelayer.util.Assert;
import ag.ion.noa.view.ISelection;

import com.sun.star.uno.XInterface;

/**
 * Selection of a open office object implementing XInterface.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */ 
public class XInterfaceObjectSelection implements IXInterfaceObjectSelection, ISelection {
	
	private XInterface interfaceObject = null;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new XInterfaceObjectSelection.
	 * 
	 * @param interfaceObject object implementing XInterface to be used
	 * 
	 * @author Markus Krüger
	 * @date 01.08.2007
	 */
	public XInterfaceObjectSelection(XInterface interfaceObject) {
		Assert.isNotNull(interfaceObject, XInterface.class, this);
		this.interfaceObject = interfaceObject;
	}	
  //----------------------------------------------------------------------------
  /**
   * Returns object implementing XInterface of the selection.
   * 
   * @return object implementing XInterface of the selection
   * 
   * @author Markus Krüger
   * @date 01.08.2007
   */
  public XInterface getXInterfaceObject() {
    return interfaceObject;
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns information whether the selection is empty.
	 * 
	 * @return information whether the selection is empty
	 * 
	 * @author Markus Krüger
   * @date 01.08.2007
	 */
	public boolean isEmpty() {
		return false;
	}
  //----------------------------------------------------------------------------
	
}