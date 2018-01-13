package ide.control.menu;

import ide.model.project_explorer.Deleteable;
import ide.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;

public class DeleteFile extends AbstractAction {


    private Deleteable deleteable;

    public DeleteFile(Deleteable deleteable) {
        super("Delete");
        this.deleteable = deleteable;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) MainFrame.getInstance().getExplorerDock().getWorkspaceTree().getLastSelectedPathComponent();
        node = (DefaultMutableTreeNode) node.getParent();

        deleteable.delete();

        ((DefaultTreeModel) MainFrame.getInstance().getExplorerDock().getWorkspaceTree().getModel()).reload();
        MainFrame.getInstance().getExplorerDock().getWorkspaceTree().revalidate();
        MainFrame.getInstance().getExplorerDock().getWorkspaceTree().expandPath(new TreePath(node.getPath()));
    }

}
