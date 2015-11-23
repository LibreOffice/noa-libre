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
package ag.ion.bion.officelayer.runtime;

/**
 * Progress monitor of the office layer.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10398 $
 */
public interface IOfficeProgressMonitor {
  
  /** The work of a task is unknown. */
  public static final int WORK_UNKNOWN = -1;
  
  //----------------------------------------------------------------------------
  /**
   * Informs the monitor about a new task.
   * 
   * @param name name of the task
   * @param totalWork total work of the task
   * 
   * @author Andreas Bröker
   */
  public void beginTask(String name, int totalWork);
  //----------------------------------------------------------------------------
  /**
   * Notifies that a given number of work unit of the main task
   * has been completed. Note that this amount represents an
   * installment, as opposed to a cumulative amount of work done
   * to date.
   *
   * @param work the number of work units just completed
   * 
   * @author Andreas Bröker
   */
  public void worked(int work);
  //----------------------------------------------------------------------------
  /**
   * Informs the monitor about a new subtask.
   * 
   * @param name name of the substask
   * 
   * @author Andreas Bröker
   */
  public void beginSubTask(String name);
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the progress monitor needs
   * to be notified about the end of the main task.
   * 
   * @return information whether the progress monitor needs
   * to be notified about the end of the main task
   * 
   * @author Andreas Bröker
   */
  public boolean needsDone();
  //----------------------------------------------------------------------------
  /**
   * Informs the progress monitor that the work is done.
   * 
   * @author Andreas Bröker
   */
  public void done();  
  //----------------------------------------------------------------------------
  /**
   * Sets information whether the work was canceled.
   * 
   * @param canceled information whether the work was canceled
   * 
   * @author Andreas Bröker
   */
  public void setCanceled(boolean canceled);  
  //----------------------------------------------------------------------------
  /**
   * Returns information whether the work was canceled.
   * 
   * @return information whether the work was canceled
   * 
   * @author Andreas Bröker
   */
  public boolean isCanceled();  
  //----------------------------------------------------------------------------
  
}