package santhosh.solution;

import santhosh.solution.ObjectFlavor;
import santhosh.Person;
import santhosh.Employee;
import santhosh.Manager;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ObjectTransferable implements Transferable {
    private Object obj;
    private DataFlavor flavor;

    public ObjectTransferable (Object obj) {
        this.obj = obj;
        flavor = new ObjectFlavor(obj.getClass());
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { flavor };
    }

    public boolean isDataFlavorSupported (DataFlavor flavor) {
        if(this.flavor.equals(flavor))
            return true;
        return false;
    }

    public Object getTransferData (DataFlavor flavor) throws UnsupportedFlavorException{
        if(isDataFlavorSupported(flavor))
            return obj;
        throw new UnsupportedFlavorException (flavor);
    }

    // testcase to clear concepts
    public static void main(String[] args){
        ObjectTransferable employeeTranferable = new ObjectTransferable(new Employee("santhosh"));
        ObjectFlavor personFlavor = new ObjectFlavor(Person.class);
        ObjectFlavor managerFlavor = new ObjectFlavor(Manager.class);

        System.err.println(employeeTranferable.isDataFlavorSupported(personFlavor)); // Employee is Person ? --> true
        System.err.println(employeeTranferable.isDataFlavorSupported(managerFlavor)); // Employee is Manager ? ---> false
    }
}