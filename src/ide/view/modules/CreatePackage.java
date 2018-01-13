package ide.view.modules;

import ide.model.project_explorer.Package;
import ide.model.project_explorer.Project;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;

public class CreatePackage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;

    private DefaultMutableTreeNode node;

    public CreatePackage(DefaultMutableTreeNode node) {
        this.node = node;
        setContentPane(contentPane);
        setModal(true);
        setTitle("Create new Package");
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
        Package pckg = new Package();
        pckg.setName(textField1.getText());

        if (node instanceof Project)
            ((Project) node).createPackage(pckg);

        else if (node instanceof Package)
            ((Package) node).createPackage(pckg);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
