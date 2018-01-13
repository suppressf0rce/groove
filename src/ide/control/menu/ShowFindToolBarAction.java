package ide.control.menu;

import bibliothek.gui.dock.common.intern.CDockable;
import ide.view.MainFrame;
import ide.view.dock.EditorDock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ShowFindToolBarAction extends AbstractAction {

    public ShowFindToolBarAction() {
        super("Show Find Search Bar");
        int ctrl = MainFrame.getInstance().getToolkit().getMenuShortcutKeyMask();
        int shift = InputEvent.SHIFT_MASK;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, ctrl | shift));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        //
        CDockable dockable = MainFrame.getInstance().getDockingControl().getFocusedCDockable();

        if (dockable instanceof EditorDock) {
            if (((EditorDock) dockable).getCsp().getDisplayedBottomComponent() == ((EditorDock) dockable).getFindToolBar()) {
                ((EditorDock) dockable).getCsp().hideBottomComponent();
            } else {
                ((EditorDock) dockable).getCsp().addBottomComponent(((EditorDock) dockable).getFindToolBar());
                ((EditorDock) dockable).getCsp().showBottomComponent(((EditorDock) dockable).getFindToolBar());
            }
        }
    }
}
