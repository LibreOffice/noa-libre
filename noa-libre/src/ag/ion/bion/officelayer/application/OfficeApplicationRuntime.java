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
package ag.ion.bion.officelayer.application;

import ag.ion.bion.officelayer.internal.application.ApplicationAssistant;
import ag.ion.bion.officelayer.internal.application.LocalOfficeApplication;
import ag.ion.bion.officelayer.internal.application.RemoteOfficeApplication;
import java.util.HashMap;

import java.util.Map;

/**
 * Runtime for OpenOffice.org applications.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class OfficeApplicationRuntime {

    private static IApplicationAssistant applicationAssistant = null;

    //----------------------------------------------------------------------------
    /**
     * Returns remote OpenOffice.org application.
     *
     * @return remote OpenOffice.org application
     *
     * @author Andreas Bröker
     *
     * @deprecated Use getApplication(...) instead.
     */
    public static IOfficeApplication getRemoteOfficeApplication() {
        return new RemoteOfficeApplication(null);
    }
    //----------------------------------------------------------------------------

    /**
     * Returns local OpenOffice.org application.
     *
     * @return local office application
     *
     * @author Andreas Bröker
     *
     * @deprecated Use getApplication(...) instead.
     */
    public static IOfficeApplication getLocalOfficeApplication() {
        return new LocalOfficeApplication(null);
    }
    //----------------------------------------------------------------------------

    /**
     * Returns office application on the basis of the submitted configuration.
     *
     * @param configuration configuration for the office application
     *
     * @return office application on the basis of the submitted configuration
     *
     * @throws OfficeApplicationException if the office application can not be provided
     *
     * @author Andreas Bröker
     */
    public static final IOfficeApplication getApplication(Map configuration) throws OfficeApplicationException {
        if (configuration == null) {
            throw new OfficeApplicationException("The submitted map is not valid.");
        }

        Object type = configuration.get(IOfficeApplication.APPLICATION_TYPE_KEY);
        if (type == null) {
            throw new OfficeApplicationException("The application type is missing.");
        }

        if (type.toString().equals(IOfficeApplication.LOCAL_APPLICATION)) {
            return new LocalOfficeApplication(configuration);
        } else {
            return new RemoteOfficeApplication(configuration);
        }
    }

    /**
     * Returns the default local office application.
     *
     * @return office application
     * @throws OfficeApplicationException if the office application can not be provided
     * @author Andreas Weber
     */
    public static IOfficeApplication getApplication() throws OfficeApplicationException {
        ILazyApplicationInfo appInfo = getApplicationAssistant().getLatestLocalLibreOfficeApplication();
        if (appInfo == null) {
            appInfo = getApplicationAssistant().getLatestLocalOpenOfficeOrgApplication();
        }

        Map configuration = new HashMap();
        configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, appInfo.getHome());
        configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);

        return new LocalOfficeApplication(configuration);
    }

    //----------------------------------------------------------------------------
    /**
     * Returns office application assistant.
     *
     * @param nativeLibPath path to the ICE registry library
     *
     * @return office application assistant
     *
     * @throws OfficeApplicationException if the office application assistant can
     * not be provided
     *
     * @author Andreas Bröker
     */
    public static IApplicationAssistant getApplicationAssistant(String nativeLibPath) throws OfficeApplicationException {
        if (applicationAssistant == null) {
            applicationAssistant = new ApplicationAssistant(nativeLibPath);
        }
        return applicationAssistant;
    }
    //----------------------------------------------------------------------------

    /**
     * Returns office application assistant.
     *
     * @return office application assistant
     *
     * @throws OfficeApplicationException if the office application assistant can
     * not be provided
     *
     * @author Andreas Bröker
     */
    public static IApplicationAssistant getApplicationAssistant() throws OfficeApplicationException {
        if (applicationAssistant == null) {
            applicationAssistant = new ApplicationAssistant(null);
        }
        return applicationAssistant;
    }
    //----------------------------------------------------------------------------
}
