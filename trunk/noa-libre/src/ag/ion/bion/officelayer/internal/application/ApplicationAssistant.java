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
 * Last changes made by $Author: markus $, $Date: 2010-07-20 12:09:08 +0200 (Di, 20 Jul 2010) $
 */
package ag.ion.bion.officelayer.internal.application;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import ag.ion.bion.officelayer.OSHelper;
import ag.ion.bion.officelayer.application.IApplicationAssistant;
import ag.ion.bion.officelayer.application.IApplicationProperties;
import ag.ion.bion.officelayer.application.ILazyApplicationInfo;
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;

import com.ice.jni.registry.NoSuchValueException;
import com.ice.jni.registry.Registry;
import com.ice.jni.registry.RegistryKey;

/**
 * Assistant for office applications.
 * 
 * @author Andreas Bröker
 * @version $Revision: 11760 $
 */
public class ApplicationAssistant implements IApplicationAssistant {

	private static final String KEY_MAIN_PART_OPEN_OFFICE_ORG = "OpenOffice.org"; //$NON-NLS-1$
	private static final String KEY_MAIN_PART_LIBRE_OFFICE = "LibreOffice"; //$NON-NLS-1$

	private static final String OPENOFFICE_ORG_OSX_APP = "OpenOffice.org.app"; //$NON-NLS-1$
	private static final String LIBREOFFICE_OSX_APP = "LibreOffice.app"; //$NON-NLS-1$

	private static final String PRE_PROGRAM_FOLDER_MAC = "Contents"; //$NON-NLS-1$
	private static final String PROGRAM_FOLDER = "program"; //$NON-NLS-1$
	private static final String RELATIVE_BOOTSTRAP = PROGRAM_FOLDER
			+ File.separator + "bootstrap"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String APPLICATION_EXECUTEABLE = "soffice"; //$NON-NLS-1$

	// ----------------------------------------------------------------------------
	/**
	 * Constructs new ApplicationAssistant.
	 * 
	 * @throws OfficeApplicationException
	 *             if the office application assitant can not be constructed
	 * 
	 * @author Andreas Bröker
	 */
	public ApplicationAssistant() throws OfficeApplicationException {
		this(null);
	}

	// ----------------------------------------------------------------------------
	/**
	 * Constructs new ApplicationAssistant.
	 * 
	 * @param nativeLibPath
	 *            path to the ICE registry library
	 * 
	 * @throws OfficeApplicationException
	 *             if the office application assitant can not be constructed
	 * 
	 * @author Andreas Bröker
	 */
	public ApplicationAssistant(String nativeLibPath)
			throws OfficeApplicationException {
		if (OSHelper.IS_WINDOWS) {
			try {
				String libPathFromProps = System
						.getProperty(IOfficeApplication.NOA_NATIVE_LIB_PATH);
				if (libPathFromProps != null) {
					nativeLibPath = libPathFromProps;
				}
				if (nativeLibPath != null) {
					String libName = "ICE_JNIRegistry.dll";
					String folder64bit = "64bit";
					boolean is64Bit = Integer.valueOf(System.getProperties()
							.getProperty("sun.arch.data.model")) == 64;
					if (is64Bit) {
						if (new File(nativeLibPath + "/" + folder64bit + "/"
								+ libName).exists()) {
							nativeLibPath = nativeLibPath + "/" + folder64bit;
						}
					}
					System.load(nativeLibPath + "\\" + libName); //$NON-NLS-1$
				} else
					System.loadLibrary("ICE_JNIRegistry"); //$NON-NLS-1$
			} catch (Throwable throwable) {
				throw new OfficeApplicationException(throwable);
			}
		}
	}

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
	public ILazyApplicationInfo getLatestLocalOpenOfficeOrgApplication() {
		return getLatestLocalOpenOfficeOrgApplication(null);
	}

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
			IOfficeProgressMonitor officeProgressMonitor) {
		ILazyApplicationInfo[] lazyApplicationInfos = getLocalApplications(officeProgressMonitor);
		ILazyApplicationInfo latestLazyApplicationInfo = null;
		if (lazyApplicationInfos.length > 0) {
			// nimm das neuste
			for (int i = 0; i < lazyApplicationInfos.length; i++) {
				ILazyApplicationInfo appInfo = lazyApplicationInfos[i];
				if (appInfo instanceof LazyOpenOfficeOrgApplicationInfo) {
					if (latestLazyApplicationInfo == null) {
						latestLazyApplicationInfo = appInfo;
					} else if (appInfo.getMajorVersion() > latestLazyApplicationInfo
							.getMajorVersion()) {
						latestLazyApplicationInfo = appInfo;
					} else if (appInfo.getMajorVersion() == latestLazyApplicationInfo
							.getMajorVersion()
							&& appInfo.getMinorVersion() > latestLazyApplicationInfo
									.getMinorVersion()) {
						latestLazyApplicationInfo = appInfo;
					} else if (appInfo.getMajorVersion() == latestLazyApplicationInfo
							.getMajorVersion()
							&& appInfo.getMinorVersion() == latestLazyApplicationInfo
									.getMinorVersion()
							&& appInfo.getUpdateVersion() > latestLazyApplicationInfo
									.getUpdateVersion()) {
						latestLazyApplicationInfo = appInfo;
					}
				}
			}
		}
		return latestLazyApplicationInfo;
	}

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
	public ILazyApplicationInfo getLatestLocalLibreOfficeApplication() {
		return getLatestLocalLibreOfficeApplication(null);
	}

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
			IOfficeProgressMonitor officeProgressMonitor) {
		ILazyApplicationInfo[] lazyApplicationInfos = getLocalApplications(officeProgressMonitor);
		ILazyApplicationInfo latestLazyApplicationInfo = null;
		if (lazyApplicationInfos.length > 0) {
			// nimm das neuste
			for (int i = 0; i < lazyApplicationInfos.length; i++) {
				ILazyApplicationInfo appInfo = lazyApplicationInfos[i];
				if (appInfo instanceof LazyLibreOfficeApplicationInfo) {
					if (latestLazyApplicationInfo == null) {
						latestLazyApplicationInfo = appInfo;
					} else if (appInfo.getMajorVersion() > latestLazyApplicationInfo
							.getMajorVersion()) {
						latestLazyApplicationInfo = appInfo;
					} else if (appInfo.getMajorVersion() == latestLazyApplicationInfo
							.getMajorVersion()
							&& appInfo.getMinorVersion() > latestLazyApplicationInfo
									.getMinorVersion()) {
						latestLazyApplicationInfo = appInfo;
					} else if (appInfo.getMajorVersion() == latestLazyApplicationInfo
							.getMajorVersion()
							&& appInfo.getMinorVersion() == latestLazyApplicationInfo
									.getMinorVersion()
							&& appInfo.getUpdateVersion() > latestLazyApplicationInfo
									.getUpdateVersion()) {
						latestLazyApplicationInfo = appInfo;
					}
				}
			}
		}
		return latestLazyApplicationInfo;
	}

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
	public ILazyApplicationInfo getLatestLocalApplication() {
		return getLatestLocalOpenOfficeOrgApplication();
	}

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
			IOfficeProgressMonitor officeProgressMonitor) {
		return getLatestLocalOpenOfficeOrgApplication(officeProgressMonitor);
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns informations about available local office applications.
	 * 
	 * @return informations about available local office applications
	 * 
	 * @author Andreas Bröker
	 */
	public ILazyApplicationInfo[] getLocalApplications() {
		return getLocalApplications(null);
	}

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
	 * @author Markus Krüger
	 */
	public ILazyApplicationInfo[] getLocalApplications(
			IOfficeProgressMonitor officeProgressMonitor) {
		ArrayList arrayList = new ArrayList();
		if (System.getProperty("oo.application.path") != null) {
			String path = new File(System.getProperty("oo.application.path"))
					.getAbsolutePath();
			ILazyApplicationInfo applicationInfo = findLocalApplicationInfo(path);
			if (applicationInfo != null)
				arrayList.add(applicationInfo);
		} else if (OSHelper.IS_WINDOWS) {
			try {
				String[] possibleOpenOfficeOrgKeys = getPossibleKeys(KEY_MAIN_PART_OPEN_OFFICE_ORG);
				String[] possibleLibreOfficeKeys = getPossibleKeys(KEY_MAIN_PART_LIBRE_OFFICE);
				List keys = new ArrayList();
				keys.addAll(Arrays.asList(possibleOpenOfficeOrgKeys));
				keys.addAll(Arrays.asList(possibleLibreOfficeKeys));
				String[] possibleKeys = (String[]) keys.toArray(new String[keys
						.size()]);
				if (officeProgressMonitor != null)
					officeProgressMonitor
							.beginTask(
									Messages.getString("ApplicationAssistant.monitor_message_scannig_registry"), possibleKeys.length); //$NON-NLS-1$
				RegistryKey[] ROOTS = new RegistryKey[] {
						Registry.HKEY_CLASSES_ROOT, Registry.HKEY_CURRENT_USER,
						Registry.HKEY_LOCAL_MACHINE };
				for (int i = 0, n = possibleKeys.length; i < n; i++) {
					if (officeProgressMonitor != null)
						officeProgressMonitor
								.beginSubTask(Messages
										.getString(
												"ApplicationAssistant.monitor_scanning_key", possibleKeys[i])); //$NON-NLS-1$
					for (int j = 0; j < ROOTS.length; j++) {
						RegistryKey registryKey = Registry.openSubkey(ROOTS[j],
								possibleKeys[i], RegistryKey.ACCESS_READ);
						if (registryKey != null) {
							String path = null;
							if (path == null) {
								try {
									path = registryKey.getStringValue("Path");
									path = "\"" + path + "\"";
								} catch (NoSuchValueException noSuchValueException) {
									// ignore
								}
							}
							if (path == null) {
								try {
									path = registryKey.getDefaultValue();
								} catch (NoSuchValueException noSuchValueException) {
									// ignore
								}
							}
							if (path != null) {
								int position = path
										.indexOf(APPLICATION_EXECUTEABLE);
								if (position != -1) {
									path = path.substring(1, position - 9);
									ILazyApplicationInfo applicationInfo = findLocalApplicationInfo(path);
									if (applicationInfo != null) {
										boolean found = false;
										for (Iterator iterator = arrayList
												.iterator(); iterator.hasNext();) {
											ILazyApplicationInfo tmpApplicationInfo = (ILazyApplicationInfo) iterator
													.next();
											if (tmpApplicationInfo.getHome()
													.equalsIgnoreCase(
															applicationInfo
																	.getHome())) {
												found = true;
												break;
											}
										}
										if (!found)
											arrayList.add(applicationInfo);
									}
								}
							}
						}
					}
					if (officeProgressMonitor != null)
						if (officeProgressMonitor.isCanceled())
							break;
				}
			} catch (Throwable throwable) {
				return ILazyApplicationInfo.EMPTY_LAZY_APPLICATION_INFOS_ARRAY;
			}
		} else {
			try {
				if (officeProgressMonitor != null)
					officeProgressMonitor
							.beginTask(
									Messages.getString("ApplicationAssistant.monitor_looking_for_office_application"), IOfficeProgressMonitor.WORK_UNKNOWN); //$NON-NLS-1$

				ArrayList possibleOfficeHomes = new ArrayList();

				if (OSHelper.IS_MAC) {
					File appsFolder = new File("/Applications"); //$NON-NLS-1$
					File oooStandardFolder = new File(appsFolder,
							OPENOFFICE_ORG_OSX_APP);
					File folderToSearch = oooStandardFolder;
					if (!folderToSearch.exists()) {
						folderToSearch = appsFolder;
					}
					findPossibleOfficeHomes(officeProgressMonitor,
							folderToSearch, possibleOfficeHomes, 1, -1);
					File libreStandardFolder = new File(appsFolder,
							LIBREOFFICE_OSX_APP);
					folderToSearch = libreStandardFolder;
					if (!folderToSearch.exists()) {
						folderToSearch = appsFolder;
					}
					findPossibleOfficeHomes(officeProgressMonitor,
							folderToSearch, possibleOfficeHomes, 1, -1);
				} else { // linux, unix
					File file = new File("/opt"); //$NON-NLS-1$
					findPossibleOfficeHomes(officeProgressMonitor, file,
							possibleOfficeHomes, 1, 2);
					file = new File("/usr"); //$NON-NLS-1$
					findPossibleOfficeHomes(officeProgressMonitor, file,
							possibleOfficeHomes, 1, 2);
				}

				if (officeProgressMonitor != null)
					officeProgressMonitor
							.beginSubTask(Messages
									.getString("ApplicationAssistant.monitor_buildung_application_infos")); //$NON-NLS-1$
				String[] officeHomes = (String[]) possibleOfficeHomes
						.toArray(new String[possibleOfficeHomes.size()]);
				for (int i = 0, n = officeHomes.length; i < n; i++) {
					ILazyApplicationInfo applicationInfo = findLocalApplicationInfo(officeHomes[i]);
					if (applicationInfo != null)
						arrayList.add(applicationInfo);
				}
			} catch (Throwable throwable) {
				return ILazyApplicationInfo.EMPTY_LAZY_APPLICATION_INFOS_ARRAY;
			}
		}

		if (officeProgressMonitor != null)
			if (officeProgressMonitor.needsDone())
				officeProgressMonitor.done();

		return (ILazyApplicationInfo[]) arrayList
				.toArray(new ILazyApplicationInfo[arrayList.size()]);
	}

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
	public ILazyApplicationInfo findLocalApplicationInfo(String home) {
		if (home == null)
			return null;

		File file = null;
		if (OSHelper.IS_WINDOWS) {
			file = new File(home + File.separator + PROGRAM_FOLDER
					+ File.separator + APPLICATION_EXECUTEABLE + ".exe"); //$NON-NLS-1$

		} else if (OSHelper.IS_MAC) {
			file = new File(home + File.separator + PRE_PROGRAM_FOLDER_MAC
					+ File.separator + PROGRAM_FOLDER + File.separator
					+ APPLICATION_EXECUTEABLE + ".bin"); //$NON-NLS-1$
		} else {
			file = new File(home + File.separator + PROGRAM_FOLDER
					+ File.separator + APPLICATION_EXECUTEABLE + ".bin"); //$NON-NLS-1$
		}

		if (file.canRead()) {
			if (home.toLowerCase().indexOf("libre") > -1)
				return new LazyLibreOfficeApplicationInfo(home,
						findApplicationProperties(home));
			return new LazyOpenOfficeOrgApplicationInfo(home,
					findApplicationProperties(home));
		}

		// fallback for OpenOffice.org 1.1.x
		if (!OSHelper.IS_WINDOWS) {
			file = new File(home + File.separator + PROGRAM_FOLDER
					+ File.separator + APPLICATION_EXECUTEABLE + ".sh"); //$NON-NLS-1$
			if (file.canRead()) {
				if (home.toLowerCase().indexOf("libre") > -1)
					return new LazyLibreOfficeApplicationInfo(home,
							findApplicationProperties(home));
				return new LazyOpenOfficeOrgApplicationInfo(home,
						findApplicationProperties(home));
			}
		}
		return null;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Looks for possible office home path entries.
	 * 
	 * @param officeProgressMonitor
	 *            office progress monitor to be used (can be null)
	 * @param root
	 *            root file entry to be used
	 * @param list
	 *            list to be filled with possible office home entries
	 * 
	 * @author Andreas Bröker
	 * @author Markus Krüger
	 */
	private void findPossibleOfficeHomes(
			IOfficeProgressMonitor officeProgressMonitor, File root, List list,
			int currentLevel, int maxLevel) {
		if (root == null)
			return;

		if (root.isDirectory())
			if (officeProgressMonitor != null)
				officeProgressMonitor
						.beginSubTask(Messages
								.getString(
										"ApplicationAssistant.monitor_scanning_directory", root.getName())); //$NON-NLS-1$

		File[] files = root.listFiles();
		if (files != null) {
			for (int i = 0, n = files.length; i < n; i++) {
				if (officeProgressMonitor != null)
					if (officeProgressMonitor.isCanceled())
						break;
				File file = files[i];
				if (file != null) {
					String fileName = file.getName();
					boolean homePathIdentified = false;
					if (!OSHelper.IS_WINDOWS) { // linux,unix,mac
						if (fileName.equals(APPLICATION_EXECUTEABLE + ".bin") || fileName.equals(APPLICATION_EXECUTEABLE + ".sh")) //$NON-NLS-1$ //$NON-NLS-2$
							homePathIdentified = true;
					} else {
						if (fileName.equals(APPLICATION_EXECUTEABLE + ".exe")) //$NON-NLS-1$
							homePathIdentified = true;
					}

					if (homePathIdentified) {
						File parent = file.getParentFile();
						if (parent != null) {
							parent = parent.getParentFile();
							if (OSHelper.IS_MAC) { // there is one more folder
													// "Contents", so one more
													// level up
								parent = parent.getParentFile();
							}
							if (parent != null)
								list.add(parent.getAbsolutePath());
						}
					}
					if (file.isDirectory()) {
						File progDir = new File(file, PROGRAM_FOLDER);
						boolean progDirExists = progDir.exists();
						if (progDirExists) {
							findPossibleOfficeHomes(officeProgressMonitor,
									progDir, list, 1, 1);
						} else if (currentLevel < maxLevel || maxLevel == -1) {
							findPossibleOfficeHomes(officeProgressMonitor,
									file, list, currentLevel + 1, maxLevel);
						}
					}
				}
			}
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns application properties on the basis of the submitted office home
	 * path. Returns null if the application properties can not be found.
	 * 
	 * @param home
	 *            home of the office application
	 * 
	 * @return application properties on the basis of the submitted office home
	 *         path or null if the application properties can not be found
	 * 
	 * @author Andreas Bröker
	 */
	private IApplicationProperties findApplicationProperties(String home) {
		File file = null;
		if (OSHelper.IS_WINDOWS) {
			file = new File(home + File.separator + RELATIVE_BOOTSTRAP + ".ini"); //$NON-NLS-1$
		} else if (OSHelper.IS_MAC) {
			file = new File(home + File.separator + PRE_PROGRAM_FOLDER_MAC
					+ File.separator + RELATIVE_BOOTSTRAP + "rc"); //$NON-NLS-1$
		} else {
			// linux,unix
			file = new File(home + File.separator + RELATIVE_BOOTSTRAP + "rc"); //$NON-NLS-1$
		}
		if (file.canRead()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				Properties properties = new Properties();
				properties.load(fileInputStream);
				return new ApplicationProperties(properties);
			} catch (Throwable throwable) {
				return null;
			}
		}
		return null;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns possible windows registry keys with the given key main part.
	 * 
	 * @param keyMainPart
	 *            The key main part to use as keys.
	 * 
	 * @return possible windows registry keys with the given key main part.
	 * 
	 * @author Andreas Bröker
	 * @author Markus Krüger
	 */
	private String[] getPossibleKeys(String keyMainPart) {
		/**
		 * Is this too much ????
		 */
		ArrayList arrayList = new ArrayList();
		int majorVersion = 1;
		int minorVersion = 0;
		int updateVersion = 0;

		for (; majorVersion <= 4; minorVersion = 0, majorVersion++) {
			arrayList
					.add("Applications\\" + keyMainPart + " " + majorVersion + "." + minorVersion + "\\shell\\edit\\command"); //$NON-NLS-1$ //$NON-NLS-2$  //$NON-NLS-4$
			arrayList
					.add("Applications\\" + keyMainPart + " " + majorVersion + "." + minorVersion + "." + updateVersion + "\\shell\\edit\\command"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			arrayList
					.add("Software\\" + keyMainPart + "\\" + keyMainPart + "\\" + majorVersion + "." + minorVersion); //$NON-NLS-1$ //$NON-NLS-2$  //$NON-NLS-4$
			arrayList
					.add("Software\\" + keyMainPart + "\\" + keyMainPart + "\\" + majorVersion + "." + minorVersion + "." + updateVersion); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			for (updateVersion = 1; updateVersion <= 150; updateVersion++) {
				if (updateVersion < 10 || updateVersion > 80) {
					arrayList
							.add("Applications\\" + keyMainPart + " " + majorVersion + "." + minorVersion + "." + updateVersion + "\\shell\\edit\\command"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					arrayList
							.add("Software\\" + keyMainPart + "\\" + keyMainPart + "\\" + majorVersion + "." + minorVersion + "." + updateVersion); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				}
			}

			for (minorVersion = 1, updateVersion = 1; minorVersion <= 10; updateVersion = 1, minorVersion++) {
				arrayList
						.add("Applications\\" + keyMainPart + " " + majorVersion + "." + minorVersion + "\\shell\\edit\\command"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				arrayList
						.add("Applications\\" + keyMainPart + " " + majorVersion + "." + minorVersion + "." + updateVersion + "\\shell\\edit\\command"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				arrayList
						.add("Software\\" + keyMainPart + "\\" + keyMainPart + "\\" + majorVersion + "." + minorVersion); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				arrayList
						.add("Software\\" + keyMainPart + "\\" + keyMainPart + "\\" + majorVersion + "." + minorVersion + "." + updateVersion); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				for (updateVersion = 2; updateVersion <= 150; updateVersion++) {
					if (updateVersion < 10 || updateVersion > 80) {
						arrayList
								.add("Applications\\" + keyMainPart + " " + majorVersion + "." + minorVersion + "." + updateVersion + "\\shell\\edit\\command"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
						arrayList
								.add("Software\\" + keyMainPart + "\\" + keyMainPart + "\\" + majorVersion + "." + minorVersion + "." + updateVersion); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					}
				}
			}
		}
		return (String[]) arrayList.toArray(new String[arrayList.size()]);
	}
	// ----------------------------------------------------------------------------

}