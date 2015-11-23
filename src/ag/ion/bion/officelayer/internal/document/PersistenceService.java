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
 * Last changes made by $Author: markus $, $Date: 2010-06-01 12:21:01 +0200 (Di, 01 Jun 2010) $
 */
package ag.ion.bion.officelayer.internal.document;

import java.io.OutputStream;
import java.net.URL;

import ag.ion.bion.officelayer.document.DocumentException;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IPersistenceService;
import ag.ion.bion.officelayer.filter.IFilter;
import ag.ion.bion.officelayer.filter.PDFFilter;
import ag.ion.noa.ErrorCodeTranslator;
import ag.ion.noa.NOAException;
import ag.ion.noa.document.URLAdapter;

import com.sun.star.beans.PropertyState;
import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XStorable;
import com.sun.star.lib.uno.adapter.OutputStreamToXOutputStreamAdapter;
import com.sun.star.task.ErrorCodeIOException;

/**
 * Persistence service for office documents.
 * 
 * @author Andreas Bröker
 * @author Alessandro Conte
 * @version $Revision: 11737 $
 */
public class PersistenceService implements IPersistenceService {

  private static final String ERROR_MESSAGE = Messages.getString("PersistenceService.general_error_message"); //$NON-NLS-1$

  private IDocument           document      = null;
  private XStorable           xStorable     = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new PersistenceService.
   * 
   * @param document document to be used
   * @param xStorable OpenOffice.org XStorable interface to be used
   * 
   * @throws IllegalArgumentException if the submitted document or the OpenOffice.org XStorable interface 
   * is not valid
   * 
   * @author Andreas Bröker
   */
  public PersistenceService(IDocument document, XStorable xStorable)
      throws IllegalArgumentException {
    if (document == null)
      throw new IllegalArgumentException(Messages.getString("PersistenceService.exception_document_invalid")); //$NON-NLS-1$
    this.document = document;

    if (xStorable == null)
      throw new IllegalArgumentException(Messages.getString("PersistenceService.exception_xstorable_interface_invalid")); //$NON-NLS-1$
    this.xStorable = xStorable;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the document has a location URL.
   * 
   * @return information whether the document has a location URL
   * 
   * @author Andreas Brueekr
   */
  public boolean hasLocation() {
    return xStorable.hasLocation();
  }

  //----------------------------------------------------------------------------
  /**
   * Returns location URL of the document. Returns null if
   * no location URL is available.
   * 
   * @return location URL of the document
   * 
   * @author Andreas Bröker
   */
  public URL getLocation() {
    String documentURL = xStorable.getLocation();
    if (documentURL == null)
      return null;
    try {
      URL url = new URL(documentURL);
      return url;
    }
    catch (Exception exception) {
      return null;
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Returns informations whether the doccument is in read only state 
   * or not.
   * 
   * @return informations whether the doccument is in read only state 
   * or not
   * 
   * @author Andreas Bröker
   */
  public boolean isReadOnly() {
    return xStorable.isReadonly();
  }

  //----------------------------------------------------------------------------
  /**
   * Stores document on to the location URL.
   * 
   * @throws DocumentException if the document can not be stored or no location URL is available
   * 
   * @author Andreas Bröker
   */
  public void store() throws DocumentException {
    try {
      xStorable.store();
    }
    catch (Throwable throwable) {
      String message = throwable.getMessage();
      if (throwable instanceof ErrorCodeIOException) {
        if (message == null || message.length() == 0)
          message = Messages.getString("PersistenceService.error_io_message", String.valueOf(((ErrorCodeIOException) throwable).ErrCode)); //$NON-NLS-1$
      }
      else if (message == null || message.length() == 0)
        message = ERROR_MESSAGE;
      throw new DocumentException(message, throwable);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Stores document to the submitted URL.
   * 
   * @param url URL to be used as location
   * 
   * @throws DocumentException if the document can not be stored
   * 
   * @author Andreas Bröker
   */
  public void store(String url) throws DocumentException {
    if (url == null)
      throw new DocumentException(Messages.getString("PersistenceService.error_url_invalid_message")); //$NON-NLS-1$

    try {
      url = URLAdapter.adaptURL(url);
      PropertyValue[] initialPropertyValues = document.getInitialProperties();
      String filterDefinition = null;
      for (int i = 0; i < initialPropertyValues.length; i++) {
        if (initialPropertyValues[i].Name.equalsIgnoreCase("FilterName"))
          filterDefinition = initialPropertyValues[i].Value.toString();
      }
      boolean useFilter = filterDefinition != null && filterDefinition.length() > 0;
      if (useFilter) {
        PropertyValue[] propertyValues = new PropertyValue[1];
        propertyValues[0] = new PropertyValue();
        propertyValues[0].Name = "FilterName"; //$NON-NLS-1$    
        propertyValues[0].Value = filterDefinition;
        xStorable.storeAsURL(url, propertyValues);
      }
      else {
        xStorable.storeAsURL(url, new PropertyValue[0]);
      }
      document.setModified(false);
    }
    catch (Throwable throwable) {
      String message = throwable.getMessage();
      if (throwable instanceof ErrorCodeIOException) {
        if (message == null || message.length() == 0)
          message = Messages.getString("PersistenceService.error_io_message", String.valueOf(((ErrorCodeIOException) throwable).ErrCode)); //$NON-NLS-1$
      }
      else if (message == null || message.length() == 0)
        message = ERROR_MESSAGE;
      throw new DocumentException(message, throwable);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Stored document in the submitted output stream. 
   * 
   * @param outputStream output stream to be used
   * 
   * @throws DocumentException if the document can not be stored
   * 
   * @author Andreas Bröker
   * @author Alessandro Conte
   * @date 07.09.2006
   */
  public void store(OutputStream outputStream) throws DocumentException {
    document.fireDocumentEvent(IDocument.EVENT_ON_SAVE);
    try {
      storeInternal(outputStream);
    }
    catch (Throwable throwable) {
      String message = throwable.getMessage();
      if (throwable instanceof ErrorCodeIOException) {
        if (message == null || message.length() == 0)
          message = Messages.getString("PersistenceService.error_io_message", String.valueOf(((ErrorCodeIOException) throwable).ErrCode)); //$NON-NLS-1$
      }
      else if (message == null || message.length() == 0)
        message = ERROR_MESSAGE;
      throw new DocumentException(message, throwable);
    }
    document.fireDocumentEvent(IDocument.EVENT_ON_SAVE_DONE);
    document.fireDocumentEvent(IDocument.EVENT_ON_SAVE_FINISHED);
  }

  //----------------------------------------------------------------------------
  /**
   * Stores document in the submitted output stream.
   * And fires save-as events on office document.
   * 
   * @param outputStream output stream to be used
   * 
   * @throws NOAException if the document can not be stored
   * 
   * @author Alessandro Conte
   * @date 07.09.2006
   */
  public void storeAs(OutputStream outputStream) throws NOAException {
    document.fireDocumentEvent(IDocument.EVENT_ON_SAVE_AS);
    try {
      storeInternal(outputStream);
    }
    catch (Throwable throwable) {
      String message = throwable.getMessage();
      if (throwable instanceof ErrorCodeIOException) {
        if (message == null || message.length() == 0)
          message = Messages.getString("PersistenceService.error_io_message", String.valueOf(((ErrorCodeIOException) throwable).ErrCode)); //$NON-NLS-1$
      }
      else if (message == null || message.length() == 0)
        message = ERROR_MESSAGE;
      throw new NOAException(message, throwable);
    }
    document.fireDocumentEvent(IDocument.EVENT_ON_SAVE_AS_DONE);
  }

  //----------------------------------------------------------------------------
  /**
   * Exports document to the URL on the basis of the submitted filter.
   * 
   * @param url URL to be used as location
   * @param filter filter to be used
   * 
   * @throws DocumentException if the document can not be exported
   * 
   * @author Andreas Bröker
   */
  public void export(String url, IFilter filter) throws DocumentException {
    if (url == null)
      throw new DocumentException(Messages.getString("PersistenceService.error_url_invalid_message")); //$NON-NLS-1$

    if (filter == null)
      throw new DocumentException(Messages.getString("PersistenceService.error_filter_invalid_message")); //$NON-NLS-1$

    String filterDefinition = filter.getFilterDefinition(document);

    PropertyValue[] properties = filter instanceof PDFFilter ? new PropertyValue[2]
        : new PropertyValue[1];

    if (filterDefinition != null) {
      properties[0] = new PropertyValue();
      properties[0].Name = "FilterName"; //$NON-NLS-1$
      properties[0].Value = filterDefinition;

      if (filter instanceof PDFFilter) {
        properties[1] = new PropertyValue();
        properties[1].Name = "FilterData";
        properties[1].Value = ((PDFFilter) filter).getPDFFilterProperties().toPropertyValues();
      }
    }

    if (!filter.isExternalFilter())
      document.fireDocumentEvent(IDocument.EVENT_ON_SAVE_AS);
    try {
      url = URLAdapter.adaptURL(url);
      xStorable.storeToURL(url, properties);
    }
    catch (Throwable throwable) {
      String message = throwable.getMessage();
      if (throwable instanceof ErrorCodeIOException) {
        message = ErrorCodeTranslator.getErrorCodeMessage(((ErrorCodeIOException) throwable).ErrCode);
        if (message == null)
          message = Messages.getString("PersistenceService.error_io_message", String.valueOf(((ErrorCodeIOException) throwable).ErrCode)); //$NON-NLS-1$
      }
      else if (message == null || message.length() == 0)
        message = ERROR_MESSAGE;
      throw new DocumentException(message, throwable);
    }
    if (!filter.isExternalFilter())
      document.fireDocumentEvent(IDocument.EVENT_ON_SAVE_AS_DONE);
  }

  //----------------------------------------------------------------------------
  /**
   * Exports document into the output stream on the basis of the submitted filter.
   * 
   * @param outputStream output stream to be used
   * @param filter filter to be used
   * 
   * @throws NOAException if the document can not be exported
   * 
   * @author Andreas Bröker
   * @date 25.08.2006
   */
  public void export(OutputStream outputStream, IFilter filter) throws NOAException {
    if (outputStream == null)
      throw new NOAException(Messages.getString("PersistenceService_error_message_invalid_output_stream")); //$NON-NLS-1$

    if (filter == null || !filter.isSupported(document))
      throw new NOAException(Messages.getString("PersistenceService.error_filter_invalid_message")); //$NON-NLS-1$

    String filterDefinition = filter.getFilterDefinition(document);
    OutputStreamToXOutputStreamAdapter streamAdapter = new OutputStreamToXOutputStreamAdapter(outputStream);

    PropertyValue[] properties = filter instanceof PDFFilter ? new PropertyValue[3]
        : new PropertyValue[2];

    properties[0] = new PropertyValue();
    properties[0].Name = "FilterName"; //$NON-NLS-1$
    properties[0].Value = filterDefinition;
    properties[1] = new PropertyValue("OutputStream", -1, streamAdapter, PropertyState.DIRECT_VALUE); //$NON-NLS-1$
    if (filter instanceof PDFFilter) {
      properties[2] = new PropertyValue();
      properties[2].Name = "FilterData";
      properties[2].Value = ((PDFFilter) filter).getPDFFilterProperties().toPropertyValues();
    }

    if (!filter.isExternalFilter())
      document.fireDocumentEvent(IDocument.EVENT_ON_SAVE_AS);
    try {
      xStorable.storeToURL("private:stream", properties); //$NON-NLS-1$
    }
    catch (Throwable throwable) {
      String message = throwable.getMessage();
      if (throwable instanceof ErrorCodeIOException) {
        message = ErrorCodeTranslator.getErrorCodeMessage(((ErrorCodeIOException) throwable).ErrCode);
        if (message == null)
          message = Messages.getString("PersistenceService.error_io_message", String.valueOf(((ErrorCodeIOException) throwable).ErrCode)); //$NON-NLS-1$
      }
      else if (message == null || message.length() == 0)
        message = ERROR_MESSAGE;
      throw new NOAException(message, throwable);
    }
    if (!filter.isExternalFilter())
      document.fireDocumentEvent(IDocument.EVENT_ON_SAVE_AS_DONE);
  }

  //----------------------------------------------------------------------------
  /**
   * Stored document in the submitted output stream. 
   * 
   * @param outputStream output stream to be used
   * 
   * @throws Throwable if the document can not be stored

   * @author Alessandro Conte
   * @date 07.09.2006
   */
  private void storeInternal(OutputStream outputStream) throws Throwable {
    if (outputStream == null)
      return;

    OutputStreamToXOutputStreamAdapter stream = new OutputStreamToXOutputStreamAdapter(outputStream);

    PropertyValue[] initialPropertyValues = document.getInitialProperties();
    String filterDefinition = null;
    for (int i = 0; i < initialPropertyValues.length; i++) {
      if (initialPropertyValues[i].Name.equalsIgnoreCase("FilterName"))
        filterDefinition = initialPropertyValues[i].Value.toString();
    }
    boolean useFilter = filterDefinition != null && filterDefinition.length() > 0;
    int propCount = 2;
    if (useFilter)
      propCount = 3;

    PropertyValue[] propertyValues = new PropertyValue[propCount];
    propertyValues[0] = new PropertyValue("OutputStream", -1, stream, PropertyState.DIRECT_VALUE); //$NON-NLS-1$
    propertyValues[1] = new PropertyValue();
    propertyValues[1].Name = "Hidden"; //$NON-NLS-1$
    propertyValues[1].Value = new Boolean(true);
    if (useFilter) {
      propertyValues[2] = new PropertyValue();
      propertyValues[2].Name = "FilterName"; //$NON-NLS-1$    
      propertyValues[2].Value = filterDefinition;
    }

    xStorable.storeToURL("private:stream", propertyValues); //$NON-NLS-1$
    document.setModified(false);
  }
  //----------------------------------------------------------------------------

}