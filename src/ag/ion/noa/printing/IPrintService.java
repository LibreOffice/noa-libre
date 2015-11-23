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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa.printing;

import ag.ion.bion.officelayer.document.DocumentException;
import ag.ion.noa.NOAException;

/**
 * Service for printing documents.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */ 
public interface IPrintService {
	
  //----------------------------------------------------------------------------
  /**
   * Prints the document to the active printer.
   * 
   * @throws DocumentException if printing fails
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public void print() throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Prints the document to the active printer with the given print properties.
   * 
   * @param printProperties the properties to print with, or null to use default settings
   * 
   * @throws DocumentException if printing fails
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public void print(IPrintProperties printProperties) throws DocumentException;
  //----------------------------------------------------------------------------
  /**
   * Returns if the active printer is busy.
   * 
   * @return if the active printer is busy
   * 
   * @throws NOAException if the busy state could not be retrieved
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public boolean isActivePrinterBusy() throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Returns the active printer.
   * 
   * @return the active printer
   * 
   * @throws NOAException if printer could not be retrieved
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public IPrinter getActivePrinter() throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Sets the active printer.
   * 
   * @param printer the printer to be set to be active
   * 
   * @throws NOAException if printer could not be set
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public void setActivePrinter(IPrinter printer) throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Constructs a printer with the given properties and returns it.
   * 
   * @param name the name of the printer cue to be used
   * 
   * @return the constructed printer
   * 
   * @throws NOAException if printer could not be constructed
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public IPrinter createPrinter(String name) throws NOAException;
  //----------------------------------------------------------------------------
	
}