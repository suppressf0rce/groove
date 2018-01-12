package ide.control;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.folding.Fold;
import org.fife.ui.rsyntaxtextarea.folding.FoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldType;

import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.List;

public class GrooveFoldParser implements FoldParser {

    /**
     * Ending of a multi-line comment in C, C++, Java, etc.
     */
    protected static final char[] C_MLC_END = "*/".toCharArray();
    /**
     * Used to find import statements when folding Java code.
     */
    private static final char[] KEYWORD_IMPORT = "import".toCharArray();
    /**
     * Whether this parser is folding Groove.
     */
    private final boolean groove;
    /**
     * Whether to scan for G-style multi-line comments and make them foldable.
     */
    private boolean foldableMultiLineComments;


    /**
     * Creates a fold parser that identifies foldable regions via curly braces
     * as well as C-style multi-line comments.
     */
    public GrooveFoldParser() {
        this(true, false);
    }


    /**
     * Constructor.
     *
     * @param cStyleMultiLineComments Whether to scan for C-style multi-line
     *                                comments and make them foldable.
     * @param groove                  Whether this parser is folding Java.  This adds extra
     *                                parsing rules, such as grouping all import statements into a
     *                                fold section.
     */
    public GrooveFoldParser(boolean cStyleMultiLineComments, boolean groove) {
        this.foldableMultiLineComments = cStyleMultiLineComments;
        this.groove = groove;
    }


    /**
     * Returns whether multi-line comments are foldable with this parser.
     *
     * @return Whether multi-line comments are foldable.
     * @see #setFoldableMultiLineComments(boolean)
     */
    public boolean getFoldableMultiLineComments() {
        return foldableMultiLineComments;
    }

    /**
     * Sets whether multi-line comments are foldable with this parser.
     *
     * @param foldable Whether multi-line comments are foldable.
     * @see #getFoldableMultiLineComments()
     */
    public void setFoldableMultiLineComments(boolean foldable) {
        this.foldableMultiLineComments = foldable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Fold> getFolds(RSyntaxTextArea textArea) {

        List<Fold> folds = new ArrayList<Fold>();

        Fold currentFold = null;
        int lineCount = textArea.getLineCount();
        boolean inMLC = false;
        int mlcStart = 0;
        int importStartLine = -1;
        int lastSeenImportLine = -1;
        int importGroupStartOffs = -1;
        int importGroupEndOffs = -1;
        int lastRightCurlyLine = -1;
        Fold prevFold = null;

        try {

            for (int line = 0; line < lineCount; line++) {

                Token t = textArea.getTokenListForLine(line);
                while (t != null && t.isPaintable()) {

                    if (getFoldableMultiLineComments() && t.isComment()) {

                        // Groove-specific stuff
                        if (groove) {

                            if (importStartLine > -1) {
                                if (lastSeenImportLine > importStartLine) {
                                    Fold fold = null;
                                    // Any imports found *should* be a top-level fold,
                                    // but we're extra lenient here and allow groups
                                    // of them anywhere to keep our parser better-behaved
                                    // if they have random "imports" throughout code.
                                    if (currentFold == null) {
                                        fold = new Fold(FoldType.IMPORTS,
                                                textArea, importGroupStartOffs);
                                        folds.add(fold);
                                    } else {
                                        fold = currentFold.createChild(FoldType.IMPORTS,
                                                importGroupStartOffs);
                                    }
                                    fold.setEndOffset(importGroupEndOffs);
                                }
                                importStartLine = lastSeenImportLine =
                                        importGroupStartOffs = importGroupEndOffs = -1;
                            }

                        }

                        if (inMLC) {
                            // If we found the end of an MLC that started
                            // on a previous line...
                            if (t.endsWith(C_MLC_END)) {
                                int mlcEnd = t.getEndOffset() - 1;
                                if (currentFold == null) {
                                    currentFold = new Fold(FoldType.COMMENT, textArea, mlcStart);
                                    currentFold.setEndOffset(mlcEnd);
                                    folds.add(currentFold);
                                    currentFold = null;
                                } else {
                                    currentFold = currentFold.createChild(FoldType.COMMENT, mlcStart);
                                    currentFold.setEndOffset(mlcEnd);
                                    currentFold = currentFold.getParent();
                                }
                                //System.out.println("Ending MLC at: " + mlcEnd + ", parent==" + currentFold);
                                inMLC = false;
                                mlcStart = 0;
                            }
                            // Otherwise, this MLC is continuing on to yet
                            // another line.
                        } else {
                            // If we're an MLC that ends on a later line...
                            if (t.getType() != Token.COMMENT_EOL && !t.endsWith(C_MLC_END)) {
                                //System.out.println("Starting MLC at: " + t.offset);
                                inMLC = true;
                                mlcStart = t.getOffset();
                            }
                        }

                    } else if (isLeftCurly(t)) {

                        // Java-specific stuff
                        if (groove) {

                            if (importStartLine > -1) {
                                if (lastSeenImportLine > importStartLine) {
                                    Fold fold = null;
                                    // Any imports found *should* be a top-level fold,
                                    // but we're extra lenient here and allow groups
                                    // of them anywhere to keep our parser better-behaved
                                    // if they have random "imports" throughout code.
                                    if (currentFold == null) {
                                        fold = new Fold(FoldType.IMPORTS,
                                                textArea, importGroupStartOffs);
                                        folds.add(fold);
                                    } else {
                                        fold = currentFold.createChild(FoldType.IMPORTS,
                                                importGroupStartOffs);
                                    }
                                    fold.setEndOffset(importGroupEndOffs);
                                }
                                importStartLine = lastSeenImportLine =
                                        importGroupStartOffs = importGroupEndOffs = -1;
                            }

                        }

                        // If a new fold block starts on the same line as the
                        // previous one ends, we treat it as one big block
                        // (e.g. K&R-style "} else {")
                        if (prevFold != null && line == lastRightCurlyLine) {
                            currentFold = prevFold;
                            // Keep currentFold.endOffset where it was, so that
                            // unclosed folds at end of the file work as well
                            // as possible
                            prevFold = null;
                            lastRightCurlyLine = -1;
                        } else if (currentFold == null) { // A top-level fold
                            currentFold = new Fold(FoldType.CODE, textArea, t.getOffset());
                            folds.add(currentFold);
                        } else { // A nested fold
                            currentFold = currentFold.createChild(FoldType.CODE, t.getOffset());
                        }

                    } else if (isRightCurly(t)) {

                        if (currentFold != null) {
                            currentFold.setEndOffset(t.getOffset());
                            Fold parentFold = currentFold.getParent();
                            //System.out.println("... Adding regular fold at " + t.offset + ", parent==" + parentFold);
                            // Don't add fold markers for single-line blocks
                            if (currentFold.isOnSingleLine()) {
                                if (!currentFold.removeFromParent()) {
                                    folds.remove(folds.size() - 1);
                                }
                            } else {
                                // Remember the end of the last completed fold,
                                // in case it needs to get merged with the next
                                // one (e.g. K&R "} else {" style)
                                lastRightCurlyLine = line;
                                prevFold = currentFold;
                            }
                            currentFold = parentFold;
                        }

                    }

                    // Java-specific folding rules
                    else if (groove) {

                        if (t.is(Token.RESERVED_WORD, KEYWORD_IMPORT)) {
                            if (importStartLine == -1) {
                                importStartLine = line;
                                importGroupStartOffs = t.getOffset();
                                importGroupEndOffs = t.getOffset();
                            }
                            lastSeenImportLine = line;
                        } else if (importStartLine > -1 &&
                                t.getType() == Token.NULL &&//SEPARATOR &&
                                t.isSingleChar('\n')) {
                            importGroupEndOffs = t.getOffset();
                        }

                    }

                    t = t.getNextToken();

                }

            }

        } catch (BadLocationException ble) { // Should never happen
            ble.printStackTrace();
        }

        return folds;

    }

    /**
     * Returns whether the token is a left curly brace.  This method exists
     * so subclasses can provide their own curly brace definition.
     *
     * @param t The token.
     * @return Whether it is a left curly brace.
     * @see #isRightCurly(Token)
     */
    public boolean isLeftCurly(Token t) {
        if (t.getTextArray().length > t.getOffset())
            return t.getTextArray()[t.getOffset()] == ':';
        return false;
    }

    /**
     * Returns whether the token is a right curly brace.  This method exists
     * so subclasses can provide their own curly brace definition.
     *
     * @param t The token.
     * @return Whether it is a right curly brace.
     * @see #isLeftCurly(Token)
     */
    public boolean isRightCurly(Token t) {
        boolean result = false;
        if (t.getOffset() + 2 < t.getTextArray().length) {
            if (t.getTextArray()[t.getOffset()] == 'e' && t.getTextArray()[t.getOffset() + 1] == 'n' && t.getTextArray()[t.getOffset() + 2] == 'd')
                result = true;
        }

        return result;
    }
}
