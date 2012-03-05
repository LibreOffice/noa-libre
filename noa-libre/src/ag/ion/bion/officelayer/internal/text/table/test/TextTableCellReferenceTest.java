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
 * Last changes made by $Author: markus $, $Date: 2007-02-26 11:24:19 +0100 (Mo, 26 Feb 2007) $
 */
package ag.ion.bion.officelayer.internal.text.table.test;

import ag.ion.bion.officelayer.internal.text.table.TextTableCellReference;
import ag.ion.bion.officelayer.internal.text.table.TextTableFormulaExpression;
import ag.ion.bion.officelayer.internal.text.table.TextTableFormulaModel;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Andreas Bröker
 */
public class TextTableCellReferenceTest extends TestCase {

  public void testIsRangeReference() {
    TextTableCellReference reference = new TextTableCellReference("<A4:B6>");
    Assert.assertEquals(true, reference.isRangeReference());
  }

  public void testGetTableName() {
    TextTableCellReference reference = new TextTableCellReference("<Table1.A4.1:B6>");
    Assert.assertEquals("Table1", reference.getTableName());
  }

  public void testGetStartColumnIndex() {
    TextTableCellReference reference = new TextTableCellReference("<Table1.A4.1:B6>");
    Assert.assertEquals(0, reference.getStartColumnIndex());
  }

  public void testGetEndColumnIndex() {
    TextTableCellReference reference = new TextTableCellReference("<Table1.A4.1:B6>");
    Assert.assertEquals(1, reference.getEndColumnIndex());
  }

  public void testGetStartRowIndex() {
    TextTableCellReference reference = new TextTableCellReference("<Table1.A4:B6>");
    Assert.assertEquals(3, reference.getStartRowIndex());
  }

  public void testGetEndRowIndex() {
    TextTableCellReference reference = new TextTableCellReference("<Table1.A4:B6>");
    Assert.assertEquals(5, reference.getEndRowIndex());
  }

  public void testSetTableName() {
    TextTableCellReference reference = new TextTableCellReference("<A4:B6>");
    reference.setTableName("Table2");
    Assert.assertEquals("<Table2.A4:B6>", reference.toString());
  }

  public void testMoveColumnIndex() {
    try {
      TextTableCellReference reference = new TextTableCellReference("<A4:B6>");
      reference.moveColumnIndex(3);
      Assert.assertEquals("<D4:E6>", reference.toString());
    }
    catch(Exception exception) {
      Assert.fail(exception.getMessage());      
    }
  }

  public void testMoveRowIndex() {
    try {
      TextTableCellReference reference = new TextTableCellReference("<A4:B6>");
      reference.moveRowIndex(3);
      Assert.assertEquals("<A7:B9>", reference.toString());
    }
    catch(Exception exception) {
      Assert.fail(exception.getMessage());      
    }
  }

  public void testToColumnRange() {
    try {
      TextTableCellReference reference = new TextTableCellReference("<A4>");
      reference.toColumnRange(0, 3);
      Assert.assertEquals("<A4:D4>", reference.toString());
    }
    catch(Exception exception) {
      Assert.fail(exception.getMessage());      
    }
  }

  public void testExtendColumnRange() {
    try {
      TextTableCellReference reference = new TextTableCellReference("<A4:B6>");
      reference.extendColumnRangeTo(3);
      Assert.assertEquals("<A4:D6>", reference.toString());
    }
    catch(Exception exception) {
      Assert.fail(exception.getMessage());      
    }
  }
  
  public void testContainsCell() {
    TextTableCellReference reference = new TextTableCellReference("<A4:D6>");
    Assert.assertEquals(true, reference.containsCell("C5"));
  }
  
  public void testContainsColumn() {
    TextTableCellReference reference = new TextTableCellReference("<A4:D6>");
    Assert.assertEquals(true, reference.containsColumn("C5"));
  }
  
  public void testContainsRow() {
    TextTableCellReference reference = new TextTableCellReference("<A4:D6>");
    Assert.assertEquals(false, reference.containsRow("C7"));
  }
  
  public void testContainsColumnAfter() {
    TextTableCellReference reference = new TextTableCellReference("<D6>");
    Assert.assertEquals(true, reference.containsColumnAfter("C6"));
  }
  
  public void testContainsRowAfter() {
    TextTableCellReference reference = new TextTableCellReference("<D6>");
    Assert.assertEquals(true, reference.containsColumnAfter("D4"));
  }
  
  public void testExtendColumnRange2() {
    try {
      TextTableCellReference reference = new TextTableCellReference("<D6>");
      reference.extendColumnRange(2);
      Assert.assertEquals("<D6:F6>", reference.toString());
      
      reference = new TextTableCellReference("<D6:F7>");
      reference.extendColumnRange(2);
      Assert.assertEquals("<D6:H7>", reference.toString());
    }
    catch(Exception exception) {
      Assert.fail(exception.getMessage());      
    }
  }
  
  public void testExtendRowRange() {
    try {
      TextTableCellReference reference = new TextTableCellReference("<D6>");
      reference.extendRowRange(4);
      Assert.assertEquals("<D6:D10>", reference.toString());
      
      reference = new TextTableCellReference("<D6:F7>");
      reference.extendRowRange(2);
      Assert.assertEquals("<D6:F9>", reference.toString());
    }
    catch(Exception exception) {
      Assert.fail(exception.getMessage());      
    }
  }
  
  public void testTextTableFormulaModel1() {
  	try {
  		TextTableFormulaExpression textTableFormulaExpression = new TextTableFormulaExpression("<A1>");
  		TextTableCellReference[] references = new TextTableCellReference[3];
  		TextTableCellReference reference1 = new TextTableCellReference("<D6>");
  		TextTableCellReference reference2 = new TextTableCellReference("<E6>");
  		TextTableCellReference reference3 = new TextTableCellReference("<F6>");
  		references[0] = reference1;
  		references[1] = reference2;
  		references[2] = reference3;
  		TextTableFormulaModel formulaModel = new TextTableFormulaModel(textTableFormulaExpression);
  		TextTableCellReference cellReference = formulaModel.getCellReferences()[0];
  		formulaModel.replaceCellReference(cellReference,references);
  		references = formulaModel.getCellReferences();
  		Assert.assertEquals(3, references.length);
  		Assert.assertEquals("(<D6>+<E6>+<F6>)", formulaModel.getExpression());
  	}
    catch(Exception exception) {
      Assert.fail(exception.getMessage());      
    }
  }
  public void testTextTableFormulaModel2() {
  	try {
  		TextTableFormulaExpression textTableFormulaExpression = new TextTableFormulaExpression("<A1>+<b2>");
  		TextTableCellReference[] references = new TextTableCellReference[3];
  		TextTableCellReference reference1 = new TextTableCellReference("<D6>");
  		TextTableCellReference reference2 = new TextTableCellReference("<E6>");
  		TextTableCellReference reference3 = new TextTableCellReference("<F6>");
  		references[0] = reference1;
  		references[1] = reference2;
  		references[2] = reference3;
  		TextTableFormulaModel formulaModel = new TextTableFormulaModel(textTableFormulaExpression);
  		TextTableCellReference cellReference = formulaModel.getCellReferences()[0];
  		formulaModel.replaceCellReference(cellReference,references);
  		references = formulaModel.getCellReferences();
  		Assert.assertEquals(4, references.length);
  		Assert.assertEquals("(<D6>+<E6>+<F6>)+<b2>", formulaModel.getExpression());
  	}
    catch(Exception exception) {
      Assert.fail(exception.getMessage());      
    }
  }
}