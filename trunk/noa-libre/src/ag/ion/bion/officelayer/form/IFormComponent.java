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
package ag.ion.bion.officelayer.form;

import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.noa.NOAException;

import com.sun.star.awt.XControl;
import com.sun.star.awt.XTextComponent;
import com.sun.star.beans.XPropertySet;
import com.sun.star.drawing.XControlShape;
import com.sun.star.form.XFormComponent;

/**
 * The interface for a form component in a document.
 * 
 * @author Markus Krüger
 * @version $Revision$
 */
public interface IFormComponent {
  
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
  public XPropertySet getXPropertySet() throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XFormComponent interface.
   * 
   * @return the OpenOffice.org XFormComponent interface
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public XFormComponent getXFormComponent();  
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XControlShape interface, can be null.
   * 
   * @return the OpenOffice.org XControlShape interface, or null
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public XControlShape getXControlShape();  
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
  public XControl getXControl() throws NOAException;  
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
  public XTextComponent getXTextComponent() throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Sets the focus to this from component.
   * 
   * @throws NOAException if the focus fails
   * 
   * @author Markus Krüger
   * @date 29.01.2007
   */
  public void focus() throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Returns the text range of the anchor where the form component starts.
   * 
   * @return the text range of the anchor where the form component starts
   * 
   * @throws NOAException if the return of text range fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public ITextRange getStartTextRange() throws NOAException; 
  //----------------------------------------------------------------------------

}