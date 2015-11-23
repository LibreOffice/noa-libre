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
 * Last changes made by $Author: markus $, $Date: 2007-02-26 11:24:19 +0100 (Mo, 26 Feb 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.beans.IProperties;
import ag.ion.bion.officelayer.internal.beans.AbstractPropertyStore;
import ag.ion.bion.officelayer.text.ICharacterProperties;
import ag.ion.bion.officelayer.text.ICharacterPropertyStore;
import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.IParagraphProperties;
import ag.ion.bion.officelayer.text.IParagraphPropertyStore;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.noa.NOAException;

/**
 * Concrete implementation of the ParagraphProeprtyStore
 * 
 * @author Sebastian Rösgen
 * @author Markus Krüger
 * @author Andreas Bröker
 * @version $Revision: 11459 $
 */
public class ParagraphPropertyStore extends AbstractPropertyStore implements IParagraphPropertyStore {

	private short breakType = -1;
	private short paraAdjust = -1;
  private String paraStyleName = null;
	private ICharacterPropertyStore characterPropertyStore = null;
  private IProperties properties = null;
	
	//----------------------------------------------------------------------------
	/**
	 * Constructor for the paragraph properties
	 *  
	 * @param paragraph the Paragraph from which to gain the properties
	 * 
	 * @author Sebastian Rösgen
	 */
	public ParagraphPropertyStore (IParagraph paragraph) throws TextException{
		fillStorage(paragraph);
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
   * Sets break type.
   * 
   * @param breakType break type
   * 
   * @throws TextException if the break type is not valid
   * 
   * @author Sebastian Rösgen
   */
  public void setBreakType(short breakType) throws TextException {
  	if (breakType == IParagraphProperties.BREAK_TYPE_PAGE_AFTER || 
  			breakType == IParagraphProperties.BREAK_TYPE_PAGE_BEFORE ||
				breakType == IParagraphProperties.BREAK_TYPE_PAGE_BOTH) {
  		this.breakType = breakType;
  	}
  	else {
  		throw new TextException("The breakType is not valid");
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
   * @author Sebastian Rösgen
   */
  public short getBreakType() throws TextException {
  	if (breakType != -1) {
  		return breakType;
  	}
  	else {
  		throw new TextException("BreakType is not available.");
  	}
  }
  //----------------------------------------------------------------------------
  /**
   * Returns para adjust.
   * 
   * @return para adjust
   * 
   * @throws TextException if the para adjust is not available
   * 
   * @author Sebastian Rösgen
   */
  public short getParaAdjust() throws TextException {
  	if (paraAdjust != -1) {
  		return paraAdjust;
  	}
  	else {
  		throw new TextException("ParaAdjust is not available.");
  	}
  }
  //----------------------------------------------------------------------------
  /**
   * Sets the para adjust.
   * 
   * @param adjust the value of the para adjust
   * 
   * @throws TextException if the para adjust is not valid
   * 
   * @author Sebastian Rösgen
   */
  public void setParaAdjust(short paraAdjust) throws TextException {
  	if (paraAdjust == IParagraphProperties.ALIGN_CENTER ||
  			paraAdjust == IParagraphProperties.ALIGN_LEFT ||
				paraAdjust == IParagraphProperties.ALIGN_RIGHT ||
				paraAdjust == IParagraphProperties.ALIGN_UNDEFINED
  			) {
  		this.paraAdjust = paraAdjust;	
  	}
  	else {
  		throw new TextException("The paraAdjust is not valid");
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
    paraStyleName = name;
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
    return paraStyleName;
  }
  //---------------------------------------------------------------------------- 
  /**
   * Gets the characterproperties contained in the paragraph
   * 
   * @return the characterproperties of the paragraph
   * 
   * @author Sebastian Rösgen
   */
  public ICharacterProperties getCharacterProperties() throws TextException {
  	return this.characterPropertyStore;
  }
  //----------------------------------------------------------------------------
  /**
   * Reads the properties out of the submitted paragraph and thus
   * fills the storage with the gained information.
   * 
   * @param paragraph the paragraph from which to gain the information
   * 
   * @author Sebastian Rösgen
   */
  private void fillStorage (IParagraph paragraph) throws TextException {
  	IParagraphProperties properties = paragraph.getParagraphProperties();
    this.properties = properties;
  	this.breakType = properties.getBreakType();
  	this.paraAdjust = properties.getParaAdjust();
    try {
      this.paraStyleName = properties.getParaStyleName();
    }
    catch(NOAException exception) {
      //do not consume
    }
  	this.characterPropertyStore = paragraph.getCharacterPropertyStore();
  }
  //----------------------------------------------------------------------------
}
