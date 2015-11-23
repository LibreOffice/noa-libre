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
 * Last changes made by $Author: markus $, $Date: 2007-01-25 18:50:33 +0100 (Do, 25 Jan 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.document.IDocument;

import ag.ion.bion.officelayer.text.IPageStyle;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.ITextTableCell;
import ag.ion.bion.officelayer.text.ITextTableCellName;
import ag.ion.bion.officelayer.text.TextException;

import com.sun.star.beans.XPropertySet;

import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNameContainer;

import com.sun.star.style.XStyle;
import com.sun.star.style.XStyleFamiliesSupplier;

import com.sun.star.table.XCell;

import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextRangeCompare;
import com.sun.star.text.XTextViewCursorSupplier;

import com.sun.star.uno.Any;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.view.XSelectionSupplier;

/**
 * Range of text in a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11363 $
 */
public class TextRange implements ITextRange {
  
  private XTextRange xTextRange = null;
  
  private IDocument document = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextRange.

   * @param document OpenOffice.org document of the text range
   * @param xTextRange OpenOffice.org XTextRange interface
   *  
   * @throws IllegalArgumentException if one the OpenOffice.org interface is not valid
   * 
   * @author Andreas Bröker
   */
  public TextRange(IDocument document, XTextRange xTextRange) throws IllegalArgumentException {
    if(xTextRange == null)
      throw new IllegalArgumentException("Submitted OpenOffice.org XTextRange interface is not valid.");
    this.xTextRange = xTextRange;    
    this.document = document;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns OpenOffice.org XTextRange interface. 
   * 
   * This method is not part of the public API. It should
   * never used from outside.
   * 
   * @return OpenOffice.org XTextRange interface
   * 
   * @author Andreas Bröker
   */
  public XTextRange getXTextRange() {
    return xTextRange;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns related page style of the text range.
   * 
   * @return page style of the text range
   * 
   * @throws TextException if the page style is not available
   * 
   * @author Andreas Bröker
   */
  public IPageStyle getPageStyle() throws TextException {
    if(document == null || !(document instanceof ITextDocument))
    	throw new TextException("Text style not available");
  	try {
      XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xTextRange);
      String pageStyleName = xPropertySet.getPropertyValue("PageStyleName").toString();
      XStyleFamiliesSupplier xStyleFamiliesSupplier = (XStyleFamiliesSupplier)UnoRuntime.queryInterface(XStyleFamiliesSupplier.class, ((ITextDocument)document).getXTextDocument());
      XNameAccess xNameAccess = xStyleFamiliesSupplier.getStyleFamilies();
      Any any = (Any)xNameAccess.getByName("PageStyles");
      XNameContainer xNameContainer = (XNameContainer)any.getObject();
      any = (Any)xNameContainer.getByName(pageStyleName);
      XStyle style = (XStyle)any.getObject();
      return new PageStyle(style);
    }
    catch(Exception exception) {
      TextException textException = new TextException(exception.getMessage());
      textException.initCause(exception);
      throw textException;
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Sets text of the text range.
   * 
   * @param text text to be used
   * 
   * @author Andreas Bröker
   */
  public void setText(String text) {
    if(text == null)
      return;
    xTextRange.setString(text);
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns cell of the texttable if the text range is part of cell. 
   * 
   * @return cell of the texttable if the text range is part of cell or null if
   * the text range is not part of a cell
   * 
   * @author Andreas Bröker
   */
  public ITextTableCell getCell() {
  	if(document == null || !(document instanceof ITextDocument))
    	return null;
  	
    XCell xCell = (XCell)UnoRuntime.queryInterface(XCell.class, xTextRange.getText());
    if(xCell == null)
      return null;
    return new TextTableCell((ITextDocument)document, xCell);
  }
  //----------------------------------------------------------------------------
  /**
   * Compares this text range with the given text range. Returns 1 if this range
   * starts before the given text range, 0 if the text ranges start at the same position
   * and -1 if this text range starts behind the given text range.
   * 
   * @param textRangeToCompare the text range to compare
   * 
   * @return 1 if this range starts before the given text range, 0 if the text ranges 
   *         start at the same position and -1 if this text range starts behind the given text range
   * 
   * @throws TextException if the text ranges to compare are not within the same text. 
   * 
   * @author Markus Krüger
   */
  public short compareRange(ITextRange textRangeToCompare) throws TextException {
    try {
      ITextRange thisCompareRange = this;
      if(document instanceof ITextDocument) {
        ITextDocument textDocument = (ITextDocument)document;
        ITextTableCell thisCompareRangeCell = thisCompareRange.getCell();
        ITextTableCell textRangeToCompareCell = textRangeToCompare.getCell();
        if(thisCompareRangeCell != null && textRangeToCompareCell == null) {
          XTextContent textTable = thisCompareRangeCell.getTextTable().getXTextContent();   
          XSelectionSupplier selectionSupplier = (XSelectionSupplier)UnoRuntime.queryInterface(XSelectionSupplier.class, textDocument.getXTextDocument().getCurrentController());
          selectionSupplier.select(textTable);
          XTextViewCursorSupplier xTextViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, textDocument.getXTextDocument().getCurrentController());
          xTextViewCursorSupplier.getViewCursor().goLeft((short)1,false);
          thisCompareRange = textDocument.getViewCursorService().getViewCursor().getTextCursorFromEnd().getEnd();
        }
        else if(textRangeToCompareCell != null && thisCompareRangeCell == null) {
          XTextContent textTable = textRangeToCompareCell.getTextTable().getXTextContent();   
          XSelectionSupplier selectionSupplier = (XSelectionSupplier)UnoRuntime.queryInterface(XSelectionSupplier.class, textDocument.getXTextDocument().getCurrentController());
          selectionSupplier.select(textTable);
          XTextViewCursorSupplier xTextViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, textDocument.getXTextDocument().getCurrentController());
          xTextViewCursorSupplier.getViewCursor().goLeft((short)1,false);
          textRangeToCompare = textDocument.getViewCursorService().getViewCursor().getTextCursorFromEnd().getEnd();
        }
        else if(thisCompareRangeCell != null && textRangeToCompareCell != null) {
          XTextContent thisCompareRangeTable = thisCompareRangeCell.getTextTable().getXTextContent();
          XTextContent textRangeToCompareTable = textRangeToCompareCell.getTextTable().getXTextContent();
          boolean sameTable = UnoRuntime.areSame(
              thisCompareRangeTable,textRangeToCompareTable);
          if(sameTable) {
            ITextTableCellName thisCompareRangeCellName = thisCompareRangeCell.getName();
            ITextTableCellName textRangeToCompareCellName = textRangeToCompareCell.getName();
            int thisCompareRangeCellRow = thisCompareRangeCellName.getRowIndex();
            int thisCompareRangeCellCol = thisCompareRangeCellName.getColumnIndex();
            int textRangeToCompareCellRow = textRangeToCompareCellName.getRowIndex();
            int textRangeToCompareCellCol = textRangeToCompareCellName.getColumnIndex();
            if(thisCompareRangeCellRow < textRangeToCompareCellRow)
              return 1;
            else if(thisCompareRangeCellRow > textRangeToCompareCellRow)
              return -1;
            else {
              if(thisCompareRangeCellCol < textRangeToCompareCellCol)
                return 1;
              else if(thisCompareRangeCellCol > textRangeToCompareCellCol)
                return -1;
            }
          }
          else {
            XSelectionSupplier selectionSupplier = (XSelectionSupplier)UnoRuntime.queryInterface(XSelectionSupplier.class, textDocument.getXTextDocument().getCurrentController());
            selectionSupplier.select(thisCompareRangeTable);
            XTextViewCursorSupplier xTextViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, textDocument.getXTextDocument().getCurrentController());
            xTextViewCursorSupplier.getViewCursor().goLeft((short)1,false);
            thisCompareRange = textDocument.getViewCursorService().getViewCursor().getTextCursorFromEnd().getEnd();
             
            selectionSupplier = (XSelectionSupplier)UnoRuntime.queryInterface(XSelectionSupplier.class, textDocument.getXTextDocument().getCurrentController());
            selectionSupplier.select(textRangeToCompareTable);
            xTextViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, textDocument.getXTextDocument().getCurrentController());
            xTextViewCursorSupplier.getViewCursor().goLeft((short)1,false);
            textRangeToCompare = textDocument.getViewCursorService().getViewCursor().getTextCursorFromEnd().getEnd();
          }
        }
      }
      XText text = thisCompareRange.getXTextRange().getText();    
      XTextRangeCompare comparator = (XTextRangeCompare) UnoRuntime.queryInterface(XTextRangeCompare.class, text);
      try {
        return comparator.compareRegionStarts(thisCompareRange.getXTextRange().getStart(), textRangeToCompare.getXTextRange().getStart());
      }
      catch (Exception exception) {
        throw new TextException(exception);
      }
    }
    catch(Exception exception) {
      throw new TextException(exception);
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Sets the document of the text range. This makes sense if the text range
   * was generated as the document was unknown.
   * 
   * @param document document to be used
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public void setDocument(IDocument document) {
    this.document = document;
  }
  //----------------------------------------------------------------------------

}