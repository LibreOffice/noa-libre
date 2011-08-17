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

import ag.ion.bion.officelayer.text.IText;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextService;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.noa.NOAException;

import java.util.HashMap;

/**
 * This code snippet creates a new text document (like in Snippet2). Furthermore
 * we will place a table in the document and add some content to it.
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 * @date 17.03.2006
 */
public class Snippet5 {

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
			 * This stuff has already been discussed in Snippet2 but now lets begin to
			 * work with tables in text documents
			 */
			constructAndFillTable(textDocument);

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
	 * The first table example. We will create a table and then place some values in the cells.
	 * 
	 * @param textDocument the document to place the table in
	 *
	 * @author Sebastian Rösgen
	 * @date 17.03.2006
	 */
	public static void constructAndFillTable(ITextDocument textDocument) {
	  try {
			// create the table
			ITextTable textTable = textDocument.getTextTableService().constructTextTable(3, 5);				
			textDocument.getTextService().getTextContentService().insertTextContent(textTable);
		
			// and place some double values in it			
		  textTable.getCell(0,0).setValue(12412);
		  textTable.getCell(0,1).setValue(4444444);
		  
		  // to place text in a cell we need the text service of the individual cell.
		  ITextService textService = textTable.getCell(0,2).getTextService();
		  IText cellText = textService.getText();
		  cellText.setText("Hello World!");
		  
		  // so now that we have seen it in detail, there is no problem in doing
		  // it the short way.
		  textTable.getCell(2,2).getTextService().getText().setText("A");
		  textTable.getCell(3,2).getTextService().getText().setText("Simple");
		  textTable.getCell(4,2).getTextService().getText().setText("Test");
		  
		} 
	  catch (TextException exception) {
	  	exception.printStackTrace();
		}
	}
	//----------------------------------------------------------------------------
	
}