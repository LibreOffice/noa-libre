import java.util.HashMap;

import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.application.OfficeApplicationRuntime;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.document.IDocumentService;
import ag.ion.bion.officelayer.filter.MSOffice97Filter;
import ag.ion.bion.officelayer.text.IBookmark;
import ag.ion.bion.officelayer.text.IBookmarkService;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextService;

public class Textmarken {

  private final static String OPEN_OFFICE_ORG_PATH = "C:\\Programme\\OpenOffice.org 2.4";

  public static void main(String[] args) {
    HashMap configuration = new HashMap();
    configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, OPEN_OFFICE_ORG_PATH);
    configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);

    try {
      String datei = "p:/tests/bsp1.doc";
      String dateiNeu = "p:/tests/bsp1NEU.doc";
      IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);
      officeAplication.activate();
      IDocumentService documentService = officeAplication.getDocumentService();
      IDocument document = documentService.loadDocument(datei, DocumentDescriptor.DEFAULT);
      ITextDocument textDocument = (ITextDocument) document;

      ITextService textService = textDocument.getTextService();
      IBookmarkService bookmarkService = textService.getBookmarkService();
      IBookmark bookmark = bookmarkService.getBookmark("Status");
      String name = bookmark.getName();
      bookmark.setText("In Arbeit NEU345");
      textDocument.getTextFieldService().refresh();
      document.getPersistenceService().export(dateiNeu, new MSOffice97Filter());
      textDocument.close();
      document.close();
      officeAplication.dispose();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}