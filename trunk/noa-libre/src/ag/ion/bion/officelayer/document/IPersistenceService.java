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
package ag.ion.bion.officelayer.document;

import ag.ion.bion.officelayer.filter.IFilter;

import ag.ion.noa.NOAException;

import java.io.OutputStream;

import java.net.URL;

/**
 * Persistence service for office documents.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public interface IPersistenceService {
  
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the document has a location URL.
   * 
   * @return information whether the document has a location URL
   * 
   * @author Andreas Brueekr
   */
  public boolean hasLocation();
  //----------------------------------------------------------------------------
  /**
   * Returns location URL of the document. Returns null if
   * no location URL is available.
   * 
   * @return location URL of the document
   * 
   * @author Andreas Bröker
   */
  public URL getLocation();
  //----------------------------------------------------------------------------
  /**
   * Returns informations whether the doccument is in read only state 
   * or not.
   * 
   * @return informations whether the doccument is in read only state 
   * or not
   * 
   * @author Andreas Bröker
   */
  public boolean isReadOnly();
  //----------------------------------------------------------------------------
  /**
   * Stores document on to the location URL.
   * 
   * @throws DocumentException if the document can not be stored or no location URL is available
   * 
   * @author Andreas Bröker
   */
  public void store() throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Stores document to the submitted URL.
   * 
   * @param url URL to be used as location
   * 
   * @throws DocumentException if the document can not be stored
   * 
   * @author Andreas Bröker
   */
  public void store(String url) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Stores document in the submitted output stream. 
   * 
   * @param outputStream output stream to be used
   * 
   * @throws DocumentException if the document can not be stored
   */
  public void store(OutputStream outputStream) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Stores document in the submitted output stream.
   * And fires save-as events on office document.
   * 
   * @param outputStream output stream to be used
   * 
   * @throws NOAException if the document can not be stored
   * 
   * @author Alessandro Conte
   * @date 07.09.2006
   */
  public void storeAs(OutputStream outputStream) throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Exports document to the URL on the basis of the submitted filter.
   * 
   * @param url URL to be used as location
   * @param filter filter to be used
   * 
   * @throws DocumentException if the document can not be exported
   * 
   * @author Andreas Bröker
   */
  public void export(String url, IFilter filter) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Exports document into the output stream on the basis of the submitted filter.
   * 
   * @param outputStream output stream to be used
   * @param filter filter to be used
   * 
   * @throws NOAException if the document can not be exported
   * 
   * @author Andreas Bröker
   * @date 25.08.2006
   */
  public void export(OutputStream outputStream, IFilter filter) throws NOAException;
  //----------------------------------------------------------------------------
  
}