package ide.view.modules;

import ide.model.Colors;
import ide.model.project_explorer.Project;
import ide.view.MainFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;

public class CreateProject extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton emptyProjectRadioButton;
    private JRadioButton fromTemplateRadioButton;
    private JList list1;
    private JTextField textField1;

    public CreateProject() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        ButtonGroup cbGrp = new ButtonGroup();
        cbGrp.add(emptyProjectRadioButton);
        cbGrp.add(fromTemplateRadioButton);
        fromTemplateRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fromTemplateRadioButton.isSelected())
                    list1.setEnabled(true);
                else
                    list1.setEnabled(false);
            }
        });

        list1.setBorder(new LineBorder(Colors.GUTTER_FOREGROUND));
    }

    private void onOK() {

        Project project = null;
        if (fromTemplateRadioButton.isSelected()) {
            if (list1.getSelectedValue() != null) {
                //TODO: Create project from selected template
            }
        }

        if (project == null)
            project = new Project();

        project.setName(textField1.getText());
        MainFrame.getInstance().getExplorerDock().createProject(project);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
