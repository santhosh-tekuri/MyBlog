package santhosh;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class DocumentGuardTest{
    private static String code = "// @author Santhosh Kumar T - santhosh@in.fiorano.com\n" +
            "public class DocumentGuard{\n"+
            "    private JTextArea textComp;\n"+
            "    private Highlighter.HighlightPainter highlightPainter = new GuardBlockHighlightPainter();\n"+
            "\n"+
            "    private GuardFilter guardFilter = new GuardFilter();\n"+
            "\n"+
            "    public DocumentGuard(JTextArea textComp){\n"+
            "        this.textComp = textComp;\n"+
            "    }\n"+
            "\n"+
            "    public void addGuardedLines(int fromLine, int toLine) throws BadLocationException{\n"+
            "        int fromOffset = textComp.getLineStartOffset(fromLine);\n"+
            "        int toOffset = textComp.getLineEndOffset(toLine);\n"+
            "        addGuardedBlock(fromOffset, toOffset);\n"+
            "    }\n"+
            "\n"+
            "    private void addGuardedBlock(int fromOffset, int toOffset) throws BadLocationException{\n"+
            "        AbstractDocument doc = (AbstractDocument)textComp.getDocument();\n"+
            "        guardFilter.addGuardedBlock(doc.createPosition(fromOffset+1), doc.createPosition(toOffset-1));\n"+
            "        textComp.getHighlighter().addHighlight(fromOffset+1, toOffset-1, highlightPainter);\n"+
            "    }\n"+
            "\n"+
            "    public void setGuardEnabled(boolean enable){\n"+
            "        AbstractDocument doc = (AbstractDocument)textComp.getDocument();\n"+
            "        doc.setDocumentFilter(enable ? guardFilter : null);\n"+
            "    }\n"+
            "}";

    public static void main(String[] args) throws Exception {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Document Guard - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screen.width - 50, screen.height-50);

        final JTextArea editor = new JTextArea(code);
        final DocumentGuard guard = new DocumentGuard(editor);
        guard.addGuardedLines(0, 0);
        guard.addGuardedLines(2, 5);
        guard.addGuardedLines(11, 11);
        guard.addGuardedLines(15, 15);
        guard.setGuardEnabled(true);

        frame.getContentPane().add(new JScrollPane(editor));
        frame.show();
    }
}
