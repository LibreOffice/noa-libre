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
 * Last changes made by $Author: andreas $, $Date: 2006-11-06 07:24:57 +0100 (Mo, 06 Nov 2006) $
 */
package ag.ion.noa.internal.frame;

import ag.ion.bion.officelayer.util.Assert;

import ag.ion.noa.NOAException;

import ag.ion.noa.frame.IDispatch;

import com.sun.star.beans.PropertyValue;

import com.sun.star.frame.XDispatch;

import com.sun.star.util.URL;

/**
 * A dispatch can be used in order execute UI commands.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10836 $
 * @date 14.06.2006
 */ 
public class Dispatch implements IDispatch {

	private XDispatch xDispatch = null;
	private URL				url				= null;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new Dispatch.
	 * 
	 * @param xDispatch OpenOffice.org XDispatch interface to be used
	 * @param url command URL to be used
	 * 
	 * @author Andreas Bröker
	 * @date 14.06.2006
	 */
	public Dispatch(XDispatch xDispatch, URL url) {
		Assert.isNotNull(xDispatch, XDispatch.class, this);
		Assert.isNotNull(url, URL.class, this);
		this.xDispatch = xDispatch;
		this.url = url;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns OpenOffice.org XDispatch interface.
	 * 
	 * @return OpenOffice.org XDispatch interface
	 * 
	 * @author Andreas Bröker
	 * @date 14.06.2006
	 */
	public XDispatch getXDispatch() {
		return xDispatch;
	}	
  //----------------------------------------------------------------------------
	/**
	 * Dispatches the related command.
	 * 
	 * @throws NOAException if the command can not be executed
	 * 
	 * @author Andreas Bröker
	 * @date 14.06.2006
	 */
	public void dispatch() throws NOAException {
		try {
			xDispatch.dispatch(url, new PropertyValue[0]);
		}
		catch(Throwable throwable) {
			throw new NOAException(throwable);
		}
	}
  //----------------------------------------------------------------------------
  /**
   * Dispatches the related command with the submitted property values.
   * 
   * @param propertyValues property values to be used
   * 
   * @throws NOAException if the command can not be executed
   * 
   * @author Andreas Bröker
   * @date 06.11.2006
   */
  public void dispatch(PropertyValue[] propertyValues) throws NOAException {
    if(propertyValues == null)
      dispatch();
    else {
      try {
        xDispatch.dispatch(url, propertyValues);
      }
      catch(Throwable throwable) {
        throw new NOAException(throwable);
      }
    }
  }
  //----------------------------------------------------------------------------
	
}