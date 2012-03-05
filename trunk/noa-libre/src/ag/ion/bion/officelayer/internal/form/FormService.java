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
 * Copyright 2003-2007 by IOn AG                                            *
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
 * Last changes made by $Author$, $Date$
 */
package ag.ion.bion.officelayer.internal.form;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.form.IForm;
import ag.ion.bion.officelayer.form.IFormComponent;
import ag.ion.bion.officelayer.form.IFormService;

import ag.ion.noa.NOAException;

import com.sun.star.awt.XControlModel;
import com.sun.star.beans.XPropertySet;

import com.sun.star.container.XIndexContainer;
import com.sun.star.container.XNameContainer;
import com.sun.star.container.XNamed;

import com.sun.star.drawing.XControlShape;
import com.sun.star.drawing.XDrawPage;
import com.sun.star.drawing.XShape;
import com.sun.star.drawing.XShapes;

import com.sun.star.form.XForm;
import com.sun.star.form.XFormComponent;
import com.sun.star.form.XFormsSupplier;
import com.sun.star.form.XFormsSupplier2;

import com.sun.star.script.XEventAttacherManager;
import com.sun.star.uno.UnoRuntime;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the form service of a document.
 * 
 * @author Markus Krüger
 * @version $Revision$
 */
public class FormService implements IFormService {
  
  private IDocument      document       = null;
  private XDrawPage      xDrawPage      = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new FormService.
   * 
   * @param document the document of the service
   * @param xDrawPage the OpenOffice.org XDrawPage interface to be used
   * 
   * @throws IllegalArgumentException if the submitted document or the OpenOffice.org XFormsSupplier interface 
   * is not valid
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public FormService(IDocument document, XDrawPage xDrawPage) throws IllegalArgumentException {
    if(document == null)
      throw new IllegalArgumentException(Messages.getString("FormService.exception_document_invalid")); //$NON-NLS-1$
    this.document = document;
    if(xDrawPage == null)
      throw new IllegalArgumentException(Messages.getString("FormService.exception_xdrawPage_interface_invalid")); //$NON-NLS-1$
    this.xDrawPage = xDrawPage;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the forms for the given form name as an array. There will be only more 
   * than one form if the given form name occurs more than once.
   * 
   * @param formName the form name to be used
   * 
   * @return the forms for the given form name as an array
   * 
   * @throws NOAException if the return of forms fails
   * 
   * @author Markus Krüger
   * @date 26.01.2007
   */
  public IForm[] getForms(String formName) throws NOAException {
    try {
      if(formName != null) {
        XFormsSupplier formsSupplier = (XFormsSupplier) UnoRuntime.queryInterface(XFormsSupplier.class, xDrawPage);
        if(formsSupplier != null) {
          XNameContainer nameContainer = formsSupplier.getForms();
          XIndexContainer indexContainer = (XIndexContainer) UnoRuntime.queryInterface(XIndexContainer.class, nameContainer);
          int len = indexContainer.getCount();
          List forms = new ArrayList();
          for(int i = 0; i < len; i++) {
            Object tmpForm = indexContainer.getByIndex(i);
            if(tmpForm != null) {              
              XNamed tmpNamed = 
                (XNamed) UnoRuntime.queryInterface(XNamed.class, tmpForm);
              if(tmpNamed != null && tmpNamed.getName().equalsIgnoreCase(formName)) {
                XFormComponent tmpFormComponent = 
                  (XFormComponent) UnoRuntime.queryInterface(XFormComponent.class, tmpForm);
                if(tmpFormComponent != null) {
                  forms.add(new Form(document,null,tmpFormComponent));
                }
              }
            }
          }
          return (IForm[]) forms.toArray(new IForm[forms.size()]);
        }
      }
      return new IForm[0];
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XEventAttacherManager interface for the given form,
   * or null if not available.
   * 
   * @param form the form to be used
   * 
   * @return the OpenOffice.org XEventAttacherManager interface for the given form,
   * or null
   * 
   * @throws NOAException if the return of OpenOffice.org XEventAttacherManager interface fails
   * 
   * @author Markus Krüger
   * @date 26.01.2007
   */
  public XEventAttacherManager getXEventAttacherManager(IForm form) throws NOAException {
    try {
      if(form != null) {
        XFormsSupplier formsSupplier = (XFormsSupplier) UnoRuntime.queryInterface(XFormsSupplier.class, xDrawPage);
        if(formsSupplier != null) {
          XNameContainer nameContainer = formsSupplier.getForms();
          XIndexContainer indexContainer = (XIndexContainer) UnoRuntime.queryInterface(XIndexContainer.class, nameContainer);
          int len = indexContainer.getCount();
          for(int i = 0; i < len; i++) { 
            XForm tmpForm = (XForm) UnoRuntime.queryInterface(XForm.class, indexContainer.getByIndex(i));
            if(tmpForm != null && UnoRuntime.areSame(form.getXFormComponent(),tmpForm)) {
              XEventAttacherManager tmpEventAttacherManager = 
                (XEventAttacherManager) UnoRuntime.queryInterface(XEventAttacherManager.class, tmpForm);
              return tmpEventAttacherManager;
            }
          }
        }
      }
      return null;
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the index of the given form component in the given form, or -1 if not found.
   * 
   * @param form the form to check index in
   * @param formComponent the form component to get index for
   * 
   * @return the index of the given form component in the given form, or -1
   * 
   * @throws NOAException if anything fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public int getIndexInForm(IForm form, IFormComponent formComponent) throws NOAException {
    try {
      if(form!= null && formComponent != null) {
        XFormsSupplier formsSupplier = (XFormsSupplier) UnoRuntime.queryInterface(XFormsSupplier.class, xDrawPage);
        if(formsSupplier != null) {
          XNameContainer nameContainer = formsSupplier.getForms();
          XIndexContainer indexContainer = (XIndexContainer) UnoRuntime.queryInterface(XIndexContainer.class, nameContainer);
          int len = indexContainer.getCount();
          for(int i = 0; i < len; i++) {
            XForm tmpForm = (XForm) UnoRuntime.queryInterface(XForm.class, indexContainer.getByIndex(i));
            if(tmpForm != null && UnoRuntime.areSame(form.getXFormComponent(),tmpForm)) {    
              XIndexContainer container = (XIndexContainer) UnoRuntime.queryInterface(XIndexContainer.class, tmpForm);
              int lenFormPComponents = container.getCount();           
              for(int j = 0; j < lenFormPComponents; j++) {
                Object tmpObject = container.getByIndex(j);
                if(tmpObject != null) {
                  XFormComponent tmpFormComponent = (XFormComponent) UnoRuntime.queryInterface(XFormComponent.class, tmpObject);
                  if(tmpFormComponent != null && UnoRuntime.areSame(tmpFormComponent,formComponent.getXFormComponent()))
                    return j;
                }
              }
            }
          }
        }        
      }
      return -1;
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns if the component has forms.
   * 
   * @return if the component has forms
   * 
   * @throws NOAException if the check fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public boolean hasForms() throws NOAException {
    XFormsSupplier2 formsSupplier2 = ((XFormsSupplier2) UnoRuntime.queryInterface(XFormsSupplier2.class, xDrawPage));
    if(formsSupplier2 != null)
      return formsSupplier2.hasForms();
    XFormsSupplier formsSupplier = ((XFormsSupplier) UnoRuntime.queryInterface(XFormsSupplier.class, xDrawPage));
    if(formsSupplier != null)
      return formsSupplier.getForms().hasElements();
    return false;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns if the component contains other form components.
   * 
   * @return if the component contains other form components
   * 
   * @throws NOAException if the check fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public boolean hasFormComponents() throws NOAException {
    try {
      XShapes xShapes = (XShapes)UnoRuntime.queryInterface(XShapes.class,xDrawPage);
      if(xShapes != null) {
        int shapeCount = xShapes.getCount();
        for(int i=0;i<shapeCount;i++) {
          XShape xShape = (XShape)UnoRuntime.queryInterface(XShape.class,xShapes.getByIndex(i));
          if(xShape != null) {
            XControlShape controlShape = (XControlShape)UnoRuntime.queryInterface(XControlShape.class,xShape);
            if(controlShape != null) {
              XControlModel control = controlShape.getControl();
              if(control != null) {
                XFormComponent xFormComponent = (XFormComponent)UnoRuntime.queryInterface(XFormComponent.class,control);
                if(xFormComponent != null) {
                  return true;
                }
              }            
            }
          }
        } 
      }
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
    return false;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns all form components contained in this component.
   * 
   * @return all form components contained in this component
   * 
   * @throws NOAException if the return fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public IFormComponent[] getFormComponents() throws NOAException {
    try {
      XShapes xShapes = (XShapes)UnoRuntime.queryInterface(XShapes.class,xDrawPage);
      if(xShapes != null) {
        int shapeCount = xShapes.getCount();
        List formComponents = new ArrayList();
        for(int i=0;i<shapeCount;i++) {
          XShape xShape = (XShape)UnoRuntime.queryInterface(XShape.class,xShapes.getByIndex(i));
          if(xShape != null) {
            XControlShape controlShape = (XControlShape)UnoRuntime.queryInterface(XControlShape.class,xShape);
            if(controlShape != null) {
              XControlModel controlModel = controlShape.getControl();
              if(controlModel != null) {
                XFormComponent xFormComponent = (XFormComponent)UnoRuntime.queryInterface(XFormComponent.class,controlModel);
                if(xFormComponent != null) {
                  formComponents.add(new FormComponent(document,controlShape,xFormComponent));
                }
              }            
            }
          }
        } 
        return (IFormComponent[]) formComponents.toArray(new IFormComponent[formComponents.size()]);
      }
      return new IFormComponent[0];
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns all form components names contained in this component.
   * 
   * @return all form components names contained in this component
   * 
   * @throws NOAException if the return fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public String[] getFormComponentsNames() throws NOAException {
    try {
      XShapes xShapes = (XShapes)UnoRuntime.queryInterface(XShapes.class,xDrawPage);
      if(xShapes != null) {
        int shapeCount = xShapes.getCount();
        List formComponentNames = new ArrayList();
        for(int i=0;i<shapeCount;i++) {
          XShape xShape = (XShape)UnoRuntime.queryInterface(XShape.class,xShapes.getByIndex(i));
          if(xShape != null) {
            XControlShape controlShape = (XControlShape)UnoRuntime.queryInterface(XControlShape.class,xShape);
            if(controlShape != null) {
              XControlModel control = controlShape.getControl();
              if(control != null) {
                XFormComponent xFormComponent = (XFormComponent)UnoRuntime.queryInterface(XFormComponent.class,control);
                if(xFormComponent != null) {
                  XPropertySet propertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xFormComponent);
                  if(propertySet != null && propertySet.getPropertySetInfo().hasPropertyByName("Name"))
                    formComponentNames.add(propertySet.getPropertyValue("Name"));
                }
              }            
            }
          }
        } 
        return (String[]) formComponentNames.toArray(new String[formComponentNames.size()]);
      }
      return new String[0];
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Removes the given form component.
   * 
   * @param formComponent the form component to remove
   * 
   * @throws NOAException if the remove fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public void removeFormComponent(IFormComponent formComponent) throws NOAException {
    if(formComponent != null)
      xDrawPage.remove(formComponent.getXControlShape());
  }  
  //----------------------------------------------------------------------------

}