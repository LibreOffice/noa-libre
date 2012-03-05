/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                     						*
 * ------------------------------------------------------------------------ *
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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: markus $, $Date: 2007-09-19 15:26:14 +0200 (Mi, 19 Sep 2007) $
 */
package ag.ion.noa.internal.text;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.bion.officelayer.text.ITextFieldService;
import ag.ion.bion.officelayer.util.Assert;

import ag.ion.noa.text.IAnnotation;
import ag.ion.noa.text.IAnnotationService;

import com.sun.star.lang.XServiceInfo;

import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;

import java.util.ArrayList;
import java.util.List;

/**
 * Annotation service of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 11572 $
 */
public class AnnotationService implements IAnnotationService {
  
  private ITextDocument textDocument = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new AnnotationService.
   * 
   * @param textDocument the text document of the service
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public AnnotationService(ITextDocument textDocument) {
    Assert.isNotNull(textDocument, XTextDocument.class, this);
    this.textDocument = textDocument;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all annotations.
   * 
   * @return all annotations
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public IAnnotation[] getAnnotations() {
    try {
      ITextField[] fields = textDocument.getTextFieldService().getUserTextFields();
      List annotations = new ArrayList();
      for(int i = 0; i < fields.length; i++) {
        XServiceInfo info = (XServiceInfo) UnoRuntime.queryInterface(XServiceInfo.class, fields[i].getXTextContent());
        if(info.supportsService(ITextFieldService.ANNOTATION_TEXTFIELD_ID)) {
          annotations.add(new Annotation(textDocument,fields[i]));
        }
      } 
      return (IAnnotation[]) annotations.toArray(new IAnnotation[annotations.size()]);
    }
    catch (Throwable throwable) {
      return new IAnnotation[0];
    }
  }
  //----------------------------------------------------------------------------
  
}