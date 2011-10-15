package santhosh;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class DebugProxy implements InvocationHandler{
    private Object obj;

    public static Class[] getAllInterfaces(Class clazz){
        ArrayList list = new ArrayList();
        while(clazz!=null){
            list.addAll(Arrays.asList(clazz.getInterfaces()));
            clazz = clazz.getSuperclass();
        }
        return (Class[])list.toArray(new Class[0]);
    }

    public static Object newInstance(Object obj, StringConvertor stringConvertor) {
        return java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                getAllInterfaces(obj.getClass()),
                new DebugProxy(obj, stringConvertor)
        );
    }

    private StringConvertor stringConvertor;

    private DebugProxy(Object obj, StringConvertor stringConvertor) {
        this.obj = obj;
        this.stringConvertor = stringConvertor;
    }

    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable{
        Object result;
        try {
            System.out.print(m.getName()+"(");
            if(args!=null){
                for(int i = 0; i<args.length; i++){
                    if(i>0)
                        System.out.print(", ");
                    System.out.print(toString(args[i]));
                }
            }
            System.out.print(")");
            result = m.invoke(obj, args);
            if(m.getReturnType()!=Void.class)
                System.out.println(" returned "+toString(result));
            else
                System.out.println();
            System.out.println("----------------------------------------------------------");
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } finally {
            System.out.flush();
        }
        return result;
    }

    public String toString(Object obj){
        if(obj!=null && obj.getClass().isArray() && !obj.getClass().getComponentType().isPrimitive())
            return Arrays.asList((Object[])obj).toString();
        else if(obj!=null && stringConvertor!=null)
            return stringConvertor.toString(obj);
        else
            return String.valueOf(obj);
    }
}