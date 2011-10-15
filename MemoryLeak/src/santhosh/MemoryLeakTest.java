package santhosh;

import org.openide.nodes.Node;
import org.openide.util.WeakListeners;

public class MemoryLeakTest{
    public void gc() throws Exception{
        for(int i=0; i<10; i++){
            System.gc();
//            Thread.sleep(1000);
        }
    }

    public void testNodeCreation() throws Exception{
        User user = new User();
        for(int i=0; i<100; i++)
            new UserNode(user);
        gc();
        user.printListenerCount();
    }

    public void testExplorer() throws Exception{
        User user = new User();
        for(int i=0; i<100; i++){
            Explorer explorer = new Explorer();
            Node node = new UserNode(user);
            explorer.getExplorerManager().setRootContext(node);
            explorer.getExplorerManager().setExploredContextAndSelection(node, new Node[]{ node });
        }
        gc();
        user.printListenerCount();
    }

    public static void main(String[] args) throws Exception{
//        new Thread(){
//            public void run(){
//                while(true){
//                    System.err.println("GC");
//                    System.gc();
//                }
//            }
//        }.start();
        MemoryLeakTest test = new MemoryLeakTest();
//        test.testNodeCreation();
        test.testExplorer();
        System.err.println("done1");
    }
}
