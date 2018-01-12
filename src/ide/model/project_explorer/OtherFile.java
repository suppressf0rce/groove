package ide.model.project_explorer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class OtherFile extends DefaultMutableTreeNode {

    boolean opened;
    private File file;
    private String name;
    private ImageIcon icon;

    public OtherFile(File file) {
        this.file = file;
        name = file.getName();
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/file.png")));
        opened = false;
    }

    @Override
    public String toString() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public File getFile() {
        return file;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
