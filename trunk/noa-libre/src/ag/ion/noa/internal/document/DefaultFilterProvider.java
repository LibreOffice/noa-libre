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
package ag.ion.noa.internal.document;

import ag.ion.bion.officelayer.document.AbstractDocument;

import ag.ion.bion.officelayer.filter.IFilter;

import ag.ion.noa.document.IFilterProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Default provider for document filters.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 08.07.2006
 */ 
public class DefaultFilterProvider implements IFilterProvider {

	private AbstractDocument abstractDocument = null;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new FilterProvider.
	 * 
	 * @param abstractDocument abstract document to be used
	 * 
	 * @author Andreas Bröker
	 * @date 08.07.2006
	 */
	public DefaultFilterProvider(AbstractDocument abstractDocument) {
		this.abstractDocument = abstractDocument;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns available save filters.
	 * 
	 * @return available save filters
	 * 
	 * @author Andreas Bröker
	 * @date 08.07.2006
	 */
	public IFilter[] getFilters() {
		List list = new ArrayList();
		IFilter[] filters = IFilter.FILTERS;
		for(int i=0, n=filters.length; i<n; i++) {
			IFilter filter = filters[i];
			if(filter.isSupported(abstractDocument) && !filter.isExternalFilter())
				list.add(filter);
		}
		return (IFilter[])list.toArray(new IFilter[list.size()]);
	}
  //----------------------------------------------------------------------------
	
}