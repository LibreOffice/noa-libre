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

import com.sun.star.drawing.XControlShape;
import com.sun.star.form.XFormComponent;

/**
 * The implementation of a form in a document.
 * 
 * @author Markus Krüger
 * @version $Revision$
 */
public class Form extends FormComponent implements IForm, IFormComponent {
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new Form.
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
  public Form(IDocument document, XControlShape xControlShape, XFormComponent xFormComponent) throws IllegalArgumentException {
    super(document,xControlShape,xFormComponent);
  }  
  //----------------------------------------------------------------------------

}