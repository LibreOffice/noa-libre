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
 * Last changes made by $Author: markus $, $Date: 2007-09-19 15:26:58 +0200 (Mi, 19 Sep 2007) $
 */
package ag.ion.bion.officelayer.text;

/**
 * Textfield service of a text document.
 * 
 * @author Andreas Bröker
 * @author Markus Krüger
 * @version $Revision: 11573 $
 */
public interface ITextFieldService {

  /** User field master id **/
  public static final String USER_TEXTFIELD_MASTER_ID            = "com.sun.star.text.FieldMaster.User";
  /** User textfield id **/
  public static final String USER_TEXTFIELD_ID                   = "com.sun.star.text.TextField.User";  
  /** Variables field master id **/
  public static final String VARIABLES_TEXTFIELD_MASTER_ID       = "com.sun.star.text.FieldMaster.SetExpression";
  /** Variables textfield id **/
  public static final String VARIABLES_TEXTFIELD_ID              = "com.sun.star.text.TextField.SetExpression";
  /** Annotation textfield id **/
  public static final String ANNOTATION_TEXTFIELD_ID             = "com.sun.star.text.TextField.Annotation";
  /** Placerholder textfield id **/
  public static final String PLACEHOLDER_TEXTFIELD_ID            = "com.sun.star.text.TextField.JumpEdit";
  
  //----------------------------------------------------------------------------
  /**
   * Returns master of a user textfield with the submitted name. Returns null if
   * a user textfield with the submitted name is not available.
   * 
   * @param name name of the master of the user textfield
   * 
   * @return master of a user textfield with the submitted name or null if a user textfield with
   * the submitted name is not available
   * 
   * @throws TextException if the user textfield can not be provided
   * 
   * @author Andreas Bröker
   */
  public ITextFieldMaster getUserTextFieldMaster(String name) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns masters of the user textfields with the submitted name prefix.
   * 
   * @param prefix name prefix to be used
   * 
   * @return masters of the user textfields with the submitted name prefix
   * 
   * @throws TextException if the masters of the user textfields can not be constructed
   * 
   * @author Andreas Bröker
   */
  public ITextFieldMaster[] getUserTextFieldMasters(String prefix) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns masters of the user textfields with the submitted name prefix and suffix.
   * 
   * @param prefix name prefix to be used
   * @param suffix name suffix to be used
   * 
   * @return masters of the user textfields with the submitted name prefix and suffix
   * 
   * @throws TextException if the masters of the user textfields can not be constructed
   * 
   * @author Markus Krüger
   */
  public ITextFieldMaster[] getUserTextFieldMasters(String prefix, String suffix) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Adds new user textfield.
   * 
   * @param name name of the textfield
   * @param content content of the textfield
   * 
   * @return new textfield
   * 
   * @throws TextException if any error occurs during textfield creation
   * 
   * @author Andreas Bröker
   */
  public ITextField addUserTextField(String name, String content) throws TextException;
  //---------------------------------------------------------------------------- 
  /**
   * Returns all available user textfields.
   * 
   * @return all available user textfields
   * 
   * @throws TextException if the user textfields can not be constructed
   * 
   * @author Andreas Bröker
   */
  public ITextField[] getUserTextFields() throws TextException;
  //----------------------------------------------------------------------------  
  /**
   * Returns all available placeholder textfields.
   * 
   * @return all available placeholder textfields
   * 
   * @throws TextException if the placeholder textfields can not be constructed
   * 
   * @author Markus Krüger
   * @date 23.01.2007
   */
  public ITextField[] getPlaceholderFields() throws TextException;
  //---------------------------------------------------------------------------- 
  /**
   * Creates a new placeholder textfield.
   * 
   * @param name name of the placeholder textfield
   * @param hint the hint of the placeholder textfield, may be null
   * @param placeholderType the type of the placeholder found in static 
   * members of com.sun.star.text.PlaceholderType (i.e. PlaceholderType.TEXT)
   * 
   * @return new placeholder textfield
   * 
   * @throws TextException if any error occurs during placeholder textfield creation
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public ITextField createPlaceholderTextField(String name, String hint, short placeholderType) throws TextException;
  //---------------------------------------------------------------------------- 
  /**
   * Returns master of the variables text fields with the submitted name, or null if not availbale.
   * 
   * @param masterName name of the master to return
   * 
   * @return master of the variables text fields with the submitted name, or null
   * 
   * @throws TextException if the master can not be returned
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public IVariableTextFieldMaster getVariableTextFieldMaster(String masterName) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns all masters of the variables text fields with the submitted name prefix.
   * 
   * @param prefix name prefix to be used
   * 
   * @return all masters of the variables text fields
   * 
   * @throws TextException if the masters can not be returned
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public IVariableTextFieldMaster[] getVariableTextFieldMasters(String prefix) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Creates a new variable textfield master and returns it, or returns the one that
   * already exists, if it does.
   * TODO maybe some more parameters are needed???
   * 
   * @param name name of the variable textfield master
   * @param variableType the type of the variable master found in static 
   * members of com.sun.star.text.SetVariableType (i.e. SetVariableType.STRING)
   * 
   * @return the variable textfield master with the given name
   * 
   * @throws TextException if any error occurs during variable textfield master creation
   * 
   * @author Markus Krüger
   * @date 30.05.2007
   */
  public IVariableTextFieldMaster createVariableTextFieldMaster(String name, short variableType) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Returns all available variable textfields with the submitted name prefix.
   * 
   * @param prefix name prefix to be used
   * 
   * @return all available variable textfields
   * 
   * @throws TextException if the variable textfields can not be constructed
   * 
   * @author Markus Krüger
   * @date 29.05.2007
   */
  public ITextField[] getVariableFields(String prefix) throws TextException;
  //----------------------------------------------------------------------------
  /**
   * Refreshes all textfields.
   * 
   * @throws TextException if refresh fails
   * 
   * @author Markus Krüger
   * @date 29.05.2007
   */
  public void refresh() throws TextException;
  //---------------------------------------------------------------------------- 

}