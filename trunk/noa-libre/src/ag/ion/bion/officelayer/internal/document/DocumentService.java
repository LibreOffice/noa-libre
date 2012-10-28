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
 * Last changes made by $Author: markus $, $Date: 2010-07-13 14:20:22 +0200 (Di, 13 Jul 2010) $
 */
package ag.ion.bion.officelayer.internal.document;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ag.ion.bion.officelayer.application.connection.IOfficeConnection;
import ag.ion.bion.officelayer.desktop.IFrame;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.DocumentException;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;
import ag.ion.noa.NOAException;
import ag.ion.noa.db.IDatabaseDocument;
import ag.ion.noa.document.URLAdapter;
import ag.ion.noa.internal.db.DatabaseDocument;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.beans.PropertyState;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.container.XEnumeration;
import com.sun.star.frame.FrameSearchFlag;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.XComponent;
import com.sun.star.sdb.XDocumentDataSource;
import com.sun.star.sdb.XOfficeDatabaseDocument;
import com.sun.star.uno.Any;
import com.sun.star.uno.UnoRuntime;

/**
 * Service for documents.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11756 $
 */
public class DocumentService implements IDocumentService {

  private static final String WRITER_FACTORY_URL  = "private:factory/swriter";               //$NON-NLS-1$
  private static final String CALC_FACTORY_URL    = "private:factory/scalc";                 //$NON-NLS-1$
  private static final String IMPRESS_FACTORY_URL = "private:factory/simpress";              //$NON-NLS-1$
  private static final String DRAW_FACTORY_URL    = "private:factory/sdraw";                 //$NON-NLS-1$
  private static final String MATH_FACTORY_URL    = "private:factory/smath";                 //$NON-NLS-1$
  private static final String WEB_FACTORY_URL     = "private:factory/swriter/web";           //$NON-NLS-1$
  private static final String GLOBAL_FACTORY_URL  = "private:factory/swriter/GlobalDocument"; //$NON-NLS-1$

  private IOfficeConnection   officeConnection    = null;
  private IServiceProvider    serviceProvider     = null;
  private XComponentLoader    xComponentLoader    = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new DocumentService.
   * 
   * @param officeConnection office connection to be used
   * @param serviceProvider the service provider to be used
   * 
   * @throws IllegalArgumentException if the submitted office connection is not valid
   * 
   * @author Andreas Bröker
   */
  public DocumentService(IOfficeConnection officeConnection, IServiceProvider serviceProvider)
      throws IllegalArgumentException {
    if (officeConnection == null)
      throw new IllegalArgumentException("The submitted office connection is not valid."); //$NON-NLS-1$
    this.officeConnection = officeConnection;
    this.serviceProvider = serviceProvider;
  }

  //----------------------------------------------------------------------------
  /**
   * Constructs new document.
   * 
   * @param documentType document type to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return new constructed document
   * 
   * @throws NOAException if the new document can not be constructed
   * 
   * @author Andreas Bröker
   */
  public IDocument constructNewDocument(String documentType, IDocumentDescriptor documentDescriptor)
      throws NOAException {
    return constructNewDocument(null, documentType, documentDescriptor);
  }

  //----------------------------------------------------------------------------
  /**
   * Constructs new document in the submitted frame.
   * 
   * <br><br>
   * Use one of the types:
   * <br><br>
   * <code>IDocument.WRITER</code><br>
   * <code>IDocument.CALC</code><br>
   * <code>IDocument.IMPRESS</code><br>
   * <code>IDocument.DRAW</code><br>
   * <code>IDocument.MATH</code><br>
   * <code>IDocument.WEB</code><br>
   * <code>IDocument.BASE</code><br>
   * <code>IDocument.GLOBAL</code>
   * 
   * @param frame frame to be used
   * @param documentType document type to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return new constructed document
   * 
   * @throws NOAException if the new document can not be constructed
   * 
   * @author Andreas Bröker
   */
  public IDocument constructNewDocument(IFrame frame, String documentType,
      IDocumentDescriptor documentDescriptor) throws NOAException {
    try {
      if (xComponentLoader == null)
        xComponentLoader = constructComponentLoader();

      checkMaxOpenDocuments(serviceProvider);

      String factoryURL = WRITER_FACTORY_URL;
      if (documentType != null) {
        if (documentType.equals(IDocument.CALC))
          factoryURL = CALC_FACTORY_URL;
        else if (documentType.equals(IDocument.DRAW))
          factoryURL = DRAW_FACTORY_URL;
        else if (documentType.equals(IDocument.IMPRESS))
          factoryURL = IMPRESS_FACTORY_URL;
        else if (documentType.equals(IDocument.MATH))
          factoryURL = MATH_FACTORY_URL;
        else if (documentType.equals(IDocument.WEB))
          factoryURL = WEB_FACTORY_URL;
        else if (documentType.equals(IDocument.GLOBAL))
          factoryURL = GLOBAL_FACTORY_URL;
        else if (documentType.equals(IDocument.BASE))
          return constructDatabaseDocument();
      }

      XComponent xComponent = null;
      PropertyValue[] props = DocumentDescriptorTransformer.documentDescriptor2PropertyValues(documentDescriptor);
      if (frame != null) {
        xComponent = xComponentLoader.loadComponentFromURL(factoryURL,
            frame.getXFrame().getName(),
            FrameSearchFlag.ALL,
            props); //$NON-NLS-1$ //$NON-NLS-2$
      }
      else {
        xComponent = xComponentLoader.loadComponentFromURL(factoryURL, "_blank", 0, props); //$NON-NLS-1$
      }
      IDocument document = DocumentLoader.getDocument(xComponent, serviceProvider, props);
      if (document == null)
        throw new DocumentException("The new document can not be constructed."); //$NON-NLS-1$
      return document;
    }
    catch (Throwable throwable) {
      throw new NOAException(throwable);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Constructs new hidden document of the submitted type.
   * <br><br>
   * Use one of the types:
   * <br><br>
   * <code>IDocument.WRITER</code><br>
   * <code>IDocument.CALC</code><br>
   * <code>IDocument.IMPRESS</code><br>
   * <code>IDocument.DRAW</code><br>
   * <code>IDocument.MATH</code><br>
   * <code>IDocument.WEB</code><br>
   * <code>IDocument.BASE</code><br>
   * <code>IDocument.GLOBAL</code>
   * 
   * @param documentType document type to be constructed
   * 
   * @return new constructed document of the submitted type
   * 
   * @throws NOAException if the document can not be contructed
   * 
   * @author Andreas Bröker
   * @date 16.03.2006
   */
  public IDocument constructNewHiddenDocument(String documentType) throws NOAException {
    return constructNewDocument(documentType, DocumentDescriptor.DEFAULT_HIDDEN);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted URL.
   * 
   * @param url URL of the document
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded or the URL does
   * not locate an OpenOffice.org document
   * 
   * @author Andreas Bröker
   */
  public IDocument loadDocument(String url) throws DocumentException {
    try {
      url = URLAdapter.adaptURL(url);
      IDocument document = DocumentLoader.loadDocument(serviceProvider, url);
      if (document != null)
        return document;
      else
        throw new DocumentException(Messages.getString("DocumentService_exception_url_invalid")); //$NON-NLS-1$
    }
    catch (Throwable throwable) {
      DocumentException documentException = new DocumentException(throwable.getMessage());
      documentException.initCause(throwable);
      throw documentException;
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted URL.
   * 
   * @param url URL of the document
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   *  
   * @throws NOAException if the document can not be loaded or the URL does
   * not locate an OpenOffice.org document
   * 
   * @author Andreas Bröker
   * @date 02.07.2006
   */
  public IDocument loadDocument(String url, IDocumentDescriptor documentDescriptor)
      throws NOAException {
    try {
      PropertyValue[] propertyValues = DocumentDescriptorTransformer.documentDescriptor2PropertyValues(documentDescriptor);
      url = URLAdapter.adaptURL(url);
      IDocument document = DocumentLoader.loadDocument(serviceProvider, url, propertyValues);
      if (document != null)
        return document;
      else
        throw new NOAException(Messages.getString("DocumentService_exception_url_invalid")); //$NON-NLS-1$
    }
    catch (Throwable throwable) {
      throw new NOAException(throwable);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted stream.
   * May be <b>significantly slower</b> than the load* variants which take an URL String as parameter.
   * 
   * @param inputStream input stream to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   */
  public IDocument loadDocument(InputStream inputStream, IDocumentDescriptor documentDescriptor)
      throws DocumentException {
    return loadDocument(null, null, inputStream, documentDescriptor);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted stream. 
   * 
   * <b>The document has no location and can not be stored with
   * the store() method of the <code>IPersistenceService</code>. Furthermore
   * OpenOffice.org can not recognize if the document is already open - 
   * therefore the document will be never opened in <code>ReadOnly</code> mode.</b>
   * 
   * May be <b>significantly slower</b> than the load* variants which take an URL String as parameter.
   * 
   * @param frame frame to be used for the document
   * @param inputStream input stream to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded
   * 
   * @author Andreas Bröker
   */
  public IDocument loadDocument(IFrame frame, InputStream inputStream,
      IDocumentDescriptor documentDescriptor) throws DocumentException {
    return loadDocument(null, frame, inputStream, documentDescriptor);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted stream.
   * May be <b>significantly slower</b> than the load* variants which take an URL String as parameter.
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * @param inputStream input stream to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded
   * 
   * @author Andreas Bröker
   */
  public IDocument loadDocument(IOfficeProgressMonitor officeProgressMonitor,
      InputStream inputStream, IDocumentDescriptor documentDescriptor) throws DocumentException {
    return loadDocument(officeProgressMonitor, null, inputStream, documentDescriptor);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document on the basis of the submitted stream.
   * 
   * <b>The document has no location and can not be stored with
   * the store() method of the <code>IPersistenceService</code>. Furthermore
   * OpenOffice.org can not recognize if the document is already open - 
   * therefore the document will be never opened in <code>ReadOnly</code> mode.</b>
   * 
   * May be <b>significantly slower</b> than the load* variants which take an URL String as parameter.
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * @param frame frame to be used for the document
   * @param inputStream input stream to be used
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded
   * 
   * @author Andreas Bröker
   */
  public IDocument loadDocument(IOfficeProgressMonitor officeProgressMonitor, IFrame frame,
      InputStream inputStream, IDocumentDescriptor documentDescriptor) throws DocumentException {
    XComponent xComponent = null;
    PropertyValue[] properties = new PropertyValue[1];
    try {
      if (inputStream == null)
        throw new DocumentException("The submitted input stream is not valid."); //$NON-NLS-1$

      if (xComponentLoader == null || !officeConnection.isConnected())
        xComponentLoader = constructComponentLoader();

      checkMaxOpenDocuments(serviceProvider);

      //begin task is done in class ByteArrayXInputStreamAdapter
      //if(officeProgressMonitor != null)
      //  officeProgressMonitor.beginTask(Messages.getString("DocumentService_monitor_message_preparing_loading"), IOfficeProgressMonitor.WORK_UNKNOWN); //$NON-NLS-1$

      ByteArrayXInputStreamAdapter byteArrayToXInputStreamAdapter = new ByteArrayXInputStreamAdapter(inputStream,
          officeProgressMonitor);
      properties[0] = new PropertyValue("InputStream", -1, byteArrayToXInputStreamAdapter, PropertyState.DIRECT_VALUE); //$NON-NLS-1$
      properties = DocumentDescriptorTransformer.documentDescriptor2PropertyValues(properties,
          documentDescriptor);

      if (frame == null)
        xComponent = xComponentLoader.loadComponentFromURL("private:stream", "_blank", 0, properties); //$NON-NLS-1$ //$NON-NLS-2$
      else
        xComponent = xComponentLoader.loadComponentFromURL("private:stream", frame.getXFrame().getName(), FrameSearchFlag.ALL, properties); //$NON-NLS-1$

    }
    catch (Throwable throwable) {
      throw new DocumentException(throwable);
    }

    if (officeProgressMonitor != null)
      officeProgressMonitor.beginSubTask(Messages.getString("DocumentService_monitor_investigating")); //$NON-NLS-1$
    IDocument document = DocumentLoader.getDocument(xComponent, serviceProvider, properties);
    if (document != null) {
      if (officeProgressMonitor != null) {
        officeProgressMonitor.beginSubTask(Messages.getString("DocumentService_monitor_loading_completed")); //$NON-NLS-1$
        officeProgressMonitor.done();
      }
      return document;
    }
    throw new DocumentException("The document can not be loaded."); //$NON-NLS-1$
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document into the submitted frame.
   * 
   * @param frame frame to be used for the document
   * @param url URL of the document (must start with file:///)
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded, the URL does
   * not locate an OpenOffice.org document of the submitted frame or URL is not valid
   * 
   * @author Andreas Bröker
   */
  public IDocument loadDocument(IFrame frame, String url) throws DocumentException {
    return loadDocument(frame, url, null);
  }

  //----------------------------------------------------------------------------
  /**
   * Loads document into the submitted frame.
   * 
   * @param frame frame to be used for the document
   * @param url URL of the document
   * @param documentDescriptor document descriptor to be used
   * 
   * @return loaded document
   * 
   * @throws DocumentException if the document can not be loaded, the URL does
   * not locate an OpenOffice.org document or the submitted frame or URL is not valid
   * 
   * @author Andreas Bröker
   */
  public IDocument loadDocument(IFrame frame, String url, IDocumentDescriptor documentDescriptor)
      throws DocumentException {
    if (url == null)
      throw new DocumentException("The submitted url is not valid."); //$NON-NLS-1$

    try {
      url = URLAdapter.adaptURL(url);
      IDocument document = null;
      if (frame == null) {
        document = DocumentLoader.loadDocument(serviceProvider,
            url,
            DocumentDescriptorTransformer.documentDescriptor2PropertyValues(documentDescriptor));
      }
      else {
        document = DocumentLoader.loadDocument(serviceProvider,
            frame.getXFrame(),
            url,
            FrameSearchFlag.ALL,
            DocumentDescriptorTransformer.documentDescriptor2PropertyValues(documentDescriptor));
      }
      if (document != null)
        return document;
      else
        throw new DocumentException(Messages.getString("DocumentService_exception_url_invalid")); //$NON-NLS-1$
    }
    catch (Exception exception) {
      DocumentException documentException = new DocumentException(exception.getMessage());
      documentException.initCause(exception);
      throw documentException;
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Returns current documents of an application.
   * 
   * @return documents of an application
   * 
   * @throws DocumentException if the documents cannot be provided
   * 
   * @author Markus Krüger
   */
  public IDocument[] getCurrentDocuments() throws DocumentException {
    return getCurrentDocuments(serviceProvider);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns current number of documents of an application.
   * 
   * @param serviceProvider the serviceProvider to be used
   * 
   * @return number documents of an application
   * 
   * @author Markus Krüger
   * @date 11.11.2008
   */
  public int getCurrentDocumentCount() throws DocumentException {
    return getCurrentDocumentCount(serviceProvider);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns current documents of an application.
   * 
   * @return documents of an application
   * 
   * @throws DocumentException if the documents cannot be provided
   * 
   * @author Markus Krüger
   * @date 11.11.2008
   */
  public static IDocument[] getCurrentDocuments(IServiceProvider serviceProvider)
      throws DocumentException {
    try {
      if (serviceProvider == null)
        return new IDocument[0];
      Object desktop = serviceProvider.createService("com.sun.star.frame.Desktop"); //$NON-NLS-1$
      XDesktop xDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, desktop);
      XEnumeration aktComponents = xDesktop.getComponents().createEnumeration();
      List arrayList = new ArrayList();
      while (aktComponents.hasMoreElements()) {
        Any a = (Any) aktComponents.nextElement();
        arrayList.add(DocumentLoader.getDocument((XComponent) a.getObject(), serviceProvider, null));
      }
      IDocument[] documents = new IDocument[arrayList.size()];
      documents = (IDocument[]) arrayList.toArray(documents);
      return documents;
    }
    catch (Exception exception) {
      throw new DocumentException(exception);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Returns current number of documents of an application.
   * 
   * @param serviceProvider the serviceProvider to be used
   * 
   * @return number documents of an application
   * 
   * @author Markus Krüger
   * @date 11.11.2008
   */
  public static int getCurrentDocumentCount(IServiceProvider serviceProvider)
      throws DocumentException {
    try {
      int i = 0;
      if (serviceProvider == null)
        return i;
      Object desktop = serviceProvider.createService("com.sun.star.frame.Desktop"); //$NON-NLS-1$
      XDesktop xDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, desktop);
      XEnumeration aktComponents = xDesktop.getComponents().createEnumeration();
      while (aktComponents.hasMoreElements()) {
        aktComponents.nextElement();
        i++;
      }
      return i;
    }
    catch (Exception exception) {
      throw new DocumentException(exception);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Checks if the OpenOffice.org instance reached the number of maximum open documents and
   * throws an exception if this is the case.
   * 
   * @param serviceProvider the serviceProvider to be used
   * 
   * @throws NOAException if the OpenOffice.org instance reached the number of maximum open documents.
   * 
   * @author Markus Krüger
   * @date 11.11.2008
   */
  public static void checkMaxOpenDocuments(IServiceProvider serviceProvider) throws NOAException {
    try {
      if (serviceProvider != null && getCurrentDocumentCount(serviceProvider) >= IDocumentService.MAX_OPENED_DOCS)
        throw new NOAException("The maximum number of opend documents was reached (Maximum number is " + IDocumentService.MAX_OPENED_DOCS //$NON-NLS-1$
            + ")."); //$NON-NLS-1$
    }
    catch (DocumentException documentException) {
      throw new NOAException(documentException);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Disposes all allocated resources.
   * 
   * @author Markus Krüger
   */
  public void dispose() {
  }

  //----------------------------------------------------------------------------
  /**
   * Constructs new database document.
   * 
   * @return new constructed database document
   * 
   * @throws NOAException if the new database document can not be constructed
   * 
   * @author Andreas Bröker
   * @date 16.03.2006
   */
  private IDatabaseDocument constructDatabaseDocument() throws NOAException {
    try {
      Object dataSource = officeConnection.getXMultiComponentFactory().createInstanceWithContext("com.sun.star.sdb.DataSource",
          officeConnection.getXComponentContext());
      XPropertySet propertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,
          dataSource);
      propertySet.setPropertyValue("URL", "sdbc:embedded:hsqldb");
      XDocumentDataSource documentDataSource = (XDocumentDataSource) UnoRuntime.queryInterface(XDocumentDataSource.class,
          dataSource);
      XOfficeDatabaseDocument officeDatabaseDocument = documentDataSource.getDatabaseDocument();
      return new DatabaseDocument(officeDatabaseDocument, null);
    }
    catch (Throwable throwable) {
      throw new NOAException(throwable);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Constructs new OpenOffice.org XComponentLoader.
   * 
   * @return new constructed OpenOffice.org XComponentLoader
   * 
   * @throws Exception if the OpenOffice.org XComponentLoader can not be constructed
   * 
   * @author Markus Krüger
   */
  private XComponentLoader constructComponentLoader() throws Exception {
    Object oDesktop = officeConnection.getXMultiComponentFactory().createInstanceWithContext("com.sun.star.frame.Desktop", officeConnection.getXComponentContext()); //$NON-NLS-1$
    return (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class, oDesktop);
  }

  //----------------------------------------------------------------------------

}