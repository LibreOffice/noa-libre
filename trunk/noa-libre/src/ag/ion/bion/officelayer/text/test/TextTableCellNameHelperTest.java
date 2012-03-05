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
package ag.ion.bion.officelayer.text.test;

import ag.ion.bion.officelayer.text.TextException;
import ag.ion.bion.officelayer.text.table.TextTableCellNameHelper;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Andreas Bröker
 */
public class TextTableCellNameHelperTest extends TestCase {

  public void testGetRowIndex() {
    int rowIndex = TextTableCellNameHelper.getRowIndex("A991");
    Assert.assertEquals(990, rowIndex);
  }

  public void testGetColumnIndex() {
    int columnIndex = TextTableCellNameHelper.getColumnIndex("Z6");
    Assert.assertEquals(25, columnIndex);    
    
    columnIndex = TextTableCellNameHelper.getColumnIndex("z6");
    Assert.assertEquals(51, columnIndex);
    
    columnIndex = TextTableCellNameHelper.getColumnIndex("AP6");
    Assert.assertEquals(67, columnIndex);
    
    columnIndex = TextTableCellNameHelper.getColumnIndex("Az6");
    Assert.assertEquals(103, columnIndex);
  }

  public void testGetColumnCharacter() {
    String character = TextTableCellNameHelper.getColumnCharacter(25);
    Assert.assertEquals("Z", character);
    
    character = TextTableCellNameHelper.getColumnCharacter(51);
    Assert.assertEquals("z", character);
    
    character = TextTableCellNameHelper.getColumnCharacter(77);
    Assert.assertEquals("AZ", character);
    
    character = TextTableCellNameHelper.getColumnCharacter(102);
    Assert.assertEquals("Ay", character);
  }
  
  public void testMoveRowCounterValue() {
    try {
      String cellName = TextTableCellNameHelper.moveRowCounterValue(5, "A2");
      Assert.assertEquals("A7", cellName);
      cellName = TextTableCellNameHelper.moveRowCounterValue(-1, "A2");      
      Assert.assertEquals("A1", cellName);
    } 
    catch (TextException e) {
      Assert.fail(e.getMessage());  
    }   
  }
  
  public void testMoveColumnIndex() {
    try {
      String cellName = TextTableCellNameHelper.moveColumnIndex(2, "A2");
      Assert.assertEquals("C2", cellName);
      cellName = TextTableCellNameHelper.moveColumnIndex(-1, "B2");      
      Assert.assertEquals("A2", cellName);
    } 
    catch (TextException e) {
      Assert.fail(e.getMessage());      
    }    
  }
  
  public void testGetCellRangeStartRowIndex() {
		int rowNumber = TextTableCellNameHelper.getCellRangeStartRowIndex("A2:B7");
		Assert.assertEquals(1, rowNumber);
  }
  
  public void testGetCellRangeStartColumnIndex() {
		int rowNumber = TextTableCellNameHelper.getCellRangeStartColumnIndex("A2:B7");
		Assert.assertEquals(0, rowNumber);
  }
  
  public void testGetCellRangeEndRowIndex() {
		int rowNumber = TextTableCellNameHelper.getCellRangeEndRowIndex("A2:B7");
		Assert.assertEquals(6, rowNumber);
  }
  
  public void testGetCellRangeEndColumnIndex() {
		int rowNumber = TextTableCellNameHelper.getCellRangeEndColumnIndex("A2:B7");
		Assert.assertEquals(1, rowNumber);
  }
}