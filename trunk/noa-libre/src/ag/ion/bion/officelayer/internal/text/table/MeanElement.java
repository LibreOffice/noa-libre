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
 * Class for mean elements.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class MeanElement implements ITextTableFormulaElement {
  
	private String formula = "";
	
	private Arguments arguments = new Arguments();
	
    private MeanElement[] meanElements = null;
	
	private int meanArgumentsCount = 1;
  //----------------------------------------------------------------------------
	/**
	 * Constructs new mean element on the basis of the submitted expression.
	 * 
	 * @param formula formula expression to be used
	 * 
	 * @author Miriam Sutter
	 */
	public MeanElement(String formula) {
		this.formula = formula;
		for(int i = 0; i < formula.length(); i++) {
			if(formula.charAt(i) == '+') {
				meanArgumentsCount++;
			}
		}
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
	 * Calculates the mean element.
	 * 
	 * @return the result of the mean
	 * 
	 * @throws TextException if the formula can not be calculated
	 * 
	 * @author Miriam Sutter
	 */
	public double calculate() throws TextException {
		return 0.0;
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
	/**
	 * Sets the internal bracket elements.
	 * 
	 * @param meanElements the bracket elements
	 * 
	 * @author Miriam Sutter
	 */
	public void setMeanElements(MeanElement[] meanElements) {
		this.meanElements = meanElements;
	}
  //----------------------------------------------------------------------------
	/**
	 * Retunrs the count of the mean arguments.
	 * 
	 * @return the count of the mean arguments
	 * 
	 * @author Miriam Sutter
	 */
	public int getMeanArgumentsCount() {
		return meanArgumentsCount;
	}
  //----------------------------------------------------------------------------
	/**
	 * Replaces the mean.
	 * 
	 * @return the replaces string
	 * 
	 * @author Miriam Sutter
	 */
	public String replace() {
		if(formula.indexOf(TextTableFormula.MEAN) != -1) {
			MeanElement meanElement = new MeanElement(formula.substring(formula.indexOf(TextTableFormula.MEAN) + TextTableFormula.MEAN.length()));
			formula = formula.substring(0, formula.indexOf(TextTableFormula.MEAN)) + meanElement.replace(); 
		}
		return "(" + formula + ") /" + meanArgumentsCount ;
	}
  //----------------------------------------------------------------------------
}
