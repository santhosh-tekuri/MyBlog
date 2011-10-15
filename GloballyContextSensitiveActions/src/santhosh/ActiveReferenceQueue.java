package santhosh;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.Reference;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ActiveReferenceQueue extends ReferenceQueue implements Runnable{
    private static ActiveReferenceQueue singleton = null;

    public static ActiveReferenceQueue getInstance(){
        if(singleton==null)
            singleton = new ActiveReferenceQueue();
        return singleton;
    }

    private ActiveReferenceQueue(){
        Thread t = new Thread(this, "ActiveReferenceQueue");
        t.setDaemon(false);
        t.start();
    }

    public void run(){
        for(;;){
            try{
                Reference ref = super.remove(0);
                if(ref instanceof Runnable){
                    try{
                        ((Runnable)ref).run();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
