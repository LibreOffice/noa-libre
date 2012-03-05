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
 * Last changes made by $Author: markus $, $Date: 2008-04-14 09:47:05 +0200 (Mo, 14 Apr 2008) $
 */
package ag.ion.bion.officelayer.text;

import ag.ion.bion.officelayer.beans.IProperties;

/**
 * Properties of a character
 * 
 * @author Miriam Sutter
 * @author Markus Krüger
 * @version $Revision: 11629 $
 */
public interface ICharacterProperties extends IProperties {
  
  /** type id of this property **/
  public static final String TYPE_ID = "ag.ion.bion.officelayer.text.CharacterProperties";

  //----------------------------------------------------------------------------
	/**
	 * Returns the font size.
	 * 
	 * @return the font size
	 * 
	 * @author Miriam Sutter
	 */
	public float getFontSize() throws TextException ;
  //----------------------------------------------------------------------------
	/**
	 * Sets the font size of the specific character.
	 * 
	 * @param fontSize the fontSize to be set
	 * 
	 * @throws TextException if any error occurs
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setFontSize(float fontSize) throws TextException;
  //----------------------------------------------------------------------------
	/**
	 * Returns if the text is bold or italic or both.
	 * 
	 * @return if if the text ist bold
	 * 
	 * @author Miriam Sutter
	 */
	public boolean isFontBold() throws TextException;
  //----------------------------------------------------------------------------	
	/**
	 * Sets the font of the character to bold style.
	 * 
	 * @param param the value if to be set to bold (if true then yes) 
	 * 
	 * @throws TextException if any error occurs
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setFontBold(boolean param) throws TextException;
	//----------------------------------------------------------------------------
	/**
	 * Returns if the text ist bold or italic or both.
	 * 
	 * @return true the text is italic or both
	 * 
	 * @author Miriam Sutter
	 */
	public boolean isFontItalic() throws TextException;
	//----------------------------------------------------------------------------	
	/**
	 * Sets the font of the character to italic style.
	 *  
	 * @param param the value if to be set to italic (if true then yes) 
	 * 
	 * @throws TextException if any error occurs
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setFontItalic(boolean param) throws TextException;
	//----------------------------------------------------------------------------
	/**
	 * Returns if the text ist bold or italic or both.
	 * 
	 * @return true the text is underlined
	 * 
	 * @author Miriam Sutter
	 */
	public boolean isFontUnderlined() throws TextException;
	//----------------------------------------------------------------------------	
	/**
	 * Sets the character to be underlined.
	 * 
	 * @param param the value that indicates if the character should be underlined (if true then yes)  
	 *  
	 * @throws TextException if any error occurs
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setFontUnderline(boolean param) throws TextException;
	//----------------------------------------------------------------------------
	/**
	 * Returns the font color.
	 * 
	 * @return the font color
	 * 
	 * @author Miriam Sutter
	 */
	public int getFontColor() throws TextException;
  //----------------------------------------------------------------------------
	/**
	 * Sets the font color of the caracter.
	 * 
	 * @param color the font color
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setFontColor(int color)  throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns the font name.
   * 
   * @return the font name
   * 
   * @throws TextException if any error occurs
   * 
   * @author Markus Krüger
   * @date 14.04.2008
   */
  public String getFontName() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Sets the font name of the caracter.
   * 
   * @param font the font name
   * 
   * @throws TextException if any error occurs
   * 
   * @author Markus Krüger
   * @date 14.04.2008
   */
  public void setFontName(String font)  throws TextException;
  //----------------------------------------------------------------------------
  
}
