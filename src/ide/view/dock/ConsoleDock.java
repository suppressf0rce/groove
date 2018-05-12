package ide.view.dock;

import ide.control.ConsoleKeyListener;
import ide.model.Colors;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.*;

public class ConsoleDock extends DockingWindow {

    private JTextPane console;
    private volatile BufferedReader out;
    private volatile BufferedReader err;
    private volatile BufferedWriter in;

    private volatile boolean accesable;

    private Thread consoleThread;

    public ConsoleDock() {
        super("Console", "img/console.png");


        console = new JTextPane();
        console.setFont(new Font("Monospaced", Font.PLAIN, 14));
        console.setForeground(Color.GREEN);
        console.setEditable(false);
        console.addKeyListener(new ConsoleKeyListener());

        setLayout(new BorderLayout());
        add(new JScrollPane(console), BorderLayout.CENTER);
    }

    public void loadOut(OutputStream input) {
        in = new BufferedWriter(new OutputStreamWriter(input));
    }

    public void loadIn(InputStream output) {
        out = new BufferedReader(new InputStreamReader(output));
    }

    public void loadErr(InputStream error) {
        err = new BufferedReader(new InputStreamReader(error));
    }

    public void startConsole() {
        consoleThread = new Thread(() -> {
            String line = null;
            StringBuilder builder;
            try {

                accesable = false;
                while ((line = out.readLine()) != null) {
                    write(line, Color.LIGHT_GRAY);
                    write(System.getProperty("line.separator"), Color.LIGHT_GRAY);
                    console.setCaretPosition(console.getDocument().getLength());
                }

                while ((line = err.readLine()) != null) {
                    write(line, Colors.CONSOLE_ERROR);
                    write(System.getProperty("line.separator"), Colors.CONSOLE_FOREGROUND);
                    console.setCaretPosition(console.getDocument().getLength());
                }
                accesable = true;

            } catch (IOException e) {
                e.printStackTrace();
                accesable = true;
            }

        });
        consoleThread.start();

    }

    public boolean isRunning() {
        if (consoleThread == null)
            return false;
        else
            return consoleThread.isAlive();
    }

    public Thread getConsoleThread() {
        return consoleThread;
    }

    public JTextPane getConsole() {
        return console;
    }

    public boolean isAccesable() {
        return accesable;
    }

    public BufferedWriter getIn() {
        return in;
    }

    public void write(String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Monospaced");

        int len = console.getDocument().getLength();
        console.setCaretPosition(len);
        console.setCharacterAttributes(aset, false);
        console.replaceSelection(msg);
    }
}
