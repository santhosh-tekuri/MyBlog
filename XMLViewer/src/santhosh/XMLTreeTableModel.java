package santhosh;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.util.Vector;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class XMLTreeTableModel implements TreeTableModel{
    private Node node;

    public XMLTreeTableModel(InputSource is, boolean trim) throws Exception{
        this(DOMUtil.createDocument(is, trim).getDocumentElement());
    }

    public XMLTreeTableModel(Node node){
        this.node = node;
    }

    public Object getRoot(){
        return node;
    }

    public Object getChild(Object parent, int index){
        Node node = (Node)parent;
        NamedNodeMap attrs = node.getAttributes();
        int attrCount = attrs!=null ? attrs.getLength() : 0;
        if(index<attrCount)
            return attrs.item(index);
        NodeList children = node.getChildNodes();
        return children.item(index - attrCount);
    }

    public int getChildCount(Object parent){
        Node node = (Node)parent;
        NamedNodeMap attrs = node.getAttributes();
        int attrCount = attrs!=null ? attrs.getLength() : 0;
        NodeList children = node.getChildNodes();
        int childCount = children!=null ? children.getLength() : 0;
        if(childCount==1 && children.item(0).getNodeType()==Node.TEXT_NODE)
            return attrCount;
        else
            return attrCount + childCount;
    }

    public boolean isLeaf(Object node){
        return getChildCount(node)==0;
    }

    public int getIndexOfChild(Object parent, Object child){
        Node node = (Node)parent;
        Node childNode = (Node)child;

        NamedNodeMap attrs = node.getAttributes();
        int attrCount = attrs!=null ? attrs.getLength() : 0;
        if(childNode.getNodeType()==Node.ATTRIBUTE_NODE){
            for(int i=0; i<attrCount; i++){
                if(attrs.item(i)==child)
                    return i;
            }
        }else{
            NodeList children = node.getChildNodes();
            int childCount = children!=null ? children.getLength() : 0;
            for(int i=0; i<childCount; i++){
                if(children.item(i)==child)
                    return attrCount + i;
            }
        }
        throw new RuntimeException("this should never happen!");
    }

    public Vector listeners = new Vector();
    public void addTreeModelListener(TreeModelListener listener){
        // not editable
    }

    public void removeTreeModelListener(TreeModelListener listener){
        // not editable
    }

    public void valueForPathChanged(TreePath path, Object newValue){
        // not editable
    }

    public int getColumnCount(){
        return 2;
    }

    public String getColumnName(int column){
        return column==0 ? "Node" : "Value";
    }

    public Class getColumnClass(int column){
        return column==0 ? TreeTableModel.class : String.class;
    }

    private Node getColumnNode(Node n){
        if(n.getNodeType()==Node.ELEMENT_NODE){
            NodeList children = n.getChildNodes();
            int childCount = children!=null ? children.getLength() : 0;
            if(childCount==1 && children.item(0).getNodeType()==Node.TEXT_NODE)
                return children.item(0);
        }
        return n;
    }

    public Object getValueAt(Object node, int column){
        if(column==0)
            return node;
        else{
            Node n = (Node)node;
            if(n.getNodeType()==Node.ELEMENT_NODE){
                NodeList children = n.getChildNodes();
                int childCount = children!=null ? children.getLength() : 0;
                if(childCount==1 && children.item(0).getNodeType()==Node.TEXT_NODE)
                    return children.item(0).getNodeValue();
            }
            return n.getNodeValue();
        }
    }

    public boolean isCellEditable(Object node, int column){
        return true;
    }

    public void setValueAt(Object value, Object node, int column){
        if(column==1){
            Node n = getColumnNode((Node)node);
            n.setNodeValue((String)value);
        }
    }
}