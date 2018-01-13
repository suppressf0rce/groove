package ide.control;

import ide.control.menu.*;
import ide.model.project_explorer.*;
import ide.model.project_explorer.Package;

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

        if (SwingUtilities.isRightMouseButton(e)) {

            //Select with right click
            int selRow = tree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = tree.getClosestPathForLocation(e.getX(), e.getY());
            tree.setSelectionPath(selPath);
            if (selRow > -1) {
                tree.setSelectionRow(selRow);
            }

            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            if (node == null) return;
            JPopupMenu menu = new JPopupMenu();
            JMenu newMenu = new JMenu("New");
            if (node instanceof Project) {
                menu.add(newMenu);
                menu.addSeparator();

                newMenu.add(new CreateNewOtherFileAction());
                newMenu.add(new CreateNewPackageAction());
            }

            if (node instanceof Package) {
                menu.add(newMenu);
                menu.addSeparator();

                newMenu.add(new CreateNewGrooveFileAction());
                newMenu.add(new CreateNewOtherFileAction());
                newMenu.add(new CreateNewPackageAction());
            }

            if (node instanceof Renameable) {
                menu.add(new RenameFile((Renameable) node));
                menu.addSeparator();
            }

            if (node instanceof Deleteable) {
                menu.add(new DeleteFile((Deleteable) node));
                menu.addSeparator();
            }

            menu.show(tree, e.getX(), e.getY());

        }

        if (SwingUtilities.isLeftMouseButton(e)) {
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
                    FileWorker.open(grooveFile);
                }

                if (node instanceof OtherFile) {
                    OtherFile otherFile = (OtherFile) node;
                    FileWorker.open(otherFile);
                }
            }
        }
    }

}
