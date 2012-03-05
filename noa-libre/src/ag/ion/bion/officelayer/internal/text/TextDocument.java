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
 * Last changes made by $Author: markus $, $Date: 2008-08-18 10:30:49 +0200 (Mo, 18 Aug 2008) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.document.AbstractDocument;
import ag.ion.bion.officelayer.document.DocumentException;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.internal.util.NumberFormatService;
import ag.ion.bion.officelayer.text.IPageService;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextFieldService;
import ag.ion.bion.officelayer.text.ITextService;
import ag.ion.bion.officelayer.text.ITextTableService;
import ag.ion.bion.officelayer.text.IViewCursorService;
import ag.ion.bion.officelayer.util.INumberFormatService;
import ag.ion.noa.document.ISearchService;
import ag.ion.noa.internal.document.SearchService;
import ag.ion.noa.internal.text.DocumentIndexService;
import ag.ion.noa.text.IDocumentIndexService;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.frame.XController;
import com.sun.star.frame.XModel;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XNumberFormatsSupplier;
import com.sun.star.util.XRefreshable;
import com.sun.star.util.XSearchable;
import com.sun.star.view.DocumentZoomType;
import com.sun.star.view.XSelectionSupplier;
import com.sun.star.view.XViewSettingsSupplier;

/**
 * OpenOffice.org text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11637 $
 */
public class TextDocument extends AbstractDocument implements ITextDocument {

  private XTextDocument        xTextDocument        = null;
  private XMultiServiceFactory xMultiServiceFactory = null;

  private TextFieldService     textFieldService     = null;
  private TextService          textService          = null;
  private TextTableService     textTableService     = null;
  private ViewCursorService    viewCursorService    = null;
  private PageService          pageService          = null;
  private NumberFormatService  numberFormatService  = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new OpenOffice.org swriter document.
   * 
   * @param xTextDocument OpenOffice.org XTextDocument interface
   * @param intitialProperties the properties that were used loading the document
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org XTextDocument is not valid
   * 
   * @author Andreas Bröker
   */
  public TextDocument(XTextDocument xTextDocument, PropertyValue[] initialProperties)
      throws IllegalArgumentException {
    super((XComponent) UnoRuntime.queryInterface(XComponent.class, xTextDocument),
        initialProperties);
    this.xTextDocument = xTextDocument;
    this.xMultiServiceFactory = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class,
        xTextDocument);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextDocument interface. This method
   * is not intended to be called by clients.
   * 
   * @return OpenOffice.org XTextDocument interface
   * 
   * @author Andreas Bröker 
   */
  public XTextDocument getXTextDocument() {
    return xTextDocument;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns type of the document.
   * 
   * @return type of the document
   * 
   * @author Andreas Bröker
   */
  public String getDocumentType() {
    return IDocument.WRITER;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the XMultiServiceFactory of the document.
   * 
   * @return multiServiceFactory the XMultiServiceFactory of the document
   * 
   * @throws Exception if any error occurs
   */
  public XMultiServiceFactory getMultiServiceFactory() throws Exception {
    XMultiServiceFactory multiServiceFactory = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class,
        xTextDocument);
    return multiServiceFactory;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns page service of the document.
   * 
   * @return page service of the document
   * 
   * @author Andreas Bröker
   */
  public IPageService getPageService() {
    if (pageService == null)
      pageService = new PageService(this);
    return pageService;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns service for text fields.
   * 
   * @return service for text fields
   * 
   * @author Andreas Bröker
   */
  public ITextFieldService getTextFieldService() {
    if (textFieldService == null)
      textFieldService = new TextFieldService(this);
    return textFieldService;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns text service.
   * 
   * @return text service
   * 
   * @author Andreas Bröker
   */
  public ITextService getTextService() {
    if (textService == null)
      textService = new TextService(this, xMultiServiceFactory, xTextDocument.getText());
    return textService;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns view cursor service.
   * 
   * @return view cursor service
   * 
   * @author Andreas Bröker
   */
  public IViewCursorService getViewCursorService() {
    if (viewCursorService == null)
      viewCursorService = new ViewCursorService(this);
    return viewCursorService;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns text table service.
   * 
   * @return text table service
   * 
   * @author Andreas Bröker
   */
  public ITextTableService getTextTableService() {
    if (textTableService == null)
      textTableService = new TextTableService(this);
    return textTableService;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns number format service.
   * 
   * @return number format service
   * 
   * @author Andreas Bröker
   */
  public INumberFormatService getNumberFormatService() {
    if (numberFormatService == null) {
      XNumberFormatsSupplier xNumberFormatsSupplier = (XNumberFormatsSupplier) UnoRuntime.queryInterface(XNumberFormatsSupplier.class,
          xTextDocument);
      numberFormatService = new NumberFormatService(this, xNumberFormatsSupplier);

      //TODO workaround, otherwise the numberformat may not be recognized corretly
      try {
        Thread.sleep(500);
      }
      catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return numberFormatService;
  }

  //----------------------------------------------------------------------------
  /**
   * Reformats the document.
   * 
   * @author Markus Krüger
   */
  public void reformat() {
    xTextDocument.reformat();
  }

  //----------------------------------------------------------------------------
  /**
   * Updates/refreshes the document.
   * 
   * @author Markus Krüger
   * @date 11.02.2008
   */
  public void update() {
    ((XRefreshable) UnoRuntime.queryInterface(XRefreshable.class, xTextDocument)).refresh();
  }

  //----------------------------------------------------------------------------
  /**
   * Returns search service of the searchable document.
   * 
   * @return search service of the searchable document
   * 
   * @author Andreas Bröker
   * @date 09.07.2006
   */
  public ISearchService getSearchService() {
    XSearchable searchable = (XSearchable) UnoRuntime.queryInterface(XSearchable.class, xComponent);
    return new SearchService(this, searchable);
  }

  //----------------------------------------------------------------------------
  /**
   * Returns index service of the document.
   * 
   * @return index service of the document
   * 
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public IDocumentIndexService getIndexService() {
    return new DocumentIndexService(xTextDocument);
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the zoom of the document.
   * 
   * @param zoomType the type of the zoom as in class {@link DocumentZoomType}
   * @param zoomValue the value of the zoom, does only take afect if zoom type is
   * set to DocumentZoomType.BY_VALUE. Values between 20 and 600 are allowed.
   * 
   * @throws DocumentException if zoom fails
   * 
   * @author Markus Krüger
   * @date 06.07.2007
   */
  public void zoom(short zoomType, short zoomValue) throws DocumentException {
    try {
      //zoomType valid?
      if (zoomType != DocumentZoomType.BY_VALUE && zoomType != DocumentZoomType.ENTIRE_PAGE
          && zoomType != DocumentZoomType.OPTIMAL
          && zoomType != DocumentZoomType.PAGE_WIDTH
          && zoomType != DocumentZoomType.PAGE_WIDTH_EXACT)
        throw new DocumentException("Invalid zoom type.");
      //zoomType valid?
      if (zoomType == DocumentZoomType.BY_VALUE && (zoomValue < 20 || zoomValue > 600))
        throw new DocumentException("Invalid zoom value. Use values between 20 and 600.");

      XModel xModel = (XModel) UnoRuntime.queryInterface(XModel.class, getXComponent());
      if (xModel != null) {
        XController xController = xModel.getCurrentController();
        XSelectionSupplier selectionSupplier = (XSelectionSupplier) UnoRuntime.queryInterface(XSelectionSupplier.class,
            xController);
        if (selectionSupplier != null) {
          XViewSettingsSupplier viewSettingsSupplier = (XViewSettingsSupplier) UnoRuntime.queryInterface(XViewSettingsSupplier.class,
              xController);
          if (viewSettingsSupplier != null) {
            XPropertySet propertySet = viewSettingsSupplier.getViewSettings();
            propertySet.setPropertyValue("ZoomType", new Short(zoomType));
            if (zoomType == DocumentZoomType.BY_VALUE)
              propertySet.setPropertyValue("ZoomValue", new Short(zoomValue));
          }
        }
      }
    }
    catch (Throwable throwable) {
      throw new DocumentException(throwable);
    }
  }
  //----------------------------------------------------------------------------
}