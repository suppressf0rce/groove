package ide.view.modules;

import ide.model.Settings;
import ide.view.MainFrame;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class SelectWorkspace extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton btnOK;
    private JTextField tfPath;
    private JButton btnBrowse;

    public SelectWorkspace() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Workspace Selection");
        getRootPane().setDefaultButton(buttonOK);

        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

        //Call onBrowse()
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onBrowse();
            }
        });

        pack();
        this.setLocationRelativeTo(MainFrame.getInstance());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SelectWorkspace dialog = new SelectWorkspace();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        if (!tfPath.getText().equals("")) {
            Settings.WORKSPACE_PATH = tfPath.getText();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "You have not selected workspace.\n Please select one and try again", "Warrning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onCancel() {
        int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Are you sure you want to exit the Groove IDE?", "Exit", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void onBrowse() {
        JFileChooser jc = new JFileChooser(System.getProperty("user.home"));
        jc.setDialogTitle("Select Workspace");
        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jc.setAcceptAllFileFilterUsed(false);

        if (jc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = jc.getSelectedFile();
            tfPath.setText(dir.getAbsolutePath());
        }
    }
}
