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
package ag.ion.bion.officelayer.util;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * A special hash-like structure which saves key value pairs and makes random access to them possible.
 * 
 * @author Sebastian Rösgen
 * @version $Revision: 10398 $
 */
public class OrderedHashMap {
  
  private HashMap list = new HashMap();
  private HashMap reversedList = new HashMap();
  
  private HashMap positionList = new HashMap();           //
  private HashMap reversedPositionList  = new HashMap();  //  These are all needed
                                                          //  to keep a trail of
  private ArrayList keyList = new ArrayList();            //  the elements in the 
  private ArrayList valueList = new ArrayList();          //  structure. 
                                                          //  So we get actually a
  private int currentPosition = -1;                       //  (virtual) random access.
  private int internalCounter = -1;                       //  
  //----------------------------------------------------------------------------
  /**
   * Adds a new element pair to the structure.
   * 
   * @param key the key of the new structure element.
   * 
   * @param value the value of the new strucutre element.
   */
  public void put(Object key, Object value) {
    
    /*
     As you will see we fill a pretty big bunch of structures.
     Most of the seem to be redundant at the first but make sense 
     in this special case cause we do actually get a pretty performance
     increase with the help of this method (and btw the random access
     profits by this method) 
    */
    
    list.put(key, value);
    reversedList.put(value, key);
    internalCounter = internalCounter < 0 ? 0 : internalCounter++;
    positionList.put(new Integer(internalCounter), key);
    reversedPositionList.put(key, new Integer(internalCounter));
    keyList.add(key);
    valueList.add(value);
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the key corresponding to the specified value.
   *
   * @param value the specific value
   * 
   * @return key  belonging to the value.
   */
  public Object getKeyByValue(Object value) {
    return reversedList.get(value);
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the element dedicated to the specified key.
   * 
   * @param key the specific key.
   *  
   * @return value belonging to the key.
   */
  public Object get(Object key){
    currentPosition = ((Integer)reversedPositionList.get(key)).intValue();
    return list.get(key);
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the element positioned at the specified position in the structure.
   * 
   * @param number the specific position of the element.
   * 
   * @return element the wanted element.
   */
  public Object getValueByIndexNumber(int number){
    currentPosition = number;
    return list.get(  positionList.get( new Integer(number) )); 
  }
  //----------------------------------------------------------------------------
  /**
   * Clears the content of the structure. 
   */
  public void clear(){
    list.clear();
    positionList.clear();
    internalCounter = -1;
    currentPosition = -1;
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the first element in the structure. 
   * The internal pointer changes to the first element in the structure. 
   * 
   * @return firstElement the first element in the structure.
   */
  public Object goToHead(){
    if(list.size()>0 && internalCounter >= 0) {
      currentPosition = 0;
      return list.get( positionList.get(new Integer(0))  );
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the last element in the structure. 
   * The internal pointer changes to the last element in the structure. 
   * 
   * @return lastElement the last element in the structure.
   */
  public Object goToTail() {
    if(list.size()>0 && internalCounter >= 0) {
      currentPosition = internalCounter;
      return list.get( positionList.get(new Integer(internalCounter))  ); 
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the next element in the structure. Returns null if there is no next element.
   * The current position of the element pointer is moved to the next element. 
   * 
   * @return value the next element.
   */
  public Object getNext() {
    currentPosition = currentPosition > -1 ? currentPosition++ : 0; 
    if(currentPosition <= internalCounter  && internalCounter > -1){ // if internalCounter is -1 the list was not initalized.
      return list.get( positionList.get(new Integer(currentPosition))  ); 
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the element directly before the current position and returns ts value.
   * 
   * @return value the predecessing element.
   */
  public Object getPredecessor(){
    currentPosition = currentPosition > 0 ? currentPosition-- : 0;
    if (currentPosition <= internalCounter && internalCounter > -1) { // if internalCounter is -1 the list was not initalized.
      return list.get(  positionList.get(new Integer(currentPosition))  ); 
    }
    return null;
  }
  //----------------------------------------------------------------------------
  /**
   * Gets a list of all keys (currently) used  keys in the structure.
   * 
   * @return listOfKeys a list of keys.
   */
  public Object[] getKeys() {
    return keyList.toArray();
  }
  //----------------------------------------------------------------------------
  /**
   * Gets a list of all (currently) set values in the structure.
   * 
   * @return listOfValues a list of elements. 
   */
  public Object[] getValues() {
    return valueList.toArray();
  }
  //----------------------------------------------------------------------------
  /**
   * Gets the number of elements (key-value pairs) contained in the structure.
   * 
   * @return size the structure size.
   */
  public int size() {
    return keyList.size();
  }
  //----------------------------------------------------------------------------
}
