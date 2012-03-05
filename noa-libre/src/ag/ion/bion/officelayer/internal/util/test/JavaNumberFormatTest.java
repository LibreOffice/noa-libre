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
package ag.ion.bion.officelayer.internal.util.test;

import java.awt.Color;

import ag.ion.bion.officelayer.internal.util.JavaNumberFormat;
import ag.ion.bion.officelayer.util.IJavaFormattedNumber;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Andreas Bröker
 */
public class JavaNumberFormatTest extends TestCase {

  public void testGetJavaNumberFormatter() {
    try {
      JavaNumberFormat javaNumberFormat = new JavaNumberFormat("#,000");
      IJavaFormattedNumber javaFormattedNumber = javaNumberFormat.formatNumber(9.9);
      String value = javaFormattedNumber.getFormattedNumber();
      Assert.assertEquals("9,900", value);
      
      javaNumberFormat = new JavaNumberFormat("#,0#");
      javaFormattedNumber = javaNumberFormat.formatNumber(1234.567);
      value = javaFormattedNumber.getFormattedNumber();
      Assert.assertEquals("1234,57", value);
      
      javaNumberFormat = new JavaNumberFormat("[<=4][GRueN]#.##0,00 \"ueC kalt\";[>7][ROT]#.##0,00 \"ueC\";[BLAU]#.##0,00 \"ueC\"");
      javaFormattedNumber = javaNumberFormat.formatNumber(-10);
      value = javaFormattedNumber.getFormattedNumber();
      Assert.assertEquals("-10,00 ueC kalt", value);
      Assert.assertEquals(Color.GREEN, javaFormattedNumber.getTextColor());
      
      
      javaNumberFormat = new JavaNumberFormat("[<=4][GRueN]#.##0,00 \"ueC kalt\";[>7][ROT]#.##0,00 \"ueC\";[BLAU]#.##0,00 \"ueC\"");
      javaFormattedNumber = javaNumberFormat.formatNumber(10);
      value = javaFormattedNumber.getFormattedNumber();
      Assert.assertEquals("10,00 ueC", value);
      Assert.assertEquals(Color.RED, javaFormattedNumber.getTextColor());
    }
    catch(Exception exception) {
      exception.printStackTrace();
      Assert.fail(exception.getMessage());
    }
  }

}
