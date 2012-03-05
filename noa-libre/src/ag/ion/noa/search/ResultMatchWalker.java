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
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa.search;

import ag.ion.bion.officelayer.document.IDocument;

import ag.ion.bion.officelayer.text.ITextRange;

import ag.ion.bion.officelayer.util.Assert;

import ag.ion.noa.NOAException;

import ag.ion.noa.text.TextRangeSelection;

/**
 * Walker for result matches. The walker will select
 * result matches in a document.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 09.07.2006
 */ 
public class ResultMatchWalker {

	private IDocument 		document 			= null;
	private ITextRange[] 	textRanges 		= null;
	
	private int index 		= -1;
	private int maxIndex 	= -1;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs new DocumentMatchWalker.
	 * 
	 * @param document document to be used
	 * @param searchResult search result to be used
	 * 
	 * @author Andreas Bröker
	 * @date 12.07.2006
	 */
	public ResultMatchWalker(IDocument document, ISearchResult searchResult) {
		Assert.isNotNull(document, IDocument.class, this);
		Assert.isNotNull(searchResult, ISearchResult.class, this);
		
		this.document = document;
		
		textRanges = searchResult.getTextRanges();
		maxIndex = textRanges.length -1;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns information whether a next match is available.
	 * 
	 * @return information whether a next match is available
	 * 
	 * @author Andreas Bröker
	 * @date 12.07.2006
	 */
	public boolean hasNextMatch() {
		if(maxIndex != -1 && index +1 <= maxIndex)
			return true;
		else
			return false;
	}
  //----------------------------------------------------------------------------
	/**
	 * Moves to the next match. Returns information whether the move to 
	 * the next match was successfully.
	 * 
	 * @return information whether the move to 
	 * the next match was successfully
	 * 
	 * @author Andreas Bröker
	 * @date 12.07.2006
	 */
	public boolean nextMatch() {
		if(maxIndex != -1) {
			index++;
			if(index <= maxIndex) {				
			ITextRange textRange = getTextRange(index);
				try {
					document.setSelection(new TextRangeSelection(textRange));
					return true;
				}
				catch(NOAException exception) {
					//do not consume
				}
			}
		}
		return false;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns information whether a previous match
	 * is available.
	 * 
	 * @return information whether a previous match
	 * is available
	 * 
	 * @author Andreas Bröker
	 * @date 12.07.2006
	 */
	public boolean hasPreviousMatch() {
		if(maxIndex != -1 && index -1 >= 0)
			return true;
		else
			return false;
	}
  //----------------------------------------------------------------------------
	/**
	 * Moves to the previous match. Returns information whether the move to 
	 * the previous match was successfully.
	 * 
	 * @return information whether the move to 
	 * the previous match was successfully
	 * 
	 * @author Andreas Bröker
	 * @date 12.07.2006
	 */
	public boolean previousMatch() {
		if(maxIndex != -1) {
			index--;
			if(index >= 0) {			
				ITextRange textRange = getTextRange(index);
				try {
					document.setSelection(new TextRangeSelection(textRange));
					return true;
				}
				catch(NOAException exception) {
					//do not consume
				}
			}
		}
		return false;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns text range with the submitted position.
	 * 
	 * @param index index to be used
	 * 
	 * @return text range with the submitted position
	 * 
	 * @author Andreas Bröker
	 * @date 12.07.2006
	 */
	private ITextRange getTextRange(int index) {
		return textRanges[index];
	}
  //----------------------------------------------------------------------------

}
