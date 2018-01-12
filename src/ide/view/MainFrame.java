package ide.view;

import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CGrid;
import bibliothek.gui.dock.common.CWorkingArea;
import bibliothek.gui.dock.common.theme.ThemeMap;
import ide.view.dock.ConsoleDock;
import ide.view.dock.EditorDock;
import ide.view.dock.HidingEclipseThemeConnector;
import ide.view.dock.ProjectExplorerDock;
import ide.view.menu.Menu;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    /**
     * Instance of main frame used for singleton
     */
    private static MainFrame instance = null;

    private ide.view.menu.Menu menu;

    private CControl dockingControl;
    private CWorkingArea editorArea;
    private CGrid grid;

    /**
     * Default constructor for the MainFrame
     */
    private MainFrame() {
        setTitle("Groove IDE");

        initialize();

        //Setting up the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/icon.png")));
    }

    /**
     * Singleton instance getter of the main fame
     *
     * @return
     */
    public static MainFrame getInstance() {
        if (instance == null)
            instance = new MainFrame();

        return instance;
    }

    private void initialize() {

        //Menu
        menu = new Menu();
        setJMenuBar(menu);

        //Docking system
        dockingControl = new CControl(this);
        setLayout(new GridLayout(1, 1));
        add(dockingControl.getContentArea());
        dockingControl.setTheme(ThemeMap.KEY_ECLIPSE_THEME);
        dockingControl.putProperty(EclipseTheme.THEME_CONNECTOR, new HidingEclipseThemeConnector(dockingControl));
        grid = new CGrid(dockingControl);

        //Project explorer dock
        ProjectExplorerDock explorerDock = new ProjectExplorerDock();
        dockingControl.addDockable(explorerDock);
        grid.add(0, 0, 1, 4, explorerDock);

        //Console dock
        ConsoleDock consoleDock = new ConsoleDock();
        dockingControl.addDockable(consoleDock);
        grid.add(1, 3, 4, 1, consoleDock);

        //Editor area
        editorArea = dockingControl.createWorkingArea("editorArea");
        grid.add(1, 1, 3, 3, editorArea);

        //TESTS
        EditorDock editorDock1 = new EditorDock("Test1", "img/console.png");
        editorArea.add(editorDock1);
        editorDock1.setVisible(true);
        EditorDock editorDock2 = new EditorDock("Test1", "img/console.png");
        editorArea.add(editorDock2);
        editorDock2.setVisible(true);
        editorDock2.toFront();

        dockingControl.getContentArea().deploy(grid);
    }
}
