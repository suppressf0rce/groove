package ide.control.menu;

import ide.model.project_explorer.Renameable;
import ide.view.MainFrame;
import ide.view.modules.Rename;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;

public class RenameFile extends AbstractAction {


    private Renameable renameable;

    public RenameFile(Renameable deleteable) {
        super("Rename");
        this.renameable = deleteable;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) MainFrame.getInstance().getExplorerDock().getWorkspaceTree().getLastSelectedPathComponent();
        node = (DefaultMutableTreeNode) node.getParent();


        Rename dialog = new Rename(renameable);
        dialog.pack();
        dialog.setLocationRelativeTo(MainFrame.getInstance());
        dialog.setVisible(true);


        ((DefaultTreeModel) MainFrame.getInstance().getExplorerDock().getWorkspaceTree().getModel()).reload();
        MainFrame.getInstance().getExplorerDock().getWorkspaceTree().revalidate();
        MainFrame.getInstance().getExplorerDock().getWorkspaceTree().expandPath(new TreePath(node.getPath()));
    }

}
