package ide.control.menu;

import bibliothek.gui.dock.common.intern.CDockable;
import ide.view.MainFrame;
import ide.view.dock.EditorDock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ShowReplaceToolBarAction extends AbstractAction {

    public ShowReplaceToolBarAction() {
        super("Show Replace Search Bar");
        int ctrl = MainFrame.getInstance().getToolkit().getMenuShortcutKeyMask();
        int shift = InputEvent.SHIFT_MASK;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ctrl | shift));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        CDockable dockable = MainFrame.getInstance().getDockingControl().getFocusedCDockable();
        if (dockable instanceof EditorDock) {
            if (((EditorDock) dockable).getCsp().getDisplayedBottomComponent() == ((EditorDock) dockable).getReplaceToolBar()) {
                ((EditorDock) dockable).getCsp().hideBottomComponent();
            } else {
                ((EditorDock) dockable).getCsp().addBottomComponent(((EditorDock) dockable).getReplaceToolBar());
                ((EditorDock) dockable).getCsp().showBottomComponent(((EditorDock) dockable).getReplaceToolBar());
            }
        }
    }
}
