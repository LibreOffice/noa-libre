/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                     						*
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2006 by IOn AG                                            *
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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/

/*
 * Last changes made by $Author: markus $, $Date: 2008-04-14 09:49:32 +0200 (Mo, 14 Apr 2008) $
 */
package ag.ion.bion.officelayer.desktop;

/**
 * URLues of global commands.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11632 $
 * @date 14.06.2006
 */
public class GlobalCommands {

  /** Command URL in order to switch the print preview. */
  public static final String PRINT_PREVIEW          = ".uno:PrintPreview";
  /** Command URL in order to set the print preview pages layout. */
  public static final String PRINT_PREVIEW_SHOW_MULTIPLE_PAGES  = ".uno:ShowMultiplePages";
  /** Command URL in order to close the print preview. */
  public static final String CLOSE_PRINT_PREVIEW    = ".uno:ClosePreview";
  /** Command URL in order to save the document as new document. */
  public static final String SAVE                   = ".uno:Save";
  /** Command URL in order to save the document as new document. */
  public static final String SAVE_AS                = ".uno:SaveAs";
  /** Command URL in order to close the application. */
  public static final String QUIT_APPLICATION       = ".uno:Quit";
  /** Command URL in order to close the document. */
  public static final String CLOSE_DOCUMENT         = ".uno:CloseDoc";
  /** Command URL in order to close the window. */
  public static final String CLOSE_WINDOW           = ".uno:CloseWin";
  /** Command URL in order to print the document. */
  public static final String PRINT_DOCUMENT         = ".uno:Print";
  /** Command URL in order to print the document directly. */
  public static final String PRINT_DOCUMENT_DIRECT  = ".uno:PrintDefault";
  /** Command URL in order to diplay the new menu. */
  public static final String NEW_MENU               = ".uno:AddDirect";
  /** Command URL in order to create a new document. */
  public static final String NEW_DOCUMENT           = ".uno:NewDoc";
  /** Command URL in order to open a document. */
  public static final String OPEN_DOCUMENT          = ".uno:Open";
  /** Command URL in order to edit a document. */
  public static final String EDIT_DOCUMENT          = ".uno:EditDoc";
  /** Command URL in order to direktly export a document. */
  public static final String DIREKT_EXPORT_DOCUMENT = ".uno:ExportDirectToPDF";
  /** Command URL in order to mail a document. */
  public static final String MAIL_DOCUMENT          = ".uno:SendMail";
  /** Command URL in order to open the hyperlink dialog. */
  public static final String OPEN_HYPERLINK_DIALOG  = ".uno:HyperlinkDialog";
  /** Command URL in order to edit a hyperlink. */
  public static final String EDIT_HYPERLINK         = ".uno:EditHyperlink";
  /** Command URL in order to open the draw toolbar. */
  public static final String OPEN_DRAW_TOOLBAR      = ".uno:InsertDraw";
  /** Command URL in order to open the navigator. */
  public static final String OPEN_NAVIGATOR         = ".uno:Navigator";
  /** Command URL in order to open the gallery. */
  public static final String OPEN_GALLERY           = ".uno:Gallery";
  /** Command URL in order to open the datasources. */
  public static final String OPEN_DATASOURCES       = ".uno:ViewDataSourceBrowser";
  /** Command URL in order to open the style sheet. */
  public static final String OPEN_STYLE_SHEET       = ".uno:DesignerDialog";
  /** Command URL in order to open the help. */
  public static final String OPEN_HELP              = ".uno:HelpIndex";  
  /** Command URL in order to show recent file list. */
  public static final String SHOW_RECENT_FILE_LIST  = ".uno:RecentFileList";
  /** Command URL in order to open the assists. */
  public static final String OPEN_ASSISTS           = ".uno:AutoPilotMenu";
  /** Command URL in order to open the version dialog. */
  public static final String OPEN_VERSION_DIALOG    = ".uno:VersionDialog";
  /** Command URL in order to open the export to menu. */
  public static final String OPEN_EXPORT_TO         = ".uno:ExportTo";
  /** Command URL in order to export a document to pdf. */
  public static final String EXPORT_TO_PDF          = ".uno:ExportToPDF";
  /** Command URL in order to open the database dialog. */
  public static final String OPEN_DATABASE_DIALOG   = ".uno:ChangeDatabaseField";
  /** Command URL in order to set plugin active. */
  public static final String PLUGINS_ACTIVE         = ".uno:PlugInsActive";
  /** Command URL in order to open the "other fields" dialog. */
  public static final String OPEN_OTHER_FIELDS_DIALOG = ".uno:InsertField";
  /** Command URL in order to open the insert script dialog. */
  public static final String OPEN_INSERT_SCRIPT_DIALOG  = ".uno:InsertScript";
  /** Command URL in order to use the format paintbrush. */
  public static final String FORMAT_PAINTBRUSH       = ".uno:FormatPaintbrush";
  /** Command URL in order to open the font dialog. */
  public static final String OPEN_FONT_DIALOG        = ".uno:FontDialog";
  /** Command URL in order to do a copy. */
  public static final String COPY                    = ".uno:Copy";
  /** Command URL in order to do a cut. */
  public static final String CUT                    = ".uno:Cut";
  /** Command URL in order to do a paste. */
  public static final String PASTE                   = ".uno:Paste";
  /** Command URL in order to insert rows. */
  public static final String INSTER_ROWS             = ".uno:InsertRows";

  //----------------------------------------------------------------------------
  /**
   * Prevents instantiation.
   * 
   * @author Andreas Bröker
   * @date 14.06.2006 
   */
  private GlobalCommands() {
    //Prevents instantiation
  }
  //----------------------------------------------------------------------------

}