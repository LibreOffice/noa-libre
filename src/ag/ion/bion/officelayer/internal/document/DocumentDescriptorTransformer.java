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
 * Last changes made by $Author: markus $, $Date: 2010-03-25 14:12:28 +0100 (Do, 25 Mrz 2010) $
 */
package ag.ion.bion.officelayer.internal.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ag.ion.bion.officelayer.document.IDocumentDescriptor;

import com.sun.star.beans.PropertyValue;

/**
 * Transformer for document descriptors.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11722 $
 */
public class DocumentDescriptorTransformer {

  //----------------------------------------------------------------------------
  /**
   * Converts a document descriptor to a array of property values.
   * 
   * @param documentDescriptor document descriptor to be converted
   * 
   * @return converted document descriptor
   * 
   * @author Andreas Bröker
   */
  public static PropertyValue[] documentDescriptor2PropertyValues(
      IDocumentDescriptor documentDescriptor) {
    return documentDescriptor2PropertyValues(null, documentDescriptor);
  }

  //----------------------------------------------------------------------------
  /**
   * Converts a document descriptor to a array of property values. The properties of the document
   * descriptor will be attached to the submitted array of property values.
   * 
   * @param propertyValues property values to be used
   * @param documentDescriptor document descriptor to be converted
   * 
   * @return converted document descriptor
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public static PropertyValue[] documentDescriptor2PropertyValues(PropertyValue[] propertyValues,
      IDocumentDescriptor documentDescriptor) {
    if (documentDescriptor == null) {
      if (propertyValues == null)
        return new PropertyValue[0];
      else
        return propertyValues;
    }

    List list = new ArrayList();
    PropertyValue propertyValue = new PropertyValue();
    propertyValue.Name = "AsTemplate";
    propertyValue.Value = new Boolean(documentDescriptor.getAsTemplate());
    list.add(propertyValue);

    if (documentDescriptor.getHidden()) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "Hidden";
      propertyValue.Value = Boolean.TRUE;
      list.add(propertyValue);
    }

    if (documentDescriptor.getReadOnly()) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "ReadOnly";
      propertyValue.Value = Boolean.TRUE;
      list.add(propertyValue);
    }

    if (documentDescriptor.getAsPreview()) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "Preview";
      propertyValue.Value = Boolean.TRUE;
      list.add(propertyValue);
    }

    String author = documentDescriptor.getAuthor();
    if (author != null) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "Author";
      propertyValue.Value = author;
      list.add(propertyValue);
    }

    String comment = documentDescriptor.getComment();
    if (comment != null) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "Comment";
      propertyValue.Value = comment;
      list.add(propertyValue);
    }

    String title = documentDescriptor.getTitle();
    if (title != null) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "DocumentTitle";
      propertyValue.Value = title;
      list.add(propertyValue);
    }

    String baseURL = documentDescriptor.getBaseURL();
    if (baseURL != null) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "DocumentBaseURL";
      propertyValue.Value = baseURL;
      list.add(propertyValue);
    }

    String URL = documentDescriptor.getURL();
    if (URL != null) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "URL";
      propertyValue.Value = URL;
      list.add(propertyValue);
    }

    String filterDef = documentDescriptor.getFilterDefinition();
    if (filterDef != null) {
      propertyValue = new PropertyValue();
      propertyValue.Name = "FilterName";
      propertyValue.Value = filterDef;
      list.add(propertyValue);
    }

    short macroExecMode = documentDescriptor.getMacroExecutionMode();
    propertyValue = new PropertyValue();
    propertyValue.Name = "MacroExecutionMode";
    propertyValue.Value = macroExecMode;
    list.add(propertyValue);

    if (propertyValues != null)
      list.addAll(Arrays.asList(propertyValues));
    return (PropertyValue[]) list.toArray(new PropertyValue[list.size()]);
  }
  //----------------------------------------------------------------------------

}