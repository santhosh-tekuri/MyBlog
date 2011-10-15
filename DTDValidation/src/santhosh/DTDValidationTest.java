package santhosh;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;

public class DTDValidationTest{
    public static void main(String[] args) throws Exception{
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser parser = factory.newSAXParser();
//        parser.getXMLReader().setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
//        parser.getXMLReader().setFeature("http://xml.org/sax/features/external-general-entities", true);
//        parser.getXMLReader().setFeature("http://xml.org/sax/features/external-parameter-entities", true);
        parser.getXMLReader().setEntityResolver(new EntityResolver(){
            public InputSource resolveEntity(String publicId, String systemId){
                return new InputSource(new ByteArrayInputStream(new byte[0]));
            }
        });
        parser.getXMLReader().parse("d:/MyBlog/DTDValidation/note.xml");
    }
}
