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
 * Last changes made by $Author: andreas $, $Date: 2006-11-02 08:14:25 +0100 (Do, 02 Nov 2006) $
 */
package ag.ion.bion.officelayer.text;

import ag.ion.bion.officelayer.beans.IProperties;
import ag.ion.noa.NOAException;

/**
 * Properties of a paragraph.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10782 $
 */
public interface IParagraphProperties extends IProperties {
  
  /** type id of this property **/
  public static final String TYPE_ID = "ag.ion.bion.officelayer.text.ParagraphProperties";
  
  public static short BREAK_TYPE_PAGE_BEFORE  = 991;
  public static short BREAK_TYPE_PAGE_AFTER   = 992;
  public static short BREAK_TYPE_PAGE_BOTH    = 993;
  
  public static final short ALIGN_UNDEFINED	= 0;
	public static final short ALIGN_RIGHT			= 1;
	public static final short ALIGN_LEFT			= 4;
	public static final short ALIGN_CENTER		= 3;
	public static final short ALIGN_BLOCK		  = 2;
  
  //----------------------------------------------------------------------------
  /**
   * Sets break type.
   * 
   * @param breakType break type
   * 
   * @throws TextException if the break type can not be set
   * 
   * @author Andreas Bröker
   */
  public void setBreakType(short breakType) throws TextException;
  //----------------------------------------------------------------------------  
  /**
   * Returns break type.
   * 
   * @return break type
   * 
   * @throws TextException if the break type is not available
   * 
   * @author Andreas Bröker
   */
  public short getBreakType() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns para adjust.
   * 
   * @return para adjust
   * 
   * @throws TextException if the break type is not available
   * 
   * @author Andreas Bröker
   */
  public short getParaAdjust() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Sets the para adjust.
   * 
   * @param adjust the value of the para adjust
   * 
   * @throws TextException if the break type is not available
   * 
   * @author Sebastian Rösgen
   */
  public void setParaAdjust(short adjust) throws TextException;
  //---------------------------------------------------------------------------- 
  /**
   * Sets name of the paragraph style.
   * 
   * @param name name of the paragraph style
   * 
   * @throws NOAException if the new paragraph style can not be set
   * 
   * @author Andreas Bröker
   * @date 02.11.2006
   */
  public void setParaStyleName(String name) throws NOAException;
  //---------------------------------------------------------------------------- 
  /**
   * Returns name of the paragraph style. Returns null
   * if a name of a paragraph style is not available.
   * 
   * @throws NOAException if the name of the paragraph style can not be provided
   * 
   * @author Andreas Bröker
   * @date 02.11.2006
   */
  public String getParaStyleName() throws NOAException;  
  //---------------------------------------------------------------------------- 
  /**
   * Gets the characterproperties contained in the paragraph
   * 
   * @return the characterproperties of the paragraph
   * 
   * @author Sebastian Rösgen
   */
  public ICharacterProperties getCharacterProperties() throws TextException;
  //----------------------------------------------------------------------------
}