package santhosh;

//import org.xml.sax.ext.Attributes2Impl;

import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

public class NSTest{
    public static void main(String[] args) throws Exception{
        SAXTransformerFactory factory = (SAXTransformerFactory)TransformerFactory.newInstance();
        System.err.println("factory:"+factory);

        TransformerHandler transformer = factory.newTransformerHandler();
        transformer.setResult(new StreamResult(System.err));

        transformer.startDocument();
//        transformer.startElement("", "test", "test", new Attributes2Impl());
        transformer.processingInstruction(Result.PI_DISABLE_OUTPUT_ESCAPING, "");
        String str = "<testing>don't escape me</testing>";
        transformer.characters(str.toCharArray(), 0, str.length());
        transformer.processingInstruction(Result.PI_ENABLE_OUTPUT_ESCAPING, "");
        transformer.endElement("", "test", "test");
        transformer.endDocument();
    }
}