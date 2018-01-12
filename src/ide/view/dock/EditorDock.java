package ide.view.dock;

import bibliothek.gui.dock.common.DefaultMultipleCDockable;
import ide.control.GrooveCompletionProvider;
import ide.control.GrooveFoldParser;
import ide.model.Colors;
import ide.model.Settings;
import ide.model.project_explorer.GrooveFile;
import ide.model.project_explorer.OtherFile;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EditorDock extends DefaultMultipleCDockable implements SyntaxConstants {

    private RSyntaxTextArea textArea;
    private RTextScrollPane scrollPane;
    private ErrorStrip errorStrip;
    private DefaultMutableTreeNode node;
    private File file;

    public EditorDock(String title, DefaultMutableTreeNode node) {
        super(null);
        this.node = node;

        setTitleText(title);

        setCloseable(true);
        setExternalizable(false);

        textArea = new RSyntaxTextArea();
        scrollPane = new RTextScrollPane(textArea);

        initializeTextArea();
        changeStyle();

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        if (node instanceof GrooveFile) {
            installHighlighting();
            setTitleIcon(((GrooveFile) node).getIcon());
            file = ((GrooveFile) node).getFile();

            Scanner scan = null;
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (scan != null) {
                scan.useDelimiter("\\Z");
                String content = scan.next();
                textArea.setText(content);
            }
        } else if (node instanceof OtherFile) {
            setTitleIcon(((OtherFile) node).getIcon());
            file = ((OtherFile) node).getFile();

            installOtherHighlighting(file.getName());
        }
    }

    private void installOtherHighlighting(String name) {

        LanguageSupportFactory.get().register(textArea);

        //ActionScript
        if (name.toLowerCase().endsWith(".actionscript")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ACTIONSCRIPT);
        }

        //Asm
        else if (name.toLowerCase().endsWith(".asm")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
        }

        //BBCode
        else if (name.toLowerCase().endsWith(".phpBB")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_BBCODE);
        }

        //C
        else if (name.toLowerCase().endsWith(".c")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        }

        //H
        else if (name.toLowerCase().endsWith(".h")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        }

        //CLojure
        else if (name.toLowerCase().endsWith(".clj")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CLOJURE);
        }

        //C++
        else if (name.toLowerCase().endsWith(".cpp")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
        }

        //C#
        else if (name.toLowerCase().endsWith(".cs")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSHARP);
        }

        //CSS
        else if (name.toLowerCase().endsWith(".css")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
        }

        //D
        else if (name.toLowerCase().endsWith(".d")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_D);
        }

        //Dart
        else if (name.toLowerCase().endsWith(".dart")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DART);
        }

        //Delphi group project
        else if (name.toLowerCase().endsWith(".groupproj")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DELPHI);
        }

        //Delphi Project
        else if (name.toLowerCase().endsWith(".dproj")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DELPHI);
        }

        //Delphi Project
        else if (name.toLowerCase().endsWith(".dpr")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DELPHI);
        }

        //Dockerfile
        else if (name.toLowerCase().contains("dockerfile")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DOCKERFILE);
        }

        //DTD
        else if (name.toLowerCase().endsWith(".dtd")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DTD);
        }

        //Fortran
        else if (name.toLowerCase().endsWith(".f")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_FORTRAN);
        }

        //Fortran
        else if (name.toLowerCase().endsWith(".f90")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DTD);
        }

        //Groovy
        else if (name.toLowerCase().endsWith(".groovy")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
        }

        //Hosts
        else if (name.toLowerCase().endsWith(".hosts")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HOSTS);
        }

        //Htaccess
        else if (name.toLowerCase().endsWith(".htaccess")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTACCESS);
        }

        //HTML
        else if (name.toLowerCase().endsWith(".html")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
        }

        //INI
        else if (name.toLowerCase().endsWith(".ini")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_INI);
        }

        //Java
        else if (name.toLowerCase().endsWith(".java")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        }

        //JavaScript
        else if (name.toLowerCase().endsWith(".js")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        }

        //JSON
        else if (name.toLowerCase().endsWith(".json")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        }

        //Jshintrc (Jason with comments)
        else if (name.toLowerCase().endsWith(".jshintrc")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON_WITH_COMMENTS);
        }

        //JSP
        else if (name.toLowerCase().endsWith(".jsp")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSP);
        }

        //Latex
        else if (name.toLowerCase().endsWith(".tex")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
        }

        //LESS
        else if (name.toLowerCase().endsWith(".less")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LESS);
        }

        //Lisp
        else if (name.toLowerCase().endsWith(".lsp")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LISP);
        }

        //LUA
        else if (name.toLowerCase().endsWith(".lua")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
        }

        //Makefile
        else if (name.toLowerCase().endsWith(".make")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_MAKEFILE);
        }

        //Makefile
        else if (name.toLowerCase().endsWith(".mk")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_MAKEFILE);
        }

        //Makefile
        else if (name.toLowerCase().endsWith(".gmk")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_MAKEFILE);
        }

        //MXML
        else if (name.toLowerCase().endsWith(".mxml")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_MXML);
        }

        //NSIS
        else if (name.toLowerCase().endsWith(".nsi")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NSIS);
        }

        //Perl
        else if (name.toLowerCase().endsWith(".pl")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PERL);
        }

        //PHP
        else if (name.toLowerCase().endsWith(".php")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PHP);
        }

        //Properties
        else if (name.toLowerCase().endsWith(".properties")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE);
        }

        //Python
        else if (name.toLowerCase().endsWith(".py")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
        }

        //Ruby
        else if (name.toLowerCase().endsWith(".rb")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_RUBY);
        }

        //SAS
        else if (name.toLowerCase().endsWith(".sas")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SAS);
        }

        //Scala
        else if (name.toLowerCase().endsWith(".scala")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA);
        }

        //SQL
        else if (name.toLowerCase().endsWith(".sql")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
        }

        //TCL
        else if (name.toLowerCase().endsWith(".tcl")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_TCL);
        }

        //TypeScript
        else if (name.toLowerCase().endsWith(".ts")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_TYPESCRIPT);
        }

        //Shell
        else if (name.toLowerCase().endsWith(".sh")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);
        }

        //VisualBasic
        else if (name.toLowerCase().endsWith(".vb")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_VISUAL_BASIC);
        }

        //Batch
        else if (name.toLowerCase().endsWith(".bat")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_WINDOWS_BATCH);
        }

        //XML
        else if (name.toLowerCase().endsWith(".xml")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        }

        //YAML
        else if (name.toLowerCase().endsWith(".yaml")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_YAML);
        }

        //YAML
        else if (name.toLowerCase().endsWith(".yml")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_YAML);
        } else {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        }

    }

    private void initializeTextArea() {

//        File file = new File(getClass().getClassLoader().getResource("english_dic.zip").getPath());
//        try {
//            //textArea.addParser(SpellingParser.createEnglishSpellingParser(file,true));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        errorStrip = new ErrorStrip(textArea);
        textArea.setAnimateBracketMatching(true);
        textArea.setLineWrap(true);
        textArea.setAntiAliasingEnabled(true);
        textArea.setAutoIndentEnabled(true);
        textArea.setCodeFoldingEnabled(true);
        RSyntaxTextArea.setTemplatesEnabled(true);
        textArea.setHyperlinksEnabled(true);
        textArea.setDragEnabled(true);
        textArea.setMarginLineEnabled(true);
        textArea.setMarginLineColor(Colors.GUTTER_FOREGROUND);
        textArea.setMarginLinePosition(125);

        //textArea.setMarkOccurrences(true);

    }

    private void installHighlighting() {
        AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        atmf.putMapping("text/Groove", "ide.control.GrooveTokenMaker");
        FoldParserManager.get().addFoldParserMapping("text/Groove", new GrooveFoldParser(true, false));
        textArea.setSyntaxEditingStyle("text/Groove");

        AutoCompletion ac = new AutoCompletion(new GrooveCompletionProvider());
        ac.install(textArea);
    }

    private void changeStyle() {
        SyntaxScheme scheme = textArea.getSyntaxScheme();
        scheme.getStyle(Token.RESERVED_WORD).foreground = Colors.KEYWORD;
        scheme.getStyle(Token.RESERVED_WORD).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Colors.STRING;
        scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.LITERAL_CHAR).foreground = Colors.STRING;
        scheme.getStyle(Token.LITERAL_CHAR).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.COMMENT_EOL).foreground = Colors.COMMENT;
        scheme.getStyle(Token.COMMENT_EOL).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.COMMENT_MULTILINE).foreground = Colors.COMMENT;
        scheme.getStyle(Token.COMMENT_MULTILINE).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Colors.NUMBER;
        scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = Colors.NUMBER;
        scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.LITERAL_NUMBER_HEXADECIMAL).foreground = Colors.NUMBER;
        scheme.getStyle(Token.LITERAL_NUMBER_HEXADECIMAL).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.LITERAL_BOOLEAN).foreground = Colors.KEYWORD;
        scheme.getStyle(Token.LITERAL_BOOLEAN).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.FUNCTION).foreground = Colors.VAR;
        scheme.getStyle(Token.FUNCTION).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.IDENTIFIER).foreground = Colors.VAR;
        scheme.getStyle(Token.IDENTIFIER).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.FUNCTION).foreground = Colors.VAR;
        scheme.getStyle(Token.FUNCTION).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.DATA_TYPE).foreground = Colors.KEYWORD;
        scheme.getStyle(Token.DATA_TYPE).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.VARIABLE).foreground = Colors.IMPLICIT_PARAMETER;
        scheme.getStyle(Token.VARIABLE).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.OPERATOR).foreground = Colors.VAR;
        scheme.getStyle(Token.OPERATOR).font = Settings.EDITOR_FONT;
        scheme.getStyle(Token.SEPARATOR).foreground = Colors.VAR;
        scheme.getStyle(Token.SEPARATOR).font = Settings.EDITOR_FONT;

        textArea.setBackground(Colors.BACKGROUND);

        textArea.setCurrentLineHighlightColor(Colors.BACKGROUND_HIGHLIGHTED);

        textArea.revalidate();

        scrollPane.getGutter().setBackground(Colors.GUTTER_BACKGROUND);
        scrollPane.getGutter().setForeground(Colors.GUTTER_FOREGROUND);
        scrollPane.getGutter().setBorderColor(Colors.GUTTER_FOREGROUND);
        scrollPane.revalidate();
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }
}
