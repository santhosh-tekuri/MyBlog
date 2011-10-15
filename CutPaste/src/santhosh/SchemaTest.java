package santhosh;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.xs.XSImplementationImpl;
import org.apache.xerces.xs.*;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.LSResourceResolver;

public class SchemaTest{
    public static final String DOM_IMPLEMENTATION_REGISTRY = "org.apache.xerces.dom.DOMXSImplementationSourceImpl";
    public static final String DOM_IMPLEMENTATION = "XS-Loader";

    public static XSModel readXSD(String uri) throws IllegalAccessException, InstantiationException, ClassNotFoundException{
        return readXSD(uri, null, null);
    }

    public static XSModel readXSD(String uri, LSResourceResolver entityResolver, DOMErrorHandler errorHandler)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException{
        XSLoader xsLoader = createXSLoader(entityResolver, errorHandler);
        return xsLoader.loadURI(uri);
    }

    public static XSLoader createXSLoader(LSResourceResolver entityResolver, DOMErrorHandler errorHandler) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.setProperty(DOMImplementationRegistry.PROPERTY, DOM_IMPLEMENTATION_REGISTRY);
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        XSImplementationImpl xsImpl = (XSImplementationImpl)registry.getDOMImplementation(DOM_IMPLEMENTATION);

        XSLoader xsLoader = xsImpl.createXSLoader(null);
        DOMConfiguration config = xsLoader.getConfig();
        config.setParameter(Constants.DOM_VALIDATE, Boolean.TRUE);

        if(entityResolver!=null)
            config.setParameter(Constants.DOM_RESOURCE_RESOLVER, entityResolver);

        if(errorHandler!=null)
            config.setParameter(Constants.DOM_ERROR_HANDLER, errorHandler);
        return xsLoader;
    }

    public static String[] getParts(String clarkName){
        String namespace, localName;

        if(clarkName.charAt(0) == '{'){
            int closeBrace = clarkName.indexOf('}');
            if(closeBrace < 0)
                throw new IllegalArgumentException("No closing '}' in Clark name");
            namespace = clarkName.substring(1, closeBrace);
            if(closeBrace == clarkName.length())
                throw new IllegalArgumentException("Missing local part in Clark name");
            localName = clarkName.substring(closeBrace + 1);
        } else{
            namespace = "";      //NOI18N
            localName = clarkName;
        }

        return new String[]{namespace, localName};
    }

    public static XSTypeDefinition getTypeUsingApproach1(XSModel model, String clarkName){
        String parts[] = getParts(clarkName);
        return model.getTypeDefinition(parts[1], parts[0]);
    }

    public static XSTypeDefinition getTypeUsingApproach2(XSModel model, String clarkName){
        String parts[] = getParts(clarkName);
        XSNamedMap map = model.getComponentsByNamespace(XSConstants.TYPE_DEFINITION, parts[0]);
        return (XSTypeDefinition)map.itemByName(parts[0], parts[1]);
    }

    public static XSTypeDefinition getTypeUsingApproach2Corrected(XSModel model, String clarkName){
        String parts[] = getParts(clarkName);
        parts[0] = parts[0].intern();
        parts[1] = parts[1].intern();
        XSNamedMap map = model.getComponentsByNamespace(XSConstants.TYPE_DEFINITION, parts[0]);
        return (XSTypeDefinition)map.itemByName(parts[0], parts[1]);
    }

    public static void runTest0(String uri) throws Exception{
        XSModel model = readXSD(uri);
        String clarkName = "{http://www.w3.org/2001/XMLSchema}string";
        if(getTypeUsingApproach1(model, clarkName)==null)
            System.err.println("test0 failed");
    }

    public static void runTest1(String uri) throws Exception{
        XSModel model = readXSD(uri);
        String clarkName = "{http://www.w3.org/2001/XMLSchema}string";
        if(getTypeUsingApproach2(model, clarkName)==null)
            System.err.println("test1 failed");
    }

    public static void runTest2(String uri) throws Exception{
        XSModel model = readXSD(uri);
        String clarkName = "{http://www.w3.org/2001/XMLSchema}string";
        if(getTypeUsingApproach2Corrected(model, clarkName)==null)
            System.err.println("test2 failed");
    }

    public static void runTest3(String uri) throws Exception{
        XSModel model = readXSD(uri);
        String clarkName = "{http://www.w3.org/2001/XMLSchema}string";
        if(getTypeUsingApproach2(model, clarkName)==null)
            System.err.println("test3a failed");
        if(getTypeUsingApproach2Corrected(model, clarkName)==null)
            System.err.println("test3b failed");
    }

    public static void runTest4(String uri) throws Exception{
        XSModel model = readXSD(uri);
        String clarkName = "{http://www.w3.org/2001/XMLSchema}string";
        if(getTypeUsingApproach2Corrected(model, clarkName)==null)
            System.err.println("test4a failed");
        if(getTypeUsingApproach2(model, clarkName)==null)
            System.err.println("test4b failed");
    }

    public static void main(String[] args) throws Exception{
        if(args.length==0){
            System.err.println("you must pass a schema file as first argument.");
            return;
        }
        runTest0(args[0]);
        runTest1(args[0]);
        runTest2(args[0]);
        runTest3(args[0]);
    }
}