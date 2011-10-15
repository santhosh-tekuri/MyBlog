package santhosh;

class A{
    public void print(){
        System.err.println("A");
    }

    public A get(){
        return this;
    }
}

class B extends A{
    public void print(){
        System.err.println("B");
    }

    public void super_print(){
        super.print();
    }
}

public class Test{
    public static void main(String[] args){
        B b = new B();
        b.print();
    }
}
