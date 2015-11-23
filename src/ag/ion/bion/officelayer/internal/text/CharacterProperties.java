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
package ag.ion.bion.officelayer.internal.text;

import com.sun.star.awt.FontSlant;
import com.sun.star.awt.FontWeight;
import com.sun.star.beans.XPropertySet;

import ag.ion.bion.officelayer.beans.IPropertyKey;
import ag.ion.bion.officelayer.internal.beans.AbstractProperties;
import ag.ion.bion.officelayer.internal.beans.PropertyKey;

import ag.ion.bion.officelayer.text.ICharacterProperties;
import ag.ion.bion.officelayer.text.TextException;

/**
 * Properties of characters.
 * 
 * @author Miriam Sutter
 * @author Markus Krüger
 * @version $Revision: 11629 $
 */
public class CharacterProperties extends AbstractProperties implements ICharacterProperties {
  
  private static String[] DEFAULT_PROPERTY_KEYS = null;
  
  private static IPropertyKey[] PROPERTY_KEYS = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new CharacterProperties.
   * 
   * @param xPropertySet OpenOffice.org XPropertySet interface
   * 
   * @throws IllegalArgumentException if the OpenOffice.org interface is not valid
   * 
   * @author Miriam Sutter
   * @author Markus Krüger
   */
  public CharacterProperties(XPropertySet xPropertySet) throws IllegalArgumentException {
    super(xPropertySet);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the possible property keys.
   * 
   * @return the possible property keys
   * 
   * @author Markus Krüger
   */
  public static IPropertyKey[] getPossiblyPropertyKeys() {
    if(PROPERTY_KEYS == null) {
      PROPERTY_KEYS = new PropertyKey[] {
          new PropertyKey("CharHeight",null,null),
          new PropertyKey("CharWeight",null,null),
          new PropertyKey("CharPosture",null,null),
          new PropertyKey("CharUnderline",null,null),
          new PropertyKey("CharColor",null,null),
          new PropertyKey("CharUnderlineColor",null,null),
          new PropertyKey("CharFontName",null,null),
          new PropertyKey("CharFontStyleName",null,null),
          new PropertyKey("CharFontFamily",null,null),
          new PropertyKey("CharFontCharSet",null,null),
          new PropertyKey("CharFontPitch",null,null),
      };
    }
    return PROPERTY_KEYS;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the default property keys.
   * 
   * @return the default property keys
   * 
   * @author Markus Krüger
   */
  public static String[] getDefaultPropertyKeys() {
    if(DEFAULT_PROPERTY_KEYS == null) {
      DEFAULT_PROPERTY_KEYS = new String[] {
          "CharColor",
          "CharHeight",
          "CharWeight",
          "CharUnderline",
          "CharPosture",
          "CharUnderlineColor",
          "CharFontName",
          "CharFontStyleName",
          "CharFontFamily",
          "CharFontCharSet",
          "CharFontPitch",
      };
    }
    return DEFAULT_PROPERTY_KEYS;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the id of the property.
   * 
   * @return the id of the property
   * 
   * @author Markus Krüger
   */
  public String getTypeID() {
    return TYPE_ID;
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns the font size.
	 * 
	 * @return the font size
     * 
     * @throws TextException if the property can not be fetched
	 * 
	 * @author Miriam Sutter
	 */
	public float getFontSize() throws TextException {
		try {
      return ((Float)getXPropertySet().getPropertyValue("CharHeight")).floatValue();
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }    
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
		try {
      getXPropertySet().setPropertyValue("CharHeight",new Float(fontSize));
		}
		catch(Exception excep) {
      TextException textException = new TextException(excep.getMessage());
      textException.initCause(excep);
      throw textException;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns if the text is bold or italic or both.
	 * 
	 * @return true if the text ist bold
     * 
     * @throws TextException if the property can not be fetched
	 * 
	 * @author Miriam Sutter
	 */
	public boolean isFontBold() throws TextException {
		try {
			float help =  ((Float)getXPropertySet().getPropertyValue("CharWeight")).floatValue();
      if(help == FontWeight.BOLD) {
      	return true;
      }
      else {
      	return false;
      }
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }  
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
		try {
			float weight = param ? FontWeight.BOLD : (float)100.0;
      getXPropertySet().setPropertyValue("CharWeight",new Float(weight));
		}
		catch(Exception excep) {
      TextException textException = new TextException(excep.getMessage());
      textException.initCause(excep);
      throw textException;
		}
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns if the text ist bold or italic or both.
	 * 
	 * @return true the text is italic or both
	 * 
     * @throws TextException if the property can not be fetched
     * 
	 * @author Miriam Sutter
	 */
	public boolean isFontItalic() throws TextException {
		try {
			FontSlant help =  (FontSlant)getXPropertySet().getPropertyValue("CharPosture");
		
			if(help == FontSlant.ITALIC) {
      	return true;
      }
      else {
      	return false;
      }
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
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
		try {
			if (param) {
        getXPropertySet().setPropertyValue("CharPosture", FontSlant.ITALIC);
			}
			else {
        getXPropertySet().setPropertyValue("CharPosture", FontSlant.NONE);
			}
	  }
	  catch(Exception exception) {
	    TextException textException = new TextException(exception.getMessage());
	    textException.initCause(exception);
	    throw textException;
	  }  
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns if the text ist bold or italic or both.
	 * 
	 * @return true the text is underlined
     * 
     * @throws TextException if the property can not be set
	 * 
	 * @author Miriam Sutter
	 */
	public boolean isFontUnderlined() throws TextException {
		try {
      short help =  ((Short)getXPropertySet().getPropertyValue("CharUnderline")).shortValue();
      if(help == 0) {
      	return false;
      }
      else {
      	return true;
      }
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }  
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
		short help = (short)(param ? 1 : 0);
		try {
      getXPropertySet().setPropertyValue("CharUnderline",new Short(help));
		}
		catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }   
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns the font color.
	 * 
	 * @return the font color
     * 
     * @throws TextException if the property can not be fetched
	 * 
	 * @author Miriam Sutter
	 */
	public int getFontColor() throws TextException {
		try {
      return ((Integer)getXPropertySet().getPropertyValue("CharColor")).intValue();
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }   
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
		try {
      getXPropertySet().setPropertyValue("CharColor",new Integer(color));
		}
		catch(Exception excep) {
      TextException textException = new TextException(excep.getMessage());
      textException.initCause(excep);
      throw textException;
		}
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
    try {
      return getXPropertySet().getPropertyValue("CharFontName").toString();
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }   
  }
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
  public void setFontName(String font)  throws TextException {
    try {
      getXPropertySet().setPropertyValue("CharFontName",font);
    }
    catch(Exception excep) {
      TextException textException = new TextException(excep.getMessage());
      textException.initCause(excep);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  
  
}
