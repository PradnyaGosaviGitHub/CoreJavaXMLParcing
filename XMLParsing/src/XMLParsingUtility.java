import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParsingUtility {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("abc.xml");
		Element rootNode = doc.getDocumentElement();
		System.out.println(rootNode.getNodeName());
		NodeList lineItems = rootNode.getElementsByTagName("LineItem");
		for(int i=0;i<lineItems.getLength();i++) {
			Element lineItem = (Element) lineItems.item(i);
			NamedNodeMap attributeMap = lineItem.getAttributes();
			String key = attributeMap.item(0).getNodeName();
			String value = attributeMap.item(0).getNodeValue();
			System.out.println(key+" : "+value);
			key = attributeMap.item(1).getNodeName();
			value = attributeMap.item(1).getNodeValue();
			System.out.println(key + " : "+value);
		}
	}
}
