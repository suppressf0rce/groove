package ide.view.menu;

import ide.control.menu.*;

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
        newMenu.addSeparator();
        //NewGroove
        newMenu.add(new CreateNewGrooveFileAction());
        //NewFile
        newMenu.add(new CreateNewOtherFileAction());
        //NewPackage
        newMenu.add(new CreateNewPackageAction());

        addSeparator();
        add(new SaveAllAction());
    }
}
