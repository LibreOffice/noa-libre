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
 * Last changes made by $Author: markus $, $Date: 2007-09-19 16:38:05 +0200 (Mi, 19 Sep 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.bion.officelayer.text.ITextFieldMaster;
import ag.ion.bion.officelayer.text.ITextFieldService;
import ag.ion.bion.officelayer.text.IVariableTextFieldMaster;
import ag.ion.bion.officelayer.text.TextException;

import com.sun.star.beans.XPropertySet;

import com.sun.star.container.NoSuchElementException;
import com.sun.star.container.XEnumeration;
import com.sun.star.container.XEnumerationAccess;
import com.sun.star.container.XNameAccess;

import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XServiceInfo;

import com.sun.star.text.XDependentTextField;
import com.sun.star.text.XTextField;
import com.sun.star.text.XTextFieldsSupplier;

import com.sun.star.uno.Any;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XRefreshable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Textfield service of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11575 $
 */
public class TextFieldService implements ITextFieldService {
  
  private static final ITextFieldMaster[] EMPTY_TEXTFIELD_MASTER_ARRAY = new ITextFieldMaster[0];  
  
  private static final String USER_TEXTFIELD_MASTER_PREFIX       = ITextFieldService.USER_TEXTFIELD_MASTER_ID+".";
  private static final String VARIABLES_TEXTFIELD_MASTER_PREFIX  = ITextFieldService.VARIABLES_TEXTFIELD_MASTER_ID+".";
  
  private ITextDocument textDocument = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextFieldService.
   * 
   * @param textDocument text document to be used
   * 
   * @throws IllegalArgumentException if the OpenOffice.org interface is not valid
   * 
   * @author Andreas Bröker
   */
  public TextFieldService(ITextDocument textDocument) {
    if(textDocument == null)
      throw new IllegalArgumentException("The submitted text document is not valid.");
    this.textDocument = textDocument;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns master of a user textfield with the submitted name. Returns null if
   * a user textfield with the submitted name is not available.
   * 
   * @param name name of the master of the user textfield
   * 
   * @return master of a user textfield with the submitted name or null if a user textfield with
   * the submitted name is not available
   * 
   * @throws TextException if the user text field can not be provided
   * 
   * @author Andreas Bröker
   */
  public ITextFieldMaster getUserTextFieldMaster(String name) throws TextException {
    try {
      XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(XTextFieldsSupplier.class, textDocument.getXTextDocument());
      XNameAccess xNameAccess = xTextFieldsSupplier.getTextFieldMasters();
      Any any = null;
      
      try {
        any = (Any)xNameAccess.getByName(USER_TEXTFIELD_MASTER_PREFIX + name);
      }
      catch(NoSuchElementException noSuchElementException) {
        return null;
      }
      
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, any);
      if(xPropertySet != null)
        return new TextFieldMaster(textDocument, xPropertySet);
      else
        return null;
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns masters of the user textfields with the submitted name prefix.
   * 
   * @param prefix name prefix to be used
   * 
   * @return masters of the user textfields with the submitted name prefix
   * 
   * @throws TextException if the masters of the user textfields can not be constructed
   * 
   * @author Andreas Bröker
   */
  public ITextFieldMaster[] getUserTextFieldMasters(String prefix) throws TextException {
    if(prefix == null)
      return EMPTY_TEXTFIELD_MASTER_ARRAY;
    
    try {
      XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(XTextFieldsSupplier.class, textDocument.getXTextDocument());
      XNameAccess xNameAccess = xTextFieldsSupplier.getTextFieldMasters();
      String[] names = xNameAccess.getElementNames();
      ArrayList arrayList = new ArrayList();
      Any any = null;
      XPropertySet xPropertySet = null;
      for(int i=0, n=names.length; i<n; i++) {
        String name = names[i];
        if(name.toLowerCase().startsWith(USER_TEXTFIELD_MASTER_PREFIX.toLowerCase())) {
          String fieldName = name.substring(USER_TEXTFIELD_MASTER_PREFIX.length());
          if(fieldName.startsWith(prefix)) {                        
            try {
              any = (Any)xNameAccess.getByName(name);
              xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, any);
              if(xPropertySet != null)
                arrayList.add(new TextFieldMaster(textDocument, xPropertySet));
            }
            catch(NoSuchElementException noSuchElementException) {
              //do nothing
            }
          }
        }
      }  
      return (ITextFieldMaster[])arrayList.toArray(new ITextFieldMaster[arrayList.size()]);      
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns masters of the user textfields with the submitted name prefix and suffix.
   * 
   * @param prefix name prefix to be used
   * @param suffix name suffix to be used
   * 
   * @return masters of the user textfields with the submitted name prefix and suffix
   * 
   * @throws TextException if the masters of the user textfields can not be constructed
   * 
   * @author Markus Krüger
   */
  public ITextFieldMaster[] getUserTextFieldMasters(String prefix, String suffix) throws TextException {
    if(prefix == null)
      return EMPTY_TEXTFIELD_MASTER_ARRAY;
    
    try {
      XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(XTextFieldsSupplier.class, textDocument.getXTextDocument());
      XNameAccess xNameAccess = xTextFieldsSupplier.getTextFieldMasters();
      String[] names = xNameAccess.getElementNames();
      ArrayList arrayList = new ArrayList();
      Any any = null;
      XPropertySet xPropertySet = null;
      for(int i=0, n=names.length; i<n; i++) {
        String name = names[i];
        if(name.toLowerCase().startsWith(USER_TEXTFIELD_MASTER_PREFIX.toLowerCase())) {
          String fieldName = name.substring(USER_TEXTFIELD_MASTER_PREFIX.length());
          if(fieldName.startsWith(prefix) && fieldName.endsWith(suffix)) {                        
            try {
              any = (Any)xNameAccess.getByName(name);
              xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, any);
              if(xPropertySet != null)
                arrayList.add(new TextFieldMaster(textDocument, xPropertySet));
            }
            catch(NoSuchElementException noSuchElementException) {
              //do nothing
            }
          }
        }
      }  
      return (ITextFieldMaster[])arrayList.toArray(new ITextFieldMaster[arrayList.size()]);      
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Adds new user textfield.
   * 
   * @param name name of the textfield
   * @param content content of the textfield
   * 
   * @return new textfield
   * 
   * @throws TextException if any error occurs during textfield creation
   * 
   * @author Andreas Bröker
   */
  public ITextField addUserTextField(String name, String content) throws TextException {
    try {
      XMultiServiceFactory xMultiServiceFactory = (XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, textDocument.getXTextDocument());
      Object textField = xMultiServiceFactory.createInstance(ITextFieldService.USER_TEXTFIELD_ID);
      XDependentTextField xDependentTextField = (XDependentTextField)UnoRuntime.queryInterface(XDependentTextField.class, textField);
      
      Object oFieldMaster = xMultiServiceFactory.createInstance(ITextFieldService.USER_TEXTFIELD_MASTER_ID);
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, oFieldMaster);
  
      xPropertySet.setPropertyValue("Name", name);
      xPropertySet.setPropertyValue("Content", content);
  
      xDependentTextField.attachTextFieldMaster(xPropertySet);
      
      return new TextField(textDocument, xDependentTextField);
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //---------------------------------------------------------------------------- 
  /**
   * Returns all available user textfields.
   * 
   * @return all available user textfields
   * 
   * @throws TextException if the user textfields can not be constructed
   * 
   * @author Andreas Bröker
   */
  public ITextField[] getUserTextFields() throws TextException {
    try {
      XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(XTextFieldsSupplier.class, textDocument.getXTextDocument());
      XEnumerationAccess xEnumerationAccess = xTextFieldsSupplier.getTextFields();
      XEnumeration xEnumeration = xEnumerationAccess.createEnumeration();
      ArrayList arrayList = new ArrayList();
      while(xEnumeration.hasMoreElements()) {
        Object object = xEnumeration.nextElement();
        XTextField xTextField = (XTextField)UnoRuntime.queryInterface(XTextField.class, object);
        arrayList.add(new TextField(textDocument, xTextField));
      }
      return (ITextField[])arrayList.toArray(new ITextField[arrayList.size()]);
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }  
  //----------------------------------------------------------------------------   
  /**
   * Returns all available placeholder textfields.
   * 
   * @return all available placeholder textfields
   * 
   * @throws TextException if the placeholder textfields can not be constructed
   * 
   * @author Markus Krüger
   * @date 23.01.2007
   */
  public ITextField[] getPlaceholderFields() throws TextException {
    try {
      XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(XTextFieldsSupplier.class, textDocument.getXTextDocument());
      XEnumerationAccess xEnumerationAccess = xTextFieldsSupplier.getTextFields();
      XEnumeration xEnumeration = xEnumerationAccess.createEnumeration();
      ArrayList arrayList = new ArrayList();
      while(xEnumeration.hasMoreElements()) {
        Object object = xEnumeration.nextElement();
        XTextField xTextField = (XTextField)UnoRuntime.queryInterface(XTextField.class, object);
        XServiceInfo xInfo = (XServiceInfo)UnoRuntime.queryInterface(XServiceInfo.class, xTextField); 
        if(xInfo.supportsService(ITextFieldService.PLACEHOLDER_TEXTFIELD_ID)){
          arrayList.add(new TextField(textDocument, xTextField));
        }
      }
      return (ITextField[])arrayList.toArray(new ITextField[arrayList.size()]);
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------  
  /**
   * Creates a new placeholder textfield.
   * 
   * @param name name of the placeholder textfield
   * @param hint the hint of the placeholder textfield, may be null
   * @param placeholderType the type of the placeholder found in static 
   * members of com.sun.star.text.PlaceholderType (i.e. PlaceholderType.TEXT)
   * 
   * @return new placeholder textfield
   * 
   * @throws TextException if any error occurs during placeholder textfield creation
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public ITextField createPlaceholderTextField(String name, String hint, short placeholderType) throws TextException {
    try {
      if(name == null)
        throw new TextException("The placeholders name to create can not be null.");
      if(placeholderType < 0 || placeholderType > 4)
        throw new TextException("The placeholder type must be one of the valid static members of com.sun.star.text.PlaceholderType.");
      XMultiServiceFactory xMultiServiceFactory = (XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, textDocument.getXTextDocument());
      Object textField = xMultiServiceFactory.createInstance(ITextFieldService.PLACEHOLDER_TEXTFIELD_ID);

      XTextField xTextField = (XTextField)UnoRuntime.queryInterface(XTextField.class, textField);
      
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xTextField);
  
      // Grundeinstellung festlegen
      xPropertySet.setPropertyValue("PlaceHolder", name);
      xPropertySet.setPropertyValue("PlaceHolderType",new Short(placeholderType));
      if(hint != null)
        xPropertySet.setPropertyValue("Hint", hint);
      
      return new TextField(textDocument, xTextField);
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //---------------------------------------------------------------------------- 
  /**
   * Returns master of the variables text fields with the submitted name, or null if not availbale.
   * 
   * @param masterName name of the master to return
   * 
   * @return master of the variables text fields with the submitted name, or null
   * 
   * @throws TextException if the master can not be returned
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public IVariableTextFieldMaster getVariableTextFieldMaster(String masterName) throws TextException {
    try {
      XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(XTextFieldsSupplier.class, textDocument.getXTextDocument());
      XNameAccess xNameAccess = xTextFieldsSupplier.getTextFieldMasters();
      Any any = null;
      
      try {
        String name = masterName;
        if(!name.toLowerCase().startsWith(VARIABLES_TEXTFIELD_MASTER_PREFIX.toLowerCase()))
          name = VARIABLES_TEXTFIELD_MASTER_PREFIX+name;
        any = (Any)xNameAccess.getByName(name);
      }
      catch(NoSuchElementException noSuchElementException) {
        return null;
      }
      
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, any);
      if(xPropertySet != null)
        return new VariableTextFieldMaster(textDocument, xPropertySet);
      return null;
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all masters of the variables text fields with the submitted name prefix.
   * 
   * @param prefix name prefix to be used
   * 
   * @return all masters of the variables text fields
   * 
   * @throws TextException if the masters can not be returned
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public IVariableTextFieldMaster[] getVariableTextFieldMasters(String prefix) throws TextException {
    try {
      XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(XTextFieldsSupplier.class, textDocument.getXTextDocument());
      XNameAccess xNameAccess = xTextFieldsSupplier.getTextFieldMasters();
      String[] names = xNameAccess.getElementNames();
      List masters = new ArrayList();
      for(int i = 0; i < names.length; i++) {
        if(names[i].toLowerCase().startsWith((VARIABLES_TEXTFIELD_MASTER_PREFIX+prefix).toLowerCase())) {
          Any any = null;          
          try {
            any = (Any)xNameAccess.getByName(names[i]);
          }
          catch(NoSuchElementException noSuchElementException) {
            continue;
          }          
          XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, any);
          if(xPropertySet != null)
            masters.add(new VariableTextFieldMaster(textDocument, xPropertySet));
        }
      }
      return (IVariableTextFieldMaster[]) masters.toArray(new IVariableTextFieldMaster[masters.size()]);
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Creates a new variable textfield master and returns it, or returns the one that
   * already exists, if it does.
   * TODO maybe some more parameters are needed???
   * 
   * @param name name of the variable textfield master
   * @param variableType the type of the variable master found in static 
   * members of com.sun.star.text.SetVariableType (i.e. SetVariableType.STRING)
   * 
   * @return the variable textfield master with the given name
   * 
   * @throws TextException if any error occurs during variable textfield master creation
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public IVariableTextFieldMaster createVariableTextFieldMaster(String name, short variableType) throws TextException {
    try {
      if(name == null)
        throw new TextException("The variable name to create can not be null.");
      if(variableType < 0 || variableType > 3)
        throw new TextException("The variable type must be one of the valid static members of com.sun.star.text.SetVariableType.");
      
      XMultiServiceFactory xMultiServiceFactory = (XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, textDocument.getXTextDocument());

      Object oFieldMaster = xMultiServiceFactory.createInstance(ITextFieldService.VARIABLES_TEXTFIELD_MASTER_ID);
      XPropertySet xMasterPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, oFieldMaster);
        
      // Grundeinstellung festlegen
      xMasterPropertySet.setPropertyValue("Name", name);
      xMasterPropertySet.setPropertyValue("SubType",new Short(variableType));
      return new VariableTextFieldMaster(textDocument, xMasterPropertySet);
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //---------------------------------------------------------------------------- 
  /**
   * Returns all available variable textfields with the submitted name prefix.
   * 
   * @param prefix name prefix to be used
   * 
   * @return all available variable textfields
   * 
   * @throws TextException if the variable textfields can not be constructed
   * 
   * @author Markus Krüger
   * @date 29.05.2007
   */
  public ITextField[] getVariableFields(String prefix) throws TextException {
    try {
      IVariableTextFieldMaster[] masters = getVariableTextFieldMasters(prefix);
      List arrayList = new ArrayList();
      for(int i = 0; i < masters.length; i++) {
        ITextField[] variables = masters[i].getVariableTextFields();
        arrayList.addAll(Arrays.asList(variables));
      }
      return (ITextField[])arrayList.toArray(new ITextField[arrayList.size()]);
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------   
  /**
   * Refreshes all textfields.
   * 
   * @throws TextException if refresh fails
   * 
   * @author Markus Krüger
   * @date 29.05.2007
   */
  public void refresh() throws TextException {
    try {
      XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(XTextFieldsSupplier.class, textDocument.getXTextDocument());
      XRefreshable xRefreshable = (XRefreshable)UnoRuntime.queryInterface(XRefreshable.class, xTextFieldsSupplier.getTextFields());
      xRefreshable.refresh();
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }
  //----------------------------------------------------------------------------  
  
}