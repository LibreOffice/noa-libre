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
 * Last changes made by $Author: markus $, $Date: 2007-07-09 11:55:58 +0200 (Mo, 09 Jul 2007) $
 */
package ag.ion.noa.internal.text;

import ag.ion.bion.officelayer.util.Assert;

import ag.ion.noa.text.IDocumentIndex;

import com.sun.star.text.XDocumentIndex;

/**
 * Index of a document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11503 $
 * @date 17.08.2006
 */ 
public class DocumentIndex implements IDocumentIndex {

  private XDocumentIndex documentIndex = null;
  
  private String type = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new DocumentIndex.
   * 
   * @param documentIndex XDocumentIndex interface to be used
   * 
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public DocumentIndex(XDocumentIndex documentIndex) {
    Assert.isNotNull(documentIndex, XDocumentIndex.class, this);
    this.documentIndex = documentIndex;
  }
  //----------------------------------------------------------------------------
  /**
   * Returny type of the index.
   * 
   * @return type of the index
   * 
   * @see ALPHABETICAL_INDEX
   * @see CONTENT_INDEX
   * @see USER_DEFINED_INDEX
   * @see OBJECT_INDEX
   * @see TABLE_INDEX
   * @see BIBLIOGRAPHICAL_INDEX
   * 
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public String getType() {
    if(type == null)
      type = documentIndex.getServiceName();
    return type;
  }
  //----------------------------------------------------------------------------
  /**
   * Updates the document index. 
   * 
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public void update() {
    documentIndex.update();
  }
  //----------------------------------------------------------------------------
  
}