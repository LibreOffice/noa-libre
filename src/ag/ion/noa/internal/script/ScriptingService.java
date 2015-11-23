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
package ag.ion.noa.internal.script;

import ag.ion.bion.officelayer.document.IDocument;

import ag.ion.bion.officelayer.util.Assert;

import ag.ion.noa.NOAException;

import ag.ion.noa.script.IScriptProvider;
import ag.ion.noa.script.IScriptingService;

import com.sun.star.script.provider.XScriptProviderSupplier;

import com.sun.star.uno.UnoRuntime;

/**
 * Service of the office scripting framework.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 13.06.2006
 */ 
public class ScriptingService implements IScriptingService {

	private IDocument document = null;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new ScriptService.
	 * 
	 * @param document document to be used
	 * 
	 * @author Andreas Bröker 
	 * @date 13.06.2006
	 */
	public ScriptingService(IDocument document) {
		Assert.isNotNull(document, IDocument.class, this);
		this.document = document;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns script provider.
	 * 
	 * @return script provider
	 * 
	 * @throws NOAException if the script provider can not be provided
	 * 
	 * @author Andreas Bröker
	 * @date 13.06.2006
	 */
	public IScriptProvider getScriptProvider() throws NOAException {
		try {
			XScriptProviderSupplier scriptProviderSupplier = (XScriptProviderSupplier)UnoRuntime.queryInterface(XScriptProviderSupplier.class, document.getXComponent());
			return new ScriptProvider(scriptProviderSupplier.getScriptProvider());
		}
		catch(Throwable throwable) {
			throw new NOAException(throwable);
		}
	}
  //----------------------------------------------------------------------------
	
}