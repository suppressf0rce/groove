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

    private boolean changed;

    public OtherFile(File file) {
        this.file = file;
        name = file.getName();
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/file.png")));
        opened = false;
        changed = false;
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

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
