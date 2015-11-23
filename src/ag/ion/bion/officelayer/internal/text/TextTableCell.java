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
 * Last changes made by $Author: markus $, $Date: 2007-11-08 12:25:20 +0100 (Do, 08 Nov 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.AbstractTextComponent;
import ag.ion.bion.officelayer.text.ICharacterProperties;
import ag.ion.bion.officelayer.text.ICharacterPropertyStore;
import ag.ion.bion.officelayer.text.IPageStyle;
import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.IText;
import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextService;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellName;
import ag.ion.bion.officelayer.text.ITextTableCellProperties;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.IFormulaService;
import ag.ion.bion.officelayer.text.table.ITextTableCellPropertyStore;

import ag.ion.bion.officelayer.clone.CloneException;
import ag.ion.bion.officelayer.clone.ICloneService;

import ag.ion.bion.officelayer.internal.text.table.TextTableCellCloneService;
import ag.ion.bion.officelayer.internal.text.table.TextTableCellPropertyStore;
import ag.ion.bion.officelayer.internal.text.table.TextTableFormulaService;

import com.sun.star.beans.XPropertySet;

import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.table.XCell;

import com.sun.star.text.XText;
import com.sun.star.text.XTextTable;

import com.sun.star.uno.Any;
import com.sun.star.uno.UnoRuntime;

/**
 * Cell of a table in a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11583 $
 */
public class TextTableCell extends AbstractTextComponent implements ITextTableCell {

	protected ITextTableCellName textTableCellName = null;

  private ITextService                textService                 = null;
  private ITextTableCellProperties    textTableCellProperties     = null;
  private TextTableFormulaService     textTableFormulaService     = null;
  private ITextRange                  textRange                   = null;
  private ITextTableCellPropertyStore textTableCellPropertyStore  = null;
  private TextTableCellCloneService   textTableCellCloneService   = null;
  private ICharacterProperties        characterProperties         = null;
  private ICharacterPropertyStore     characterPropertyStore      = null;
  private ITextTable                  textTable                   = null;
  
  private XCell xCell = null;
    
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableCell.
   * 
   * @param textDocument text document to be used
   * @param xCell OpenOffice.org XCell interface
   *  
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XCell interface 
   * is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTableCell(ITextDocument textDocument, XCell xCell) throws IllegalArgumentException {
    super(textDocument);
    
    if(xCell == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XCell interface is not valid.");
    this.xCell = xCell;
    
    if(getName().getColumnIndex() > ITextTable.MAX_COLUMNS_IN_TABLE) { 
    	this.xCell=null;
    	throw new IllegalArgumentException("The submitted range is not valid");
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text document.
   * 
   * @return text document
   * 
   * @author Andreas Bröker
   */
  public ITextDocument getTextDocument() {
    return textDocument;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns name of the cell. 
   * 
   * @return name of the cell or null if the name is not available
   * 
   * @author Andreas Bueker
   */
  public ITextTableCellName getName() {
  	if(textTableCellName == null) {
	    XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xCell);
	    try {        
	      textTableCellName = new TextTableCellName(xPropertySet.getPropertyValue("CellName").toString());
	    }
	    catch(Exception exception) {
	      return null;
	    }
  	}
  	return textTableCellName;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text table of the cell.
   * 
   * @return text table of the cell
   * 
   * @throws TextException if the text table is not available
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public ITextTable getTextTable() throws TextException {
    if(textTable == null) {
      try {
        XText xText = (XText)UnoRuntime.queryInterface(XText.class, xCell);
        XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xText.getStart());
        Any any = (Any)xPropertySet.getPropertyValue("TextTable");
        XTextTable xTextTable = (XTextTable)any.getObject();
        textTable =  new TextTable(textDocument, xTextTable);
      }
      catch(Exception exception) {
        TextException textException = new TextException(exception.getMessage());
        textException.initCause(exception);
        throw textException;
      }
    }
    return textTable;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns content type.
   * 
   * @return content type
   * 
   * @author Andreas Bröker
   */
  public int getContentType() {
    String formula = xCell.getFormula();
    if(formula != null) {
      if(formula.length() != 0)
        return ITextTableCell.TYPE_FORMULA;
    }
    double value = xCell.getValue();
    if(value != 0)
      return ITextTableCell.TYPE_VALUE;
    XText xText = (XText)UnoRuntime.queryInterface(XText.class, xCell);
    String content = xText.getString();
    if(content.length() == 0)
      return ITextTableCell.TYPE_EMPTY;
    char chars[] = content.toCharArray();
    for(int i=0; i<chars.length; i++) {
      if(Character.isDigit(chars[i])) {
        if(chars[i] != 0)
          return ITextTableCell.TYPE_TEXT;
      }
    }
    return ITextTableCell.TYPE_TEXT;    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text service.
   * 
   * @return text service
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public ITextService getTextService() {
    if(textService == null) {
      XText xText = (XText)UnoRuntime.queryInterface(XText.class, xCell);
      XMultiServiceFactory xMultiServiceFactory = 
        (XMultiServiceFactory)UnoRuntime.queryInterface(
            XMultiServiceFactory.class, getTextDocument().getXTextDocument());
      textService = new TextService(textDocument, xMultiServiceFactory, xText);
    }
    return textService;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell properties.
   * 
   * @return text table cell properties
   * 
   * @author Andreas Bröker
   */
  public ITextTableCellProperties getProperties() {
    if(textTableCellProperties == null) {
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xCell);
      textTableCellProperties = new TextTableCellProperties(xPropertySet);
    }
    return textTableCellProperties;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell character properties.
   * 
   * @return text table cell character properties
   * 
   * @author Markus Krüger
   */
  public ICharacterProperties getCharacterProperties() {
    if(characterProperties == null) {
      ITextCursor cursor = getTextService().getCursorService().getTextCursor();
      cursor.gotoStart(false);
      cursor.gotoEnd(true);
      characterProperties = cursor.getCharacterProperties();
    }
    return characterProperties;
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns the formula service.
	 * 
	 * @return the formula Service 
	 * 
	 * @author Miriam Sutter
	 */
	public IFormulaService getFormulaService() {
    if(textTableFormulaService == null)
      textTableFormulaService = new TextTableFormulaService(xCell);
		return textTableFormulaService;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns related page style of the text table cell.
   * 
   * @return related page style of the text table cell
   * 
   * @throws TextException if the page style is not available
   * 
   * @author Andreas Bröker
   */
  public IPageStyle getPageStyle() throws TextException {
    if(textRange == null) {
      XText xText = (XText)UnoRuntime.queryInterface(XText.class, xCell);
      textRange = new TextRange(textDocument, xText.getStart());
    }
    return textRange.getPageStyle();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the property store of this cell.
   * 
   * @param horizontalPosition horizontal position to be used
   * @param verticalPosition vertical position to be used
   * 
   * @return property store of the cell
   * 
   * @throws TextException if the cell property store is not available
   * 
   * @author Sebastian Rösgen
   */
  public ITextTableCellPropertyStore getCellPropertyStore(int horizontalPosition, int verticalPosition) throws TextException {
  	if(textTableCellPropertyStore == null) {
      textTableCellPropertyStore = new TextTableCellPropertyStore(this);
    }
  	return textTableCellPropertyStore;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the character property store of this cell.
   * 
   * @return character property store of the cell
   * 
   * @throws TextException if property store could not be returned
   * 
   * @author Markus Krüger
   */
  public ICharacterPropertyStore getCharacterPropertyStore() throws TextException {
    if(characterPropertyStore == null) {      
      ITextCursor cursor = getTextService().getCursorService().getTextCursor();
      cursor.gotoStart(false);
      characterPropertyStore = new CharacterPropertyStore(cursor);
    }
    return characterPropertyStore;
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the clone service of the element.
   * 
   * @return the clone service
   * 
   * @throws CloneException if the clone service could not be returned
   * 
   * @author Markus Krüger
   */
  public ICloneService getCloneService() throws CloneException {
  	/*try {
	  	ITextTable textTable = getTextTable();
	  	ITextTableCellRange textTableCellRange = textTable.getCellRange(this.getName().getName()+":" + this.getName().getName());
	  	return new TextTableCellRangeCloneService(textTableCellRange); 
  	}
  	catch(TextException exception) {
  		CloneException cloneException = new CloneException(exception.getMessage());
  		cloneException.initCause(exception);
  		throw cloneException;
  	}*/
  	if(textTableCellCloneService == null)
      textTableCellCloneService = new TextTableCellCloneService(this);
  	return textTableCellCloneService; 
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the value of the cell.
   * 
   * @return the value of the cell
   * 
   * @author Sebastian Rösgen
   */
  public double getValue() {
  	return xCell.getValue();
  }
  //----------------------------------------------------------------------------  
  /**
   * Sets the value of the cell.
   * 
   * @param value the value to be set in the table
   * 
   * @author SebastianRösgen
   */
  public void setValue(double value){
  	xCell.setValue(value);
  }
  //----------------------------------------------------------------------------
  /**
   * Sets the formula.
   * 
   * @param formula formula
   * 
   * @author Miriam Sutter
   */
  public void setFormula(String formula) {
    getFormulaService().setFormula(formula);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the page number of the cell, returns -1 if page number
   * could not be determined.
   * 
   * @return the page number of the cell, returns -1 if page number
   * could not be determined
   * 
   * @author Markus Krüger
   */
  public short getPageNumber() {
    return getTextService().getCursorService().getTextCursor().getStartPageNumber();
  }  
  //----------------------------------------------------------------------------
  /**
   * Sets style of the cell paragraph.
   * NOTE: The style will be applied to all paragraphs found in the cell.
   * 
   * @param cellParagraphStyle style of the cell paragraph
   * 
   * @throws TextException if the property can not be modified
   * 
   * @author Markus Krüger
   * @date 21.03.2007
   */
  public void setCellParagraphStyle(String cellParagraphStyle) throws TextException {
    if(cellParagraphStyle == null)
      return;
    try {    
      IText cellText = getTextService().getText();
      IParagraph[] paragraphs = cellText.getTextContentEnumeration().getParagraphs();
      int len = paragraphs.length;
      if(len > 1)
        len = len - 1;
      for(int i = 0; i < len; i++) {
        paragraphs[i].getParagraphProperties().setParaStyleName(cellParagraphStyle);
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
   * Returns style of the cell paragraph, or null if not available.
   * NOTE: The style of the first found paragraph in the cell will be returned.
   * 
   * @return style of the cell paragraph, or null
   * 
   * @throws TextException if the property is not available
   */
  public String getCellParagraphStyle() throws TextException {
    try {    
      IText cellText = getTextService().getText();
      IParagraph[] paragraphs = cellText.getTextContentEnumeration().getParagraphs();
      if(paragraphs.length > 0)
        return paragraphs[0].getParagraphProperties().getParaStyleName();
      return null;
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }  
  //----------------------------------------------------------------------------
  
}