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
 * Last changes made by $Author: markus $, $Date: 2008-04-14 09:49:10 +0200 (Mo, 14 Apr 2008) $
 */
package ag.ion.bion.officelayer.internal.text;

import com.sun.star.awt.FontSlant;

import ag.ion.bion.officelayer.beans.IProperties;
import ag.ion.bion.officelayer.internal.beans.AbstractPropertyStore;
import ag.ion.bion.officelayer.text.ICharacterProperties;
import ag.ion.bion.officelayer.text.ICharacterPropertyStore;
import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.TextException;

/**
 * Concrete implementation of the ICharacterProeprtyStore. This class
 * stoers properties belonging to characters, thus that after loosing the
 * reference of the original character we still have stored the properties
 * 
 * @author Sebastian Rösgen
 * @author Markus Krüger
 * @version $Revision: 11631 $
 */
public class CharacterPropertyStore extends AbstractPropertyStore implements ICharacterPropertyStore{
	
	
	private float fontSize = -1;
	private boolean fontBold = false;
	private FontSlant fontItalic = null; 
	private boolean fontUnderlined = false;
	private int fontColor = 0;
  private String fontName = null;
  
  private IProperties properties = null;
	
	//----------------------------------------------------------------------------	
	/**
	 * Constructor for a character property store.
	 *
	 * @param origin the original position of the character
	 * 
	 * @throws TextException if any error occurs
	 * 
	 * @author Sebastian Rösgen
	 */
	public CharacterPropertyStore(IParagraph origin) throws TextException {
    ICharacterProperties characterProperties = origin.getCharacterProperties();
    this.properties = characterProperties;
		fillStorage(characterProperties);
	}
	//----------------------------------------------------------------------------
	/**
	 * Constructor for a character property store.
	 *
	 * @param origin the original position of the character
	 * 
	 * @throws TextException if any error occurs
	 * 
	 * @author Sebastian Rösgen
   * @author Markus Krüger
	 */
	public CharacterPropertyStore(ITextCursor origin) throws TextException{
    ICharacterProperties characterProperties = origin.getCharacterProperties();
    this.properties = characterProperties;
    fillStorage(characterProperties);
	}
	//----------------------------------------------------------------------------
  /**
   * Returns the properties.
   * 
   * @return the properties
   * 
   * @author Markus Krüger
   */
  public IProperties getProperties() {
    return properties;
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns the font size.
	 * 
	 * @return the font size
	 * 
	 * @throws TextException if any error occurs
	 * 
	 * @author Sebastian Rösgen
	 */
	public float getFontSize() throws TextException {
		return fontSize;
	}
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
	public void setFontSize(float fontSize) throws TextException {
		this.fontSize = fontSize;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns if the text is bold or italic or both.
	 * 
	 * @return true if the text ist bold
     * 
     * @throws TextException if the property can not be fetched
	 * 
	 * @author Sebastian Rösgen
	 */
	public boolean isFontBold() throws TextException {
		return fontBold;
	}
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
	public void setFontBold(boolean param) throws TextException {
		fontBold = param;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns if the text ist bold or italic or both.
	 * 
	 * @return true the text is italic or both
     * 
     * @throws TextException if the property can not be fetched
	 * 
	 * @author Sebastian Rösgen
	 */
	public boolean isFontItalic() throws TextException {
		if (fontItalic != null) {
			if (fontItalic == FontSlant.ITALIC) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			throw new TextException("Problems getting font italic");
		}
	}
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
	public void setFontItalic(boolean param) throws TextException {
		if (param) {
			fontItalic = FontSlant.ITALIC;
		}
		else {
			fontItalic = FontSlant.NONE;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns if the text ist bold or italic or both.
	 * 
	 * @return true the text is underlined
     * 
     * @throws TextException if the property can not be fetched
	 * 
	 * @author Sebastian Rösgen
	 */
	public boolean isFontUnderlined() throws TextException {
		return fontUnderlined;
	}
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
	public void setFontUnderline(boolean param) throws TextException  {
		this.fontUnderlined = param;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the font color.
	 * 
	 * @return the font color
     * 
     * @throws TextException if the property can not be fetched
	 * 
	 * @author Sebastian Rösgen
	 */
	public int getFontColor() throws TextException {
		return fontColor;
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the font color of the caracter.
	 * 
	 * @param color the font color
     * 
     * @throws TextException if the property can not be set
	 * 
	 * @author Sebastian Rösgen
	 */
	public void setFontColor(int color)  throws TextException {
		this.fontColor = color;
	}
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
  public String getFontName() throws TextException {
    return fontName;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets the font name of the caracter.
   * 
   * @param fontName the font name
   * 
   * @throws TextException if any error occurs
   * 
   * @author Markus Krüger
   * @date 14.04.2008
   */
  public void setFontName(String fontName)  throws TextException {
    this.fontName = fontName;
  }
  //----------------------------------------------------------------------------
	/**
	 * Reads in the properties and stores them
	 * 
	 * @param properties the character properties that will be stored
     * 
     * @throws TextException if the property can not be set
	 */
	private void fillStorage(ICharacterProperties properties) throws TextException{
		fontColor = properties.getFontColor();
		fontBold = properties.isFontBold();
		boolean isItalic = properties.isFontItalic();
		if(isItalic) {
    	fontItalic = FontSlant.ITALIC;
    }
		else {
			fontItalic = FontSlant.NONE;
		}
		fontUnderlined = properties.isFontUnderlined();
		fontSize = properties.getFontSize();
    fontName = properties.getFontName();
	}
  //----------------------------------------------------------------------------
}
