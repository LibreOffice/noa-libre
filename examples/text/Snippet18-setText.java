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
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.internal.application.ApplicationAssistant;
import ag.ion.bion.officelayer.text.IParagraph;
import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.noa.NOAException;

import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.sheet.XSheetCellCursor;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheets;

//(2a*)
//(3a)

public class Loewe extends JFrame {

	private JPanel NOApanel;
	private IOfficeApplication officeApplication;
	private NativeView nat;
	private IFrame officeFrame;
	private ITextDocument /* (3e) */document;
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

			HashMap<String, String> configuration = new HashMap<String, String>();
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
			document = /* (3f) */(ITextDocument) officeApplication
					.getDocumentService().constructNewDocument(officeFrame, /*(3g)*/
							IDocument.WRITER, DocumentDescriptor.DEFAULT);
			

			// Quick and dirty
			document.getTextService().getText().setText("Hello World Test Text");
			
			String temp = document.getTextService().getText().getText();
			document.getTextService().getText().setText(temp + "\n" + "Next Line in text");	
			// now cleaner:
			// first we create a text cursor 
			ITextCursor textCursor = document.getTextService().getCursorService().getTextCursor();
			
			// then we go to the end of the text
			textCursor.gotoEnd(false);
			// and now we use setText on textCursor:

	  		textCursor.getEnd().setText("One..."); //we place the text
	  		textCursor.getEnd().setText("\n"); // and we wrap the line
	  		textCursor.getEnd().setText("Two..."); //we place the text
	  		textCursor.getEnd().setText("\n"); // and we wrap the line

			// now the cleanest way: using textcursor, we place a paragraph
			IParagraph paragraph=document.getTextService().getTextContentService().constructNewParagraph();
			// insert the paragraph
			document.getTextService().getTextContentService().insertTextContent(textCursor.getEnd(), paragraph);
			// and finally set the text
			paragraph.setParagraphText("Hello World!");
			
			paragraph.getCharacterProperties().setFontSize(36);
			paragraph.getCharacterProperties().setFontUnderline(true);
			// setFontColor is apparently expecting RGB values
			paragraph.getCharacterProperties().setFontColor(0xff8080);
			paragraph.getCharacterProperties().getXPropertySet().setPropertyValue("CharFontName","Arial");

		} catch (OfficeApplicationException e1) {
			e1.printStackTrace();
		} catch (DesktopException e1) {
			e1.printStackTrace();
		} catch (NOAException e1) {
			e1.printStackTrace();
		} catch (TextException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownPropertyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WrappedTargetException e1) {
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
