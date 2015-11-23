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
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.beans.IPropertyKey;
import ag.ion.bion.officelayer.internal.beans.AbstractProperties;
import ag.ion.bion.officelayer.internal.beans.PropertyKey;
import ag.ion.bion.officelayer.text.ICharacterProperties;
import ag.ion.bion.officelayer.text.IParagraphProperties;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.noa.NOAException;

import com.sun.star.beans.XPropertySet;

import com.sun.star.style.BreakType;
import com.sun.star.style.ParagraphAdjust;

/**
 * Properties of a paragraph.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10782 $
 */
public class ParagraphProperties extends AbstractProperties implements IParagraphProperties {
  
  private static String[] DEFAULT_PROPERTY_KEYS = null;
  
  private static PropertyKey[] PROPERTY_KEYS = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new ParagraphProperties.
   * 
   * @param xPropertySet OpenOffice.org XPropertySet interface
   * 
   * @throws IllegalArgumentException if the OpenOffice.org interface is not valid
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public ParagraphProperties(XPropertySet xPropertySet) throws IllegalArgumentException {
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
          new PropertyKey("BreakType",null,null),
          new PropertyKey("ParaAdjust",null,null),
          new PropertyKey("ParaLastLineAdjust",null,null),
          new PropertyKey("ParaLeftMargin",null,null),
          new PropertyKey("ParaRightMargin",null,null),
          new PropertyKey("ParaTopMargin",null,null),
          new PropertyKey("ParaBottomMargin",null,null),
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
          "BreakType",
          "ParaAdjust",
          "ParaLastLineAdjust",
          "ParaLeftMargin",
          "ParaRightMargin",
          "ParaTopMargin",
          "ParaBottomMargin",
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
   * Sets break type.
   * 
   * @param breakType break type
   * 
   * @throws TextException if the break type can not be set
   * 
   * @author Andreas Bröker
   */
  public void setBreakType(short breakType) throws TextException {
    try {
      if(breakType == IParagraphProperties.BREAK_TYPE_PAGE_AFTER)   
        getXPropertySet().setPropertyValue("BreakType", BreakType.PAGE_AFTER);
      else if(breakType == IParagraphProperties.BREAK_TYPE_PAGE_BEFORE)
        getXPropertySet().setPropertyValue("BreakType", BreakType.PAGE_BEFORE);
      else if(breakType == IParagraphProperties.BREAK_TYPE_PAGE_BOTH)
        getXPropertySet().setPropertyValue("BreakType", BreakType.PAGE_BOTH);
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
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
  public short getBreakType() throws TextException {
    try {
      BreakType breakType = (BreakType)getXPropertySet().getPropertyValue("BreakType");
      if(breakType == BreakType.PAGE_AFTER) 
        return IParagraphProperties.BREAK_TYPE_PAGE_AFTER;
      else if(breakType == BreakType.PAGE_BEFORE) 
        return IParagraphProperties.BREAK_TYPE_PAGE_BEFORE;
      else if(breakType == BreakType.PAGE_BOTH)
        return IParagraphProperties.BREAK_TYPE_PAGE_BOTH;
      return -1;
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }     
  }
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
  public short getParaAdjust() throws TextException {
  	try {
      short paragraphAdjust = ((Short)getXPropertySet().getPropertyValue("ParaAdjust")).shortValue();
      if(paragraphAdjust == ParagraphAdjust.RIGHT_value) 
        return IParagraphProperties.ALIGN_RIGHT;
      else if(paragraphAdjust == ParagraphAdjust.LEFT_value) 
        return IParagraphProperties.ALIGN_LEFT;
      else if(paragraphAdjust == ParagraphAdjust.CENTER_value)
        return IParagraphProperties.ALIGN_CENTER;
      return ALIGN_UNDEFINED;
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }     
  }
  //----------------------------------------------------------------------------  
  /**
   * Sets the para adjust
   * 
   * @param adjustValue the para adjust to be set
   * 
   * @throws TextException if any error occurs
   * 
   * @author Sebastian Rösgen
   */
  public void setParaAdjust(short adjustValue) throws TextException {
  	try {
      getXPropertySet().setPropertyValue("ParaAdjust",new Short(adjustValue));
  	}
  	catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
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
  public void setParaStyleName(String name) throws NOAException {
    if(name == null)
      return;
    try {
      getXPropertySet().setPropertyValue("ParaStyleName", name);
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }
  //---------------------------------------------------------------------------- 
  /**
   * Returns name of the paragraph style.
   * 
   * @throws NOAException if the name of the paragraph style can not be provided
   * 
   * @author Andreas Bröker
   * @date 02.11.2006
   */
  public String getParaStyleName() throws NOAException {
    try {
      return (String)getXPropertySet().getPropertyValue("ParaStyleName");
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }
  //---------------------------------------------------------------------------- 
  /**
   * Gets the characterproperties contained in the paragraph
   * 
   * @return the characterproperties of the paragraph
   * 
   * @throws TextException if any error occurs
   * 
   * @author Sebastian Rösgen
   */
  public ICharacterProperties getCharacterProperties() throws TextException {
  	return new CharacterProperties(getXPropertySet());
  }
  //----------------------------------------------------------------------------
  
}