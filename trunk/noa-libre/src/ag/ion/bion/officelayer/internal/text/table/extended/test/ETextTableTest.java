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
package ag.ion.bion.officelayer.internal.text.table.extended.test;

import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.LocalOfficeApplicationConfiguration;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;

import ag.ion.bion.officelayer.internal.text.table.extended.ETextTable;

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
//import ag.ion.bion.officelayer.text.table.extended.IETextTableCellRange;


import junit.framework.TestCase;

/**
 * @author Miriam Sutter
 */
public class ETextTableTest extends TestCase {

	public void testETextTable() {
		IOfficeApplication officeApplication = OfficeApplicationRuntime.getLocalOfficeApplication();
		ITextDocument docu = null;
		try {
	
			LocalOfficeApplicationConfiguration localOfficeApplicationConfiguration = new LocalOfficeApplicationConfiguration();
			localOfficeApplicationConfiguration.setPort("8101");
			localOfficeApplicationConfiguration.setApplicationHomePath("d:\\Programme\\OpenOffice.org1.1.3");
			officeApplication.setConfiguration(localOfficeApplicationConfiguration);
			officeApplication.activate();
			docu = (ITextDocument)officeApplication.getDocumentService().loadDocument("file:///d:/java/eclipse/workspace/officeapi/test/testETables.sxw");
			
			ITextTable table = docu.getTextTableService().getTextTables()[0];
			ETextTable textTable = new ETextTable(docu,table);
			
			textTable.addRows(200); //o.k.
			
			textTable.getCell(10,1).setCellFormula("<A100>+<B3>");
			textTable.getCell(10,2).setCellFormula("<A100:A200>+<B3:B7>");
//			textTable.addRows(1,10);	// o.k.
//			textTable.addRows(100,10); //o.k.
////			textTable.addRows(1,82); // o.k.
////			textTable.addRows(1,83); // o.k.
////				
////			textTable.addRows(100,82); // o.k. 
////				
//			textTable.addRows(1,220); // o.k.
//			textTable.addRows(100,220); // o.k.
				
//				IETextTableCellRange tableCellRange = textTable.getCellRange(0,100,5,110);
//				Object[][] objects = tableCellRange.getData();
//				for(int i = 0; i < objects.length; i++) { 
//					for(int j = 0; j < objects[i].length; j++) {
//						objects[i][j] = i + "/" + j;
//					}
//				}
//				tableCellRange.setData(objects);
//				System.out.println(textTable.getColumn(0).getCellRange().getRangeName().getRangeName());
//				System.out.println(textTable.getColumn(0).getCellRange().getCells()[0].getName().getName());;
//				System.out.println(textTable.getRow(7).getCellRange().getRangeName().getRangeName());
//				System.out.println(textTable.getRows()[7].getCellRange().getRangeName().getRangeName());
//				System.out.println(textTable.getCellRange(0,30,5,50).getRangeName().getRangeName());
//				
//				System.out.println(textTable.getColumn(0).getCellRange().getRangeName().getRangeName());
				
//				System.out.println(textTable.getCellRange(0,30,5,150).getRangeName().getRangeName());
//				System.out.println(textTable.getCellRange(0,0,textTable.getColumnCount()-1,textTable.getRowCount()-1).getRangeName().getRangeName());
//				System.out.println(textTable.getColumn(0).getCellRange().getCell(0,200,textTable.getColumnCount()).getName().getName());
//				System.out.println(textTable.getColumn(0).getCellRange().getCell(0,125,textTable.getColumnCount()).getTableCell().getTextService().getText().getText());
//				
//				ITextTable[] textTables = textTable.getTextTableManagement().getTextTables();
//				for(int i = 0; i < textTables.length; i++) {
//					System.out.println(textTables[i].getName());
//				}
				docu.close();
			}
			catch (Exception excep) {
				try {
					docu.close();
					officeApplication.deactivate();
				}
				catch(Exception e) {
					fail();
				}
				fail();
			}
	}
	
}
