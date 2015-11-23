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
 * <p>Listener modify events beeing triggered in the documents.
 * The big differences between this class (and the DocumentModify-
 * ListenerWrapper is the way they get triggered (fired).</p> 
 * 
 * <p>The normal document listener just gets fired by an event and 
 * aferwards needs a internally defined action to be reset, so that
 * it can react the next time. This "reset" event is the saving of
 * the document.</p>
 * 
 * <p>So if you type a letter in (in the document), the DocumentListener
 * <b>AND</b> the IDocumentModifyListener will be fired, if you type in the 
 * next letter <b>ONLY</b> the DocumentMOdifyListener will be fired. For each
 * letter following <b>ONLY</b> the DocumentModifyListener willl be triggered, 
 * <b>UNTIL</b> you save the document. Afterwards the wholecycle starts again :
 * the IDocumentListene and the DocumentModifyListener will be triggered
 * both for the first time, then only the DocumentModifyListener until
 * hitting save ... and so forth.
 * </p>
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 */
public interface IDocumentModifyListener extends IEventListener {

  // -------------------------------------------------------------
  /**
   * Should be the kind of method that gets called, whenever
   * the special occurence that triggerede the modify event 
   * is unknown. 
   *
   * @param eventObject the object containing the fired event
   *
   * @author Sebastian Rösgen
   */
  public void reactOnUnspecificEvent(IEvent eventObject);
  // -------------------------------------------------------------
  
}
