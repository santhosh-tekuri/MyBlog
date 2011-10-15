package santhosh;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.dom.DOMResult;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class DOMUtil{
    public static Document createDocument(InputSource is, boolean trim) throws Exception{
        return trim ? createTrimmedDocument(is) : createDocument(is);
    }

    public static Document createTrimmedDocument(InputSource is) throws Exception{
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        saxFactory.setNamespaceAware(true);
        saxFactory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        SAXParser parser = saxFactory.newSAXParser();
        XMLReader reader = new XMLTrimFilter(parser.getXMLReader());

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        DOMResult result = new DOMResult();
        transformer.transform(new SAXSource(reader, is), result);
        return (Document)result.getNode();
    }

    public static Document createDocument(InputSource is) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setCoalescing(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(is);
    }
}
