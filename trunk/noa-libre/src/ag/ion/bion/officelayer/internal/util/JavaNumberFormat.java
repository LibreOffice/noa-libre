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

import ag.ion.bion.officelayer.util.IJavaFormattedNumber;
import ag.ion.bion.officelayer.util.IJavaNumberFormat;
import ag.ion.bion.officelayer.util.UtilException;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.StringTokenizer;

import java.awt.Color;

/**
 * Number format for java code.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class JavaNumberFormat implements IJavaNumberFormat {
  
  /**
   * The colors appear in different languages in the the format pattern. Therefore
   * they are listed here in english and german. We need a better solution - but for 
   * now it works.
   */
  private static final String COLOR_CYAN     = "cyan";
  private static final String COLOR_MAGENTA  = "magenta";
  
  private static final String COLOR_GREEN_EN    = "green";
  private static final String COLOR_BLACK_EN    = "black";
  private static final String COLOR_BLUE_EN     = "blue"; 
  private static final String COLOR_RED_EN      = "red";
  private static final String COLOR_WHITE_EN    = "white";
  private static final String COLOR_YELLOW_EN   = "yellow";  
  
  private static final String COLOR_GREEN_DE    = "gruen";
  private static final String COLOR_BLACK_DE    = "schwarz";
  private static final String COLOR_BLUE_DE     = "blau";
  private static final String COLOR_RED_DE      = "rot";
  private static final String COLOR_WHITE_DE    = "weiss";
  private static final String COLOR_YELLOW_DE   = "gelb";
    
  private String formatPattern = null;
  
  private JavaNumberFormatterRegistry javaNumberFormatterRegistry = null;
  
  //----------------------------------------------------------------------------
  /**
   * Internal registry for number formatter.
   *  
   * @author Andreas Bröker
   */
  private class JavaNumberFormatterRegistry {
    
    private ArrayList arrayList = new ArrayList();
    
    //----------------------------------------------------------------------------
    /**
     * Adds new java number formatter. 
     * 
     * @param javaNumberFormatter new java number formatter
     * 
     * @author Andreas Bröker
     */
    public void addJavaNumberFormatter(JavaNumberFormatter javaNumberFormatter) {
      arrayList.add(javaNumberFormatter);
    }
    //----------------------------------------------------------------------------
    /**
     * Returns java number formatter for the submitted double value.
     * 
     * @param value value to be used
     * 
     * @return java number formatter for the submitted double value
     * 
     * @author Andreas Bröker
     */
    public JavaNumberFormatter getJavaNumberFormatter(double value) {
      JavaNumberFormatter defaultJavaNumberFormatter = null;
      for(int i=0; i<arrayList.size(); i++) {
        JavaNumberFormatter javaNumberFormatter = (JavaNumberFormatter)arrayList.get(i);
        JavaNumberFormatCondition javaNumberFormatCondition = javaNumberFormatter.getJavaNumberFormatCondition();
        if(javaNumberFormatCondition != null) {
          if(javaNumberFormatCondition.checkCondition(value)) {
            return javaNumberFormatter;
          }
        }
        else {
          if(defaultJavaNumberFormatter == null) {
            defaultJavaNumberFormatter = javaNumberFormatter;
          }
        }
      }      
      return defaultJavaNumberFormatter;
    }
    //----------------------------------------------------------------------------
    /**
     * Returns size of the registry.
     * 
     * @return size of the registry
     * 
     * @author Andreas Bröker
     */
    public int size() {
      return arrayList.size();
    }
  }
  //----------------------------------------------------------------------------
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new JavaNumberFormat.
   * 
   * @param formatPattern pattern to be used
   * 
   * @throws UtilException if the submitted pattern is not suitable
   * 
   * @author Andreas Bröker
   */
  public JavaNumberFormat(String formatPattern) throws UtilException {
    this.formatPattern = formatPattern;
    javaNumberFormatterRegistry = new JavaNumberFormatterRegistry();
    
    StringTokenizer stringTokenizer = new StringTokenizer(formatPattern, ";");
    int counter = 1;
    while(stringTokenizer.hasMoreTokens()) {
      String token = stringTokenizer.nextToken();
      StringBuffer pattern = new StringBuffer();
      JavaNumberFormatter javaNumberFormatter = new JavaNumberFormatter();      
      StringBuffer contentBefore = new StringBuffer();
      StringBuffer contentAfter = new StringBuffer();
      for(int i=0; i<token.length(); i++) {
        char character = token.charAt(i);
        boolean onlyContent = false;
        boolean appendContent = false;
        if(character == '[') {
          StringBuffer bracketContent = new StringBuffer();
          for(int j=i+1; j<token.length(); j++) {
            character = token.charAt(j);
            if(onlyContent) {
              if(character == '-') {
                /**
                 * Ignore the locale.
                 */
                appendContent = false;                
              }
              else if(character == ']') {
                i = j;
                break;
              }
              else {
                if(appendContent) {
                  if(pattern.length() == 0) 
                    contentBefore.append(character);           
                  else 
                    contentAfter.append(character);  
                }
              }
            }
            if(character == '$') {
              onlyContent = true;  
              appendContent = true;
            }
            else {
              if(character == ']') {
                i = j;
                if(!onlyContent)
                  analyzeBracketContent(bracketContent.toString(), javaNumberFormatter);
                break;
              }
              else {
                if(!onlyContent)
                  bracketContent.append(character);
              }
            }
          }
        }        
        else {          
          if(character == '#' || character == '.' || character == ',' || character == '0') {
            if(character == ',') 
              pattern.append('.');
            else if(character == '.')
              pattern.append(',');
            else
              pattern.append(character);
          }
          else {
            if(character != '\"' && character != '\\') {
              if(pattern.length() == 0) 
                contentBefore.append(character);           
              else 
                contentAfter.append(character);
              }
          }
        }
      }    
      
      if(pattern.toString().length() != 0) {
        try {          
          javaNumberFormatter.setDecimalFormat(new DecimalFormat(pattern.toString()));
          javaNumberFormatter.setContentBefore(contentBefore.toString());
          javaNumberFormatter.setContentAfter(contentAfter.toString());
                          
          if(counter == 2) {
            if(javaNumberFormatter.getJavaNumberFormatCondition() == null) {
              javaNumberFormatter.setJavaNumberFormatCondition(new JavaNumberFormatCondition(JavaNumberFormatCondition.LOWER, 0.00));
              javaNumberFormatter.setIsNegativePattern(true);
            }
          }
          javaNumberFormatterRegistry.addJavaNumberFormatter(javaNumberFormatter);
        }
        catch(Exception exception) {
          //the number format pattern is not valid - thus it will be ignored
        }
      }
      counter++;
    }
    
    if(javaNumberFormatterRegistry.size() == 0) {
      throw new UtilException("No suitable pattern available.");
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns formated number for java code. The method returns null
   * if no suitable format pattern for the submitted value is available.
   * 
   * @param value value to be formatted
   * 
   * @return formated number for java code
   * 
   * @throws UtilException if the format operation fails
   * 
   * @author Andreas Bröker
   */
  public IJavaFormattedNumber formatNumber(double value) throws UtilException {
    try {
      JavaNumberFormatter javaNumberFormatter = javaNumberFormatterRegistry.getJavaNumberFormatter(value);
      if(javaNumberFormatter != null) {
        return new JavaFormattedNumber(javaNumberFormatter.format(value), javaNumberFormatter.getTextColor());      
      }
      return null;
    }
    catch(Exception exception) {
      UtilException utilException = new UtilException(exception.getMessage());
      utilException.initCause(exception);
      throw utilException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns formated number for java code. The method returns null
   * if no suitable format pattern for the submitted value is available.
   * 
   * @param value value to be formatted
   * 
   * @return formated number for java code
   * 
   * @throws UtilException if the format operation fails
   * 
   * @author Andreas Bröker
   */
  public IJavaFormattedNumber formatNumber(String value) throws UtilException {
    double doubleValue = -1;
    try {
      doubleValue = Double.parseDouble(value.replace(',', '.'));
    }
    catch(Exception exception) {
      //the value is not a valid number
      return null;
    }
    try {
      JavaNumberFormatter javaNumberFormatter = javaNumberFormatterRegistry.getJavaNumberFormatter(doubleValue);
      if(javaNumberFormatter != null) {
        return new JavaFormattedNumber(javaNumberFormatter.format(doubleValue), javaNumberFormatter.getTextColor());      
      }
      return null;
    }
    catch(Exception exception) {
      UtilException utilException = new UtilException(exception.getMessage());
      utilException.initCause(exception);
      throw utilException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Analyzes bracket content.
   * 
   * @param bracketContent content of a bracket   
   * @param javaNumberFormatter java number formatter to be used
   * 
   * @author Andreas Bröker
   */
  private void analyzeBracketContent(String bracketContent, JavaNumberFormatter javaNumberFormatter) {
    Color color = isColor(bracketContent);
    if(color != null) {
      javaNumberFormatter.setTextColor(color);
      return;
    }
    
    JavaNumberFormatCondition javaNumberFormatCondition = isJavaNumberFormatCondition(bracketContent);
    if(javaNumberFormatCondition != null) {
      javaNumberFormatter.setJavaNumberFormatCondition(javaNumberFormatCondition);
      return;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns color on the basis of the submitted bracket content. Returns null
   * if the bracket content is not a color.
   * 
   * @param bracketContent content of a bracket
   * 
   * @return color on the basis of the submitted bracket content
   * 
   * @author Andreas Bröker
   */
  private Color isColor(String bracketContent) {
    /**
     * Not really elegantly.
     */
    if(bracketContent.equalsIgnoreCase(COLOR_BLACK_EN) || bracketContent.equalsIgnoreCase(COLOR_BLACK_DE)) {
      return Color.BLACK;
    }
    else if(bracketContent.equalsIgnoreCase(COLOR_BLUE_EN) || bracketContent.equalsIgnoreCase(COLOR_BLUE_DE)) {
      return Color.BLUE;
    }
    else if(bracketContent.equalsIgnoreCase(COLOR_CYAN)) {
      return Color.CYAN;
    }
    else if(bracketContent.equalsIgnoreCase(COLOR_GREEN_EN) || bracketContent.equalsIgnoreCase(COLOR_GREEN_DE)) {
      return Color.GREEN;
    }
    else if(bracketContent.equalsIgnoreCase(COLOR_MAGENTA)) {
      return Color.MAGENTA;
    }
    else if(bracketContent.equalsIgnoreCase(COLOR_RED_EN) || bracketContent.equalsIgnoreCase(COLOR_RED_DE) ) {
      return Color.RED;
    }
    else if(bracketContent.equalsIgnoreCase(COLOR_WHITE_EN) || bracketContent.equalsIgnoreCase(COLOR_WHITE_DE)) {
      return Color.WHITE;
    }
    else if(bracketContent.equalsIgnoreCase(COLOR_YELLOW_EN) || bracketContent.equalsIgnoreCase(COLOR_YELLOW_DE)) {
      return Color.YELLOW;
    }
    else {
      return null;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns java number format condition. Returns null if the content of the
   * bracket is not a format condition.
   * 
   * @param bracketContent content of a bracket
   * 
   * @return java number format condition
   * 
   * @author Andreas Bröker
   */
  private JavaNumberFormatCondition isJavaNumberFormatCondition(String bracketContent) {
    String operator = "";
    StringBuffer compareValue = new StringBuffer();
    for(int i=0; i<bracketContent.length(); i++) {
      char character = bracketContent.charAt(i);
      if(Character.isDigit(character)) {
        compareValue.append(character);
      }
      else {
        operator+= character;
      }
    }
    
    try {
      double value = Double.parseDouble(compareValue.toString().replace(',','.'));
      return new JavaNumberFormatCondition(operator, value);
    }
    catch(Exception exception) {
      return null;
    }    
  }
  //----------------------------------------------------------------------------  
}