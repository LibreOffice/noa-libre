import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/*
 * DISABLES THE MOVE METHODS, and iconifieys other OOOInternalFrames if maximized
 */
public class OOOInternalFrame extends JInternalFrame {

  List<OOOInternalFrame> iconifiedOthers = new ArrayList<OOOInternalFrame>();

  public OOOInternalFrame() {
    super();
    init();
  }

  public OOOInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable,
      boolean iconifiable) {
    super(title, resizable, closable, maximizable, iconifiable);
    init();
  }

  public OOOInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable) {
    super(title, resizable, closable, maximizable);
    init();
  }

  public OOOInternalFrame(String title, boolean resizable, boolean closable) {
    super(title, resizable, closable);
    init();
  }

  public OOOInternalFrame(String title, boolean resizable) {
    super(title, resizable);
    init();
  }

  public OOOInternalFrame(String title) {
    super(title);
    init();
  }

  public void init() {
    addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(IS_MAXIMUM_PROPERTY)) {
          boolean newValue = ((Boolean) evt.getNewValue()).booleanValue();
          if (newValue) {
            JDesktopPane desktop = getDesktopPane();
            Component[] children = desktop.getComponents();
            for (int i = 0; i < children.length; i++) {
              Component child = children[i];
              if (child != OOOInternalFrame.this && child instanceof OOOInternalFrame) {
                OOOInternalFrame oooInternalFrame = (OOOInternalFrame) child;
                if (!oooInternalFrame.isIcon()) {
                  try {
                    oooInternalFrame.setIcon(true);
                    iconifiedOthers.add(oooInternalFrame);
                  }
                  catch (PropertyVetoException e) {
                    //ignore for now
                  }
                }
              }
            }
          }
          else if (iconifiedOthers.size() > 0) {
            for (Iterator<OOOInternalFrame> iterator = iconifiedOthers.iterator(); iterator.hasNext();) {
              OOOInternalFrame oooInternalFrame = iterator.next();
              try {
                oooInternalFrame.setIcon(false);
              }
              catch (PropertyVetoException e) {
                //ignore for now
              }
            }
            iconifiedOthers.clear();
          }
        }
      }
    });
  }

  public void moveToBack() {
    //do nothing as this breaks the ooo window
  }

  public void moveToFront() {
    //do nothing as this breaks the ooo window
  }
}
