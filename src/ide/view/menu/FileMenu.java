package ide.view.menu;

import ide.control.menu.CreateNewProjectAction;

import javax.swing.*;

public class FileMenu extends JMenu {

    private JMenu newMenu;

    public FileMenu() {
        super("File");
        setMnemonic('F');

        //New Menu
        newMenu = new JMenu("New");
        add(newMenu);
        //NewProject
        newMenu.add(new CreateNewProjectAction());
    }
}
