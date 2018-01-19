package ide.control.menu;

import bibliothek.gui.dock.common.CControl;
import ide.control.FileWorker;
import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.OtherFile;
import ide.view.MainFrame;
import ide.view.dock.EditorDock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAllAction extends AbstractAction {

    public SaveAllAction() {
        super("Save All", new ImageIcon(Toolkit.getDefaultToolkit().getImage(SaveAllAction.class.getClassLoader().getResource("img/save.png"))));
        int ctrl = MainFrame.getInstance().getToolkit().getMenuShortcutKeyMask();
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ctrl));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        int i;

        CControl dock = MainFrame.getInstance().getDockingControl();
        for (i = 0; i < dock.getCDockableCount(); i++) {
            if (dock.getCDockable(i) instanceof EditorDock) {

                if (((EditorDock) dock.getCDockable(i)).getNode() instanceof GrooveFile) {
                    FileWorker.saveFile(((GrooveFile) ((EditorDock) dock.getCDockable(i)).getNode()).getFile(), ((EditorDock) dock.getCDockable(i)).getTextArea().getText());
                    ((GrooveFile) ((EditorDock) dock.getCDockable(i)).getNode()).setChanged(false);
                }
                if (((EditorDock) dock.getCDockable(i)).getNode() instanceof OtherFile) {
                    FileWorker.saveFile(((OtherFile) ((EditorDock) dock.getCDockable(i)).getNode()).getFile(), ((EditorDock) dock.getCDockable(i)).getTextArea().getText());
                    ((OtherFile) ((EditorDock) dock.getCDockable(i)).getNode()).setChanged(false);
                }
            }
        }

    }

}
