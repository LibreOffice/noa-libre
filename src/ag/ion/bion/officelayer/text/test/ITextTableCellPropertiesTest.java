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
 * Last changes made by $Author: markus $, $Date: 2007-02-26 11:24:19 +0100 (Mo, 26 Feb 2007) $
 */
package ag.ion.bion.officelayer.text.test;

import ag.ion.bion.officelayer.application.LocalOfficeApplicationConfiguration;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.internal.application.LocalOfficeApplication;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableProperties;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Miriam Sutter
 */
public class ITextTableCellPropertiesTest extends TestCase {

	public void testGetBackgroundColor() {
//		 try {
//      LocalOfficeConnection localOfficeConnection = LocalOfficeConnection.getInstance();
//      localOfficeConnection.setOfficePath("d:\\Programme\\OpenOffice.org1.1.3");
//      localOfficeConnection.openConnection();
//      
//      TextDocument textDocument = (TextDocument)TextDocumentFactory.createTextDocument(localOfficeConnection.getXMultiComponentFactory(), localOfficeConnection.getXComponentContext());
//      
//      ITextTable textTable = textDocument.getTextTableService().constructTextTable(5, 15);
//      textDocument.getTextService().getTextContentService().insertTextContent(textTable);
//      
//      ITextTableCell cell = textTable.getCell("A2");
//      ICharacterProperties characterProperties = cell.getTextService().getText().getTextCursorService().getTextCursor().getCharacterProperties();
//      System.out.println(characterProperties.isFontBold());
//      
//    }
//    catch(Exception exception) {
//      exception.printStackTrace();
//      TestCase.fail();
//    }    
	}
  //----------------------------------------------------------------------------
	public void testGetParagraphs() {
//		 try {
//      LocalOfficeConnection localOfficeConnection = LocalOfficeConnection.getInstance();
//      localOfficeConnection.setOfficePath("d:\\Programme\\OpenOffice.org1.1.3");
//      localOfficeConnection.openConnection();
//      
//      TextDocument textDocument = (TextDocument)TextDocumentFactory.createTextDocument(localOfficeConnection.getXMultiComponentFactory(), localOfficeConnection.getXComponentContext());
//      
//      ITextTable textTable = textDocument.getTextTableService().constructTextTable(5, 15);
//      textDocument.getTextService().getTextContentService().insertTextContent(textTable);
//      
//      IParagraph[] paragraphs = textTable.getCell("A2").getTextService().getText().getTextContentEnumeration().getParagraphs();
//      for(int i = 0; i < paragraphs.length; i++) {
//      	System.out.println(paragraphs[i].getParagraphProperties().getParaAdjust());
//      }
//      
//    }
//    catch(Exception exception) {
//      exception.printStackTrace();
//      TestCase.fail();
//    }    
	}
	
	public void testCellWidths() {
	 try {     
      LocalOfficeApplication localOfficeApplication = new LocalOfficeApplication(null);
      LocalOfficeApplicationConfiguration configuration = new LocalOfficeApplicationConfiguration();
      configuration.setApplicationHomePath("d:\\Programme\\OpenOffice.org1.1.3");
      localOfficeApplication.setConfiguration(configuration);
      localOfficeApplication.activate();
      ITextDocument textDocument = (ITextDocument)localOfficeApplication.getDocumentService().constructNewDocument(IDocument.WRITER, new DocumentDescriptor());
		  
		  ITextTable textTable = textDocument.getTextTableService().constructTextTable(5, 15);
		  textDocument.getTextService().getTextContentService().insertTextContent(textTable);
		  
		  ITextTableProperties tableProperties = textTable.getProperties();
		  tableProperties.getCellWidths();
		  
		  
		}
		catch(Exception exception) {
		  exception.printStackTrace();
      Assert.fail();
		}  
	}
  //----------------------------------------------------------------------------
}
