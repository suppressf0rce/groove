package ide.view.dock;

import bibliothek.gui.dock.common.DefaultMultipleCDockable;
import ide.control.GrooveCompletionProvider;
import ide.control.GrooveFoldParser;
import ide.model.Colors;
import ide.model.Settings;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

public class EditorDock extends DefaultMultipleCDockable implements SyntaxConstants {

    private RSyntaxTextArea textArea;
    private RTextScrollPane scrollPane;
    private ErrorStrip errorStrip;

    public EditorDock(String title, String imagePath) {
        super(null);

        setTitleText(title);
        ImageIcon imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource(imagePath)));
        setTitleIcon(imageIcon);

        setCloseable(true);
        setExternalizable(false);

        textArea = new RSyntaxTextArea();
        scrollPane = new RTextScrollPane(textArea);

        initializeTextArea();
        installHighlighting();
        changeStyle();

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
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

        //textArea.setMarkOccurrences(true);


        AutoCompletion ac = new AutoCompletion(new GrooveCompletionProvider());
        ac.install(textArea);

    }

    private void installHighlighting() {
        AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        atmf.putMapping("text/Groove", "ide.control.GrooveTokenMaker");
        FoldParserManager.get().addFoldParserMapping("text/Groove", new GrooveFoldParser(true, false));
        textArea.setSyntaxEditingStyle("text/Groove");
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


}
