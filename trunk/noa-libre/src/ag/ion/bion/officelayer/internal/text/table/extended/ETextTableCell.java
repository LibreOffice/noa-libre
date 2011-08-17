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
package ag.ion.bion.officelayer.internal.text.table.extended;

import ag.ion.bion.officelayer.internal.text.TextTableCellName;

import ag.ion.bion.officelayer.internal.text.table.TextTableFormulaExpression;

import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.extended.IETextTable;
import ag.ion.bion.officelayer.text.table.extended.IETextTableCell;

/**
 * Implementation for extended table cells.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class ETextTableCell implements IETextTableCell{
	
	private ITextTableCell 				tableCell 					= null;
	private TextTableCellName 		textTableCellName 	= null;
	private ETextTable						textTable						= null;
	
	private String								cellFormula					= null;
	
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableCell.
   * 
   * @param tableCell the text table cell
   * @param cellName the cell name
   * @param textTable the text table
   *  
   * @throws IllegalArgumentException if one the OpenOffice.org interfaces is not valid
   * 
   * @author Miriam Sutter
   */
  public ETextTableCell(ITextTableCell tableCell, String cellName, IETextTable textTable) throws IllegalArgumentException {
  	if(tableCell == null) 
  		throw new IllegalArgumentException("The submitted cell is not valid.");
  	if(cellName == null) 
      throw new IllegalArgumentException("The submitted cell name is not valid.");
  	if(textTable == null) 
      throw new IllegalArgumentException("The submitted table management is not valid.");
		if(!(textTable instanceof ETextTable)) 
      throw new IllegalArgumentException("The submitted table is not valid.");
  	this.tableCell = tableCell;
  	this.textTable = (ETextTable)textTable;
  	textTableCellName = new TextTableCellName(cellName);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the cell name.
   * 
   * @return cell name
   * 
   * @author Miriam Sutter
   */
  public TextTableCellName getName() {
  	return textTableCellName;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the containing text table cell.
   * 
   * @return text table cell
   * 
   * @author Miriam Sutter
   */
  public ITextTableCell getTableCell() {
  	return tableCell;
  }
  //----------------------------------------------------------------------------
  /**
   * Sets the cell formula.
   *
   * @param cellFormula cell formula
   * 
   * @throws TextException if any error occurs.
   *
   * @author Miriam Sutter
   */
  public void setCellFormula(String cellFormula) throws TextException{
  	this.cellFormula = cellFormula;
  	TextTableFormulaExpression textTableFormulaExpression = new TextTableFormulaExpression(cellFormula);
  	
  	ETextTableCellReferencesService textTableCellReferencesService = new ETextTableCellReferencesService(textTableFormulaExpression,textTable);
  	textTableCellReferencesService.applyModifications();
  	
  	tableCell.setFormula(textTableFormulaExpression.getExpression());
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the cell formula.
   * 
   * @return cell formula
   * 
   * @author Miriam Sutter
   */
  public String getCellFormula() {
  	return cellFormula;
  }
  //----------------------------------------------------------------------------
}
