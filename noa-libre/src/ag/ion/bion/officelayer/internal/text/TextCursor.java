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
 * Last changes made by $Author: markus $, $Date: 2007-09-19 15:46:56 +0200 (Mi, 19 Sep 2007) $
 */
package ag.ion.bion.officelayer.internal.text;

import ag.ion.bion.officelayer.filter.IFilter;
import ag.ion.bion.officelayer.internal.document.ByteArrayXInputStreamAdapter;
import ag.ion.bion.officelayer.text.ICharacterProperties;
import ag.ion.bion.officelayer.text.IPageCursor;
import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextRange;
import ag.ion.bion.officelayer.text.IViewCursor;

import ag.ion.noa.NOAException;

import ag.ion.noa.document.URLAdapter;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.document.XDocumentInsertable;

import com.sun.star.style.BreakType;

import com.sun.star.table.XCell;

import com.sun.star.text.ControlCharacter;
import com.sun.star.text.XParagraphCursor;
import com.sun.star.text.XSentenceCursor;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XWordCursor;

import com.sun.star.uno.UnoRuntime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Cursor of a text document.
 * 
 * @author Miriam Sutter
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11574 $
 */
public class TextCursor implements ITextCursor {
  
  private ITextDocument textDocument = null;
 
	private XTextCursor       xTextCursor       = null;
  private XWordCursor       xWordCursor       = null;
  private XSentenceCursor   xSentenceCursor   = null;
  private XParagraphCursor  xParagraphCursor  = null;
    
  //----------------------------------------------------------------------------
  /**
   * Constructs new TextCursor.
   * 
   * @param textDocument text document to be used
   * @param xTextCursor OpenOffice.org XTextCursor interface
   * 
   * @throws IllegalArgumentException if the submitted OpenOffice.org XTextCursor or XTextDocument interface 
   * is not valid
   * 
   * @author Andreas Bröker
   */
	public TextCursor(ITextDocument textDocument, XTextCursor xTextCursor) {
		if(xTextCursor == null)
      throw new IllegalArgumentException("The submitted OpenOffice.org XTextCursor interface is not valid.");
    this.xTextCursor = xTextCursor;
    
    if(textDocument == null)
      throw new IllegalArgumentException("The submitted text document is not valid.");
    this.textDocument = textDocument;
  }
  //----------------------------------------------------------------------------
	/**
	 * Returns the character properties.
	 * 
	 * @return character properties
	 * 
	 * @author Miriam Sutter
	 */
	public ICharacterProperties getCharacterProperties() {
		 XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xTextCursor);
	   return new CharacterProperties(xPropertySet);
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the cursor to the end position (wherever it is currently)
	 * 
	 * @param mark indicates if the space between the current  
	 * 				position and the end is to be marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public void gotoEnd(boolean mark) {
		xTextCursor.gotoEnd(mark);
	}
  //----------------------------------------------------------------------------
	/**
	 * Sets the cursor to the start position (wherever it is currently)
	 * @param mark indicates if the space between the current  
	 * 				position and the start is to be marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public void gotoStart(boolean mark) {
		xTextCursor.gotoStart(mark);
	}
  //----------------------------------------------------------------------------
	/**
	 * Goes to the submitted position.
	 * 
	 * @param range the range that specifies where to got
	 * @param mark indicates if the space between the current  
	 * 				position and the new one is to be marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public void gotoRange(ITextRange range, boolean mark) {
		xTextCursor.gotoRange(range.getXTextRange(), mark);
	}
  //----------------------------------------------------------------------------
  /**
   * Sets content to the cursor.
   * 
   * @param content content to be used
   * 
   * @author Andreas Bröker
   */
  public void setString(String content) {
    if(content != null)
      xTextCursor.setString(content);
  }
  //----------------------------------------------------------------------------
	/**
	 * Gets the text that is marked by the cursor.
	 * 
	 * @return the text marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public String getString() {
		return xTextCursor.getString();
	}
  //----------------------------------------------------------------------------	
	/**
	 * Goes to the submitted position.
	 * 
	 * @param stepNumber stepNumber the number of cursor steps to jump 
	 * @param mark mark indicates if the space between the current  
	 * 				position and the new one is to be marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public void goLeft(short stepNumber, boolean mark) { 
		xTextCursor.goLeft(stepNumber, mark);
	}
  //----------------------------------------------------------------------------
	/**
	 * Goes to the submitted position.
	 * 
	 * @param stepNumber the number of cursor steps to jump 
	 * @param mark mark indicates if the space between the current  
	 * 				position and the new one is to be marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public void goRight(short stepNumber, boolean mark) {
		xTextCursor.goRight(stepNumber, mark);
	}
  //----------------------------------------------------------------------------
  /**
   * Returns start position of the cursor.
   * 
   * @return start position of the cursor
   * 
   * @author Andreas Bröker
   */
  public ITextRange getStart() {
    return new TextRange(textDocument, xTextCursor.getStart());
  }
  //----------------------------------------------------------------------------
  /**
   * Returns end position of the cursor.
   * 
   * @return end position of the cursor
   * 
   * @author Markus Krüger
   */
  public ITextRange getEnd() {
    return new TextRange(textDocument, xTextCursor.getEnd());
  }
  //----------------------------------------------------------------------------
  /**
   * Returns the page number of the text cursor start, returns -1 if page number
   * could not be determined.
   * 
   * @return the page number of the text cursor start, returns -1 if page number
   * could not be determined
   * 
   * @author Markus Krüger
   */
  public short getStartPageNumber() {
    IViewCursor viewCursor = textDocument.getViewCursorService().getViewCursor();
    IPageCursor pageCursor = viewCursor.getPageCursor();
    if(pageCursor != null) {
      viewCursor.goToRange(getStart(),false);
      return pageCursor.getPage();
    }
    return -1;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the page number of the text cursor end, returns -1 if page number
   * could not be determined.
   * 
   * @return the page number of the text cursor end, returns -1 if page number
   * could not be determined
   * 
   * @author Markus Krüger
   */
  public short getEndPageNumber() {
    IViewCursor viewCursor = textDocument.getViewCursorService().getViewCursor();
    IPageCursor pageCursor = viewCursor.getPageCursor();
    if(pageCursor != null) {
      viewCursor.goToRange(getEnd(),false);
      return pageCursor.getPage();
    }
    return -1;
  }  
  //----------------------------------------------------------------------------
  /**
   * Inserts page break at the current cursor position. 
   * 
   * @throws NOAException if the page break can not be set
   * 
   * @author Andreas Bröker
   * @date 19.09.2006
   */
  public void insertPageBreak() throws NOAException {
    try {
      XCell xCell = (XCell)UnoRuntime.queryInterface(XCell.class, xTextCursor.getText());
      XPropertySet propertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xTextCursor);
      propertySet.setPropertyValue("BreakType", BreakType.PAGE_AFTER);
      if(xCell == null) {
        xTextCursor.getText().insertControlCharacter(xTextCursor, ControlCharacter.PARAGRAPH_BREAK, false);
      }      
    }
    catch(Throwable throwable) {
      throw new NOAException("Error inserting page break.",throwable);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Inserts a text document at the current cursor location.
   * 
   * @param url URL of the document (must look like file:///c:/test.odt)
   * 
   * @throws NOAException if the text document can not be inserted
   * 
   * @author Andreas Bröker
   * @date 27.10.2006
   */
  public void insertDocument(String url) throws NOAException {
    if(url == null)
      return;
    try {
      XDocumentInsertable xDocumentInsertable = (XDocumentInsertable)UnoRuntime.queryInterface(XDocumentInsertable.class, xTextCursor);
      if(xDocumentInsertable != null)
        xDocumentInsertable.insertDocumentFromURL(URLAdapter.adaptURL(url), new PropertyValue[0]);
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Inserts a file stream at the current cursor location.
   * 
   * @param inputStream a file stream to be inserted
   * @param filter the filter that the stream is baes on
   * 
   * @throws NOAException if the file stream can not be inserted
   * 
   * @author Markus Krüger
   * @date 24.05.2007
   */
  public void insertDocument(InputStream inputStream,IFilter filter) throws NOAException {
    if(inputStream == null || filter == null)
      return;
    FileOutputStream outputStream = null;
    File tempFile = null;
    try {
      XDocumentInsertable xDocumentInsertable = (XDocumentInsertable)UnoRuntime.queryInterface(XDocumentInsertable.class, xTextCursor);
      if(xDocumentInsertable != null) {
        boolean useOld = true;
        if(useOld) {
          byte buffer[]= new byte[0xffff];
          int bytes = -1;
          tempFile = File.createTempFile("noatemp"+System.currentTimeMillis(),"tmp");
          tempFile.deleteOnExit();
          outputStream = new FileOutputStream(tempFile);
          while((bytes = inputStream.read(buffer)) != -1)
            outputStream.write(buffer, 0, bytes);   
          insertDocument(tempFile.getAbsolutePath());
        }
        else {
          PropertyValue[] loadProps = new PropertyValue[2]; 
          loadProps[0] = new PropertyValue();
          loadProps[0].Name = "InputStream";  //$NON-NLS-1$
          loadProps[0].Value = new ByteArrayXInputStreamAdapter(inputStream,null);
  
          loadProps[1] = new PropertyValue(); 
          loadProps[1].Name = "FilterName";  //$NON-NLS-1$
          loadProps[1].Value = filter.getFilterDefinition(textDocument);
  
          xDocumentInsertable.insertDocumentFromURL("private:stream", loadProps);  
        }
      }
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
    finally {
      if(inputStream != null) {
        try {
          inputStream.close();
        } 
        catch (IOException ioException) {
          //do nothing
        }
      }
      if(outputStream != null) {
        try {
          outputStream.close();
        } 
        catch (IOException ioException) {
          //do nothing
        }
      }
      if(tempFile != null)
        tempFile.delete();
    }
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns if the current cursor supports word cursor operations.
   * 
   * @return if the current cursor supports word cursor operations
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean supportsWordCursor() {
    if(this.xWordCursor != null)
      return true;
    XWordCursor xWordCursor = (XWordCursor)UnoRuntime.queryInterface(XWordCursor.class, xTextCursor);
    if(xWordCursor != null) {
      this.xWordCursor = xWordCursor;
      return true;
    }
    return false;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns if the cursor is positioned at the start of a word.
   * 
   * @return if the cursor is positioned at the start of a word
   * 
   * @throws NOAException if word cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean isStartOfWord() throws NOAException {
    if(supportsWordCursor())
      return xWordCursor.isStartOfWord();
    throw new NOAException("Word cursor operations not supported");
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns if the cursor is positioned at the end of a word.
   * 
   * @return if the cursor is positioned at the end of a word
   * 
   * @throws NOAException if word cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean isEndOfWord() throws NOAException {
    if(supportsWordCursor())
      return xWordCursor.isEndOfWord();
    throw new NOAException("Word cursor operations not supported");
  }   
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the next word.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   *        
   * @return if the cursor could be positioned
   * 
   * @throws NOAException if word cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean gotoNextWord(boolean mark) throws NOAException {
    if(supportsWordCursor())
      return xWordCursor.gotoNextWord(mark);
    throw new NOAException("Word cursor operations not supported");
  }      
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the previous word.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   *        
   * @return if the cursor could be positioned
   * 
   * @throws NOAException if word cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean gotoPreviousWord(boolean mark) throws NOAException {
    if(supportsWordCursor())
      return xWordCursor.gotoPreviousWord(mark);
    throw new NOAException("Word cursor operations not supported");
  }        
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the end of the current word.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   *        
   * @return if the cursor could be positioned
   * 
   * @throws NOAException if word cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean gotoEndOfWord(boolean mark) throws NOAException {
    if(supportsWordCursor())
      return xWordCursor.gotoEndOfWord(mark);
    throw new NOAException("Word cursor operations not supported");
  }       
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the start of the current word.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   *        
   * @return if the cursor could be positioned
   * 
   * @throws NOAException if word cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean gotoStartOfWord(boolean mark) throws NOAException {
    if(supportsWordCursor())
      return xWordCursor.gotoStartOfWord(mark);
    throw new NOAException("Word cursor operations not supported");
  }        
  //----------------------------------------------------------------------------
  /**
   * Returns if the current cursor supports sentence cursor operations.
   * 
   * @return if the current cursor supports sentence cursor operations
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean supportsSentenceCursor() {
    if(this.xSentenceCursor != null)
      return true;
    XSentenceCursor xSentenceCursor = (XSentenceCursor)UnoRuntime.queryInterface(XSentenceCursor.class, xTextCursor);
    if(xSentenceCursor != null) {
      this.xSentenceCursor = xSentenceCursor;
      return true;
    }
    return false;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns if the cursor is positioned at the start of a sentence.
   * 
   * @return if the cursor is positioned at the start of a sentence
   * 
   * @throws NOAException if sentence cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean isStartOfSentence() throws NOAException {
    if(supportsSentenceCursor())
      return xSentenceCursor.isStartOfSentence();
    throw new NOAException("Sentence cursor operations not supported");
  }     
  //----------------------------------------------------------------------------
  /**
   * Returns if the cursor is positioned at the end of a sentence.
   * 
   * @return if the cursor is positioned at the end of a sentence
   * 
   * @throws NOAException if sentence cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean isEndOfSentence() throws NOAException {
    if(supportsSentenceCursor())
      return xSentenceCursor.isEndOfSentence();
    throw new NOAException("Sentence cursor operations not supported");
  } 
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the next sentence.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   * 
   * @throws NOAException if sentence cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public void gotoNextSentence(boolean mark) throws NOAException {
    if(supportsSentenceCursor())
      xSentenceCursor.gotoNextSentence(mark);
    else
      throw new NOAException("Sentence cursor operations not supported");
  }    
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the previous sentence.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   * 
   * @throws NOAException if sentence cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public void gotoPreviousSentence(boolean mark) throws NOAException {
    if(supportsSentenceCursor())
      xSentenceCursor.gotoPreviousSentence(mark);
    else
      throw new NOAException("Sentence cursor operations not supported");
  }       
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the end of the current sentence.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   * 
   * @throws NOAException if sentence cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public void gotoEndOfSentence(boolean mark) throws NOAException {
    if(supportsSentenceCursor())
      xSentenceCursor.gotoEndOfSentence(mark);
    else
      throw new NOAException("Sentence cursor operations not supported");
  }       
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the start of the current sentence.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   * 
   * @throws NOAException if sentence cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public void gotoStartOfSentence(boolean mark) throws NOAException {
    if(supportsSentenceCursor())
      xSentenceCursor.gotoStartOfSentence(mark);
    else
      throw new NOAException("Sentence cursor operations not supported");
  }       
  //----------------------------------------------------------------------------
  /**
   * Returns if the current cursor supports paragraph cursor operations.
   * 
   * @return if the current cursor supports paragraph cursor operations
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean supportsParagraphCursor() {
    if(this.xParagraphCursor != null)
      return true;
    XParagraphCursor xParagraphCursor = (XParagraphCursor)UnoRuntime.queryInterface(XParagraphCursor.class, xTextCursor);
    if(xParagraphCursor != null) {
      this.xParagraphCursor = xParagraphCursor;
      return true;
    }
    return false;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns if the cursor is positioned at the start of a paragraph.
   * 
   * @return if the cursor is positioned at the start of a paragraph
   * 
   * @throws NOAException if paragraph cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean isStartOfParagraph() throws NOAException {
    if(supportsParagraphCursor())
      return xParagraphCursor.isStartOfParagraph();
    throw new NOAException("Paragraph cursor operations not supported");
  }    
  //----------------------------------------------------------------------------
  /**
   * Returns if the cursor is positioned at the end of a paragraph.
   * 
   * @return if the cursor is positioned at the end of a paragraph
   * 
   * @throws NOAException if paragraph cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean isEndOfParagraph() throws NOAException {
    if(supportsParagraphCursor())
      return xParagraphCursor.isEndOfParagraph();
    throw new NOAException("Paragraph cursor operations not supported");
  }      
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the next paragraph.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   * 
   * @throws NOAException if paragraph cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public void gotoNextParagraph(boolean mark) throws NOAException {
    if(supportsParagraphCursor())
      xParagraphCursor.gotoNextParagraph(mark);
    else
      throw new NOAException("Paragraph cursor operations not supported");
  }         
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the previous paragraph.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   * 
   * @throws NOAException if paragraph cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public void gotoPreviousParagraph(boolean mark) throws NOAException {
    if(supportsParagraphCursor())
      xParagraphCursor.gotoPreviousParagraph(mark);
    else
      throw new NOAException("Paragraph cursor operations not supported");
  }           
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the end of the current paragraph.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   * 
   * @throws NOAException if paragraph cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public void gotoEndOfParagraph(boolean mark) throws NOAException {
    if(supportsParagraphCursor())
      xParagraphCursor.gotoEndOfParagraph(mark);
    else
      throw new NOAException("Paragraph cursor operations not supported");
  }          
  //----------------------------------------------------------------------------
  /**
   * Moves the cursor to the start of the current paragraph.
   * 
   * @param mark mark indicates if the space between the current  
   *        position and the new one is to be marked
   * 
   * @throws NOAException if paragraph cursor operations are not supported
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public void gotoStartOfParagraph(boolean mark) throws NOAException {
    if(supportsParagraphCursor())
      xParagraphCursor.gotoStartOfParagraph(mark);
    else
      throw new NOAException("Paragraph cursor operations not supported");
  }          
  //----------------------------------------------------------------------------

}