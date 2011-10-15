package santhosh;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeModelListener;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

public class FileSystemTreeModel implements TreeModel{
    FileSystemView fs = FileSystemView.getFileSystemView();

    Map cache = new WeakHashMap();

    public Object getRoot(){
        return fs;
    }

    private File[] getChildren(Object parent){
        File files[] = (File[])cache.get(parent);
        if(files==null){
            files = parent==fs
                    ? fs.getRoots()
                    : fs.getFiles((File)parent, true);
            cache.put(parent, files);
        }
        return files;
    }

    public int getChildCount(Object parent){
        return getChildren(parent).length;
    }

    public boolean isLeaf(Object node){
        return node!=fs && ((File)node).isFile();
    }


    public Object getChild(Object parent, int index){
        return getChildren(parent)[index];
    }

    public int getIndexOfChild(Object parent, Object child){
        File files[] = getChildren(parent);
        for(int i = 0; i<files.length; i++){
            if(files[i]==child)
                return i;
        }
        return -1;
    }


    public void addTreeModelListener(TreeModelListener l){
        //@todo
    }

    public void removeTreeModelListener(TreeModelListener l){
        //@todo
    }

    public void valueForPathChanged(TreePath path, Object newValue){
        //@todo
    }
}