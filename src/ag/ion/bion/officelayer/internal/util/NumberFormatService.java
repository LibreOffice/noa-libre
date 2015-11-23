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
 * Last changes made by $Author: markus $, $Date: 2007-07-30 16:51:14 +0200 (Mo, 30 Jul 2007) $
 */
package ag.ion.bion.officelayer.internal.util;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.util.INumberFormat;
import ag.ion.bion.officelayer.util.INumberFormatService;
import ag.ion.bion.officelayer.util.UtilException;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.beans.XPropertySet;

import com.sun.star.lang.Locale;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XNumberFormatTypes;
import com.sun.star.util.XNumberFormats;
import com.sun.star.util.XNumberFormatsSupplier;
import com.sun.star.util.XNumberFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Number format service.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11534 $
 */
public class NumberFormatService implements INumberFormatService {
  
  private static final String STRING_TRUE   = "true";
  private static final String INT_TRUE      = "1";
  private static final String STRING_FALSE  = "false";
  private static final String INT_FALSE     = "0";
  
  private static final int[] ADDITIONAL_CURRENCY_KEYS = new int[] {
    /*5106,
    5107,
    5108,
    5109,
    5110,
    5111,
    5112
    */};

  private ITextDocument          textDocument           = null;
  private XNumberFormatsSupplier xNumberFormatsSupplier = null;
  private XNumberFormatter       xNumberFormatter       = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new NumberFormatService.
   * 
   * @param textDocument the textDocument of this service
   * @param xNumberFormatsSupplier OpenOffice.org XNumberFormatsSupplier interface
   * 
   * @throws IllegalArgumentException if the OpenOffice.org XNumberFormatsSupplier interface is not valid
   * 
   * @author Andreas Bröker
   */
  public NumberFormatService(ITextDocument textDocument, XNumberFormatsSupplier xNumberFormatsSupplier) throws IllegalArgumentException {
    if(xNumberFormatsSupplier == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XNumberFormatsSupplier interface is not valid.");
    if(textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    this.xNumberFormatsSupplier = xNumberFormatsSupplier;
    this.textDocument = textDocument;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XNumberFormatsSupplier interface it was created with.
   * 
   * @return the OpenOffice.org XNumberFormatsSupplier interface it was created with
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public XNumberFormatsSupplier getXNumberFormatsSupplier() {
    return xNumberFormatsSupplier;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns number format on the basis of the submitted key.
   * 
   * @param key key of the number format
   * 
   * @return number format on the basis of the submitted key
   * 
   * @throws UtilException if the number format is not available
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public INumberFormat getNumberFormat(int key) throws UtilException {
    INumberFormat[] allFormats = getNumberFormats();
    for(int i = 0; i < allFormats.length; i++) {
      if(key == allFormats[i].getFormatKey())
        return allFormats[i];
    }
    try {
      XNumberFormats xNumberFormats = xNumberFormatsSupplier.getNumberFormats();
      XPropertySet docProps = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, textDocument.getXTextDocument());
      Locale docLocale = (Locale) docProps.getPropertyValue("CharLocale");
      XNumberFormatTypes numberFormatTypes = (XNumberFormatTypes)UnoRuntime.queryInterface(XNumberFormatTypes.class, xNumberFormats);
      int newKey = numberFormatTypes.getFormatForLocale(key,docLocale);
      for(int i = 0; i < allFormats.length; i++) {
        if(newKey == allFormats[i].getFormatKey())
          return allFormats[i];
      }
    }
    catch(Exception exception) {
      UtilException utilException = new UtilException(exception.getMessage());
      utilException.initCause(exception);
      throw utilException;
    }
    throw new UtilException("The number format is not available.");
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all available number formats.
   * 
   * @return all available number formats
   * 
   * @throws UtilException if the number formats are not available
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public INumberFormat[] getNumberFormats() throws UtilException {
    return getNumberFormats(com.sun.star.util.NumberFormat.ALL);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all available number formats of the given type.
   * 
   * @param type the type to get all number formats for as constants of {@link com.sun.star.util.NumberFormat}
   * 
   * @return all available number formats of the given type
   * 
   * @throws UtilException if the number formats are not available
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public INumberFormat[] getNumberFormats(short type) throws UtilException {
    try {
      XNumberFormats xNumberFormats = xNumberFormatsSupplier.getNumberFormats();
      XPropertySet docProps = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, textDocument.getXTextDocument());
      Locale docLocale = (Locale) docProps.getPropertyValue("CharLocale");
      //XNumberFormatTypes numberFormatTypes = (XNumberFormatTypes)UnoRuntime.queryInterface(XNumberFormatTypes.class, xNumberFormats);
      int[] keys = xNumberFormats.queryKeys(type,docLocale,true);
      List<INumberFormat> formats = new ArrayList<INumberFormat>();
      boolean additionalCurrenciesSet = false;
      for(int i = 0; i < keys.length; i++) {
        int key = keys[i];
        XPropertySet xProp = xNumberFormats.getByKey(key);
        if(((Short)xProp.getPropertyValue("Type")).equals(new Short(com.sun.star.util.NumberFormat.CURRENCY)) &&
            !additionalCurrenciesSet) {
          for(int j = 0; j < ADDITIONAL_CURRENCY_KEYS.length; j++) {
            XPropertySet xPropAdd = xNumberFormats.getByKey(ADDITIONAL_CURRENCY_KEYS[j]);
            formats.add(new NumberFormat(ADDITIONAL_CURRENCY_KEYS[j],xPropAdd,this));
          }
          additionalCurrenciesSet = true;
        }
        formats.add(new NumberFormat(key,xProp,this));
      }
      return formats.toArray(new INumberFormat[formats.size()]);
    }
    catch(Exception exception) {
      UtilException utilException = new UtilException(exception.getMessage());
      utilException.initCause(exception);
      throw utilException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Converts a string which contains a formatted number into a number. 
   * If this is a text format, the string will not be converted. 
   * 
   * @param key the key of the format for the conversion
   * @param text the text to be converted
   * 
   * @return the number
   * 
   * @throws UtilException if the conversion fails
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public double convertStringToNumber(int key, String text) throws UtilException {
    try {
      if(xNumberFormatter == null) {
        IServiceProvider serviceProvider = textDocument.getServiceProvider();
        if(serviceProvider == null)
          throw new UtilException("No service provider available in document.");
        Object formatter = serviceProvider.createServiceWithContext("com.sun.star.util.NumberFormatter");
        xNumberFormatter = (XNumberFormatter)UnoRuntime.queryInterface(XNumberFormatter.class, formatter);
        xNumberFormatter.attachNumberFormatsSupplier(xNumberFormatsSupplier);     
      }
      return xNumberFormatter.convertStringToNumber(key,text);
    }
    catch(Exception exception) {
      UtilException utilException = new UtilException(exception.getMessage());
      utilException.initCause(exception);
      throw utilException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Converts a number into a string. 
   * 
   * @param key the key of the format for the conversion
   * @param number the number to be converted
   * 
   * @return the string
   * 
   * @throws UtilException if the conversion fails
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public String convertNumberToString(int key, double number) throws UtilException {
    try {
      if(xNumberFormatter == null) {
        IServiceProvider serviceProvider = textDocument.getServiceProvider();
        if(serviceProvider == null)
          throw new UtilException("No service provider available in document.");
        Object formatter = serviceProvider.createServiceWithContext("com.sun.star.util.NumberFormatter");
        xNumberFormatter = (XNumberFormatter)UnoRuntime.queryInterface(XNumberFormatter.class, formatter);
        xNumberFormatter.attachNumberFormatsSupplier(xNumberFormatsSupplier);     
      }
      return xNumberFormatter.convertNumberToString(key,number);
    }
    catch(Exception exception) {
      UtilException utilException = new UtilException(exception.getMessage());
      utilException.initCause(exception);
      throw utilException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Applies the given format to the given string and returns the formatted new string.
   * 
   * @param text the text to apply number format for
   * @param numberFormat the number format to be applied
   * 
   * @return the formatted new string
   * 
   * @throws UtilException if format fails
   * 
   * @author Markus Krüger
   * @date 26.07.2007
   */
  public String applyFormat(String text, INumberFormat numberFormat) throws UtilException {
    if(numberFormat == null || text == null)
      return text;
    short type = numberFormat.getFormatType();
    int key = numberFormat.getFormatKey();
    if(type == com.sun.star.util.NumberFormat.TEXT)
      return text;
    else if(type == com.sun.star.util.NumberFormat.NUMBER ||
        type == com.sun.star.util.NumberFormat.PERCENT ||
        type == com.sun.star.util.NumberFormat.CURRENCY ||
        type == (short)(com.sun.star.util.NumberFormat.CURRENCY + 1) ||
        type == com.sun.star.util.NumberFormat.DATE ||
        type == com.sun.star.util.NumberFormat.TIME ||
        type == com.sun.star.util.NumberFormat.DATETIME ||
        type == com.sun.star.util.NumberFormat.SCIENTIFIC ||
        type == com.sun.star.util.NumberFormat.FRACTION) {
      double number = convertStringToNumber(key,text);
      return convertNumberToString(key,number);
    }
    else if(type == com.sun.star.util.NumberFormat.LOGICAL) {
      if(text.equals(STRING_TRUE))
        text = INT_TRUE;
      else if(text.equals(STRING_FALSE))
        text = INT_FALSE;
      double number = convertStringToNumber(key,text);
      return convertNumberToString(key,number);
    }
    return text;
  }
  //----------------------------------------------------------------------------
}