import java.util.HashMap;
import java.util.Map;

import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.text.ITextDocument;

public class ConnectionTests {

  //private final static String OPEN_OFFICE_ORG_PATH = "C:\\Programme\\OpenOffice.org 3\\"; 
  private final static String OPEN_OFFICE_ORG_PATH = "C:\\Programme\\OpenOffice.org 2.4\\";

  public static void main(String[] args) {
    try {
      Map<String, String> configuration = new HashMap<String, String>();
      //configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
      //configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY,
      //    IOfficeApplication.LOCAL_APPLICATION);
      configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY,
          IOfficeApplication.REMOTE_APPLICATION);
      configuration.put(IOfficeApplication.APPLICATION_HOST_KEY, "host");
      configuration.put(IOfficeApplication.APPLICATION_PORT_KEY, "8100");

      final IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);
      officeAplication.setConfiguration(configuration);
      officeAplication.activate();
      IDocument document = officeAplication.getDocumentService().constructNewDocument(IDocument.WRITER,
          DocumentDescriptor.DEFAULT);
      ITextDocument textDocument = (ITextDocument) document;
      textDocument.getTextService().getText().setText("HalloWelt");
      textDocument.close();
      officeAplication.deactivate();
    }
    catch (Throwable exception) {
      exception.printStackTrace();
    }
  }

}
