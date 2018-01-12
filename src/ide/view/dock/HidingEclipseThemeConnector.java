package ide.view.dock;

import bibliothek.extension.gui.dock.theme.eclipse.EclipseTabDockActionLocation;
import bibliothek.extension.gui.dock.theme.eclipse.EclipseTabStateInfo;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.action.CAction;
import bibliothek.gui.dock.common.action.predefined.CCloseAction;
import bibliothek.gui.dock.common.theme.eclipse.CommonEclipseThemeConnector;

public class HidingEclipseThemeConnector extends CommonEclipseThemeConnector {
    public HidingEclipseThemeConnector(CControl control) {
        super(control);
    }

    @Override
    protected EclipseTabDockActionLocation getLocation(CAction action, EclipseTabStateInfo tab) {
        if (action instanceof CCloseAction) {
            /* By redefining the behavior of the close-action, we can hide it if the tab
             * is not selected */
            if (tab.isSelected()) {
                return EclipseTabDockActionLocation.TAB;
            } else {
                return EclipseTabDockActionLocation.HIDDEN;
            }
        }
        return super.getLocation(action, tab);
    }
}
