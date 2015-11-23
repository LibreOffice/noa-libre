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
 * Last changes made by $Author: markus $, $Date: 2007-06-21 14:11:47 +0200 (Do, 21 Jun 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.event.IElementDisposedListener;
import ag.ion.bion.officelayer.internal.event.ElementDisposeListenerWrapper;

import ag.ion.bion.officelayer.text.AbstractTextComponent;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.bion.officelayer.text.ITextFieldMaster;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.noa.text.TextRangeSelection;

import com.sun.star.beans.XPropertySet;
import com.sun.star.frame.XController;
import com.sun.star.lang.XEventListener;
import com.sun.star.text.XDependentTextField;
import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextField;
import com.sun.star.text.XTextViewCursor;
import com.sun.star.text.XTextViewCursorSupplier;

import com.sun.star.uno.Any;
import com.sun.star.uno.UnoRuntime;

/**
 * Text field implementation of a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11496 $
 */
public class TextField extends AbstractTextComponent implements ITextField {
  
  private XDependentTextField xDependentTextField = null;
  private ElementDisposeListenerWrapper wrapper = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextField.
   * 
   * @param textDocument text document to be used
   * @param xDependentTextField OpenOffice.org XDependentTextField interface
   * 
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XDependentTextField interface  
   * is not valid
   * 
   * @author Andreas Bröker
   */
  public TextField(ITextDocument textDocument, XDependentTextField xDependentTextField) throws IllegalArgumentException {
    super(textDocument);
    
    if(xDependentTextField == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org interface is not valid.");
    this.xDependentTextField = xDependentTextField;       
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextField.
   * 
   * @param textDocument text document to be used
   * @param xTextField OpenOffice.org XTextField interface
   * 
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XTextField interface 
   * is not valid
   * 
   * @author Andreas Bröker 
   */
  public TextField(ITextDocument textDocument, XTextField xTextField) throws IllegalArgumentException {
    super(textDocument);

    xDependentTextField = (XDependentTextField)UnoRuntime.queryInterface(XDependentTextField.class, xTextField);
    if(xDependentTextField == null)
      throw new IllegalArgumentException("The submitted OpenOffice.org XTextField interface is not valid");
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextContent interface.
   * 
   * @return OpenOffice.org XTextContent interface
   * 
   * @author Andreas Bröker
   */
  public XTextContent getXTextContent() {
    return xDependentTextField;
  } 
  //----------------------------------------------------------------------------
  /**
   * Returns displayed text of the text field.
   * 
   * @return display text of the text field
   * 
   * @author Andreas Bröker
   */
  public String getDisplayText() {
    return xDependentTextField.getPresentation(false);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns master of the textfield.
   * 
   * @return master of the textfield.
   * 
   * @author Andreas Bröker
   */
  public ITextFieldMaster getTextFieldMaster() {
    return new TextFieldMaster(textDocument, xDependentTextField.getTextFieldMaster());
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text range of the textfield.
   * 
   * @return text range of the textfield
   * 
   * @author Andreas Bröker
   */
  public ITextRange getTextRange() {
    return new TextRange(textDocument, xDependentTextField.getAnchor());
  }  
  //----------------------------------------------------------------------------
  /**
   * Removes the text field from the document.
   * 
   * @author Andreas Bröker
   */
  public void remove() {
    xDependentTextField.dispose();    
  }
  //----------------------------------------------------------------------------
  /**
   * Adds a dispose listener to the field.
   * 
   * @param listener listene to be used
   * 
   * @author Sebastian Rösgen
   */
  public void addDisposeListener(IElementDisposedListener listener) {
    wrapper = new ElementDisposeListenerWrapper(listener);
    addEventListener(wrapper);
  }
  //----------------------------------------------------------------------------  
  /**
   * Removes the dispose listener belonging to the field
   * (if there is any).
   * 
   * @param listener the dispose listener to be removed
   * 
   * @author Sebastian Rösgen
   */
  public void removeDisposeListener(IElementDisposedListener listener) {
    if (wrapper != null)
      removeEventListener(wrapper);
  }
  //----------------------------------------------------------------------------
  /**
   * Adds the low level listener to this field.
   * 
   * @param xEventListener the event listener wrapper to be added
   * 
   * @author Sebastian Rösgen
   */
  private void addEventListener(XEventListener xEventListener) {
    XTextField xTextField = (XTextField)UnoRuntime.queryInterface(XTextField.class, xDependentTextField);
    xTextField.addEventListener(wrapper);
  }
  //----------------------------------------------------------------------------  
  /**
   * Removes the low level listener on this field.
   * 
   * @param xEventListener the event listener wrapper to be removed
   * 
   * @author Sebastian Rösgen
   */
  private void removeEventListener(XEventListener xEventListener) {
    XTextField xTextField = (XTextField)UnoRuntime.queryInterface(XTextField.class, xDependentTextField);
    xTextField.removeEventListener(wrapper);
  }
  //----------------------------------------------------------------------------
  /**
   * Marks the text field in the text.
   * 
   * @author Sebastian Rösgen
   * @author Markus Krüger
   */
  public void markTextField() {    
    try {
      ITextDocument textDocument = getTextDocument();
      textDocument.setSelection(new TextRangeSelection(getTextRange()));
      
      
      /*XTextDocument xTextDocument= getTextDocument().getXTextDocument();
      Object master1 = xDependentTextField.getTextFieldMaster();
      XText anchorText = xDependentTextField.getAnchor().getText();

      XTextCursor xTextCursor = anchorText.createTextCursor();
      xTextCursor.gotoStart(false);
      
      String fieldName = getDisplayText();      
      String completeString = anchorText.getString();
      completeString = completeString.replaceAll(System.getProperty("line.separator")," ");
      completeString = completeString.replaceAll("\\s"," ");
      int firstIndex = completeString.indexOf(fieldName);
      if(firstIndex == completeString.lastIndexOf(fieldName)) {
        xTextCursor.goRight((short)firstIndex,false);
      }
      else {   
        boolean stop = false;
        while(!stop) {
          XPropertySet propertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xTextCursor);
          Any value = (Any) propertySet.getPropertyValue("TextField");        
          XDependentTextField tmpDependentTextField = (XDependentTextField)UnoRuntime.queryInterface(XDependentTextField.class, value);
          if(tmpDependentTextField != null) {
            Object master2 = tmpDependentTextField.getTextFieldMaster();
            if(UnoRuntime.areSame(master1,master2)) {
              stop = true;   
              break;
            }
          }
          xTextCursor.goRight((short)1,false);
        }        
      }
      xTextCursor.goRight((short)1,true);
      
      XController viewController = xTextDocument.getCurrentController();
      XTextViewCursorSupplier xTextViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, viewController);
      XTextViewCursor xTextViewCursor = xTextViewCursorSupplier.getViewCursor();
      xTextViewCursor.gotoRange( xTextCursor, false );  */
    }
    catch (Exception exception) {
      //TODO exception handling if needed, do nothing for now
    }    
  }
  //----------------------------------------------------------------------------
  
}