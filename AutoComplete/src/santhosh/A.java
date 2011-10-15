package santhosh;

import java.util.*;


class A{}
class B extends A{}
class C extends B{}
class D extends B{}
class E extends A{}

interface Visitor{
    void visit(Object obj);
}
/*
class MyVisitor1 implements Visitor{

    public void visit(Object obj){
        if(obj instanceof E)
            visit((E)obj);
        else if(obj instanceof C)
            visit((C)obj);
        else if(obj instanceof D)
            visit((D)obj);
        else if(obj instanceof B)
            visit((B)obj);
        else if(obj instanceof A)
            visit((A)obj);
        else
            throw new IllegalArgumentException();
    }

    private void visit(A a){
        ...
    }

    private void visit(B b){
        ...
    }

    private void visit(C c){
        ...
    }

    private void visit(D d){
        ....
    }

    private void visit(E e){
        ...
    }
}

class MyVisitor2 implements Visitor{

    public void visit(Object obj){
        if(obj instanceof A)
            visit((A)obj);
        else
            throw new IllegalArgumentException();
    }

    private void visit(A a){
        if(a instanceof B)
            visit((B)a);
        else if(a instanceof E)
            visit((E)a);
        else{
            ....
        }
    }

    private void visit(B b){
        if(b instanceof C)
            visit((C)b);
        else if(b instanceof D)
            visit((D)b);
        else{
            .....
        }
    }

    private void visit(C c){
        ...
    }

    private void visit(D d){
        ....
    }

    private void visit(E e){
        .....
    }
}

class AVisitor implements Visitor{...}
class BVisitor implements Visitor{...}
class CVisitor implements Visitor{...}
class DVisitor implements Visitor{...}
class EVisitor implements Visitor{...}

class MyVisitor3 implements Visitor{
    private static Map visitors = new HashMap();

    static{
        visitors.put(A.class, new AVisitor());
        visitors.put(B.class, new BVisitor());
        visitors.put(C.class, new CVisitor());
        visitors.put(D.class, new DVisitor());
        visitors.put(E.class, new EVisitor());
    }

    public void _visit(Object obj){
        if(obj instanceof E)
            ((Visitor)visitors.get(E.class)).visit(obj);
        else if(obj instanceof C)
            ((Visitor)visitors.get(C.class)).visit(obj);
        else if(obj instanceof D)
            ((Visitor)visitors.get(D.class)).visit(obj);
        else if(obj instanceof B)
            ((Visitor)visitors.get(B.class)).visit(obj);
        else if(obj instanceof A)
            ((Visitor)visitors.get(A.class)).visit(obj);
        else
            throw new IllegalArgumentException();
    }

    private static Class classes[] = {
        E.class,
        C.class,
        D.class,
        B.class,
        A.class
    };

    public void visit(Object obj){
        for(int i=0; i<classes.length; i++){
            if(classes[i].isInstance(obj))
                ((Visitor)visitors.get(classes[i])).visit(obj);
        }
    }
}
*/
