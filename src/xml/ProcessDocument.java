package xml;
// 23
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProcessDocument {
	
	private static Document document;
	private static Element productName;
	private static Element services;
	
	public ProcessDocument() {
        // TODO Logik des Trys in Funktion mit throws
		try {
			
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void createStructureOfDocument() {
		productName = document.createElement("productName");
		document.appendChild(productName);
		
		services = document.createElement("services");
		productName.appendChild(services);
	}
	// 12
	public void createServiceElements(int sequenceNumber, String serviceFileName) {
		Element newService = document.createElement("service");
		Attr sequenceNumberAttribute = document.createAttribute("sequenceNumber");
		sequenceNumberAttribute.setValue(String.valueOf(sequenceNumber));
		newService.setAttributeNode(sequenceNumberAttribute);
		
		Element name = document.createElement("name");
		name.appendChild(document.createTextNode("Transport"));
		newService.appendChild(name);
		
		Element linkElement = document.createElement("link");
		linkElement.appendChild(document.createTextNode(serviceFileName));
		newService.appendChild(linkElement);
		
		Element functionNameElement = document.createElement("functionName");
		String functionName = serviceFileName.split("\\.")[0];
		functionNameElement.appendChild(document.createTextNode(functionName));
		newService.appendChild(functionNameElement);
		
		services.appendChild(newService);
	}
	
	public void saveChangesAndCloseFile(String path, String processFileName) {
		
		try {
			
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            DOMSource domSource = new DOMSource(document);
            File file = new File(path + processFileName);
            StreamResult streamResult = new StreamResult(file);
            
            transformer.transform(domSource, streamResult);
            
        } catch (TransformerException te) {
            System.out.println(te.getMessage());
        }
	}

}
