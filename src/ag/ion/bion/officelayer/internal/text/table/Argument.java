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

/**
 * Class for formula arguments.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class Argument implements IArgument{
	
	private String	name	= "";
	
	private double	value	= 0.0;
	
	private boolean isCellReference = false;
  //----------------------------------------------------------------------------
	/**
	 * Constructs a new argument.
	 * 
	 * @param name the name of the argument
	 * 
	 * @author Miriam Sutter
	 */
	public Argument(String name) {
		this.name = name;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the name of the argument.
	 * 
	 * @return the name of the argument
	 * 
	 * @author Miriam Sutter
	 */
	public String getName() {
		return this.name;
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the value of the argument.
	 * 
	 * @param value the value of the argument
	 * 
	 * @author Miriam Sutter
	 */
	public void setValue(Object value){
		try {
			this.value = new Double(value.toString()).doubleValue();
		}
		catch(Exception e) {
			String help = value.toString();
			while(help.indexOf('.') != -1) {
				help = help.substring(0,help.indexOf('.')) + help.substring(help.indexOf('.')+1);
			}
			help = help.replace(',','.');
			try {
				this.value = new Double(help).doubleValue();
			}
			catch(NumberFormatException ex) {
				
			}
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Gets the value of the argument.
	 * 
	 * @return the value of the argument
	 * 
	 * @author Miriam Sutter
	 */
	public double getValue() {
		return value;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns true if it is a cell reference.
	 * 
	 * @return true if it is a cell reference
	 * 
	 * @author Miriam Sutter
	 */
	public boolean isCellReference() {
		return isCellReference;
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets if it is a cell reference
	 * 
	 * @param isCellReference true if it is a cell reference
	 * 
	 * @author Miriam Sutter
	 */
	public void setCellReference(boolean isCellReference) {
		this.isCellReference = isCellReference;
	}
  //----------------------------------------------------------------------------
}