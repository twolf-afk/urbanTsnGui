package xml;
// 46
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseService {
	
	private Document document;
	private String serviceName;
	private String url;
	private LinkedList<ServiceArgument> inputs;
	private LinkedList<ServiceArgument> outputs;
	// 41
	public ParseService(String fileName) {
		
		serviceName = fileName;
		
		inputs = new LinkedList<ServiceArgument>();
		outputs = new LinkedList<ServiceArgument>();
		
		File file = new File(fileName);
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        
		try {
			
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getUrlFromDocument() {
		
		Node urlNode = document.getElementsByTagName("soap:address").item(0);
		Element urlElement = (Element) urlNode;
		
		url = urlElement.getAttribute("location");
		
		return url;
	}
	// 28
	public LinkedList<ServiceArgument> getInputsFromDocument() {
		
		Node all = document.getElementsByTagName("all").item(0);
		NodeList inputNodes = all.getChildNodes();
		
		for (int i = 0; i < inputNodes.getLength(); i++) {
			
			Node inputNode = inputNodes.item(i);
			
			if (inputNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element inputElement = (Element) inputNode;
				
				ServiceArgument input = new ServiceArgument();
				
				String name = inputElement.getAttribute("name");
				input.setName(name);
				
				String datatype = inputElement.getAttribute("type");
				input.setDatatype(datatype);
				
				String value = inputElement.getAttribute("value");
				input.setValue(value);
				
				inputs.add(input);
								
			}
		}
		return inputs;
	}
	// 26
	public LinkedList<ServiceArgument> getOutputsFromDocument() {
		
		Node all = document.getElementsByTagName("all").item(1);
		NodeList outputNodes = all.getChildNodes();

		for (int i = 0; i < outputNodes.getLength(); i++) {

			Node outputNode = outputNodes.item(i);

			if (outputNode.getNodeType() == Node.ELEMENT_NODE) {

				Element outputElement = (Element) outputNode;

				ServiceArgument output = new ServiceArgument();

				String name = outputElement.getAttribute("name");
				output.setName(name);

				String datatype = outputElement.getAttribute("type");
				output.setDatatype(datatype);

				String value = outputElement.getAttribute("value");
				output.setValue(value);

				outputs.add(output);

			}
		}
		return outputs;
	}
	// 14
	public void updateService(LinkedList<ServiceArgument> arguments) {
		for (int j = 0; j < 2; j++) {
			
			Node all = document.getElementsByTagName("all").item(j);
			NodeList argumentNodes = all.getChildNodes();
	
			for (int i = 0; i < argumentNodes.getLength(); i++) {
				
				Node argumentNode = argumentNodes.item(i);
	
				if (argumentNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element element = (Element) argumentNode;
					int countArguments = arguments.size();
					
					for (int k = 0; k < countArguments; k++) {
						
						ServiceArgument argument = arguments.get(k);
						
						if (element.getAttribute("name").equals(argument.getName())) {
							element.setAttribute("value", argument.getValue());
						}
					}
				}
			}
		}
	}
	
	public void saveChangesAndCloseFile() {
		try {
			
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(serviceName)));

        } catch (TransformerException te) {
            System.out.println(te.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
	}
	
}
