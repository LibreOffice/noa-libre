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
 * Copyright 2003-2008 by IOn AG                                            *
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
 * Last changes made by $Author: markus $, $Date: 2007-04-03 12:40:19 +0200 (Di, 03 Apr 2007) $
 */
package ag.ion.noa.filter;

import ag.ion.bion.officelayer.document.IDocument;

import ag.ion.bion.officelayer.filter.IFilter;

/**
 * Filter for GIF.
 * 
 * @author Markus Krueger
 * @version $Revision: 11479 $
 */ 
public class GIFFilter extends AbstractFilter implements IFilter {
	
	/** Global filter for GIF.*/
	public static final IFilter FILTER = new GIFFilter();
	
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
   * @author Markus Krueger
   * @date 13.03.2008
   */
  public String getFilterDefinition(String documentType) {
    if(documentType.equals(IDocument.DRAW)) {
    	return "draw_gif_Export";
    }
    else if(documentType.equals(IDocument.IMPRESS)) {
    	return "impress_gif_Export";
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
   * @author Markus Krueger
   * @date 07.01.2008
   */
  public String getFileExtension(String documentType) {
    if(documentType == null)
      return null;
    if(documentType.equals(IDocument.IMPRESS) || 
        documentType.equals(IDocument.DRAW)) {
      return "gif";
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
   * @author Markus Krueger
   * @date 13.03.2008
   */
  public String getName(String documentType) {
	  if(documentType.equals(IDocument.IMPRESS) || 
	      documentType.equals(IDocument.DRAW)) {
      return "GIF - Graphics Interchange Format";
    }
    return null;
	}
  //----------------------------------------------------------------------------

}