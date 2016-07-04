/**
 * This code snippet shows how to insert data in a calc document.
 * Currently with UNO API access as it is not yet implemented in NOA.
 *
 * @author Markus Krüger
 */

package simplecalctest;

import java.util.HashMap;

import com.sun.star.sheet.XSheetCellCursor;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.XCell;
import com.sun.star.text.XText;
import com.sun.star.uno.UnoRuntime;

import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.spreadsheet.ISpreadsheetDocument;
import ag.ion.bion.officelayer.util.OfficeLoader;


public class SimpleCalcTest {

    private final static String OPEN_OFFICE_ORG_PATH = "/usr/lib64/libreoffice";
    public static HashMap<String, String> configuration = new HashMap<String, String>();
    static {
        configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
        configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
    }

    public static void main(String[] args)
    {
        OfficeLoader.init(configuration);

        try {
           OfficeLoader.run( new String[]{"simplecalctest.SimpleCalcTest$SimpleCalcTestCore"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class SimpleCalcTestCore {
        public static void main(String[] args) {
            try {
                IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);
                officeAplication.setConfiguration(configuration);
                officeAplication.activate();
                IDocumentService documentService = officeAplication.getDocumentService();
                IDocument document = documentService.constructNewDocument(IDocument.CALC, DocumentDescriptor.DEFAULT);
                ISpreadsheetDocument spreadsheetDocument = (ISpreadsheetDocument) document;
                XSpreadsheets spreadsheets = spreadsheetDocument.getSpreadsheetDocument().getSheets();
                String sheetName= "Sheet1";
                Object[][] rows = new Object[][]{
                    new Object[]{"DataCell1","DataCell2","DataCell3","DataCell4"},
                    new Object[]{new Integer(10),new Integer(20),new Integer(30),new Integer(40)},
                    new Object[]{new Double(11.11),new Double(22.22),new Double(33.33),new Double(44.44)}};

                XSpreadsheet spreadsheet1 = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class,spreadsheets.getByName(sheetName));
                //insert your Data
                XSheetCellCursor cellCursor = spreadsheet1.createCursor();

                for(int i = 0; i < rows.length; i++) {
                  Object[] cols = rows[i];

                  for(int j = 0; j < cols.length; j++) {
                    XCell cell= cellCursor.getCellByPosition(j,i);
                    XText cellText = (XText)UnoRuntime.queryInterface(XText.class, cell);
                    Object insert = cols[j];
                    if(insert instanceof Number)
                      cell.setValue(((Number)insert).doubleValue());
                    else if(insert instanceof String)
                      cellText.setString((String)insert);
                    else
                      cellText.setString(insert.toString());
                   }
                }
            }
            catch (Throwable exception) {
                exception.printStackTrace();
            }
        }
    }
}
