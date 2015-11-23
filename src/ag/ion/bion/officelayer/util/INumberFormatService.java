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
package ag.ion.bion.officelayer.util;

import com.sun.star.util.XNumberFormatsSupplier;

/**
 * Number format service.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11534 $
 */
public interface INumberFormatService {
  
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XNumberFormatsSupplier interface it was created with.
   * 
   * @return the OpenOffice.org XNumberFormatsSupplier interface it was created with
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public XNumberFormatsSupplier getXNumberFormatsSupplier();
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
   */
  public INumberFormat getNumberFormat(int key) throws UtilException;
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
  public INumberFormat[] getNumberFormats() throws UtilException;
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
  public INumberFormat[] getNumberFormats(short type) throws UtilException;
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
  public double convertStringToNumber(int key, String text) throws UtilException;
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
  public String convertNumberToString(int key, double number) throws UtilException;
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
  public String applyFormat(String text, INumberFormat numberFormat) throws UtilException;
  //----------------------------------------------------------------------------
  
}