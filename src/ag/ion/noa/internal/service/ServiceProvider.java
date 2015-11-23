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
 * Last changes made by $Author: andreas $, $Date: 2006-10-04 14:14:28 +0200 (Mi, 04 Okt 2006) $
 */
package ag.ion.noa.internal.service;

import ag.ion.noa.NOAException;

import ag.ion.noa.service.IServiceProvider;

import ag.ion.bion.officelayer.application.connection.IOfficeConnection;

import ag.ion.bion.officelayer.util.Assert;

/**
 * Provider for servives.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 * @date 15.08.2006
 */ 
public class ServiceProvider implements IServiceProvider {

  private IOfficeConnection officeConnection = null;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new ServiceProvider.
   * 
   * @param officeConnection office connection to be used
   * 
   * @author Andreas Bröker
   * @date 15.08.2006
   */
  public ServiceProvider(IOfficeConnection officeConnection) {
    Assert.isNotNull(officeConnection, IOfficeConnection.class, this);
    this.officeConnection = officeConnection;
  }  
  //----------------------------------------------------------------------------
  /**
   * Returns service with the submitted name. Returns null
   * if the a service with the submitted name is not available.
   * 
   * @param serviceName service name to be used
   * 
   * @return service with the submitted name or null
   * if the a service with the submitted name is not available
   * 
   * @throws NOAException if the service can not be requested
   * 
   * @author Andreas Bröker
   * @date 15.08.2006
   */
  public Object createService(String serviceName) throws NOAException {
    try {
      return officeConnection.getXMultiServiceFactory().createInstance(serviceName);
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Returns service with the submitted name and internal context. Returns null
   * if the a service with the submitted name is not available.
   * 
   * @param serviceName service name to be used
   * 
   * @return service with the submitted name or null
   * if the a service with the submitted name is not available
   * 
   * @throws NOAException if the service can not be requested
   * 
   * @author Andreas Bröker
   * @date 15.08.2006
   */
  public Object createServiceWithContext(String serviceName) throws NOAException {
    try {
      return officeConnection.getXMultiComponentFactory().createInstanceWithContext(serviceName, officeConnection.getXComponentContext());
    }
    catch(Throwable throwable) {
      throw new NOAException(throwable);
    }
  }
  //----------------------------------------------------------------------------
  
}