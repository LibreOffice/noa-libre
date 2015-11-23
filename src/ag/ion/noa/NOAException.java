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
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa;

/**
 * General exception of NOA.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 2006/02/05
 */ 
public class NOAException extends Exception {

  private static final long serialVersionUID = -8641751067281926240L;
  
  private static final String DEFAULT_EXCEPTION_MESSAGE = "No message available.";

  //----------------------------------------------------------------------------
  /**
   * Constructs new NOAException.
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public NOAException() {
    super(); 
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new NOAException.
   * 
   * @param message exception message
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public NOAException(String message) {
    super(message == null ? DEFAULT_EXCEPTION_MESSAGE : message); 
  }  
  //----------------------------------------------------------------------------
  /**
   * Constructs new NOAException with the submitted throwable.
   * 
   * @param throwable throwable to be used
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public NOAException(Throwable throwable) {
    super(throwable.getMessage() == null ? DEFAULT_EXCEPTION_MESSAGE : throwable.getMessage());
    initCause(throwable);
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new NOAException with the submitted throwable and a message.
   * 
   * @param message message to be used
   * @param throwable throwable to be used
   * 
   * @author Andreas Bröker
   * @date 2006/02/05
   */
  public NOAException(String message, Throwable throwable) {
    super(message == null ? DEFAULT_EXCEPTION_MESSAGE : message);
    initCause(throwable);
  }
  //----------------------------------------------------------------------------
  
}