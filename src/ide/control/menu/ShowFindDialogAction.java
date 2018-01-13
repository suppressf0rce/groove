package ide.control.menu;

import bibliothek.gui.dock.common.intern.CDockable;
import ide.view.MainFrame;
import ide.view.dock.EditorDock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ShowFindDialogAction extends AbstractAction {

    public ShowFindDialogAction() {
        super("Find...");
        int c = MainFrame.getInstance().getToolkit().getMenuShortcutKeyMask();
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, c));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CDockable dockable = MainFrame.getInstance().getDockingControl().getFocusedCDockable();
        if (dockable instanceof EditorDock) {
            if (((EditorDock) dockable).getReplaceDialog().isVisible()) {
                ((EditorDock) dockable).getReplaceDialog().setVisible(false);
            }
            ((EditorDock) dockable).getFindDialog().setVisible(true);
        }
    }

}