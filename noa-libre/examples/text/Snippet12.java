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
import ag.ion.bion.officelayer.text.ITextRange;

import ag.ion.noa.NOAException;

import ag.ion.noa.search.ISearchResult;
import ag.ion.noa.search.SearchDescriptor;

import ag.ion.noa.text.TextRangeSelection;

import java.util.HashMap;

/**
 * This code snippet shows how it is possible to search
 * within a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 16.07.2006
 */
public class Snippet12 {

	/*
	 * The path to the office application, in this case on a windows system.
	 * 
	 * On a Linux system this would look like: 
	 * => private final static String officeHome = "/usr/lib/ooo-2.0"; 
	 */
	private final static String OPEN_OFFICE_ORG_PATH = "C:\\Programme\\OpenOffice.org 2.0"; 
		
	public static void main(String[] args) {
		try {
			HashMap configuration = new HashMap();
			configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
			configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
			IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);	
			officeAplication.setConfiguration(configuration);
			officeAplication.activate();
			IDocumentService documentService = officeAplication.getDocumentService();
			IDocument document = documentService.constructNewDocument(IDocument.WRITER, DocumentDescriptor.DEFAULT);
			ITextDocument textDocument = (ITextDocument)document;
			//First some text ...
			textDocument.getTextService().getText().setText("This is a text content for a search example with NOA.");
			
			//OK - now we need a search query
			SearchDescriptor searchDescriptor = new SearchDescriptor("NOA");
			searchDescriptor.setIsCaseSensitive(true);
			//Perform the search ...
			ISearchResult searchResult = textDocument.getSearchService().findFirst(searchDescriptor);
			if(!searchResult.isEmpty()) {
				//...and now select the result
				ITextRange[] textRanges = searchResult.getTextRanges();
				textDocument.setSelection(new TextRangeSelection(textRanges[0]));
			}
		} 
		catch (OfficeApplicationException exception) {
			exception.printStackTrace();
		} 
		catch (NOAException exception) {
			exception.printStackTrace();
		}
	}

}