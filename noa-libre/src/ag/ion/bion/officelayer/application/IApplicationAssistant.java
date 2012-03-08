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
 * Last changes made by $Author: markus $, $Date: 2010-07-07 13:02:51 +0200 (Mi, 07 Jul 2010) $
 */
package ag.ion.bion.officelayer.application;

import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;

/**
 * Assistant for office applications.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11749 $
 */
public interface IApplicationAssistant {

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about latest available local OpenOffice.org
	 * application, or null if none was found.
	 * 
	 * @return informations about latest available local OpenOffice.org
	 *         application, or null if none was found
	 * 
	 * @author Markus Krüger
	 * @date 04.03.2012
	 */
	public ILazyApplicationInfo getLatestLocalOpenOfficeOrgApplication();

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about latest available local OpenOffice.org
	 * application, or null if none was found.
	 * 
	 * @param officeProgressMonitor
	 *            office progress monitor to be used, can be null
	 * 
	 * @return informations about latest available local OpenOffice.org
	 *         application, or null if none was found
	 * 
	 * @author Markus Krüger
	 * @date 04.03.2012
	 */
	public ILazyApplicationInfo getLatestLocalOpenOfficeOrgApplication(
			IOfficeProgressMonitor officeProgressMonitor);

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about latest available local LibreOffice
	 * application, or null if none was found.
	 * 
	 * @return informations about latest available local LibreOffice
	 *         application, or null if none was found
	 * 
	 * @author Markus Krüger
	 * @date 04.03.2012
	 */
	public ILazyApplicationInfo getLatestLocalLibreOfficeApplication();

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about latest available local LibreOffice
	 * application, or null if none was found.
	 * 
	 * @param officeProgressMonitor
	 *            office progress monitor to be used, can be null
	 * 
	 * @return informations about latest available local LibreOffice
	 *         application, or null if none was found
	 * 
	 * @author Markus Krüger
	 * @date 04.03.2012
	 */
	public ILazyApplicationInfo getLatestLocalLibreOfficeApplication(
			IOfficeProgressMonitor officeProgressMonitor);

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about latest available local office application, or
	 * null if none was found.
	 * 
	 * @return informations about latest available local office application, or
	 *         null if none was found
	 * 
	 * @deprecated As now also LibreOffice is supported, this method is only
	 *             keept for compatibility reasons and returns the same as
	 *             {@link #getLatestLocalOpenOfficeOrgApplication()}
	 * 
	 * @author Markus Krüger
	 * @date 07.07.2010
	 */
	public ILazyApplicationInfo getLatestLocalApplication();

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about latest available local office application, or
	 * null if none was found.
	 * 
	 * @param officeProgressMonitor
	 *            office progress monitor to be used, can be null
	 * 
	 * @return informations about latest available local office application, or
	 *         null if none was found
	 * 
	 * @deprecated As now also LibreOffice is supported, this method is only
	 *             keept for compatibility reasons and returns the same as
	 *             {@link #getLatestLocalOpenOfficeOrgApplication(IOfficeProgressMonitor)
	 *             )}
	 * 
	 * @author Markus Krüger
	 * @date 07.07.2010
	 */
	public ILazyApplicationInfo getLatestLocalApplication(
			IOfficeProgressMonitor officeProgressMonitor);

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about available local office applications.
	 * 
	 * @return informations about available local office applications
	 * 
	 * @author Andreas Bröker
	 */
	public ILazyApplicationInfo[] getLocalApplications();

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about available local office applications.
	 * 
	 * @param officeProgressMonitor
	 *            office progress monitor to be used
	 * 
	 * @return informations about available local office applications
	 * 
	 * @author Andreas Bröker
	 */
	public ILazyApplicationInfo[] getLocalApplications(
			IOfficeProgressMonitor officeProgressMonitor);

	// ----------------------------------------------------------------------------
	/**
	 * Looks for application info on the basis of the submitted application home
	 * path. Returns null if the application info can not be provided.
	 * 
	 * @param home
	 *            home path to be used
	 * 
	 * @return application info on the basis of the submitted application home
	 *         path or null if the application info can not be provided
	 * 
	 * @author Andreas Bröker
	 */
	public ILazyApplicationInfo findLocalApplicationInfo(String home);
	// ----------------------------------------------------------------------------

}