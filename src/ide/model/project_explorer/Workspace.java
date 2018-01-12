package ide.model.project_explorer;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class Workspace extends DefaultMutableTreeNode {

    private File file;
    private boolean opened;

    public Workspace(File file) {
        this.file = file;
        opened = false;
    }

    public void openWorkspace() {
        opened = true;

        File[] list = file.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory() && !f.getName().startsWith(".")) {
                add(new Project(f));
            }
        }
    }

    public void closeWorkspace() {
        opened = false;
        removeAllChildren();
    }

    public boolean isOpened() {
        return opened;
    }

    public File getFile() {
        return file;
    }
}
