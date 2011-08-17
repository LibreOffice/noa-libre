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

import ag.ion.bion.officelayer.internal.text.table.TextTableCellReference;
import ag.ion.bion.officelayer.internal.text.table.TextTableCellReferencesService;
import ag.ion.bion.officelayer.internal.text.table.TextTableFormulaExpression;

import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.TextException;

import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

import ag.ion.bion.officelayer.text.table.extended.IETextTableCell;

import java.util.ArrayList;

/**
 * Implementation for extended tables
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class ETextTableCellReferencesService extends TextTableCellReferencesService {

  private ETextTable									textTable										= null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableCellReferencesService.
   * 
   * @param textTableFormulaExpression text table formua expression to be used
   * @param textTable the text table
   * 
   * @throws IllegalArgumentException if the submitted text table formula expression is not valid
   * 
   * @author Miriam Sutter
   */
  public ETextTableCellReferencesService(TextTableFormulaExpression textTableFormulaExpression, ETextTable textTable) throws IllegalArgumentException {
  	super(textTableFormulaExpression);
    if(textTable == null)
    	throw new IllegalArgumentException("The submitted table is not valid.");
    this.textTable = textTable;
  } 
  //----------------------------------------------------------------------------
  /**
   * Applies cell reference modifications to the text table cell formula.
   * 
   * @throws TextException if any error occurs.
   * 
   * @author Miriam Sutter
   */
	public void applyModifications() throws TextException{
		updateFormula();
		textTableFormulaExpression.setExpression(textTableFormulaModel.getExpression());
	}
  //----------------------------------------------------------------------------
  /**
   * Updates the formula for extended tables.
   *
   * @throws TextException if any error occurs.
   * 
   * @author Miriam Sutter
   */
  private void updateFormula() throws TextException{
  	TextTableCellReference[] textTableCellReferences = textTableFormulaModel.getCellReferences();
  	int startIndex = 0;
  	int endIndex = 0;
  	for(int i = 0; i < textTableCellReferences.length; i++) {
  		ArrayList newTextTableCellReferences = new ArrayList();
  		int startColumnIndex = textTableCellReferences[i].getStartColumnIndex();
  		int startRowIndex = textTableCellReferences[i].getStartRowIndex();
  		int endColumnIndex = textTableCellReferences[i].getEndColumnIndex();
  		int endRowIndex = textTableCellReferences[i].getEndRowIndex();
  		
  		IETextTableCell startCell = textTable.getCell(startRowIndex,startColumnIndex);
  		IETextTableCell endCell = textTable.getCell(endRowIndex,endColumnIndex);
  		String formula = "";
  		TextTableCellReference textTableCellReference = null;

  		int startTableIndex = textTable.getTextTableIndex(startCell.getTableCell().getTextTable());
  		int endTableIndex = textTable.getTextTableIndex(endCell.getTableCell().getTextTable());
  		
  		ITextTable table = textTable.getTextTable(startTableIndex);
  		if(!startCell.getName().getName().equals(endCell.getName().getName())) {
  		
				if(startTableIndex != endTableIndex) {
					formula = table.getName()+ "." + startCell.getTableCell().getName().getName() + ":" + table.getCell(endColumnIndex,table.getRowCount()-1).getName().getName();
					textTableCellReference = new TextTableCellReference(formula);
					newTextTableCellReferences.add(textTableCellReference);
					for(int j = startTableIndex+1; j < endTableIndex; j++) {
						table = textTable.getTextTable(j);
						formula = table.getName()+"." +TextTableCellNameHelper.getColumnCharacter(startCell.getName().getColumnIndex()) + "1" + ":" + table.getCell(endColumnIndex,table.getRowCount()-1).getName().getName();
		  			textTableCellReference = new TextTableCellReference(formula);
		  			newTextTableCellReferences.add(textTableCellReference);
					}
					
					table = textTable.getTextTable(endTableIndex);
					formula = table.getName()+"." + TextTableCellNameHelper.getColumnCharacter(startCell.getName().getColumnIndex()) + "1" + ":" + endCell.getTableCell().getName().getName();
				}
				else {
					formula = table.getName()+"." + startCell.getTableCell().getName().getName() + ":" + endCell.getTableCell().getName().getName();
				}
  		}
  		else {
  			formula = table.getName() + "." + startCell.getTableCell().getName().getName();
  		}
			textTableCellReference = new TextTableCellReference(formula);
			newTextTableCellReferences.add(textTableCellReference);
			
  		TextTableCellReference[] references = new TextTableCellReference[newTextTableCellReferences.size()];
  		references = (TextTableCellReference[])newTextTableCellReferences.toArray(references);
  		textTableFormulaModel.replaceCellReference(textTableCellReferences[i],references);
  	}
  }
  //----------------------------------------------------------------------------
}
