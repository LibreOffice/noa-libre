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

import java.util.ArrayList;
import java.util.List;

/**
 * A (really) simple model of a formula which was placed into a text table.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 10398 $
 */
public class TextTableFormulaModel {
  
  private String formulaExpression = null;
  
  private List formulaElements = null;
  private List cellReferences  = null;
  
  //these variables are used to revert the model to its state before replacing references
  private List formulaElementsOrig = null;
  private List cellReferencesOrig  = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextTableFormulaModel.
   * 
   * @param textTableFormulaExpression formula expression to be used
   * 
   * @throws IllegalArgumentException if the submitted formula expression is not valid
   * 
   * @author Andreas Bröker
   */
  public TextTableFormulaModel(TextTableFormulaExpression textTableFormulaExpression) throws IllegalArgumentException {
    if(textTableFormulaExpression == null)
      throw new IllegalArgumentException("The submitted formula expression is not valid.");
    this.formulaExpression = textTableFormulaExpression.getExpression();
    init();
    parse();
  }
  //----------------------------------------------------------------------------
  /**
   * Inits all variables.
   * 
   * @author Andreas Bröker
   */
  private void init() {
    formulaElements = new ArrayList();
    cellReferences  = new ArrayList();
  }
  //----------------------------------------------------------------------------
  /**
   * Returns text table cell references.
   * 
   * @return text table cell references
   * 
   * @author Andreas Bröker
   */
  public TextTableCellReference[] getCellReferences() {
    TextTableCellReference[] cellReference = new TextTableCellReference[cellReferences.size()];
    return (TextTableCellReference[])cellReferences.toArray(cellReference);
  }
  //----------------------------------------------------------------------------
  /**
   * Returns formula expression. All modifications will be applied with this
   * method call.
   * 
   * @return formula expression
   * 
   * @author Andreas Bröker
   */
  public String getExpression() {
    StringBuffer stringBuffer = new StringBuffer();
    for(int i=0; i<formulaElements.size(); i++) {
      stringBuffer.append(formulaElements.get(i).toString());
    }
    return stringBuffer.toString();
  }
  //----------------------------------------------------------------------------
  /**
   * Parses the formula expression in order to create the model. 
   *
   * @author Andreas Bröker
   */
  private void parse() {
    char formulaChars[] = formulaExpression.toCharArray();
    StringBuffer formulaPart = new StringBuffer();
    StringBuffer cellReferenceExpression = null;
    for(int i=0; i<formulaChars.length; i++) {
      if(formulaChars[i] == '<') {
        cellReferenceExpression = new StringBuffer();
        i++;
        for(; i<formulaChars.length; i++) {
          if(formulaChars[i] == '>') {                
            break;
          }
          cellReferenceExpression.append(formulaChars[i]);
        }
        TextTableCellReference cellReference = new TextTableCellReference(cellReferenceExpression.toString());
        formulaElements.add(cellReference);
        cellReferences.add(cellReference);
        cellReferenceExpression = null;
        if(formulaPart.length() != 0) {
          formulaElements.add(formulaElements.size() -1, formulaPart);
          formulaPart = new StringBuffer();
        }
        continue; 
      }
      else {
        formulaPart.append(formulaChars[i]);
      }      
    }
    formulaElements.add(formulaPart);
    if(cellReferenceExpression != null)
      formulaElements.add(cellReferenceExpression);      
  }
  //----------------------------------------------------------------------------
  /**
   * Replaces a cell reference.
   * 
   * @param textTableCellReference the old cell refernce
   * @param textTableCellReferences the new cell references
   * 
   * @author Miriam Sutter
   * @author Markus Krüger
   */
  public void replaceCellReference(TextTableCellReference textTableCellReference,TextTableCellReference[] textTableCellReferences) {
  	if(formulaElementsOrig == null) {
      formulaElementsOrig = new ArrayList();
      cellReferencesOrig = new ArrayList();
      formulaElementsOrig.addAll(formulaElements);
      cellReferencesOrig.addAll(cellReferences);
    }
    
    int index = formulaElements.indexOf(textTableCellReference);
  	formulaElements.set(index,"(");
  	index++;
  	int referenceIndex = cellReferences.indexOf(textTableCellReference);
  	cellReferences.remove(referenceIndex);
  	referenceIndex++;
  	for(int i = 0; i < textTableCellReferences.length; i++) {
  		if(cellReferences.size() < referenceIndex) {
  			cellReferences.add(textTableCellReferences[i]);
  			referenceIndex++;
  		}
  		else {
  			cellReferences.add(referenceIndex,textTableCellReferences[i]);
  			referenceIndex++;
  		}
  		if(formulaElements.size() < index) {
  			formulaElements.add(textTableCellReferences[i]);
  			index++;
  			formulaElements.add("+");
  			index++;
  		}
  		else {
  			formulaElements.add(index,textTableCellReferences[i]);
  			index = index + 1;
  			if(formulaElements.size() < index) {
  				formulaElements.add("+");
  				index++;
  			}
  			else {
  				formulaElements.add(index,"+");
  				index++;
  			}
  		}
  	}
  	formulaElements.set(index-1,")");
  }
  //----------------------------------------------------------------------------
  /**
   * Reverts the model to the state before replacing cell references.
   * 
   * @author Markus Krüger
   */
  public void revertToOriginal() {
    if(formulaElementsOrig != null) {
      formulaElements.clear();
      cellReferences.clear();
      formulaElements.addAll(formulaElementsOrig);
      cellReferences.addAll(cellReferencesOrig);
    }
  }
  //----------------------------------------------------------------------------
}