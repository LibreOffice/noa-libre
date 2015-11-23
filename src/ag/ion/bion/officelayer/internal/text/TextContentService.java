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
 * Last changes made by $Author: markus $, $Date: 2010-07-07 11:05:26 +0200 (Mi, 07 Jul 2010) $
 */
package ag.ion.bion.officelayer.internal.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.ITextContent;
import ag.ion.bion.officelayer.text.ITextContentService;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextDocumentImage;
import ag.ion.bion.officelayer.text.ITextDocumentTextShape;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.noa.graphic.GraphicInfo;
import ag.ion.noa.graphic.TextInfo;

import com.sun.star.beans.XPropertySet;
import com.sun.star.container.NoSuchElementException;
import com.sun.star.container.XEnumeration;
import com.sun.star.container.XEnumerationAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNameContainer;
import com.sun.star.container.XNamed;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.text.SizeType;
import com.sun.star.text.XRelativeTextContentInsert;
import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextGraphicObjectsSupplier;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextRangeCompare;
import com.sun.star.text.XTextTable;
import com.sun.star.uno.Any;
import com.sun.star.uno.AnyConverter;
import com.sun.star.uno.UnoRuntime;

/**
 * Content service implementation of a text document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11744 $
 */
public class TextContentService implements ITextContentService {

  private ITextDocument                       textDocument            = null;

  private XText                               xText                   = null;
  private XMultiServiceFactory                xMultiServiceFactory    = null;
  private XNameContainer                      xBitmapContainer        = null;
  private XNameContainer                      xTextShapeContainer     = null;

  private Map<ITextDocumentImage, String>     imageToImageIds         = null;
  private Map<ITextDocumentTextShape, String> textShapeToTextShapeIds = null;

  // ----------------------------------------------------------------------------
  /**
   * Constructs new TextContentService.
   * 
   * @param textDocument text document to be used
   * @param xText OpenOffice.org XText interface
   * @param xMultiServiceFactory OpenOffice.org XMultiServiceFactory interface
   * 
   * @throws IllegalArgumentException if the submitted text document or OpenOffice.org XText interface 
   * is not valid
   * 
   * @author Andreas Bröker
   * @author Sebastian Rösgen
   * @author Markus Krüger
   */
  public TextContentService(ITextDocument textDocument, XMultiServiceFactory xMultiServiceFactory,
      XText xText) throws IllegalArgumentException {
    if (xText == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XText interface is not valid.");
    if (textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    if (xMultiServiceFactory == null)
      throw new IllegalArgumentException("Submitted multi service factory is not valid.");
    this.xText = xText;
    this.xMultiServiceFactory = xMultiServiceFactory;
    this.textDocument = textDocument;
  }

  // ----------------------------------------------------------------------------
  /**
   * Constructs new paragraph.
   * 
   * @return new paragraph
   * 
   * @throws TextException if the paragraph can not be constructed
   * 
   * @author Andreas Bröker
   */
  public IParagraph constructNewParagraph() throws TextException {
    try {
      if (xMultiServiceFactory == null)
        throw new TextException("OpenOffice.org XMultiServiceFactory inteface not valid.");
      Object object = xMultiServiceFactory.createInstance("com.sun.star.text.Paragraph");
      XTextContent xTextContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class,
          object);

      return new Paragraph(textDocument, xTextContent);
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Constructs new image.
   * 
   * @param graphicInfo the graphic information to construct image with
   * 
   * @return new image
   * 
   * @throws TextException if the image can not be constructed
   * 
   * @author Markus Krüger
   * @date 09.07.2007
   */
  public ITextDocumentImage constructNewImage(GraphicInfo graphicInfo) throws TextException {
    try {
      if (xMultiServiceFactory == null)
        throw new TextException("OpenOffice.org XMultiServiceFactory inteface not valid.");

      if (xBitmapContainer == null)
        xBitmapContainer = (XNameContainer) UnoRuntime.queryInterface(XNameContainer.class,
            xMultiServiceFactory.createInstance("com.sun.star.drawing.BitmapTable"));

      XTextContent xImage = (XTextContent) UnoRuntime.queryInterface(XTextContent.class,
          xMultiServiceFactory.createInstance("com.sun.star.text.TextGraphicObject"));

      XPropertySet xProps = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xImage);

      String tempId = "tempImageId" + System.currentTimeMillis();
      xBitmapContainer.insertByName(tempId, graphicInfo.getUrl());
      String internalURL = AnyConverter.toString(xBitmapContainer.getByName(tempId));

      xProps.setPropertyValue("AnchorType", graphicInfo.getAnchor());
      xProps.setPropertyValue("GraphicURL", internalURL);
      xProps.setPropertyValue("Width", Integer.valueOf(graphicInfo.getWidth()));
      xProps.setPropertyValue("Height", Integer.valueOf(graphicInfo.getHeight()));
      xProps.setPropertyValue("HoriOrient", Short.valueOf(graphicInfo.getHorizontalAlignment()));
      xProps.setPropertyValue("VertOrient", Short.valueOf(graphicInfo.getVerticalAlignment()));

      ITextDocumentImage textDocumentImage = new TextDocumentImage(textDocument,
          xImage,
          graphicInfo);

      if (imageToImageIds == null)
        imageToImageIds = new HashMap<ITextDocumentImage, String>();
      imageToImageIds.put(textDocumentImage, tempId);

      return textDocumentImage;
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * {@inheritDoc}
   */
  public ITextDocumentTextShape constructNewTextShape(TextInfo textInfo) throws TextException {
    try {
      if (xMultiServiceFactory == null)
        throw new TextException("OpenOffice.org XMultiServiceFactory interface not valid.");

      if (xTextShapeContainer == null)
        xTextShapeContainer = (XNameContainer) UnoRuntime.queryInterface(XNameContainer.class,
            xMultiServiceFactory.createInstance("com.sun.star.drawing.MarkerTable"));

      Object textShape = xMultiServiceFactory.createInstance("com.sun.star.text.TextFrame");

      XTextContent xTextShape = (XTextContent) UnoRuntime.queryInterface(XTextContent.class,
          textShape);

      XPropertySet xProps = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTextShape);

      String tempId = "tempTextShapeId" + System.currentTimeMillis();
      xTextShapeContainer.insertByName(tempId, textInfo.getName());

      XNamed xNamed = (XNamed) UnoRuntime.queryInterface(XNamed.class, xTextShape);
      xNamed.setName(textInfo.getName());

      xProps.setPropertyValue("AnchorType", textInfo.getAnchor());

      int minWidth = textInfo.getMinimumWidth();
      int minHeight = textInfo.getMinimumHeight();
      if (minWidth != -1) {
        xProps.setPropertyValue("Width", new Integer(minWidth));
      }
      if (minHeight != -1) {
        xProps.setPropertyValue("Height", new Integer(minHeight));
      }
      if (textInfo.isAutoWidth()) {
        xProps.setPropertyValue("WidthType", new Short((short) 2)); //automatic
      }
      if (textInfo.isAutoHeight()) {
        xProps.setPropertyValue("FrameIsAutomaticHeight", new Boolean(true)); //automatic
        xProps.setPropertyValue("SizeType", SizeType.MIN);
      }
      else {
        xProps.setPropertyValue("SizeType", SizeType.FIX);
      }

      xProps.setPropertyValue("HoriOrient", Short.valueOf(textInfo.getHorizontalAlignment()));
      xProps.setPropertyValue("VertOrient", Short.valueOf(textInfo.getVerticalAlignment()));
      int backColor = textInfo.getBackColor();
      if (backColor != -1) {
        xProps.setPropertyValue("BackColor", new Integer(textInfo.getBackColor()));
        xProps.setPropertyValue("BackColorTransparency", new Short((short) 0));
        xProps.setPropertyValue("BackTransparent", new Boolean(false));
      }
      else {
        xProps.setPropertyValue("BackColorTransparency", new Short((short) 100));
        xProps.setPropertyValue("BackTransparent", new Boolean(true));
      }

      ITextDocumentTextShape textDocumentTextShape = new TextDocumentTextShape(textDocument,
          xTextShape,
          textInfo);

      ////////
      XText xShapeText = (XText) UnoRuntime.queryInterface(XText.class, textShape);
      textDocumentTextShape.setXText(xShapeText);
      ////////

      if (textShapeToTextShapeIds == null)
        textShapeToTextShapeIds = new HashMap<ITextDocumentTextShape, String>();
      textShapeToTextShapeIds.put(textDocumentTextShape, tempId);

      return textDocumentTextShape;
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * {@inheritDoc}
   */
  public ITextRange constructNewTextRange(IDocument doc, XTextRange xTextRange)
      throws TextException {
    try {
      TextRange range = new TextRange(doc, xTextRange);
      return range;
    }
    catch (Exception e) {
      TextException textException = new TextException(e.getMessage());
      textException.initCause(e);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Inserts content.
   * 
   * @param textContent text content to be inserted
   * 
   * @throws TextException if the text content can not be inserted
   * 
   * @author Andreas Bröker
   */
  public void insertTextContent(ITextContent textContent) throws TextException {
    try {
      xText.insertTextContent(xText.getStart(), textContent.getXTextContent(), true);
      cleanupImage(textContent);
      cleanupTextShape(textContent);

    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Removes content.
   * 
   * @param textContent text content to be removed
   * 
   * @throws TextException if the text content can not be removed
   * 
   * @author Miriam Sutter
   */
  public void removeTextContent(ITextContent textContent) throws TextException {
    try {
      xText.removeTextContent(textContent.getXTextContent());
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Inserts content at submitted position.
   * 
   * @param textRange position to be used
   * @param textContent text content to be inserted
   * 
   * @throws TextException if the text content can not be inserted
   * 
   * @author Andreas Bröker
   */
  public void insertTextContent(ITextRange textRange, ITextContent textContent)
      throws TextException {
    try {
      if (textContent instanceof IParagraph) {
        IParagraph paragraph = (IParagraph) textContent;
        IParagraph[] oldParagraphsBeforeInsert = getRealParagraphs();
        Object object = xMultiServiceFactory.createInstance("com.sun.star.text.TextTable");
        XTextTable xTable = (XTextTable) UnoRuntime.queryInterface(XTextTable.class, object);
        xTable.initialize(1, 1);
        xText.insertTextContent(textRange.getXTextRange(), xTable, false);
        XRelativeTextContentInsert xRelativeTextContentInsert = (XRelativeTextContentInsert) UnoRuntime.queryInterface(XRelativeTextContentInsert.class,
            xText);
        xRelativeTextContentInsert.insertTextContentAfter(paragraph.getXTextContent(), xTable);
        xText.removeTextContent(xTable);
        paragraph.setXTextContent(getNewParagraphTextContent(oldParagraphsBeforeInsert));
      }
      else {
        XText xText = textRange.getXTextRange().getText();
        XTextRange targetRange = textRange.getXTextRange();
        XTextContent xTextContent = textContent.getXTextContent();
        xText.insertTextContent(targetRange, xTextContent, true);
        cleanupImage(textContent);
        cleanupTextShape(textContent);
      }
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Inserts new text content before other text content.
   * 
   * @param newTextContent text content to be inserted
   * @param textContent available text content
   * 
   * @throws TextException if the text content can not be inserted
   * 
   * @author Andreas Bröker
   */
  public void insertTextContentBefore(ITextContent newTextContent, ITextContent textContent)
      throws TextException {
    try {
      XRelativeTextContentInsert xRelativeTextContentInsert = (XRelativeTextContentInsert) UnoRuntime.queryInterface(XRelativeTextContentInsert.class,
          xText);
      IParagraph[] oldParagraphsBeforeInsert = getRealParagraphs();
      if (newTextContent instanceof ITextTable) {
        IParagraph paragraph = constructNewParagraph();
        xRelativeTextContentInsert.insertTextContentBefore(paragraph.getXTextContent(),
            textContent.getXTextContent());
        paragraph.setXTextContent(getNewParagraphTextContent(oldParagraphsBeforeInsert));

        xText.insertTextContent(paragraph.getXTextContent().getAnchor(),
            newTextContent.getXTextContent(),
            false);
        xText.removeTextContent(paragraph.getXTextContent());
      }
      else {
        xRelativeTextContentInsert.insertTextContentBefore(newTextContent.getXTextContent(),
            textContent.getXTextContent());

        if (newTextContent instanceof IParagraph) {
          IParagraph paragraph = (IParagraph) newTextContent;
          paragraph.setXTextContent(getNewParagraphTextContent(oldParagraphsBeforeInsert));
        }

        cleanupImage(textContent);
        cleanupTextShape(textContent);
      }
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Inserts new text content after other text content.
   * 
   * @param newTextContent text content to be inserted
   * @param textContent available text content
   * 
   * @throws TextException if the text content can not be inserted
   * 
   * @author Andreas Bröker
   */
  public void insertTextContentAfter(ITextContent newTextContent, ITextContent textContent)
      throws TextException {
    try {
      XRelativeTextContentInsert xRelativeTextContentInsert = (XRelativeTextContentInsert) UnoRuntime.queryInterface(XRelativeTextContentInsert.class,
          xText);
      IParagraph[] oldParagraphsBeforeInsert = getRealParagraphs();
      if (newTextContent instanceof ITextTable) {
        IParagraph paragraph = constructNewParagraph();
        XTextContent oldXTextContent = textContent.getXTextContent();
        xRelativeTextContentInsert.insertTextContentAfter(paragraph.getXTextContent(),
            oldXTextContent);

        paragraph.setXTextContent(getNewParagraphTextContent(oldParagraphsBeforeInsert));

        xText.insertTextContent(paragraph.getXTextContent().getAnchor(),
            newTextContent.getXTextContent(),
            false);
        xText.removeTextContent(paragraph.getXTextContent());
      }
      else if (newTextContent instanceof IParagraph) {
        if (textContent instanceof ITextTable) {
          XTextContent newXTextContent = newTextContent.getXTextContent();
          XTextContent oldXTextContent = textContent.getXTextContent();
          xRelativeTextContentInsert.insertTextContentAfter(newXTextContent, oldXTextContent);
        }
        else {
          Object object = xMultiServiceFactory.createInstance("com.sun.star.text.TextTable");
          XTextTable xTable = (XTextTable) UnoRuntime.queryInterface(XTextTable.class, object);
          xTable.initialize(1, 1);
          xText.insertTextContent(textContent.getXTextContent().getAnchor(), xTable, false);
          xRelativeTextContentInsert.insertTextContentAfter(newTextContent.getXTextContent(),
              xTable);
          xText.removeTextContent(xTable);
        }
        IParagraph paragraph = (IParagraph) newTextContent;
        paragraph.setXTextContent(getNewParagraphTextContent(oldParagraphsBeforeInsert));
      }
      else {
        XTextContent newContent = newTextContent.getXTextContent();
        XTextContent successor = textContent.getXTextContent();
        xRelativeTextContentInsert.insertTextContentAfter(newContent, successor);
        // xRelativeTextContentInsert.insertTextContentAfter(textContent.getXTextContent(),
        // newTextContent.getXTextContent());
        cleanupImage(textContent);
        cleanupTextShape(textContent);
      }
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Convert linked text images to embedded images.
   * 
   * @throws TextException if conversion fails
   * 
   * @author Markus Krüger
   * @date 07.09.2009
   */
  public void convertLinkedImagesToEmbeded() throws TextException {
    try {
      XTextGraphicObjectsSupplier graphicObjSupplier = (XTextGraphicObjectsSupplier) UnoRuntime.queryInterface(XTextGraphicObjectsSupplier.class,
          textDocument.getXTextDocument());
      XNameAccess nameAccess = graphicObjSupplier.getGraphicObjects();
      String[] names = nameAccess.getElementNames();
      for (int i = 0; i < names.length; i++) {
        Any xImageAny = (Any) nameAccess.getByName(names[i]);
        Object xImageObject = xImageAny.getObject();
        XTextContent xImage = (XTextContent) xImageObject;
        XServiceInfo xInfo = (XServiceInfo) UnoRuntime.queryInterface(XServiceInfo.class, xImage);
        if (xInfo.supportsService("com.sun.star.text.TextGraphicObject")) {
          XPropertySet xPropSet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,
              xImage);
          String name = xPropSet.getPropertyValue("LinkDisplayName").toString();
          String graphicURL = xPropSet.getPropertyValue("GraphicURL").toString();
          //only ones that are not embedded
          if (graphicURL.indexOf("vnd.sun.") == -1) {
            XMultiServiceFactory multiServiceFactory = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class,
                textDocument.getXTextDocument());
            XNameContainer xBitmapContainer = (XNameContainer) UnoRuntime.queryInterface(XNameContainer.class,
                multiServiceFactory.createInstance("com.sun.star.drawing.BitmapTable"));
            if (!xBitmapContainer.hasByName(name)) {
              xBitmapContainer.insertByName(name, graphicURL);
              String newGraphicURL = xBitmapContainer.getByName(name).toString();
              xPropSet.setPropertyValue("GraphicURL", newGraphicURL);
            }
          }
        }
      }
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Some cleanup for text image.
   * 
   * @param textContent the text image as text content
   * 
   * @throws WrappedTargetException if cleanup fails
   * @throws NoSuchElementException if cleanup fails
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  private void cleanupImage(ITextContent textContent) throws NoSuchElementException,
      WrappedTargetException {
    if (textContent instanceof ITextDocumentImage && imageToImageIds != null
        && xBitmapContainer != null) {
      String id = imageToImageIds.get(textContent);
      if (id != null) {
        imageToImageIds.remove(textContent);
        xBitmapContainer.removeByName(id);
      }
      ((ITextDocumentImage) textContent).getGraphicInfo().cleanUp();
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Some cleanup for text shape.
   * 
   * @param textContent the text shape as text content
   * 
   * @throws WrappedTargetException if cleanup fails
   * @throws NoSuchElementException if cleanup fails
   * 
   * @author Jan Reimann
   * @date 20.08.2009
   */
  private void cleanupTextShape(ITextContent textContent) throws NoSuchElementException,
      WrappedTargetException {
    if (textContent instanceof ITextDocumentTextShape && textShapeToTextShapeIds != null
        && xTextShapeContainer != null) {
      String id = textShapeToTextShapeIds.get(textContent);
      if (id != null) {
        textShapeToTextShapeIds.remove(textContent);
        xTextShapeContainer.removeByName(id);
      }
    }
  }

  // ----------------------------------------------------------------------------
  /**
   * Returns the XTextContent interface of the paragraph just added,
   * or null if not found.
   * 
   * @param oldParagraphsBeforeInsert the old paragraphs array before the new paragraph is added
   * 
   * @return  the XTextContent interface of the paragraph just added,
   * or null if not found
   * 
   * @throws TextException if something fails
   * 
   * @author Markus Krüger
   * @date 21.06.2010
   */
  private XTextContent getNewParagraphTextContent(IParagraph[] oldParagraphsBeforeInsert)
      throws TextException {
    try {
      IParagraph[] newParagraphs = getRealParagraphs();
      for (int i = 0; i < newParagraphs.length; i++) {
        IParagraph newParagraph = newParagraphs[i];
        IParagraph oldParagraph = null;

        if (i < oldParagraphsBeforeInsert.length) {
          oldParagraph = oldParagraphsBeforeInsert[i];
        }
        else {
          return newParagraphs[newParagraphs.length - 1].getXTextContent();
        }

        XText text = newParagraph.getXTextContent().getAnchor().getText();
        text = textDocument.getTextService().getText().getXText();
        XTextRangeCompare comparator = (XTextRangeCompare) UnoRuntime.queryInterface(XTextRangeCompare.class,
            text);

        if (comparator.compareRegionStarts(newParagraph.getXTextContent().getAnchor().getStart(),
            oldParagraph.getXTextContent().getAnchor().getStart()) != 0) {
          return newParagraph.getXTextContent();
        }
      }
      return null;
    }
    catch (Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }

  // ----------------------------------------------------------------------------
  private IParagraph[] getRealParagraphs() throws Exception {
    XEnumerationAccess xEnumerationAccess = (XEnumerationAccess) UnoRuntime.queryInterface(XEnumerationAccess.class,
        textDocument.getTextService().getText().getXText());
    XEnumeration xParagraphEnumeration = xEnumerationAccess.createEnumeration();
    List<IParagraph> realParagraphs = new ArrayList<IParagraph>();
    while (xParagraphEnumeration.hasMoreElements()) {
      XTextContent xTextContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class,
          xParagraphEnumeration.nextElement());
      XServiceInfo xServiceInfo = (XServiceInfo) UnoRuntime.queryInterface(XServiceInfo.class,
          xTextContent);
      if (xServiceInfo.supportsService("com.sun.star.text.Paragraph")) {
        realParagraphs.add(new Paragraph(textDocument, xTextContent));
      }
    }
    return realParagraphs.toArray(new IParagraph[realParagraphs.size()]);
  }

}