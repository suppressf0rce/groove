package ide.model.project_explorer;

import ide.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class OtherFile extends DefaultMutableTreeNode implements Renameable, Deleteable {

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

    public OtherFile() {
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

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void rename(String newName) {
        File newFile = new File(file.getParentFile().getAbsolutePath() + File.separator + newName);
        if (!file.renameTo(newFile)) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Could not rename file", "Error", JOptionPane.ERROR_MESSAGE);
        }else {
            this.file = newFile;
            this.name = newName;
        }
    }

    @Override
    public String getOldName() {
        return name;
    }

    @Override
    public void delete() {
        file.delete();
        removeFromParent();
    }
}
