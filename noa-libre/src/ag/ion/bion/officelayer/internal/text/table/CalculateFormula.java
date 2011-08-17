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

import java.util.ArrayList;

/**
 * Class for calculating a formula.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class CalculateFormula {

  //----------------------------------------------------------------------------
	/**
	 * Calculates the formula.
	 * 
	 * @param elements the elements of the formula
	 * @param arguments the arguments of the formula 
	 * 
	 * @return the result of the formula
	 * 
	 * @throws TextException if the formula can not be calculated
	 * 
	 * @author Miriam Sutter
	 */
	public static double calculate(ITextTableFormulaElement[] elements, Arguments arguments, String formula) throws TextException {
		double result = 0.0;
		CalculateString calculateString = new CalculateString();
		if(formula.length() == 0) return result;
		if(elements.length == 0) {
			result = calculateString.calculate(formula,arguments);
		}
		else {
			int count = 0;
			String calcString  = formula;
			for(int i = 0; i < elements.length; i++) {
				ITextTableFormulaElement element = elements[i];
				double help = element.calculate();
				String helpString = element.getFormula();
				int index = calcString.indexOf(helpString);
				calcString = calcString.substring(0,index) + "<HELP" + count + ">"  + calcString.substring(index + helpString.length()); 
				Argument helpArgument = new Argument("<HELP" + count + ">");
				helpArgument.setValue(new Double(help));
				arguments.addArgument(helpArgument); 
				count++;
			}
			result = calculateString.calculate(calcString,arguments);
		}
		
		return result;
	}	
  //----------------------------------------------------------------------------
	/**
	 * Calculates the element.
	 * 
	 * @return the result of the element
	 * 
	 * @throws TextException if the formula can not be calculated
	 * 
	 * @author Miriam Sutter
	 */
	public static double calculate(ArrayList toCalc) throws TextException {
		if(toCalc ==  null) return 0.0;
		if(toCalc.size() == 0) return 0.0;
		double result = 0.0;
		double help = 0.0;
		calcPow(toCalc);
		calcFirst(toCalc);
		if((toCalc.get(0) instanceof Argument)) {
			Argument first = (Argument)toCalc.get(0);
			result = first.getValue();
		}
		for(int i = 0, n = toCalc.size(); i < n; i++) {
			if(!(toCalc.get(i) instanceof Argument) && i < toCalc.size()-1) {
				String operator = toCalc.get(i).toString();
				if(toCalc.get(i+1) instanceof Argument) {
					Argument argument = (Argument)toCalc.get(i+1);
					help = argument.getValue();
				}
				else if(toCalc.get(i+1).toString().equals(TextTableFormula.PI)) {
					help = Math.PI;
				}
				else if(toCalc.get(i+1).toString().equals(TextTableFormula.E)) {
					help = Math.E;
				}
				if(operator.equals(TextTableFormula.PLUS)) {
					result = result + help;
				}
				else if(operator.equals(TextTableFormula.MINUS)) {
					result = result - help;
				}
				else if(operator.equals(TextTableFormula.ROUND)) {
					result = roundOK(result,help);
				}
			}
		}
		
		return result;
	}
  //----------------------------------------------------------------------------
	/**
	 * Calculates the percent 
	 * 
	 * @param value the value to calculate
	 * 
	 * @return the calculatet value
	 */
	private static double percent(double value) {
		return value/100;
	}
  //----------------------------------------------------------------------------
	/**
	 * Calculates the sqrt
	 * 
	 * @param value the value to calculate
	 * 
	 * @return the calculatet value
	 */
	private static double sqrt(double value) {
		return Math.sqrt(value);
	}
  //----------------------------------------------------------------------------
	/**
	 * Calculates the pow
	 * 
	 * @param firstValue the first value to calculate
	 * @param secondValue the second value to calculate
	 * 
	 * @return the calculatet value
	 */
	private static double pow(double firstValue,double secondValue) {
		return Math.pow(firstValue,secondValue);
	}
  //----------------------------------------------------------------------------
	/**
	 * Rounds the values.
	 * 
	 * @param valueLeft the left value
	 * @param valueRight the right value
	 * 
	 * @return the rounded value
	 * 
	 * @author Miriam Sutter
	 */
	private static double roundOK(double valueLeft, double valueRight) {
		double result = 0.0;
		valueRight = Math.floor(valueRight);
		
		if(valueLeft > 0) {
			result = valueLeft*Math.pow(10,valueRight) + 0.5;
		}
		else {
			result = valueLeft*Math.pow(10,valueRight) - 0.5;
		}
		
		result = Math.floor(result);
		result = result/Math.pow(10,valueRight);
		
		return result;
	}
  //----------------------------------------------------------------------------
	/**
	 * Calculates the multiply and divide first.
	 * 
	 * @param toCalc the list contains operators and values.
	 * 
	 * @author Miriam Sutter
	 */
	private static void calcFirst(ArrayList toCalc){
		for(int i = 0; i < toCalc.size(); i++) {
			if((!(toCalc.get(i) instanceof Argument) && i > 0 
					&& i < toCalc.size() - 1) || toCalc.get(i).toString().equals(TextTableFormula.SQRT) ) {
				String operator = toCalc.get(i).toString();
				calc(toCalc,operator,i);
			}
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Calculates the multiply and divide first.
	 * 
	 * @param toCalc the list contains operators and values.
	 * 
	 * @author Miriam Sutter
	 */
	private static void calcPow(ArrayList toCalc){
		for(int i = 0; i < toCalc.size(); i++) {
			if(i > 0 && toCalc.get(i).toString().equals(TextTableFormula.POW)) {
				String operator = toCalc.get(i).toString();
				calc(toCalc,operator,i);
			}
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Calcs for a specified operator
	 * 
	 * @param toCalc the list contains operators and values.
	 * @param operator the operator
	 * @param i the index of the list
	 * 
	 * @author Miriam Sutter
	 */
	private static void calc(ArrayList toCalc, String operator, int i){
		double firstValue = 0.0;
		double secondValue = 0.0;
		int count = 0;
		Argument argument = null;
		if(!toCalc.get(i).toString().equals(TextTableFormula.SQRT)) {
			if(toCalc.get(i-1) instanceof Argument) {
				Argument first = (Argument)toCalc.get(i-1);
				firstValue = first.getValue();
			}
			else {
				if(operator.equals(TextTableFormula.PI)) {
					firstValue = Math.PI;
				}
				else if(operator.equals(TextTableFormula.E)) {
					firstValue = Math.E;
				} 
			}
		}
		if(toCalc.get(i+1) instanceof Argument) {
			argument = (Argument)toCalc.get(i+1);
			secondValue = argument.getValue();
		}
		else {
			if(operator.equals(TextTableFormula.PI)) {
				secondValue = Math.PI;
			}
			else if(operator.equals(TextTableFormula.E)) {
				secondValue = Math.E;
			} 
		}
		
		double result = 0;
		if(operator.equals(TextTableFormula.MULTIPLY)) {
			result = firstValue* secondValue;
		}
		else if(operator.equals(TextTableFormula.DIVIDE)) {
			result = firstValue / secondValue;
		}
		else if(operator.equals(TextTableFormula.PERCENT)) {
			result = percent(firstValue);
		}
		else if(operator.equals(TextTableFormula.SQRT)) {
			result = sqrt(secondValue);
		}
		else if(operator.equals(TextTableFormula.POW)) {
			result = pow(firstValue,secondValue);
		}
		if(operator.equals(TextTableFormula.MULTIPLY) || 
				operator.equals(TextTableFormula.DIVIDE) ||
				operator.equals(TextTableFormula.PERCENT)||
				operator.equals(TextTableFormula.SQRT) ||
				operator.equals(TextTableFormula.POW)) {
			if(!operator.equals(TextTableFormula.SQRT)) {
				toCalc.remove(i-1);
			}
			else {
				toCalc.remove(i);
			}
			if(!operator.equals(TextTableFormula.PERCENT) && !operator.equals(TextTableFormula.SQRT)) {
				toCalc.remove(i-1);
			}
			argument = new Argument("help" + count);
			argument.setValue(new Double(result));
			if(!operator.equals(TextTableFormula.SQRT)) {
				toCalc.set(i-1,argument);
			}
			else {
				toCalc.set(i,argument);
			}
			count++;
			i--;
		}
	}
  //----------------------------------------------------------------------------
}