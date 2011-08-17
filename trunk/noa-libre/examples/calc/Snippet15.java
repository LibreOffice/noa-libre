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
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;

import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentService;

import ag.ion.bion.officelayer.spreadsheet.ISpreadsheetDocument;

import com.sun.star.sheet.XSheetCellCursor;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheets;

import com.sun.star.table.XCell;

import com.sun.star.text.XText;

import com.sun.star.uno.UnoRuntime;

import java.util.HashMap;

/**
 * This code snippet shows how to insert data in a calc document.
 * Currently with UNO API access as it is not yet implemented in NOA.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 * @date 09.03.2007
 */
public class Snippet15 {

	/*
	 * The path to the office application, in this case on a windows system.
	 * 
	 * On a Linux system this would look like: 
	 * => private final static String officeHome = "/usr/lib/ooo-2.1"; 
	 */
	private final static String OPEN_OFFICE_ORG_PATH = "C:\\Programme\\OpenOffice.org 2.1"; 
		
	public static void main(String[] args) {
		try {
			HashMap configuration = new HashMap();
			configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
			configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
			IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);	
			officeAplication.setConfiguration(configuration);
			officeAplication.activate();
			IDocumentService documentService = officeAplication.getDocumentService();
			IDocument document = documentService.constructNewDocument(IDocument.CALC, DocumentDescriptor.DEFAULT);
			ISpreadsheetDocument spreadsheetDocument = (ISpreadsheetDocument) document;
      XSpreadsheets spreadsheets = spreadsheetDocument.getSpreadsheetDocument().getSheets();
      String sheetName= "Tabelle1";
      Object[][] rows = new Object[][]{
          new Object[]{"DataCell1","DataCell2","DataCell3","DataCell4"},
          new Object[]{new Integer(10),new Integer(20),new Integer(30),new Integer(40)},
          new Object[]{new Double(11.11),new Double(22.22),new Double(33.33),new Double(44.44)}};
      XSpreadsheet spreadsheet1 = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class,spreadsheets.getByName(sheetName));
      //insert your Data
      XSheetCellCursor cellCursor = spreadsheet1.createCursor();
      for(int i = 0; i < rows.length; i++) {
        Object[] cols = rows[i];
        for(int j = 0; j < cols.length; j++) {
          XCell cell= cellCursor.getCellByPosition(j,i);
          XText cellText = (XText)UnoRuntime.queryInterface(XText.class, cell);
          Object insert = cols[j];
          if(insert instanceof Number)
            cell.setValue(((Number)insert).doubleValue());
          else if(insert instanceof String)
            cellText.setString((String)insert);
          else
            cellText.setString(insert.toString());          
        }
      }
		} 
		catch (Throwable exception) {
			exception.printStackTrace();
		} 
	}

}