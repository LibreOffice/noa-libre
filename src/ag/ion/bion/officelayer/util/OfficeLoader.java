/*
 * This file is part of noa-libre.
 *
 * The Contents of this file are made available subject to
 * the terms of GNU Lesser General Public License Version 2.1.
 *
 * GNU Lesser General Public License Version 2.1
 * ========================================================================
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Publi
 * License version 2.1, as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston
 * MA  02111-1307  USA
 *
 * This file incorporates work covered by the following license notice:
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements. See the NOTICE file distributed
 *   with this work for additional information regarding copyright
 *   ownership. The ASF licenses this file to you under the Apache
 *   License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of
 *   the License at http://www.apache.org/licenses/LICENSE-2.0 .
 */

package ag.ion.bion.officelayer.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.StringTokenizer;
import java.util.ArrayList;

import ag.ion.bion.officelayer.application.IOfficeApplication;

/**
 * This class can be used as a loader for application classes which use UNO.
 *
 * <p>The Loader class detects a UNO installation on the system and adds the
 * UNO jar files to the search path of a customized class loader, which is used
 * for loading the application classes.</p>
 */
public final class OfficeLoader {

    private static ClassLoader m_Loader = null;

    private static String m_OfficePath = null;

    /**
     * do not instantiate
     */
    private OfficeLoader() {}

    public static void init( HashMap config) {
        if ( config.containsKey( IOfficeApplication.APPLICATION_HOME_KEY) ) {
            Object home = config.get( IOfficeApplication.APPLICATION_HOME_KEY );

	    if ( home != null ) {
                m_OfficePath = home.toString();
	    }
	}
    }

    /**
     * This method instantiates a customized class loader with the
     * UNO jar files added to the search path and loads the application class,
     * which is specified as the first parameter in the argument list.
     */
    public static void run( String  [] arguments ) throws Exception {

        String className = null;
        Class clazz = OfficeLoader.class;
        ClassLoader loader = clazz.getClassLoader();
        ArrayList<URL> res = new ArrayList<URL>();
        try {
            Enumeration<URL> en = loader.getResources( "META-INF/MANIFEST.MF" );
            while ( en.hasMoreElements() ) {
                res.add( en.nextElement() );
            }
        } catch ( IOException e ) {
            System.err.println( OfficeLoader.class.getName() +
                                "::run: cannot get manifest resources: " + e );
        }

        // get the name of the class to be loaded from the argument list
	String[] args;
        if ( arguments.length > 0 ) {
            className = arguments[0];
            args = new String[arguments.length - 1];
            System.arraycopy( arguments, 1, args, 0, args.length );
        } else {
            throw new IllegalArgumentException(
                "The name of the class to be loaded must be " +
                "specified as a command line argument." );
        }

        // load the class with the customized class loader and
        // invoke the main method
        if ( className != null ) {
            ClassLoader cl = getCustomLoader();
            Thread.currentThread().setContextClassLoader(cl);
            Class c = cl.loadClass( className );
            @SuppressWarnings("unchecked")
            Method m = c.getMethod( "main", new Class[] { String[].class } );
            m.invoke( null, new Object[] { args } );
        }
    }

    /**
     * Gets the customized class loader with the UNO jar files added to the
     * search path.
     *
     * @return the customized class loader
     */
    public static synchronized ClassLoader getCustomLoader() {
        if ( m_Loader == null ) {

            // get the urls from which to load classes and resources
            // from the class path
            ArrayList<URL> vec = new ArrayList<URL>();
            String classpath = null;
            try {
                classpath = System.getProperty( "java.class.path" );
            } catch ( SecurityException e ) {
                // don't add the class path entries to the list of class
                // loader URLs
                System.err.println( OfficeLoader.class.getName() +
                    "::getCustomLoader: cannot get system property " +
                    "java.class.path: " + e );
            }
            if ( classpath != null ) {
                addUrls(vec, classpath, File.pathSeparator);
            }

            // get the urls from which to load classes and resources
            // from the UNO installation
            String path = (m_OfficePath != null) ? m_OfficePath : InstallationFinder.getPath();

            if ( path != null ) {
                callUnoinfo(path, vec);
            } else {
                System.err.println( OfficeLoader.class.getName() +
                    "::getCustomLoader: no UNO installation found!" );
            }

            //add path to officebean jar
	    addUrl(vec, new String(path + "/classes/officebean.jar"));

            // copy urls to array
            URL[] urls = new URL[vec.size()];
            vec.toArray( urls );

            // instantiate class loader
            m_Loader = new CustomURLClassLoader( urls );
        }

        return m_Loader;
    }

    private static void addUrls(ArrayList<URL> urls, String data, String delimiter) {
        StringTokenizer tokens = new StringTokenizer( data, delimiter );
        while ( tokens.hasMoreTokens() ) {
            addUrl( urls, tokens.nextToken() );
        }
    }

    private static void addUrl(ArrayList<URL> urls, String singlePath) {
        try {
            urls.add( new File( singlePath).toURI().toURL() );
        } catch ( MalformedURLException e ) {
            // don't add this class path entry to the list of class loader
            // URLs
            System.err.println( OfficeLoader.class.getName() +
                "::getCustomLoader: bad pathname: " + e );
        }
    }


    private static void callUnoinfo(String path, ArrayList<URL> urls) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(
                new String[] {
                    new File( new File(path, "program"),
                              "unoinfo").getPath(), "java" });
        } catch (IOException e) {
            System.err.println(
                OfficeLoader.class.getName() + "::getCustomLoader: exec" +
                " unoinfo: " + e);
            return;
        }
	//FIXME: perhaps remove this one & the whole Drain class entirely
	//we're not doing anything w/ content of stderr anyway
        new Drain(p.getErrorStream()).start();
        int code;
        byte[] buf = new byte[1000];
        int n = 0;
        try {
            InputStream s = p.getInputStream();
            code = s.read();
            for (;;) {
                if (n == buf.length) {
                    if (n > Integer.MAX_VALUE / 2) {
                        System.err.println(
                            OfficeLoader.class.getName() + "::getCustomLoader:" +
                            " too much unoinfo output");
                        return;
                    }
                    byte[] buf2 = new byte[2 * n];
                    System.arraycopy(buf, 0, buf2, 0, n);
                    buf = buf2;
                }
                int k = s.read(buf, n, buf.length - n);
                if (k == -1) {
                    break;
                }
                n += k;
            }
        } catch (IOException e) {
            System.err.println(
                OfficeLoader.class.getName() + "::getCustomLoader: reading" +
                " unoinfo output: " + e);
            return;
        }
        int ev;
        try {
            ev = p.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(
                OfficeLoader.class.getName() + "::getCustomLoader: waiting for" +
                " unoinfo: " + e);
            return;
        }
        if (ev != 0) {
            System.err.println(
                OfficeLoader.class.getName() + "::getCustomLoader: unoinfo"
                + " exit value " + n);
            return;
        }
        String s;
        if (code == '0') {
            s = new String(buf);
        } else if (code == '1') {
            try {
                s = new String(buf, "UTF-16LE");
            } catch (UnsupportedEncodingException e) {
                System.err.println(
                    OfficeLoader.class.getName() + "::getCustomLoader:" +
                    " transforming unoinfo output: " + e);
                return;
            }
        } else {
            System.err.println(
                OfficeLoader.class.getName() + "::getCustomLoader: bad unoinfo"
                + " output");
            return;
        }
        addUrls(urls, s, "\0");
    }

    private static final class Drain extends Thread {
        public Drain(InputStream stream) {
            super("unoinfo stderr drain");
            this.stream = stream;
        }

        @Override
        public void run() {
            try {
                while (stream.read() != -1) {}
            } catch (IOException e) { /* ignored */ }
        }

        private final InputStream stream;
    }

    /**
     * A customized class loader which is used to load classes and resources
     * from a search path of user-defined URLs.
     */
    private static final class CustomURLClassLoader extends URLClassLoader {

        public CustomURLClassLoader( URL[] urls ) {
            super( urls );
        }

        @Override
        protected Class<?> findClass( String name ) throws ClassNotFoundException {
            // This is only called via this.loadClass -> super.loadClass ->
            // this.findClass, after this.loadClass has already called
            // super.findClass, so no need to call super.findClass again:
            throw new ClassNotFoundException( name );
        }

        @Override
        protected synchronized Class<?> loadClass( String name, boolean resolve )
            throws ClassNotFoundException
        {
            Class c = findLoadedClass( name );
            if ( c == null ) {
                try {
                    c = super.findClass( name );
                } catch ( ClassNotFoundException e ) {
                    return super.loadClass( name, resolve );
                } catch ( SecurityException e ) {
                    // A SecurityException "Prohibited package name: java.lang"
                    // may occur when the user added the JVM's rt.jar to the
                    // java.class.path:
                    return super.loadClass( name, resolve );
                }
            }
            if ( resolve ) {
                resolveClass( c );
            }
            return c;
        }
    }
}
