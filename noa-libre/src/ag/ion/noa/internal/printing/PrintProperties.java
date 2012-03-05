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

import ag.ion.noa.printing.IPrintProperties;

/**
 * Properties for printing documents.
 * 
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */ 
public class PrintProperties implements IPrintProperties {

  private short  copyCount = 1;
  private String pages     = null;
	
  //----------------------------------------------------------------------------
  /**
   * Constructs new PrintProperties.
   * 
   * @param copyCount the number of copies to print 
   * (values less then 1 will result in a copy count of 1)
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public PrintProperties(short copyCount) {
    this(copyCount,null);
  }
  //----------------------------------------------------------------------------
	/**
	 * Constructs new PrintProperties.
	 * 
	 * @param copyCount the number of copies to print 
	 * (values less then 1 will result in a copy count of 1)
   * @param pages the pages to print (e.g. "1, 3, 4-7, 9-"), or null for default
   * TODO: This format is not checked yet, so validate it yourself for now
	 * 
	 * @author Markus Krüger
   * @date 16.08.2007
	 */
	public PrintProperties(short copyCount, String pages) {
	  if(copyCount > 1)
	    this.copyCount = copyCount;
		this.pages = pages;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns the number of copies to print.
   * 
   * @return the number of copies to print
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public short getCopyCount() {
    return copyCount;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the pages to print (e.g. "1, 3, 4-7, 9-").
   * 
   * @return the pages to print (e.g. "1, 3, 4-7, 9-")
   * 
   * @author Markus Krüger
   * @date 16.08.2007
   */
  public String getPages() {
    return pages;
  }
  //----------------------------------------------------------------------------
	
}