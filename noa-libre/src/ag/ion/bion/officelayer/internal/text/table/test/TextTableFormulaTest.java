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
 * Last changes made by $Author: markus $, $Date: 2007-02-26 11:24:19 +0100 (Mo, 26 Feb 2007) $
 */
package ag.ion.bion.officelayer.internal.text.table.test;

import ag.ion.bion.officelayer.internal.text.table.TextTableFormula;
import ag.ion.bion.officelayer.internal.text.table.TextTableFormulaExpression;
import ag.ion.bion.officelayer.text.table.IArgument;
import java.util.HashMap;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Testcase for calculating a formula..
 * 
 * @author Miriam Sutter
 * @version $Revision: 11459 $
 */
public class TextTableFormulaTest extends TestCase {

  //----------------------------------------------------------------------------
	/**
	 * Test the formula service.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaService1() {
		String formula = "((<A1>*(<A2>+<A3>)+<A4>)*<A3>+sum<A4:A7>)/(<A1|A3>)";
		double realResult = 8.6111111;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A1>",new Double(1.5));
		argumentMap.put("<A2>",new Double(2.5));
		argumentMap.put("<A3>",new Double(3));
		argumentMap.put("<A4>",new Double(1));
		argumentMap.put("<A5>",new Double(5));
		argumentMap.put("<A6>",new Double(3));
		argumentMap.put("<A7>",new Double(2));
		
		formula(formula,realResult,argumentMap);
	}
  //----------------------------------------------------------------------------
	/**
	 * Test the formula service.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaService2() {
		String formula = "<A1:B7>";
		double realResult = 36;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A1>",new Double(1.5));
		argumentMap.put("<A2>",new Double(2.5));
		argumentMap.put("<A3>",new Double(3));
		argumentMap.put("<A4>",new Double(1));
		argumentMap.put("<A5>",new Double(5));
		argumentMap.put("<A6>",new Double(3));
		argumentMap.put("<A7>",new Double(2));
		argumentMap.put("<B1>",new Double(1.5));
		argumentMap.put("<B2>",new Double(2.5));
		argumentMap.put("<B3>",new Double(3));
		argumentMap.put("<B4>",new Double(1));
		argumentMap.put("<B5>",new Double(5));
		argumentMap.put("<B6>",new Double(3));
		argumentMap.put("<B7>",new Double(2));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaService3() {
		String formula = "((<A1>*(<A2>+<A3>)+<A4>)*<A3>+sum<A4:A7>/(<A1|A3>)+20+ <A2>*<A1|A3|A7>)/2+2+ 2,5";
		double realResult = 37.722222;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A1>",new Double(1.5));
		argumentMap.put("<A2>",new Double(2.5));
		argumentMap.put("<A3>",new Double(3));
		argumentMap.put("<A4>",new Double(1));
		argumentMap.put("<A5>",new Double(5));
		argumentMap.put("<A6>",new Double(3));
		argumentMap.put("<A7>",new Double(2));
		
		formula(formula,realResult,argumentMap);
	
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaService4() {
		String formula = "<A1|A7|A4> + sum(<A1:A7>) + <A3>*7,223";
		double realResult = 44.169;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A1>",new Double(1.5));
		argumentMap.put("<A2>",new Double(2.5));
		argumentMap.put("<A3>",new Double(3));
		argumentMap.put("<A4>",new Double(1));
		argumentMap.put("<A5>",new Double(5));
		argumentMap.put("<A6>",new Double(3));
		argumentMap.put("<A7>",new Double(2));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for round.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceRound() {
		String formula = "<A2> + <B2> RoUND <C2> + <C3> ";
		double realResult = 8.123;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3.123));
		argumentMap.put("<C2>",new Double(4));
		argumentMap.put("<C3>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for round.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceRound2() {
		String formula = "(<A2> + <B2>) RoUND (<C2> + <C3>) ";
		double realResult = 5.123;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3.123));
		argumentMap.put("<C2>",new Double(4));
		argumentMap.put("<C3>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for round.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceRound3() {
		String formula = "(<A2> + <B2>) RoUND 2 + <C3> ";
		double realResult = 8.12;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3.123));
		argumentMap.put("<C2>",new Double(4));
		argumentMap.put("<C3>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for round.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceRound4() {
		String formula = "(<A2> + <B2>) RoUND <C3> round <C2> ";
		double realResult = 5.123;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3.123));
		argumentMap.put("<C2>",new Double(4));
		argumentMap.put("<C3>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for round.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceRound5() {
		String formula = "(<A2> + <B2>) RoUND (1.234 round 2)> ";
		double realResult = 5.1;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3.123));
		argumentMap.put("<C2>",new Double(4));
		argumentMap.put("<C3>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
//----------------------------------------------------------------------------
	/**
	 * Test the formula service for round.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceRound6() {
		String formula = "<A2> * <B2> RoUND (1.234 round 2) ";
		double realResult = 6.2;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3.123));
		argumentMap.put("<C2>",new Double(4));
		argumentMap.put("<C3>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for round.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceRound7() {
		String formula = "<A2> + <B2> RoUND <C3> round <C2> ";
		double realResult = 5.123;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3.123));
		argumentMap.put("<C2>",new Double(4));
		argumentMap.put("<C3>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for percent.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServicePercent() {
		String formula = "15 phd + <A2>";
		double realResult = 2.15;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3));
		argumentMap.put("<C2>",new Double(4));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for percent.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServicePercent2() {
		String formula = " <A2> + <B2> phd + <A2>";
		double realResult = 4.03;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(2));
		argumentMap.put("<B2>",new Double(3));
		argumentMap.put("<C2>",new Double(4));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for percent.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceSqrt() {
		String formula = " sqrt<A2>";
		double realResult = 5;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(25));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for percent.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceSqrt1() {
		String formula = " sqrt<A2> + <B2>";
		double realResult = 8;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(25));
		argumentMap.put("<B2>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for percent.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceSqrt2() {
		String formula = "<C2> + sqrt<A2> * <B2>";
		double realResult = 17;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(25));
		argumentMap.put("<B2>",new Double(3));
		argumentMap.put("<C2>",new Double(2));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for percent.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceSqrt3() {
		String formula = "sqrt <A2:B2>";
		double realResult = 6;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(25));
		argumentMap.put("<B2>",new Double(11));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for percent.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServicePow() {
		String formula = "2*<C2>pow <D2>";
		double realResult = 16;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<C2>",new Double(2));
		argumentMap.put("<D2>",new Double(3));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMean() {
		String formula = "mean <A2>|<B2>|<C2>|<D2>|<E2>+<F2>";
		double realResult = 3.5;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMean2() {
		String formula = "mean <A2>|<B2>|<C2>|<D2>|<E2>*<F2>";
		double realResult = 8;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMean3() {
		String formula = "mean <A2>+<B2>+<C2>+<D2>+<E2>+<F2>";
		double realResult = 3.5;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMean4() {
		String formula = "mean<A2:F2>";
		double realResult = 3.5;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		
		formula(formula,realResult,argumentMap);
	}
//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMean5() {
		String formula = "2 + (mean<A2:F2>) + 3";
		double realResult = 8.5;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMean6() {
		String formula = "mean (<A2>|<B2>)|<C2>|<D2>|<E2>|<F2>";
		double realResult = 3.5;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMean7() {
		String formula = "mean (<A2>|<B2>)|<C2>|<D2>|<E2>|<F2>|20";
		double realResult = 5.857143;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin() {
		String formula = "max <A2>|<B2>|<C2>|<D2>|<E2>|<F2>|<G2>";
		double realResult = 7;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}	
//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin2() {
		String formula = "max(<A2>+<B2>+<C2>+<D2>+<E2>+<F2>+<G2>)";
		double realResult = 28;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin3() {
		String formula = "max<A2:G2>";
		double realResult = 7;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin4() {
		String formula = "max<A2>|<B2>|<C2>|<D2>|<E2>|<F2>+<G2>";
		double realResult = 13;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin5() {
		String formula = "max(<A2>|<B2>|<C2>|<D2>|<E2>|<F2>)+<G2>";
		double realResult = 13;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin6() {
		String formula = "max<A2>|<B2>|<C2>|<D2>|<F2>|<E2>*<G2>";
		double realResult = 42;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin7() {
		String formula = "max<A2>|<B2>|<C2>|<D2>|<E2>+<G2>+<F2>";
		double realResult = 18;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin8() {
		String formula = "max<A2:F2>|<G2>";
		double realResult = 7;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin9() {
		String formula = "min<A2:F2>|<G2>";
		double realResult = 1;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin10() {
		String formula = "min<A2:F2>|<G2> + max<A2:F2>|<G2>";
		double realResult = 8;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin11() {
		String formula = "min<A2:F2>|0";
		double realResult = 0;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin12() {
		String formula = "min<A2:F2>|9";
		double realResult = 1;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin13() {
		String formula = "max<A2:F2>|0";
		double realResult = 6;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
	//----------------------------------------------------------------------------
	/**
	 * Test the formula service for mean.
	 *
	 * @author Miriam Sutter
	 */
	public void testFormulaServiceMaxMin14() {
		String formula = "max<A2:F2> | 9";
		double realResult = 9;
		HashMap argumentMap = new HashMap();
		argumentMap.put("<A2>",new Double(1));
		argumentMap.put("<B2>",new Double(2));
		argumentMap.put("<C2>",new Double(3));
		argumentMap.put("<D2>",new Double(4));
		argumentMap.put("<E2>",new Double(5));
		argumentMap.put("<F2>",new Double(6));
		argumentMap.put("<G2>",new Double(7));
		
		formula(formula,realResult,argumentMap);
	}
  //----------------------------------------------------------------------------
	/**
	 * Test the formula.
	 * 
	 * @param formula the formula
	 * @param realResult the expected result
	 * @param argumentMap the arguments
	 * 
	 * @author Miriam Sutter 
	 */
	private void formula(String formulaValue, double realResult, HashMap argumentMap) {
		try {      
			TextTableFormula formula = new TextTableFormula(new TextTableFormulaExpression(formulaValue));
			IArgument[] arguments = formula.getArguments();
			for(int i = 0; i < arguments.length; i++) {
				arguments[i].setValue(argumentMap.get(arguments[i].getName()));
			}
			 
			double result = formula.calcFormula();
			Assert.assertEquals(result,realResult,0.001);
		}
		catch(Exception e) {
			e.printStackTrace();
      Assert.fail();
		}
	}
  //----------------------------------------------------------------------------
}
