package ide.model;

import ide.view.MainFrame;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.List;

public class Configuration {

    private String name;
    private File file;
    private Process process;

    public Configuration(String name) {
        this.name = name;
    }

    public void run() throws Exception {
//        // java binary
//        String java = System.getProperty("java.home") + "/bin/java";
//        // vm arguments
//        List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
//        StringBuffer vmArgsOneLine = new StringBuffer();
//        for (String arg : vmArguments) {
//            if (!arg.contains("-agentlib")) {
//                vmArgsOneLine.append(arg);
//                vmArgsOneLine.append(" ");
//            }
//        }
//
//        final StringBuffer cmd = new StringBuffer("" + java + " " + vmArgsOneLine);
//
//        // program main and program arguments
//        String[] mainCommand = System.getProperty("sun.java.command").split(" ");
//
//        // program main is a jar
//        if (mainCommand[0].endsWith(".jar")) {
//
//            // if it's a jar, add -jar mainJar
//            cmd.append("-jar " + new File(mainCommand[0]).getPath());
//        } else {
//
//            // else it's a .class, add the classpath and mainClass
//            cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
//        }
//        // finally add program arguments
//
//        cmd.append(" --file ");
//        cmd.append(file.getAbsolutePath());

        // java binary
        String java = System.getProperty("java.home") + File.separator + "bin"+File.separator+"java";
        // vm arguments
        List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        StringBuffer vmArgsOneLine = new StringBuffer();
        for (String arg : vmArguments) {
            // if it's the agent argument : we ignore it otherwise the
            // address of the old application and the new one will be in conflict
            if (!arg.contains("-agentlib")) {
                vmArgsOneLine.append(arg);
                vmArgsOneLine.append(" ");
            }
        }
        // init the command to execute, add the vm args
        final StringBuffer cmd = new StringBuffer("" + java + " " + vmArgsOneLine);
        // program main and program arguments (be careful a sun property. might not be supported by all JVM)
        String[] mainCommand = System.getProperty("sun.java.command").split(" ");
        // program main is a jar
        if (mainCommand[0].endsWith(".jar")) {
            // if it's a jar, add -jar mainJar
            cmd.append("-jar " + new File(mainCommand[0]).getPath());
        } else {
            // else it's a .class, add the classpath and mainClass
            //cmd.append("-cp \"" + System.getProperty("java.class.path").replaceAll(" ", "\\ ") + "\" " + mainCommand[0]);
        }
        // finally add program arguments
        //cmd.append(" --file "+file.getAbsolutePath());
        cmd.append(" --file " + file.getAbsolutePath());
        // execute the command in a shutdown hook, to be sure that all the
        // resources have been disposed before restarting the application

        MainFrame.getInstance().getConsoleDock().getConsole().setText("");
        MainFrame.getInstance().getConsoleDock().getConsole().append("Starting Process...\n");

        process = Runtime.getRuntime().exec(cmd.toString());

        MainFrame.getInstance().getConsoleDock().loadIn(process.getInputStream());
        MainFrame.getInstance().getConsoleDock().loadErr(process.getErrorStream());
        MainFrame.getInstance().getConsoleDock().loadOut(process.getOutputStream());

        if (!MainFrame.getInstance().getConsoleDock().isRunning()) {
            MainFrame.getInstance().getConsoleDock().startConsole();
        }

        Thread processThread = new Thread(() -> {
            int result;
            try {
                result = process.waitFor();

                while (true) {
                    if (MainFrame.getInstance().getConsoleDock().isAccesable()) {
                        MainFrame.getInstance().getConsoleDock().getConsole().append("\nProcess exited with code: " + result);
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        processThread.start();

    }

    public void debug() {

    }

    public void stop() {
        if (process != null && process.isAlive()) {
            process.destroyForcibly();
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
