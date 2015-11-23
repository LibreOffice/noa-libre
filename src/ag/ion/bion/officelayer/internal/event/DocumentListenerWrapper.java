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
package ag.ion.bion.officelayer.internal.event;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.event.IDocumentListener;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.document.XEventListener;
import com.sun.star.document.EventObject;

/**
 * Wrapper in order to register a document listener in OpenOffice.org.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @author Alessandro Conte
 * @version $Revision: 10398 $
 */
public class DocumentListenerWrapper extends EventListenerWrapper implements XEventListener {

  private IDocumentListener documentListener = null;
	
  //----------------------------------------------------------------------------
  /**
   * Constructs new DocumentListenerWrapper.
   * 
   * @param documentListener document listener to be wrapped
   * @param serviceProvider the service provider to be used
   * 
   * @throws IllegalArgumentException if the submitted document listener is not valid
   * 
   * @author Andreas Bröker
   */
  public DocumentListenerWrapper(IDocumentListener documentListener,
      IServiceProvider serviceProvider) throws IllegalArgumentException {
    super(documentListener, serviceProvider);
    this.documentListener = documentListener;
  }

  //----------------------------------------------------------------------------
	/**
	 * Is called whenever a document event occurs. 
	 * 
	 * @param eventObject source of the event
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   * @author Alessandro Conte
   * @date 04.09.2006
   */
  public final void notifyEvent(EventObject eventObject) {
    if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_NEW)) {
      documentListener.onNew(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_LOAD)) {
      documentListener.onLoad(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_LOAD_DONE)) {
      documentListener.onLoadDone(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_LOAD_FINISHED)) {
      documentListener.onLoadFinished(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_SAVE_DONE)) {
      documentListener.onSaveDone(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_SAVE_FINISHED)) {
      documentListener.onSaveFinished(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_SAVE)) {
      documentListener.onSave(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_SAVE_AS)) {
      documentListener.onSaveAs(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_SAVE_AS_DONE)) {
      documentListener.onSaveAsDone(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_MODIFY_CHANGED)) {
      documentListener.onModifyChanged(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_MOUSE_OVER)) {
      documentListener.onMouseOver(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_MOUSE_OUT)) {
      documentListener.onMouseOut(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_FOCUS)) {
      documentListener.onFocus(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_ALPHA_CHAR_INPUT)) {
      documentListener.onAlphaCharInput(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_NON_ALPHA_CHAR_INPUT)) {
      documentListener.onNonAlphaCharInput(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_INSERT_START)) {
      documentListener.onInsertStart(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_INSERT_DONE)) {
      documentListener.onInsertDone(new DocumentEvent(eventObject, getServiceProvider()));
    }
    else if (eventObject.EventName.equalsIgnoreCase(IDocument.EVENT_ON_UNLOAD)) {
      documentListener.onUnload(new DocumentEvent(eventObject, getServiceProvider()));
    }
  }
  //----------------------------------------------------------------------------

}