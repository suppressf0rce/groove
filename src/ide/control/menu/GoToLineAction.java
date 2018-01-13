package ide.control.menu;

import bibliothek.gui.dock.common.intern.CDockable;
import ide.view.MainFrame;
import ide.view.dock.EditorDock;
import org.fife.rsta.ui.GoToDialog;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GoToLineAction extends AbstractAction {

    public GoToLineAction() {
        super("Go To Line...");
        int c = MainFrame.getInstance().getToolkit().getMenuShortcutKeyMask();
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, c));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CDockable dockable = MainFrame.getInstance().getDockingControl().getFocusedCDockable();
        if (dockable instanceof EditorDock) {
            if (((EditorDock) dockable).getFindDialog().isVisible()) {
                ((EditorDock) dockable).getFindDialog().setVisible(false);
            }
            if (((EditorDock) dockable).getReplaceDialog().isVisible()) {
                ((EditorDock) dockable).getReplaceDialog().setVisible(false);
            }
            GoToDialog dialog = new GoToDialog(MainFrame.getInstance());
            dialog.setMaxLineNumberAllowed(((EditorDock) dockable).getTextArea().getLineCount());
            dialog.setVisible(true);
            int line = dialog.getLineNumber();
            if (line > 0) {
                try {
                    ((EditorDock) dockable).getTextArea().setCaretPosition(((EditorDock) dockable).getTextArea().getLineStartOffset(line - 1));
                } catch (BadLocationException ble) { // Never happens
                    UIManager.getLookAndFeel().provideErrorFeedback(((EditorDock) dockable).getTextArea());
                    ble.printStackTrace();
                }
            }
        }
    }

}