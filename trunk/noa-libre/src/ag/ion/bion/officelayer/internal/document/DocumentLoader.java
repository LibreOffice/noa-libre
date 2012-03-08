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
 * Last changes made by $Author: markus $, $Date: 2010-04-28 16:30:00 +0200 (Mi, 28 Apr 2010) $
 */
package ag.ion.bion.officelayer.internal.document;

import java.io.IOException;

import ag.ion.bion.officelayer.document.AbstractDocument;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.internal.draw.DrawingDocument;
import ag.ion.bion.officelayer.internal.formula.FormulaDocument;
import ag.ion.bion.officelayer.internal.presentation.PresentationDocument;
import ag.ion.bion.officelayer.internal.spreadsheet.SpreadsheetDocument;
import ag.ion.bion.officelayer.internal.text.GlobalTextDocument;
import ag.ion.bion.officelayer.internal.text.TextDocument;
import ag.ion.bion.officelayer.internal.web.WebDocument;
import ag.ion.noa.internal.db.DatabaseDocument;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.awt.XWindow;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.drawing.XDrawPagesSupplier;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XFrame;
import com.sun.star.io.XInputStream;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.presentation.XPresentationSupplier;
import com.sun.star.sdb.XOfficeDatabaseDocument;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;

/**
 * Document loading helper class. 
 * 
 * @author Andreas Bröker
 * @version $Revision: 11724 $
 */
public class DocumentLoader {

  //----------------------------------------------------------------------------
  /**
   * Loads document from submitted URL.
   * 
   * @param serviceProvider the service provider to be used
   * @param URL URL of the document
   * 
   * @return loaded document
   * 
   * @throws Exception if an OpenOffice.org communication error occurs
   * @throws IOException if document can not be found
   */
  public static IDocument loadDocument(IServiceProvider serviceProvider, String URL)
      throws Exception, IOException {
    return loadDocument(serviceProvider, URL, null);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document from submitted URL.
   * 
   * @param serviceProvider the service provider to be used
   * @param URL URL of the document
   * @param properties properties for OpenOffice.org
   * 
   * @return loaded document
   * 
   * @throws Exception if an OpenOffice.org communication error occurs
   * @throws IOException if document can not be found
   */
  public static IDocument loadDocument(IServiceProvider serviceProvider, String URL,
      PropertyValue[] properties) throws Exception, IOException {
    if (properties == null) {
      properties = new PropertyValue[0];
    }
    Object oDesktop = serviceProvider.createServiceWithContext("com.sun.star.frame.Desktop");
    XComponentLoader xComponentLoader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,
        oDesktop);
    return loadDocument(serviceProvider, xComponentLoader, URL, "_blank", 0, properties);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted XInputStream implementation.
   * 
   * @param serviceProvider the service provider to be used
   * @param xInputStream OpenOffice.org XInputStream inplementation
   * 
   * @return loaded Document
   * 
   * @throws Exception if an OpenOffice.org communication error occurs
   * @throws IOException if document can not be found
   */
  public static IDocument loadDocument(IServiceProvider serviceProvider, XInputStream xInputStream)
      throws Exception, IOException {
    return loadDocument(serviceProvider, xInputStream, null);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted XInputStream implementation.
   * 
   * @param serviceProvider the service provider to be used
   * @param xInputStream OpenOffice.org XInputStream inplementation
   * @param properties properties for OpenOffice.org
   * 
   * @return loaded Document
   * 
   * @throws Exception if an OpenOffice.org communication error occurs
   * @throws IOException if document can not be found
   */
  public static IDocument loadDocument(IServiceProvider serviceProvider, XInputStream xInputStream,
      PropertyValue[] properties) throws Exception, IOException {
    if (properties == null) {
      properties = new PropertyValue[0];
    }
    PropertyValue[] newProperties = new PropertyValue[properties.length + 1];
    for (int i = 0; i < properties.length; i++) {
      newProperties[i] = properties[i];
    }
    newProperties[properties.length] = new PropertyValue();
    newProperties[properties.length].Name = "InputStream";
    newProperties[properties.length].Value = xInputStream;

    Object oDesktop = serviceProvider.createServiceWithContext("com.sun.star.frame.Desktop");
    XComponentLoader xComponentLoader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,
        oDesktop);
    return loadDocument(serviceProvider,
        xComponentLoader,
        "private:stream",
        "_blank",
        0,
        newProperties);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document from the submitted URL into the OpenOffice.org frame.
   * 
   * @param serviceProvider the service provider to be used
   * @param xFrame frame to used for document
   * @param URL URL of the document
   * @param searchFlags search flags for the target frame
   * @param properties properties for OpenOffice.org
   * 
   * @return loaded document
   * 
   * @throws Exception if an OpenOffice.org communication error occurs
   * @throws IOException if document can not be found
   */
  public static IDocument loadDocument(IServiceProvider serviceProvider, XFrame xFrame, String URL,
      int searchFlags, PropertyValue[] properties) throws Exception, IOException {
    if (xFrame != null) {
      if (properties == null) {
        properties = new PropertyValue[0];
      }
      XComponentLoader xComponentLoader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,
          xFrame);
      return loadDocument(serviceProvider,
          xComponentLoader,
          URL,
          xFrame.getName(),
          searchFlags,
          properties);
    }
    return null;
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document into OpenOffice.org
   * 
   * @param serviceProvider the service provider to be used
   * @param xComponentLoader OpenOffice.org component loader
   * @param URL URL of the document
   * @param targetFrameName name of the OpenOffice.org target frame
   * @param searchFlags search flags for the target frame
   * @param properties properties for OpenOffice.org
   * 
   * @return loaded document
   * 
   * @throws Exception if an OpenOffice.org communication error occurs
   * @throws IOException if document can not be found
   */
  private static IDocument loadDocument(IServiceProvider serviceProvider,
      XComponentLoader xComponentLoader, String URL, String targetFrameName, int searchFlags,
      PropertyValue[] properties) throws Exception, IOException {
    DocumentService.checkMaxOpenDocuments(serviceProvider);
    XComponent xComponent = xComponentLoader.loadComponentFromURL(URL,
        targetFrameName,
        searchFlags,
        properties);
    if (xComponent != null) {
      return getDocument(xComponent, serviceProvider, properties);
    }
    throw new IOException("Document not found.");
  }

  //----------------------------------------------------------------------------
  /**
   * Returns document on the basis of the submitted OpenOffice.org XComponent. Returns
   * null if the document can not be builded.
   * 
   * @param xComponent OpenOffice.org XComponent or null if the document can not be 
   * builded
   * @param serviceProvider the service provider to be used for the documents
   * @param intitialProperties the properties that were used loading the document
   * 
   * @return constructed document or null
   */
  public static IDocument getDocument(XComponent xComponent, IServiceProvider serviceProvider,
      PropertyValue[] intitialProperties) {
    if (intitialProperties == null) {
      intitialProperties = new PropertyValue[0];
    }
    IDocument document = null;
    XServiceInfo xServiceInfo = (XServiceInfo) UnoRuntime.queryInterface(XServiceInfo.class,
        xComponent);
    if (xServiceInfo.supportsService("com.sun.star.text.TextDocument")) {
      XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class,
          xComponent);
      if (xTextDocument != null) {
        document = new TextDocument(xTextDocument, intitialProperties);
      }
    }
    else if (xServiceInfo.supportsService("com.sun.star.sheet.SpreadsheetDocument")) {
      XSpreadsheetDocument xSpreadsheetDocument = (XSpreadsheetDocument) UnoRuntime.queryInterface(XSpreadsheetDocument.class,
          xComponent);
      if (xSpreadsheetDocument != null) {
        document = new SpreadsheetDocument(xSpreadsheetDocument, intitialProperties);
      }
    }
    else if (xServiceInfo.supportsService("com.sun.star.presentation.PresentationDocument")) {
      XPresentationSupplier presentationSupplier = (XPresentationSupplier) UnoRuntime.queryInterface(XPresentationSupplier.class,
          xComponent);
      if (presentationSupplier != null) {
        document = new PresentationDocument(presentationSupplier, intitialProperties);
      }
    }
    else if (xServiceInfo.supportsService("com.sun.star.drawing.DrawingDocument")) {
      XDrawPagesSupplier xDrawPagesSupplier = (XDrawPagesSupplier) UnoRuntime.queryInterface(XDrawPagesSupplier.class,
          xComponent);
      if (xDrawPagesSupplier != null) {
        document = new DrawingDocument(xDrawPagesSupplier, intitialProperties);
      }
    }
    else if (xServiceInfo.supportsService("com.sun.star.formula.FormulaProperties")) {
      XPropertySet xPropertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,
          xComponent);
      if (xPropertySet != null) {
        document = new FormulaDocument(xPropertySet, intitialProperties);
      }
    }
    else if (xServiceInfo.supportsService("com.sun.star.text.WebDocument")) {
      XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class,
          xComponent);
      if (xTextDocument != null) {
        document = new WebDocument(xTextDocument, intitialProperties);
      }
    }
    else if (xServiceInfo.supportsService("com.sun.star.text.GlobalDocument")) {
      XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class,
          xComponent);
      if (xTextDocument != null) {
        document = new GlobalTextDocument(xTextDocument, intitialProperties);
      }
    }
    else if (xServiceInfo.supportsService("com.sun.star.sdb.OfficeDatabaseDocument")) {
      XOfficeDatabaseDocument xOfficeDatabaseDocument = (XOfficeDatabaseDocument) UnoRuntime.queryInterface(XOfficeDatabaseDocument.class,
          xComponent);
      if (xOfficeDatabaseDocument != null) {
        document = new DatabaseDocument(xOfficeDatabaseDocument, intitialProperties);
      }
    }
    if (document != null && document instanceof AbstractDocument) {
      ((AbstractDocument) document).setServiceProvider(serviceProvider);
      boolean isHidden = false;
      for (int i = 0; i < intitialProperties.length; i++) {
        if (intitialProperties[i].Name.equals("Hidden") && intitialProperties[i].Value.equals(Boolean.TRUE)) {
          isHidden = true;
          break;
        }
      }
      //XXX WORKAROUND: If you haven an app with more than one openoffice noa integrated frames, then the second frame and upwards is sometimes
      //not displayed under linux. The following lines of code works around this issue.
      if (!isHidden) {
        if (document.getServiceProvider() != null) {
          XWindow containerWindow = document.getFrame().getXFrame().getContainerWindow();
          containerWindow.setVisible(false);
          containerWindow.setVisible(true);
        }
      }
    }
    return document;
  }

  //----------------------------------------------------------------------------

}