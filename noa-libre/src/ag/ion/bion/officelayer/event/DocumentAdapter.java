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
package ag.ion.bion.officelayer.event;

/**
 * Adapter for document listener.
 * 
 * @author Andreas Bröker 
 * @version $Revision: 10398 $
 */
public class DocumentAdapter extends EventAdapter implements IDocumentListener {
  
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnNew document event occurs.
   * 
   * @param documentEvent source of the event
   * 
   * @author Markus Krüger
   */
  public void onNew(IDocumentEvent documentEvent) {   
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnLoad document event occurs.
   * 
   * @param documentEvent source of the event
   * 
   * @author Markus Krüger
   */
  public void onLoad(IDocumentEvent documentEvent) {   
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnLoadDone document event occurs.
   * 
   * @param documentEvent source of the event
   * 
   * @author Markus Krüger
   */
  public void onLoadDone(IDocumentEvent documentEvent) {   
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnLoadFinished document event occurs.
   * 
   * @param documentEvent source of the event
   * 
   * @author Markus Krüger
   */
  public void onLoadFinished(IDocumentEvent documentEvent) {   
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnSaveDone document event occurs.
   * 
   * @param documentEvent source of the event
   * 
   * @author Markus Krüger
   */
  public void onSaveDone(IDocumentEvent documentEvent) {   
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnSaveFinished document event occurs.
   * 
   * @param documentEvent source of the event
   * 
   * @author Markus Krüger
   */
  public void onSaveFinished(IDocumentEvent documentEvent) {   
  }
  //----------------------------------------------------------------------------
  /**
   * Is called before a document if going to be saved.
   * 
   * @param documentEvent source of the event
   * 
   * @author Andreas Bröker
   */
  public void onSave(IDocumentEvent documentEvent) {    
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnSaveAs document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onSaveAs(IDocumentEvent documentEvent) {   
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnSaveAsDone document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onSaveAsDone(IDocumentEvent documentEvent) {   
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnModifyChanged document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onModifyChanged(IDocumentEvent documentEvent) {    
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnMouseOver document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onMouseOver(IDocumentEvent documentEvent) {
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnMouseOut document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onMouseOut(IDocumentEvent documentEvent) {
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnFocus document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onFocus(IDocumentEvent documentEvent) {
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnAlphaCharInput document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onAlphaCharInput(IDocumentEvent documentEvent) {
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnNonAlphaCharInput document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onNonAlphaCharInput(IDocumentEvent documentEvent) {
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnInsertStart document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onInsertStart(IDocumentEvent documentEvent) {
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnInsertDone document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Andreas Bröker
   */
  public void onInsertDone(IDocumentEvent documentEvent) {
  }
  //----------------------------------------------------------------------------
  /**
   * Is called whenever a OnUnload document event occurs.
   * 
   * @param documentEvent document event
   * 
   * @author Markus Krüger
   */
  public void onUnload(IDocumentEvent documentEvent) {
  }
  //----------------------------------------------------------------------------

}