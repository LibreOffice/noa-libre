/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                                 *
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
 *  http://www.ion.ag                                                       *
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
 * Abstract base filter.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11619 $
 * @date 09.07.2006
 */ 
public abstract class AbstractFilter implements IFilter {
	
  //----------------------------------------------------------------------------
  /**
   * Returns definition of the filter. Returns null if the filter
   * is not available for the submitted document.
   * 
   * @param document document to be exported 
   * 
   * @return definition of the filter or null if the filter
   * is not available for the submitted document
   */
  public final String getFilterDefinition(IDocument document) {
    if(document != null)
      return getFilterDefinition(document.getDocumentType());
    return null;
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns information whether the submitted document
	 * is supported by the filter.
	 * 
	 * @param document document to be used
	 * 
	 * @return information whether the submitted document
	 * is supported by the filter
	 * 
	 * @author Andreas Bröker
	 * @date 08.07.2006
	 */
	public final boolean isSupported(IDocument document) {
	  if(document != null)
      return isSupported(document.getDocumentType());
    return false;
	}
	//----------------------------------------------------------------------------
  /**
   * Returns information whether the submitted document type
   * is supported by the filter.
   * 
   * @param documentType document type to be used
   * 
   * @return information whether the submitted document type
   * is supported by the filter
   * 
   * @author Markus Krüger
   * @date 13.03.2008
   */
  public final boolean isSupported(String documentType) {
    if(getFilterDefinition(documentType) == null)
      return false;
    return true;
  } 
  //----------------------------------------------------------------------------
	/**
	 * Returns information whether the filter constructs
	 * a document which can not be interpreted again.
	 * 
	 * @return information whether the filter constructs
	 * a document which can not be interpreted again
	 * 
	 * @author Andreas Bröker
	 * @date 08.07.2006
	 */
	public boolean isExternalFilter() {
		return false;
	}
	//----------------------------------------------------------------------------
  /**
   * Returns file extension of the filter. Returns null
   * if the document is not supported by the filter.
   * 
   * @param document document to be used
   * 
   * @return file extension of the filter
   * 
   * @author Markus Krüger
   * @date 03.04.2007
   */
  public final String getFileExtension(IDocument document) {
    if(document != null)
      return getFileExtension(document.getDocumentType());
    return null;
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns name of the filter. Returns null
	 * if the submitted document is not supported by the filter.
	 * 
	 * @param document document to be used
	 * 
	 * @return name of the filter
	 * 
	 * @author Andreas Bröker
	 * @date 14.07.2006
	 */
	public final String getName(IDocument document) {
	  if(document != null)
      return getName(document.getDocumentType());
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
    return getFilterDefinition(documentType);
  }  
  //----------------------------------------------------------------------------
}
