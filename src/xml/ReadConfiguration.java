package xml;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReadConfiguration {

	public static Configuration readConfiguration() {

		Configuration configuration = new Configuration();

		String workflowEngineUrl = "";
		String serviceLibUrl = "";
		String pathToProcesses = "";
		String pathToServices = "";
		

		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("configuration.xml");
			document.getDocumentElement().normalize();
			
			NodeList xmlElements = document.getElementsByTagName("configuration").item(0).getChildNodes();

			for (int i = 1; i < xmlElements.getLength(); i++) {

				if(xmlElements.item(i).getNodeType() == Element.ELEMENT_NODE) {

					switch (xmlElements.item(i).getNodeName()) {
					case "workflowEngine":
						workflowEngineUrl = xmlElements.item(i).getTextContent();
						break;

					case "serviceLib":
						serviceLibUrl = xmlElements.item(i).getTextContent();
						break;
						
					case "processPath":
						pathToProcesses = xmlElements.item(i).getTextContent();
						break;
						
					case "servicePath":
						pathToServices = xmlElements.item(i).getTextContent();
						break;

					
					default:
						JOptionPane.showMessageDialog(null, "unkown xml element name");
						break;
					}
				}
			}

			configuration = new Configuration(workflowEngineUrl, serviceLibUrl, pathToProcesses, pathToServices);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error, while loading testconfiguration. See LogFile.json for details.");
		}
		return configuration;
	}
}
