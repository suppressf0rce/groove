package ide.view.modules;

import ide.model.project_explorer.Renameable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rename extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;

    private Renameable renameable;

    public Rename(Renameable renameable) {
        setContentPane(contentPane);
        setTitle("Rename");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/icon.png")));
        this.renameable = renameable;
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

        textField1.setText(renameable.getOldName());
        textField1.setSelectionStart(0);
        textField1.setSelectionEnd(renameable.getOldName().length());
    }

    private void onOK() {
        renameable.rename(textField1.getText());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
