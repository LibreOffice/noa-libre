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
 *  http://www.ion.ag                                                       *
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa.text;

/**
 * Index of a document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 17.08.2006
 */ 
public interface IDocumentIndex {
  
  /** Alphabetical index of the document. */
  public static final String ALPHABETICAL_INDEX     = "com.sun.star.text.DocumentIndex";
  /** Content index of the document. */
  public static final String CONTENT_INDEX          = "com.sun.star.text.ContentIndex";
  /** User defined document index. */
  public static final String USER_DEFINED_INDEX     = "com.sun.star.text.UserIndex";
  /** Index of illustrations within the document. */
  public static final String ILLUSTARTION_INDEX     = "com.sun.star.text.IllustrationIndex";
  /** Index of objects within the document. */
  public static final String OBJECT_INDEX           = "com.sun.star.text.ObjectIndex";
  /** Index of tables within the document. */
  public static final String TABLE_INDEX            = "com.sun.star.text.TableIndex";
  /** Bibliographical index of the document. */
  public static final String BIBLIOGRAPHICAL_INDEX  = "com.sun.star.text.Bibliography";
  
  //----------------------------------------------------------------------------
  /**
   * Returny type of the index.
   * 
   * @return type of the index
   * 
   * @see ALPHABETICAL_INDEX
   * @see CONTENT_INDEX
   * @see USER_DEFINED_INDEX
   * @see OBJECT_INDEX
   * @see TABLE_INDEX
   * @see BIBLIOGRAPHICAL_INDEX
   * 
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public String getType();  
  //----------------------------------------------------------------------------
  /**
   * Updates the document index. 
   * 
   * @author Andreas Bröker
   * @date 17.08.2006
   */
  public void update();
  //----------------------------------------------------------------------------
  
}