package santhosh;

import java.util.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class AllInOneVisitor implements Visitor{
    protected Map map = new HashMap();
    protected ArrayList classes = new ArrayList();
    protected Comparator cmp = null;

    public AllInOneVisitor(Comparator classComparator){
        this.cmp = classComparator;
    }

    public AllInOneVisitor(){
        cmp = new ClassComparator(true, false);
    }

    public void addVisitor(Class clazz, Visitor visitor){
        if(map.put(clazz, visitor)!=null)
            return;
        for(int i=0; i<classes.size(); i++){
            int result = cmp.compare(clazz, classes.get(i));
            if(result==0)
                return;
            if(result<0){
                classes.add(i, clazz);
                return;
            }
        }
        classes.add(clazz);
    }

    public void visit(Object obj){
        Iterator iter = classes.iterator();
        while(iter.hasNext()){
            Class clazz = (Class)iter.next();
            if(clazz.isInstance(obj))
                ((Visitor)map.get(clazz)).visit(obj);
        }
        throw new IllegalArgumentException();
    }

//    public static void main(String[] args){
//        AllInOneVisitor visitor = new AllInOneVisitor();
//        visitor.addVisitor(A.class, new AVisitor());
//        visitor.addVisitor(B.class, new BVisitor());
//        visitor.addVisitor(C.class, new CVisitor());
//        visitor.addVisitor(D.class, new DVisitor());
//        visitor.addVisitor(E.class, new EVisitor());
//
//        visitor.visit(new C());
//    }
}