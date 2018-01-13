package ide.view.modules;

import ide.model.project_explorer.OtherFile;
import ide.model.project_explorer.Package;
import ide.model.project_explorer.Project;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;
import java.io.IOException;

public class CreateOtherFile extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;

    private DefaultMutableTreeNode node;

    public CreateOtherFile(DefaultMutableTreeNode node) {
        this.node = node;
        setContentPane(contentPane);
        setModal(true);
        setTitle("Create File");
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
    }

    private void onOK() {
        OtherFile file = new OtherFile();
        file.setName(textField1.getText());

        if (node instanceof Project) {
            try {
                ((Project) node).createOtherFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (node instanceof Package) {
            try {
                ((Package) node).createOtherFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
