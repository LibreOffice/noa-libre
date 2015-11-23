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
 * Last changes made by $Author: markus $, $Date: 2010-03-25 14:12:28 +0100 (Do, 25 Mrz 2010) $
 */
package ag.ion.bion.officelayer.document;

import com.sun.star.document.MacroExecMode;

/**
 * Descriptor of a document. A document descriptor can be used
 * in order control the load or store process of a document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11722 $
 */
public class DocumentDescriptor implements IDocumentDescriptor {

  /** A default document descriptor. */
  public static final DocumentDescriptor DEFAULT        = new DocumentDescriptor();

  /** A default document descriptor for hidden documents. */
  public static final DocumentDescriptor DEFAULT_HIDDEN = new DocumentDescriptor(true);

  private boolean                        hidden         = false;
  private boolean                        asTemplate     = false;
  private boolean                        readOnly       = false;
  private boolean                        asPreview      = false;

  private String                         author         = null;
  private String                         comment        = null;
  private String                         title          = null;
  private String                         baseURL        = null;
  private String                         URL            = null;
  private String                         filterDef      = null;

  private short                          macroExecMode  = MacroExecMode.FROM_LIST_AND_SIGNED_WARN;

  //----------------------------------------------------------------------------
  /**
   * Constructs new DocumentDescriptor.
   * 
   * @author Andreas Bröker
   * @date 09.07.2006
   */
  public DocumentDescriptor() {
  }

  //----------------------------------------------------------------------------
  /**
   * Constructs new DocumentDescriptor.
   * 
   * @param hidden information whether the document is hidden
   * 
   * @author Andreas Bröker
   * @date 09.07.2006
   */
  public DocumentDescriptor(boolean hidden) {
    this.hidden = hidden;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets information whether the document is hidden.
   * 
   * @param hidden information whether the document is hidden
   * 
   * @author Andreas Bröker
   */
  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the document is hidden.
   * 
   * @return information whether the document is hidden
   * 
   * @author Andreas Bröker
   */
  public boolean getHidden() {
    return hidden;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets information whether the document is a template.
   * 
   * @param asTemplate information whether the document is a template
   * 
   * @author Andreas Bröker
   */
  public void setAsTemplate(boolean asTemplate) {
    this.asTemplate = asTemplate;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the document is a template. The default value
   * is <code>false</code>
   * 
   * @return information whether the document is a template
   * 
   * @author Andreas Bröker
   */
  public boolean getAsTemplate() {
    return asTemplate;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets information whether the document should be opened read only. The default value
   * is <code>false</code>
   * 
   * @param readOnly information whether the document should be opened read only
   * 
   * @author Andreas Bröker
   * @date 13.06.2006
   */
  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the document should be opened read only. The default value
   * is <code>false</code>
   * 
   * @return information whether the document should be opened read only
   * 
   * @author Andreas Bröker
   * @date 13.06.2006
   */
  public boolean getReadOnly() {
    return readOnly;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets information whether the document should be opened as preview. The default value
   * is <code>false</code>
   * 
   * @param asPreview information whether the document should be opened as preview
   * 
   * @author Andreas Bröker
   * @date 13.06.2006
   */
  public void setAsPreview(boolean asPreview) {
    this.asPreview = asPreview;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns information whether the document should be opened as preview. The default value
   * is <code>false</code>
   * 
   * @return information whether the document should be opened as preview
   * 
   * @author Andreas Bröker
   * @date 13.06.2006
   */
  public boolean getAsPreview() {
    return asPreview;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the author of the document.
   * 
   * @param author the author of the document to be set
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the author of the document.
   * 
   * @return the author of the document
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public String getAuthor() {
    return author;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the comment of the document.
   * 
   * @param comment the comment of the document to be set
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the comment of the document.
   * 
   * @return the comment of the document
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public String getComment() {
    return comment;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the title of the document.
   * 
   * @param title the title of the document to be set
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public void setTitle(String title) {
    this.title = title;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the title of the document.
   * 
   * @return the title of the document
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public String getTitle() {
    return title;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the base URL of the document to be used to resolve relative links.
   * 
   * @param baseURL the base URL of the document to be set
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public void setBaseURL(String baseURL) {
    this.baseURL = baseURL;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the base URL of the document.
   * 
   * @return the base URL of the document
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public String getBaseURL() {
    return baseURL;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the URL of the document. This is the location of the component in 
   * URL syntax. It must be the full qualified URL .
   * 
   * @param URL the URL of the document to be set
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public void setURL(String URL) {
    this.URL = URL;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the URL of the document. This is the location of the component in 
   * URL syntax. It must be the full qualified URL 
   * 
   * @return the URL of the document
   * 
   * @author Markus Krüger
   * @date 24.08.2006
   */
  public String getURL() {
    return URL;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the filter definition of the document. This can be used for forcing the document
   * loading to use a special filter type.
   * 
   * @param filterDefinition the filter definition of the document to be set
   * 
   * @author Markus Krüger
   * @date 13.03.2008
   */
  public void setFilterDefinition(String filterDefinition) {
    this.filterDef = filterDefinition;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the filter definition of the document. This can be used for forcing the document
   * loading to use a special filter type.
   * 
   * @return the filter definition of the document
   * 
   * @author Markus Krüger
   * @date 13.03.2008
   */
  public String getFilterDefinition() {
    return filterDef;
  }

  //----------------------------------------------------------------------------
  /**
   * Sets the macro execution mode.
   * 
   * @param mode the macro execution mode of class com.sun.star.document.MacroExecMode
   * 
   * @author Markus Krüger
   * @date 25.03.2010
   */
  public void setMacroExecutionMode(short mode) {
    this.macroExecMode = mode;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the macro execution mode.
   * 
   * @return the macro execution mode
   * 
   * @author Markus Krüger
   * @date 25.03.2010
   */
  public short getMacroExecutionMode() {
    return macroExecMode;
  }
  //----------------------------------------------------------------------------

}