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
 * Last changes made by $Author: andreas $, $Date: 2006-11-22 09:31:27 +0100 (Mi, 22 Nov 2006) $
 */
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;

import ag.ion.bion.officelayer.desktop.GlobalCommands;
import ag.ion.bion.officelayer.desktop.IFrame;

import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.HashMap;

/**
 * This code snippet disables commands within the OpenOffice.org
 * User Interface.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11063 $
 * @date 16.07.2006
 */
public class Snippet14 {

	/*
	 * The path to the office application, in this case on a windows system.
	 * 
	 * On a Linux system this would look like: 
	 * => private final static String officeHome = "/usr/lib/ooo-2.0"; 
	 */
	private final static String OPEN_OFFICE_ORG_PATH = "C:\\Programme\\OpenOffice.org 2.0"; 
		
	public static void main(String[] args) {				
		try {
			HashMap configuration = new HashMap();
			configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
			configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
			final IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);	
			
			officeAplication.setConfiguration(configuration);
			officeAplication.activate();
			
			final Frame frame = new Frame();
      frame.setVisible(true);
      frame.setSize(400, 400);
      frame.validate();
      Panel panel = new Panel(new BorderLayout());
      frame.add(panel);  
      panel.setVisible(true);
      frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent windowEvent) {
          frame.dispose();
          try {
            officeAplication.deactivate();
          }
          catch (OfficeApplicationException applicationException) {            
          }
        }        
      }); 
      
      IFrame officeFrame = officeAplication.getDesktopService().constructNewOfficeFrame(panel);
      officeAplication.getDocumentService().constructNewDocument(officeFrame, IDocument.WRITER, DocumentDescriptor.DEFAULT);
      frame.validate();
      
      //Now it is time to disable two commands in the frame
      officeFrame.disableDispatch(GlobalCommands.CLOSE_DOCUMENT);
      officeFrame.disableDispatch(GlobalCommands.QUIT_APPLICATION);
      officeFrame.updateDispatches();
		} 
		catch (Throwable throwable) {
			throwable.printStackTrace();
		} 
	}
}