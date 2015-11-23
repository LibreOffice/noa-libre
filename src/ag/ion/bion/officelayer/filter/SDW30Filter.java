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
 * Contains information in order to export an OpenOffice.org document 
 * to StarWriter 3.0.
 * 
 * @author Markus Krüger
 * @author Andreas Bröker
 * @version $Revision: 11619 $
 * 
 * @deprecated Use StarOffice30Filter instead.
 */
public class SDW30Filter extends AbstractFilter implements IFilter {
	
	/** Global filter for StarWriter 3.0.*/
	public static final IFilter FILTER = new SDW30Filter();
	
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
      return "StarWriter 3.0";
    }
    if(documentType.equals(IDocument.GLOBAL)) {
      return "StarWriter 3.0 (StarWriter/Global Document)";
    }
    if(documentType.equals(IDocument.WEB)) {
      return "StarWriter 3.0 (StarWriter/Web)";
    }
    if(documentType.equals(IDocument.CALC)) {
      return "StarCalc 3.0";
    }
    if(documentType.equals(IDocument.DRAW)) {
      return "StarDraw 3.0";
    }
    if(documentType.equals(IDocument.MATH)) {
      return "StarMath 3.0";
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
      return "sdw";
    }
    if(documentType.equals(IDocument.GLOBAL)) {
      return "sgl";
    }
    if(documentType.equals(IDocument.WEB)) {
      return "html";
    }
    if(documentType.equals(IDocument.CALC)) {
      return "sdc";
    }
    if(documentType.equals(IDocument.DRAW)) {
      return "sdd";
    }
    if(documentType.equals(IDocument.MATH)) {
      return "smf";
    }
    return null;
	}
  //----------------------------------------------------------------------------
}
