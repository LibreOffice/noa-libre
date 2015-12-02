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

import ag.ion.bion.officelayer.text.IText;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextTable;
import ag.ion.bion.officelayer.text.ITextTableService;
import ag.ion.bion.officelayer.text.TextException;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.text.XText;
import com.sun.star.text.XTextCursor;
import com.sun.star.uno.UnoRuntime;

/**
 * This class handles tables withinh OO files
 */
public class TableHandler {

    private final ITextDocument doc;
    private final ITextTableService tableService;
    private final ITextTable table;

    /**
     * Creates a new TableHandler with a new table in it, containing the given values
     * @param doc
     * @param values
     * @throws TextException
     */
    public TableHandler(ITextDocument doc, Object[][] values) throws TextException {
        this.doc = doc;
        this.tableService = doc.getTextTableService();
        table = addTable(values);
    }

    /**
     * Creates a new TableHandler for the table with the given table name inside the given document
     * @param doc
     * @param tableName
     * @throws TextException
     * @throws IllegalArgumentException
     */
    public TableHandler(ITextDocument doc, String tableName) throws TextException, IllegalArgumentException {
        this.doc = doc;
        this.tableService = doc.getTextTableService();
        try {
            table = tableService.getTextTable(tableName);
        } catch (TextException textException) {
            throw new IllegalArgumentException("Table not in document: " + tableName);
        }
    }

    /**
     * The count of rows in the table
     * @return
     */
    public int getRowCount() {
        return getTable().getRowCount();
    }

    /**
     * The count of columns in the table
     * @return
     */
    public int getColumnCount() {
        return getTable().getColumnCount();
    }

    /**
     * Append the specified amount of rows to the table
     * @param count
     * @throws TextException
     */
    public void addRows(int count) throws TextException {
        getTable().addRow(count);
    }

    /**
     * Append the specified amount of cols to the table
     * @param count
     * @throws TextException
     */
    public void addColumns(int count) throws TextException {
        getTable().addColumn(count);
    }

    /**
     * Specify how many lines shall be formatted in header style
     * @param lines
     * @throws TextException
     */
    public void setHeaderLines(int lines) throws TextException {
        getTable().setHeaderRows(3);
    }

    /**
     * Set the value at the given position. If the given row exceeds the current number of rows, the rows are appended to the end of the table.
     * @param value
     * @param column
     * @param row
     * @throws TextException
     */
    public synchronized void setValueAt(Object value, int column, int row) throws TextException {

        if (getRowCount() <= row) {
            addRows(row - getRowCount() + 1);
        }

        if (getColumnCount() <= column) {
            addColumns(column - getColumnCount() + 1);
        }

        if (value != null && value instanceof Double) {
            getTable().getCell(column, row).setValue(Double.valueOf(value.toString()));
        } else if (value != null) {
            getTable().getCell(column, row).getTextService().getText().setText(value.toString().replace("\\n", "\n"));
        }
    }

    /**
     * Set the link at the given position. If the given row exceeds the current number of rows, the rows are appended to the end of the table.
     * @param value
     * @param link
     * @param column
     * @param row
     * @throws TextException
     * @throws UnknownPropertyException
     * @throws PropertyVetoException
     * @throws IllegalArgumentException
     * @throws WrappedTargetException
     */
    public synchronized void setHyperlinkAt(Object value, String link, int column, int row) throws TextException, UnknownPropertyException, PropertyVetoException, IllegalArgumentException, WrappedTargetException {

        if (getRowCount() <= row) {
            addRows(row - getRowCount() + 1);
        }

        if (getColumnCount() <= column) {
            addColumns(column - getColumnCount() + 1);
        }

        IText text = getTable().getCell(column, row).getTextService().getText();
        XText xText = text.getXText();
        XTextCursor xTextCursor = xText.createTextCursor();
        xText.insertString(xTextCursor, value.toString(), true);
        XPropertySet xPropertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTextCursor);
        xPropertySet.setPropertyValue("HyperLinkURL", link);
    }

    /**
     * Returns the value at the given position
     * @param column
     * @param row
     * @return
     * @throws TextException
     */
    public Object getValueAt(int column, int row) throws TextException {
        return getTable().getCell(column, row).getValue();
    }

    /**
     * Set the width of the given column.
     * @param column
     * @param width
     * @throws TextException
     */
    public void setColumnWidth(int column, short width) throws TextException {
        getTable().getColumn(column).setWidth(width);
    }

    /**
     * Add a table to the current document
     * @param values
     * @return
     * @throws TextException
     */
    public ITextTable addTable(Object[][] values) throws TextException {
        ITextTable textTable = null;
        try {
            textTable = getTableService().constructTextTable(values.length, values[0].length);
        } catch (IndexOutOfBoundsException ex) {
            textTable = getTableService().constructTextTable(values.length, 0);
        }

        getDoc().getTextService().getTextContentService().insertTextContent(textTable);

        for (int i = 0; i < values.length; i++) {
            Object[] objects = values[i];
            for (int j = 0; j < objects.length; j++) {
                Object object = objects[j];
                setValueAt(object, j, i);
            }
        }
        return textTable;
    }

    /**
     * @return the doc
     */
    public ITextDocument getDoc() {
        return doc;
    }

    /**
     * @return the tableService
     */
    public ITextTableService getTableService() {
        return tableService;
    }

    /**
     * @return the table
     */
    public ITextTable getTable() {
        return table;
    }
}
