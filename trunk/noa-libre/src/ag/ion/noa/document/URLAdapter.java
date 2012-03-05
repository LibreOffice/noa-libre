/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                                 *
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
 *  http://www.ion.ag                                                       *
 *  http://ubion.ion.ag                                                     *
 *  info@ion.ag                                                             *
 *                                                                          *
 ****************************************************************************/
 
/*
 * Last changes made by $Author: markus $, $Date: 2007-08-14 14:57:19 +0200 (Di, 14 Aug 2007) $
 */
package ag.ion.noa.document;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Adapter for document URLs. This adapter can be used
 * in order to build valid document URLs for loading
 * and storing.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11566 $
 * @date 25.08.2006
 */ 
public class URLAdapter {
  
  private static final String DEFAULT_PROTOCOL  = "file";
  private static final String LOCALHOST         = "localhost";
  private static final String LOCALIP           = "127.0.0.1";
  private static final String PROTOCOL_DEVIDER  = ":";
  private static final String PORT_DEVIDER      = ":";
  private static final String EMPTY_STRING      = "";
  private static final String BLANK_STRING      = " ";
  private static final String BLANK_ENCODED     = "%20";
  private static final char   PATH_DEVIDER_CHAR = '/';
  
  //----------------------------------------------------------------------------
  /**
   * Adapts the submitted URL to a valid OpenOffice.org URL. 
   * 
   * @param url URL to be adapted
   * 
   * @return adapted URL for OpenOffice.org
   * 
   * @throws MalformedURLException if the URL can not be adapted
   * @throws UnknownHostException if the host is unknown
   * 
   * @author Andreas Bröker
   * @author Markus Krüger
   * @author Sebastian Gibelius (contrubution by community)
   * @date 25.08.2006
   */
  public static String adaptURL(String url) throws MalformedURLException, UnknownHostException {
    if(url == null)
      return null;
    
    String localhostAddress = null;
    
    try {
      localhostAddress = InetAddress.getLocalHost().getHostAddress();
    } 
    catch(UnknownHostException unknownHostException) {
      //do not consume
    }
    
    // replace backslash with slash
    url = url.replace('\\',PATH_DEVIDER_CHAR);

    // check for protocol
    URL urlObj = null;
    int port = -1;
    String protocol = DEFAULT_PROTOCOL;
    try {
      urlObj = new URL(url);
      String tmpProtocol = urlObj.getProtocol();
      port = urlObj.getPort();
      if(port < 0)
        port = urlObj.getDefaultPort();
      if(tmpProtocol != null && tmpProtocol.length() > 0)
        protocol = tmpProtocol;
    } 
    catch(MalformedURLException malformedURLException) {
      //do not consume
    }
  
    if(urlObj == null) {
      if(!url.startsWith(protocol+PROTOCOL_DEVIDER))
        url = protocol+PROTOCOL_DEVIDER+url;
      urlObj = new URL(url);
    }
    
    String path = urlObj.getPath();
    
    String host = urlObj.getHost();
    if(!host.equals(EMPTY_STRING) && host.length() > 1) {
      if(host.equals(LOCALHOST) && path.contains(PROTOCOL_DEVIDER))
        host = EMPTY_STRING;
      else if(host.equals(LOCALHOST) && !path.contains(PROTOCOL_DEVIDER)) {
        if(localhostAddress.equals(LOCALIP))
          throw new MalformedURLException(
              LOCALHOST+" "+LOCALIP+" not allowed - leave host empty");
        host = localhostAddress;
      }
      else if(!host.equals(LOCALHOST) && !path.contains(PROTOCOL_DEVIDER))
        host = InetAddress.getByName(host).getHostAddress();
    }
    else 
      host = EMPTY_STRING;
    
    String portString = EMPTY_STRING;
    if(port > 0)
      portString = PORT_DEVIDER+String.valueOf(port);
    if(!host.equals(EMPTY_STRING))
      url = protocol+PROTOCOL_DEVIDER+PATH_DEVIDER_CHAR+PATH_DEVIDER_CHAR+ host + portString + path;
    else if(path.startsWith(String.valueOf(PATH_DEVIDER_CHAR)))
      url = protocol+PROTOCOL_DEVIDER+PATH_DEVIDER_CHAR+PATH_DEVIDER_CHAR+ path;
    else
      url = protocol+PROTOCOL_DEVIDER+PATH_DEVIDER_CHAR+PATH_DEVIDER_CHAR+PATH_DEVIDER_CHAR + path;
  
    // replace whitespaces
    url = url.replaceAll(BLANK_STRING, BLANK_ENCODED);
  
    return url;
  }
  //----------------------------------------------------------------------------
  
}