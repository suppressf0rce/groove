package ide.control;

import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.OtherFile;
import ide.view.MainFrame;
import ide.view.dock.EditorDock;

import javax.swing.*;

@SuppressWarnings("Duplicates")
public class FileOpener {

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

}
