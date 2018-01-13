package ide.view.menu;

import ide.control.menu.ShowFindToolBarAction;
import ide.control.menu.ShowReplaceToolBarAction;

import javax.swing.*;

public class ViewMenu extends JMenu {

    public ViewMenu() {
        super("View");
        setMnemonic('V');

        this.addSeparator();
        this.add(new JMenuItem(new ShowFindToolBarAction()));
        this.add(new JMenuItem(new ShowReplaceToolBarAction()));
        this.addSeparator();

    }
}
