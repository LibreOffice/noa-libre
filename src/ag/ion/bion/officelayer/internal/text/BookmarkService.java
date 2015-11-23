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
 * Last changes made by $Author: markus $, $Date: 2008-10-06 18:31:10 +0200 (Mo, 06 Okt 2008) $
 */
package ag.ion.bion.officelayer.internal.text;

import java.util.ArrayList;
import java.util.List;

import ag.ion.bion.officelayer.text.IBookmark;
import ag.ion.bion.officelayer.text.IBookmarkService;
import ag.ion.bion.officelayer.text.ITextDocument;

import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNamed;
import com.sun.star.text.XBookmarksSupplier;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;

/**
 * Bookmark service of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 11664 $
 */
public class BookmarkService implements IBookmarkService {

  private ITextDocument textDocument = null;

  //----------------------------------------------------------------------------
  /**
   * Constructs new CursorService.
   * 
   * @param textDocument the text document of the service
   * 
   * @throws IllegalArgumentException if the text document is not valid
   * 
   * @author Markus Krüger
   */
  public BookmarkService(ITextDocument textDocument) throws IllegalArgumentException {
    if (textDocument == null)
      throw new IllegalArgumentException("Submitted text document is not valid.");
    this.textDocument = textDocument;
  }

  //----------------------------------------------------------------------------
  /**
   * Returns all bookmarks.
   * 
   * @return all bookmarks
   * 
   * @author Markus Krüger
   */
  public IBookmark[] getBookmarks() {
    try {
      XBookmarksSupplier xBookmarksSupplier = (XBookmarksSupplier) UnoRuntime.queryInterface(XBookmarksSupplier.class,
          textDocument.getXTextDocument());
      if (xBookmarksSupplier == null)
        return new IBookmark[0];
      XNameAccess nameAccess = xBookmarksSupplier.getBookmarks();
      String[] names = nameAccess.getElementNames();
      if (names.length < 1)
        return new IBookmark[0];
      List bookmarks = new ArrayList();
      for (int i = 0; i < names.length; i++) {
        Object bookmark = nameAccess.getByName(names[i]);
        XTextContent xBookmarkContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class,
            bookmark);
        XNamed xNamed = (XNamed) UnoRuntime.queryInterface(XNamed.class, bookmark);
        if (xBookmarkContent == null)
          continue;
        XTextRange xBookmarkRange = xBookmarkContent.getAnchor();
        if (xBookmarkRange == null)
          continue;
        bookmarks.add(new Bookmark(textDocument, xBookmarkRange, xNamed));
      }
      return (IBookmark[]) bookmarks.toArray(new IBookmark[bookmarks.size()]);
    }
    catch (Exception exception) {
      return new IBookmark[0];
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the bookmark for the specified name, or null if none was found with this name.
   * 
   * @param name the bookmark name to be used
   * 
   * @return the bookmark for the specified name, or null
   * 
   * @author Markus Krüger
   */
  public IBookmark getBookmark(String name) {
    try {
      if (name == null)
        return null;
      XBookmarksSupplier xBookmarksSupplier = (XBookmarksSupplier) UnoRuntime.queryInterface(XBookmarksSupplier.class,
          textDocument.getXTextDocument());
      if (xBookmarksSupplier == null)
        return null;
      Object bookmark = xBookmarksSupplier.getBookmarks().getByName(name);
      XTextContent xBookmarkContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class,
          bookmark);
      XNamed xNamed = (XNamed) UnoRuntime.queryInterface(XNamed.class, bookmark);
      if (xBookmarkContent == null)
        return null;
      XTextRange xBookmarkRange = xBookmarkContent.getAnchor();
      if (xBookmarkRange == null)
        return null;
      return new Bookmark(textDocument, xBookmarkRange, xNamed);
    }
    catch (Exception exception) {
      return null;
    }
  }
  //----------------------------------------------------------------------------
}