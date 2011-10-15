package santhosh;

import javax.swing.text.*;
import javax.swing.*;
import javax.swing.plaf.TextUI;
import java.awt.*;

public class DocumentGuard{
    private JTextArea textComp;
    private Highlighter.HighlightPainter highlightPainter = new GuardBlockHighlightPainter();

    private GuardFilter guardFilter = new GuardFilter();

    public DocumentGuard(JTextArea textComp){
        this.textComp = textComp;
    }

    public void addGuardedLines(int fromLine, int toLine) throws BadLocationException{
        int fromOffset = textComp.getLineStartOffset(fromLine);
        int toOffset = textComp.getLineEndOffset(toLine);
        addGuardedBlock(fromOffset, toOffset);
    }

    private void addGuardedBlock(int fromOffset, int toOffset) throws BadLocationException{
        AbstractDocument doc = (AbstractDocument)textComp.getDocument();
        guardFilter.addGuardedBlock(doc.createPosition(fromOffset), doc.createPosition(toOffset-1));
        textComp.getHighlighter().addHighlight(fromOffset+1, toOffset-1, highlightPainter);
    }

    public void setGuardEnabled(boolean enable){
        AbstractDocument doc = (AbstractDocument)textComp.getDocument();
        doc.setDocumentFilter(enable ? guardFilter : null);
    }
}
