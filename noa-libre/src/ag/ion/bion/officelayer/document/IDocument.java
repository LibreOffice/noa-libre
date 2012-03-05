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
 * Last changes made by $Author: markus $, $Date: 2008-08-18 10:29:46 +0200 (Mo, 18 Aug 2008) $
 */
package ag.ion.bion.officelayer.document;

import java.net.URL;

import ag.ion.bion.officelayer.desktop.IFrame;
import ag.ion.bion.officelayer.event.ICloseListener;
import ag.ion.bion.officelayer.event.IDocumentListener;
import ag.ion.bion.officelayer.event.IDocumentModifyListener;
import ag.ion.bion.officelayer.form.IFormService;
import ag.ion.noa.document.IFilterProvider;
import ag.ion.noa.printing.IPrintService;
import ag.ion.noa.script.IScriptingService;
import ag.ion.noa.service.IServiceProvider;
import ag.ion.noa.view.ISelectionProvider;

import com.sun.star.beans.PropertyValue;
import com.sun.star.lang.XComponent;

/**
 * Office document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11636 $
 */
public interface IDocument extends ISelectionProvider {

  /** Identifier of a OpenOffice.org Writer document. */
  public static final String WRITER                        = "Text Document";
  /** Identifier of a OpenOffice.org Calc document. */
  public static final String CALC                          = "Calc Document";
  /** Identifier of a OpenOffice.org Impress document. */
  public static final String IMPRESS                       = "Impress Document";
  /** Identifier of a OpenOffice.org Draw document. */
  public static final String DRAW                          = "Draw Document";
  /** Identifier of a OpenOffice.org Math document. */
  public static final String MATH                          = "Math Document";
  /** Identifier of a OpenOffice.org Web document. */
  public static final String WEB                           = "Web Document";
  /** Identifier of a OpenOffice.org Base document. */
  public static final String BASE                          = "Base Document";
  /** Identifier of a OpenOffice.org Global document. */
  public static final String GLOBAL                        = "Global Document";

  /** Event constant for new document. */
  public final String        EVENT_ON_NEW                  = "OnNew";
  /** Event constant for document about to load. */
  public final String        EVENT_ON_LOAD                 = "OnLoad";
  /** Event constant for document load done. */
  public final String        EVENT_ON_LOAD_DONE            = "OnLoadDone";
  /** Event constant for document load finished. */
  public final String        EVENT_ON_LOAD_FINISHED        = "OnLoadFinished";
  /** Event constant for document save done. */
  public final String        EVENT_ON_SAVE_DONE            = "OnSaveDone";
  /** Event constant for document save finished. */
  public final String        EVENT_ON_SAVE_FINISHED        = "OnSaveFinished";
  /** Event constant for document about to save. */
  public final String        EVENT_ON_SAVE                 = "OnSave";
  /** Event constant for document about to save as. */
  public final String        EVENT_ON_SAVE_AS              = "OnSaveAs";
  /** Event constant for document save as done. */
  public final String        EVENT_ON_SAVE_AS_DONE         = "OnSaveAsDone";
  /** Event constant for document modify changed. */
  public final String        EVENT_ON_MODIFY_CHANGED       = "OnModifyChanged";
  /** Event constant for document mouse over. */
  public final String        EVENT_ON_MOUSE_OVER           = "OnMouseOver";
  /** Event constant for document mouse out. */
  public final String        EVENT_ON_MOUSE_OUT            = "OnMouseOut";
  /** Event constant for document focus. */
  public final String        EVENT_ON_FOCUS                = "OnFocus";
  /** Event constant for document alpha char input. */
  public final String        EVENT_ON_ALPHA_CHAR_INPUT     = "OnAlphaCharInput";
  /** Event constant for document non alpha char input. */
  public final String        EVENT_ON_NON_ALPHA_CHAR_INPUT = "OnNonAlphaCharInput";
  /** Event constant for document insert done. */
  public final String        EVENT_ON_INSERT_DONE          = "OnInsertDone";
  /** Event constant for document insert start. */
  public final String        EVENT_ON_INSERT_START         = "OnInsertStart";
  /** Event constant for document unload. */
  public final String        EVENT_ON_UNLOAD               = "OnUnload";

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XComponent interface. This method is
   * not part of the public API.
   * 
   * @return OpenOffice.org XComponent interface 
   * 
   * @author Andreas Bröker
   */
  public XComponent getXComponent();

  //----------------------------------------------------------------------------
  /**
   * Returns Frame of the document.
   * 
   * @return Frame of the document
   * 
   * @author Markus Krüger
   * @date 01.08.2007
   */
  public IFrame getFrame();

  //----------------------------------------------------------------------------
  /**
   * Returns persistence service.
   * 
   * @return persistence service
   * 
   * @author Andreas Bröker  
   */
  public IPersistenceService getPersistenceService();

  //----------------------------------------------------------------------------
  /**
   * Returns scripting service of the document.
   * 
   * @return scripting service of the document
   * 
   * @author Andreas Bröker
   * @date 13.06.2006
   */
  public IScriptingService getScriptingService();

  //----------------------------------------------------------------------------
  /**
   * Returns filter provider.
   * 
   * @return filter provider
   * 
   * @author Andreas Bröker
   * @date 14.07.2006
   */
  public IFilterProvider getFilterProvider();

  //----------------------------------------------------------------------------
  /**
   * Returns form service, or null if not available.
   * 
   * @return form service, or null
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public IFormService getFormService();

  //----------------------------------------------------------------------------
  /**
   * Adds new document listener.
   * 
   * @param documentListener new document listener
   * 
   * @author Andreas Bröker
   */
  public void addDocumentListener(IDocumentListener documentListener);

  //----------------------------------------------------------------------------
  /**
   * Removes document listener.
   * 
   * @param documentListener document listener
   * 
   * @author Andreas Bröker
   */
  public void removeDocumentListener(IDocumentListener documentListener);

  //----------------------------------------------------------------------------
  /**
   * Returns type of the document.
   * 
   * @return type of the document
   * 
   * @author Andreas Bröker
   */
  public String getDocumentType();

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the document was modified.
   * 
   * @return information whether the document was modified
   * 
   * @author Andreas Bröker
   */
  public boolean isModified();

  //----------------------------------------------------------------------------
  /**
   * Sets modified flag.
   * 
   * @param modified flag value
   * 
   * @throws DocumentException if the status of the document can not be set
   * 
   * @author Andreas Bröker
   */
  public void setModified(boolean modified) throws DocumentException;

  //----------------------------------------------------------------------------  
  /**
   * Adds listener for closing events to the document.
   * 
   * @param closeListener close listener
   * 
   * @author Andreas Bröker
   */
  public void addCloseListener(ICloseListener closeListener);

  //----------------------------------------------------------------------------  
  /**
   * Removes listener for closing events to the document.
   * 
   * @param closeListener close listener
   * 
   * @author Andreas Bröker
   */
  public void removeCloseListener(ICloseListener closeListener);

  //----------------------------------------------------------------------------  
  /**
   * Returns location of the document. Returns null if the URL
   * is not available.
   * 
   * @return location of the document
   * 
   * @throws DocumentException if the URL is not valid
   * 
   * @deprecated Use the IPersistenceService instead.
   * 
   * @author Andreas Bröker
   */
  public URL getLocationURL() throws DocumentException;

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the document is open.
   * 
   * @return information whether the document is open
   * 
   * @author Andreas Bröker
   */
  public boolean isOpen();

  //----------------------------------------------------------------------------
  /**
   * Closes the document.
   * 
   * @author Andreas Bröker
   */
  public void close();

  //----------------------------------------------------------------------------
  /**
   * Checks if the document equals another document.
   * 
   * @param compareDocument document to be compared
   * 
   * @return true if they are equal, flase if they are different
   * 
   * @author Markus Krüger
   */
  public boolean equalsTo(IDocument compareDocument);

  //----------------------------------------------------------------------------
  /**
   * Reformats the document.
   * 
   * @author Markus Krüger
   */
  public void reformat();

  //----------------------------------------------------------------------------
  /**
   * Prints the document.
   * 
   * @throws DocumentException if printing fails
   * 
   * @author Markus Krüger
   */
  public void print() throws DocumentException;

  //----------------------------------------------------------------------------
  /**
   * Returns the print service of the document.
   * 
   * @return the print service of the document
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public IPrintService getPrintService();

  //----------------------------------------------------------------------------
  /**
   * Adds new document modify listener.
   * 
   * @param documentModifyListener new document modify listener
   * 
   * @author Sebastian Rösgen
   */
  public void addDocumentModifyListener(IDocumentModifyListener documentModifyListener);

  //----------------------------------------------------------------------------
  /**
   * Removes the  specified modify listener.
   * 
   * @param documentModifyListener the modify listener to be removed
   * 
   * @author Sebastian Rösgen
   */
  public void removeDocumentModifyListener(IDocumentModifyListener documentModifyListener);

  //----------------------------------------------------------------------------
  /**
   * Fires the document event for the submitted document event constant.
   * 
   * @param documentEventName string constant for document event
   * 
   * @see IDocument.EVENT_ON_NEW
   * @see IDocument.EVENT_ON_LOAD
   * @see IDocument.EVENT_ON_LOAD_DONE
   * @see IDocument.EVENT_ON_LOAD_FINISHED
   * @see IDocument.EVENT_ON_SAVE_DONE
   * @see IDocument.EVENT_ON_SAVE_FINISHED
   * @see IDocument.EVENT_ON_SAVE
   * @see IDocument.EVENT_ON_SAVE_AS
   * @see IDocument.EVENT_ON_SAVE_AS_DONE
   * @see IDocument.EVENT_ON_MODIFY_CHANGED
   * @see IDocument.EVENT_ON_MOUSE_OVER
   * @see IDocument.EVENT_ON_MOUSE_OUT
   * @see IDocument.EVENT_ON_FOCUS
   * @see IDocument.EVENT_ON_ALPHA_CHAR_INPUT
   * @see IDocument.EVENT_ON_NON_ALPHA_CHAR_INPUT
   * @see IDocument.EVENT_ON_INSERT_DONE
   * @see IDocument.EVENT_ON_INSERT_START
   * @see IDocument.EVENT_ON_UNLOAD
   * 
   * @author Alessandro Conte
   * @date 04.09.2006
   */
  public void fireDocumentEvent(String documentEventName);

  //----------------------------------------------------------------------------
  /**
   * Returns the properties the document was loaded with, or an empty array if not available.
   * 
   * @return the properties the document was loaded with, or an empty array
   * 
   * @author Markus Krüger
   * @date 18.08.2008
   */
  public PropertyValue[] getInitialProperties();

  //----------------------------------------------------------------------------
  /**
   * Returns the applications service provider, or null if not available.
   * 
   * @return the applications service provider, or null
   * 
   * @author Markus Krüger
   * @date 25.07.2007
   */
  public IServiceProvider getServiceProvider();

  //----------------------------------------------------------------------------
  /**
   * Updates/refreshes the document.
   * 
   * @author Markus Krüger
   * @date 11.02.2008
   */
  public void update();
  //----------------------------------------------------------------------------

}