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
package ag.ion.bion.officelayer.util;

import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.noa.NOAException;

import com.sun.star.beans.XPropertySet;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.NumberFormat;

/**
 * Helper for variable text fields.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public final class VariableTextFieldHelper {

  //----------------------------------------------------------------------------
  /**
   * Sets the given content to the given variable text field.
   * 
   * @param content the content to be set
   * @param variableTextField the variable text field to set content for
   * @param isFormula if the content is a formula
   * @param numberFormatService the number format service to be used
   * 
   * @throws NOAException if setting the content fails
   * 
   * @author Markus Krüger
   * @date 27.07.2007
   */
  public static void setContent(String content, ITextField variableTextField, boolean isFormula,
      INumberFormatService numberFormatService) throws NOAException {
    try {
      if(content == null || variableTextField == null || numberFormatService == null)
        return;
      XPropertySet xPropertySetField = 
        (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, variableTextField.getXTextContent());
      int formatKey = ((Integer)xPropertySetField.getPropertyValue("NumberFormat")).intValue();
      INumberFormat numberFormat = null;
      if(formatKey != -1)
        numberFormat = numberFormatService.getNumberFormat(formatKey);
      
      //special fraction handling
      if(numberFormat != null && numberFormat.getFormatType() == NumberFormat.FRACTION) {
        if(content.indexOf(' ') > -1) {
          String[] split = content.split(" ");
          if(content.indexOf('/') > -1) {
            if(split[0].indexOf('/') > -1) {
              throw new Throwable("ERROR");
            }
            String[] split2 = split[1].split("/");
            content = ""+(Double.parseDouble(split[0])+Double.parseDouble(split2[0])/Double.parseDouble(split2[1]));
          }
          else {
            content = ""+(Double.parseDouble(split[0])/Double.parseDouble(split[1]));
          }
        }
        else if(content.indexOf('/') > -1) {
          String[] split = content.split("/");
          content = ""+(Double.parseDouble(split[0])/Double.parseDouble(split[1]));
        }
        content = content.replace('.',',');
      }
      
      String presentation = content;         
      try {
        if(!isFormula && numberFormat != null && numberFormat.getFormatType() != NumberFormat.TEXT)
          presentation = numberFormatService.applyFormat(presentation,numberFormat);    
      }
      catch(Throwable throwable) {
        //ignore
      }
      
      //special data and time handling
      if(numberFormat != null && (numberFormat.getFormatType() == NumberFormat.DATE ||
          numberFormat.getFormatType() == NumberFormat.TIME ||
          numberFormat.getFormatType() == NumberFormat.DATETIME)) {
        try {
          double doubelValue = numberFormatService.convertStringToNumber(numberFormat.getFormatKey(),content);
          content = String.valueOf(doubelValue); 
          content = content.replace('.',',');   
        }
        catch(Throwable throwable) {
          //ignore
        }
      }
      xPropertySetField.setPropertyValue("Content", content);
      if(!isFormula)
        xPropertySetField.setPropertyValue("CurrentPresentation", presentation);
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Applies the given number format to the given variable text field.
   * 
   * @param numberFormat the number format to be set
   * @param variableTextField the variable text field to set number forma for
   * @param isFormula if the variable text field is a formula
   * @param numberFormatService the number format service to be used
   * 
   * @throws NOAException if setting the number format fails
   * 
   * @author Markus Krüger
   * @date 27.07.2007
   */
  public static void applyNumberFormat(INumberFormat numberFormat, ITextField variableTextField,
      boolean isFormula, INumberFormatService numberFormatService) throws NOAException {
    try {
      if(numberFormat == null || variableTextField == null || numberFormatService == null)
        return;
      XPropertySet xPropertySetField = 
        (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, variableTextField.getXTextContent());
      String content = (String)xPropertySetField.getPropertyValue("Content");      
      xPropertySetField.setPropertyValue("NumberFormat", new Integer(numberFormat.getFormatKey()));
      setContent(content,variableTextField,isFormula,numberFormatService);
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Constructs new VariableTextFieldHelper.
   * 
   * @author Markus Krüger
   * @date 27.07.2007
   */
  private VariableTextFieldHelper() {
    //prevent instaciation
  }
  //----------------------------------------------------------------------------
}