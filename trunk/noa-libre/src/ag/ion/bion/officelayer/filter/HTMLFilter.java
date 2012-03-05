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
 * Last changes made by $Author: markus $, $Date: 2008-03-13 07:36:47 +0100 (Do, 13 Mrz 2008) $
 */
package ag.ion.bion.officelayer.filter;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.noa.filter.AbstractFilter;

/**
 * Contains information in order to export an OpenOffice.org document to HTML.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11619 $
 */
public class HTMLFilter extends AbstractFilter implements IFilter {
	
	/** Global filter for HTML.*/
	public static final IFilter FILTER = new HTMLFilter();
	
	private static final String FILE_EXTENSION = "html";
  
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
      return "HTML (StarWriter)";
    }
    else if(documentType.equals(IDocument.CALC)) {
      return "HTML (StarCalc)";
    }
    else if(documentType.equals(IDocument.IMPRESS)) {
      return "impress_html_Export";
    }
    else if(documentType.equals(IDocument.DRAW)) {
      return "draw_html_Export";
    }
    else {
      return null;
    }
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
      return FILE_EXTENSION;
    }
    else if(documentType.equals(IDocument.CALC)) {
      return FILE_EXTENSION;
    }
    else if(documentType.equals(IDocument.IMPRESS)) {
      return FILE_EXTENSION;
    }
    else if(documentType.equals(IDocument.DRAW)) {
      return FILE_EXTENSION;
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
		if(documentType.equals(IDocument.IMPRESS)) {
      return "HTML-Dokument";
    }
		else if(documentType.equals(IDocument.DRAW)) {
			return "HTML-Dokument";
		}
		else
			return super.getName(documentType);
	}
	//----------------------------------------------------------------------------
	
}