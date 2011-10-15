package santhosh;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.Reader;
import java.io.StringReader;

public class XMLEditor extends JFrame{
    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
            "\n"+
            "<!-- TODO :: specify project name here -->\n"+
            "<project basedir=\".\" default=\"make\" name=\"undoredodropDownButton1\">\n"+
            "\n"+
            "  <target name=\"init\">\n"+
            "    <mkdir dir=\"classes\"/>\n"+
            "  </target>\n"+
            "\n"+
            "  <target name=\"compile\" depends=\"init\">\n"+
            "    <javac srcdir=\"src\" destdir=\"classes\" debug=\"true\" deprecation=\"false\" nowarn=\"false\"/>\n"+
            "  </target>\n"+
            "\n"+
            "</project>";

    JTextArea editor = new JTextArea(xml);
    JTextArea errors = new JTextArea();

    public XMLEditor(){
        super("XMLEditor - santhosh@in.fiorano.com");
        errors.setEditable(false);
        errors.setForeground(Color.red);
        editor.getDocument().addDocumentListener(new DelayedDocumentListener(editorListener));
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createScroll(editor), createScroll(errors));
        split.setResizeWeight(0.7d);

        JPanel contents = (JPanel)getContentPane();
        contents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(split);
    }

    DocumentListener editorListener = new DocumentListener(){
        public void insertUpdate(DocumentEvent e){
            validateXML(e.getDocument());
        }

        public void removeUpdate(DocumentEvent e){
            validateXML(e.getDocument());
        }

        public void changedUpdate(DocumentEvent e){
            validateXML(e.getDocument());
        }
    };

    private void validateXML(Document doc){
        try{
            errors.setText("");

            Reader reader = new StringReader(doc.getText(0, doc.getLength()));
            SAXParserFactory.newInstance().newSAXParser().parse(new InputSource(reader), new DefaultHandler(){
                public void warning(SAXParseException e){
                    errors.append("Warning ["+e.getLineNumber()+':'+e.getColumnNumber()+"]: "+ e.getMessage()+"\n");
                }

                public void error(SAXParseException e){
                    errors.append("Error ["+e.getLineNumber()+':'+e.getColumnNumber()+"]: "+ e.getMessage()+"\n");
                }

                public void fatalError(SAXParseException e) throws SAXException{
                    errors.append("FatalError ["+e.getLineNumber()+':'+e.getColumnNumber()+"]: "+ e.getMessage()+"\n");
                    super.fatalError(e);
                }
            });
        } catch(Exception ignore){
        }
    }

    private JScrollPane createScroll(Component c){
        JScrollPane scroll = new JScrollPane(c);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }
}
