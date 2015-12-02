
import enoa.connection.NoaConnection;
import enoa.handler.DocumentHandler;
import enoa.handler.TableHandler;
import java.awt.Desktop;
import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Andreas
 */
public class Lo4ConnectTest2 {

    public static void main(String[] args) throws Exception {
        System.setProperty("enoa.connection.path", "C:\\Program Files (x86)\\LibreOffice 4\\program");
        
        NoaConnection n = NoaConnection.getConnection();
        DocumentHandler d = new DocumentHandler(n);
        Object[][] data = new Object[][]{
            new String[]{"00", "01", "02", "03"}, 
            new String[]{"10", "11", "12", "13"}};
        TableHandler t = new TableHandler(d.createTextDocument(), data);
        d.saveAs(new File("test.pdf"));
        Desktop.getDesktop().open(new File("test.pdf"));
    }
}
