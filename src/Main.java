import Interpreter.Interpreter;
import com.bulenkov.darcula.DarculaLaf;
import ide.view.MainFrame;
import ide.view.modules.SelectWorkspace;
import lexical_analysis.Lexer;
import semantic_analysis.SemanticAnalyzer;
import syntax_analysis.Parser;
import syntax_analysis.tree.Node;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static Lexer lexer;

    private static void compile(String text){
        lexer = new Lexer(text, true);
        Parser parser = new Parser(lexer);
        Node tree = parser.parse();
        SemanticAnalyzer.analyze(tree, true);

        Interpreter.run(tree);
    }

    private static String compileTerminal(String line){
        Parser parser = new Parser(lexer);
        parser.parse();

        return null;
    }

    public static void clearTerminal() {
        System.out.print("\033[H\033[2J");
    }

    private static void ide() {

        BasicLookAndFeel darcula = new DarculaLaf();
        try {
            UIManager.getFont("Label.font");
            Font f = new Font("arial", Font.PLAIN, 15);
            UIManager.put("Menu.font", f);
            UIManager.put("MenuItem.font", f);
            UIManager.put("MenuBar.font", f);
            UIManager.put("defaultFont", f);
            UIManager.put("Button.font", f);
            UIManager.put("ToggleButton.font", f);
            UIManager.put("RadioButton.font", f);
            UIManager.put("CheckBox.font", f);
            UIManager.put("ColorChooser.font", f);
            UIManager.put("ComboBox.font", f);
            UIManager.put("Label.font", f);
            UIManager.put("List.font", f);
            UIManager.put("MenuBar.font", f);
            UIManager.put("MenuItem.font", f);
            UIManager.put("RadioButtonMenuItem.font", f);
            UIManager.put("CheckBoxMenuItem.font", f);
            UIManager.put("Menu.font", f);
            UIManager.put("PopupMenu.font", f);
            UIManager.put("OptionPane.font", f);
            UIManager.put("Panel.font", f);
            UIManager.put("ProgressBar.font", f);
            UIManager.put("ScrollPane.font", f);
            UIManager.put("Viewport.font", f);
            UIManager.put("TabbedPane.font", f);
            UIManager.put("Table.font", f);
            UIManager.put("TableHeader.font", f);
            UIManager.put("TextField.font", f);
            UIManager.put("PasswordField.font", f);
            UIManager.put("TextArea.font", f);
            UIManager.put("TextPane.font", f);
            UIManager.put("EditorPane.font", f);
            UIManager.put("TitledBorder.font", f);
            UIManager.put("ToolBar.font", f);
            UIManager.put("ToolTip.font", f);
            UIManager.put("Tree.font", f);
            UIManager.setLookAndFeel(darcula);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        //JFrame.setDefaultLookAndFeelDecorated(true);
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);

        new SelectWorkspace();
    }

    private static void terminal(){

        lexer = new Lexer("package terminal\n",false);
        Scanner scanner = new Scanner(System.in);
        System.out.print("$Groove > ");
        String line = scanner.nextLine();
        String result = null;
        if(!line.equals("exit") && !line.equals("quit"))
            result = compileTerminal(line);

        if(result != null)
            System.out.println(result);

        while(!line.equals("exit") && !line.equals("quit")){
            System.out.print("$Groove > ");
            line = scanner.nextLine();

            if(line.equals("clear")){
                clearTerminal();
                continue;
            }

            result = compileTerminal(line);

            if(result != null)
                System.out.println(result);
        }

    }

    public static void main(String[] args) {

        if(args.length > 0){

            if(args.length > 2) {
                invalidUsage();
                return;
            }

            //Checking if user wants help
            if(args[0].equals("-h") || args[0].equals("--help") ){
                usage();
                return;
            }

            //Check if argument is file
            if(args[0].equals("-f") || args[0].equals("--file")){

                //Check if we have file argument
                if(args.length > 1){

                    //Check if file exists
                    File file = new File(args[1]);
                    try {
                        compile(new String(Files.readAllBytes(Paths.get(file.getPath()))));
                    } catch (IOException e) {
                        System.err.println("System error: Could not read the file: "+args[1]);
                        System.err.flush();
                    }

                }else{
                    invalidUsage();
                    return;
                }

            }


            //Check if argument is code
            if(args[0].equals("-c") || args[0].equals("--code")){
                if(args.length > 1){
                    String code = args[1];
                    compile(code);
                }else{
                    invalidUsage();
                    return;
                }
            }


            //Check if argument is terminal
            if(args[0].equals("-t") || args[0].equals("--terminal")){
                if(args.length > 1){
                    invalidUsage();
                }else{
                    terminal();
                }
            }

            //Check if argument is IDE
            if (args[0].equals("-i") || args[0].equals("--ide")) {
                if (args.length > 1) {
                    invalidUsage();
                } else {
                    ide();
                }
            }


        }else{
            invalidUsage();
        }
    }


    private static void usage(){
        System.out.println("Groove [options]");
        System.out.println("Options:");

        System.out.println("-h | --help       Print the help");
        System.out.println("-f | --file       An file with the groove code");
        System.out.println("-c | --code       Raw groove code");
        System.out.println("-t | --terminal   Open groove in terminal mode");
        System.out.println("-i | --ide        Opens the groove IDE");
    }

    private static void invalidUsage(){
        System.err.println("Invalid usage of the Groove compiler see -h or --help for more info");
        System.err.flush();
    }
}
