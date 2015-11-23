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

import ag.ion.bion.officelayer.text.ITextTableProperties;
import ag.ion.bion.officelayer.text.TextException;

import com.sun.star.beans.XPropertySet;
import com.sun.star.text.TableColumnSeparator;

/**
 * Properties of a text table.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableProperties extends AbstractProperties implements ITextTableProperties {
  
  private long					width					= 0;
  
  private static String[] DEFAULT_PROPERTY_KEYS = null;
  
  private static PropertyKey[] PROPERTY_KEYS = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableProperties.
   * 
   * @param xPropertySet OpenOffice.org XPropertySet interface
   * 
   * @throws IllegalArgumentException if the OpenOffice.org interface is not valid
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public TextTableProperties(XPropertySet xPropertySet) throws IllegalArgumentException {
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
          new PropertyKey("RepeatHeadline",null,null),
          new PropertyKey("TableColumnSeparators",null,null),
          new PropertyKey("TableColumnRelativeSum",null,null),
          new PropertyKey("BreakType",null,null),
          new PropertyKey("LeftMargin",null,null),
          new PropertyKey("RightMargin",null,null),
          new PropertyKey("TopMargin",null,null),
          new PropertyKey("BottomMargin",null,null),
          new PropertyKey("HoriOrient",null,null),
          new PropertyKey("ShadowFormat",null,null),
          new PropertyKey("TableBorder",null,null),
          new PropertyKey("BackColor",null,null),
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
        "RepeatHeadline",
        "TableColumnSeparators",
        "BreakType",
        "LeftMargin",
        "RightMargin",
        "TopMargin",
        "BottomMargin",
        "HoriOrient",
        "ShadowFormat",
        "TableBorder",
        "BackColor",
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
   * Sets information whether the first row is repeated on every page
   * as headline.
   * 
   * @param repeatHeadline information whether the first row is repeated on every page
   * as headline
   * 
   * @throws TextException if the property can not be set
   * 
   * @author Andreas Bröker
   */
  public void setRepeatHeadline(boolean repeatHeadline) throws TextException {
    try {
      getXPropertySet().setPropertyValue("RepeatHeadline", new Boolean(repeatHeadline));
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the first row is repeated on every page
   * as headline.
   * 
   * @return information whether the first row is repeated on every page
   * as headline
   * 
   * @throws TextException if the property is not available
   * 
   * @author Andreas Bröker
   */
  public boolean repeatHeadline() throws TextException {
    try {
      return ((Boolean)getXPropertySet().getPropertyValue("RepeatHeadline")).booleanValue();
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the widths of the cells.
   * 
   * @return widths of the cells
   * 
   * @throws TextException if the property is not available
   * 
   * @author Miriam sutter
   */
  public int[] getCellWidths() throws TextException {
  	int widths[] = null;
  	try {
  		TableColumnSeparator[] columnSeparator = (TableColumnSeparator[])getXPropertySet().getPropertyValue("TableColumnSeparators");
  		widths = new int[columnSeparator.length+1];
  		widths[0] =  columnSeparator[0].Position;
  		for(int i = 1; i < columnSeparator.length;i++) {
  			widths[i] = columnSeparator[i].Position - columnSeparator[i-1].Position;
  		}
  		widths[columnSeparator.length] = (int)(width - columnSeparator[columnSeparator.length-1].Position);
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  	return widths;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the widths of the table.
   * 
   * @return widths of the table
   * 
   * @throws TextException if the property is not available
   * 
   * @author Miriam sutter
   */
  public long getWidth() throws TextException  {
  	try {
  		width = new Long(getXPropertySet().getPropertyValue("TableColumnRelativeSum").toString()).longValue();
  		return width;
  		
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the table column separators.
   * 
   * @return separators of the table columns
   * 
   * @throws TextException if the property is not available
   * 
   * @author Markus Krüger
   */
  public TextTableColumnsSeparator[] getTableColumnSeparators() throws TextException {
    try {
    	TableColumnSeparator[] tableColumnSeparators = (TableColumnSeparator[])getXPropertySet().getPropertyValue("TableColumnSeparators");
	  
    	TextTableColumnsSeparator[] textTableColumnsSeparators = new TextTableColumnsSeparator[tableColumnSeparators.length];
    	
    	for(int i = 0; i < tableColumnSeparators.length; i++)
    	  textTableColumnsSeparators[i] = new TextTableColumnsSeparator(tableColumnSeparators[i]);
    	
    	return textTableColumnsSeparators;
    }
	  catch(Exception exception) {
	    TextException textException = new TextException(exception.getMessage());
	    textException.initCause(exception);
	    throw textException;
	  }
  }
  //----------------------------------------------------------------------------
  /**
   * Sets the table column separators.
   * 
   * @param textTableColumnSeparators text table column separators
   * 
   * @throws TextException if the property is not available
   * 
   * @author Markus Krüger
   */
  public void setTableColumnSeparators(TextTableColumnsSeparator[] textTableColumnSeparators) throws TextException {
    try {
      TableColumnSeparator[] tableColumnSeparators = new TableColumnSeparator[textTableColumnSeparators.length];
    	
    	for(int i = 0; i < textTableColumnSeparators.length; i++) {
    	  TableColumnSeparator tableColumnSeparator =  new TableColumnSeparator();
    	  tableColumnSeparator.Position = textTableColumnSeparators[i].getPosition();
    	  tableColumnSeparator.IsVisible = textTableColumnSeparators[i].getIsVisible();
    	  tableColumnSeparators[i] = tableColumnSeparator;
    	}
      
      getXPropertySet().setPropertyValue("TableColumnSeparators", tableColumnSeparators);
	  }
	  catch(Exception exception) {
	    TextException textException = new TextException(exception.getMessage());
	    textException.initCause(exception);
	    throw textException;
	  }
  }
  //----------------------------------------------------------------------------
  
}