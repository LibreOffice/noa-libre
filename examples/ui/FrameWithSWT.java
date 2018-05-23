/****************************************************************************
 *                                                                          *
 * NOA (Nice Office Access)                                                 *
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          *
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2003-2006 by IOn AG                                            *
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
 ****************************************************************************/

// based loosely on Snippet14.java

import java.lang.System;
import java.util.HashMap;
import java.util.concurrent.Future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.internal.win32.OS;

import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.desktop.GlobalCommands;
import ag.ion.bion.officelayer.desktop.IFrame;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.noa.NOAException;

public class FrameWithSWT extends ApplicationWindow {
    private IOfficeApplication officeApplication=null;
    private ITextDocument textDocument=null;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public FrameWithSWT(Shell arg0)
    {
        super(arg0);
    }

    // open a very bare-bones SWT application window
    public static void main(String[] args)
    {
        try
        {
            Shell shell = new Shell(Display.getCurrent(), SWT.SHELL_TRIM);
            FrameWithSWT window =  new FrameWithSWT(shell);
            window.setBlockOnOpen(true);
            window.open();
            Display.getCurrent().dispose();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected Point getInitialSize()
    {
        return new Point(500, 375);
    }

    private IFrame officeFrame=null;
    private IDocument document=null;

    /*
     * The path to the office application, in this case on a windows system.
     *
     * On a Linux system this would look like:
     * => private final static String officeHome = "/usr/lib64/libreoffce";
     */
    private final static String OFFICE_PATH = "C:\\Program Files\\LibreOffice 5";

    // and fill it with an embedded LibreOffice Writer
    protected Control createContents(Composite parent)
    {
        Composite container = new Composite(parent, SWT.EMBEDDED);
        container.setLayout(new FillLayout());

        try {
            HashMap<String, String> configuration = new HashMap<String, String>();
            configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OFFICE_PATH);
            configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
            try {
                officeApplication=OfficeApplicationRuntime.getApplication(configuration);
                officeApplication.setConfiguration(configuration);
                officeApplication.activate();
            } catch (OfficeApplicationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // this is a bit annoying - due to SWT's tight integration
            // with the operating system's windowing system, we need
            // to decouple the SWT mainthread from _any_ UNO calls
            // that need the main thread inside LibreOffice
            Future res = executor.submit(() -> {
                    try {
                        // create empty office frame
                        officeFrame = officeApplication.getDesktopService().constructNewOfficeFrame(container);
                        // create a new document
                        document=officeApplication.getDocumentService().constructNewDocument(
                            officeFrame, IDocument.WRITER, DocumentDescriptor.DEFAULT);
                    }
                    catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
              });

            // process all pending OS events - which includes some
            // LibreOffice ones to create & setup the native operating
            // system windows
            while (!res.isDone()) {
                if (!Display.getCurrent().readAndDispatch())
                    Display.getCurrent().sleep();
            }
            // done, future is available. join threads.
            res.get();

            // frame is up, windows created - now we can start
            // interacting with the document
            try {
                textDocument=(ITextDocument) document;
                textDocument.getTextService().getText().setText("This is a text content for a SWT example with NOA.");

                officeFrame.disableDispatch(GlobalCommands.CLOSE_DOCUMENT);
                officeFrame.disableDispatch(GlobalCommands.QUIT_APPLICATION);

                // activate toplevel window
                getShell().forceActive();

                // Windows-only: set focus to contained
                // LibreOffice. Embed protocol not properly working...
                if (System.getProperty("os.name").startsWith("Windows"))
                {
                    long hwndChild = OS.GetWindow(container.handle,
                                                  OS.GW_CHILD);
                    OS.SetFocus(hwndChild);
                }
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return container;
    }

    @Override
    public boolean close() {
        // with SWT, we need to decouple all windowing-system
        // originating messages before calling into UNO -
        // otherwise there's an annoying re-entrancy problem, with
        // the SWT mainloop waiting for an answer, and the
        // LibreOffice mainloop not processing things, see above
        executor.submit(() -> {
                try
                {
                    textDocument.close();
                    officeApplication.deactivate();
                    officeApplication.getDesktopService().terminate();
                }
                catch (OfficeApplicationException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (NOAException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });

        // Seppuku for our executors, once all tasks have finished
        executor.shutdown();

        return super.close();
    }
}
