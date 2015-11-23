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
package ag.ion.bion.officelayer.internal.document;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.filter.IFilter;

import com.sun.star.uno.UnoRuntime;

import com.sun.star.frame.XStorable;

import com.sun.star.beans.PropertyValue;

import com.sun.star.io.XOutputStream;

import java.io.IOException;

/**
 * Document exporting helper class.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class DocumentExporter {
  
  //----------------------------------------------------------------------------
  /**
   * Exports document on the basis of the submitted filter and URL.
   * 
   * @param document document to be exported
   * @param URL URL to be used
   * @param filter OpenOffice.org filter to be used
   * 
   * @throws IOException if any error occurs
   */
  public static void exportDocument(IDocument document, String URL, IFilter filter) 
  throws IOException {
    exportDocument(document, URL, filter, null);
  }
  //----------------------------------------------------------------------------
  /**
   * Exports document on the basis of the submitted filter and URL.
   * 
   * @param document document to be exported
   * @param URL URL to be used
   * @param filter OpenOffice.org filter to be used
   * @param properties properties properties for OpenOffice.org
   * 
   * @throws IOException if any error occurs
   */
  public static void exportDocument(IDocument document, String URL, IFilter filter, PropertyValue[] properties) 
  throws IOException {
    if(properties == null) {
      properties = new PropertyValue[0];
    }
    PropertyValue[] newProperties = new PropertyValue[properties.length + 1];
    for(int i=0; i<properties.length; i++) {
      newProperties[i] = properties[i];
    }
        
    newProperties[properties.length] = new PropertyValue(); 
    newProperties[properties.length].Name = "FilterName"; 
    newProperties[properties.length].Value = filter.getFilterDefinition(document);
    
    XStorable xStorable = (XStorable)UnoRuntime.queryInterface(XStorable.class, document.getXComponent());
    try {
      xStorable.storeToURL(URL, newProperties);
    }
    catch(com.sun.star.io.IOException ioException) {
      throw new IOException(ioException.getMessage());
    }    
  }
  //----------------------------------------------------------------------------
  /**
   * Exports document on the basis of the submitted filter and XOutputStream implementation.
   * 
   * @param document document to be stored
   * @param xOutputStream OpenOffice.org XOutputStream inplementation
   * @param filter filter to be used
   * 
   * @throws IOException if any error occurs
   */
  public static void exportDocument(IDocument document, XOutputStream xOutputStream, IFilter filter) 
  throws IOException {
    exportDocument(document, xOutputStream, filter, null);
  }
  //----------------------------------------------------------------------------
  /**
   * Exports document on the basis of the submitted filter and XOutputStream implementation.
   * 
   * @param document document to be stored
   * @param xOutputStream OpenOffice.org XOutputStream inplementation
   * @param filter filter to be used
   * @param properties properties properties for OpenOffice.org
   * 
   * @throws IOException if any error occurs
   */
  public static void exportDocument(IDocument document, XOutputStream xOutputStream, IFilter filter, PropertyValue[] properties) 
  throws IOException {
    if(properties == null) {
      properties = new PropertyValue[0];
    }
    
    PropertyValue[] newProperties = new PropertyValue[properties.length + 2];
    for(int i=0; i<properties.length; i++) {
      newProperties[i] = properties[i];
    }
    newProperties[properties.length] = new PropertyValue(); 
    newProperties[properties.length].Name = "OutputStream"; 
    newProperties[properties.length].Value = xOutputStream;
    
    newProperties[properties.length + 1] = new PropertyValue(); 
    newProperties[properties.length + 1].Name = "FilterName"; 
    newProperties[properties.length + 1].Value = filter.getFilterDefinition(document);
    
    XStorable xStorable = (XStorable)UnoRuntime.queryInterface(XStorable.class, document.getXComponent());
    try {
      xStorable.storeToURL("private:stream", newProperties);
    }
    catch(com.sun.star.io.IOException ioException) {
      throw new IOException(ioException.getMessage());
    }    
  }  
  //----------------------------------------------------------------------------  
}

