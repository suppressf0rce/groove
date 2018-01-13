package ide.control.menu;

import ide.model.project_explorer.Package;
import ide.view.MainFrame;
import ide.view.modules.CreateGrooveFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateNewGrooveFileAction extends AbstractAction {


    public CreateNewGrooveFileAction() {
        super("Groove", new ImageIcon(Toolkit.getDefaultToolkit().getImage(CreateNewGrooveFileAction.class.getClassLoader().getResource("img/groove_file.png"))));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) MainFrame.getInstance().getExplorerDock().getWorkspaceTree().getLastSelectedPathComponent();

        if (node instanceof Package) {

            CreateGrooveFile dialog = new CreateGrooveFile(node);
            dialog.pack();
            dialog.setLocationRelativeTo(MainFrame.getInstance());
            dialog.setVisible(true);

            ((DefaultTreeModel) MainFrame.getInstance().getExplorerDock().getWorkspaceTree().getModel()).reload();
            MainFrame.getInstance().getExplorerDock().getWorkspaceTree().revalidate();
            MainFrame.getInstance().getExplorerDock().getWorkspaceTree().expandPath(new TreePath(node.getPath()));

        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "No valid package selection for creating new Groove file\n", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

}
