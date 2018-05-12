package ide.view.dock;

import bibliothek.gui.dock.common.CLocation;
import ide.control.ProjectExplorerMouseListener;
import ide.model.project_explorer.Project;
import ide.model.project_explorer.Workspace;
import ide.view.MainFrame;
import ide.view.ProjectExplorerTreeCellRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;

public class ProjectExplorerDock extends DockingWindow {

    private Workspace workspace;
    private JTree workspaceTree;

    public ProjectExplorerDock() {
        super("Project Explorer", "img/project-explorer.png");

        setLocation(CLocation.base().minimalWest());

        workspaceTree = new JTree(new DefaultTreeModel(null));
        workspaceTree.setRootVisible(false);
        workspaceTree.setCellRenderer(new ProjectExplorerTreeCellRenderer());
        workspaceTree.addMouseListener(new ProjectExplorerMouseListener(workspaceTree));

        setLayout(new BorderLayout());
        add(new JScrollPane(workspaceTree), BorderLayout.CENTER);
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
            ((DefaultTreeModel) workspaceTree.getModel()).reload();
            workspace = null;
        }
    }

    public void createProject(Project project) {
        if (workspace == null)
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "There is no active workspaces in IDE! \nPlease open a workspace and try again", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            File dir = new File(workspace.getFile().getAbsolutePath() + File.separator + project.getName());

            // attempt to create the directory here
            boolean successful = dir.mkdir();
            if (successful) {
                project.setFile(dir);
                workspace.add(project);
                workspaceTree.revalidate();
                ((DefaultTreeModel) workspaceTree.getModel()).reload();
            } else {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Could not create project directory! \nPlease try again", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JTree getWorkspaceTree() {
        return workspaceTree;
    }
}
