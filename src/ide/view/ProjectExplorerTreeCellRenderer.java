package ide.view;

import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.OtherFile;
import ide.model.project_explorer.Package;
import ide.model.project_explorer.Project;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class ProjectExplorerTreeCellRenderer implements TreeCellRenderer {

    private JLabel label;

    public ProjectExplorerTreeCellRenderer() {
        label = new JLabel();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object o, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {


        if (o instanceof Project) {
            Project project = (Project) o;
            label.setIcon(project.getIcon());
            label.setText(project.toString());
        } else if (o instanceof Package) {
            Package pckg = (Package) o;
            label.setIcon(pckg.getIcon());
            label.setText(pckg.toString());
        } else if (o instanceof GrooveFile) {
            GrooveFile node = (GrooveFile) o;
            label.setIcon(node.getIcon());
            label.setText(node.toString());
        } else if (o instanceof OtherFile) {
            OtherFile node = (OtherFile) o;
            label.setIcon(node.getIcon());
            label.setText(node.toString());
        } else {
            label.setIcon(null);
            label.setText("" + o);
        }
        return label;
    }
}
