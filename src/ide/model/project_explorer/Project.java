package ide.model.project_explorer;

import ide.control.FileWorker;
import ide.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@SuppressWarnings("Duplicates")
public class Project extends DefaultMutableTreeNode implements Renameable, Deleteable {

    private File file;
    private String name;
    private boolean opened;

    private ImageIcon icon;

    public Project(File file) {
        this.file = file;
        name = file.getName();
        opened = false;

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/project-explorer.png")));
    }

    public Project() {
        opened = false;

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/project-explorer.png")));
    }

    @SuppressWarnings("Duplicates")
    public void openProject() {
        opened = true;

        File[] content = file.listFiles();

        if (content != null) {
            for (File f : content) {
                if (!f.getName().startsWith(".")) {
                    if (f.isDirectory()) {
                        Package pckg = new Package(f);
                        add(pckg);
                        pckg.loadContent();
                    } else {
                        if (f.getName().endsWith(".gr"))
                            add(new GrooveFile(f));
                        else
                            add(new OtherFile(f));
                    }
                }
            }
        }
    }

    public void closeProject() {
        opened = false;
        removeAllChildren();
    }

    public void createPackage(Package pckg) {
        if (!isOpened())
            openProject();

        File file = new File(getFile().getAbsolutePath() + File.separator + pckg.getName());

        // attempt to create the directory here
        boolean successful = file.mkdir();
        if (successful) {
            pckg.setFile(file);
            add(pckg);
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Could not create package directory! \nPlease try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createOtherFile(OtherFile file) throws IOException {
        if (!isOpened())
            openProject();

        File content = new File(getFile().getAbsolutePath() + File.separator + file.getName());

        // attempt to create the directory here
        boolean successful = content.createNewFile();
        if (successful) {
            file.setFile(content);
            add(file);
            FileWorker.open(file);
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Could not create file! \nPlease try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public String toString() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public boolean isOpened() {
        return opened;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void rename(String newName) {
        if (newName.contains("."))
            newName = newName.split("\\.")[0];
        File newFile = new File(file.getParentFile().getAbsolutePath() + File.separator + newName);
        if (!file.renameTo(newFile)) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Could not rename project", "Error", JOptionPane.ERROR_MESSAGE);
        }else {
            this.file = newFile;
            this.name = newName;
        }
    }

    @Override
    public String getOldName() {
        return name;
    }

    @Override
    public void delete() {
        Path directory = Paths.get(file.getPath());
        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            //DO Nothing
        }
        file.delete();
        removeFromParent();
    }
}
