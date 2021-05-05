package main;
// 70
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import processProcessing.ProcessManagement;
import serviceProcessing.ServiceManagement;
import util.Util;
import xml.ProcessDocument;
import xml.ReadConfiguration;
import xml.ServiceArgument;
import xml.Configuration;
import xml.ParseService;

public class Core {
	// 66
	public static void initGuiBackend() {
		
		ProcessManagement.setUrls();
		ServiceManagement.setServiceLibUrl();
		
		String serviceList = ServiceManagement.getListOfServices();
		
		String[] services = serviceList.split(";");
		
		for (int i = 0; i < services.length; i++) {
			
			String serviceFilename = services[i];
			
			String service = ServiceManagement.getServiceFromServiceLib(serviceFilename);
			// TODO path from configuration file
			Util.saveAsFile(service, "services/" + serviceFilename);
			
		}
		
		String processList = ProcessManagement.getListOfProcesses();
		
		String[] processes = processList.split(";");
		
		for (int i = 0; i < processes.length; i++) {
			
			String processFilename = processes[i];
			
			if (!processFilename.equals("")) {
				
				String process = ProcessManagement.getProcessFromServiceLib(processFilename);
				// TODO path from configuration file
				Util.saveAsFile(process, "processes/" + processFilename);
				
			}
		}
	}
	
	public static void resetBackend() {
		Util.deleteFileInDirectory("processes/");
		Util.deleteFileInDirectory("services/");
	}
	// 48
	public static void executeProcess(String processToExecute) {
		
		String processName = processToExecute.split("/")[1];
		
		LinkedList<String> files = new LinkedList<>();
		
		if (Util.directoryExists(processToExecute)) {
			files = Util.getFilesInDirectory(processToExecute);
		}
		
		for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
			
			String fileName = (String) iterator.next();
			
			JOptionPane.showMessageDialog(null, fileName);
			
			String text = Util.openFileAndGetText(processToExecute + "/", fileName);
			
			if (fileName.endsWith(".xml")) {
				ProcessManagement.saveProcessInServiceLib(processName, text);
			} else {
				ServiceManagement.saveServiceInServiceLib(processName, fileName, text);
			}
			
		}
		
		ProcessManagement.executeProcess(processName);
	}
	// 37
	public static void executeProcessStep(String processName, String sequenceNumber) {
		
		ProcessManagement.executeProcessStep(processName, sequenceNumber);
		
	}
	// 34
	public static ParseService parseService(String processName, String serviceName) {
		
		Configuration configuration = ReadConfiguration.readConfiguration();
		
		ParseService service = new ParseService(configuration.getPathToProcesses() + processName + "/" + serviceName);
		return service;
	}
	// 15+6+8+2
	public static void updateServiceArguments(String serviceName, LinkedList<ServiceArgument> arguments) {
		ParseService service = new ParseService(serviceName);
		service.updateService(arguments);
		
		service.saveChangesAndCloseFile();
	}
	// 15+6+8
	public static void saveProcess(String processName, String servicesList) {
		
		String[] services = servicesList.split(";");
		
		ProcessDocument process = new ProcessDocument();
		process.createStructureOfDocument();
		
		for (int i = 1; i < services.length; i++) {
			String service = services[i];
			
			process.createServiceElements(i, service);
			
			process.saveChangesAndCloseFile("processes/" + processName + "/", processName + ".xml");
			
		}
		
	}
	// 15+6
	public static String copyFileFromSourceToDestination(String source, String destination) {
		
		String[] destinationParts  = destination.split("/");
		String[] fileNameParts = destinationParts[2].split("\\.");
		String newFileName = fileNameParts[0] + "." + fileNameParts[1];
		
		if (Util.directoryExists(destination)) {
			
			newFileName = fileNameParts[0] + System.currentTimeMillis() + "." + fileNameParts[1];
			destination = destinationParts[0] + "/" + destinationParts[1] + "/" + newFileName;
			
		}
		
		Util.copyFileFromSourceToDestination(source, destination);
		return newFileName;
	}
	// 15
	public static LinkedList<String> getOutputOfServices(String processName) {
		
		LinkedList<String> outputs = new LinkedList<>();
		outputs.add("");
		
		Configuration configuration = ReadConfiguration.readConfiguration();
		
		LinkedList<String> files = Util.getFilesInDirectory(configuration.getPathToProcesses() + processName + "/");
		int filesLength = files.size();
		
		for (int i = 0; i < filesLength; i++) {
			
			String fileName = files.get(i);
			
			ParseService service = new ParseService(configuration.getPathToProcesses() + processName + "/" + fileName);
			
			LinkedList<ServiceArgument> arguments = service.getOutputsFromDocument();
			
			int argumentsLength = arguments.size();
			for (int j = 0; j < argumentsLength; j++) {
				
				String name = arguments.get(j).getName();
				
				if (name.contains("outputOpcUa") && !name.contains("processDone")) {
					outputs.add(fileName + "-" + name);
				}
				
			}
			
		}
		
		return outputs;
	}
	
}
