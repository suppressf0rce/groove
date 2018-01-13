package ide.view.toolbar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class ComboBoxRenderer extends JLabel implements ListCellRenderer {
    final String SEPARATOR = "SEPARATOR";
    JSeparator separator;

    public ComboBoxRenderer() {
        setOpaque(true);
        setBorder(new EmptyBorder(1, 1, 1, 1));
        separator = new JSeparator(JSeparator.HORIZONTAL);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        String str = (value == null) ? "" : value.toString();
        if (SEPARATOR.equals(str)) {
            return new JPopupMenu.Separator();
        }
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setFont(list.getFont());
        setText(str);
        return this;
    }
}