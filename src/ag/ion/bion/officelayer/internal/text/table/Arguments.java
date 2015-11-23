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
package ag.ion.bion.officelayer.internal.text.table;

import ag.ion.bion.officelayer.text.table.IArgument;

import java.util.HashMap;

/**
 * Class for formula arguments.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class Arguments {
  
	private HashMap arguments = new HashMap();
	
  //----------------------------------------------------------------------------
	/** 
	 * Addes an argument.
	 * 
	 * @param argument the argument
	 * 
	 * @author Miriam Sutter 
	 */
	public void addArgument(Argument argument) {
		arguments.put(argument.getName(),argument);
	}
	//----------------------------------------------------------------------------
	/** 
	 * Returns an argument.
	 * 
     * @param name name to be used
     * 
	 * @return the argument
	 * 
	 * @author Miriam Sutter 
	 */
	public Argument getArgument(String name) {
		return (Argument)arguments.get(name);
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the arguments.
	 * 
	 * @return the arguments.
	 * 
	 * @author Miriam Sutter
	 */
	public IArgument[] getArguments() {
		IArgument[] arguments = new Argument[this.arguments.size()];
		return (IArgument[])this.arguments.values().toArray(arguments);
	}
  //----------------------------------------------------------------------------
}