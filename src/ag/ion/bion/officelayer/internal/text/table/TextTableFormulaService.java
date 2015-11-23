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
package ag.ion.bion.officelayer.internal.text.table;

import ag.ion.bion.officelayer.text.table.IFormula;
import ag.ion.bion.officelayer.text.table.IFormulaService;

import com.sun.star.table.XCell;

/**
 * Formula service of text table cell.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class TextTableFormulaService implements IFormulaService {

  private XCell xCell = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableFormulaService.
   * 
   * @param xCell OpenOffice.org XCell interface
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org XCell interface is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTableFormulaService(XCell xCell) throws IllegalArgumentException {
    if(xCell == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XCell interface is not valid");
    this.xCell = xCell;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets formula expression.
   * 
   * @param formulaExpression formula expression to be used
   * 
   * @author Andreas Bröker
   */
  public void setFormula(String formulaExpression) {
    xCell.setFormula(formulaExpression);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns formula of the text table cell.
   * 
   * @return formula of the text table cell
   * 
   * @author Andreas Bröker
   */
  public IFormula getFormula() {
  	String formulaExpression = xCell.getFormula();
  	if(formulaExpression == null) 
      return null;
  	if(formulaExpression.trim().equals("")) 
      return null;
    TextTableFormulaExpression textTableFormulaExpression = new TextTableFormulaExpression(formulaExpression);
    return new TextTableFormula(textTableFormulaExpression);
  }
  //----------------------------------------------------------------------------
}