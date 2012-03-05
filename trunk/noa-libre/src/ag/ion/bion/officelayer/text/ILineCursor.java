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
package ag.ion.bion.officelayer.text;

/**
 * Line cursor of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public interface ILineCursor {

  //----------------------------------------------------------------------------
  /**
   * Determines if the cursor is positioned at the start of a line. 
   * 
   * @author Markus Krüger
   * @date 06.04.2009
   */
  public boolean isAtStartOfLine();

  //----------------------------------------------------------------------------
  /**
   * Determines if the cursor is positioned at the end of a line. 
   * 
   * @author Markus Krüger
   * @date 06.04.2009
   */
  public boolean isAtEndOfLine();

  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the start of the current line.
   * 
   * @param mark indicates if the space between the current position and the end 
   * is to be marked
   * 
   * @author Markus Krüger
   * @date 06.04.2009
   */
  public void gotoStartOfLine(boolean mark);

  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the end of the current line.
   * 
   * @param mark indicates if the space between the current position and the end 
   * is to be marked
   * 
   * @author Markus Krüger
   * @date 06.04.2009
   */
  public void gotoEndOfLine(boolean mark);

  //----------------------------------------------------------------------------

}