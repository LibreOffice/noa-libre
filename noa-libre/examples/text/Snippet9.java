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
 * We will experiment a little bit more with texts in this example. We will experiment
 * with paragraphs and formatting of the text. This snippet is more complex than the previous
 * ones, so it is recommended that you already worked the other snippets over.
 * 
 * We will do the whole stuff as paragraphs which will be slighlty new to you since 
 * we used only TextCursors to place the text in the previous examples. 
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 * @date 17.03.2006
 */
public class Snippet9 {

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
	  	
  	String[] firstText2BePlaced = { 
  		"The Raven (excerpt)",
			"",
			"Once upon a midnight dreary, while I pondered weak and weary,",
			"Over many a quaint and curious volume of forgotten lore,",
			"While I nodded, nearly napping, suddenly there came a tapping,",
			"As of some one gently rapping, rapping at my chamber door.",
			"`'Tis some visitor,' I muttered, `tapping at my chamber door -",
			"Only this, and nothing more.'"
		};
  	
  	String[] secondText2BePlaced = { 
    		"Ah, distinctly I remember it was in the bleak December,",
  			"And each separate dying ember wrought its ghost upon the floor.",
				"Eagerly I wished the morrow; - vainly I had sought to borrow",
				"From my books surcease of sorrow - sorrow for the lost Lenore -",
				"For the rare and radiant maiden whom the angels named Lenore -",
				"Nameless here for evermore."
  		};
  	
  	String[] thirdText2BePlaced = { 
  			"And the silken sad uncertain rustling of each purple curtain",
  			"Thrilled me - filled me with fantastic terrors never felt before;",
  			"So that now, to still the beating of my heart, I stood repeating",
  			"`'Tis some visitor entreating entrance at my chamber door -",
  			"Some late visitor entreating entrance at my chamber door; -",
  			"This it is, and nothing more,'"
  		};
  	
  	String allTexts2BePlaced[][] = 
  		new String[][]{firstText2BePlaced,secondText2BePlaced,thirdText2BePlaced};
  	
  	try {
  		
  		/*
  		 * Ok, now we gonna place the text in the document, each of the staves (stanzas)
  		 * of the poem will be a separate paragprah. This will serve us well since our 
  		 * intention is to format every paragprah in a different way.
  		 */
  		for(int i=0;i<allTexts2BePlaced.length;i++) {
		  	ITextCursor textCursor = 
		  		textDocument.getTextService().getText().getTextCursorService().getTextCursor();
		  	
		  	textCursor.gotoEnd(false);	  	
		  	IParagraph paragraph = 
		  		textDocument.getTextService().getTextContentService().constructNewParagraph();	  	
		  	textCursor.gotoEnd(false);
		  	textDocument.getTextService().getTextContentService().insertTextContent(textCursor.getEnd(),paragraph);
		  	StringBuffer bufferedString = new StringBuffer();
		  	for(int j=0;j<allTexts2BePlaced[i].length;j++ ) {
		  		bufferedString.append(allTexts2BePlaced[i][j] +"\n"); 
		  	}
		  	
	  		paragraph.setParagraphText(bufferedString.toString());
  		}
  		
  		/* After we wrote the paragraphs down to the document we will fetch them again
  		 * out of the document and format them a little bit. 
  		 * For sure it would have been easy for us just to store the newly created paragraphs
  		 * into an array or arraylist or something like that, but it makes sense as well
  		 * to use the already built-in functions of NOA. 
  		 */ 
  		
  		IParagraph paragraphs[] = 
  			textDocument.getTextService().getText().getTextContentEnumeration().getParagraphs();
  		
  		// we actually know that there are only four paragraphs, the one that is 
  		// initially placed in the document and our own three added paras. The last
  		// paragraph is of no interest for us, because it is the empty initial paragraph
  		
	  	/*
	  	 * Format paragraph 1
	  	 */
	  	IParagraphProperties paragraphPropoerties = paragraphs[0].getParagraphProperties();
	  	paragraphPropoerties.setParaAdjust(IParagraphProperties.ALIGN_RIGHT);
	  	paragraphPropoerties.getCharacterProperties().setFontBold(true);
	  	paragraphPropoerties.getCharacterProperties().setFontSize(8);
	  	paragraphPropoerties.getCharacterProperties().setFontItalic(true);
	  	paragraphPropoerties.getCharacterProperties().setFontColor(150);
  		
	  	/*
	  	 * Format paragraph 2
	  	 */
	  	IParagraphProperties paragraphPropoerties2 = paragraphs[1].getParagraphProperties();
	  	paragraphPropoerties2.setParaAdjust(IParagraphProperties.ALIGN_CENTER);
	  	paragraphPropoerties2.getCharacterProperties().setFontBold(true);
	  	paragraphPropoerties2.getCharacterProperties().setFontSize(12);
	  	paragraphPropoerties2.getCharacterProperties().setFontUnderline(true);
	  	paragraphPropoerties2.getCharacterProperties().setFontColor(10);
	  	
	  	/*
	  	 * Format paragraph 3
	  	 */
	  	IParagraphProperties paragraphPropoerties3 = paragraphs[2].getParagraphProperties();
	  	paragraphPropoerties3.setParaAdjust(IParagraphProperties.ALIGN_LEFT); 
	  	paragraphPropoerties3.getCharacterProperties().setFontSize(10);
	  	paragraphPropoerties3.getCharacterProperties().setFontUnderline(true);
	  	paragraphPropoerties3.getCharacterProperties().setFontColor(300);
	  	 	
	  }
  	catch(TextException exception) {
  		exception.printStackTrace();
  	}
	}
	//----------------------------------------------------------------------------
	
}