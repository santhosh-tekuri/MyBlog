package santhosh;

import javax.swing.text.JTextComponent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * This class can be used to highlight the current line for any JTextComponent.
 *
 * @author Santhosh Kumar T
 * @author Peter De Bruycker
 * @version 1.0
 */
public class CurrentLineHighlighter{
    private static final String LINE_HIGHLIGHT = "linehilight"; //NOI18N - used as clientproperty
    private static final String PREVIOUS_CARET = "previousCaret"; //NOI18N - used as clientproperty
    private static Color col = new Color(255, 255, 204); //Color used for highlighting the line

    // to be used as static utility
    private CurrentLineHighlighter(){}

    // Installs CurrentLineHilighter for the given JTextComponent
    public static void install(JTextComponent c){
        try{
            Object obj = c.getHighlighter().addHighlight(0, 0, painter);
            c.putClientProperty(LINE_HIGHLIGHT, obj);
            c.putClientProperty(PREVIOUS_CARET, new Integer(c.getCaretPosition()));
            c.addCaretListener(caretListener);
            c.addMouseListener(mouseListener);
            c.addMouseMotionListener(mouseListener);
        } catch(BadLocationException ignore){}
    }

    // Uninstalls CurrentLineHighligher for the given JTextComponent
    public static void uninstall(JTextComponent c){
        c.putClientProperty(LINE_HIGHLIGHT, null);
        c.putClientProperty(PREVIOUS_CARET, null);
        c.removeCaretListener(caretListener);
        c.removeMouseListener(mouseListener);
        c.removeMouseMotionListener(mouseListener);
    }

    private static CaretListener caretListener = new CaretListener(){
        public void caretUpdate(CaretEvent e){
            JTextComponent c = (JTextComponent)e.getSource();
            currentLineChanged(c);
        }
    };

    private static MouseInputAdapter mouseListener = new MouseInputAdapter(){
        public void mousePressed(MouseEvent e){
            JTextComponent c = (JTextComponent)e.getSource();
            currentLineChanged(c);
        }
        public void mouseDragged(MouseEvent e){
            JTextComponent c = (JTextComponent)e.getSource();
            currentLineChanged(c);
        }
    };

    /**
     * Fetches the previous caret location, stores the current caret location,
     * If the caret is on another line, repaint the previous line and the current line
     *
     * @param c the text component
     */
    private static void currentLineChanged(JTextComponent c){
        try{
            int previousCaret = ((Integer)c.getClientProperty(PREVIOUS_CARET)).intValue();
            Rectangle prev = c.modelToView(previousCaret);
            Rectangle r = c.modelToView(c.getCaretPosition());
            c.putClientProperty(PREVIOUS_CARET, new Integer(c.getCaretPosition()));

            if(prev.y!=r.y){
                c.repaint(0, prev.y, c.getWidth(), r.height);
                c.repaint(0, r.y, c.getWidth(), r.height);
            }
        } catch(BadLocationException ignore){}
    }

    private static Highlighter.HighlightPainter painter = new Highlighter.HighlightPainter(){
        public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c){
            try{
                Rectangle r = c.modelToView(c.getCaretPosition());
                g.setColor(col);
                g.fillRect(0, r.y, c.getWidth(), r.height);
            } catch(BadLocationException ignore){}
        }
    };

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        final JFrame frame = new JFrame("CurrentLineHighlighter - santhosh@in.fiorano.com");
        JTextArea textArea = new JTextArea("Hi, \n"+
                "\n"+
                "try to move cursor. you will notice that\n"+
                "highlighter follows the caret(current line)\n"+
                "\n"+
                "- santhosh\n"+
                "\n"+
                "", 10, 50);
        CurrentLineHighlighter.install(textArea);
        frame.getContentPane().add(new JScrollPane(textArea));
        frame.getContentPane().add(new JButton("xxxx"), BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel contents = (JPanel)frame.getContentPane();
        contents.registerKeyboardAction(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    SwingUtilities.updateComponentTreeUI(frame);
                } catch(Exception ex){
                    ex.printStackTrace();
                }
                System.err.println("changed");
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}