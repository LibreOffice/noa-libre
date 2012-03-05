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
 * Last changes made by $Author: andreas $, $Date: 2006-10-27 08:20:15 +0200 (Fr, 27 Okt 2006) $
 */
package ag.ion.bion.officelayer.internal.document;

import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;

import com.sun.star.io.XInputStream;
import com.sun.star.io.XSeekable;

import com.sun.star.lib.uno.helper.ComponentBase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper in order to use a byte array as a XInputStream interface.
 * 
 * This class bases on the OpenOffice.org Java API class ByteArrayToXInputStreamAdapter.
 * 
 * @author Andreas Bröker
 * @version $Revision: 10751 $
 */
public class ByteArrayXInputStreamAdapter extends ComponentBase implements XInputStream, XSeekable {
   
  private IOfficeProgressMonitor officeProgressMonitor = null;
  
  private byte[] bytes = null;;
 
  private int length        = -1;
  private int pos           = -1;
  private int counter       = 0;  
  private int buildCounter  = 0;
  
  private boolean showBuildInformation = false;
  
  //----------------------------------------------------------------------------
  /**
   * Constructs new ByteArrayXInputStreamAdapter.
   * 
   * @param inputStream input stream to be used
   * @param officeProgressMonitor office progress monitor to be used
   * 
   * @throws IOException if the input stream can not be read
   * 
   * @author Andreas Bröker
   * @date 27.10.2006
   */
  public ByteArrayXInputStreamAdapter(InputStream inputStream, IOfficeProgressMonitor officeProgressMonitor) throws IOException {    
    this(officeProgressMonitor, toByteArray(inputStream));
  } 
  //----------------------------------------------------------------------------
  /**
   * Constructs new ByteArrayXInputStreamAdapter.
   * 
   * @param bytes bytes to be used
   * 
   * @throws IllegalArgumentException if the submitted bytes are not valid
   * 
   * @author Andreas Bröker
   */
  public ByteArrayXInputStreamAdapter(byte[] bytes) throws IllegalArgumentException {
    this(null, bytes);
  }
  //----------------------------------------------------------------------------
  /**
   * Constructs new ByteArrayXInputStreamAdapter.
   * 
   * @param officeProgressMonitor office progress monitor to be used
   * @param bytes bytes to be used
   * 
   * @throws IllegalArgumentException if the submitted bytes are not valid
   * 
   * @author Andreas Bröker
   */
  public ByteArrayXInputStreamAdapter(IOfficeProgressMonitor officeProgressMonitor, byte[] bytes) throws IllegalArgumentException {
    if(bytes == null)
      throw new IllegalArgumentException("The submitted bytes are not valid."); //$NON-NLS-1$
    this.bytes = bytes;
    length = bytes.length;
    pos = 0;
    
    this.officeProgressMonitor = officeProgressMonitor;
    if(officeProgressMonitor != null) {
      officeProgressMonitor.beginTask(Messages.getString("ByteArrayXInputStreamAdapter_monitor_task_name"), length); //$NON-NLS-1$
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Reads number of available bytes.
   * 
   * @return number of available bytes
   * 
   * @throws com.sun.star.io.IOException if any I/O related exception occurs
   * 
   * @author Andreas Bröker
   */
  public int available() throws com.sun.star.io.IOException {
    long available = length - pos;        
    if(available != (int)available) 
      throw new com.sun.star.io.IOException("Integer overflow.");         //$NON-NLS-1$
    else 
      return (int)available;       
  }
  //----------------------------------------------------------------------------
  /**
   * Closes the stream.
   * 
   * @throws com.sun.star.io.IOException if any I/O related exception occurs
   * 
   * @author Andreas Bröker
   */
  public void closeInput() throws com.sun.star.io.IOException {
    bytes = null;
  }
  //----------------------------------------------------------------------------
  /**
   * Reads bytes from the stream.
   * 
   * @param buffer byte array to be used
   * @param size number of bytes to be fetched
   * 
   * @return number of fetched bytes
   * 
   * @throws com.sun.star.io.BufferSizeExceededException if the buffer size was exceeded
   * @throws com.sun.star.io.IOException if any I/O related exception occurs
   * 
   * @author Andreas Bröker
   */
  public int readBytes(byte[][] buffer, int size) throws com.sun.star.io.BufferSizeExceededException, com.sun.star.io.IOException {
    try {    
      int remain = (int)(length - pos);
      if(size > remain) size = remain;
      if(buffer[0] == null)              
        buffer[0] = new byte[size];
      System.arraycopy(bytes, pos, buffer[0], 0, size);
      
      if(pos >= counter) {
        counter+=size;
      }
      else {
        int difference = counter-pos;
        counter+= size-difference;
      }
      
      pos+= size;
      
      if(officeProgressMonitor != null) {
        if(!showBuildInformation) {
          officeProgressMonitor.worked(size);
          officeProgressMonitor.beginSubTask(Messages.getString("ByteArrayXInputStreamAdapter_monitor_sub_task", new Object[] {new Integer(counter), new Integer(length)})); //$NON-NLS-1$
        }
        else {
          officeProgressMonitor.beginSubTask(Messages.getString("ByteArrayXInputStreamAdapter__monitor_integrating", new Integer(buildCounter++))); //$NON-NLS-1$
        }
        
        if(counter >= length)
          showBuildInformation = true;
      }
      return size;
    }
    catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new com.sun.star.io.BufferSizeExceededException("Buffer overflow."); //$NON-NLS-1$
    }
    catch (Exception exception) {
      throw new com.sun.star.io.IOException("Error accessing byte array."); //$NON-NLS-1$
    }
  }
  //----------------------------------------------------------------------------
  /**
   * Reads bytes from the stream.
   * 
   * @param buffer byte array to be used
   * @param size number of bytes to be fetched
   * 
   * @return number of fetched bytes
   * 
   * @throws com.sun.star.io.BufferSizeExceededException if the buffer size was exceeded
   * @throws com.sun.star.io.IOException if any I/O related exception occurs
   * 
   * @author Andreas Bröker
   */
  public int readSomeBytes(byte[][] buffer, int size) throws com.sun.star.io.BufferSizeExceededException, com.sun.star.io.IOException {
    return readBytes(buffer, size);
  }
  //----------------------------------------------------------------------------
  /**
   * Skips bytes.
   * 
   * @param size number of bytes to be skipped
   * 
   * @throws com.sun.star.io.BufferSizeExceededException if the buffer size was exceeded
   * @throws com.sun.star.io.IOException if any I/O related exception occurs
   * 
   * @author Andreas Bröker
   */
  public void skipBytes(int size) throws com.sun.star.io.BufferSizeExceededException, com.sun.star.io.IOException {
    if(size > (length - pos))
      throw new com.sun.star.io.BufferSizeExceededException("Buffer overflow."); //$NON-NLS-1$
    pos+= size;
  }
  //----------------------------------------------------------------------------
  /**
   * Returns number of available bytes.
   *
   * @return number of available bytes
   *
   * @throws com.sun.star.io.IOException if any I/O related exception occurs
   * 
   * @author Andreas Bröker
   */
  public long getLength() throws com.sun.star.io.IOException {
    if(bytes != null) return length;
      else throw new com.sun.star.io.IOException("No bytes available."); //$NON-NLS-1$
  }
  //----------------------------------------------------------------------------
  /**
   * Returns current position.
   *
   * @return current position
   *
   * @throws com.sun.star.io.IOException if any I/O related exception occurs
   * 
   * @author Andreas Bröker
   */
  public long getPosition() throws com.sun.star.io.IOException {
    if(bytes != null) 
      return pos;
    else throw new com.sun.star.io.IOException("No bytes available.");       //$NON-NLS-1$
  }
  //----------------------------------------------------------------------------
  /**
   * Seeks position.
   *
   * @param position position to be used
   *
   * @throws com.sun.star.lang.IllegalArgumentException if the seek position is invalid
   * @throws com.sun.star.io.IOException if any I/O related exception occurs
   * 
   * @author Andreas Bröker
   */
  public void seek(long position) throws com.sun.star.lang.IllegalArgumentException, com.sun.star.io.IOException {
    if(bytes != null) {
      if(position < 0 || position > length) 
        throw new com.sun.star.lang.IllegalArgumentException("Invalid seek position."); //$NON-NLS-1$
      else pos = (int)position;
    }
    else 
      throw new com.sun.star.io.IOException("No bytes available.");  //$NON-NLS-1$
   }
  //----------------------------------------------------------------------------
  /**
   * Converts the submitted stream to a byte array.
   * 
   * @param inputStream input stream to be used
   * 
   * @return bytes of the input stream
   * 
   * @throws IOException if the input stream can not be read
   * 
   * @author Andreas Bröker
   * @date 27.10.2006
   */
  private static final byte[] toByteArray(InputStream inputStream) throws IOException {
    int size = 4096;
    int read = 0;
    int counter = 0;
    byte[] buffer = new byte[size];
    byte[] newBuffer = null;
    while ((read = inputStream.read(buffer, counter, buffer.length-counter))>0) {
      counter += read;
      if (inputStream.available() > buffer.length - counter) {
        newBuffer = new byte[buffer.length*2];
        System.arraycopy(buffer, 0, newBuffer, 0, counter);
        buffer = newBuffer;
      }
    }
    if (buffer.length != counter) {
      newBuffer = new byte[counter];
      System.arraycopy(buffer, 0, newBuffer, 0, counter);
      buffer = newBuffer;
    }
    return buffer;
  }  
  //----------------------------------------------------------------------------
  
}