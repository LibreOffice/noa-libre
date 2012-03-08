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
package ag.ion.bion.officelayer.internal.event;

import ag.ion.bion.officelayer.event.ITerminateListener;
import ag.ion.noa.service.IServiceProvider;

import com.sun.star.frame.TerminationVetoException;
import com.sun.star.frame.XTerminateListener;
import com.sun.star.lang.EventObject;

/**
 * Wrapper in order to register a terminate listener.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public class TerminateListenerWrapper extends EventListenerWrapper implements
		XTerminateListener {

	private ITerminateListener terminateListener = null;

	// ----------------------------------------------------------------------------
	/**
	 * Constructs new TerminateListenerWrapper.
	 * 
	 * @param terminateListener
	 *            terminate listener to be used
	 * @param serviceProvider
	 *            the service provider to be used
	 * 
	 * @throws IllegalArgumentException
	 *             if the submitted terminate listener is not valid
	 * 
	 * @author Andreas Bröker
	 */
	public TerminateListenerWrapper(ITerminateListener terminateListener,
			IServiceProvider serviceProvider) throws IllegalArgumentException {
		super(terminateListener, serviceProvider);
		this.terminateListener = terminateListener;
	}

	// ----------------------------------------------------------------------------
	/**
	 * Is called when the master enviroment is about to terminate.
	 * 
	 * @param eventObject
	 *            source of the event
	 * 
	 * @throws TerminationVetoException
	 *             listener can disagree with this query by throwing a veto
	 *             exception
	 * 
	 * @author Andreas Bröker
	 */
	public void queryTermination(EventObject eventObject)
			throws TerminationVetoException {
		TerminateEvent terminateEvent = new TerminateEvent(eventObject,
				getServiceProvider());
		terminateListener.queryTermination(terminateEvent);
		if (terminateEvent.getVeto())
			throw new TerminationVetoException();
	}

	// ----------------------------------------------------------------------------
	/**
	 * Is called when the master enviroment is finally terminated.
	 * 
	 * @param eventObject
	 *            of the event
	 * 
	 * @author Andreas Bröker
	 */
	public void notifyTermination(EventObject eventObject) {
		TerminateEvent terminateEvent = new TerminateEvent(eventObject,
				getServiceProvider());
		terminateListener.notifyTermination(terminateEvent);
	}
	// ----------------------------------------------------------------------------

}