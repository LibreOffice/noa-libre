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
package ag.ion.noa.internal.script;

import ag.ion.bion.officelayer.util.Assert;

import ag.ion.noa.NOAException;

import ag.ion.noa.script.IScript;
import ag.ion.noa.script.IScriptProvider;

import com.sun.star.script.provider.XScript;

/**
 * Script of the office scripting framework which can be invoked.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11524 $
 * @date 13.06.2006
 */ 
public class Script implements IScript {	
	
  private String  uri     = null;
	private XScript xScript = null;
	
  //----------------------------------------------------------------------------
	/**
	 * Constructs a Script.
	 * 
   * @param uri the uri to be used
	 * @param xScript OpenOffice.org XScript interface to be used
	 * 
	 * @author Andreas Bröker
	 * @date 13.06.2006
	 */
	public Script(String uri, XScript xScript) {
    Assert.isNotNull(uri, String.class, this);
    Assert.isNotNull(xScript, XScript.class, this);
    this.uri = uri;
    this.xScript = xScript;
	}
  //----------------------------------------------------------------------------
  /**
   * Returns the name of the script.
   * 
   * @return the name of the script
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getName() {
    if(getLanguage().equals(IScriptProvider.TYPE_BASIC)) {
      String[] splitted = uri.split(":");
      splitted = splitted[1].split("\\?");
      splitted = splitted[0].split("\\.");
      return splitted[2];
    }
    String[] splitted = uri.split(":");
    splitted = splitted[1].split("\\?");
    splitted = splitted[0].split("\\.");
    return splitted[1]+"."+splitted[2];
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the module name of the script, or null if not TYPE_BASIC.
   * 
   * @return the module name of the script, or null if not TYPE_BASIC
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getModuleName() {
    if(getLanguage().equals(IScriptProvider.TYPE_BASIC)) {
      String[] splitted = uri.split(":");
      splitted = splitted[1].split("\\?");
      splitted = splitted[0].split("\\.");
      return splitted[1];
    }
    return null;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the library name of the script.
   * 
   * @return the library name of the script
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getLibraryName() {
    String[] splitted = uri.split(":");
    splitted = splitted[1].split("\\?");
    splitted = splitted[0].split("\\.");
    return splitted[0];
  }  
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
  public String getLanguage() {
    String[]  splitted = uri.split("\\?");
    splitted = splitted[1].split("&");
    splitted = splitted[0].split("=");
    return splitted[1];
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns the URI of the script as string.
   * 
   * @return the URI of the script as string
   * 
   * @author Markus Krüger
   * @date 17.07.2007
   */
  public String getURI() {
    return uri;
  }  
  //----------------------------------------------------------------------------
	/**
	 * Returns OpenOffice.org XScript interface.
	 * 
	 * @return OpenOffice.org XScript interface
	 * 
	 * @author Andreas Bröker
	 * @date 14.06.2006
	 */
	public XScript getXScript() {
		return xScript;
	}
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
	public Object invoke(Object[] parameters, short[][] outParameterIndices, Object[][] outParameters) throws NOAException {
		try {
			return xScript.invoke(parameters, outParameterIndices, outParameters);
		}
		catch(Throwable throwable) {
			throw new NOAException(throwable);
		}
	}
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
	public Object invoke() throws NOAException {
		try {
			return xScript.invoke(new Object[0], new short[1][1], new Object[1][1]);
		}
		catch(Throwable throwable) {
			throw new NOAException(throwable);
		}
	}
  //----------------------------------------------------------------------------
	
}