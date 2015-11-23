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
 * Copyright 2003-2008 by IOn AG                                            *
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
 * Last changes made by $Author: markus $, $Date: 2007-06-19 15:50:33 +0200 (Di, 19 Jun 2007) $
 */
package ag.ion.bion.officelayer.internal.presentation;

import ag.ion.bion.officelayer.presentation.IPageService;
import ag.ion.bion.officelayer.presentation.IPresentationDocument;
import ag.ion.bion.officelayer.presentation.PresentationException;

import com.sun.star.drawing.XDrawPage;
import com.sun.star.drawing.XDrawPages;
import com.sun.star.drawing.XDrawPagesSupplier;
import com.sun.star.drawing.XDrawView;
import com.sun.star.frame.XController;
import com.sun.star.frame.XModel;
import com.sun.star.presentation.XPresentationPage;
import com.sun.star.uno.UnoRuntime;

/**
 * Page service of a presentation document.
 * 
 * @author Markus Krüger
 * @version $Revision: 11494 $
 */
public class PageService implements IPageService {

  private IPresentationDocument presentationDocument = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new PageService.
   * 
   * @param presentationDocument presentation document to be used
   * 
   * @throws IllegalArgumentException if the submitted presentation document is not valid
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public PageService(IPresentationDocument presentationDocument) throws IllegalArgumentException {
    if(presentationDocument == null)
      throw new IllegalArgumentException("Submitted presentation document is not valid.");
    this.presentationDocument = presentationDocument;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns number of available pages.
   * 
   * @return number of available pages
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public int getPageCount() {
    try {
      XDrawPagesSupplier dSupplier = (XDrawPagesSupplier) UnoRuntime.queryInterface(XDrawPagesSupplier.class, presentationDocument.getXComponent()); 
      XDrawPages pages = dSupplier.getDrawPages(); 
      return pages.getCount();
    }
    catch(Exception exception) {
      return 0;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Navigates to the page with the submitted index. The first page has the index 0.
   * 
   * @param index index of the page
   * 
   * @throws PresentationException if the page is not available
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public void goToPage(int index) throws PresentationException {    
    try {
      XDrawPagesSupplier dSupplier = (XDrawPagesSupplier) UnoRuntime.queryInterface(XDrawPagesSupplier.class, presentationDocument.getXComponent()); 
      XDrawPages pages = dSupplier.getDrawPages(); 

      UnoRuntime.queryInterface(XDrawPage.class, pages.getByIndex(index)); 
      XPresentationPage page = (XPresentationPage) UnoRuntime.queryInterface(XPresentationPage.class, pages.getByIndex(index)); 

      XModel xModel = (XModel) UnoRuntime.queryInterface(XModel.class, presentationDocument.getPresentationSupplier()); 
      XController xController = xModel.getCurrentController(); 
      XDrawView drawView = (XDrawView) UnoRuntime.queryInterface(XDrawView.class, xController); 
      drawView.setCurrentPage(page);
    }
    catch(Throwable throwable) {
      PresentationException textException = new PresentationException(throwable.getMessage());
      textException.initCause(throwable);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------

}