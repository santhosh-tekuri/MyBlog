package santhosh;

import java.awt.datatransfer.DataFlavor;

public class ObjectFlavor extends DataFlavor{
    Class representationClass;

    public ObjectFlavor(Class representationClass){
        super(Object.class, "Object"); //NOI18N
        this.representationClass = representationClass;
    }

    /**
     * return true if any object represented by this flavor is assinable to
     * the class represented by given flavor <br>
     *
     * Note: <br>
     *
     * here we are violating the javadoc of Object.equals(...).
     * javadoc says obj1.equals(obj2) == obj2.equals(obj1) should hold
     * in this implementation it is no longer hold. <br>
     *
     * if you don't want to violate the javadoc, rename this method
     * to <code>match</code> so that it overrides DataFlavor.match(...) and use
     * match(...) insteadof equals(...) in ObjectTransferable ,
     * MultiTransferable and in your TransferHandler subclass
     */
    public boolean equals(DataFlavor that){
        if(that instanceof ObjectFlavor){
            return ((ObjectFlavor)that).representationClass.isAssignableFrom(representationClass);
        }else
            return false;
    }
}
