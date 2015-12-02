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
package enoa.handler;

import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.DocumentException;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.filter.*;
import ag.ion.bion.officelayer.form.IFormComponent;
import ag.ion.bion.officelayer.text.ITextContentService;
import ag.ion.bion.officelayer.text.ITextCursor;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextDocumentImage;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.bion.officelayer.text.ITextFieldService;
import ag.ion.bion.officelayer.text.ITextService;
import ag.ion.bion.officelayer.text.IVariableTextFieldMaster;
import ag.ion.bion.officelayer.text.TextException;
import ag.ion.noa.NOAException;
import ag.ion.noa.filter.OpenDocumentFilter;
import ag.ion.noa.graphic.GraphicInfo;
import com.sun.star.awt.XTextComponent;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.form.XFormComponent;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.text.HoriOrientation;
import com.sun.star.text.TextContentAnchorType;
import com.sun.star.text.VertOrientation;
import com.sun.star.uno.UnoRuntime;
import enoa.connection.NoaConnection;
import enoa.connection.URLAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.table.TableModel;

/**
 * This class OpenOffice Documents IO
 */
public class DocumentHandler {

    /**
     * All known OO file extensions
     */
    public static final String EXTENSION = ".*ott$|.*sxw$|.*doc$|.*xls$|.*odt$|.*ods$|.*pps$|.*odt$|.*ppt$|.*odp$";
    /**
     * A FileFilter looking for OO files
     */
    public static FileFilter OFFICE_FILE_FILTER = new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            return pathname.getName().matches(EXTENSION);
        }
    };
    private final NoaConnection connection;
    private final DocumentDescriptor descriptor;
    private IDocument document;
    private ITextFieldService textFieldService;
    private ITextField[] placeholders;
    private TableHandler tablehandler;
    private File file;

    /**
     * Creates a new (hidden) Document Handler on top of the given connection
     *
     * @param connection The OO connection to use
     */
    public DocumentHandler(NoaConnection connection) {
        if (connection != null) {
            this.connection = connection;
            descriptor = DocumentDescriptor.DEFAULT_HIDDEN;
        } else {
            throw new NullPointerException("Connection cannot be null");
        }
    }

    /**
     * Load an existing document into the Document Handler
     *
     * @param file The file to load
     * @param asTemplate If true, the file is treatened as template (.ott)
     * @throws Exception Any error thrown
     */
    public void loadDocument(File file, boolean asTemplate) throws Exception {
        if (!OFFICE_FILE_FILTER.accept(file)) {
            throw new UnsupportedOperationException("The file extension must match: " + EXTENSION);
        }
        if (asTemplate) {
            descriptor.setAsTemplate(asTemplate);
        }
        this.file = file;
        descriptor.setHidden(true);
        clear();
    }

    /**
     * Creates a new, empty text document (.odt)
     *
     * @throws Exception
     */
    public ITextDocument createTextDocument() throws Exception {
        IDocument d = connection.getDocumentService().constructNewDocument(IDocument.WRITER, descriptor);
        document = d;
        return (ITextDocument) d;
    }

    /**
     * Save the given document to the physical location of the given file.
     *
     * @param file
     * @throws DocumentException
     */
    public synchronized void saveAs(File file) throws DocumentException {

        if (file.exists()) {
            file.delete();
        }

        document.reformat();
        document.update();

        if (file.getName().split("\\.").length < 2) {
            throw new UnsupportedOperationException("The file must have an extension: " + file);
        }

        IFilter filter = null;
        String extension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
        if (extension.equalsIgnoreCase(".pdf")) {
            filter = PDFFilter.FILTER;
        } else if (extension.equalsIgnoreCase(".doc")) {
            filter = MSOffice97Filter.FILTER;
        } else if (extension.equalsIgnoreCase(".txt")) {
            filter = TextFilter.FILTER;
        } else if (extension.equalsIgnoreCase(".odt")) {
            filter = OpenDocumentFilter.FILTER;
        } else if (extension.equalsIgnoreCase(".html")) {
            filter = HTMLFilter.FILTER;
        }


        if (filter != null) {
            try {
                debug("Exporting to: " + file);
                document.getPersistenceService().export(new FileOutputStream(file), filter);

            } catch (Exception ex) {
                debug(ex);
            }
        } else {
            throw new UnsupportedOperationException("File extension not supported: " + extension);
        }
    }

    /**
     * Close the underlying doc
     */
    public void close() {
        document.close();
    }

    /**
     * Fill the Form Fields of the template with values
     *
     * @param data
     * @throws Exception
     * @throws NOAException
     * @deprecated slow :-/
     */
    public synchronized void fillFormFields(HashMap<String, Object> data) throws Exception, NOAException {
        debug("Looking for form fields in: " + document);
        IFormComponent[] formComponents = document.getFormService().getFormComponents();
        Iterator<String> keys = data.keySet().iterator();
        String key = null;
        while (keys.hasNext()) {

            // get column name
            key = keys.next();
//            debug( "Found key: " + key + " [" + data.get(key) + "]");
            for (int i = 0; i < formComponents.length; i++) {

                XFormComponent xFormComponent = formComponents[i].getXFormComponent();
                XTextComponent xTextComponent = formComponents[i].getXTextComponent();
                XPropertySet propertySet = UnoRuntime.queryInterface(XPropertySet.class,
                        xFormComponent);

                if (propertySet != null && propertySet.getPropertySetInfo().hasPropertyByName("Name")) {
                    String n = propertySet.getPropertyValue("Name").toString();
//                    debug( "Found form field: " + n);
                    if (n.equalsIgnoreCase(key) || key.endsWith(n)) {
                        debug("Form field matches key: " + key + " [" + data.get(key) + "]");
                        xTextComponent.setText(String.valueOf(data.get(key)));
                    }
                }
            }
//            textDocument.getTextFieldService().refresh();
        }
    }

    /**
     * Fill the Placeholder Fields of the template with values
     *
     * @param data
     * @throws Exception
     * @throws NOAException
     */
    public synchronized void fillPlaceholderFields(HashMap<String, Object> data) throws Exception, NOAException {
        debug("Looking for placeholder fields in: " + document);
        Iterator<String> keys = data.keySet().iterator();
        String key = null;
        String[] placehrepr = new String[0];
        List<ITextField> fields = new ArrayList<ITextField>();
        if (textFieldService == null || placeholders == null) {
            textFieldService = ((ITextDocument) document).getTextFieldService();
            placeholders = textFieldService.getPlaceholderFields();
            placehrepr = new String[placeholders.length];
            for (int i = 0; i < placeholders.length; i++) {
                placehrepr[i] = placeholders[i].getDisplayText();
                fields.add(placeholders[i]);
            }
            debug("Got: " + Arrays.asList(placehrepr));
        }
        InputStream is = null;
        ITextCursor cursor;

        while (keys.hasNext()) {
            key = keys.next();
            try {
                for (int placeholderIndex = 0; placeholderIndex < placehrepr.length; placeholderIndex++) {
                    String placeholderDisplayText = placehrepr[placeholderIndex];
//                    debug( "Check placeholder key: " + placeholderDisplayText);
                    if (placeholderDisplayText.equalsIgnoreCase(key) || placeholderDisplayText.equalsIgnoreCase("<" + key + ">")
                            || placeholderDisplayText.equalsIgnoreCase("<RTF." + key + ">")) {
                        debug("Found placeholder key: " + key + " [" + data.get(key) + "]");
                        if (placeholderDisplayText.startsWith("<RTF.") || placeholderDisplayText.startsWith("<rtf.")) {
                            final String s = ((String) data.get(key)).replace("`", "'");
                            is = new ByteArrayInputStream(s.getBytes("UTF-8"));
                            cursor = placeholders[placeholderIndex].getTextDocument().
                                    getViewCursorService().
                                    getViewCursor().
                                    getTextCursorFromStart();
                            cursor.gotoRange(placeholders[placeholderIndex].getTextRange(),
                                    false);
                            cursor.insertDocument(is,
                                    RTFFilter.FILTER);
//                            placeholders[i].getTextRange().
//                                    setText("");
                        } else if (placeholderDisplayText.endsWith("image>")) {
                            debug("Found placeholder for image: " + key);
                            File xfile = new File(String.valueOf(data.get(key)));
                            if (xfile.exists()) {
                                try {
                                    cursor = placeholders[placeholderIndex].getTextDocument().
                                            getViewCursorService().
                                            getViewCursor().
                                            getTextCursorFromStart();
                                    cursor.gotoRange(placeholders[placeholderIndex].getTextRange(), false);
                                    BufferedImage bimg = ImageIO.read(xfile);
                                    int width = bimg.getWidth();
                                    int height = bimg.getHeight();
                                    setImage(new FileInputStream(xfile), cursor, height, width);
                                } catch (Exception ex) {
                                    debug(ex);
                                }
                            } else {
                                debug("File does not exist: " + xfile);
                            }
                        } else {
                            placeholders[placeholderIndex].getTextRange().setText(String.valueOf(data.get(key)));
                        }
                    }
                }
            } catch (java.lang.Exception ex) {
                debug(ex);
            }
        }
        if (System.getProperty("enoa.documenthandler.blankunusedfields.disable", "false").equals("false")) {
            for (int i = 0; i < fields.size(); i++) {
                try {
                    ITextField xTextComponent = fields.get(i);
                    if (xTextComponent.getXTextContent().getAnchor() != null) {
                        debug("Filling unspecified field: " + xTextComponent.getDisplayText());
                        xTextComponent.getTextRange().setText("");
                    }
                } catch (Throwable ex) {
                    debug(ex);
                }
            }
        }
    }

    /**
     * Fill the Variable Text Fields of the template with values
     *
     * @param data
     * @throws Exception
     * @throws NOAException
     * @deprecated slow :_/
     */
    public synchronized void fillTextVariableFields(HashMap<String, Object> data) throws Exception, NOAException {
        debug("Looking for variable fields in: " + document);
        Iterator<String> keys = data.keySet().iterator();
        String key = null;
        IVariableTextFieldMaster x;

        while (keys.hasNext()) {
            // get column name
            key = keys.next();
            if (textFieldService == null) {
                textFieldService = ((ITextDocument) document).getTextFieldService();
            }
            x = textFieldService.getVariableTextFieldMaster(key);

            if (x != null) {
                ITextField[] variables = x.getVariableTextFields();
                for (int i = 0; i < variables.length; i++) {
                    XPropertySet xPropertySetField = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, variables[i].getXTextContent());
                    if (xPropertySetField.getPropertyValue("CurrentPresentation").toString().equalsIgnoreCase(key)) {
                        debug("Found variable key: " + key + " [" + data.get(key) + "]");
                        xPropertySetField.setPropertyValue("Content", data.get(key));
                    }
                }
            }
        }
    }

    /**
     * Export a file to another format/file. Supported target formats: <li>pdf
     * (pdf/a) <li>odt <li>txt
     *
     * @param source The file to export
     * @param target The target file
     * @return The target file
     * @throws DocumentException
     */
    public File export(File source, File target) throws DocumentException {
        if (target.getName().split("\\.").length < 2) {
            throw new UnsupportedOperationException("The file must have an extension: " + target);
        }

        IFilter filter = null;
        String extension = target.getName().substring(target.getName().lastIndexOf("."), target.getName().length());
        if (extension.equalsIgnoreCase(".pdf")) {
            if (System.getProperty("enoa.documenthandler.pdf-a.enable", "false").equals("true")) {
                try {
                    return exportPDFA(source, target);
                } catch (Exception ex) {
                    debug(ex);
                }
            } else {
                filter = PDFFilter.FILTER;
            }
        } else if (extension.equalsIgnoreCase(".doc")) {
            filter = MSOffice97Filter.FILTER;
        } else if (extension.equalsIgnoreCase(".txt")) {
            filter = TextFilter.FILTER;
        } else if (extension.equalsIgnoreCase(".odt")) {
            filter = OpenDocumentFilter.FILTER;
        } else if (extension.equalsIgnoreCase(".html")) {
            filter = HTMLFilter.FILTER;
        }

        if (filter != null) {
            connection.getDocumentService().loadDocument(source.getAbsolutePath()).getPersistenceService().export(target.getPath(), filter);
        } else {
            throw new UnsupportedOperationException("File extension not supported: " + extension);
        }

        return target;
    }

    private File exportPDFA(File source, File target) throws DocumentException, MalformedURLException, FileNotFoundException, IOException, UnknownHostException {

        IDocument doc = connection.getDocumentService().loadDocument(new FileInputStream(source), descriptor);

        PDFFilter pdfFilter = (PDFFilter) PDFFilter.FILTER;
        /*PDFFilterProperties pdfFilterProperties = pdfFilter.getPDFFilterProperties();
        pdfFilterProperties.setPdfVersion(1);
        doc.getPersistenceService().export(url, pdfFilter);*/

        PropertyValue[] filterData = new PropertyValue[1];
        filterData[0] = new PropertyValue();
        filterData[0].Name = "SelectPdfVersion";
        filterData[0].Value = new Integer(1); //0: normal 1.4, 1: PDF/A

        String filterDefinition = pdfFilter.getFilterDefinition(doc);
        PropertyValue[] properties = new PropertyValue[2];
        properties[0] = new PropertyValue();
        properties[0].Name = "FilterName"; //$NON-NLS-1$
        properties[0].Value = filterDefinition;
        properties[1] = new PropertyValue();
        properties[1].Name = "FilterData";
        properties[1].Value = filterData;


        String url = URLAdapter.adaptURL(target.getPath());

        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,
                doc.getXComponent());
        xStorable.storeToURL(url, properties);

        return target;
    }

    /**
     * Print the document directly
     */
    public void print() {
        try {
            document.getPrintService().print();
        } catch (DocumentException ex) {
            debug(ex);
        }
    }

    /**
     * Fills the tables in an .odt file
     *
     * @param data
     * @param template
     */
    public void fillTable(TableModel model, String documentTableName) {
        debug("Looking for tables in: " + document);
        try {
            TableModel m = model;
            if (tablehandler == null) {
                tablehandler = new TableHandler((ITextDocument) document, documentTableName);
            }
            for (int j = 0; j < m.getRowCount(); j++) {
                String[] strings = new String[m.getColumnCount()];
                for (int k = 0; k < strings.length; k++) {
                    if (m.getValueAt(j, k) != null) {
                        strings[k] = String.valueOf(m.getValueAt(j, k));
                    }
                }
                doRow(strings, j);
            }
        } catch (Exception e) {
            debug(e);
        }
    }
    public static final String linkstartidentifier = "[url]";
    public static final String linkendidentifier = "[/url]";

    /**
     * Reset the doc
     *
     * @throws DocumentException
     */
    public void clear() throws DocumentException {
        try {
            debug("Trying to load: " + URLAdapter.adaptURL(file.getPath()));
//            document = connection.getDocumentService().loadDocument(file.getPath().replace("\\", "/"), descriptor);
            if (connection.getType() == NoaConnection.TYPE_REMOTE) {
                document = connection.getDocumentService().loadDocument(new FileInputStream(file), descriptor);
            } else {
                document = connection.getDocumentService().loadDocument(file.toURI().toURL().toString(), descriptor);
            }
        } catch (Exception ex) {
            debug(ex);
        }
        textFieldService = null;
        placeholders = null;
        tablehandler = null;
    }

    /**
     * Set some image
     *
     * @param data
     */
    public void setImage(FileInputStream imagePath, ITextCursor textCursor, int pixelHeight, int pixelWidth) {
        try {
            GraphicInfo graphicInfo = new GraphicInfo(imagePath, pixelWidth,
                    true, pixelHeight, true, VertOrientation.TOP, HoriOrientation.LEFT,
                    TextContentAnchorType.AT_PARAGRAPH);

            ITextService textService = ((ITextDocument) document).getTextService();
            ITextContentService textContentService = textService.getTextContentService();
            ITextDocumentImage textDocumentImage = textContentService.constructNewImage(graphicInfo);
            textContentService.insertTextContent(textCursor.getEnd(), textDocumentImage);
        } catch (Exception ex) {
            Logger.getLogger(DocumentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doRow(String[] strings, int row) {
        debug("Filling row: " + Arrays.asList(strings).toString());
        for (int j = 0; j < strings.length; j++) {
            String cellValue = strings[j];
            if (cellValue == null) {
                cellValue = "";
            }
            cellValue = cellValue.replaceAll("\\<.*?\\>", "");//remove html/xml tags
            if (!cellValue.contains("://")) {//lets say its a valid url
                try {
                    tablehandler.setValueAt(cellValue, j, row);
                } catch (TextException ex) {
                    debug(ex);
                }
            } else {
                try {
                    String linkname = "Link";
                    if (cellValue.contains("@")) {
                        String link1 = cellValue.split("@")[1];
                        linkname = cellValue.split("@")[0];
                        cellValue = link1;
                    }
                    tablehandler.setHyperlinkAt(linkname, cellValue, j, row);
                } catch (Exception ex) {
                    debug(ex);
                }
            }
        }
    }

    private static void debug(Object string) {
        Logger.getLogger(DocumentHandler.class.getName()).info(String.valueOf(string));
    }
}
