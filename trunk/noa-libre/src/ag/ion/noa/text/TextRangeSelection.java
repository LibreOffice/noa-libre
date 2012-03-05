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
 * Copyright 2003-2006 by IOn AG                                            *
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
 * Last changes made by $Author: markus $, $Date: 2007-08-03 14:06:27 +0200 (Fr, 03 Aug 2007) $
 */
package ag.ion.noa.text;

import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.util.Assert;
import ag.ion.noa.view.ISelection;

import com.sun.star.uno.XInterface;

/**
 * Selection of a text range.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11545 $
 * @date 09.07.2006
 */ 
public class TextRangeSelection implements ITextRangeSelection , IXInterfaceObjectSelection, ISelection {
	
	private ITextRange textRange = null;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new TextRangeSelection.
	 * 
	 * @param textRange text range selection to be used
	 * 
	 * @author Andreas Bröker
	 * @date 09.07.2006
	 */
	public TextRangeSelection(ITextRange textRange) {
		Assert.isNotNull(textRange, ITextRange.class, this);
		this.textRange = textRange;
	}	
  //----------------------------------------------------------------------------
	/**
	 * Returns text range of the selection.
	 * 
	 * @return text range of the selection
	 * 
	 * @author Andreas Bröker
	 * @date 09.07.2006
	 */
	public ITextRange getTextRange() {
		return textRange;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns object implementing XInterface of the selection.
   * 
   * @return object implementing XInterface of the selection
   * 
   * @author Markus Krüger
   * @date 01.08.2007
   */
  public XInterface getXInterfaceObject() {
    return textRange.getXTextRange();
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns information whether the selection is empty.
	 * 
	 * @return information whether the selection is empty
	 * 
	 * @author Andreas Bröker
	 * @date 09.07.2006
	 */
	public boolean isEmpty() {
		return false;
	}
  //----------------------------------------------------------------------------
	
}