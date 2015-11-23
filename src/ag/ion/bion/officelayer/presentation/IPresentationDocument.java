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
 * Last changes made by $Author: markus $, $Date: 2008-01-07 16:25:58 +0100 (Mo, 07 Jan 2008) $
 */
package ag.ion.bion.officelayer.presentation;

import ag.ion.bion.officelayer.document.IDocument;

import com.sun.star.presentation.XPresentationSupplier;

/**
 * OpenOffice.org impress document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11611 $
 */
public interface IPresentationDocument extends IDocument {

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XPresentationSupplier interface.
   * 
   * @return OpenOffice.org XPresentationSupplier interface
   * 
   * @author Andreas Bröker
   */
  public XPresentationSupplier getPresentationSupplier();
  //----------------------------------------------------------------------------
  /**
   * Returns page service of the document.
   * 
   * @return page service of the document
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public IPageService getPageService();
  //----------------------------------------------------------------------------
  
}