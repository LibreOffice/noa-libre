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

import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.IParagraphProperties;
import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.noa.NOAException;

import java.util.HashMap;

/**
 * In this snippet we will do some text formatting. 
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 * @date 17.03.2006
 */
public class Snippet8 {

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
			 * Now do the work with text content.
			 */
			placeSomeTextContent(textDocument);

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
	 * We will now format the text, we create, a little bit.
	 * 
	 * @param textDocument the document to place the table in
	 *
	 * @author Sebastian Rösgen
	 * @date 17.03.2006
	 */
	public static void placeSomeTextContent(ITextDocument textDocument) {
	  	
  	String[] text2BePlaced = { 
  		"The Raven (excerpt)\n",
			"",
			"...",
			"...",
			"",
			"But the raven, sitting lonely on the placid bust, spoke only,",
			"That one word, as if his soul in that one word he did outpour.",
			"Nothing further then he uttered - not a feather then he fluttered -",
			"Till I scarcely more than muttered `Other friends have flown before -",
			"On the morrow will he leave me, as my hopes have flown before.'",
			"Then the bird said, `Nevermore.'",
			"",
			"Startled at the stillness broken by reply so aptly spoken,",
			"`Doubtless,' said I, `what it utters is its only stock and store,",
			"Caught from some unhappy master whom unmerciful disaster",
			"Followed fast and followed faster till his songs one burden bore -",
			"Till the dirges of his hope that melancholy burden bore",
			"Of \"Never-nevermore.\"'"
  	};
  	
  	try {
	  	ITextCursor textCursor = 
	  		textDocument.getTextService().getText().getTextCursorService().getTextCursor();
	  	
	  	textCursor.gotoEnd(false);
	  	
	  	for(int i=0;i<text2BePlaced.length;i++ ) {
	  		textCursor.getEnd().setText(text2BePlaced[i]); //we place the text
	  		textCursor.getEnd().setText("\n"); // and we wrap the line
	  	}
	  	
	  	// Now some new stuff. We will set the align of the poem -- which should be centered
	  	// as it fits for a good poem -- afterwards we will	  	
	  	IParagraph[] paragraphs = 
	  		textDocument.getTextService().getText().getTextContentEnumeration().getParagraphs();
	  	
	  	
	  	// there should be only one paragraph, since we did not insert any additional ones
	  	// so we use index 0 of the array.	  	
	  	IParagraphProperties paragraphPropoerties = paragraphs[0].getParagraphProperties();
	  	paragraphPropoerties.setParaAdjust(IParagraphProperties.ALIGN_CENTER);
	  	
	  	// set the font bold and italic, set size to 9 and color the text blue, 
	  	paragraphPropoerties.getCharacterProperties().setFontBold(true);
	  	paragraphPropoerties.getCharacterProperties().setFontSize(9);
	  	paragraphPropoerties.getCharacterProperties().setFontItalic(true);
	  	paragraphPropoerties.getCharacterProperties().setFontColor(150);
	  	
	  }
  	catch(TextException exception) {
  		exception.printStackTrace();
  	}

	}
	//----------------------------------------------------------------------------
	
}