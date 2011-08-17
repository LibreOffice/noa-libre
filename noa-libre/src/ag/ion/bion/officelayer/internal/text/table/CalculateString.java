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

import java.util.ArrayList;

import ag.ion.bion.officelayer.text.TextException;

/**
 * Class for calculating a string.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class CalculateString {
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new calculation
	 *  
	 * @author Miriam Sutter
	 */
	public CalculateString() {
	}
	//----------------------------------------------------------------------------
	/**
	 * Calculates the string.
	 * 
	 * @return the result of the string
	 * 
	 * @throws TextException if the formula can not be calculated
	 * 
	 * @author Miriam Sutter
	 */
	public double calculate(String calculateString, Arguments arguments) throws TextException {
		ArrayList toCalc = new ArrayList();
		for(int i = 0; i < calculateString.length(); i++) {
			for(int j = 0; j < TextTableFormula.operators.length; j++) {
				if(TextTableFormula.operators[j].length()==1) {
					if(calculateString.charAt(i) == TextTableFormula.operators[j].charAt(0)) {
						if(i!=0 && i != calculateString.length()-1) {
							toCalc.add(TextTableFormula.operators[j]);
							break;
						}
					}
				}
				else {
					String help = calculateString.substring(i).trim();
					if(help.toLowerCase().startsWith(TextTableFormula.operators[j])) {
						toCalc.add(TextTableFormula.operators[j]);
						i = i + TextTableFormula.operators[j].length()-1;
					}
				}
			}
			if(calculateString.charAt(i) == '<') {
				String help = calculateString.substring(i);
				toCalc.add(arguments.getArgument(help.substring(0,help.indexOf(">")+1)));
				i = i + arguments.getArgument(help.substring(0,help.indexOf(">")+1)).getName().length()-1;
			}
			else {
				if(calculateString.length() >= i+1) {
					if(calculateString.charAt(i) == TextTableFormula.PI.charAt(0) &&
							calculateString.charAt(i+1) == TextTableFormula.PI.charAt(1)){
						toCalc.add(TextTableFormula.PI);
					}
				}
				if(calculateString.charAt(i) == TextTableFormula.E.charAt(0)){
					toCalc.add(TextTableFormula.E);
				}
				if(Character.isDigit(calculateString.charAt(i))) {
					String helpString = new Character(calculateString.charAt(i)).toString();
					for(int j = i+1; j < calculateString.length();j++) {
						if(Character.isDigit(calculateString.charAt(j))) {
							helpString = helpString + new Character(calculateString.charAt(j)).toString();
						}
						else if(calculateString.charAt(j) == ',') {
							helpString = helpString + ".";
						}
						else if(calculateString.charAt(j) == '.') {
							helpString = helpString + ".";
						}
						else if(calculateString.charAt(j) != '.'){
							break;
						}
					}
					Argument argument = new Argument("DIGIT" + i);
					argument.setValue(helpString);
					toCalc.add(argument);
					i = i + helpString.length()-1;
				}
			}
		}
		
		return CalculateFormula.calculate(toCalc);
	}
	//----------------------------------------------------------------------------
}
