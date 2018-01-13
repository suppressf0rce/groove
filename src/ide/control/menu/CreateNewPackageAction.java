package ide.control.menu;

import ide.model.project_explorer.Package;
import ide.model.project_explorer.Project;
import ide.view.MainFrame;
import ide.view.modules.CreatePackage;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateNewPackageAction extends AbstractAction {


    public CreateNewPackageAction() {
        super("Package", new ImageIcon(Toolkit.getDefaultToolkit().getImage(CreateNewPackageAction.class.getClassLoader().getResource("img/package.png"))));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) MainFrame.getInstance().getExplorerDock().getWorkspaceTree().getLastSelectedPathComponent();

        if (node instanceof Package || node instanceof Project) {

            CreatePackage dialog = new CreatePackage(node);
            dialog.pack();
            dialog.setLocationRelativeTo(MainFrame.getInstance());
            dialog.setVisible(true);

            ((DefaultTreeModel) MainFrame.getInstance().getExplorerDock().getWorkspaceTree().getModel()).reload();
            MainFrame.getInstance().getExplorerDock().getWorkspaceTree().revalidate();
            MainFrame.getInstance().getExplorerDock().getWorkspaceTree().expandPath(new TreePath(node.getPath()));
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "No valid selection for creating new package\n", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

}
