package ide.control;

import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.OtherFile;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class TextChangeListener implements DocumentListener {

    private DefaultMutableTreeNode node;

    public TextChangeListener(DefaultMutableTreeNode node) {
        this.node = node;
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        if (node instanceof GrooveFile) {
            ((GrooveFile) node).setChanged(true);
        } else if (node instanceof OtherFile) {
            ((OtherFile) node).setChanged(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        if (node instanceof GrooveFile) {
            ((GrooveFile) node).setChanged(true);
        } else if (node instanceof OtherFile) {
            ((OtherFile) node).setChanged(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        if (node instanceof GrooveFile) {
            ((GrooveFile) node).setChanged(true);
        } else if (node instanceof OtherFile) {
            ((OtherFile) node).setChanged(true);
        }
    }
}
