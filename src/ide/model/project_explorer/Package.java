package ide.model.project_explorer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class Package extends DefaultMutableTreeNode {

    private File file;
    private String name;
    private ImageIcon icon;

    public Package(File file) {
        this.file = file;
        this.name = file.getName();

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/package.png")));
    }

    @Override
    public String toString() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    @SuppressWarnings("Duplicates")
    public void loadContent() {
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
}
