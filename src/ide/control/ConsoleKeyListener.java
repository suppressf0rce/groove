package ide.control;

import ide.view.MainFrame;
import ide.view.dock.ConsoleDock;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class ConsoleKeyListener implements KeyListener {

    static CharsetEncoder asciiEncoder =
            Charset.forName("US-ASCII").newEncoder();
    private StringBuilder buffer = new StringBuilder();

    public ConsoleKeyListener() {

    }

    public boolean canEncode(char c) {
        return asciiEncoder.canEncode(c);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        if (canEncode(keyEvent.getKeyChar()))
            buffer.append(keyEvent.getKeyChar());

        ConsoleDock consoleDock = MainFrame.getInstance().getConsoleDock();

        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                consoleDock.getIn().write(buffer.toString());
                consoleDock.getIn().flush();
                buffer = new StringBuilder();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
