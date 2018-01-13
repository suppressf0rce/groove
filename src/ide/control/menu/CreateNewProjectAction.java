package ide.control.menu;

import ide.view.MainFrame;
import ide.view.modules.CreateProject;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CreateNewProjectAction extends AbstractAction {


    public CreateNewProjectAction() {
        super("Project...");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        CreateProject dialog = new CreateProject();
        dialog.pack();
        dialog.setLocationRelativeTo(MainFrame.getInstance());
        dialog.setVisible(true);
    }

}
