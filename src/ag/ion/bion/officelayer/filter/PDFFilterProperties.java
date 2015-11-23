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
 * Last changes made by $Author: markus $, $Date: 2008-03-13 07:36:47 +0100 (Do, 13 Mrz 2008) $
 */
package ag.ion.bion.officelayer.filter;

import java.util.ArrayList;
import java.util.List;

import com.sun.star.beans.PropertyValue;

/**
 * Contains PDF properties for exporting an OpenOffice.org document to PDF.
 * 
 * @author Markus Krüger
 * @version $Revision: 11619 $
 */
public class PDFFilterProperties {

  // Set the password that a user will need to change the permissions
  // of the exported PDF. The password should be in clear text.
  // Must be used with the ueRestrictPermissionsue property
  private String          permissionPassword                    = null;
  // Set the password you must know to open the PDF document
  private String          documentOpenPassword                  = null;
  // Specifies printing of the document:
  //   0: PDF document cannot be printed
  //   1: PDF document can be printed at low resolution only
  //   2: PDF document can be printed at maximum resolution.
  private int             printingMode                          = 2;
  // Specifies the changes allowed to the document:
  //   0: PDF document cannot be changed
  //   1: Inserting, deleting and rotating pages is allowed
  //   2: Filling of form field is allowed
  //   3: Filling of form field and commenting is allowed
  //   4: All the changes of the previous selections are permitted,
  //      with the only exclusion of page extraction
  private int             changesMode                           = 4;
  // Specifies that the pages and the PDF document content can be
  // extracted to be used in other documents: Copy from the PDF
  // document and paste eleswhere
  private boolean         enableCopyingOfContent                = true;
  // Specifies that the PDF document content can be extracted to
  // be used in accessibility applications
  private boolean         enableTextAccessForAccessibilityTools = true;
  // Specifies which pages are exported to the PDF document.
  // To export a range of pages, use the format 3-6.
  // To export single pages, use the format 7;9;11.
  // Specify a combination of page ranges and single pages
  // by using a format like 2-4;6.
  // If the document has less pages than defined in the range,
  // the result might be the exception
  // "com.sun.star.task.ErrorCodeIOException".
  // This exception occured for example by using an ODT file with
  // only one page and a page range of "2-4;6;8-10". Changing the
  // page range to "1" prevented this exception.
  // For no apparent reason the exception didn't occure by using 
  // an ODT file with two pages and a page range of "2-4;6;8-10".
  private String          pageRange                             = null;
  // Specifiesthe quality of the JPG export in a range from 0 to 100.
  // A higher value results in higher quality and file size.
  // The value 100 results in a lossless compression.
  private int             quality                               = 90;
  // If not -1 then all images will be reduced to the given value in DPI.
  // If -1 then no reduction will be used
  private int             maxImageResolution                    = -1;
  // Specifies whether form fields are exported as widgets or
  // only their fixed print representation is exported
  private boolean         exportFormFields                      = true;
  // Specifies that the PDF viewer window is centered to the
  // screen when the PDF document is opened
  private boolean         centerWindow                          = false;
  // Specifies the action to be performed when the PDF document
  // is opened:
  //   0: Opens with default zoom magnification
  //   1: Opens magnified to fit the entire page within the window
  //   2: Opens magnified to fit the entire page width within
  //      the window
  //   3: Opens magnified to fit the entire width of its boundig
  //      box within the window (cuts out margins)
  //   4: Opens with a zoom level given in the ueZoomue property
  private int             magnificationMode                     = 0;
  // Specifies the zoom level a PDF document is opened with.
  // Only valid if the property "Magnification" is set to 4
  private int             zoom                                  = 100;
  // Specifies that automatically inserted empty pages are
  // suppressed. This option only applies for storing Writer
  // documents.
  private boolean         skipEmptyPages                        = false;
  // Specifies the PDF version that should be generated:
  //   0: PDF 1.4 (default selection)
  //   1: PDF/A-1 (ISO 19005-1:2005)
  private int             pdfVersion                            = 0;

  private PropertyValue[] additionalProps                       = new PropertyValue[0];

  //----------------------------------------------------------------------------
  /**
   * Returns the password that a user will need to change the permissions of the 
   * exported PDF, or null if no permission password is set. 
   * The password is in clear text.
   * 
   * @return the password that a user will need to change the permissions of the 
   * exported PDF, or null if no permission password is set
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public String getPermissionPassword() {
    return permissionPassword;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the password that a user will need to change the permissions of the 
   * exported PDF. 
   * The password should be in clear text.
   * 
   * @param permissionPassword the clear test password to be used, or null to not
   * use a permission password
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setPermissionPassword(String permissionPassword) {
    this.permissionPassword = permissionPassword;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the password you must know to open the PDF document, or null if no 
   * document open password is set. 
   * The password is in clear text.
   * 
   * @return the password you must know to open the PDF document, or null if no 
   * document open password is set
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public String getDocumentOpenPassword() {
    return documentOpenPassword;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the password you must know to open the PDF document. 
   * The password should be in clear text.
   * 
   * @param documentOpenPassword the clear test password to be used, or null to not
   * use a document open password
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setDocumentOpenPassword(String documentOpenPassword) {
    this.documentOpenPassword = documentOpenPassword;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the printing mode of the document:
   * 0: PDF document cannot be printed
   * 1: PDF document can be printed at low resolution only
   * 2: PDF document can be printed at maximum resolution
   * 
   * @return the printing mode
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public int getPrintingMode() {
    return printingMode;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the printing mode of the document:
   * 0: PDF document cannot be printed
   * 1: PDF document can be printed at low resolution only
   * 2: PDF document can be printed at maximum resolution
   * 
   * @param printingMode the printing mode to be set
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setPrintingMode(int printingMode) {
    if (printingMode >= 0 && printingMode <= 2)
      this.printingMode = printingMode;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the changes mode allowed to the document:
   * 0: PDF document cannot be changed
   * 1: Inserting, deleting and rotating pages is allowed
   * 2: Filling of form field is allowed
   * 3: Filling of form field and commenting is allowed
   * 4: All the changes of the previous selections are permitted, with the only 
   * exclusion of page extraction
   * 
   * @return the changes mode
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public int getChangesMode() {
    return changesMode;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the changes mode allowed to the document:
   * 0: PDF document cannot be changed
   * 1: Inserting, deleting and rotating pages is allowed
   * 2: Filling of form field is allowed
   * 3: Filling of form field and commenting is allowed
   * 4: All the changes of the previous selections are permitted, with the only 
   * exclusion of page extraction
   * 
   * @param changesMode the changes mode to be set
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setChangesMode(int changesMode) {
    if (changesMode >= 0 && changesMode <= 4)
      this.changesMode = changesMode;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns if the pages and the PDF document content can be
   * extracted to be used in other documents: Copy from the PDF
   * document and paste eleswhere.
   * 
   * @return if the pages and the PDF document content can be
   * extracted to be used in other documents: Copy from the PDF
   * document and paste eleswhere
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public boolean getEnableCopyingOfContent() {
    return enableCopyingOfContent;
  }

  //----------------------------------------------------------------------------
  /**
   * Set if the pages and the PDF document content can be
   * extracted to be used in other documents: Copy from the PDF
   * document and paste eleswhere.
   * 
   * @param enableCopyingOfContent if the pages and the PDF document content can be
   * extracted to be used in other documents
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setEnableCopyingOfContent(boolean enableCopyingOfContent) {
    this.enableCopyingOfContent = enableCopyingOfContent;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns if the PDF document content can be extracted to
   * be used in accessibility applications.
   * 
   * @return if the PDF document content can be extracted to
   * be used in accessibility applications
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public boolean getEnableTextAccessForAccessibilityTools() {
    return enableTextAccessForAccessibilityTools;
  }

  //----------------------------------------------------------------------------
  /**
   * Set if the PDF document content can be extracted to
   * be used in accessibility applications.
   * 
   * @param enableTextAccessForAccessibilityTools if the PDF document content can be extracted to
   * be used in accessibility applications
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setEnableTextAccessForAccessibilityTools(boolean enableTextAccessForAccessibilityTools) {
    this.enableTextAccessForAccessibilityTools = enableTextAccessForAccessibilityTools;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns which pages are exported to the PDF document.
   * To export a range of pages, use the format 3-6.
   * To export single pages, use the format 7;9;11.
   * Specify a combination of page ranges and single pages
   * by using a format like 2-4;6.
   * If the document has less pages than defined in the range,
   * the result might be the exception
   * "com.sun.star.task.ErrorCodeIOException".
   * This exception occured for example by using an ODT file with
   * only one page and a page range of "2-4;6;8-10". Changing the
   * page range to "1" prevented this exception.
   * For no apparent reason the exception didn't occure by using
   * an ODT file with two pages and a page range of "2-4;6;8-10".
   * Can return null if no range is specified.
   * 
   * @return which pages are exported to the PDF document, or null
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public String getPageRange() {
    return pageRange;
  }

  //----------------------------------------------------------------------------
  /**
   * Set which pages are exported to the PDF document.
   * To export a range of pages, use the format 3-6.
   * To export single pages, use the format 7;9;11.
   * Specify a combination of page ranges and single pages
   * by using a format like 2-4;6.
   * If the document has less pages than defined in the range,
   * the result might be the exception
   * "com.sun.star.task.ErrorCodeIOException".
   * This exception occured for example by using an ODT file with
   * only one page and a page range of "2-4;6;8-10". Changing the
   * page range to "1" prevented this exception.
   * For no apparent reason the exception didn't occure by using
   * an ODT file with two pages and a page range of "2-4;6;8-10".
   * Can be set to null to not specify a range and print all pages.
   * 
   * @param enableTextAccessForAccessibilityTools if the PDF document content can be extracted to
   * be used in accessibility applications
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setPageRange(String pageRange) {
    this.pageRange = pageRange;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the quality of the JPG export in a range from 0 to 100.
   * A higher value results in higher quality and file size.
   * The value 100 results in a lossless compression.
   * 
   * @return the quality of the JPG export in a range from 0 to 100
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public int getPictureQuality() {
    return quality;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the quality of the JPG export in a range from 0 to 100.
   * A higher value results in higher quality and file size.
   * The value 100 results in a lossless compression.
   * 
   * @param quality the quality of the JPG export in a range from 0 to 100
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setPictureQuality(int quality) {
    if (quality >= 0 && quality <= 100)
      this.quality = quality;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the DPI to reduce images to.
   * If not -1 then all images will be reduced to the given value in DPI.
   * If -1 then no reduction will be used.
   * Allowed values are -1, 75, 150, 300, 600, 1200
   * 
   * return the DPI to reduce images to, or -1 to not reduce
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public int getMaxImageResolution() {
    return maxImageResolution;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the DPI to reduce images to.
   * If not -1 then all images will be reduced to the given value in DPI.
   * If -1 then no reduction will be used.
   * Allowed values are -1, 75, 150, 300, 600, 1200
   * 
   * @param maxImageResolution the DPI to reduce images to, or -1 to not reduce
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setMaxImageResolution(int maxImageResolution) {
    if (maxImageResolution == -1 || maxImageResolution == 75
        || maxImageResolution == 150
        || maxImageResolution == 300
        || maxImageResolution == 600
        || maxImageResolution == 1200)
      this.maxImageResolution = maxImageResolution;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns whether form fields are exported as widgets or
   * only their fixed print representation is exported.
   * 
   * @return whether form fields are exported as widgets or
   * only their fixed print representation is exported
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public boolean getExportFormFields() {
    return exportFormFields;
  }

  //----------------------------------------------------------------------------
  /**
   * Set whether form fields are exported as widgets or
   * only their fixed print representation is exported.
   * 
   * @param exportFormFields whether form fields are exported as widgets or
   * only their fixed print representation is exported
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setExportFormFields(boolean exportFormFields) {
    this.exportFormFields = exportFormFields;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns if the PDF viewer window is centered to the
   * screen when the PDF document is opened.
   * 
   * @return if the PDF viewer window is centered to the
   * screen when the PDF document is opened
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public boolean getCenterWindow() {
    return centerWindow;
  }

  //----------------------------------------------------------------------------
  /**
   * Set if the PDF viewer window is centered to the
   * screen when the PDF document is opened.
   * 
   * @param centerWindow if the PDF viewer window is centered to the
   * screen when the PDF document is opened
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setCenterWindow(boolean centerWindow) {
    this.centerWindow = centerWindow;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the magnification to be performed when the PDF document
   * is opened:
   *   0: Opens with default zoom magnification
   *   1: Opens magnified to fit the entire page within the window
   *   2: Opens magnified to fit the entire page width within
   *      the window
   *   3: Opens magnified to fit the entire width of its boundig
   *      box within the window (cuts out margins)
   *   4: Opens with a zoom level given in the ueZoomue property.
   * 
   * @return the magnification to be performed when the PDF document
   * is opened
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public int getMagnificationMode() {
    return magnificationMode;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the magnification to be performed when the PDF document
   * is opened:
   *   0: Opens with default zoom magnification
   *   1: Opens magnified to fit the entire page within the window
   *   2: Opens magnified to fit the entire page width within
   *      the window
   *   3: Opens magnified to fit the entire width of its boundig
   *      box within the window (cuts out margins)
   *   4: Opens with a zoom level given in the ueZoomue property.
   * 
   * @param magnificationMode the magnification to be performed when the PDF document
   * is opened
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setMagnificationMode(int magnificationMode) {
    if (magnificationMode >= 0 && magnificationMode <= 4)
      this.magnificationMode = magnificationMode;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the zoom level between 50 and 1600 a PDF document is opened with.
   * Only valid if the magnification mode is set to 4.
   * 
   * @return the zoom level between 50 and 1600 a PDF document is opened with.
   * Only valid if the magnification mode is set to 4
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public int getZoom() {
    return zoom;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the zoom level between 50 and 1600 a PDF document is opened with.
   * Only valid if the magnification mode is set to 4.
   * 
   * @param zoom the zoom level between 50 and 1600 a PDF document is opened with
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setZoom(int zoom) {
    if (zoom >= 50 && zoom <= 1600)
      this.zoom = zoom;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns if automatically inserted empty pages are
   * suppressed. This option only applies for storing Writer
   * documents.
   * 
   * @return if automatically inserted empty pages are
   * suppressed. This option only applies for storing Writer
   * documents
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public boolean getSkipEmptyPages() {
    return skipEmptyPages;
  }

  //----------------------------------------------------------------------------
  /**
   * Set if automatically inserted empty pages are
   * suppressed. This option only applies for storing Writer
   * documents.
   * 
   * @param skipEmptyPages if automatically inserted empty pages are
   * suppressed
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setSkipEmptyPages(boolean skipEmptyPages) {
    this.skipEmptyPages = skipEmptyPages;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the PDF version that should be generated:
   *   0: PDF 1.4 (default selection)
   *   1: PDF/A-1 (ISO 19005-1:2005).
   * 
   * @return the PDF version that should be generated
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public int getPdfVersion() {
    return pdfVersion;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the PDF version that should be generated:
   *   0: PDF 1.4 (default selection)
   *   1: PDF/A-1 (ISO 19005-1:2005).
   * 
   * @param pdfVersion the PDF version that should be generated
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setPdfVersion(int pdfVersion) {
    if (pdfVersion >= 0 && pdfVersion <= 1)
      this.pdfVersion = pdfVersion;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the additional property values.
   * 
   * @return the additional property values
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public PropertyValue[] getAdditionalPropertyValues() {
    return additionalProps;
  }

  //----------------------------------------------------------------------------
  /**
   * Set the additional property values that can be used for properties not covered
   * by this class.
   * 
   * @param additionalProps the additional property values that are not covered
   * by this class
   * 
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public void setAdditionalPropertyValues(PropertyValue[] additionalProps) {
    if (additionalProps != null)
      this.additionalProps = additionalProps;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the property values of this properties.
   * 
   * @return the property values of this properties
   *  
   * @author Markus Krüger
   * @date 01.06.2010
   */
  public PropertyValue[] toPropertyValues() {
    List<PropertyValue> props = new ArrayList<PropertyValue>();

    if (permissionPassword != null) {
      PropertyValue propertyValue1 = new PropertyValue();
      propertyValue1.Name = "PermissionPassword";
      propertyValue1.Value = permissionPassword;
      props.add(propertyValue1);

      PropertyValue propertyValue2 = new PropertyValue();
      propertyValue2.Name = "RestrictPermissions";
      propertyValue2.Value = new Boolean(true);
      props.add(propertyValue2);
    }
    if (documentOpenPassword != null) {
      PropertyValue propertyValue1 = new PropertyValue();
      propertyValue1.Name = "DocumentOpenPassword";
      propertyValue1.Value = documentOpenPassword;
      props.add(propertyValue1);

      PropertyValue propertyValue2 = new PropertyValue();
      propertyValue2.Name = "EncryptFile";
      propertyValue2.Value = new Boolean(true);
      props.add(propertyValue2);
    }

    PropertyValue propertyValue1 = new PropertyValue();
    propertyValue1.Name = "Printing";
    propertyValue1.Value = new Integer(printingMode);
    props.add(propertyValue1);

    PropertyValue propertyValue2 = new PropertyValue();
    propertyValue2.Name = "Changes";
    propertyValue2.Value = new Integer(changesMode);
    props.add(propertyValue2);

    PropertyValue propertyValue3 = new PropertyValue();
    propertyValue3.Name = "EnableCopyingOfContent";
    propertyValue3.Value = new Boolean(enableCopyingOfContent);
    props.add(propertyValue3);

    PropertyValue propertyValue4 = new PropertyValue();
    propertyValue4.Name = "EnableTextAccessForAccessibilityTools";
    propertyValue4.Value = new Boolean(enableTextAccessForAccessibilityTools);
    props.add(propertyValue4);

    if (pageRange != null) {
      PropertyValue propertyValue = new PropertyValue();
      propertyValue.Name = "PageRange";
      propertyValue.Value = pageRange;
      props.add(propertyValue);
    }

    PropertyValue propertyValue5 = new PropertyValue();
    propertyValue5.Name = "UseLosslessCompression";
    propertyValue5.Value = new Boolean(quality == 100);
    props.add(propertyValue5);

    if (quality != 100) {
      PropertyValue propertyValue6 = new PropertyValue();
      propertyValue6.Name = "Quality";
      propertyValue6.Value = new Integer(quality);
      props.add(propertyValue6);
    }

    PropertyValue propertyValue7 = new PropertyValue();
    propertyValue7.Name = "ReduceImageResolution";
    propertyValue7.Value = new Boolean(maxImageResolution != -1);
    props.add(propertyValue7);

    if (maxImageResolution != -1) {
      PropertyValue propertyValue8 = new PropertyValue();
      propertyValue8.Name = "MaxImageResolution";
      propertyValue8.Value = new Integer(maxImageResolution);
      props.add(propertyValue8);
    }

    PropertyValue propertyValue9 = new PropertyValue();
    propertyValue9.Name = "ExportFormFields";
    propertyValue9.Value = new Boolean(exportFormFields);
    props.add(propertyValue9);

    PropertyValue propertyValue10 = new PropertyValue();
    propertyValue10.Name = "CenterWindow";
    propertyValue10.Value = new Boolean(centerWindow);
    props.add(propertyValue10);

    PropertyValue propertyValue11 = new PropertyValue();
    propertyValue11.Name = "Magnification";
    propertyValue11.Value = new Integer(magnificationMode);
    props.add(propertyValue11);

    if (magnificationMode == 4) {
      PropertyValue propertyValue12 = new PropertyValue();
      propertyValue12.Name = "Zoom";
      propertyValue12.Value = new Integer(zoom);
      props.add(propertyValue12);
    }

    PropertyValue propertyValue13 = new PropertyValue();
    propertyValue13.Name = "IsSkipEmptyPages";
    propertyValue13.Value = new Boolean(skipEmptyPages);
    props.add(propertyValue13);

    PropertyValue propertyValue14 = new PropertyValue();
    propertyValue14.Name = "SelectPdfVersion";
    propertyValue14.Value = new Integer(pdfVersion);
    props.add(propertyValue14);

    if (additionalProps != null) {
      for (int i = 0; i < additionalProps.length; i++) {
        props.add(additionalProps[i]);
      }
    }
    return props.toArray(new PropertyValue[props.size()]);
  }
  //----------------------------------------------------------------------------

}
