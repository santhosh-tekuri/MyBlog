package santhosh;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

class ChildrenEnumeration implements Enumeration{
    TreeModel treeModel;
    Object node;
    int index = -1;
    int depth;

    public ChildrenEnumeration(TreeModel treeModel, Object node){
        this.treeModel = treeModel;
        this.node = node;
    }

    public boolean hasMoreElements(){
        return index<treeModel.getChildCount(node)-1;
    }

    public Object nextElement(){
        return treeModel.getChild(node, ++index);
    }
}

class PreorderEnumeration implements Enumeration{
    private TreeModel treeModel;
    protected Stack stack;
    private int depth = 0;

    public PreorderEnumeration(TreeModel treeModel){
        this.treeModel = treeModel;
        Vector v = new Vector(1);
        v.addElement(treeModel.getRoot());
        stack = new Stack();
        stack.push(v.elements());
    }

    public boolean hasMoreElements(){
        return (!stack.empty() &&
                ((Enumeration)stack.peek()).hasMoreElements());
    }

    public Object nextElement(){
        Enumeration enumer = (Enumeration)stack.peek();
        Object node = enumer.nextElement();
        depth = enumer instanceof ChildrenEnumeration
                ? ((ChildrenEnumeration)enumer).depth
                : 0;
        if(!enumer.hasMoreElements())
            stack.pop();
        ChildrenEnumeration children = new ChildrenEnumeration(treeModel, node);
        children.depth = depth+1;
        if(children.hasMoreElements()){
            stack.push(children);
        }
        return node;
    }

    public int getDepth(){
        return depth;
    }
}

// assumes that tree structure is not changed for simplicity
class TreeListModel extends AbstractListModel implements ComboBoxModel{
    private TreeModel treeModel;
    private Object selectedObject;

    public TreeListModel(TreeModel treeModel){
        this.treeModel = treeModel;
    }

    public int getSize(){
        int count = 0;
        Enumeration enumer = new PreorderEnumeration(treeModel);
        while(enumer.hasMoreElements()){
            enumer.nextElement();
            count++;
        }
        return count;
    }

    public Object getElementAt(int index){
        Enumeration enumer = new PreorderEnumeration(treeModel);
        for(int i=0; i<index; i++)
            enumer.nextElement();
        return enumer.nextElement();
    }

    public void setSelectedItem(Object anObject){
        if((selectedObject!=null && !selectedObject.equals(anObject)) ||
                selectedObject==null && anObject!=null){
            selectedObject = anObject;
            fireContentsChanged(this, -1, -1);
        }
    }

    public Object getSelectedItem() {
        return selectedObject;
    }
}

class IndentBorder extends EmptyBorder{
    int indent = UIManager.getInt("Tree.leftChildIndent");

    public IndentBorder(){
        super(0, 0, 0, 0);
    }

    public void setDepth(int depth){
        left = indent*depth;
    }
}

public class TreeListCellRenderer extends JPanel implements ListCellRenderer{
    private static final JTree tree = new JTree();
    TreeModel treeModel;
    TreeCellRenderer treeRenderer;
    IndentBorder indentBorder = new IndentBorder();

    public TreeListCellRenderer(TreeModel treeModel, TreeCellRenderer treeRenderer){
        this.treeModel = treeModel;
        this.treeRenderer = treeRenderer;
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(indentBorder);
        setOpaque(false);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        if(value==null){ //if selected value is null
            removeAll();
            return this;
        }

        boolean leaf = treeModel.isLeaf(value);
        Component comp = treeRenderer.getTreeCellRendererComponent(tree, value, isSelected, true, leaf, index, cellHasFocus);
        removeAll();
        add(comp);

        // compute the depth of value
        PreorderEnumeration enumer = new PreorderEnumeration(treeModel);
        for(int i = 0; i<=index; i++)
            enumer.nextElement();
        indentBorder.setDepth(enumer.getDepth());

        return this;
    }


    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Tree inside JComboBox - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTree tree = new JTree();

        int row = 0;
        while(row<tree.getRowCount())
            tree.expandRow(row++);

        JComboBox comboBox = new JComboBox(new TreeListModel(tree.getModel()));
        UIManager.put("Tree.textBackground", UIManager.getColor("ComboBox.background"));
        comboBox.setRenderer(new TreeListCellRenderer(tree.getModel(), new DefaultTreeCellRenderer()));
        frame.getContentPane().add(comboBox, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.show();
    }
}