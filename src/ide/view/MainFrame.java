package ide.view;

import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CGrid;
import bibliothek.gui.dock.common.CWorkingArea;
import bibliothek.gui.dock.common.action.predefined.CCloseAction;
import bibliothek.gui.dock.common.intern.CDockable;
import bibliothek.gui.dock.common.theme.ThemeMap;
import ide.control.FileWorker;
import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.OtherFile;
import ide.view.dock.ConsoleDock;
import ide.view.dock.EditorDock;
import ide.view.dock.HidingEclipseThemeConnector;
import ide.view.dock.ProjectExplorerDock;
import ide.view.menu.Menu;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MainFrame extends JFrame {

    /**
     * Instance of main frame used for singleton
     */
    private static MainFrame instance = null;

    private ide.view.menu.Menu menu;
    private ToolBar toolBar;

    private CControl dockingControl;
    private CWorkingArea editorArea;
    private CGrid grid;

    ProjectExplorerDock explorerDock;

    /**
     * Default constructor for the MainFrame
     */
    private MainFrame() {
        setTitle("Groove IDE");

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
        if (instance == null) {
            instance = new MainFrame();
            instance.initialize();
        }

        return instance;

        //TODO: Add functionality of saving all the files
        //TODO: Add functionality of the edit system (cut & copy & paste & undo & redo)
    }

    private void initialize() {

        //Toolbar
        toolBar = new ToolBar();
        add(toolBar, BorderLayout.PAGE_START);

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
        dockingControl.putProperty(CControl.CLOSE_ACTION_FACTORY, (cControl, cDockable) -> new EditorCloseButton(dockingControl));


        //Project explorer dock
        explorerDock = new ProjectExplorerDock();
        dockingControl.addDockable(explorerDock);
        grid.add(0, 0, 1, 1, explorerDock);

        //Console dock
        ConsoleDock consoleDock = new ConsoleDock();
        dockingControl.addDockable(consoleDock);
        grid.add(0, 3, 4, 1, consoleDock);

        //Editor area
        editorArea = dockingControl.createWorkingArea("editorArea");
        grid.add(1, 0, 3, 3, editorArea);

        dockingControl.getContentArea().deploy(grid);
    }

    public CControl getDockingControl() {
        return dockingControl;
    }

    public ProjectExplorerDock getExplorerDock() {
        return explorerDock;
    }

    public CWorkingArea getEditorArea() {
        return editorArea;
    }

    public void focusNode(DefaultMutableTreeNode node) {
        for (int i = 0; i < dockingControl.getCDockableCount(); i++) {
            CDockable dockable = dockingControl.getCDockable(i);

            if (dockable instanceof EditorDock) {
                if (((EditorDock) dockable).getNode() == node) {
                    ((EditorDock) dockable).toFront();
                }
            }
        }
    }

    private static class EditorCloseButton extends CCloseAction {
        public EditorCloseButton(CControl control) {
            super(control);
        }

        @Override
        public void close(CDockable dockable) {
            if (dockable instanceof EditorDock) {
                EditorDock editorDock = (EditorDock) dockable;
                DefaultMutableTreeNode node = editorDock.getNode();

                if (node instanceof OtherFile) {
                    FileWorker.close((OtherFile) node, editorDock);
                } else if (node instanceof GrooveFile) {
                    FileWorker.close((GrooveFile) node, editorDock);
                }
            }
        }
    }
}
