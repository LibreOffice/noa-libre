/*
 *      This is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License Version 2.1 as published by
 *      the Free Software Foundation, either version 2.1 of the License, or
 *      (at your option) any later version.
 *
 *      This is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *      GNU Lesser General Public License Version 2.1 for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License Version 2.1
 *      along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package ag.ion.bion.officelayer.application.connection;

/**
 *
 * @author anti43
 */
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.desktop.IDesktopService;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.noa.NOAException;

import com.sun.star.auth.InvalidArgumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *This class handles connections to remote and local OpenOffice installations
 */
public class SimpleConnection {

    /**
     * Indicates a Local OO Installation.
     */
    public static final int TYPE_LOCAL = 0;
    /**
     * Indicates a remote OO installation.
     */
    public static final int TYPE_REMOTE = 1;
    private IOfficeApplication officeAplication;
    private int type = -1;
    private IDocumentService documentService;
    private IDesktopService desktopService;
    private static SimpleConnection Connection;

    /**
     * Creates a connection, depending on the current local config.
     * @return
     */
    public synchronized static SimpleConnection getConnection() {
        Properties props = System.getProperties();
        if (props.containsKey("OFFICE_HOST")) {
            if (Connection == null) {
                try {
                    Connection = new SimpleConnection(props.getProperty("OFFICE_HOST"), Integer.valueOf(props.getProperty("OFFICE_PORT")));

                } catch (Exception ex) {
                    Logger.getLogger(SimpleConnection.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
            return Connection;
        } else {
            throw new UnsupportedOperationException("OpenOffice is not configured yet.");
        }
    }

    /**
     * New NoaConnection instance, connects to OO using the given parameters.
     * @param connectionString The connection String. Can be a <b>Path<b/> or an <b>IP<b/>
     * @param port The port to connect to. A port value of zero (0) indicates a <b>local<b/> connection
     * @throws Exception If any Exception is thrown during the connection attempt
     */
    public SimpleConnection(String connectionString, int port) throws Exception {
        if (connectionString != null && connectionString.length() > 1 && port <= 0) {
            createLocalConnection(connectionString);
        } else if (connectionString != null && connectionString.length() > 1 && port > 0 && port < 9999) {
            createServerConnection(connectionString, port);
        } else {
            throw new Exception("Connection not possible with the given parameters: [" + connectionString + ":" + port + "]");
        }
    }

    /**
     * Creates a new connection
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
            configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY,
                    IOfficeApplication.REMOTE_APPLICATION);
            configuration.put(IOfficeApplication.APPLICATION_HOST_KEY, host.replace("http://", ""));
            configuration.put(IOfficeApplication.APPLICATION_PORT_KEY, String.valueOf(port));

            officeAplication =
                    OfficeApplicationRuntime.getApplication(configuration);
            officeAplication.setConfiguration(configuration);
            try {
                officeAplication.activate();
            } catch (Exception officeApplicationException) {
                try {
                    Thread.sleep(6666);
                } catch (InterruptedException ex) {
                }
                try {
                    officeAplication.activate();
                } catch (OfficeApplicationException officeApplicationException1) {
                    Logger.getLogger(SimpleConnection.class.getName()).log(Level.SEVERE, null, officeApplicationException1);
                }
            }
            documentService =
                    officeAplication.getDocumentService();
            desktopService =
                    officeAplication.getDesktopService();
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
        if (OOOPath != null) {
            Map<String, String> configuration = new HashMap<String, String>();
            configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OOOPath);
            configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY,
                    IOfficeApplication.LOCAL_APPLICATION);

            officeAplication =
                    OfficeApplicationRuntime.getApplication(configuration);
            officeAplication.setConfiguration(configuration);
            officeAplication.activate();
            documentService =
                    officeAplication.getDocumentService();
            desktopService =
                    officeAplication.getDesktopService();

            setType(TYPE_LOCAL);
        } else {
            throw new InvalidArgumentException("Path to OO cannot be null: " + OOOPath);
        }

        return true;
    }

    /**
     *  -1 indicates no connection.
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
     * Tries to start OO in headless server mode. <code>Give it at least 3-4 seconds before attempting to use the server.</code>
     * @param path The path where the OO installation resides
     * @param port The port the server shall listen to
     * @throws IOException
     */
    public synchronized static void startOOServerIfNotRunning(final String path, final int port) {
        final Properties props = System.getProperties();
        final String command = path.replace("\\", "\\\\") + File.separator + props.getProperty("OFFICE_BINARY_FOLDER") + File.separator + "soffice" + " "
                + "-headless" + " "
                + "-nofirststartwizard" + " "
                + "-norestore" + " "
                + "-nolockcheck" + " "
                + "-nocrashreport" + " "
                + "-nodefault" + " "
                + "-accept='socket,host=0.0.0.0,port=" + port + ";urp;StarOffice.Service'";

        try {
            SocketAddress addr = new InetSocketAddress("127.0.0.1", port);
            Socket socket = new Socket();
            socket.connect(addr, 100);
            throw new UnsupportedOperationException("Port " + port + " is already in use :-/. Not going to start OO here.");
        } catch (IOException iOException) {
            //nothing is running here
        }

        final ProcessBuilder builder = new ProcessBuilder(command);

        Map<String, String> environment = builder.environment();
        environment.put("path", ";"); // Clearing the path variable;
        environment.put("path", path.replace("\\", "\\\\") + File.pathSeparator);

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Process oos = builder.start();
                    InputStream is = oos.getErrorStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        Logger.getLogger(SimpleConnection.class.getName()).log(Level.INFO, null, line);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SimpleConnection.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        new Thread(runnable).start();

    }
}
