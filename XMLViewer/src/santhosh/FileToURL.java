package santhosh;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.BadLocationException;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import java.io.File;
import java.util.Date;

public class FileToURL{
    public static void main1(String[] args) throws Exception{
        File file = new File("C:\\Documents and Settings\\administrator\\Desktop\\xsl\\test.xsl");
        System.out.println("file.toURL() ----> "+file.toURL());
        System.out.println("file.toURI().toURL() ----> "+file.toURI().toURL());
    }

    public static void main(String[] args){
        JEditorPane editor = new JEditorPane("text/html",
                "<html>" +
                "   <body>" +
                "      <a href='http://www.google.com'>search</a>x" +
                "   </body>"+
                "</html>"
                );
        editor.setEditable(false);
        JFrame frame = new JFrame();
        editor.addHyperlinkListener(new HyperlinkListener(){
            public void hyperlinkUpdate(HyperlinkEvent e){
                if(e.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
                    Element elem = e.getSourceElement();
                    int start = elem.getStartOffset();
                    int end = elem.getEndOffset();
                    try{
                        elem.getDocument().remove(start, end-start);
                        elem.getDocument().insertString(start, new Date().toString(), elem.getAttributes());
                    } catch(BadLocationException e1){
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    System.err.println(e.getDescription());
                    String html = ((JEditorPane)e.getSource()).getText();
                    System.err.println(html);
                }
            }
        });
        frame.getContentPane().add(new JScrollPane(editor));
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
