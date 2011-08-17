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
import java.util.HashMap;
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.event.ICloseEvent;
import ag.ion.bion.officelayer.event.ICloseListener;
import ag.ion.bion.officelayer.event.IEvent;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.noa.NOAException;


/**
 * You may have noticed it already: there may occur problems if the opened document is 
 * closed by the user. So there must be a possibility to notice the closing of the 
 * document. This is done via a listener, which will be discussed in this snippet.
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 * @date 17.03.2006
 */
public class Snippet4 {

	/*
	 * The path to the office application, in this case on a(n OpenSUSE)Linux system.
	 * 
	 * On a Windows system this would look like: 
	 * => private final static String officeHome = "C:\\Programme\\OpenOffice.org 2.0"; 
	 */
	private final static String OPEN_OFFICE_ORG_PATH = "/usr/lib/ooo-2.0"; 
	
	/**
	 * The internal close listener of the document. It does not really 
	 * make sense but is actually expedient enough to show how it works .
	 * 
	 * @author Sebastian Rösgen
	 */
	private static class InternalCloseListener implements ICloseListener {

	  //----------------------------------------------------------------------------
	  /**
	   * Is called when someone tries to close a listened object. Not needed in
	   * here.
	   * 
	   * @param closeEvent close event
	   * @param getsOwnership information about the ownership
	   * 
	   * @author Sebastian Rösgen
	   * @date 17.03.2006
	   */ 
		public void queryClosing(ICloseEvent closeEvent, boolean getsOwnership) {
			//nothing to do in here
		}
	  //----------------------------------------------------------------------------
	  /**
	   * Is called when the listened object is closed really.
	   * 
	   * @param closeEvent close event
	   * 
	   * @author Sebastian Rösgen
	   * @date 17.03.2006
	   */
		public void notifyClosing(ICloseEvent closeEvent) {
			try {
				officeAplication.deactivate(); // this is really necessary
				System.out.println("Office application deactivated.");
			} 
			catch (OfficeApplicationException exception) {
				System.err.println("Error closing office application!");
				exception.printStackTrace();
			}			
		}		
	  //----------------------------------------------------------------------------
	  /**
	   * Is called when the broadcaster is about to be disposed. 
	   * 
	   * @param event source event
	   * 
	   * @author Sebastian Rösgen
	   * @date 17.03.2006
	   */
		public void disposing(IEvent event) {
			//nothing to do in here
		}
	  //----------------------------------------------------------------------------
	}
		
	public static IOfficeApplication officeAplication = null;
	
	public static void main(String[] args) {
		HashMap configuration = new HashMap();
		configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
		configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
		
		try {
			officeAplication = OfficeApplicationRuntime.getApplication(configuration);
			officeAplication.activate();
			IDocumentService documentService = officeAplication.getDocumentService();
			IDocument document = documentService.constructNewDocument(IDocument.WRITER, DocumentDescriptor.DEFAULT);
			ITextDocument textDocument = (ITextDocument)document;
			textDocument.addCloseListener(new InternalCloseListener());
		} 
		catch (OfficeApplicationException exception) {
			exception.printStackTrace();
		} 
		catch (NOAException exception) {
			exception.printStackTrace();
		}
	}

}