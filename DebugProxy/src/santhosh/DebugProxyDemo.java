package santhosh;

import javax.swing.*;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentAdapter;

public class DebugProxyDemo{
    public static void main(String[] args) throws Exception{
        JTree tree = createTree();
        tree.addComponentListener((ComponentListener)DebugProxy.newInstance(new ComponentAdapter(){}, null));
    }


    private static JTree createTree(){
        return null;
    }
}
