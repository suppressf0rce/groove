package ide.view.menu;

import ide.control.menu.GoToLineAction;
import ide.control.menu.ShowFindDialogAction;
import ide.control.menu.ShowReplaceDialogAction;

import javax.swing.*;

public class EditMenu extends JMenu {

    public EditMenu() {
        super("Edit");
        setMnemonic('E');

        this.addSeparator();
        this.add(new JMenuItem(new ShowFindDialogAction()));
        this.add(new JMenuItem(new ShowReplaceDialogAction()));
        this.add(new JMenuItem(new GoToLineAction()));
        this.addSeparator();

    }
}
