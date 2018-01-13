package ide.control.toolbar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockComboListener implements ActionListener {

    final String SEPARATOR = "SEPARATOR";
    JComboBox combo;

    Object currentItem;

    public BlockComboListener(JComboBox combo) {
        this.combo = combo;
        currentItem = combo.getSelectedItem();
    }

    public void actionPerformed(ActionEvent e) {
        String tempItem = (String) combo.getSelectedItem();
        if (SEPARATOR.equals(tempItem)) {
            combo.setSelectedItem(currentItem);
        } else {
            currentItem = tempItem;
        }
    }
}