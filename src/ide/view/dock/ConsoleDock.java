package ide.view.dock;

import javax.swing.*;
import java.awt.*;

public class ConsoleDock extends DockingWindow {

    JTextArea console;

    public ConsoleDock() {
        super("Console", "img/console.png");

        console = new JTextArea();
        //console.setBackground(Color.BLACK);
        //console.setForeground(Color.lightGray);

//        PrintStream outStream = new PrintStream(new ConsoleOutputStream(console));
//        System.setOut(outStream);
//        PrintStream errorStream = new PrintStream(new ConsoleErrorStream(console));
//        System.setErr(errorStream);

        console.setFont(new Font("Monospaced", Font.PLAIN, 14));

        setLayout(new BorderLayout());
        add(new JScrollPane(console), BorderLayout.CENTER);
    }

}
