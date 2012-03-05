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
 * Last changes made by $Author: markus $, $Date: 2007-08-03 14:13:49 +0200 (Fr, 03 Aug 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.ITextContentEnumeration;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;

import com.sun.star.beans.XPropertySet;

import com.sun.star.container.XEnumeration;
import com.sun.star.container.XEnumerationAccess;

import com.sun.star.lang.IllegalArgumentException;

import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextField;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextRangeCompare;

import com.sun.star.uno.Any;
import com.sun.star.uno.UnoRuntime;

import java.util.ArrayList;

/**
 * Enumeration over all text content parts.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11551 $
 */
public class TextContentEnumeration implements ITextContentEnumeration {

  private ITextDocument textDocument = null;
  
  private XTextRange xTextRange = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextContentEnumeration.
   * 
   * @param textDocument text document to be used
   * @param xTextRange OpenOffice.org XTextRange interface
   * 
   * @throws IllegalArgumentException IllegalArgumentException IllegalArgumentException if the OpenOffice.org interface 
   * is not valid
   * 
   * @author Andreas Bröker
   * @author Sebastian Rösgen
   */
  public TextContentEnumeration(ITextDocument textDocument, XTextRange xTextRange) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    
    if(xTextRange == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XTextRange interface is not valid.");
    
    this.xTextRange = xTextRange;
    this.textDocument = textDocument;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all available text fields.
   * 
   * @return all available text fields
   * 
   * @author Andreas Bröker
   */
  public ITextField[] getTextFields() {
    ArrayList arrayList = new ArrayList();    
    XTextCursor textCursor = xTextRange.getText().createTextCursorByRange(xTextRange.getStart());
    XTextRangeCompare xTextRangeCompare = (XTextRangeCompare)UnoRuntime.queryInterface(XTextRangeCompare.class, xTextRange.getText());
    try {      
      while(xTextRangeCompare.compareRegionEnds(textCursor.getStart(), xTextRange.getEnd()) != -1) {
        XPropertySet propertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, textCursor);
        Any any = (Any)propertySet.getPropertyValue("TextField");
        XTextField xTextField = (XTextField)any.getObject();  
        if(xTextField != null)
          arrayList.add(new TextField(textDocument, xTextField));
        if(!textCursor.goRight((short)1, false)) 
          break;
      }
    }
    catch(Exception exception) {
      //do nothing
    }
    
    ITextField[] textFields = new ITextField[arrayList.size()];
    return (ITextField[])arrayList.toArray(textFields);    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all available paragraphs.
   * 
   * @return all available paragraphs
   * 
   * @author Miriam Sutter
   * @author Sebastian Rösgen
   */
  public IParagraph[] getParagraphs() {
  	ArrayList arrayList = new ArrayList();
  	XEnumerationAccess contentEnumerationAccess = (XEnumerationAccess)UnoRuntime.queryInterface(XEnumerationAccess.class, xTextRange);
  	XEnumeration enumeration = contentEnumerationAccess.createEnumeration();
  	try {
	  	while(enumeration.hasMoreElements()) {
	  		Any any = (Any)enumeration.nextElement();
	  		XTextContent xTextContent = (XTextContent)any.getObject();
	  		arrayList.add(new Paragraph(textDocument, xTextContent));
	  	}
  	}
    catch(Exception exception) {
      //do nothing
    }
  	IParagraph[] paragraphs = new IParagraph[arrayList.size()];
    return (IParagraph[])arrayList.toArray(paragraphs);
  }
  //----------------------------------------------------------------------------
  
}