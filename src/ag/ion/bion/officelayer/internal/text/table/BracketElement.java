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

/**
 * Class for bracket elements.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class BracketElement implements ITextTableFormulaElement{
  
	private String formula = "";
	
	private BracketElement[] bracketElements = null;
	
	private Arguments arguments = new Arguments();
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new bracket element on the basis of the submitted expression.
	 * 
	 * @param formula formula expression to be used
	 * 
	 * @author Miriam Sutter
	 */
	public BracketElement(String formula) {
		this.formula = formula;
	}
	//----------------------------------------------------------------------------
	/**
	 * Calculates the bracket element.
	 * 
	 * @return the result of the bracket
	 * 
	 * @throws TextException if the formula can not be calculated
	 * 
	 * @author Miriam Sutter
	 */
	public double calculate() throws TextException {
		double result = 0;
		int count = 0;
		String calcString  = formula;
		if(bracketElements.length != 0) {
			for(int i = 0; i < bracketElements.length; i++) {
				double help = bracketElements[i].calculate();
				String helpString = bracketElements[i].getFormula();
				int index = calcString.indexOf(helpString);
				calcString = calcString.substring(0,index) + "<HELP" + count + ">"  + calcString.substring(index + helpString.length()); 
				Argument helpArgument = new Argument("<HELP" + count + ">");
				helpArgument.setValue(new Double(help));
				arguments.addArgument(helpArgument); 
				count++;
			}
		}
		CalculateString calculateString = new CalculateString();
		result = calculateString.calculate(calcString.substring(1,calcString.length()-1),arguments);
		return result;
	}
	//----------------------------------------------------------------------------
	/**
	 * Sets the internal bracket elements.
	 * 
	 * @param bracketElements the bracket elements
	 * 
	 * @author Miriam Sutter
	 */
	public void setBracketElements(BracketElement[] bracketElements) {
		this.bracketElements = bracketElements;
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