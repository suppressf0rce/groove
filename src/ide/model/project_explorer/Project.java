package ide.model.project_explorer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class Project extends DefaultMutableTreeNode {

    private File file;
    private String name;
    private boolean opened;

    private ImageIcon icon;

    public Project(File file) {
        this.file = file;
        name = file.getName();
        opened = false;

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/project-explorer.png")));
    }

    public Project() {
        opened = false;

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/project-explorer.png")));
    }

    @SuppressWarnings("Duplicates")
    public void openProject() {
        opened = true;

        File[] content = file.listFiles();

        if (content != null) {
            for (File f : content) {
                if (!f.getName().startsWith(".")) {
                    if (f.isDirectory()) {
                        Package pckg = new Package(f);
                        add(pckg);
                        pckg.loadContent();
                    } else {
                        if (f.getName().endsWith(".gr"))
                            add(new GrooveFile(f));
                        else
                            add(new OtherFile(f));
                    }
                }
            }
        }
    }

    public void closeProject() {
        opened = false;
        removeAllChildren();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
