package santhosh;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @author Santhsoh Kumar T
 */
public class TreePathUtil{
    public static String serialize(TreePath treePath){
        Object path[] = treePath.getPath();
        StringBuffer buff = new StringBuffer();
        for(int i=0; i<path.length; i++){
            if(i>0)
                buff.append("/");
            buff.append(path[i].toString());
        }
        return buff.toString();
    }

    public static TreePath deserialize(TreeModel model, String str){
        StringTokenizer stok = new StringTokenizer(str, "/");
        Stack path = new Stack();
        path.push(model.getRoot());
        stok.nextToken();
        while(stok.hasMoreTokens()){
            String token = stok.nextToken();
            Object node = path.peek();
            int count = model.getChildCount(node);
            for(int i=0; i<count; i++){
                Object child = model.getChild(node, i);
                if(token.equals(child.toString())){
                    path.push(child);
                    break;
                }
            }
        }
        return new TreePath(path.toArray());
    }

    public static String serialize(TreeSelectionModel selectionModel){
        TreePath path[] = selectionModel.getSelectionPaths();
        StringBuffer buff = new StringBuffer();
        for(int i=0; i<path.length; i++){
            if(i!=0)
                buff.append(",");
            buff.append(serialize(path[i]));
        }
        return buff.toString();
    }

    public static void deserialize(TreeModel model, TreeSelectionModel selectionModel, String str){
        selectionModel.clearSelection();
        StringTokenizer stok = new StringTokenizer(str, ",");
        while(stok.hasMoreTokens())
            selectionModel.addSelectionPath(deserialize(model, stok.nextToken()));
    }
}
