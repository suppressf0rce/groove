package ide.view.toolbar;

import ide.control.toolbar.BlockComboListener;
import ide.model.Colors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToolBar extends JToolBar {

    private JComboBox<Object> buildConfigurations;

    public ToolBar() {

        //Disable toolbar dragging
        setFloatable(false);

        //After this code everything will go to  the right side of the toolbar
        add(Box.createHorizontalGlue());

        buildConfigurations = new JComboBox<>();
        Dimension preferredSize = buildConfigurations.getPreferredSize();
        preferredSize.width = 150;
        buildConfigurations.setRenderer(new ComboBoxRenderer());
        buildConfigurations.addActionListener(new BlockComboListener(buildConfigurations));
        buildConfigurations.addItem("Edit Configurations...");
        buildConfigurations.addItem("SEPARATOR");
        buildConfigurations.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");
        addToToolbar(buildConfigurations);

        JButton runButton = new JButton();
        flatternButton(runButton);
        runButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/run.png"))));
        add(runButton);

        JButton debugButton = new JButton();
        flatternButton(debugButton);
        debugButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/bug.png"))));
        add(debugButton);

        JButton stopButton = new JButton();
        flatternButton(stopButton);
        stopButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/stop.png"))));
        add(stopButton);

        setBorder(new LineBorder(Colors.GUTTER_FOREGROUND));
    }

    private void addToToolbar(Component component) {
        Dimension d = component.getPreferredSize();
        component.setMaximumSize(d);
        component.setMinimumSize(d);
        component.setPreferredSize(d);
        add(component);

    }

    public void flatternButton(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                button.setBorderPainted(true);
                button.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
            }
        });
    }

}
