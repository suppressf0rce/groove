package ide.view.dock;

import bibliothek.gui.dock.common.CLocation;
import ide.control.ProjectExplorerMouseListener;
import ide.model.project_explorer.Workspace;
import ide.view.ProjectExplorerTreeCellRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;

public class ProjectExplorerDock extends DockingWindow {

    Workspace workspace;
    private JTree workspaceTree;

    public ProjectExplorerDock() {
        super("Project Explorer", "img/project-explorer.png");

        setLocation(CLocation.base().minimalWest());

        workspaceTree = new JTree(new DefaultTreeModel(null));
        workspaceTree.setRootVisible(false);
        workspaceTree.setCellRenderer(new ProjectExplorerTreeCellRenderer());
        workspaceTree.addMouseListener(new ProjectExplorerMouseListener(workspaceTree));

        setLayout(new BorderLayout());
        add(workspaceTree, BorderLayout.CENTER);
    }

    public void openWorkspace(File file) {
        workspace = new Workspace(file);
        workspaceTree.setModel(new DefaultTreeModel(workspace));
        workspace.openWorkspace();

        workspaceTree.revalidate();
        ((DefaultTreeModel) workspaceTree.getModel()).reload();
    }

    public void closeWorkspace() {
        if (workspace.isOpened()) {
            workspace.closeWorkspace();
            workspaceTree.revalidate();
        }
    }

}
