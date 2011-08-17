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
package ag.ion.bion.officelayer.text.test;

import java.io.File;
import java.io.FileInputStream;

import com.sun.star.text.XTextCursor;

import ag.ion.bion.officelayer.application.LocalOfficeApplicationConfiguration;
import ag.ion.bion.officelayer.clone.CloneDestinationPosition;

import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.internal.application.LocalOfficeApplication;

import ag.ion.bion.officelayer.internal.text.TextDocument;
import ag.ion.bion.officelayer.internal.text.TextRange;
import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.ITextContentEnumeration;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;

import junit.framework.TestCase;

/**
 * JUnit test case for the IParagraph and the contained properties
 * IParagprahPropertyStore and ICharacterPropertyStore 
 * 
 * @author sebastianr
 */
public class IParagraphTest extends TestCase {

	
	/**
	 * Basic test class
	 *
	 */
	public void testGetParagraphText() {
		try {
      LocalOfficeApplication localOfficeApplication = new LocalOfficeApplication(null);
      LocalOfficeApplicationConfiguration configuration = new LocalOfficeApplicationConfiguration();
      configuration.setApplicationHomePath("/home/Sebastianr/OpenOffice.org1.1.1");
      localOfficeApplication.setConfiguration(configuration);
      localOfficeApplication.activate();
      
      IDocument document = localOfficeApplication.getDocumentService().loadDocument(new FileInputStream(new File("test/testTexts.sxw")), new DocumentDescriptor());
			if (document != null) {
				System.out.println("Loaded document");
				
				ITextDocument textDocument = (ITextDocument)document;
							
				
				ITextContentEnumeration textEnumeration = textDocument.getTextService().getText().getTextContentEnumeration();
				IParagraph[] paragraphs = textEnumeration.getParagraphs();
				
				
				ITextRange newTextRange = textDocument.getViewCursorService().getViewCursor().getStartTextRange();
				XTextCursor cursor = newTextRange.getXTextRange().getText().createTextCursor();
				cursor.gotoEnd(false);
				newTextRange = new TextRange(textDocument, cursor);
				paragraphs[0].getCloneService().cloneToPosition(new CloneDestinationPosition(newTextRange, newTextRange.getClass()), true, null);
								
				textDocument.close();
        localOfficeApplication.deactivate();
			}
		}
		catch (Exception excep) {
			excep.printStackTrace();
		}
	}	
	
}
