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
 * Last changes made by $Author: markus $, $Date: 2008-11-18 11:22:42 +0100 (Di, 18 Nov 2008) $
 */
package ag.ion.bion.officelayer.internal.application;

import ag.ion.bion.officelayer.application.IApplicationProperties;
import ag.ion.bion.officelayer.application.ILazyApplicationInfo;

/**
 * Abstract information provider of an office application.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11681 $
 */
public abstract class AbstractLazyApplicationInfo implements
		ILazyApplicationInfo {

	private IApplicationProperties applicationProperties = null;

	private String home = null;
	private String productNameInProperties = null;

	private int majorVersion = -1;
	private int minorVersion = -1;
	private int updateVersion = -1;

	// ----------------------------------------------------------------------------
	/**
	 * Constructs new AbstractLazyApplicationInfo.
	 * 
	 * @param home
	 *            home of the office application to be used
	 * @param applicationProperties
	 *            application properties to be used
	 * 
	 * @throws IllegalArgumentException
	 *             if the submitted home path is not valid
	 * 
	 * @author Andreas Bröker
	 * @author Markus Krüger
	 */
	public AbstractLazyApplicationInfo(String home,
			IApplicationProperties applicationProperties,
			String productNameInProperties) throws IllegalArgumentException {
		if (home == null)
			throw new IllegalArgumentException(
					"The submitted home path is not valid.");
		if (productNameInProperties == null)
			throw new IllegalArgumentException(
					"The submitted product name is not valid.");
		this.home = home;
		this.applicationProperties = applicationProperties;
		this.productNameInProperties = productNameInProperties;
		if (applicationProperties != null)
			initVersion(applicationProperties);
	}

	// ----------------------------------------------------------------------------
	/**
	 * Sets version of the office application.
	 * 
	 * @param major
	 *            major version to be used
	 * @param minor
	 *            minor version to be used
	 * @param update
	 *            update version to be used
	 * 
	 * @author Andreas Bröker
	 */
	public void setVersion(int major, int minor, int update) {
		majorVersion = major;
		minorVersion = minor;
		updateVersion = update;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns home of the office application.
	 * 
	 * @return home of the office application
	 * 
	 * @author Andreas Bröker
	 */
	public String getHome() {
		return home;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns properties of the office application. The properties will be
	 * invetigated from the bootstrap.ini file. Returns null if the properties
	 * are not available.
	 * 
	 * @return properties of the office application or null if the properties
	 *         are not available
	 * 
	 * @author Andreas Bröker
	 */
	public IApplicationProperties getProperties() {
		return applicationProperties;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns major version of the office application. Returns <code>-1</code>
	 * if the major version is not available.
	 * 
	 * @return major version of the office application or <code>-1</code> if the
	 *         major version is not available
	 * 
	 * @author Andreas Bröker
	 */
	public int getMajorVersion() {
		return majorVersion;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns minor version of the office application. Returns <code>-1</code>
	 * if the minor version is not available.
	 * 
	 * @return minor version of the office application or <code>-1</code> if the
	 *         minor version is not available
	 * 
	 * @author Andreas Bröker
	 */
	public int getMinorVersion() {
		return minorVersion;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns update version of the office application. Returns <code>-1</code>
	 * if the update version is not available.
	 * 
	 * @return update version of the office application or <code>-1</code> if
	 *         the update version is not available
	 * 
	 * @author Andreas Bröker
	 */
	public int getUpdateVersion() {
		return updateVersion;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Inits version number.
	 * 
	 * @param applicationProperties
	 *            application properties to be used
	 * 
	 * @author Andreas Bröker
	 */
	private void initVersion(IApplicationProperties applicationProperties) {
		String productKey = applicationProperties
				.getPropertyValue(IApplicationProperties.PRODUCT_KEY_PROPERTY);
		if (productKey != null) {
			String version = productKey.substring(
					productNameInProperties.length()).trim();
			String[] versionParts = version.split("\\.");
			int majorVersion = 0;
			int minorVersion = 0;
			int updateVersion = 0;
			for (int i = 0, n = versionParts.length; i < n; i++) {
				int number = -1;
				try {
					number = Integer.parseInt(versionParts[i]);
				} catch (Throwable throwable) {
					// do not consume
				}
				if (i == 0)
					majorVersion = number;
				else if (i == 1)
					minorVersion = number;
				else if (i == 2)
					updateVersion = number;
			}
			setVersion(majorVersion, minorVersion, updateVersion);
		}
	}
	// ----------------------------------------------------------------------------

}