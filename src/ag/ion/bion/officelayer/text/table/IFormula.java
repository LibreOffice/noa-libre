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
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.bion.officelayer.text.table;

import ag.ion.bion.officelayer.text.TextException;

/**
 * Formula of a text table cell.
 * 
 * @author Miriam Sutter
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public interface IFormula {
  
  //----------------------------------------------------------------------------
  /**
   * Returns the arguments of the formula.
   * 
   * @return arguments of the formula
   * 
   * @author Miriam Sutter
   */
  public IArgument[] getArguments();
  //----------------------------------------------------------------------------
  /**
   * Calculates the formula.
   * 
   * @return calculated value
   * 
   * @throws TextException if the formula could not be calculated
   * 
   * @author Miriam Sutter
   */
  public double calcFormula() throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell references service.
   * 
   * @return text table cell references service
   * 
   * @author Andreas Bröker
   */
  public ITextTableCellReferencesService getCellReferencesService();  
  //----------------------------------------------------------------------------
  /**
   * Returns formula expression.
   * 
   * @return formula expression
   * 
   * @author Andreas Bröker
   */
  public String getExpression();  
  //----------------------------------------------------------------------------
}