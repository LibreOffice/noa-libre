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
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.beans.IPropertyKey;
import ag.ion.bion.officelayer.internal.beans.AbstractProperties;
import ag.ion.bion.officelayer.internal.beans.PropertyKey;
import ag.ion.bion.officelayer.text.ITextTableCellProperties;
import ag.ion.bion.officelayer.text.TextException;

import com.sun.star.beans.XPropertySet;

import com.sun.star.text.VertOrientation;

/**
 * Properties of cell of a text table.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableCellProperties extends AbstractProperties implements ITextTableCellProperties {
  
  private static String[] DEFAULT_PROPERTY_KEYS = null;
  
  private static PropertyKey[] PROPERTY_KEYS = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableCellProperties.
   * 
   * @param xPropertySet OpenOffice.org XPropertySet interface
   * 
   * @throws IllegalArgumentException if the OpenOffice.org interface is not valid
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public TextTableCellProperties(XPropertySet xPropertySet) throws IllegalArgumentException {
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
          new PropertyKey("LeftBorder",null,null),
          new PropertyKey("RightBorder",null,null),
          new PropertyKey("TopBorder",null,null),
          new PropertyKey("BottomBorder",null,null),
          new PropertyKey("LeftBorderDistance",null,null),
          new PropertyKey("RightBorderDistance",null,null),
          new PropertyKey("TopBorderDistance",null,null),
          new PropertyKey("BottomBorderDistance",null,null),
          new PropertyKey("NumberFormat",null,null),
          new PropertyKey("BackColor",null,null),
          new PropertyKey("VertOrient",null,null),
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
          "LeftBorder",
          "RightBorder",
          "TopBorder",
          "BottomBorder",
          "LeftBorderDistance",
          "RightBorderDistance",
          "TopBorderDistance",
          "BottomBorderDistance",
          "NumberFormat",
          "BackColor",
          "VertOrient",
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
   * Sets style of the cell.
   * 
   * @param cellStyle style of the cell
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Andreas Bröker
   */
  public void setCellStyle(String cellStyle) throws TextException {
    try {
     // xPropertySet.setPropertyValue("CellStyle", cellStyle);
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns style of the cell.
   * 
   * @return style of the cell
   * 
   * @throws TextException if the property is not available
   */
  public String getCellStyle() throws TextException {
    try {
      return getXPropertySet().getPropertyValue("CellStyle").toString();
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    } 
  }
  //----------------------------------------------------------------------------
  /**
   * Sets number format.
   * 
   * @param numberFormat number format
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Andreas Bröker
   */
  public void setNumberFormat(int numberFormat) throws TextException {
    try {
      getXPropertySet().setPropertyValue("NumberFormat", new Integer(numberFormat));
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns number format.
   * 
   * @return number format
   * 
   * @throws TextException if the property is not available
   * 
   * @author Andreas Bröker
   */
  public int getNumberFormat() throws TextException {
    try {
      return ((Integer)getXPropertySet().getPropertyValue("NumberFormat")).intValue();
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the cell background color.
   * 
   * @return cell background color
   * 
   * @throws TextException if the property is not available
   * 
   * @author Miriam Sutter
   */
  public int getBackColor() throws TextException {
  	try {
      return ((Integer)getXPropertySet().getPropertyValue("BackColor")).intValue();
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the vertical alignment.
   * 
   * @return vertical alignment
   * 
   * @throws TextException if the property is not available
   * 
   * @author Miriam Sutter
   */
  public short getVertOrient() throws TextException {
  	try {
		  short verticalAlignment = ((Short)getXPropertySet().getPropertyValue("VertOrient")).shortValue();
		  if(verticalAlignment == VertOrientation.CENTER) {
		  	return ALIGN_CENTER; 
		  }
		  else if(verticalAlignment == VertOrientation.BOTTOM) {
		  	return ALIGN_BOTTOM; 
		  }
		  else if(verticalAlignment == VertOrientation.TOP) {
		  	return ALIGN_TOP; 
		  }
		  else {
		  	return ALIGN_UNDEFINED; 
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
   * Sets cell background color.
   * 
   * @param color color to be used
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Sebastian Rösgen
   */
	public void setBackColor(int color) throws TextException {
		try {
      getXPropertySet().setPropertyValue("BackColor", new Integer(color));
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
	}
  //----------------------------------------------------------------------------
  /**
   * Sets vertical alignment in the cell.
   * 
   * @param align alignment to be used
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Sebastian Rösgen
   */
	public void setVertOrient(short align) throws TextException {
		try {
      getXPropertySet().setPropertyValue("VertOrient", new Short(align));
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
	}
  //----------------------------------------------------------------------------
  
}