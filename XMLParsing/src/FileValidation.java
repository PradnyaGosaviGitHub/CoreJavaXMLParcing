import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class FileValidation {
    static  DocumentBuilderFactory factory;
    static DocumentBuilder builder;
    static Document doc;
    static Element rootNode;
    static Set fileSet = new HashSet();


    public FileValidation(String fileName)  {
    factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            doc = builder.parse(fileName);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootNode = doc.getDocumentElement();
//        NodeList lineItems = rootNode.getElementsByTagName("LineItem");
    }
	
	boolean isDuplicate(File file) {
        boolean isDuplicate = false;
        String fileName = file.getName();
		if (!fileSet.contains(fileName)){
		    fileSet.add(fileName);
		    isDuplicate = true;
            System.out.println(fileSet);
            return isDuplicate;
        }
        else {
            System.out.println("File is already present");
            isDuplicate = false;
        }
        return isDuplicate;
	}

	
	boolean isInTime(File file) {
		// TODO Auto-generated method stub
        boolean isInTime = false;
        NodeList lineItems = rootNode.getElementsByTagName("fileInfo");
        for(int i=0;i<lineItems.getLength();i++) {
            Element lineItem = (Element) lineItems.item(i);
            NamedNodeMap attributeMap = lineItem.getAttributes();
            String key = attributeMap.item(1).getNodeName();
            String value = attributeMap.item(1).getNodeValue();
            String[] input = value.split(":");
            //todo XML file time
            long hrFile = Integer.parseInt(input[0]);
            long mmFile = Integer.parseInt(input[1]);
            long ssFile = Integer.parseInt(input[2]);
            //todo XML file time converted into miliseconds
            long fileMiliSec = (hrFile*60*60+mmFile*60+ssFile)*1000;
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String time = formatter.format(date);
            String[] timeArray = time.split(":");
            long hhCurrent = Long.parseLong(timeArray[0]);
            long mmCurrent = Long.parseLong(timeArray[1]);
            long ssCurrent = Long.parseLong(timeArray[2]);
            long currentTimeInMiliSec = (hhCurrent*60*60+mmCurrent*60+ssCurrent)*1000;
            long currentMiliSec = System.currentTimeMillis();//predefined function to get time in miliseconds
            //todo If current time is less than file time then file is in time
            if(currentTimeInMiliSec <= fileMiliSec ){
                System.out.println("File is in Time");
                isInTime = true;
                return isInTime;
            }else {
                System.out.println("Invalid Time!!!!");
                isInTime = false;
                return isInTime;}
        }
        return isInTime;
	}

	boolean isValid(File file) {
		// TODO Auto-generated method stub
        boolean isValid = false;
        NodeList lineItems = rootNode.getElementsByTagName("fileInfo");
        for(int i=0;i<lineItems.getLength();i++) {
            Element lineItem = (Element) lineItems.item(i);
            NamedNodeMap attributeMap = lineItem.getAttributes();
            String key = attributeMap.item(0).getNodeName();
            String value = attributeMap.item(0).getNodeValue();
            System.out.println(key+" : "+value);
                String fn = file.getName();
            if(fn.equals(value)){
//                System.out.println(file+" : "+value);
                isValid = true;
                System.out.println(isValid);
                return isValid;
            }
            else {
                isValid = false;
            }
        }

		return isValid;
	}
	
}
