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
 *  http://www.ion.ag                                                       *
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa.test;

import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;

import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;

import ag.ion.bion.officelayer.text.ITextDocument;

import junit.framework.TestCase;

import java.io.FileOutputStream;

import java.util.HashMap;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Test case for a remote connection to OpenOffice.org.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 17.08.2006
 */ 
public class RemoteWriteTest extends TestCase {
  
  private static Logger LOGGER = Logger.getLogger("ag.ion");
  
  //----------------------------------------------------------------------------
  /**
   * Main entry point for the OpenOffice.org Remote Write Test.
   *
   * @param args arguments of the test
   *
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public static void main(String[] args) {
    if(args.length < 2) {
      System.out.println("NOA Remote Write Test");
      System.out.println("-------------------");
      System.out.println("Usage:");
      System.out.println("RemoteWriteTest <host> <port>");
    }
    else { 
      LogManager.getLogManager().reset();
      ConsoleHandler consoleHandler = new ConsoleHandler();
      consoleHandler.setLevel(Level.FINEST);
      LOGGER.addHandler(consoleHandler);
      LOGGER.setLevel(Level.FINEST);
      
      try {
        FileHandler fileHandler = new FileHandler("log.xml");
        fileHandler.setLevel(Level.FINEST);
        LOGGER.addHandler(fileHandler);
      }
      catch (Throwable throwable) {
      }
      RemoteWriteTest remoteWriteTest = new RemoteWriteTest();
      remoteWriteTest.test(args[0], args[1]);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Test OpenOffice.org remote writing.
   *
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public void testOfficeBean() {
    RemoteWriteTest remoteWriteTest = new RemoteWriteTest();
    remoteWriteTest.test("localhost", "8101");
  }
  //----------------------------------------------------------------------------
  /**
   * Test the OpenOffice.org Bean.
   *
   * @param officeHome home path to OpenOffice.org
   *
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public void test(String host, String port) {
    System.out.println("NOA Remote Write Test");
    System.out.println("Host: " + host);
    System.out.println("Port: " + port);
    HashMap hashMap = new HashMap(2);
    hashMap.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.REMOTE_APPLICATION);
    hashMap.put(IOfficeApplication.APPLICATION_HOST_KEY, host);
    hashMap.put(IOfficeApplication.APPLICATION_PORT_KEY, port);
    
    try {
      IOfficeApplication officeApplication = OfficeApplicationRuntime.getApplication(hashMap);
      officeApplication.activate();
      
      System.out.println("Constructing a new writer document ...");
      ITextDocument textDocument = (ITextDocument)officeApplication.getDocumentService().constructNewDocument(IDocument.WRITER, DocumentDescriptor.DEFAULT_HIDDEN);
      System.out.println("Inserting text into the new writer document ...");
      textDocument.getTextService().getText().setText("This is a NOA test.");
      System.out.println("Storing new writer document ...");
      textDocument.getPersistenceService().store(new FileOutputStream("noatest.odt"));
      textDocument.close();
      officeApplication.deactivate();
      
    }
    catch(Throwable throwable) {
      throwable.printStackTrace();
    }
    System.out.println("NOA Remote Write Test successfully.");
  }
  //----------------------------------------------------------------------------
  
}