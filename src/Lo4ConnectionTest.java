package lo4connectiontest;

import java.util.HashMap;

import ag.ion.bion.officelayer.application.IOfficeApplication;
import ag.ion.bion.officelayer.util.OfficeLoader;

public class Lo4ConnectionTest {

    private final static String LIBREOFFICE_PATH = "/usr/lib64/libreoffice";
    public static HashMap<String, String> configuration = new HashMap<String, String>();
    static {
        configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, LIBREOFFICE_PATH);
        configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
    }

    public static void main(String[] args){

        OfficeLoader.init(configuration);

        try {
           OfficeLoader.run( new String[]{"lo4connectiontest.Lo4ConnectionTest$Lo4ConnectionTestCore"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Lo4ConnectionTestCore {

        public static void main(String[] args) {
            com.sun.star.uno.XComponentContext xContext = null;

            try {
                // get the remote office component context
                xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();

                if (xContext != null)
                        System.out.println("Connected to a running office ...");

                com.sun.star.lang.XMultiComponentFactory xMCF = xContext
                            .getServiceManager();
                Object oDesktop = xMCF.createInstanceWithContext(
                            "com.sun.star.frame.Desktop", xContext);
                com.sun.star.frame.XComponentLoader xCompLoader = (com.sun.star.frame.XComponentLoader) com.sun.star.uno.UnoRuntime
                            .queryInterface(com.sun.star.frame.XComponentLoader.class,oDesktop);
                com.sun.star.lang.XComponent xComponent = xCompLoader.loadComponentFromURL(
                            "private:factory/swriter", "_blank", 0,
                            new com.sun.star.beans.PropertyValue[0]);

                com.sun.star.document.XEventBroadcaster xEventBroadcaster = (com.sun.star.document.XEventBroadcaster) com.sun.star.uno.UnoRuntime
                            .queryInterface(com.sun.star.document.XEventBroadcaster.class, xComponent);
                if (xEventBroadcaster != null) {
                    // HERE COMES THE FIRST EXCEPTION
                    xEventBroadcaster.addEventListener(new com.sun.star.document.XEventListener() {

                        @Override
                        public void disposing(com.sun.star.lang.EventObject arg0) {
                                    System.out.println("disposing");
                        }

                        @Override
                        public void notifyEvent(com.sun.star.document.EventObject arg0) {
                                    System.out.println("notifyEvent");
                         }
                    });
                }
                System.exit(0);
           } catch (Throwable throwable) {
              throwable.printStackTrace();
           }
        }
    }
}
