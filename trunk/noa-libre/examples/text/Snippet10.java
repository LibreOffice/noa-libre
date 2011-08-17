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

import ag.ion.bion.officelayer.document.DocumentException;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentService;

import ag.ion.bion.officelayer.text.ITextDocument;

import java.util.HashMap;

/**
 * In this snippet we will open an already existing document.
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 * @date 17.03.2006
 */
public class Snippet10 {

	/*
	 * The path to the office application, in this case on a(n OpenSUSE)Linux system.
	 * 
	 * On a Windows system this would look like: 
	 * => private final static String officeHome = "C:\\Programme\\OpenOffice.org 2.0"; 
	 */
	private final static String OPEN_OFFICE_ORG_PATH = "/usr/lib/ooo-2.0"; 
	
	/*
	 * Name of the OpenOffice file that should be opened.
	 */
	private final static String DOCUMENT_NAME = "an_openoffice_file.odt";
	
	public static void main(String[] args) {
		HashMap configuration = new HashMap();
		configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
		configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
		
		try {
			IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);
			officeAplication.activate();
			IDocumentService documentService = officeAplication.getDocumentService();
			
			// we expect a file, beeing placed in the user home directory, otherwise use another url
			String url = System.getProperty("user.home") +System.getProperty("file.separator")+ DOCUMENT_NAME;
			IDocument document = documentService.loadDocument(url);
			
			ITextDocument textDocument = (ITextDocument)document;
			textDocument.addCloseListener(new SnippetDocumentCloseListener(officeAplication));

		} 
		catch (OfficeApplicationException exception) {
			exception.printStackTrace();
		} 
		catch (DocumentException exception) {
			exception.printStackTrace();
		}
	}
	//----------------------------------------------------------------------------
	
}