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

import ag.ion.noa.filter.DataInterchangeFilter;
import ag.ion.noa.filter.MSOffice2003XMLFilter;
import ag.ion.noa.filter.MSOffice60TemplateFilter;
import ag.ion.noa.filter.MSOffice97Template;
import ag.ion.noa.filter.MathMLFilter;
import ag.ion.noa.filter.ODPresentationToDrawFilter;
import ag.ion.noa.filter.OOPresentationToDrawFilter;
import ag.ion.noa.filter.OpenDocumentFilter;
import ag.ion.noa.filter.OpenOfficeFilter;
import ag.ion.noa.filter.OpenOfficeTemplateFilter;
import ag.ion.noa.filter.OpenTemplateFilter;
import ag.ion.noa.filter.PocketOfficeFilter;
import ag.ion.noa.filter.SO30PresentationToDraw;
import ag.ion.noa.filter.SO50PresentationToDrawFilter;
import ag.ion.noa.filter.StarOffice30Filter;
import ag.ion.noa.filter.StarOffice30TemplateFilter;
import ag.ion.noa.filter.StarOffice40Filter;
import ag.ion.noa.filter.StarOffice40TemplateFilter;
import ag.ion.noa.filter.StarOffice50Filter;
import ag.ion.noa.filter.StarOffice50TemplateFilter;
import ag.ion.noa.filter.SylkFilter;
import ag.ion.noa.filter.XHTMLFilter;

/**
 * Filter for the export of documents.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11619 $
 */
public interface IFilter {
	
	/** Available default filters. */
	public static final IFilter[] FILTERS = new IFilter[]{OpenDocumentFilter.FILTER, 
																												OpenTemplateFilter.FILTER, 
																												OpenOfficeFilter.FILTER, 
																												OpenOfficeTemplateFilter.FILTER,
																												MSOffice97Filter.FILTER,
																												MSOffice97Template.FILTER,
																												OOPresentationToDrawFilter.FILTER,
																												SO50PresentationToDrawFilter.FILTER,
																												SO30PresentationToDraw.FILTER,
																												MSOffice95Filter.FILTER,
																												MSOffice95Filter.FILTER,
																												MSOffice60Filter.FILTER,
																												MSOffice60TemplateFilter.FILTER,
																												RTFFilter.FILTER,
																												DataInterchangeFilter.FILTER,
																												StarOffice50Filter.FILTER,
																												StarOffice50TemplateFilter.FILTER,
																												MathMLFilter.FILTER,
																												StarOffice40Filter.FILTER,
																												StarOffice40TemplateFilter.FILTER,
																												ODPresentationToDrawFilter.FILTER,
																												StarOffice30Filter.FILTER,
																												StarOffice30TemplateFilter.FILTER,
																												SylkFilter.FILTER,
																												TextFilter.FILTER,
																												TextEncFilter.FILTER,
																												HTMLFilter.FILTER,
                                                        XHTMLFilter.FILTER,
																												PDBFilter.FILTER,
																												XMLDocBookFilter.FILTER,
																												MSOffice2003XMLFilter.FILTER,
																												PocketOfficeFilter.FILTER};

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
	public String getFilterDefinition(IDocument document);
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
  public String getFilterDefinition(String documentType);
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
	public boolean isSupported(IDocument document);	
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
  public boolean isSupported(String documentType); 
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
	public boolean isExternalFilter();
	//----------------------------------------------------------------------------
	/**
	 * Returns file extension of the filter. Returns null
	 * if the document is not supported by the filter.
	 * 
	 * @param document document to be used
	 * 
	 * @return file extension of the filter
	 * 
	 * @author Andreas Bröker
	 * @date 08.07.2006
	 */
	public String getFileExtension(IDocument document);	
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
  public String getFileExtension(String documentType); 
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
	public String getName(IDocument document);	
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
  public String getName(String documentType);  
  //----------------------------------------------------------------------------
	
}