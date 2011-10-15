package santhosh;

import java.util.*;

/**
 * Comparator that can compare classes.
 * A Class is greater than its superClasses.
 *
 * Note: this comparator must be used only
 * with insertion sort
 *
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 */
public final class ClassComparator implements Comparator{
    private int subclassLess, interfaceLess;

    /**
     * Creates a new Class Comparator with given options
     * @param subclassLess    true -> subclass < superclass
     * @param interfaceLess   true -> interface < class
     */
    public ClassComparator(boolean subclassLess, boolean interfaceLess){
        this.subclassLess = subclassLess ? +1 : -1;
        this.interfaceLess = interfaceLess ? +1 : -1;
    }

    public int compare(Object o1, Object o2){
        if(o1==o2)
            return 0;

        Class class1 = (Class)o1;
        Class class2 = (Class)o2;

        boolean interface1 = class1.isInterface();
        boolean interface2 = class2.isInterface();

        if(interface1 ^ interface2)   // one of them is interface
            return (interface1 ? -1 : +1 ) * interfaceLess;
        else{ // both of them are either interfaces or classes
            boolean isSuperClass = class1.isAssignableFrom(class2);
            boolean isSubClass = class2.isAssignableFrom(class1);
            if(isSuperClass || isSubClass)
                return (isSubClass ? -1 : +1) * subclassLess;
            else
                return subclassLess; // trick
        }
    }
}