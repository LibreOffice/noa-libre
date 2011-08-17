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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/

/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;

import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentService;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableRow;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.noa.NOAException;

import java.util.HashMap;

/**
 * This code snippet creates a new text document and do some stuff with tables.
 * 
 * We will create two tables, put some content in them (iterating through all cells)
 * and then add some new rows and columns to the second table.
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 * @date 17.03.2006
 */
public class Snippet6 {

	/*
	 * The path to the office application, in this case on a(n OpenSUSE)Linux system.
	 * 
	 * On a Windows system this would look like: 
	 * => private final static String officeHome = "C:\\Programme\\OpenOffice.org 2.0"; 
	 */
	private final static String OPEN_OFFICE_ORG_PATH = "/usr/lib/ooo-2.0"; 
		
	public static void main(String[] args) {
		HashMap configuration = new HashMap();
		configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
		configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
		
		try {
			IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);
			officeAplication.activate();
			IDocumentService documentService = officeAplication.getDocumentService();
			IDocument document = documentService.constructNewDocument(IDocument.WRITER, DocumentDescriptor.DEFAULT);
			ITextDocument textDocument = (ITextDocument)document;
			textDocument.addCloseListener(new SnippetDocumentCloseListener(officeAplication));
			
			/*
			 * This stuff was already discussed in Snippet2 but now lets begin to
			 * work with tables in text documents
			 */
			constructAndFillTables(textDocument);

		} 
		catch (OfficeApplicationException exception) {
			exception.printStackTrace();
		} 
		catch (NOAException exception) {
			exception.printStackTrace();
		}
	}
	//----------------------------------------------------------------------------
	/**
	 * The second table example. We will place some content in the table, after the creation.
	 * 
	 * @param textDocument the document to place the table in
	 *
	 * @author Sebastian Rösgen
	 * @date 17.03.2006
	 */
	public static void constructAndFillTables(ITextDocument textDocument) {
	  try {
			// create the first table
			ITextTable firstTextTable = textDocument.getTextTableService().constructTextTable(3, 5);				
			textDocument.getTextService().getTextContentService().insertTextContent(firstTextTable);
		
			int firstTableRowCount = firstTextTable.getRowCount();
			int firstTableColumnCount = firstTextTable.getColumnCount();
			
			int counter=0; // just to fill some value inside the cells
			
			// we will just fill it with double values
			for(int i=0;i<firstTableRowCount;i++) {
				for(int j=0;j<firstTableColumnCount;j++) {
					firstTextTable.getCell(j,i).setValue(++counter);
				}
			}
			
			// create the second table
			ITextTable secondTextTable = textDocument.getTextTableService().constructTextTable(4, 4);				
			textDocument.getTextService().getTextContentService().insertTextContent(secondTextTable);
			ITextTableRow[] rows = secondTextTable.getRows();
			
			// we will iterate through it in a different manner than before
			for(int i=0;i<rows.length;i++) {
				ITextTableRow currentRow = rows[i];
				ITextTableCell[] cellsOfRow = currentRow.getCells();
				for(int j=0;j<cellsOfRow.length;j++) {
					String cellContent = "Column: " + (j+1) + " Row: " + (i+1);
					//that which follows is alrady known from Snippet3
					cellsOfRow[j].getTextService().getText().setText(cellContent);
				}
			}
			
			// add two new rows at the end of the table
			secondTextTable.addRow(2);
			
			// add a new row after the second row
			secondTextTable.addRow(2,1);
								  
		} 
	  catch (TextException exception) {
	  	exception.printStackTrace();
		}
	}
	//----------------------------------------------------------------------------
	
}