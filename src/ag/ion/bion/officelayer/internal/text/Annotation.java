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
 * Copyright 2003-2006 by IOn AG                                            *
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

import ag.ion.bion.officelayer.text.AbstractTextComponent;
import ag.ion.bion.officelayer.text.IAnnotation;
import ag.ion.bion.officelayer.text.ITextComponent;
import ag.ion.bion.officelayer.text.ITextContent;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.bion.officelayer.text.ITextFieldService;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.IViewCursor;

import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.text.XDependentTextField;
import com.sun.star.text.XTextContent;
import com.sun.star.uno.UnoRuntime;

/**
 * Annotation of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 11572 $
 */
public class Annotation extends AbstractTextComponent implements IAnnotation, ITextContent, ITextComponent {
  
  private XDependentTextField xDependentTextField = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new Annotation.
   * 
   * @param textDocument text document to be used
   * @param textField the text field to be used
   *  
   * @throws IllegalArgumentException if one the OpenOffice.org interface is not valid
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public Annotation(ITextDocument textDocument, ITextField textField) throws IllegalArgumentException {
    super(textDocument);    
    if(textField == null || !(textField.getXTextContent() instanceof XDependentTextField))
      throw new IllegalArgumentException("Submitted OpenOffice.org interface is not valid.");
    this.xDependentTextField = (XDependentTextField)textField.getXTextContent();    
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new Annotation.
   * 
   * @param textDocument text document to be used
   * @param xDependentTextField OpenOffice.org XDependentTextField interface
   *  
   * @throws IllegalArgumentException if one the OpenOffice.org interface is not valid
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public Annotation(ITextDocument textDocument, XDependentTextField xDependentTextField) throws IllegalArgumentException {
    super(textDocument);    
    if(xDependentTextField == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org interface is not valid.");
    this.xDependentTextField = xDependentTextField;    
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the text content of the annotation, or null if text content is not available.
   * 
   * @return the text content of the annotation, or null
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public String getText() {
    XServiceInfo info = (XServiceInfo) UnoRuntime.queryInterface(XServiceInfo.class, getXTextContent());
    if(info.supportsService(ITextFieldService.ANNOTATION_TEXTFIELD_ID)) {
      XPropertySet properties = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, getXTextContent());
      try {
        return (String) properties.getPropertyValue("Content");
      }
      catch(UnknownPropertyException unknownPropertyException) {
        //TODO exception handling if needed, do nothing for now
      }
      catch(WrappedTargetException wrappedTargetException) {
        //TODO exception handling if needed, do nothing for now
      }
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text range of the textfield.
   * 
   * @return text range of the textfield
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public ITextRange getTextRange() {
    return new TextRange(textDocument, xDependentTextField.getAnchor());
  }  
  //----------------------------------------------------------------------------
  /**
   * Jumps to the annotation.
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public void jumpTo() {
    IViewCursor viewCursor = getTextDocument().getViewCursorService().getViewCursor();
    viewCursor.goToRange(getTextRange(),false);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextContent interface.
   * 
   * This method is not part of the public API. It should
   * be never used from outside.
   * 
   * @return OpenOffice.org XTextContent interface
   * 
   * @author Andreas Bröker
   * @date 13.07.2006
   */
  public XTextContent getXTextContent() {
    return xDependentTextField;
  }
  //----------------------------------------------------------------------------
  
}