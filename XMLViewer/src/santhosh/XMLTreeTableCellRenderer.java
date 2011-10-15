package santhosh;

import org.w3c.dom.Node;
import org.w3c.dom.DocumentType;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class XMLTreeTableCellRenderer extends DefaultTreeCellRenderer{
    Color elementColor = new Color(0, 0, 128);
    Color attributeColor = new Color(0, 128, 0);

    public XMLTreeTableCellRenderer(){
        setOpenIcon(null);
        setClosedIcon(null);
        setLeafIcon(null);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus){
        Node node = (Node)value;
        switch(node.getNodeType()){
            case Node.ELEMENT_NODE:
                value = '<'+node.getNodeName()+'>';
                break;
            case Node.ATTRIBUTE_NODE:
                value = '@'+node.getNodeName();
                break;
            case Node.TEXT_NODE:
                value = "# text";
                break;
            case Node.COMMENT_NODE:
                value = "# comment";
                break;
            case Node.DOCUMENT_TYPE_NODE:
                DocumentType dtype = (DocumentType)node;
                value = "# doctype";
                break;
            default:
                value = node.getNodeName();
        }
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if(!selected){
            switch(node.getNodeType()){
                case Node.ELEMENT_NODE:
                    setForeground(elementColor);
                    break;
                case Node.ATTRIBUTE_NODE:
                    setForeground(attributeColor);
                    break;
            }
        }
        return this;
    }
}
