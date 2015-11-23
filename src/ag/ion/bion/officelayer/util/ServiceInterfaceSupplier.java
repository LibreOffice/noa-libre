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
package ag.ion.bion.officelayer.util;

import com.sun.star.frame.XDesktop;

import com.sun.star.lang.XMultiServiceFactory;

import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.UnoRuntime;

/**
 * Supplier for OpenOffice.org services.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class ServiceInterfaceSupplier {
	
  //----------------------------------------------------------------------------
	/**
	 * Returns the XDesktop interface from the OpenOffice.org desktop service.
	 * 
	 * @param xMultiServiceFactory factory in order to construct the service
	 * 
	 * @return XDesktop interface from the OpenOffice.org desktop service
	 * 
	 * @throws Exception if any error occurs
	 */
	public static XDesktop getXDesktop(XMultiServiceFactory xMultiServiceFactory) throws Exception {
		Object service = xMultiServiceFactory.createInstance("com.sun.star.frame.Desktop");
		if(service != null) {
			return (XDesktop)UnoRuntime.queryInterface(XDesktop.class, service);
		}
		else {
			return null;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the XDesktop interface from the OpenOffice.org desktop service.
	 * 
	 * @param componentContext component context to be used
	 * 
	 * @return XDesktop interface from the OpenOffice.org desktop service
	 * 
	 * @throws Exception if any error occurs
	 */
	public static XDesktop getXDesktop(XComponentContext componentContext) throws Exception {
		Object service = componentContext.getServiceManager().createInstanceWithContext("com.sun.star.frame.Desktop", componentContext);
		if(service != null) {
			return (XDesktop)UnoRuntime.queryInterface(XDesktop.class, service);
		}
		else {
			return null;
		}
	}
  //----------------------------------------------------------------------------
}

