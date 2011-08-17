/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                                 *
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2007 by IOn AG                                            *
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
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/

/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.text.ITextContentService;
import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextDocumentImage;
import ag.ion.noa.graphic.GraphicInfo;

import com.sun.star.text.HoriOrientation;
import com.sun.star.text.TextContentAnchorType;
import com.sun.star.text.VertOrientation;

import java.io.FileInputStream;
import java.util.HashMap;

/** 
 * This code snippet creates an image inside a text document. 
 *  
 * @author Markus Krüger
 * @version $Revision: 10398 $ 
 */
public class Snippet17 {

  /* 
   * The path to the office application, in this case on a(n OpenSUSE)Linux system. On a Windows system this 
   * would look like: => private final static String officeHome = "C:\\Programme\\OpenOffice.org 2.0"; 
   */
  private final static String OPEN_OFFICE_ORG_PATH = "C:\\Programme\\OpenOffice.org 2.2";

  public static void main(String[] args) {
    try {
      HashMap configuration = new HashMap();
      configuration.put(IOfficeApplication.APPLICATION_HOME_KEY,
          OPEN_OFFICE_ORG_PATH);
      configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY,
          IOfficeApplication.LOCAL_APPLICATION);
      final IOfficeApplication officeAplication = OfficeApplicationRuntime
          .getApplication(configuration);
      officeAplication.setConfiguration(configuration);
      officeAplication.activate();
      IDocumentService documentService = officeAplication.getDocumentService();
      IDocument document = documentService.constructNewDocument(
          IDocument.WRITER, new DocumentDescriptor());

      ITextDocument textDocument = (ITextDocument) document;

      boolean useStream = true;
      GraphicInfo graphicInfo = null;

      String imagePath = "d:\\my_image.gif";
      int pixelWidth = 265;
      int pixelHeight = 256;

      if(!useStream) {
        //with url
        graphicInfo = new GraphicInfo(imagePath, pixelWidth, true, pixelHeight, true,
            VertOrientation.TOP, HoriOrientation.LEFT,
            TextContentAnchorType.AT_PARAGRAPH);
      }
      else {
        //with stream
        graphicInfo = new GraphicInfo(new FileInputStream(imagePath), pixelWidth,
            true, pixelHeight, true, VertOrientation.TOP, HoriOrientation.LEFT,
            TextContentAnchorType.AT_PARAGRAPH);
      }

      ITextContentService textContentService = textDocument.getTextService()
          .getTextContentService();

      ITextCursor textCursor = textDocument.getTextService().getText()
          .getTextCursorService().getTextCursor();

      ITextDocumentImage textDocumentImage = textContentService
          .constructNewImage(graphicInfo);
      textContentService.insertTextContent(textCursor.getEnd(),
          textDocumentImage);

      officeAplication.deactivate();
    }
    catch(OfficeApplicationException exception) {
      exception.printStackTrace();
    }
    catch(Throwable exception) {
      exception.printStackTrace();
    }
  }

}
