package santhosh;

import javax.swing.tree.TreeModel;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

public class PreorderEnumeration implements Enumeration{
    private TreeModel treeModel;
    protected Stack stack;
    private int depth = 0;

    public PreorderEnumeration(TreeModel treeModel){
        this(treeModel, new Object[]{ treeModel.getRoot() });
    }

    public PreorderEnumeration(TreeModel treeModel, Object elements[]){
        this.treeModel = treeModel;
        Vector v = new Vector(elements.length);
        for(Object element: elements)
            v.addElement(element);
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

