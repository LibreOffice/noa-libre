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
import ag.ion.bion.officelayer.form.IFormComponent;
import ag.ion.bion.officelayer.internal.document.Messages;
import ag.ion.bion.officelayer.internal.text.TextRange;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;

import ag.ion.noa.NOAException;

import com.sun.star.awt.XControl;
import com.sun.star.awt.XTextComponent;
import com.sun.star.awt.XWindow;
import com.sun.star.beans.XPropertySet;
import com.sun.star.drawing.XControlShape;
import com.sun.star.form.XFormComponent;
import com.sun.star.text.XTextContent;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.view.XControlAccess;

/**
 * The implementation of a form component in a document.
 * 
 * @author Markus Krüger
 * @version $Revision$
 */
public class FormComponent implements IFormComponent {
  
  private IDocument       document        = null;
  private XControlShape   xControlShape   = null;
  private XFormComponent  xFormComponent  = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new FormComponent.
   * 
   * @param document the document of the form component
   * @param xControlShape the OpenOffice.org XControlShape interface to be used
   * @param xFormComponent the OpenOffice.org XFormComponent interface to be used
   * 
   * @throws IllegalArgumentException if the submitted document or the OpenOffice.org 
   * XFormComponent or XControlShape interface is not valid
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public FormComponent(IDocument document, XControlShape xControlShape, XFormComponent xFormComponent) throws IllegalArgumentException {
    if(document == null)
      throw new IllegalArgumentException(Messages.getString("Form.exception_document_invalid")); //$NON-NLS-1$
    if(xFormComponent == null)
      throw new IllegalArgumentException(Messages.getString("Form.exception_xformcomponent_interface_invalid")); //$NON-NLS-1$
    this.document = document;
    this.xFormComponent = xFormComponent;
    this.xControlShape = xControlShape;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the property set of this form component, or null if not available.
   * 
   * @return the property set of this form component, or null
   * 
   * @throws NOAException if the return fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public XPropertySet getXPropertySet() throws NOAException {
    return (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xFormComponent);
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XFormComponent interface.
   * 
   * @return the OpenOffice.org XFormComponent interface
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public XFormComponent getXFormComponent() {
    return xFormComponent;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XControlShape interface, can be null.
   * 
   * @return the OpenOffice.org XControlShape interface, or null
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public XControlShape getXControlShape() {
    return xControlShape;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XControl interface, can be null.
   * 
   * @return the OpenOffice.org XControl interface, or null
   * 
   * @throws NOAException if the return fails
   * 
   * @author Markus Krüger
   * @date 29.01.2007
   */
  public XControl getXControl() throws NOAException {
    try {
      if(xControlShape != null) {
        XControlAccess controlAccess = 
          (XControlAccess)UnoRuntime.queryInterface(XControlAccess.class,((ITextDocument)document).getXTextDocument().getCurrentController());
        XControl control = controlAccess.getControl(xControlShape.getControl());
        return control;
      }
      return null;
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XTextComponent interface, or null if not available.
   * 
   * @return the OpenOffice.org XTextComponent interface, or null
   * 
   * @throws NOAException if the return fails
   * 
   * @author Markus Krüger
   * @date 29.01.2007
   */
  public XTextComponent getXTextComponent() throws NOAException {
    XControl xControl = getXControl();
    if(xControl != null)
      return (XTextComponent) UnoRuntime.queryInterface(XTextComponent.class,xControl);
    return null;
  }  
  //----------------------------------------------------------------------------
  /**
   * Sets the focus to this from component.
   * 
   * @throws NOAException if the focus fails
   * 
   * @author Markus Krüger
   * @date 29.01.2007
   */
  public void focus() throws NOAException {
    XControl xControl = getXControl();
    if(xControl != null) {
      // the focus can be set to an XWindow only
      XWindow xControlWindow = (XWindow)UnoRuntime.queryInterface( XWindow.class,
          xControl );
      // grab the focus
      xControlWindow.setFocus();
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the text range of the anchor where the form component starts, or null
   * if not available.
   * 
   * @return the text range of the anchor where the form component starts,or null
   * 
   * @throws NOAException if the return of text range fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public ITextRange getStartTextRange() throws NOAException {
    XTextContent textContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class,xControlShape);
    if(textContent != null)
      return new TextRange(document,textContent.getAnchor());
    return null;
  } 
  //----------------------------------------------------------------------------

}