package ide.model.project_explorer;

import ide.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class GrooveFile extends DefaultMutableTreeNode implements Renameable, Deleteable {

    private boolean opened;
    private File file;
    private String name;
    private ImageIcon icon;
    private boolean changed;

    public GrooveFile(File file) {
        this.file = file;
        name = file.getName();
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/groove_file.png")));

        opened = false;
        changed = false;
    }

    public GrooveFile() {
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/groove_file.png")));

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

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public File getFile() {
        return file;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void rename(String newName) {
        if (!newName.endsWith(".gr"))
            newName += ".gr";
        File newFile = new File(file.getParentFile().getAbsolutePath() + File.separator + newName);
        if (!file.renameTo(newFile)) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Could not rename Groove", "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.name = newName;
    }

    @Override
    public void delete() {
        file.delete();
        removeFromParent();
    }

}
