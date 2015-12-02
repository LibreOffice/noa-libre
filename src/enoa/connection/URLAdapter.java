/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                                 *
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU General Public License Version 2.1.              *
 *                                                                          * 
 * GNU General Public License Version 2.1                            *
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
 *  http://www.ion.ag                                                       *
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: andreas $, $Date: 2006/10/04 12:14:26 $
 */
package enoa.connection;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Adapter for document URLs. This adapter can be used
 * in order to build valid document URLs for loading
 * and storing.
 * 
 * @author Andreas Br�ker
 * @version $Revision: 1.1 $
 * @date 25.08.2006
 */ 
public class URLAdapter {
  
  //----------------------------------------------------------------------------
  /**
   * Adapts the submitted URL to a valid OpenOffice.org URL. 
   * 
   * @param url URL to be adapted
   * 
   * @return adapted URL for OpenOffice.org
   * 
   * @throws MalformedURLException if the URL can not be adapted
   * 
   * @author Andreas Br�ker
 * @throws MalformedURLException 
 * @throws UnknownHostException 
   * @date 25.08.2006
   */
  public static String adaptURL(String url) throws MalformedURLException, UnknownHostException {
	  
	  if(url == null)
	      return null;
	  
	  String localhostAddress = null;
	  try {
		  localhostAddress = InetAddress.getLocalHost().getHostAddress();
	  } catch (UnknownHostException ignored) {}
	  
	  // replace backslash with slash
	  url = url.replace('\\', '/' );

	  if(!url.startsWith("file:")) 
		  url = "file:" + url;
	  
	  URL urlObj = new URL(url);
		
	  String path = urlObj.getPath();
	  
	  String host = urlObj.getHost();
	  if(!host.equals("") && host.length() > 1) {
		  if(host.equals("localhost") && path.contains(":"))
			  host = "";
		  else if(host.equals("localhost") && !path.contains(":")) {
			  if(localhostAddress.equals("127.0.0.1"))
				  throw new MalformedURLException(
						  "localhost 127.0.0.1 not allowed - leave host empty");
			  host = localhostAddress;
		  }
		  else if(!host.equals("localhost") && !path.contains(":"))
			  host = InetAddress.getByName(host).getHostAddress();
	  }
	  else host = "";
	  
	  if(!host.equals(""))
		  url = "file://" + host + path;
	  else if(path.startsWith("/"))
		  url = "file://" + path;
	  else
		  url = "file:///" + path;

	  // replace whitespaces
	  url = url.replaceAll(" ", "%20");

	  return url;    
  }
  //----------------------------------------------------------------------------
  
}