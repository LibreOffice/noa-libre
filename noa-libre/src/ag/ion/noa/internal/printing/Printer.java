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
 * Last changes made by $Author: andreas $, $Date: 2006-11-06 07:24:57 +0100 (Mo, 06 Nov 2006) $
 */
package ag.ion.noa.internal.printing;

import ag.ion.noa.NOAException;
import ag.ion.noa.printing.IPrinter;

/**
 * A printer that can be used for the printer service.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */ 
public class Printer implements IPrinter {
  
  private String name = null;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new Printer.
	 * 
	 * @param name the name of the printer que to be used
	 * 
	 * @throws NOAException if the name is invalid
	 * 
	 * @author Markus Krüger
   * @date 16.08.2007
	 */
	public Printer(String name) throws NOAException {
		if(name == null)
		  throw new NOAException("Invalid name for printer que.");
		this.name = name;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns the name of the printer que that is used.
   * 
   * @return the name of the printer que that is used
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public String getName() {
    return name;
  }
  //----------------------------------------------------------------------------
	
}