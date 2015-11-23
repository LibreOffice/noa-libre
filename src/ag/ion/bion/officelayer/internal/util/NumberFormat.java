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
 * Last changes made by $Author: markus $, $Date: 2007-07-30 16:49:07 +0200 (Mo, 30 Jul 2007) $
 */
package ag.ion.bion.officelayer.internal.util;

import ag.ion.bion.officelayer.util.IJavaNumberFormat;
import ag.ion.bion.officelayer.util.INumberFormat;
import ag.ion.bion.officelayer.util.INumberFormatService;
import ag.ion.bion.officelayer.util.UtilException;

import com.sun.star.beans.XPropertySet;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Number format. 
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11533 $
 */
public class NumberFormat implements INumberFormat {
  
  private static final String EXAMPLE_NUMBER      = "-1234,12";
  private static final String EXAMPLE_PERCENT     = "-123412,35%";
  private static final String EXAMPLE_CURRENCY    = "-1234,12";
  private static final String EXAMPLE_SCIENTIFIC  = "-1,23E+003";
  private static final String EXAMPLE_FRACTION    = "-1234 10/81";
  private static final String EXAMPLE_LOGICAL     = "true";
  private static final String EXAMPLE_STRING      = "Text";
  private static final String EXAMPLE_UNKNOWN     = "Unknown";

  private int                   formatKey             = 0;
  private XPropertySet          xPropertySet          = null;
  private INumberFormatService  numberFormatService   = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new NumberFormat.
   * 
   * @param formatKey the key of the format
   * @param xPropertySet OpenOffice.org XPropertySet interface
   * @param numberFormatService the number format service the format was created with
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org XPropertySet interface is not valid
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public NumberFormat(int formatKey, XPropertySet xPropertySet, INumberFormatService numberFormatService) throws IllegalArgumentException {
    if(xPropertySet == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XPropertySet interface is not valid.");
    this.formatKey = formatKey;
    this.xPropertySet = xPropertySet;
    this.numberFormatService = numberFormatService;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns format key.
   * 
   * @return format key
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public int getFormatKey() {
    return formatKey;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns format type as constants of {@link com.sun.star.util.NumberFormat}.
   * 
   * @return format type as constants of {@link com.sun.star.util.NumberFormat}
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public short getFormatType() {
    try {
      return ((Short)xPropertySet.getPropertyValue("Type")).shortValue();
    }
    catch(Exception exception) {
      return com.sun.star.util.NumberFormat.UNDEFINED;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns an example string.
   * 
   * @return an example string
   * 
   * @throws UtilException if returning the example fails
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public String getExample() throws UtilException {
    short type = getFormatType();
    if(type == com.sun.star.util.NumberFormat.TEXT)
      return numberFormatService.applyFormat(EXAMPLE_STRING,this);
    else if(type == com.sun.star.util.NumberFormat.NUMBER)
      return numberFormatService.applyFormat(EXAMPLE_NUMBER,this);
    else if(type == com.sun.star.util.NumberFormat.PERCENT)
      return numberFormatService.applyFormat(EXAMPLE_PERCENT,this);
    //TODO currecy hack for euros
    else if(type == com.sun.star.util.NumberFormat.CURRENCY ||
        type == (short)(com.sun.star.util.NumberFormat.CURRENCY + 1))
      return numberFormatService.applyFormat(EXAMPLE_CURRENCY,this);
    else if(type == com.sun.star.util.NumberFormat.DATE) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String dateTimeString = simpleDateFormat.format(Calendar.getInstance().getTime());
      return numberFormatService.applyFormat(dateTimeString,this);
    }
    else if(type == com.sun.star.util.NumberFormat.TIME) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
      String dateTimeString = simpleDateFormat.format(Calendar.getInstance().getTime());
      return numberFormatService.applyFormat(dateTimeString,this);
    }
    else if(type == com.sun.star.util.NumberFormat.DATETIME) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateTimeString = simpleDateFormat.format(Calendar.getInstance().getTime());
      return numberFormatService.applyFormat(dateTimeString,this);
    }
    else if(type == com.sun.star.util.NumberFormat.SCIENTIFIC) {
      return numberFormatService.applyFormat(EXAMPLE_SCIENTIFIC,this);
    }
    else if(type == com.sun.star.util.NumberFormat.FRACTION) {
      return numberFormatService.applyFormat(EXAMPLE_FRACTION,this);
    }
    else if(type == com.sun.star.util.NumberFormat.LOGICAL) {
      return numberFormatService.applyFormat(EXAMPLE_LOGICAL,this);
    }
    return numberFormatService.applyFormat(EXAMPLE_UNKNOWN,this);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns format pattern.
   * 
   * @return format pattern
   * 
   * @author Andreas Bröker
   */
  public String getFormatPattern() {
    try {
      return (String)xPropertySet.getPropertyValue("FormatString");
    }
    catch(Exception exception) {
      return null;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns number format for java code.
   * 
   * @return number format for java code
   * 
   * @throws UtilException if no suitable number format is available
   * 
   * @author Andreas Bröker
   */
  public IJavaNumberFormat getJavaNumberFormat() throws UtilException {
    return new JavaNumberFormat(getFormatPattern());
  }
  //----------------------------------------------------------------------------
  /**
   * Returns whether some other object is "equal to" this one.
   * 
   * @return whether some other object is "equal to" this one
   * 
   * @author Markus Krüger
   * @date 27.07.2007
   */
  public boolean equals(Object obj) {
    if(obj == null || !(obj instanceof INumberFormat))
      return false;
    return hashCode() == obj.hashCode();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns a hash code value for the object.
   * 
   * @return a hash code value for the object
   * 
   * @author Markus Krüger
   * @date 27.07.2007
   */
  public int hashCode() {
    return toString().hashCode();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   * 
   * @author Markus Krüger
   * @date 27.07.2007
   */
  public String toString() {    
    return getFormatType() + "_" + formatKey;
  }
  //----------------------------------------------------------------------------
  
}