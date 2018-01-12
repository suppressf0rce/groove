package ide.control;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class ConsoleErrorStream extends OutputStream {

    private JTextArea console;

    public ConsoleErrorStream(JTextArea console) {
        this.console = console;
    }


    @Override
    public void write(int i) throws IOException {
        // redirects data to the text area
        console.requestFocus();
        console.append(String.valueOf((char) i));
        console.setCaretPosition(console.getDocument().getLength());

    }
}
