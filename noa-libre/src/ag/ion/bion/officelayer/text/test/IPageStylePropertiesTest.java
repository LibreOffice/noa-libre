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

import ag.ion.bion.officelayer.text.IPage;
import ag.ion.bion.officelayer.text.IPageService;
import ag.ion.bion.officelayer.text.IPageStyle;
import ag.ion.bion.officelayer.text.IPageStyleProperties;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Andreas Bröker
 */
public class IPageStylePropertiesTest extends TestCase {

  public void testGetIsLandscape() {
    try {
      LocalOfficeApplication localOfficeApplication = new LocalOfficeApplication(null);
      LocalOfficeApplicationConfiguration configuration = new LocalOfficeApplicationConfiguration();
      configuration.setApplicationHomePath("c:\\Programme\\OpenOffice.org1.1.3");
      localOfficeApplication.setConfiguration(configuration);
      localOfficeApplication.activate();
      
      ITextDocument textDocument = (ITextDocument)localOfficeApplication.getDocumentService().constructNewDocument(IDocument.WRITER, new DocumentDescriptor());
      IPageService pageService = textDocument.getPageService();
      IPage page = pageService.getPage(0);
      IPageStyle pageStyle = page.getPageStyle();
      IPageStyleProperties pageStyleProperties = pageStyle.getProperties();
      boolean value = pageStyleProperties.getIsLandscape();
      Assert.assertEquals(false, value);
      
      ITextTable textTable = textDocument.getTextTableService().constructTextTable(5, 15);
      textDocument.getTextService().getTextContentService().insertTextContent(textTable);
      page = pageService.getPage(0);
      pageStyle = page.getPageStyle();
      pageStyleProperties = pageStyle.getProperties();
      value = pageStyleProperties.getIsLandscape();
      Assert.assertEquals(false, value);
      
      localOfficeApplication.deactivate();
    }
    catch(Exception exception) {
      exception.printStackTrace();
      Assert.fail();
    }    
  }

}
