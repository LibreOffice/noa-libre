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
package ag.ion.noa;

/**
 * Translator for error code messages.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 2006/02/05
 */ 
public class ErrorCodeTranslator {
	
	/** Message for the ERRCODE_IO_ABORT.*/
	public static final String ERRCODE_IO_ABORT_MESSAGE 		= Messages.getString("ErrorCodeTranslator_io_abort"); //$NON-NLS-1$
	/** Code of ERRCODE_IO_ABORT.*/ 
	public static final int ERRCODE_IO_ABORT 			= 283;
	/** Message for the ERRCODE_IO_CANTWRITE.*/
	public static final String ERRCODE_IO_CANTWRITE_MESSAGE = Messages.getString("ErrorCodeTranslator_io_cantwrite"); //$NON-NLS-1$
	/** Code of ERRCODE_IO_CANTWRITE.*/ 
	public static final int ERRCODE_IO_CANTWRITE 	= 3088;
	
  //----------------------------------------------------------------------------
	/**
	 * Returns message for the submitted error code. Returns null
	 * if a message is not available.
	 * 
	 * @param errorCode error code to be used
	 * 
	 * @return message for the submitted error code or null
	 * if a message is not available
	 * 
	 * @author Andreas Bröker
	 * @date 21.02.2006
	 */
	public static String getErrorCodeMessage(int errorCode) {
		if(errorCode == ERRCODE_IO_ABORT)
			return ERRCODE_IO_ABORT_MESSAGE;
		else if(errorCode == ERRCODE_IO_CANTWRITE)
			return ERRCODE_IO_CANTWRITE_MESSAGE;
		return null;
	}
  //----------------------------------------------------------------------------
	
}