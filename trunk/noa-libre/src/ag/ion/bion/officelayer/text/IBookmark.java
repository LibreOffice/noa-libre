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
package ag.ion.bion.officelayer.text;

/**
 * Bookmark of a text document.
 * 
 * @author Markus Krüger
 * @version $Revision: 11664 $
 */
public interface IBookmark extends ITextRange {

  //----------------------------------------------------------------------------
  /**
   * Returns the name of the bookmark.
   * 
   * @return the name of the bookmark
   * 
   * @author Markus Krüger
   * @date 13.07.2006
   */
  public String getName();

  //----------------------------------------------------------------------------
  /**
   * Sets the name of the bookmark.
   * 
   * @param name the new name of the bookmark
   * 
   * @author Markus Krüger
   * @date 06.10.2008
   */
  public void setName(String name);

  //----------------------------------------------------------------------------
  /**
   * Jumps to the bookmark.
   * 
   * @author Markus Krüger
   */
  public void jumpTo();
  //----------------------------------------------------------------------------

}