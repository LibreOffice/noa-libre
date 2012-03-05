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
 * Copyright 2003-2010 by IOn AG                                            *
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
 * Last changes made by $Author: markus $, $Date: 2010-07-07 14:14:28 +0200 (Mi, 07 Jul 2010) $
 */
package ag.ion.bion.officelayer;

/**
 * Helper for detecting os!
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class OSHelper {

  public static final String  OS_NAME    = System.getProperty("os.name");                          //$NON-NLS-1$
  public static final boolean IS_WINDOWS = getOSMatchesName("Windows");                            //$NON-NLS-1$
  public static final boolean IS_LINUX   = getOSMatchesName("Linux") || getOSMatchesName("LINUX"); //$NON-NLS-1$ //$NON-NLS-2$
  public static final boolean IS_MAC     = getOSMatchesName("Mac") || getOSMatchesName("Mac OS X"); //$NON-NLS-1$ //$NON-NLS-2$

  //----------------------------------------------------------------------------
  /**
   * Returns if the given part of the os name is contained in the current os name.
   * 
   * @param osNamePart the part of the os name to check for
   * 
   * @return if the given part of the os name is contained in the current os name
   * 
   * @author Markus Krüger
   * @date 07.07.2010
   */
  private static boolean getOSMatchesName(String osNamePart) {
    return OS_NAME.toLowerCase().indexOf(osNamePart.toLowerCase()) != -1;
  }

  //----------------------------------------------------------------------------
  /**
   * Constructs new OSHelper.
   * 
   * @author Markus Krüger
   * @date 07.07.2010
   */
  private OSHelper() {
    //prevent instantiation
  }
  //----------------------------------------------------------------------------

}
