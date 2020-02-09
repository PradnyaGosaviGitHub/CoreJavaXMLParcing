import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyFileProcessorThread extends Thread{
	
	File file;
	
	MyFileProcessorThread(File file){
		this.file = file;
	}

	@Override

	public void run() {
//        while(true){
            // TODO Auto-generated method stub

            try {
                System.out.println("In MyFile Processor......");
                fileParsing();
                Thread.sleep(10000);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
	}


	void fileParsing() throws ParserConfigurationException, SAXException, IOException {

        FileValidation f = new FileValidation("dependencies.xml");
		NodeList lineItems = f.rootNode.getElementsByTagName("field");
        String nameValue=null, orderValue=null,typeValue = null;
        String  maxLengthValue = null;
		for(int i=0;i<lineItems.getLength();i++) {
            Element lineItem = (Element) lineItems.item(i);
            NamedNodeMap attributeMap = lineItem.getAttributes();
            nameValue = attributeMap.item(1).getNodeValue();
            orderValue = attributeMap.item(2).getNodeValue();
            maxLengthValue = attributeMap.item(0).getNodeValue();
        }
//		System.out.println("name value : "+nameValue);
//		System.out.println("orderValue : "+orderValue);
//		System.out.println("maxLengthValue : "+maxLengthValue);
            NodeList lineSeparator = f.rootNode.getElementsByTagName("format1");
            Element lineSeperatorElement = (Element) lineSeparator.item(0);
            NamedNodeMap lineAttributeMap = lineSeperatorElement.getAttributes();
            String sep = lineAttributeMap.item(0).getNodeValue();
            
            int limit = Integer.parseInt(maxLengthValue);
            System.out.println("Limit : "+maxLengthValue );
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    FileWriter  invalidTextWriteFile=null,validTextWriteFile=null;
                    FileReader validfileReader = null;
                    ConnectionPool connPool = new ConnectionPool(file);
                    File[] files = new File("InvalidTxt").listFiles();
                    for(File Invalidfile:files) {
                	invalidTextWriteFile = new FileWriter(Invalidfile);
                    }
                	File[] filesValid = new File("validTxt").listFiles();
                    for(File validFile:filesValid) {
                		System.out.println(validFile);
                	validTextWriteFile = new FileWriter(validFile);
                	validfileReader = new FileReader(validFile);
                    }
                                
                    String name=null,dob=null,salary=null;
                    try {
						connPool.createTable();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    while ((line = br.readLine()) != null) {
                        int pos = line.indexOf(sep);                        
                         name = line.split("\\|")[0];
                         dob = line.split("\\|")[1];
                         salary = line.split("\\|")[2];
                         System.out.println(name.indexOf(sep));
                         System.out.println(dob.indexOf(sep));
                         System.out.println(salary.indexOf(sep));
                        if (pos > limit ){
                            invalidTextWriteFile.write(line+"\n");
                        	invalidTextWriteFile.flush();
                        	}
                        if(name.indexOf(sep)>42 | dob.indexOf(sep)>50 ) {
                        	System.out.println("inside loop");
                        	 invalidTextWriteFile.write(line+"\n");
                         	invalidTextWriteFile.flush();
                        }
                        else {          	
                        	validTextWriteFile.write(line+"\n");
                        	validTextWriteFile.flush();
                        	try {
                        		
								connPool.insertData(name, dob, salary);
							} catch (ClassNotFoundException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}                        	
                        }                        	
                        }   
                   
                    invalidTextWriteFile.close();
                    validTextWriteFile.close();
                    validfileReader.close();
                    }
//                NodeList dependentfileNode = f.rootNode.getElementsByTagName("dependentfile");
//                Element dependentfileElement = (Element) dependentfileNode.item(0);
//                NamedNodeMap dependentfileAttributeMap = dependentfileElement.getAttributes();
//                NodeList ch = (NodeList) dependentfileElement.getChildNodes().item(0);
//                System.out.println(ch);
//                String dependentfile = dependentfileAttributeMap.item(0).getNodeValue();
//                System.out.println("dependentfile : "+dependentfile);
                file.delete();
	}
			
}


//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//DocumentBuilder builder = factory.newDocumentBuilder();
//String fileName = "dependencies.xml";
//Document doc = builder.parse(fileName);
//Element rootNode = doc.getDocumentElement();
//Element rootNode1 = doc.getDocumentElement();