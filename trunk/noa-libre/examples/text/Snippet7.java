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

import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.noa.NOAException;

import java.util.HashMap;

/**
 * Since we have done some work with tables now, it is a good idea to 
 * do some work with simple text. More examples for tables and texts can follow after we 
 * just get used to the basics a little bit more.
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 * @date 17.03.2006
 */
public class Snippet7 {

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
	 * Now we will write a text to the document. After that is done (we already
	 * shoud now how to write text down to the document) we will use a new 
	 * method to append more text content.
	 * 
	 * @param textDocument the document to place the table in
	 *
	 * @author Sebastian Rösgen
	 * @date 17.03.2006
	 */
	public static void placeSomeTextContent(ITextDocument textDocument) {
	  	
  	textDocument.getTextService().getText().setText("The Raven (excerpt)\n\n");		
  	
  	String[] text2BePlaced = { 
  		// since there is no better poem than Edgar Allan Poe's the Raven
  		// we will use some staves from it
			"Once upon a midnight dreary, while I pondered weak and weary,",
			"Over many a quaint and curious volume of forgotten lore,",
			"While I nodded, nearly napping, suddenly there came a tapping,",
			"As of some one gently rapping, rapping at my chamber door.",
			"`'Tis some visitor,' I muttered, `tapping at my chamber door -",
			"Only this, and nothing more.'",
			"",
			"...",
			"",
			"Then this ebony bird beguiling my sad fancy into smiling,",
			"By the grave and stern decorum of the countenance it wore,",
			"`Though thy crest be shorn and shaven, thou,' I said, `art sure no craven.",
			"Ghastly grim and ancient raven wandering from the nightly shore -",
			"Tell me what thy lordly name is on the Night's Plutonian shore!'",
			"Quoth the raven, `Nevermore.'",
			"",
			"Much I marvelled this ungainly fowl to hear discourse so plainly,",
			"Though its answer little meaning - little relevancy bore;",
			"For we cannot help agreeing that no living human being",
			"Ever yet was blessed with seeing bird above his chamber door -",
			"Bird or beast above the sculptured bust above his chamber door,",
			"With such name as `Nevermore.'",
			"",
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
	  }
  	catch(TextException exception) {
  		exception.printStackTrace();
  	}


	}
	//----------------------------------------------------------------------------
	
}