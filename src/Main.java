import lexical_analysis.Lexer;
import lexical_analysis.Token;
import lexical_analysis.TokenType;
import syntax_analysis.Parser;
import syntax_analysis.tree.Node;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static Lexer lexer;

    private static void compile(String text){
        lexer = new Lexer(text, true);
        Parser parser = new Parser(lexer);
        Node tree = parser.parse();
    }

    private static String compileTerminal(String line){
        Parser parser = new Parser(lexer);
        parser.parse();

        return null;
    }

    public static void clearTerminal() {
        System.out.print("\033[H\033[2J");
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
    }

    private static void invalidUsage(){
        System.err.println("Invalid usage of the Groove compiler see -h or --help for more info");
        System.err.flush();
    }
}
