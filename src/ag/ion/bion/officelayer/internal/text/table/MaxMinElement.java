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

import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.text.table.IArgument;

/**
 * Class for maximum and minimum elements.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class MaxMinElement implements ITextTableFormulaElement {
	private String formula = "";
	
	private Arguments arguments = new Arguments();
	
	private boolean max = true;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new bracket element on the basis of the submitted expression.
	 * 
	 * @param formula formula expression to be used
	 * 
	 * @author Miriam Sutter
	 */
	public MaxMinElement(String formula, boolean max) {
		this.formula = formula;
		this.max = max;
	}
  //----------------------------------------------------------------------------
	/**
	 * Addes a formula argument.
	 * 
	 * @param argument the argument
	 */
	public void addArgument(Argument argument) {
		arguments.addArgument(argument);
	}
	//----------------------------------------------------------------------------
	/**
	 * Addes a formula argument.
	 * 
	 * @param arguments the arguments
	 */
	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
	}
	//----------------------------------------------------------------------------
	/**
	 * Calculates the maximum or minimum element.
	 * 
	 * @return the result of the maximum or minimum
	 * 
	 * @throws TextException if the formula can not be calculated
	 * 
	 * @author Miriam Sutter
	 */
	public double calculate() throws TextException {
		double result = 0.0;
		IArgument[] argumentsArray = arguments.getArguments();
		for(int i = 0; i < argumentsArray.length; i++) {
			double value = argumentsArray[i].getValue();
			if(i==0) {
				result = value;
			}
			if(max) {
				if(result < value) {
					result = value;
				}
			}
			else {
				if(result > value) {
					result = value;
				}
			}
		}
		return result;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the formula of the bracket element.
	 * 
	 * @return the formula of the bracket element
	 * 
	 * @author Miriam Sutter
	 */
	public String getFormula() {
		return formula;
	}
	//----------------------------------------------------------------------------
}
