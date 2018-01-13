package ide.view.dock;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ConsoleDock extends DockingWindow {

    private JTextArea console;
    private volatile BufferedReader out;
    private volatile BufferedReader err;
    private volatile BufferedWriter in;

    private volatile boolean accesable;

    private Thread consoleThread;

    public ConsoleDock() {
        super("Console", "img/console.png");

        console = new JTextArea();
        console.setFont(new Font("Monospaced", Font.PLAIN, 14));
        console.setEditable(false);

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
                    console.append(line);
                    console.append(System.getProperty("line.separator"));
                    console.setCaretPosition(console.getDocument().getLength());
                }

                while ((line = err.readLine()) != null) {
                    console.append(line);
                    console.append(System.getProperty("line.separator"));
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

    public JTextArea getConsole() {
        return console;
    }

    public boolean isAccesable() {
        return accesable;
    }
}
