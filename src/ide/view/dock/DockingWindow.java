package ide.view.dock;

import bibliothek.gui.dock.common.DefaultSingleCDockable;

import javax.swing.*;
import java.awt.*;

public class DockingWindow extends DefaultSingleCDockable {

    /**
     * Defualt constructor for docking window
     */
    public DockingWindow(String title, String iconPath) {
        super(title, title);
        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource(iconPath)));
        setTitleIcon(icon);

        setExternalizable(false);

    }

}
