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
 * Last changes made by $Author: markus $, $Date: 2007-07-18 13:35:08 +0200 (Mi, 18 Jul 2007) $
 */
package ag.ion.noa.script;

import com.sun.star.script.provider.XScript;

import ag.ion.noa.NOAException;

/**
 * Script of the office scripting framework which can be invoked.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11524 $
 * @date 13.06.2006
 */ 
public interface IScript {
	
	/** Empty array of scripts. */
	public static final IScript[] EMPTY_ARRAY = new IScript[0];
	
  //----------------------------------------------------------------------------
  /**
   * Returns the name of the script.
   * 
   * @return the name of the script
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getName();  
  //----------------------------------------------------------------------------
  /**
   * Returns the module name of the script, or null if not TYPE_BASIC.
   * 
   * @return the module name of the script, or null if not TYPE_BASIC
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getModuleName();  
  //----------------------------------------------------------------------------
  /**
   * Returns the library name of the script.
   * 
   * @return the library name of the script
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getLibraryName();  
  //----------------------------------------------------------------------------
  /**
   * Returns the language of the script.
   * 
   * @see IScriptProvider#TYPE_BASIC
   * @see IScriptProvider#TYPE_BEAN_SHELL
   * @see IScriptProvider#TYPE_JAVA
   * @see IScriptProvider#TYPE_JAVA_SCRIPT
   * @see IScriptProvider#TYPE_PYTHON
   * 
   * @return the language of the script
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getLanguage();  
  //----------------------------------------------------------------------------
  /**
   * Returns the URI of the script as string.
   * 
   * @return the URI of the script as string
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getURI();  
  //----------------------------------------------------------------------------
	/**
	 * Returns OpenOffice.org XScript interface.
	 * 
	 * @return OpenOffice.org XScript interface
	 * 
	 * @author Andreas Bröker
	 * @date 14.06.2006
	 */
	public XScript getXScript();	
  //----------------------------------------------------------------------------
	/**
	 * Invokes the script.
	 * 
	 * @param parameters parameters to be used for script invocation
	 * @param outParameterIndices indices of output related parameters within the parameters
	 * @param outParameters storage for out parameters
	 * 
	 * @return output of the script
	 * 
	 * @throws NOAException if the script can not be invoked
	 * 
	 * @author Andreas Bröker
	 * @date 13.06.2006
	 */
	public Object invoke(Object[] parameters, short[][] outParameterIndices, Object[][] outParameters) throws NOAException;
  //----------------------------------------------------------------------------
	/**
	 * Invokes the script without parameters.
	 * 
	 * @return output of the script
	 * 
	 * @throws NOAException if the script can not be invoked
	 * 
	 * @author Andreas Bröker
	 * @date 13.06.2006
	 */
	public Object invoke() throws NOAException;	
  //----------------------------------------------------------------------------
	
}