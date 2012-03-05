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
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.bion.officelayer.presentation;

/**
 * Exception for presentation parts of the Office API.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class PresentationException extends Exception {
  
  private static final String DEFAULT_EXCEPTION_MESSAGE = "No message available.";

  //----------------------------------------------------------------------------
  /**
   * Constructs new TextException.
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public PresentationException() {
   super(); 
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextException.
   * 
   * @param message exception message
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public PresentationException(String message) {
   super(message == null ? DEFAULT_EXCEPTION_MESSAGE : message); 
  }  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextException with the submitted exception.
   * 
   * @param exception exception to be used
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public PresentationException(Exception exception) {
    super(exception.getMessage() == null ? DEFAULT_EXCEPTION_MESSAGE : exception.getMessage());
    initCause(exception);
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextException with the submitted exception and a message.
   * 
   * @param message message to be used
   * @param exception exception to be used
   * 
   * @author Markus Krüger
   * @date 07.01.2008
   */
  public PresentationException(String message, Exception exception) {
    super(message == null ? DEFAULT_EXCEPTION_MESSAGE : message);
    initCause(exception);
  }
  //----------------------------------------------------------------------------
}