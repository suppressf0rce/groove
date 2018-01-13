package ide.model.project_explorer;

import ide.control.FileWorker;
import ide.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;

@SuppressWarnings("Duplicates")
public class Package extends DefaultMutableTreeNode implements Renameable, Deleteable {

    private File file;
    private String name;
    private ImageIcon icon;

    public Package(File file) {
        this.file = file;
        this.name = file.getName();

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/package.png")));
    }

    public Package() {
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/package.png")));
    }

    @Override
    public String toString() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    @SuppressWarnings("Duplicates")
    public void loadContent() {
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

    public void createPackage(Package pckg) {
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

    public void createGrooveFile(GrooveFile file, boolean mainFunction) {
        if (!file.getName().endsWith(".gr"))
            file.setName(file.getName() + ".gr");

        File content = new File(getFile().getAbsolutePath() + File.separator + file.getName());

        StringBuilder pck = new StringBuilder(name);
        TreeNode parent = getParent();
        while (parent instanceof Package) {
            pck.insert(0, ((Package) getParent()).name + ".");
            parent = parent.getParent();
        }

        pck.insert(0, "package ");

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(content), Charset.defaultCharset()))) {
            writer.write(pck.toString());

            if (mainFunction)
                writer.write("\n\nlet function main() be void:\n\nend");

            writer.close();
            file.setFile(content);
            add(file);
            FileWorker.open(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Could not create Groove file! \nPlease try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Could not rename package", "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.name = newName;
    }

    @Override
    public void delete() {
        file.delete();
        removeFromParent();
    }
}
