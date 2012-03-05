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
 * Last changes made by $Author: markus $, $Date: 2007-05-29 15:40:14 +0200 (Di, 29 Mai 2007) $
 */
package ag.ion.bion.officelayer.text;

import ag.ion.bion.officelayer.filter.IFilter;
import ag.ion.noa.NOAException;

import java.io.InputStream;

/**
 * Cursor of a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11482 $
 */
public interface ITextCursor {
	
  //----------------------------------------------------------------------------
	/**
	 * Returns the character properties.
	 * 
	 * @return character properties
	 * 
	 * @author Miriam Sutter
	 */
	public ICharacterProperties getCharacterProperties();
  //----------------------------------------------------------------------------
	/**
	 * Sets the cursor to the end position (wherever it is currently).
	 * 
	 * @param mark indicates if the space between the current  
	 * 				position and the end is to be marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public void gotoEnd(boolean mark);
  //----------------------------------------------------------------------------
	/**
	 * Sets the cursor to the start position (wherever it is currently).
	 * 
	 * @param mark indicates if the space between the current  
	 * 				position and the start is to be marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public void gotoStart(boolean mark);
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
	public void gotoRange(ITextRange range, boolean mark);
  //----------------------------------------------------------------------------
  /**
   * Sets content to the cursor.
   * 
   * @param content content to be used
   * 
   * @author Andreas Bröker
   */
  public void setString(String content);
  //----------------------------------------------------------------------------
	/**
	 * Gets the text that is marked by the cursor.
	 * 
	 * @return the text marked
	 * 
	 * @author Sebastian Rösgen
	 */
	public String getString();
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
	public void goLeft(short stepNumber, boolean mark);
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
	public void goRight(short stepNumber, boolean mark);
  //----------------------------------------------------------------------------
  /**
   * Returns start position of the cursor.
   * 
   * @return start position of the cursor
   * 
   * @author Andreas Bröker
   */
  public ITextRange getStart();  
  //----------------------------------------------------------------------------
  /**
   * Returns end position of the cursor.
   * 
   * @return end position of the cursor
   * 
   * @author Markus Krüger
   */
  public ITextRange getEnd();  
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
  public short getStartPageNumber();  
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
  public short getEndPageNumber();  
  //----------------------------------------------------------------------------
  /**
   * Inserts page break at the current cursor position. 
   * 
   * @throws NOAException if the page break can not be set
   * 
   * @author Andreas Bröker
   * @date 19.09.2006
   */
  public void insertPageBreak() throws NOAException; 
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
  public void insertDocument(String url) throws NOAException;  
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
  public void insertDocument(InputStream inputStream,IFilter filter) throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Returns if the current cursor supports word cursor operations.
   * 
   * @return if the current cursor supports word cursor operations
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean supportsWordCursor();  
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
  public boolean isStartOfWord() throws NOAException;  
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
  public boolean isEndOfWord() throws NOAException;   
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
  public boolean gotoNextWord(boolean mark) throws NOAException;   
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
  public boolean gotoPreviousWord(boolean mark) throws NOAException;  
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
  public boolean gotoEndOfWord(boolean mark) throws NOAException; 
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
  public boolean gotoStartOfWord(boolean mark) throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Returns if the current cursor supports sentence cursor operations.
   * 
   * @return if the current cursor supports sentence cursor operations
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean supportsSentenceCursor();  
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
  public boolean isStartOfSentence() throws NOAException;   
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
  public boolean isEndOfSentence() throws NOAException; 
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
  public void gotoNextSentence(boolean mark) throws NOAException;   
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
  public void gotoPreviousSentence(boolean mark) throws NOAException;   
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
  public void gotoEndOfSentence(boolean mark) throws NOAException;   
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
  public void gotoStartOfSentence(boolean mark) throws NOAException;   
  //----------------------------------------------------------------------------
  /**
   * Returns if the current cursor supports paragraph cursor operations.
   * 
   * @return if the current cursor supports paragraph cursor operations
   * 
   * @author Markus Krüger
   * @date 13.12.2006
   */
  public boolean supportsParagraphCursor();  
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
  public boolean isStartOfParagraph() throws NOAException;   
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
  public boolean isEndOfParagraph() throws NOAException;  
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
  public void gotoNextParagraph(boolean mark) throws NOAException;   
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
  public void gotoPreviousParagraph(boolean mark) throws NOAException;  
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
  public void gotoEndOfParagraph(boolean mark) throws NOAException; 
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
  public void gotoStartOfParagraph(boolean mark) throws NOAException; 
  //----------------------------------------------------------------------------

}