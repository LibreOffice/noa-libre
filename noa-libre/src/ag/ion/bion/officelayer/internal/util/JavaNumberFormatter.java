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

import java.awt.Color;
import java.text.DecimalFormat;

/**
 * Number formatter for java code.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class JavaNumberFormatter {

  private Color                     textColor                 = null;
  private DecimalFormat             decimalFormat             = null;
  private JavaNumberFormatCondition javaNumberFormatCondition = null;
  
  private String contentBefore  = null;
  private String contentAfter   = null;
  
  private boolean isNegativePattern = false;
  
  //----------------------------------------------------------------------------
  /**
   * Sets color of the text.
   * 
   * @param textColor color of the text
   * 
   * @author Andreas Bröker
   */
  public void setTextColor(Color textColor) {
    this.textColor = textColor;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets decimal format.
   * 
   * @param decimalFormat decimal format to be used
   * 
   * @author Andreas Bröker
   */
  public void setDecimalFormat(DecimalFormat decimalFormat) {
    this.decimalFormat = decimalFormat;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets number format condition.
   * 
   * @param javaNumberFormatCondition number format condition to be used
   * 
   * @author Andreas Bröker
   */
  public void setJavaNumberFormatCondition(JavaNumberFormatCondition javaNumberFormatCondition) {
    this.javaNumberFormatCondition = javaNumberFormatCondition;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets content before pattern.
   * 
   * @param contentBefore content before pattern
   * 
   * @author Andreas Bröker
   */
  public void setContentBefore(String contentBefore) {
    this.contentBefore = contentBefore;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets conent after pattern.
   * 
   * @param contentAfter content after pattern
   * 
   * @author Andreas Bröker
   */
  public void setContentAfter(String contentAfter) {
    this.contentAfter = contentAfter;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets information whether this pattern is a negative pattern.
   * 
   * @param isNegativePattern information whether this pattern is a negative pattern
   * 
   * @author Andreas Bröker
   */
  public void setIsNegativePattern(boolean isNegativePattern) {
    this.isNegativePattern = isNegativePattern;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text color. Returns null if no color was set.
   * 
   * @return text color
   * 
   * @author Andreas Bröker
   */
  public Color getTextColor() {
    return textColor;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns decimal format.
   * 
   * @return decimal format
   * 
   * @author Andreas Bröker
   */
  public DecimalFormat getDecimalFormat() {
    return decimalFormat;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns number format condition.
   * 
   * @return number format condition
   * 
   * @author Andreas Bröker
   */
  public JavaNumberFormatCondition getJavaNumberFormatCondition() {
    return javaNumberFormatCondition;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns content before pattern.
   * 
   * @return content before pattern
   * 
   * @author Andreas Bröker
   */
  public String getContentBefore() {
    return contentBefore;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns content after pattern.
   * 
   * @return content after pattern
   * 
   * @author Andreas Bröker
   */
  public String getContentAfter() {
    return contentAfter;
  }
  //----------------------------------------------------------------------------
  /**
   * Formats submitted number.
   * 
   * @param value value to be formatted
   * 
   * @return formatted number
   * 
   * @author Andreas Bröker
   */
  public String format(double value) {
    String textValue = decimalFormat.format(value);    
    if(isNegativePattern) {
      if(textValue.startsWith("-")) 
        textValue = textValue.substring(1);      
    }
    
    String returnValue = "";
    if(contentBefore != null)
      returnValue = contentBefore;
    returnValue+= textValue;
    if(contentAfter != null)
      returnValue+= contentAfter;
      
    return returnValue;
  }
  //----------------------------------------------------------------------------
}