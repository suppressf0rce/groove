package ide.control;

import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.OtherFile;
import ide.model.project_explorer.Project;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectExplorerMouseListener extends MouseAdapter {

    private JTree tree;

    public ProjectExplorerMouseListener(JTree tree) {
        this.tree = tree;
    }


    @SuppressWarnings("Duplicates")
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            if (node == null) return;

            //2 Click
            if (node instanceof Project) {
                Project project = (Project) node;
                if (!project.isOpened()) {
                    project.openProject();
                    ((DefaultTreeModel) tree.getModel()).reload();
                    tree.revalidate();
                    tree.expandPath(new TreePath(node.getPath()));
                }
            }

            if (node instanceof GrooveFile) {
                GrooveFile grooveFile = (GrooveFile) node;
                FileOpener.open(grooveFile);
            }

            if (node instanceof OtherFile) {
                OtherFile otherFile = (OtherFile) node;
                FileOpener.open(otherFile);
            }
        }
    }

}
