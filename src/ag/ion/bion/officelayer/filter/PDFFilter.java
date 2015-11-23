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
 * Last changes made by $Author: markus $, $Date: 2010-06-01 12:21:01 +0200 (Di, 01 Jun 2010) $
 */
package ag.ion.bion.officelayer.filter;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.noa.filter.AbstractFilter;

/**
 * Contains information in order to export an OpenOffice.org document to PDF.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11737 $
 */
public class PDFFilter extends AbstractFilter implements IFilter {

  /** Global filter for PDF.*/
  public static final PDFFilter FILTER              = new PDFFilter();

  private static final String   FILE_EXTENSION      = "pdf";

  private PDFFilterProperties   pdfFilterProperties = new PDFFilterProperties();

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
    if (documentType.equals(IDocument.WRITER)) {
      return "writer_pdf_Export";
    }
    if (documentType.equals(IDocument.GLOBAL)) {
      return "writer_globaldocument_pdf_Export";
    }
    if (documentType.equals(IDocument.WEB)) {
      return "writer_web_pdf_Export";
    }
    if (documentType.equals(IDocument.CALC)) {
      return "calc_pdf_Export";
    }
    if (documentType.equals(IDocument.DRAW)) {
      return "draw_pdf_Export";
    }
    if (documentType.equals(IDocument.IMPRESS)) {
      return "impress_pdf_Export";
    }
    if (documentType.equals(IDocument.MATH)) {
      return "math_pdf_Export";
    }
    return null;
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
    return true;
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
    if (documentType == null)
      return null;
    if (documentType.equals(IDocument.WRITER)) {
      return FILE_EXTENSION;
    }
    if (documentType.equals(IDocument.GLOBAL)) {
      return FILE_EXTENSION;
    }
    if (documentType.equals(IDocument.WEB)) {
      return FILE_EXTENSION;
    }
    if (documentType.equals(IDocument.CALC)) {
      return FILE_EXTENSION;
    }
    if (documentType.equals(IDocument.DRAW)) {
      return FILE_EXTENSION;
    }
    if (documentType.equals(IDocument.IMPRESS)) {
      return FILE_EXTENSION;
    }
    if (documentType.equals(IDocument.MATH)) {
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
    return "Portable Document Format";
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the PDFFilterProperties.
   * 
   * @return the PDFFilterProperties.
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public PDFFilterProperties getPDFFilterProperties() {
    return pdfFilterProperties;
  }
  //----------------------------------------------------------------------------
}
