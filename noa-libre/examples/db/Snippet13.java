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

import ag.ion.bion.officelayer.document.IDocument;

import java.util.HashMap;

/**
 * This code snippet creates a base document
 * and stores it.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 16.03.2006
 */
public class Snippet13 {
	
	public static void main(String args[]) {		
		String officeHome = "C:\\Programme\\OpenOffice.org 2.0"; //define your office home here
		String storePath = "C:\\MyDB.odb"; //define your db target path
		
		HashMap hashMap = new HashMap(2);
		hashMap.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
		hashMap.put(IOfficeApplication.APPLICATION_HOME_KEY, officeHome);

		try {
			IOfficeApplication application = OfficeApplicationRuntime.getApplication(hashMap);
			application.activate();
			IDocument document = application.getDocumentService().constructNewHiddenDocument(IDocument.BASE);
			
			document.getPersistenceService().store(storePath);
			application.deactivate();
			application.dispose();
		}
		catch(Throwable throwable) {
			throwable.printStackTrace();
		}
	}
	
}