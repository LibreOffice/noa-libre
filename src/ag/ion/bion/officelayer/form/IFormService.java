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
 * Copyright 2003-2007 by IOn AG                                            *
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
 * Last changes made by $Author$, $Date$
 */
package ag.ion.bion.officelayer.form;

import ag.ion.noa.NOAException;

import com.sun.star.script.XEventAttacherManager;

/**
 * Service for forms.
 * 
 * @author Markus Krüger
 * @version $Revision$
 */
public interface IFormService {
  
  //----------------------------------------------------------------------------
  /**
   * Returns the forms for the given form name as an array. There will be only more 
   * than one form if the given form name occurs more than once.
   * 
   * @param formName the form name to be used
   * 
   * @return the forms for the given form name as an array
   * 
   * @throws NOAException if the return of forms fails
   * 
   * @author Markus Krüger
   * @date 26.01.2007
   */
  public IForm[] getForms(String formName) throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Returns the OpenOffice.org XEventAttacherManager interface for the given form,
   * or null if not available.
   * 
   * @param form the form to be used
   * 
   * @return the OpenOffice.org XEventAttacherManager interface for the given form,
   * or null
   * 
   * @throws NOAException if the return of OpenOffice.org XEventAttacherManager interface fails
   * 
   * @author Markus Krüger
   * @date 26.01.2007
   */
  public XEventAttacherManager getXEventAttacherManager(IForm form) throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Returns the index of the given form component in the given form, or -1 if not found.
   * 
   * @param form the form to check index in
   * @param formComponent the form component to get index for
   * 
   * @return the index of the given form component in the given form, or -1
   * 
   * @throws NOAException if anything fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public int getIndexInForm(IForm form, IFormComponent formComponent) throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Returns if the component has forms.
   * 
   * @return if the component has forms
   * 
   * @throws NOAException if the check fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public boolean hasForms() throws NOAException;
  //----------------------------------------------------------------------------
  /**
   * Returns if the component contains other form components.
   * 
   * @return if the component contains other form components
   * 
   * @throws NOAException if the check fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public boolean hasFormComponents() throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Returns all form components contained in this component.
   * 
   * @return all form components contained in this component
   * 
   * @throws NOAException if the return fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public IFormComponent[] getFormComponents() throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Returns all form components names contained in this component.
   * 
   * @return all form components names contained in this component
   * 
   * @throws NOAException if the return fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public String[] getFormComponentsNames() throws NOAException;  
  //----------------------------------------------------------------------------
  /**
   * Removes the given form component.
   * 
   * @param formComponent the form component to remove
   * 
   * @throws NOAException if the remove fails
   * 
   * @author Markus Krüger
   * @date 25.01.2007
   */
  public void removeFormComponent(IFormComponent formComponent) throws NOAException;  
  //----------------------------------------------------------------------------

}