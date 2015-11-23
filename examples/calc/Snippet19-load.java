/****************************************************************************
 *                                                                          *
 * NOA-Libre                                        						*
 * ------------------------------------------------------------------------ *
 *                                                                          *
 * The Contents of this file are made available subject to                  *
 * the terms of GNU Lesser General Public License Version 2.1.              *
 *                                                                          * 
 * GNU Lesser General Public License Version 2.1                            *
 * ======================================================================== *
 * Copyright 2012-2013 by the NOA-Libre project                             *
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
 *  http://code.google.com/p/noa-libre/										*
 *                                                                          *
 ****************************************************************************/

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ag.ion.bion.officelayer.NativeView;
import ag.ion.bion.officelayer.application.IApplicationAssistant;
import ag.ion.bion.officelayer.application.ILazyApplicationInfo;
import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationException;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.desktop.DesktopException;
import ag.ion.bion.officelayer.desktop.IFrame;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.DocumentException;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.internal.application.ApplicationAssistant;
import ag.ion.bion.officelayer.spreadsheet.ISpreadsheetDocument;
import ag.ion.noa.NOAException;

import com.sun.star.container.NoSuchElementException;
import com.sun.star.lang.IndexOutOfBoundsException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.sheet.XSheetCellCursor;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.XCell;
import com.sun.star.text.XText;
import com.sun.star.uno.UnoRuntime;

//(2a*)
//(3a)

public class Loewe extends JFrame {

	private JPanel NOApanel;
	private IOfficeApplication officeApplication;
	private NativeView nat;
	private IFrame officeFrame;
	private ISpreadsheetDocument /* (3e) */document;
	private XSpreadsheets spreadsheets;
	private XSpreadsheet spreadSheet1;
	private XSheetCellCursor cellCursor;

	// (2b*)
	// (3b)
	public Loewe() {
		setVisible(true);
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (document != null) {
					document.close();
				}
				if (officeApplication != null) {
					try {
						officeApplication.deactivate();
					} catch (OfficeApplicationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				super.windowClosing(e);
			}

		});

		NOApanel = new JPanel();

		getContentPane().setLayout(new GridLayout());
		getContentPane().add(NOApanel);
		IApplicationAssistant ass;
		try {
			ass = new ApplicationAssistant(System.getProperty("user.dir")
					+ File.separator + "lib");
			ILazyApplicationInfo[] ila = ass.getLocalApplications();

			HashMap configuration = new HashMap();
			configuration.put(IOfficeApplication.APPLICATION_HOME_KEY,
					ila[0].getHome());
			configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY,
					IOfficeApplication.LOCAL_APPLICATION);

			officeApplication = OfficeApplicationRuntime
					.getApplication(configuration);
			officeApplication.activate();
			nat = new NativeView(System.getProperty("user.dir")
					+ File.separator + "lib");
			nat.setSize(NOApanel.getWidth(), NOApanel.getHeight());
			NOApanel.addComponentListener(new ComponentAdapter() {

				@Override
				public void componentResized(ComponentEvent e) {
					nat.setPreferredSize(new Dimension(NOApanel.getWidth(),
							NOApanel.getHeight()));
					NOApanel.getLayout().layoutContainer(NOApanel);
					super.componentResized(e);
				}

			});

			NOApanel.add(nat);
			NOApanel.setVisible(true);
			officeFrame = officeApplication.getDesktopService()
					.constructNewOfficeFrame(nat);
			document = /* (3f) */(ISpreadsheetDocument) officeApplication
					.getDocumentService().loadDocument(officeFrame, "C:\\Users\\jstaerk\\Documents\\finanzen.ods", DocumentDescriptor.DEFAULT);
/*			spreadsheets = document.getSpreadsheetDocument().getSheets();
			spreadSheet1 = (XSpreadsheet) UnoRuntime.queryInterface(
					XSpreadsheet.class,
					spreadsheets.getByName(spreadsheets.getElementNames()[0]));

			cellCursor = spreadSheet1.createCursor();
			XCell cell = null;
			cell = cellCursor.getCellByPosition(0, 1);
			cell.setValue(10);
			cell = cellCursor.getCellByPosition(0, 2);
			cell.setValue(12.2);
			cell = cellCursor.getCellByPosition(1, 1);
			cell.setValue(22.2);
			cell = cellCursor.getCellByPosition(0, 0);
			XText cellText = UnoRuntime.queryInterface(XText.class, cell);

			cellText.setString("Accounts receivables");*/
			
		} catch (OfficeApplicationException e1) {
			e1.printStackTrace();
		} catch (DesktopException e1) {
			e1.printStackTrace();
		} catch (NoSuchElementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WrappedTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IndexOutOfBoundsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Loewe l = new Loewe();

	}

}