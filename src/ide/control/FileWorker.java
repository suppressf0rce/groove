package ide.control;

import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.OtherFile;
import ide.view.MainFrame;
import ide.view.dock.EditorDock;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

@SuppressWarnings("Duplicates")
public class FileWorker {

    public static void open(GrooveFile grooveFile) {
        if (!grooveFile.isOpened()) {
            EditorDock editorDock = new EditorDock(grooveFile.toString(), grooveFile);
            MainFrame.getInstance().getEditorArea().add(editorDock);
            grooveFile.setOpened(true);
            editorDock.setVisible(true);
            editorDock.toFront();
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "File already opened in editor", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void open(OtherFile otherFile) {
        int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Do you want to open this file in Groove IDE editor?", "Open File", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            if (!otherFile.isOpened()) {
                EditorDock editorDock = new EditorDock(otherFile.toString(), otherFile);
                MainFrame.getInstance().getEditorArea().add(editorDock);
                otherFile.setOpened(true);
                editorDock.setVisible(true);
                editorDock.toFront();
            } else {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "File already opened in editor", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public static void close(OtherFile node, EditorDock editor) {
        if (node.isChanged()) {
            int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "There is unsaved change to file. Do you wish to save changes?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                saveFile(node.getFile(), editor.getTextArea().getText());
            }
        }

        if (editor.isVisible()) {
            editor.setVisible(false);
        }
        MainFrame.getInstance().getDockingControl().removeDockable(editor);
        node.setOpened(false);
        node.setChanged(false);
    }

    public static void close(GrooveFile node, EditorDock editor) {
        if (node.isChanged()) {
            int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "There is unsaved change to file. Do you wish to save changes?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                saveFile(node.getFile(), editor.getTextArea().getText());
            }
        }

        if (editor.isVisible()) {
            editor.setVisible(false);
        }
        MainFrame.getInstance().getDockingControl().removeDockable(editor);
        node.setOpened(false);
        node.setChanged(false);
    }

    public static String readFile(File file, Charset encoding) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, encoding);
    }

    public static void saveFile(File file, String text) {
        FileOutputStream fos = null; // true to append
        try {
            fos = new FileOutputStream(file, false);
            // false to overwrite.
            byte[] myBytes = text.getBytes();
            fos.write(myBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
