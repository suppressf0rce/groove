package ide.view.dock;

import bibliothek.gui.dock.common.CLocation;

public class ProjectExplorerDock extends DockingWindow {

    public ProjectExplorerDock() {
        super("Project Explorer", "img/project-explorer.png");

        setLocation(CLocation.base().minimalWest());
    }

}
