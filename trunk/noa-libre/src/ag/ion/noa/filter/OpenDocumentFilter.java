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
 * Last changes made by $Author: markus $, $Date: 2008-03-13 07:36:47 +0100 (Do, 13 Mrz 2008) $
 */
package ag.ion.noa.filter;

import ag.ion.bion.officelayer.document.IDocument;

import ag.ion.bion.officelayer.filter.IFilter;

/**
 * Filter for the Open Document format.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11619 $
 * @date 09.07.2006
 */ 
public class OpenDocumentFilter extends AbstractFilter implements IFilter {

	/** Filter for the Open Document format.*/
	public static final IFilter FILTER = new OpenDocumentFilter();
	
	//----------------------------------------------------------------------------
	/**
   * Returns definition of the filter. Returns null if the filter
   * is not available for the submitted document type.
   * 
   * @param documentType document type to be used
   * 
   * @return definition of the filter or null if the filter
   * is not available for the submitted document type
   * 
   * @author Markus Krüger
   * @date 13.03.2008
   */
  public String getFilterDefinition(String documentType) {
		if(documentType.equals(IDocument.WRITER)) {
      return "writer8";
    }
		else if(documentType.equals(IDocument.GLOBAL)) {
      return "writerglobal8";
    }
		else if(documentType.equals(IDocument.BASE)) {
      return "base8";
    }
    else if(documentType.equals(IDocument.WEB)) {
      return "writerweb8_writer";
    }
    if(documentType.equals(IDocument.CALC)) {
      return "calc8";
    }
    else if(documentType.equals(IDocument.DRAW)) {
      return "draw8";
    }
    else if(documentType.equals(IDocument.IMPRESS)) {
      return "impress8";
    }
    else if(documentType.equals(IDocument.MATH)) {
      return "math8";
    }
    return null;
	}
	//----------------------------------------------------------------------------
  /**
   * Returns file extension of the filter. Returns null
   * if the document type is not supported by the filter.
   * 
   * @param documentType document type to be used
   * 
   * @return file extension of the filter
   * 
   * @author Markus Krüger
   * @date 03.04.2007
   */
  public String getFileExtension(String documentType) {
    if(documentType == null)
      return null;
		if(documentType.equals(IDocument.WRITER)) {
      return "odt";
    }
		else if(documentType.equals(IDocument.GLOBAL)) {
      return "odm";
    }
		else if(documentType.equals(IDocument.BASE)) {
      return "odb";
    }
    else if(documentType.equals(IDocument.WEB)) {
      return "html";
    }
    else if(documentType.equals(IDocument.CALC)) {
      return "ods";
    }
    else if(documentType.equals(IDocument.DRAW)) {
      return "odg";
    }
    else if(documentType.equals(IDocument.IMPRESS)) {
      return "odp";
    }
    else if(documentType.equals(IDocument.MATH)) {
      return "odf";
    }
    return null;
	}
	//----------------------------------------------------------------------------
  /**
   * Returns name of the filter. Returns null
   * if the submitted document type is not supported by the filter.
   * 
   * @param documentType document type to be used
   * 
   * @return name of the filter
   * 
   * @author Markus Krüger
   * @date 13.03.2008
   */
  public String getName(String documentType) {
		if(documentType.equals(IDocument.WRITER)) {
      return "Open Document Text";
    }
		else if(documentType.equals(IDocument.GLOBAL)) {
      return "Open Document Global Document";
    }
		else if(documentType.equals(IDocument.WEB)) {
      return "HTML-Document";
    }
		else if(documentType.equals(IDocument.BASE)) {
      return "Open Document Database";
    }
		else if(documentType.equals(IDocument.CALC)) {
      return "Open Document Spreadsheet";
    }
		else if(documentType.equals(IDocument.DRAW)) {
      return "Open Document Drawing";
    }
		else if(documentType.equals(IDocument.IMPRESS)) {
      return "Open Document Presentation";
    }
		else if(documentType.equals(IDocument.MATH)) {
      return "Open Document Formula";
    }
    return null;
	}
	//----------------------------------------------------------------------------
	
}