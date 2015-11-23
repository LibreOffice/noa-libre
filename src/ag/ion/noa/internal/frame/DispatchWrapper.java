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
package ag.ion.noa.internal.frame;

import ag.ion.bion.officelayer.util.Assert;

import ag.ion.noa.frame.IDispatchDelegate;

import com.sun.star.beans.PropertyValue;

import com.sun.star.frame.XDispatch;
import com.sun.star.frame.XStatusListener;

import com.sun.star.util.URL;

/**
 * Wrapper for a dispatch delegate.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 09.07.2006
 */ 
public class DispatchWrapper implements XDispatch {
	
	private IDispatchDelegate dispatchDelegate = null;
	
	private XDispatch xDispatch = null;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new DispatchWrapper.
	 * 
	 * @param dispatchDelegate dispatch delegate in order to execute the command
	 * @param xDispatch OpenOffice.org XDispatch interface in order to handle the status listener
	 * 
	 * @author Andreas Bröker
	 * @date 09.07.2006
	 */
	public DispatchWrapper(IDispatchDelegate dispatchDelegate, XDispatch xDispatch) {
		Assert.isNotNull(dispatchDelegate, IDispatchDelegate.class, this);
		Assert.isNotNull(xDispatch, XDispatch.class, this);
		this.dispatchDelegate = dispatchDelegate;
		this.xDispatch = xDispatch;
	}
  //----------------------------------------------------------------------------
	/**
	 * Dispatches the command.
	 * 
	 * @param url url of the command
	 * @param propertyValues property values to be used
	 * 
	 * @author Andreas Bröker
	 * @date 09.07.2006
	 */
	public void dispatch(URL url, PropertyValue[] propertyValues) {
		dispatchDelegate.dispatch(propertyValues);
	}
  //----------------------------------------------------------------------------
	/**
	 * Adds new status listener.
	 * 
	 * @param status listener to be added
	 * @param url url of the command
	 * 
	 * @author Andreas Bröker
	 * @date 09.07.2006
	 */
	public void addStatusListener(XStatusListener statusListener, URL url) {
		if(xDispatch != null)
			xDispatch.addStatusListener(statusListener, url);
	}
  //----------------------------------------------------------------------------
	/**
	 * Removes status listener.
	 * 
	 * @param status listener to be removed
	 * @param url url of the command
	 * 
	 * @author Andreas Bröker
	 * @date 09.07.2006
	 */
	public void removeStatusListener(XStatusListener statusListener, URL url) {
		if(xDispatch != null)
			xDispatch.removeStatusListener(statusListener, url);
	}
  //----------------------------------------------------------------------------
	
}