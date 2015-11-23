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
 *  http://www.ion.ag																												*
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.bion.officelayer.util;

/**
 * Checker for internal application states.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 2006/02/05
 */
public class Assert {
	
  //----------------------------------------------------------------------------  
	/**
	 * Internal assertion exception.
	 * 
	 * @author Andreas Bröker
	 * @date 2006/02/05
	 */
	private static class AssertionException extends RuntimeException {

		private static final long serialVersionUID = 3676710571146111660L;

		//----------------------------------------------------------------------------  
		/**
		 * Constructs new AssertionException.
		 * 
		 * @param message message to be used
		 * 
		 * @author Andreas Bröker
		 * @date 2006/02/05
		 */
		public AssertionException(String message) {
			super(message);
		}
	  //----------------------------------------------------------------------------  
		
	}
  //----------------------------------------------------------------------------  
	
  //----------------------------------------------------------------------------  
	/**
	 * Checks the submitted object that it is not null. If the submitted
	 * object is null a type of <code>RuntimeException</code> will be thrown.
	 * 
	 * @param object object to be checked
	 * @param type type of the object
	 * @param requester requester of the check 
	 * 
	 * @author Andreas Bröker
	 * @date 2006/02/05
	 */
	public static void isNotNull(Object object, Class type, Object requester) {
		if(object == null)
			if(type != null && requester != null)
				throw new AssertionException(requester.getClass().getName() + ": The submitted " + type.getName() + " is not valid.");
			else
				throw new AssertionException("The submitted type is not valid.");
	}
  //----------------------------------------------------------------------------  
	
}
