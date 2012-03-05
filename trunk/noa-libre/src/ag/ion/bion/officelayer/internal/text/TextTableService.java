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
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableService;
import ag.ion.bion.officelayer.text.TextException;

import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.lang.XMultiServiceFactory;

import com.sun.star.text.XTextTable;
import com.sun.star.text.XTextTablesSupplier;

import com.sun.star.uno.Any;
import com.sun.star.uno.UnoRuntime;

/**
 * Service for text tables.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class TextTableService implements ITextTableService {
  
  private ITextDocument textDocument = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableService.
   * 
   * @param textDocument text document to be used
   * 
   * @throws IllegalArgumentException if the submitted text document is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTableService(ITextDocument textDocument) throws IllegalArgumentException {
    if(textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    this.textDocument = textDocument;
  }  
  //----------------------------------------------------------------------------
  /**
   * Constructs new text table.
   * 
   * @param rows rows to be added
   * @param columns columns to be added
   * 
   * @return new table
   * 
   * @throws TextException if the new text table can not be constructed
   * 
   * @author Andreas Bröker
   */
  public ITextTable constructTextTable(int rows, int columns) throws TextException {
  	if(columns > ITextTable.MAX_COLUMNS_IN_TABLE) {
  		throw new TextException("The submitted table is not valid");
  	}
    try {
      XMultiServiceFactory xMultiServiceFactory = (XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, textDocument.getXTextDocument());
      Object newTable = xMultiServiceFactory.createInstance("com.sun.star.text.TextTable");
      XTextTable newTextTable = (XTextTable)UnoRuntime.queryInterface(XTextTable.class, newTable);
      newTextTable.initialize(rows,columns);
      
      TextTable textTable = new TextTable(textDocument, newTextTable);
      return textTable;
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns all available text tables.
   * 
   * @return all available text tables
   * 
   * @author Andreas Bröker
   */
  public ITextTable[] getTextTables() {
    XTextTablesSupplier xTextTablesSupplier = (XTextTablesSupplier)UnoRuntime.queryInterface(XTextTablesSupplier.class, textDocument.getXTextDocument());
    XNameAccess xNameAccess = xTextTablesSupplier.getTextTables();
    XIndexAccess xIndexAccess = (XIndexAccess)UnoRuntime.queryInterface(XIndexAccess.class, xNameAccess);
    ITextTable[] textTables = new ITextTable[xIndexAccess.getCount()];
    for(int i=0, n=xIndexAccess.getCount(); i<n; i++) {
      try {
        Any any = (Any)xIndexAccess.getByIndex(i);
        XTextTable textTable = (XTextTable)any.getObject();
        if(textTable.getColumns().getCount() <= ITextTable.MAX_COLUMNS_IN_TABLE) {
          textTables[i] = new TextTable(textDocument, textTable);
      	}
      }
      catch(Exception exception) {
        //do nothing
      }
    }
    return textTables;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns table with the submitted name.
   * 
   * @param name name of the table
   * 
   * @return table with the submitted name
   * 
   * @throws TextException if the table does not exist
   * 
   * @author Andreas Bröker
   */
  public ITextTable getTextTable(String name) throws TextException {
    try {
      XTextTablesSupplier xTextTablesSupplier = (XTextTablesSupplier)UnoRuntime.queryInterface(XTextTablesSupplier.class, textDocument.getXTextDocument());
      XNameAccess xNameAccess = xTextTablesSupplier.getTextTables();
      Any any = (Any)xNameAccess.getByName(name);
      XTextTable textTable = (XTextTable)any.getObject();
      if(textTable.getColumns().getCount() <= ITextTable.MAX_COLUMNS_IN_TABLE) {
      	return new TextTable(textDocument, textTable);
      }
      else {
      	throw new TextException("The submitted table is not valid");
      }
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  
}