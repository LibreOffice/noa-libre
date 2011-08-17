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

import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.TextException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Management of text tables.
 * 
 * @author Miriam Sutter
 * @version $Revision: 10398 $
 */
public class ETextTableManagement {
	
	ArrayList textTablesByIndex = new ArrayList();
	HashMap		textTablesByName	= new HashMap();
	
  //----------------------------------------------------------------------------
	/**
	 * Addes a text table.
	 * 
	 * @param textTable text table.
	 * 
	 * @author Miriam Sutter
	 */
	public void addTextTable(ITextTable textTable) {
		textTablesByIndex.add(textTable);
		textTablesByName.put(textTable.getName(),textTable);
	}
  //----------------------------------------------------------------------------
	/**
	 * Addes a text table.
	 * 
	 * @param index index of the new table
	 * @param textTable text table.
	 * 
	 * @author Miriam Sutter
	 */
	public void addTextTable(int index,ITextTable textTable) {
		textTablesByIndex.add(index,textTable);
		textTablesByName.put(textTable.getName(),textTable);
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns all text tables.
	 * 
	 * @return all text tables
	 * 
	 * @author Miriam Sutter
	 */
	public ITextTable[] getTextTables() {
		ITextTable[] textTableArray = new ITextTable[textTablesByIndex.size()];
		textTableArray = (ITextTable[])textTablesByIndex.toArray(textTableArray);
		return textTableArray;
	}
  //----------------------------------------------------------------------------
	/**
	 * Returns a specified text table.
	 * 
	 * @param index the index of the text table
	 * 
	 * @return the text table
	 * 
	 * @author Miriam Sutter
	 */
	public ITextTable getTextTable(int index) {
		return (ITextTable)textTablesByIndex.get(index);
	}  
  //----------------------------------------------------------------------------
	/**
	 * Returns a specified text table.
	 * 
	 * @param name the name of the text table
	 * 
	 * @return the text table
	 * 
	 * @author Miriam Sutter
	 */
	public ITextTable getTextTable(String name) {
		return (ITextTable)textTablesByName.get(name);
	}  
  //----------------------------------------------------------------------------
	/**
	 * Returns a specified text table.
	 * 
	 * @param textTable text table
	 * 
	 * @return the index of a text table
	 * 
	 * @author Miriam Sutter
	 */
	public int getTextTableIndex(ITextTable textTable) {
		return textTablesByIndex.indexOf(textTablesByName.get(textTable.getName()));
	}  
	//----------------------------------------------------------------------------
	/**
	 * Returns the first text table.
	 * 
	 * @return first text table
	 * 
	 * @author Miriam Sutter
	 */
	public ITextTable getFirstTextTable() {
		if(textTablesByIndex.size()==0) {
			return null;
		}
		else {
			return (ITextTable)textTablesByIndex.get(0);
		}
	}
	//----------------------------------------------------------------------------
	/**
	 * Returns the last text table.
	 * 
	 * @return last text table
	 * 
	 * @author Miriam Sutter
	 */
	public ITextTable getLastTextTable() {
		if(textTablesByIndex.size()==0) {
			return null;
		}
		else {
			return (ITextTable)textTablesByIndex.get(textTablesByIndex.size()-1);
		}
	}
	//----------------------------------------------------------------------------
	/**
	 * Removes a specified text table.
	 * 
	 * @param index index of the table
	 * 
	 * @throws TextException if the table could not be removed
	 * 
	 * @author Miriam Sutter
	 */
	public void removeTextTable(int index, ITextDocument textDocument) throws TextException{
		ITextTable textTable = (ITextTable)textTablesByIndex.get(index);
		textTable.remove();
		textTablesByIndex.remove(index);
		textTablesByName.remove(textTable.getName());
	}
	//----------------------------------------------------------------------------
	/**
	 * Removes all text tables.
	 *
	 * @author Miriam Sutter
	 */
	public void removeTextTables() {
		textTablesByIndex = new ArrayList();
		textTablesByName = new HashMap();
	}
	//----------------------------------------------------------------------------	
}
