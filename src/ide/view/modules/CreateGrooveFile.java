package ide.view.modules;

import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.Package;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;

public class CreateGrooveFile extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JCheckBox createMainFunctionCheckBox;
    private DefaultMutableTreeNode node;

    public CreateGrooveFile(DefaultMutableTreeNode node) {
        this.node = node;
        setContentPane(contentPane);
        setModal(true);
        setTitle("Create New Groove");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/icon.png")));
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
        GrooveFile file = new GrooveFile();
        file.setName(textField1.getText());

        if (node instanceof Package) {

            ((Package) node).createGrooveFile(file, createMainFunctionCheckBox.isSelected());

        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
