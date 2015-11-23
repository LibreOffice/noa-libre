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
package ag.ion.bion.officelayer.internal.util;

/**
 * Number format condition for java code.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class JavaNumberFormatCondition {

  /** Logical operator < .*/
  public static final String LOWER          = "<";
  /** Logical operator <= .*/
  public static final String LOWER_EQUALS   = "<=";
  /** Logical operator > .*/
  public static final String GREATER        = ">";
  /** Logical operator >= .*/
  public static final String GREATER_EQUALS = ">=";
  /** Logical operator = .*/
  public static final String EQUALS         = "=";
  /** Logical operator <> .*/
  public static final String NOT            = "<>";
  
  private String operator = null;
  
  private double compareValue = -1;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new JavaNumberFormatCondition.
   * 
   * @param operator logical operator to be used
   * @param compareValue compare value to be used
   * 
   * @throws IllegalArgumentException if the submitted operator is not valid
   * 
   * @author Andreas Bröker
   */
  public JavaNumberFormatCondition(String operator, double compareValue) throws IllegalArgumentException {
    if(!(operator.equals(LOWER) ||
        operator.equals(LOWER_EQUALS) ||
        operator.equals(GREATER) ||
        operator.equals(GREATER_EQUALS) ||
        operator.equals(EQUALS) ||
        operator.equals(NOT))) {
      throw new IllegalArgumentException("Invalid operator submitted.");
    }
    this.operator = operator;
    this.compareValue = compareValue;
  }
  //----------------------------------------------------------------------------
  /**
   * Checks condition against submitted value.
   * 
   * @param value value to be used
   * 
   * @return result of the condition checking
   * 
   * @author Andreas Bröker
   */
  public boolean checkCondition(double value) {
    if(operator.equals(LOWER)) {
      if(value < compareValue) {
        return true;
      }
      return false;
    }
    else if(operator.equals(LOWER_EQUALS)) {
      if(value <= compareValue) {
        return true;
      }
      return false;
    }
    else if(operator.equals(GREATER)) {
      if(value > compareValue) {
        return true;
      }
      return false;
    }
    else if(operator.equals(GREATER_EQUALS)) {
      if(value >= compareValue) {
        return true;
      }
      return false;
    }
    else if(operator.equals(EQUALS)) {
      if(value >= compareValue) {
        return true;
      }
      return false;
    }
    else {
      if(value != compareValue) {
        return true;
      }
      return false;
    }
  }
  //----------------------------------------------------------------------------
}