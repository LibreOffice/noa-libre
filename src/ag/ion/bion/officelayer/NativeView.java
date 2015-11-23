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
 * Last changes made by $Author$, $Date$
 */
package ag.ion.bion.officelayer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import ag.ion.bion.officelayer.application.IOfficeApplication;
import java.lang.reflect.Field;

/**
 * Class to pass the system window handle to the OpenOffice.org toolkit.
 * It use special JNI methods to get the system handle of used java window.
 *
 * Attention!
 * Use JNI functions on already visible canvas objects only!
 * Otherwise they can make some trouble.
 * 
 * Integrated into NOA by Markus Krüger as it is needed for integration in Swing applications.
 *
 * @author  Andreas Schl&uuml;ns
 * @author Markus Krüger
 * @created 22.02.2002 08:47
 */
public class NativeView extends Canvas {

  static final long serialVersionUID = 744155902373436284L;

  /**
   * @member  maHandle    system window handle
   */
  public Integer    maHandle;
  /**
   * @member  maSystem    info about currently used platform
   */
  public int        maSystem;

  /**
   * JNI interface of this class
   * These two methods are implemented by using JNI mechanismen.
   * The will be used to get the platform dependent window handle
   * of a java awt canvas. This handle can be used to create an office
   * window as direct child of it. So it's possible to plug Office
   * windows in a java UI container.
   *
   * Note:
   * Native code for windows register special function pointer to handle
   * window messages ... But if it doesn't check for an already registered
   * instance of this handler it will do it twice and produce a stack overflow
   * because such method call herself in a never ending loop ...
   * So we try to use the JNI code one times only and safe already getted
   * informations inside this class.
   */
  public native int getNativeWindowSystemType();

  private native long getNativeWindow(); // private! => use getHWND() with cache mechanism!

  private Dimension preferredSize = new Dimension(500, 300);
  private Dimension minSize       = new Dimension(100, 100);
  private Dimension maxSize       = new Dimension(1024, 768);

  private String    libPath       = null;

  //----------------------------------------------------------------------------
  /**
   * ctor
   * Does nothing realy.
   * We can use our JNI mechanism for an already visible
   * canvas only. So we overload the method for showing ("setVisible()")
   * and make our intialization there. BUt we try to show an empty clean
   * window till there.
   */
  public NativeView() {
    maHandle = null;
    maSystem = 0;
    this.setBackground(Color.white);
    loadLibrary();
  }

  //----------------------------------------------------------------------------
  /**
   * ctor
   * Does nothing realy.
   * We can use our JNI mechanism for an already visible
   * canvas only. So we overload the method for showing ("setVisible()")
   * and make our intialization there. BUt we try to show an empty clean
   * window till there.
   * 
   * @param nativeLibPath path to the native view library
   */
  public NativeView(String nativeLibPath) {
    libPath = nativeLibPath;
    maHandle = null;
    maSystem = 0;
    this.setBackground(Color.white);
    loadLibrary();
  }

  //----------------------------------------------------------------------------
  /**
   * Overload this method to make neccessary initializations here.
   * (e.g. get the window handle and neccessary system informations)
   *
   * Why here?
   * Because the handle seams to be available for already visible windows
   * only. So it's the best place to get it. Special helper method
   * can be called more then ones - but call native code one times only
   * and safe the handle and the system type on our members maHandle/maSystem!
   */
  public void setVisible(boolean bState) {
    getHWND();
  }

  //----------------------------------------------------------------------------
  /**
   * to guarantee right resize handling inside a swing container
   * (e.g. JSplitPane) we must provide some informations about our
   * prefered/minimum and maximum size.
   */
  public Dimension getPreferredSize() {
    return preferredSize;
  }

  //----------------------------------------------------------------------------
  public void setPreferredSize(Dimension preferredSize) {
    super.setPreferredSize(preferredSize);
    this.preferredSize = preferredSize;
  }

  public Dimension getMaximumSize() {
    return maxSize;
  }

  //----------------------------------------------------------------------------
  public Dimension getMinimumSize() {
    return minSize;
  }

  //----------------------------------------------------------------------------
  /**
   * overload paint routine to show provide against
   * repaint errors if no office view is realy plugged
   * into this canvas.
   * If handle is present - we shouldn't paint anything further.
   * May the remote window is already plugged. In such case we
   * shouldn't paint it over.
   */
  public void paint(Graphics aGraphic) {
    if (maHandle == null) {
      Dimension aSize = getSize();
      aGraphic.clearRect(0, 0, aSize.width, aSize.height);
    }
  }

  //----------------------------------------------------------------------------
  /**
   * Returns the window handle.
   * 
   * @return the window handle
   */
  public Integer getHWND() {
    if (maHandle == null) {
      maHandle = new Integer((int) getNativeWindow());
      maSystem = getNativeWindowSystemType();
    }
    return maHandle;
  }

  //----------------------------------------------------------------------------
  /**
   * for using of the JNI methods it's necessary to load
   * system library which exports it.
   */
  private void loadLibrary() {
    String libPathFromProps = System.getProperty(IOfficeApplication.NOA_NATIVE_LIB_PATH);
    if (libPathFromProps != null) {
      libPath = libPathFromProps;
    }
    if (libPath != null) {
      String libName = "libnativeview.so";
      String folder64bit = "64bit";
      if (OSHelper.IS_WINDOWS) {
        libName = "nativeview.dll";
      }
      boolean is64Bit = Integer.valueOf(System.getProperties().getProperty("sun.arch.data.model")) == 64;
      if (is64Bit) {
        if (new File(libPath + File.separator + folder64bit + File.separator + libName).exists()) {
          libPath = libPath + File.separator + folder64bit;
        }
      }
      System.load(libPath + File.separator + libName);
    }else if(OSHelper.IS_LINUX || OSHelper.IS_MAC){
      String libName = "libnativeview.so";
      boolean is64Bit = Integer.valueOf(System.getProperties().getProperty("sun.arch.data.model")) == 64;
      libPath = "/usr/lib" + (is64Bit?"64":"");

      System.load(libPath + File.separator + libName);
    }else{
        //not giving up yet
        System.loadLibrary("nativeview");
    }
  }
}
