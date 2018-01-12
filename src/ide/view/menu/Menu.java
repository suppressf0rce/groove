package ide.view.menu;

import javax.swing.*;

public class Menu extends JMenuBar {

    private FileMenu fileMenu;

    /**
     * Default constructor of menu class
     */
    public Menu() {
        initializeMenus();
    }

    /**
     * Initializes file menu and its components
     */
    public void initializeMenus() {

        fileMenu = new FileMenu();
        this.add(fileMenu);

    }

}
