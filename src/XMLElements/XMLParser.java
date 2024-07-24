package XMLElements;

import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.FileReader;

public class XMLParser {
    public Scene parseXMLFile(String path){
        Scene scene = null;
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(Scene.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Start Citation:
            // https://stackoverflow.com/questions/9909465/how-to-disable-dtd-fetching-using-jaxb2-0
            // To fix error caused by DTD fetching when using JAXB
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            XMLReader xmlReader = spf.newSAXParser().getXMLReader();
            InputSource inputSource = new InputSource(new FileReader(file));
            // End Citation

            scene = (Scene) jaxbUnmarshaller.unmarshal(new SAXSource(xmlReader, inputSource));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scene;
    }
}
