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
 * Copyright 2003-2007 by IOn AG                                            *
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
 * Last changes made by $Author$, $Date$
 */
package ag.ion.bion.officelayer.internal.form;

import java.text.MessageFormat;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Messages provider.
 * 
 * @author Markus Krüger
 * @version $Revision$
 */
public class Messages {
  
  private static final String BUNDLE_NAME = "ag.ion.bion.officelayer.internal.form.messages"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  //---------------------------------------------------------------------------- 
  /**
   * Returns message.
   * 
   * @param key key to be used
   * 
   * @return recognized message
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    }
    catch (MissingResourceException missingResourceException) {
      return '!' + key + '!';
    }
  }
  //---------------------------------------------------------------------------- 
  /**
   * Returns message.
   * 
   * @param key key to be used
   * @param argument message argument to be used
   * 
   * @return recognized message
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public static String getString(String key, Object argument) {
    return getString(key, new Object[]{argument});
  }
  //---------------------------------------------------------------------------- 
  /**
   * Returns message.
   * 
   * @param key key to be used
   * @param arguments message arguments to be used
   * 
   * @return recognized message
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public static String getString(String key, Object[] arguments) {
    try {
      String message = RESOURCE_BUNDLE.getString(key);
      message = MessageFormat.format(message, arguments);
      return message;
    }
    catch (MissingResourceException missingResourceException) {
      return '!' + key + '!';
    }
  }
  //---------------------------------------------------------------------------- 
  /**
   * Prevents instantiation.
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  private Messages() {
    //prevents instantiation
  }
  //---------------------------------------------------------------------------- 

}