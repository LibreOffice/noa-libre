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
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa.internal.text;

import ag.ion.bion.officelayer.util.Assert;

import ag.ion.noa.text.IDocumentIndex;
import ag.ion.noa.text.IDocumentIndexService;

import com.sun.star.container.XIndexAccess;

import com.sun.star.text.XDocumentIndex;
import com.sun.star.text.XDocumentIndexesSupplier;
import com.sun.star.text.XTextDocument;

import com.sun.star.uno.UnoRuntime;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for document indices.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 17.08.2006
 */ 
public class DocumentIndexService implements IDocumentIndexService {

  private XTextDocument textDocument = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new DocumentIndexProvider.
   * 
   * @param textDocument XTextDocument interface to be used
   * 
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public DocumentIndexService(XTextDocument textDocument) {
    Assert.isNotNull(textDocument, XTextDocument.class, this);
    this.textDocument = textDocument;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns available document indexes.
   * 
   * @return available document indexes
   * 
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public IDocumentIndex[] getDocumentIndexes() {
    XDocumentIndexesSupplier documentIndexesSupplier = (XDocumentIndexesSupplier)UnoRuntime.queryInterface(XDocumentIndexesSupplier.class, textDocument);
    if(documentIndexesSupplier == null)
      return new IDocumentIndex[0];
    
    XIndexAccess indexAccess = documentIndexesSupplier.getDocumentIndexes();
    List list = new ArrayList();
    for(int i=0, n=indexAccess.getCount(); i<n; i++) {
      try {
        Object object = indexAccess.getByIndex(i);
        XDocumentIndex documentIndex = (XDocumentIndex)UnoRuntime.queryInterface(XDocumentIndex.class, object);
        if(documentIndex != null)
          list.add(new DocumentIndex(documentIndex));
      }
      catch(Throwable throwable) {
        //do not consume
      }
    }
    return (IDocumentIndex[])list.toArray(new IDocumentIndex[list.size()]);
  }
  //----------------------------------------------------------------------------

}