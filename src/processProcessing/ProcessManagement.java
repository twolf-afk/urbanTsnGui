package processProcessing;
// 37
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import opcua.Client;
import xml.Configuration;
import xml.ReadConfiguration;

public class ProcessManagement {
	
	private static  NodeId objectNodeId = new NodeId(0, 85);
	private static String serviceLibUrl;
	private static String workflowEngineUrl;
	
	public static void setUrls() {
		
		Configuration configuration = ReadConfiguration.readConfiguration();
		serviceLibUrl = configuration.getServiceLibUrl();
		workflowEngineUrl = configuration.getWorkflowEngineUrl();
		
	}
	
	public static String getListOfProcesses() {
		
		NodeId methodNodeId = new NodeId(1, "Get List of");
		
		String list = Client.callMethodWithArgument(serviceLibUrl, objectNodeId, methodNodeId, "processes");
		
		return list;
	}
	// 26
	public static String getProcessFromServiceLib(String processName) {
		
		NodeId methodNodeId = new NodeId(1, "Get Process");
		
		String process = Client.callMethodWithArgument(serviceLibUrl, objectNodeId, methodNodeId, processName);
		
		return process;
	}
	
	public static void saveServicesOfProcess(String processName, String serviceName, String text) {
		
		NodeId methodNodeId = new NodeId(1, "Save Process as XML");
		
		String[] arguments = new String[3];
		
		arguments[0] = processName;
		arguments[1] = serviceName;
		arguments[2] = text;
		
		Client.callMethodWithThreeArguments(serviceLibUrl, objectNodeId, methodNodeId, arguments);
		
	}
	// 16
	public static void saveProcessInServiceLib(String processName, String text) {
		
		NodeId methodNodeId = new NodeId(1, "Save Process as XML");
		
		String[] arguments = new String[2];
		
		arguments[0] = processName;
		arguments[1] = text;
		
		Client.callMethodWithTwoArguments(serviceLibUrl, objectNodeId, methodNodeId, arguments);
		
	}
	// 10
	public static String executeProcess(String processToExecute) {
		
		String result = "error";
		
		NodeId methodNodeId = new NodeId(1, "Execute Process");
		
		result = Client.callMethodWithArgument(workflowEngineUrl, objectNodeId, methodNodeId, processToExecute);
		
		return result;
	}
	
	public static void executeProcessStep(String processName, String sequenceNumber) {
		
		NodeId methodNodeId = new NodeId(1, "Execute Process Step");
		
		String argument = processName + ";" + sequenceNumber;
		
		Client.callMethodWithArgument(workflowEngineUrl, objectNodeId, methodNodeId, argument);
		
	}
}
