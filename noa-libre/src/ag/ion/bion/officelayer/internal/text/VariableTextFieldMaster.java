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

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.bion.officelayer.text.ITextFieldService;
import ag.ion.bion.officelayer.text.IVariableTextFieldMaster;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.util.INumberFormat;
import ag.ion.bion.officelayer.util.INumberFormatService;
import ag.ion.bion.officelayer.util.VariableTextFieldHelper;

import ag.ion.noa.NOAException;

import com.sun.star.beans.XPropertySet;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiServiceFactory;

import com.sun.star.text.XDependentTextField;

import com.sun.star.uno.UnoRuntime;

/**
 * Master of a variable text field of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class VariableTextFieldMaster implements IVariableTextFieldMaster {
  
  private static final ITextField[] EMPTY_TEXTFIELD_ARRAY = new ITextField[0];
  
  private ITextDocument textDocument = null;
  private XPropertySet  xPropertySet = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new VariableTextFieldMaster.
   * 
   * @param textDocument text document to be used
   * @param xPropertySet OpenOffice.org XPropertySet interface to be used
   * 
   * @throws IllegalArgumentException if the submitted text document or 
   * OpenOffice.org XPropertySet interface is not valid
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public VariableTextFieldMaster(ITextDocument textDocument, XPropertySet xPropertySet) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("The submitted text document is not valid.");
    this.textDocument = textDocument;
    
    if(xPropertySet == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org interface is not valid.");
    this.xPropertySet = xPropertySet;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the property set of this master.
   * 
   * @return the property set of this master
   * 
   * @author Markus Krüger
   * @date 18.07.2007
   */
  public XPropertySet getXPropertySet() {
    return xPropertySet;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns name of the master of a variable text field. Returns null if a name is
   * not available.
   * 
   * @return name of the master of a variable text field or null if a name is
   * not available
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public String getName() {
    try {
      Object object = xPropertySet.getPropertyValue("Name");
      if(object != null)
        return object.toString();
    }
    catch(Exception exception) {   
      //ignore
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all related variable text fields of this variable text field master.
   * 
   * @return all related variable text fields of this variable text field master
   * 
   * @throws TextException if the variable text fields can not be fetched
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public ITextField[] getVariableTextFields() throws TextException {
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
   * Constructs new variable text field on the basis of this variable text field master.
   * TODO maybe some more parameters are needed???
   * 
   * @param content the content of the variable textfield
   * @param visible if the variable should be visible
   * 
   * @return new constructed variable text field on the basis of this variable text field master
   * 
   * @throws NOAException if the new variable text field can not be constructed
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public ITextField constructNewVariableTextField(String content, boolean visible) throws NOAException {
    return constructNewVariableTextField(content,visible,null,false);
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new variable text field on the basis of this variable text field master.
   * TODO maybe some more parameters are needed???
   * 
   * @param content the content of the variable textfield
   * @param visible if the variable should be visible
   * @param numberFormat the number format used for the variable
   * @param isFormula if the given content is a formula
   * 
   * @return new constructed variable text field on the basis of this variable text field master
   * 
   * @throws NOAException if the new variable text field can not be constructed
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public ITextField constructNewVariableTextField(String content, boolean visible,
      INumberFormat numberFormat, boolean isFormula) throws NOAException {
    try {
      XMultiServiceFactory xMultiServiceFactory = (XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, textDocument.getXTextDocument());
      Object textField = xMultiServiceFactory.createInstance(ITextFieldService.VARIABLES_TEXTFIELD_ID);
      XDependentTextField xDependentTextField = (XDependentTextField)UnoRuntime.queryInterface(XDependentTextField.class, textField);
      xDependentTextField.attachTextFieldMaster(xPropertySet);

      XPropertySet xPropertySetField = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xDependentTextField);
      xPropertySetField.setPropertyValue("IsVisible", new Boolean(visible));
      xPropertySetField.setPropertyValue("IsFixedLanguage", new Boolean(false));  
      
      ITextField variableTextField = new TextField(textDocument, xDependentTextField);

      INumberFormatService numberFormatService = textDocument.getNumberFormatService();
      VariableTextFieldHelper.setContent(content,variableTextField,isFormula,numberFormatService);
      if(numberFormat != null)
        VariableTextFieldHelper.applyNumberFormat(numberFormat,variableTextField,isFormula,numberFormatService);
      
      return variableTextField;
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Removes the master of a variable text field from the document.
   *
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public void remove() {
    XComponent xComponent = (XComponent)UnoRuntime.queryInterface(XComponent.class, xPropertySet);
    if(xComponent != null)
      xComponent.dispose();
  }
  //----------------------------------------------------------------------------  
  
}