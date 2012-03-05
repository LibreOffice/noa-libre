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
 * Last changes made by $Author: markus $, $Date: 2007-09-19 15:26:14 +0200 (Mi, 19 Sep 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.bion.officelayer.text.ITextFieldMaster;
import ag.ion.bion.officelayer.text.ITextFieldService;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.noa.NOAException;

import com.sun.star.beans.XPropertySet;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiServiceFactory;

import com.sun.star.text.XDependentTextField;

import com.sun.star.uno.UnoRuntime;

/**
 * Interface for a master of a textfield.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11572 $
 */
public class TextFieldMaster implements ITextFieldMaster {
  
  private static final ITextField[] EMPTY_TEXTFIELD_ARRAY = new ITextField[0];
  
  private ITextDocument textDocument = null;
  private XPropertySet  xPropertySet = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextFieldMaster.
   * 
   * @param textDocument text document to be used
   * @param xPropertySet OpenOffice.org XPropertySet interface to be used
   * 
   * @throws IllegalArgumentException if the submitted text document or 
   * OpenOffice.org XPropertySet interface is not valid
   * 
   * @author Andreas Bröker
   */
  public TextFieldMaster(ITextDocument textDocument, XPropertySet xPropertySet) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("The submitted text document is not valid.");
    this.textDocument = textDocument;
    
    if(xPropertySet == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org interface is not valid.");
    this.xPropertySet = xPropertySet;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns name of the master of a textfield. Returns null if a name is
   * not available.
   * 
   * @return name of the master of a textfield or null if a name is
   * not available
   * 
   * @author Andreas Bröker
   */
  public String getName() {
    try {
      Object object = xPropertySet.getPropertyValue("Name");
      if(object != null)
        return object.toString();
    }
    catch(Exception exception) {     
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns content of the master of a textfield. Returns null if a content
   * is not available.
   * 
   * @return content of the master of a textfield or null if a content
   * is not available
   * 
   * @author Andreas Bröker
   */
  public String getContent() {
    try {
      Object object = xPropertySet.getPropertyValue("Content");
      if(object != null)
        return object.toString();
    }
    catch(Exception exception) {
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets content of the master of a textfield.
   * 
   * @param content content to be used
   * 
   * @throws TextException if the new content can not be set
   *  
   * @author Andreas Bröker
   */
  public void setContent(String content) throws TextException {
    try {
      xPropertySet.setPropertyValue("Content", content);
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all related textfields of this textfield master.
   * 
   * @return all related textfields of this textfield master
   * 
   * @throws TextException if the textfields can not be fetched
   * 
   * @author Andreas Bröker
   */
  public ITextField[] getTextFields() throws TextException {
    try {
      Object object = xPropertySet.getPropertyValue("DependentTextFields");
      if(object == null)
        return EMPTY_TEXTFIELD_ARRAY; 
      
      XDependentTextField[] dependentTextFields = (XDependentTextField[])object;
      ITextField[] textFields = new ITextField[dependentTextFields.length];
      for(int i=0, n=dependentTextFields.length; i<n; i++) {
        textFields[i] = new TextField(textDocument, dependentTextFields[i]);
      }
      return textFields;
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new textfield on the basis of this textfield master.
   * 
   * @return new constructed textfield on the basis of this textfield master
   * 
   * @throws NOAException if the new textfield can not be constructed
   * 
   * @author Andreas Bröker
   * @date 16.02.2006
   */
  public ITextField constructNewTextField() throws NOAException {
  	try {
	  	XMultiServiceFactory xMultiServiceFactory = (XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, textDocument.getXTextDocument());
	    Object textField = xMultiServiceFactory.createInstance(ITextFieldService.USER_TEXTFIELD_ID);
	    XDependentTextField xDependentTextField = (XDependentTextField)UnoRuntime.queryInterface(XDependentTextField.class, textField);
	    xDependentTextField.attachTextFieldMaster(xPropertySet);
	    return new TextField(textDocument, xDependentTextField);
  	}
  	catch(Throwable throwable) {
  		throw new NOAException(throwable);
  	}
  }
  //----------------------------------------------------------------------------
  /**
   * Removes the master of a textfield from the document.
   *
   * @author Andreas Bröker
   */
  public void remove() {
    XComponent xComponent = (XComponent)UnoRuntime.queryInterface(XComponent.class, xPropertySet);
    if(xComponent != null)
      xComponent.dispose();
  }
  //----------------------------------------------------------------------------  
  
}