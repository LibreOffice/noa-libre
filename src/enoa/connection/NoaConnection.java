/*
 *  This file is part of YaBS.
 *
 *      YaBS is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      YaBS is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with YaBS.  If not, see <http://www.gnu.org/licenses/>.
 */
package enoa.connection;

import ag.ion.bion.officelayer.application.*;
import ag.ion.bion.officelayer.desktop.IDesktopService;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.runtime.IOfficeProgressMonitor;
import ag.ion.noa.NOAException;

import com.sun.star.auth.InvalidArgumentException;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles connections to remote and local OpenOffice installations
 */
public class NoaConnection {

    /**
     * Indicates a Local OO Installation.
     */
    public static final int TYPE_LOCAL = 0;
    /**
     * Indicates a remote OO installation.
     */
    public static final int TYPE_REMOTE = 1;
    public static volatile boolean officeAvailable = false;

    /*
     * Define the native dll /so path
     */
    public static void definePath() {
        if (System.getProperty(IOfficeApplication.NOA_NATIVE_LIB_PATH) == null) {
            System.setProperty(IOfficeApplication.NOA_NATIVE_LIB_PATH, findUserDir().getPath() + File.separator + "lib");
        }
    }
    protected static IOfficeApplication officeAplication;
    protected int type = -1;
    protected IDocumentService documentService;
    protected IDesktopService desktopService;
    protected static NoaConnection cachedConnection;

    /**
     * Creates a connection, depending on the current local config. Should not
     * be called from EDT
     *
     * @return
     * @throws Exception
     */
    public synchronized static NoaConnection getConnection() throws OfficeApplicationException {
        definePath();
        if (cachedConnection != null) {
            return cachedConnection;
        }
        debug("Connection not established yet, trying to connect.. ");

        try {
            cachedConnection = new NoaConnection(System.getProperty("enoa.connection.path", "localhost"), Integer.valueOf(System.getProperty("enoa.connection.port", "0")));
            officeAvailable = true;
        } catch (Exception ex) {
            debug(ex);
            throw ex;
        } finally {
            return cachedConnection;
        }
    }

    /**
     * clears the onnnetion for testing puproses
     */
    public static void clearConnection() {
        cachedConnection = null;
    }
    private IOfficeProgressMonitor monitor = new IOfficeProgressMonitor() {

        @Override
        public void beginTask(String name, int totalWork) {
            debug("beginTask: " + name);
        }

        @Override
        public void worked(int work) {
            debug("worked: " + work);
        }

        @Override
        public void beginSubTask(String name) {
            debug("beginSubTask: " + name);
        }

        @Override
        public boolean needsDone() {
            return true;
        }

        @Override
        public void done() {
            debug("done");
        }

        @Override
        public void setCanceled(boolean canceled) {
        }

        @Override
        public boolean isCanceled() {
            return false;
        }
    };

    /**
     * New NoaConnection instance, connects to OO using the given parameters.
     *
     * @param connectionString The connection String. Can be a <b>Path<b/> or an
     * <b>IP<b/>
     * @param port The port to connect to. A port value of zero (0) indicates a
     * <b>local<b/> connection
     * @throws Exception If any Exception is thrown during the connection
     * attempt
     */
    private NoaConnection(String connectionString, int port) throws Exception {
        if (port <= 0) {
            createLocalConnection(connectionString);
        } else if (connectionString != null && connectionString.length() > 1 && port > 0 && port < 9999) {
            createServerConnection(connectionString, port);
        } else {
            throw new Exception("Connection not possible with the given parameters: [" + connectionString + ":" + port + "]");
        }
    }

    /**
     * dummy
     *
     * @throws Exception
     */
    public NoaConnection() throws Exception {
    }

    /**
     * Creates a new connection
     *
     * @param host
     * @param port
     * @return
     * @throws OfficeApplicationException
     * @throws NOAException
     * @throws InvalidArgumentException
     */
    private synchronized boolean createServerConnection(String host, int port) throws OfficeApplicationException, NOAException, InvalidArgumentException {
        if (host != null && port > 0) {
            Map<String, String> configuration = new HashMap<String, String>();
            configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.REMOTE_APPLICATION);
            configuration.put(IOfficeApplication.APPLICATION_HOST_KEY, host.replace("http://", ""));
            configuration.put(IOfficeApplication.APPLICATION_PORT_KEY, String.valueOf(port));
            configuration.put(IOfficeApplication.APPLICATION_ARGUMENTS_KEY, String.valueOf(port));

            officeAplication =
                    OfficeApplicationRuntime.getApplication(configuration);
            officeAplication.setConfiguration(configuration);
            try {
                officeAplication.activate(monitor);
            } catch (Throwable officeApplicationException) {
                try {
                    Thread.sleep(6666);
                } catch (InterruptedException ex) {
                }
                try {
                    officeAplication.activate(monitor);
                } catch (Throwable officeApplicationException1) {
                    debug(officeApplicationException1);
                }
            }
            if (officeAplication.isActive()) {
                documentService = officeAplication.getDocumentService();
                desktopService = officeAplication.getDesktopService();
            } else {
                throw new RuntimeException("Office " + officeAplication + " cannot get activated .. " + new ArrayList(configuration.values()));
            }
            setType(TYPE_REMOTE);
        } else {
            throw new InvalidArgumentException("Host cannot be null and port must be > 0: " + host + ":" + port);
        }

        return true;
    }

    /**
     *
     * @param OOOPath
     * @return
     * @throws OfficeApplicationException
     * @throws NOAException
     * @throws InvalidArgumentException
     */
    private synchronized boolean createLocalConnection(String OOOPath) throws OfficeApplicationException, NOAException, InvalidArgumentException {

        debug("Office connection attempt with " + String.valueOf(OOOPath));
        if (OOOPath != null && !OOOPath.equals("null")) {
            HashMap<String, String> configuration = new HashMap<String, String>();
            configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OOOPath);
            configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
            officeAplication = OfficeApplicationRuntime.getApplication(configuration);

            officeAplication.setConfiguration(configuration);
            debug("Office activation attempt.. ");
            officeAplication.activate(monitor);
            debug("Office activation attempt.. SUCCESS!");

            debug("Getting Office services.. ");
            documentService = officeAplication.getDocumentService();
            desktopService = officeAplication.getDesktopService();
            debug("Getting Office services.. SUCCESS!");
            setType(TYPE_LOCAL);
        } else {
            officeAplication = OfficeApplicationRuntime.getApplication();
            debug("Default Office activation attempt.. ");
            officeAplication.activate(monitor);
            debug("Default Office activation attempt.. SUCCESS!");

            debug("Getting Office services.. ");
            documentService = officeAplication.getDocumentService();
            desktopService = officeAplication.getDesktopService();
            debug("Getting Office services.. SUCCESS!");
            setType(TYPE_LOCAL);
        }
        return true;
    }

    /**
     * -1 indicates no connection.
     *
     * @return the type
     */
    public synchronized int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    private synchronized void setType(int type) {
        this.type = type;
    }

    /**
     * @return the documentService
     */
    public synchronized IDocumentService getDocumentService() {
        return documentService;
    }

    /**
     * @return the desktopService
     */
    public synchronized IDesktopService getDesktopService() {
        return desktopService;
    }

    /**
     * @return the IOfficeApplication or null
     */
    public IOfficeApplication getApplication() {
        return officeAplication;
    }

    private static void debug(Object o) {
        if (o instanceof Throwable) {
            Logger.getLogger(NoaConnection.class.getName()).log(Level.SEVERE, String.valueOf(o), (Throwable) o);
        }else{
            Logger.getLogger(NoaConnection.class.getName()).info(String.valueOf(o));
        }
    }

    private static File findUserDir() {
        String classPath = NoaConnection.class.getResource("NoaConnection.class").toString();
        String app = "";
        if (!classPath.startsWith("jar")) {
            // Class not from JAR FIXME 'dist' is Netbeans specific
            app = File.separator + "dist";
        }
        return new File(System.getProperty("user.dir") + app);
    }
}
