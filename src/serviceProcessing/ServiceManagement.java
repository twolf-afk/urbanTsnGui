package serviceProcessing;
// 20
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import opcua.Client;
import xml.Configuration;
import xml.ReadConfiguration;

public class ServiceManagement {
	
	private static  NodeId objectNodeId = new NodeId(0, 85);
	private static String serviceLibUrl;
	
	public static void setServiceLibUrl() {
		Configuration configuration = ReadConfiguration.readConfiguration();
		serviceLibUrl = configuration.getServiceLibUrl();
	}
	
	public static String getListOfServices() {
		
		NodeId methodNodeId = new NodeId(1, "Get List of");
		
		String list = Client.callMethodWithArgument(serviceLibUrl, objectNodeId, methodNodeId, "services");
		
		return list;
	}
	// 11
	public static String getServiceFromServiceLib(String serviceName) {
		
		NodeId methodNodeId = new NodeId(1, "Get Service");
		
		String service = Client.callMethodWithArgument(serviceLibUrl, objectNodeId, methodNodeId, serviceName);
		
		return service;
	}
	
	public static void saveServiceInServiceLib(String processName, String serviceName, String text) {
		
		NodeId methodNodeId = new NodeId(1, "Save Service of Process");
		
		String[] arguments = new String[3];
		
		arguments[0] = processName;
		arguments[1] = serviceName.split("\\.")[0];
		arguments[2] = text;
		
		Client.callMethodWithThreeArguments(serviceLibUrl, objectNodeId, methodNodeId, arguments);
		
	}
	
}
