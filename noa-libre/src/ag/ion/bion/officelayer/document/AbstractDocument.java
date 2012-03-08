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
 * Last changes made by $Author: markus $, $Date: 2008-08-18 10:29:46 +0200 (Mo, 18 Aug 2008) $
 */
package ag.ion.bion.officelayer.document;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ag.ion.bion.officelayer.desktop.IFrame;
import ag.ion.bion.officelayer.event.ICloseListener;
import ag.ion.bion.officelayer.event.IDocumentEvent;
import ag.ion.bion.officelayer.event.IDocumentListener;
import ag.ion.bion.officelayer.event.IDocumentModifyListener;
import ag.ion.bion.officelayer.event.IEvent;
import ag.ion.bion.officelayer.form.IFormService;
import ag.ion.bion.officelayer.internal.desktop.Frame;
import ag.ion.bion.officelayer.internal.document.PersistenceService;
import ag.ion.bion.officelayer.internal.event.CloseListenerWrapper;
import ag.ion.bion.officelayer.internal.event.DocumentEvent;
import ag.ion.bion.officelayer.internal.event.DocumentListenerWrapper;
import ag.ion.bion.officelayer.internal.event.DocumentModifyListenerWrapper;
import ag.ion.bion.officelayer.internal.form.FormService;
import ag.ion.noa.NOAException;
import ag.ion.noa.document.IFilterProvider;
import ag.ion.noa.internal.document.DefaultFilterProvider;
import ag.ion.noa.internal.printing.PrintService;
import ag.ion.noa.internal.script.ScriptingService;
import ag.ion.noa.printing.IPrintService;
import ag.ion.noa.script.IScriptingService;
import ag.ion.noa.service.IServiceProvider;
import ag.ion.noa.text.IXInterfaceObjectSelection;
import ag.ion.noa.view.ISelection;

import com.sun.star.beans.PropertyValue;
import com.sun.star.document.EventObject;
import com.sun.star.document.XEventBroadcaster;
import com.sun.star.document.XEventListener;
import com.sun.star.drawing.XDrawPage;
import com.sun.star.drawing.XDrawPageSupplier;
import com.sun.star.frame.XController;
import com.sun.star.frame.XFrame;
import com.sun.star.frame.XModel;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XCloseable;
import com.sun.star.util.XModifiable;
import com.sun.star.util.XModifyBroadcaster;
import com.sun.star.util.XModifyListener;
import com.sun.star.view.XSelectionSupplier;

/**
 * OpenOffice.org base document implementation.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11636 $
 */
public abstract class AbstractDocument implements IDocument {

	protected XComponent xComponent = null;
	private PropertyValue[] initialProperties = null;
	private IServiceProvider serviceProvider = null;
	private IPersistenceService persistenceService = null;
	private DocumentListenerWrapper documentListenerWrapper = null;
	private IScriptingService scriptingService = null;
	private IFilterProvider filterProvider = null;
	private IPrintService printService = null;
	private List documentListenerList = null;
	private Hashtable modifyListenerTable = null;
	private Hashtable closeListeners = null;
	private boolean isModified = false;

	// ----------------------------------------------------------------------------
	/**
	 * Internal document listener.
	 * 
	 * @author Andreas Bröker
	 */
	private class DocumentListener implements IDocumentListener {

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnNew document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onNew(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onNew(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnLoad document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onLoad(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onLoad(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnLoadDone document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onLoadDone(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onLoadDone(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnLoadFinished document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onLoadFinished(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onLoadFinished(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called before a document if going to be saved.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onSave(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onSave(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnSaveDone document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onSaveDone(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onSaveDone(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnSaveFinished document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onSaveFinished(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onSaveFinished(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnSaveAs document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onSaveAs(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onSaveAs(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnSaveAsDone document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onSaveAsDone(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onSaveAsDone(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnModifyChanged document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onModifyChanged(IDocumentEvent documentEvent) {
			isModified = !isModified;
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onModifyChanged(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnMouseOver document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onMouseOver(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onMouseOver(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnMouseOut document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onMouseOut(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onMouseOut(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnFocus document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onFocus(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onFocus(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnAlphaCharInput document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onAlphaCharInput(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onAlphaCharInput(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnNonAlphaCharInput document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onNonAlphaCharInput(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onNonAlphaCharInput(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnInsertStart document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onInsertStart(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onInsertStart(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnInsertDone document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onInsertDone(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onInsertDone(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called whenever a OnUnload document event occurs.
		 * 
		 * @param documentEvent
		 *            source of the event
		 * 
		 * @author Andreas Bröker
		 */
		public void onUnload(IDocumentEvent documentEvent) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.onUnload(documentEvent);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
			}
		}

		// ----------------------------------------------------------------------------
		/**
		 * Is called when the broadcaster is about to be disposed.
		 * 
		 * @param event
		 *            source event
		 * 
		 * @author Andreas Bröker
		 */
		public void disposing(IEvent event) {
			if (documentListenerList != null) {
				for (int i = 0, n = documentListenerList.size(); i < n; i++) {
					try {
						((IDocumentListener) documentListenerList.get(i))
								.disposing(event);
					} catch (java.lang.Exception exception) {
						// do not consume
					}
				}
				documentListenerList = null;
			}
		}
		// ----------------------------------------------------------------------------
	}

	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	/**
	 * Constructs new OpenOffice.org document.
	 * 
	 * @param xComponent
	 *            OpenOffice.org XComponent interface
	 * @param intitialProperties
	 *            the properties that were used loading the document
	 * 
	 * @throws IllegalArgumentException
	 *             if the submitted OpenOffice.org XComponent interface is not
	 *             valid
	 * 
	 * @author Andreas Bröker
	 */
	public AbstractDocument(XComponent xComponent,
			PropertyValue[] initialProperties) throws IllegalArgumentException {
		if (xComponent == null) {
			throw new IllegalArgumentException(
					"The submitted OpenOffice.org XComponent interface is not valid.");
		}
		this.xComponent = xComponent;
		this.initialProperties = initialProperties;

		documentListenerWrapper = new DocumentListenerWrapper(
				new DocumentListener(), getServiceProvider());
		addEventListener(documentListenerWrapper);
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns OpenOffice.org XComponent interface. This method is not part of
	 * the public API.
	 * 
	 * @return OpenOffice.org XComponent interface
	 * 
	 * @author Andreas Bröker
	 */
	public XComponent getXComponent() {
		return xComponent;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns OpenOffice.org XFrame interface.
	 * 
	 * @return OpenOffice.org XFrame interface
	 * 
	 * @author Markus Krüger
	 * @date 01.08.2007
	 */
	public XFrame getXFrame() {
		XModel xModel = (XModel) UnoRuntime.queryInterface(XModel.class,
				getXComponent());
		XController xController = xModel.getCurrentController();
		return xController.getFrame();
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns Frame of the document.
	 * 
	 * @return Frame of the document
	 * 
	 * @author Markus Krüger
	 * @date 01.08.2007
	 */
	public IFrame getFrame() {
		XModel xModel = (XModel) UnoRuntime.queryInterface(XModel.class,
				getXComponent());
		XController xController = xModel.getCurrentController();
		XFrame xFrame = xController.getFrame();
		return new Frame(xFrame, getServiceProvider());
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns persistence service.
	 * 
	 * @return persistence service
	 * 
	 * @author Andreas Bröker
	 */
	public IPersistenceService getPersistenceService() {
		if (persistenceService == null) {
			XStorable xStorable = (XStorable) UnoRuntime.queryInterface(
					XStorable.class, xComponent);
			persistenceService = new PersistenceService(this, xStorable);
		}
		return persistenceService;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns scripting service of the document.
	 * 
	 * @return scripting service of the document
	 * 
	 * @author Andreas Bröker
	 * @date 13.06.2006
	 */
	public IScriptingService getScriptingService() {
		if (scriptingService == null) {
			scriptingService = new ScriptingService(this);
		}
		return scriptingService;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns filter provider.
	 * 
	 * @return filter provider
	 * 
	 * @author Andreas Bröker
	 * @date 14.07.2006
	 */
	public IFilterProvider getFilterProvider() {
		if (filterProvider == null) {
			filterProvider = new DefaultFilterProvider(this);
		}
		return filterProvider;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns form service, or null if not available.
	 * 
	 * @return form service, or null
	 * 
	 * @author Markus Krüger
	 * @date 25.01.2007
	 */
	public IFormService getFormService() {
		XDrawPageSupplier drawPageSupplier = ((XDrawPageSupplier) UnoRuntime
				.queryInterface(XDrawPageSupplier.class, xComponent));
		if (drawPageSupplier != null) {
			XDrawPage drawPage = drawPageSupplier.getDrawPage();
			if (drawPage != null) {
				return new FormService(this, drawPage);
			}
		}
		return null;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Adds new document modify listener.
	 * 
	 * @param documentModifyListener
	 *            new document modify listener
	 * 
	 * @author Sebastian Rösgen
	 */
	public void addDocumentModifyListener(
			IDocumentModifyListener documentModifyListener) {
		DocumentModifyListenerWrapper documentListenerWrapper =

		new DocumentModifyListenerWrapper(documentModifyListener,
				getServiceProvider());
		addModifyListener(documentListenerWrapper);
		if (modifyListenerTable == null) {
			modifyListenerTable = new Hashtable();
		}
		modifyListenerTable
				.put(documentModifyListener, documentListenerWrapper);
	}

	// ----------------------------------------------------------------------------
	/**
	 * Adds new document listener.
	 * 
	 * @param documentListener
	 *            new document listener
	 * 
	 * @author Andreas Bröker
	 */
	public void addDocumentListener(IDocumentListener documentListener) {
		if (documentListener == null) {
			return;
		}

		if (documentListenerList == null) {
			documentListenerList = new ArrayList();
		}

		if (!documentListenerList.contains(documentListener)) {
			documentListenerList.add(documentListener);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * removes specified document modify listener.
	 * 
	 * @param documentModifyListener
	 *            the modif listener to be removed
	 * 
	 * @author Sebastian Rösgen
	 * @author Markus Krüger
	 */
	public void removeDocumentModifyListener(
			IDocumentModifyListener documentModifyListener) {
		if (modifyListenerTable != null) {
			if (modifyListenerTable.containsKey(documentModifyListener)) {
				DocumentModifyListenerWrapper documentModifyListenerWrapper = (DocumentModifyListenerWrapper) modifyListenerTable
						.get(documentModifyListener);
				if (documentModifyListenerWrapper != null) {
					removeModifyListener(documentModifyListenerWrapper);
				}
			}
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Removes document listener.
	 * 
	 * @param documentListener
	 *            document listener
	 * 
	 * @author Andreas Bröker
	 * @author Markus Krüger
	 */
	public void removeDocumentListener(IDocumentListener documentListener) {
		if (documentListener == null) {
			return;
		}

		if (documentListenerList == null) {
			return;
		}

		if (documentListenerList.contains(documentListener)) {
			documentListenerList.remove(documentListener);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns information whether the document was modified.
	 * 
	 * @return information whether the document was modified
	 * 
	 * @author Andreas Bröker
	 */
	public boolean isModified() {
		// XModifiable xModifiable =
		// (XModifiable)UnoRuntime.queryInterface(XModifiable.class,
		// xComponent);
		// if(xModifiable != null) {
		// return xModifiable.isModified();
		// }
		// else {
		// return false;
		// }
		/**
		 * We do not use the XModifiable interface because it can cause a
		 * deadlock in Multi-Thread Enviroments.
		 */
		return isModified;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Sets modified flag.
	 * 
	 * @param modified
	 *            flag value
	 * 
	 * @throws DocumentException
	 *             if the status of the document can not be set
	 * 
	 * @author Andreas Bröker
	 */
	public void setModified(boolean modified) throws DocumentException {
		try {
			XModifiable xModifiable = (XModifiable) UnoRuntime.queryInterface(
					XModifiable.class, xComponent);
			xModifiable.setModified(modified);
		} catch (Throwable throwable) {
			throw new DocumentException(throwable);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns location of the document. Returns null if the URL is not
	 * available.
	 * 
	 * @return location of the document
	 * 
	 * @throws DocumentException
	 *             if the URL is not valid
	 * 
	 * @deprecated Use the IPersistenceService instead.
	 * 
	 * @author Andreas Bröker
	 */
	public URL getLocationURL() throws DocumentException {
		XModel xModel = (XModel) UnoRuntime.queryInterface(XModel.class,
				xComponent);
		String documentURL = xModel.getURL();
		if (documentURL == null) {
			return null;
		}
		try {
			URL url = new URL(documentURL);
			return url;
		} catch (Throwable throwable) {
			throw new DocumentException(throwable);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Adds listener for closing events to the document.
	 * 
	 * @param closeListener
	 *            close listener
	 * 
	 *            Andreas Bröker
	 */
	public void addCloseListener(ICloseListener closeListener) {
		if (closeListener == null) {
			return;
		}

		if (closeListeners == null) {
			closeListeners = new Hashtable();
		}

		XCloseable xCloseable = (XCloseable) UnoRuntime.queryInterface(
				XCloseable.class, xComponent);
		if (xCloseable != null) {
			CloseListenerWrapper closeListenerWrapper = new CloseListenerWrapper(
					closeListener, getServiceProvider());
			xCloseable.addCloseListener(closeListenerWrapper);
			closeListeners.put(closeListener, closeListenerWrapper);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Removes listener for closing events to the document.
	 * 
	 * @param closeListener
	 *            close listener
	 * 
	 * @author Andreas Bröker
	 * @author Markus Krüger
	 */
	public void removeCloseListener(ICloseListener closeListener) {
		if (closeListener == null) {
			return;
		}

		if (closeListeners == null) {
			return;
		}

		if (closeListeners.containsKey(closeListener)) {
			CloseListenerWrapper closeListenerWrapper = (CloseListenerWrapper) closeListeners
					.get(closeListener);
			if (closeListenerWrapper != null) {
				XCloseable xCloseable = (XCloseable) UnoRuntime.queryInterface(
						XCloseable.class, xComponent);
				if (xCloseable != null) {
					xCloseable.removeCloseListener(closeListenerWrapper);
				}
			}
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns information wheter the document is open.
	 * 
	 * @return information wheter the document is open
	 * 
	 * @author Andreas Bröker
	 */
	public boolean isOpen() {
		if (xComponent != null) {
			try {
				XModel xModel = (XModel) UnoRuntime.queryInterface(
						XModel.class, xComponent);
				if (xModel != null) {
					xModel.getURL();
					return true;
				} else {
					return false;
				}
			} catch (Exception exception) {
				return false;
			}
		} else {
			return false;
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Closes the document.
	 * 
	 * @author Andreas Bröker
	 * @author Markus Krüger
	 */
	public void close() {
		long millis = 50l;
		/*
		 * geht nur für Writer und nicht für alle anderen.... XRefreshable
		 * refresh = null; refresh =
		 * (XRefreshable)UnoRuntime.queryInterface(XRefreshable.class,
		 * xComponent); refresh.refresh();
		 */
		removeDocumentListeners();
		removeModifyListeners();

		if (xComponent != null) {
			// System.gc();
			try {
				Thread.sleep(millis);
				// Check supported functionality of the document (model or
				// controller).

				com.sun.star.frame.XModel xModel = (com.sun.star.frame.XModel) UnoRuntime
						.queryInterface(com.sun.star.frame.XModel.class,
								xComponent);
				if (xModel != null) {
					// It's a full featured office document.
					// Reset the modify state of it and close it.
					// Note: Model can disgree by throwing a veto exception.
					Thread.sleep(millis);
					com.sun.star.util.XModifiable xModify = (com.sun.star.util.XModifiable) UnoRuntime
							.queryInterface(
									com.sun.star.util.XModifiable.class, xModel);
					Thread.sleep(millis);
					if (xModify.isModified()) {
						xModify.setModified(false);
					}
					xComponent.dispose();
					Thread.sleep(millis);
				} else {
					// It's a document which supports a controller .. or may by
					// a pure
					// window only. If it's at least a controller - we can try
					// to
					// suspend him. But - he can disagree with that!
					com.sun.star.frame.XController xController = (com.sun.star.frame.XController) UnoRuntime
							.queryInterface(
									com.sun.star.frame.XController.class,
									xComponent);
					Thread.sleep(millis);
					if (xController != null) {
						if (xController.suspend(true) == true) {
							// Note: Don't dispose the controller - destroy the
							// frame
							// to make it right!
							com.sun.star.frame.XFrame xFrame = xController
									.getFrame();
							Thread.sleep(millis);
							xFrame.dispose();
						}
					}
				}
			} catch (com.sun.star.beans.PropertyVetoException exVeto) {
				// Can be thrown by "setModified()" call on model.
				// He disagree with our request.
				// But there is nothing to do then. Following "dispose()" call
				// wasn't
				// never called (because we catch it before). Closing failed
				// -that's it.
				// TODO handel veto exception
			} catch (com.sun.star.lang.DisposedException exDisposed) {
				// If an UNO object was already disposed before - he throw this
				// special
				// runtime exception. Of course every UNO call must be look for
				// that -
				// but it's a question of error handling.
				// For demonstration this exception is handled here.
				// TODO handel veto exception
			} catch (com.sun.star.uno.RuntimeException exRuntime) {
				// Every uno call can throw that.
				// Do nothing - closing failed - that's it.
				// TODO handel veto exception
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			removeCloseListeners();
		} catch (Exception ex) {
			System.out.println("Exception in 'removeCloseListener()' geworfen");
		}

		// System.out.println("removeCloseLiseners ok");
	}

	// ----------------------------------------------------------------------------
	/**
	 * Adds a new modify listener to the document.
	 * 
	 * @param xModifyListener
	 * 
	 * @author Sebastian Rösgen
	 */
	private void addModifyListener(XModifyListener xModifyListener) {
		XModifyBroadcaster xModifyBroadcaster = (XModifyBroadcaster) UnoRuntime
				.queryInterface(XModifyBroadcaster.class, xComponent);
		if (xModifyBroadcaster != null) {
			xModifyBroadcaster.addModifyListener(xModifyListener);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Adds a new modify listener to the document.
	 * 
	 * @param modifyListener
	 *            event listener to be added
	 * 
	 * @author Sebastian Rösgen
	 */
	private void removeModifyListener(XModifyListener modifyListener) {
		XModifyBroadcaster xModifyBroadcaster = (XModifyBroadcaster) UnoRuntime
				.queryInterface(XModifyBroadcaster.class, xComponent);
		if (xModifyBroadcaster != null) {
			if (modifyListener != null) {
				xModifyBroadcaster.removeModifyListener(modifyListener);
			}
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Adds event listener.
	 * 
	 * @param xEventListener
	 *            event listener to be added
	 * 
	 * @author Andreas Bröker
	 */
	private void addEventListener(XEventListener xEventListener) {
		XEventBroadcaster xEventBroadcaster = (XEventBroadcaster) UnoRuntime
				.queryInterface(XEventBroadcaster.class, xComponent);
		if (xEventBroadcaster != null) {
			xEventBroadcaster.addEventListener(xEventListener);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Removes document event listener.
	 * 
	 * @param xEventListener
	 *            document event listener to be removed
	 * 
	 * @author Andreas Bröker
	 */
	private void removeEventListener(XEventListener xEventListener) {
		XEventBroadcaster xEventBroadcaster = (XEventBroadcaster) UnoRuntime
				.queryInterface(XEventBroadcaster.class, xComponent);
		if (xEventBroadcaster != null) {
			xEventBroadcaster.removeEventListener(xEventListener);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Prints the document.
	 * 
	 * @throws DocumentException
	 *             if printing fails
	 * 
	 * @author Markus Krüger
	 */
	public void print() throws DocumentException {
		getPrintService().print();
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns the print service of the document.
	 * 
	 * @return the print service of the document
	 * 
	 * @author Markus Krüger
	 * @date 16.08.2007
	 */
	public IPrintService getPrintService() {
		if (printService == null) {
			printService = new PrintService(this);
		}
		return printService;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Checks if the document equals another document.
	 * 
	 * @param compareDocument
	 *            document to be compared
	 * 
	 * @return true if they are equal, flase if they are different
	 * 
	 * @author Markus Krüger
	 * @author Andreas Bröker
	 */
	public boolean equalsTo(IDocument compareDocument) {
		if (compareDocument == null) {
			return false;
		}

		if (UnoRuntime.areSame(xComponent, compareDocument.getXComponent())) {
			return true;
		}
		return false;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Reformats the document. The default behaviour is to do nothing.
	 * 
	 * @author Andreas Bröker
	 * @date 16.03.2006
	 */
	public void reformat() {
		// do nothing by default
	}

	// ----------------------------------------------------------------------------
	/**
	 * Updates/refreshes the document.
	 * 
	 * @author Markus Krüger
	 * @date 11.02.2008
	 */
	public void update() {
		// do nothing by default
	}

	// ----------------------------------------------------------------------------
	/**
	 * Sets selection.
	 * 
	 * @param selection
	 *            selection to be set
	 * 
	 * @throws NOAException
	 *             if the selection type is not supported
	 * 
	 * @author Andreas Bröker
	 * @date 09.07.2006
	 */
	public void setSelection(ISelection selection) throws NOAException {
		if (selection == null || selection.isEmpty()) {
			return;
		}

		if (selection instanceof IXInterfaceObjectSelection) {
			setXInterfaceObjectSelection((IXInterfaceObjectSelection) selection);
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Fires the document event for the submitted document event constant.
	 * 
	 * @param documentEventName
	 *            string constant for document event
	 * 
	 * @see IDocument.EVENT_ON_NEW
	 * @see IDocument.EVENT_ON_LOAD
	 * @see IDocument.EVENT_ON_LOAD_DONE
	 * @see IDocument.EVENT_ON_LOAD_FINISHED
	 * @see IDocument.EVENT_ON_SAVE_DONE
	 * @see IDocument.EVENT_ON_SAVE_FINISHED
	 * @see IDocument.EVENT_ON_SAVE
	 * @see IDocument.EVENT_ON_SAVE_AS
	 * @see IDocument.EVENT_ON_SAVE_AS_DONE
	 * @see IDocument.EVENT_ON_MODIFY_CHANGED
	 * @see IDocument.EVENT_ON_MOUSE_OVER
	 * @see IDocument.EVENT_ON_MOUSE_OUT
	 * @see IDocument.EVENT_ON_FOCUS
	 * @see IDocument.EVENT_ON_ALPHA_CHAR_INPUT
	 * @see IDocument.EVENT_ON_NON_ALPHA_CHAR_INPUT
	 * @see IDocument.EVENT_ON_INSERT_DONE
	 * @see IDocument.EVENT_ON_INSERT_START
	 * @see IDocument.EVENT_ON_UNLOAD
	 * 
	 * @author Alessandro Conte
	 * @date 04.09.2006
	 */
	public void fireDocumentEvent(String documentEventName) {
		if (documentListenerList == null) {
			return;
		}

		IDocumentEvent documentEvent = new DocumentEvent(new EventObject(
				getXComponent(), documentEventName), getServiceProvider());

		for (int i = 0, n = documentListenerList.size(); i < n; i++) {
			Object object = documentListenerList.get(i);
			if (object instanceof IDocumentListener) {
				IDocumentListener documentListener = (IDocumentListener) object;

				if (documentEventName.equalsIgnoreCase(IDocument.EVENT_ON_NEW)) {
					documentListener.onNew(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_LOAD)) {
					documentListener.onLoad(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_LOAD_DONE)) {
					documentListener.onLoadDone(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_LOAD_FINISHED)) {
					documentListener.onLoadFinished(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_SAVE_DONE)) {
					documentListener.onSaveDone(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_SAVE_FINISHED)) {
					documentListener.onSaveFinished(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_SAVE)) {
					documentListener.onSave(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_SAVE_AS)) {
					documentListener.onSaveAs(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_SAVE_AS_DONE)) {
					documentListener.onSaveAsDone(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_MODIFY_CHANGED)) {
					documentListener.onModifyChanged(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_MOUSE_OVER)) {
					documentListener.onMouseOver(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_MOUSE_OUT)) {
					documentListener.onMouseOut(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_FOCUS)) {
					documentListener.onFocus(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_ALPHA_CHAR_INPUT)) {
					documentListener.onAlphaCharInput(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_NON_ALPHA_CHAR_INPUT)) {
					documentListener.onNonAlphaCharInput(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_INSERT_DONE)) {
					documentListener.onInsertDone(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_INSERT_START)) {
					documentListener.onInsertStart(documentEvent);
				} else if (documentEventName
						.equalsIgnoreCase(IDocument.EVENT_ON_UNLOAD)) {
					documentListener.onUnload(documentEvent);
				}
			}
		}

	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns the applications service provider, or null if not available.
	 * 
	 * @return the applications service provider, or null
	 * 
	 * @author Markus Krüger
	 * @date 25.07.2007
	 */
	public IServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Sets the service provider for the document.
	 * 
	 * @param serviceProvider
	 *            the service provider to be set
	 * 
	 * @author Markus Krüger
	 * @date 25.07.2007
	 */
	public void setServiceProvider(IServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
		documentListenerWrapper.setServiceProvider(serviceProvider);
	}

	// ----------------------------------------------------------------------------
	/**
	 * Returns the properties the document was loaded with, or an empty array if
	 * not available.
	 * 
	 * @return the properties the document was loaded with, or an empty array
	 * 
	 * @author Markus Krüger
	 * @date 18.08.2008
	 */
	public PropertyValue[] getInitialProperties() {
		if (initialProperties == null) {
			return new PropertyValue[0];
		}
		return initialProperties;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Sets selection on the XInterface object selection.
	 * 
	 * @param interfaceObject
	 *            XInterface object selection to be set
	 * 
	 * @throws NOAException
	 *             if the selection type is not supported
	 * 
	 * @author Andreas Bröker
	 * @author Markus Krüger
	 * @date 09.07.2006
	 */
	protected void setXInterfaceObjectSelection(
			IXInterfaceObjectSelection interfaceObject) throws NOAException {
		XModel xModel = (XModel) UnoRuntime.queryInterface(XModel.class,
				xComponent);
		if (xModel != null) {
			XController xController = xModel.getCurrentController();
			XSelectionSupplier selectionSupplier = (XSelectionSupplier) UnoRuntime
					.queryInterface(XSelectionSupplier.class, xController);
			if (selectionSupplier != null) {
				try {
					selectionSupplier.select(interfaceObject
							.getXInterfaceObject());
				} catch (Throwable throwable) {
					throw new NOAException(throwable);
				}
			}
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Removes all close listeners.
	 * 
	 * @author Markus Krüger
	 */
	protected void removeCloseListeners() {
		if (closeListeners != null) {
			Object[] closeListenersArr = closeListeners.keySet().toArray();
			for (int i = 0; i < closeListenersArr.length; i++) {
				removeCloseListener((ICloseListener) closeListenersArr[i]);
			}
			closeListeners = null;
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Removes all modify listeners.
	 * 
	 * @author Markus Krüger
	 */
	protected void removeModifyListeners() {
		if (modifyListenerTable != null) {
			Object[] modifyListeners = modifyListenerTable.keySet().toArray();
			for (int i = 0; i < modifyListeners.length; i++) {
				removeDocumentModifyListener((IDocumentModifyListener) modifyListeners[i]);
			}
			modifyListenerTable = null;
		}
	}

	// ----------------------------------------------------------------------------
	/**
	 * Removes all document listeners.
	 * 
	 * @author Markus Krüger
	 */
	protected void removeDocumentListeners() {
		if (documentListenerList != null) {
			Object[] documentListeners = documentListenerList.toArray();
			for (int i = 0; i < documentListeners.length; i++) {
				removeDocumentListener((IDocumentListener) documentListeners[i]);
			}
			documentListenerList = null;
		}
		if (documentListenerWrapper != null) {
			removeEventListener(documentListenerWrapper);
		}
	}
	// ----------------------------------------------------------------------------
}