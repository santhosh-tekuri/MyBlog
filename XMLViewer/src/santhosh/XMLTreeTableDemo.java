package santhosh;

import org.xml.sax.InputSource;
import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import java.awt.*;
import java.io.StringReader;
import java.io.StringWriter;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class XMLTreeTableDemo extends JFrame{
    JScrollPane scroll = new JScrollPane(){
        public void setColumnHeaderView(Component view){
        }
    };
    JTextArea editor = new JTextArea(20, 75);

    public XMLTreeTableDemo(){
        super("XMLViewer - santhosh@in.fiorano.com");
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(10, 10, 10, 10));
        final JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("EDITOR", new JScrollPane(editor));
        tabPane.addTab("TREETABLE", scroll);

        JPanel contents = (JPanel)getContentPane();
        contents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contents.add(tabPane);

        tabPane.addChangeListener(new ChangeListener(){
            boolean processing = false;
            public void stateChanged(ChangeEvent ce){
                if(processing)
                    return;
                processing = true;
                try{
                    switch(tabPane.getSelectedIndex()){
                        case 0:
                            try{
                                updateText();
                            } catch(Exception ex){
                                ex.printStackTrace();
                                tabPane.setSelectedIndex(1);
                                JOptionPane.showMessageDialog(tabPane, ex.getMessage());
                            }
                            break;
                        case 1:
                            try{
                                updateTree();
                            } catch(Exception ex){
                                tabPane.setSelectedIndex(0);
                                JOptionPane.showMessageDialog(tabPane, ex.getMessage());
                            }
                    }
                }finally{
                    processing = false;
                }
            }
        });
    }    

    private MyTreeTable createTreeTable(XMLTreeTableModel model){
        MyTreeTable treeTable = new MyTreeTable(model);
        JTree tree = (JTree)treeTable.getDefaultRenderer(TreeTableModel.class);
        tree.setFont(new Font("DialogInput", Font.PLAIN, 12));
        treeTable.setFont(tree.getFont());
        tree.setShowsRootHandles(true);
        tree.setCellRenderer(new XMLTreeTableCellRenderer());
        int row = 0;
        while(row<tree.getRowCount())
            tree.expandRow(row++);
        return treeTable;
    }

    private void updateText() throws Exception{
        MyTreeTable treeTable = (MyTreeTable)scroll.getViewport().getView();
        TreeTableModel treeTableModel = ((TreeTableModelAdapter)treeTable.getModel()).treeTableModel;
        Node node = (Node)((XMLTreeTableModel)treeTableModel).getRoot();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(node), new StreamResult(writer));
        editor.setText(writer.toString());
    }

    private void updateTree() throws Exception{
        InputSource is = new InputSource(new StringReader(editor.getText()));
        MyTreeTable treeTable = createTreeTable(new XMLTreeTableModel(is, true));
        scroll.setViewportView(treeTable);
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JFrame frame = new XMLTreeTableDemo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}