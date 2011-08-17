/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                                 *
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2007 by IOn AG                                            *
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
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/

/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.text.IText;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;

import java.util.HashMap;

/**
 * This code snippet creates a table and makes the first three line table header style.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class Snippet16 {

  /*
   * The path to the office application, in this case on a Windows system.
   * 
   * On a (n OpenSUSE)Linux system this would look like: 
   * => private final static String officeHome = "/usr/lib/OpenOffice.org2.1"; 
   */
  private final static String OPEN_OFFICE_ORG_PATH = "C:\\Programme\\OpenOffice.org 2.1"; 
    
  public static void main(String[] args) {
    try {
      HashMap configuration = new HashMap();
      configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
      configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
      final IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration); 
      officeAplication.setConfiguration(configuration);
      officeAplication.activate();
      IDocumentService documentService = officeAplication.getDocumentService();
      ITextDocument textDocument = (ITextDocument)documentService.constructNewDocument(IDocument.WRITER, DocumentDescriptor.DEFAULT);

      //construct table
      int rows = 15;
      int cols = 5;
      ITextTable textTable = textDocument.getTextTableService().constructTextTable(rows, cols);       
      textDocument.getTextService().getTextContentService().insertTextContent(textTable);
      textTable.setHeaderRows(3);
      //set some text
      for(int i = 0, n = rows; i < n; i++) {
        for(int j = 0, m = cols; j < m; j++) {
          IText cellText = textTable.getCell(j,i).getTextService().getText();
          cellText.setText("Line "+(i+1)+" Col "+(j+1));
        }
      }
    } 
    catch (Throwable exception) {
      exception.printStackTrace();
    } 
  }

}