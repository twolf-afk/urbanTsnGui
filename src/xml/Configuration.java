package xml;

public class Configuration {
	
	private String workflowEngineUrl;
	private String serviceLibUrl;
	
	private String pathToProcesses;
	private String pathToServices;
	
	public Configuration() {
		this.workflowEngineUrl = "";
		this.serviceLibUrl = "";
		this.setPathToProcesses("");
		this.setPathToServices("");
	}
	
	public Configuration(String workflowEngineUrl, String serviceLibUrl, String PathToProcesses, String PathToServices) {
		
		this.workflowEngineUrl = workflowEngineUrl;
		this.serviceLibUrl = serviceLibUrl;
		this.setPathToProcesses(PathToProcesses);
		this.setPathToServices(PathToServices);
		
	}
	
	public String getWorkflowEngineUrl() {
		return workflowEngineUrl;
	}
	public void setWorkflowEngineUrl(String workflowEngineUrl) {
		this.workflowEngineUrl = workflowEngineUrl;
	}
	public String getServiceLibUrl() {
		return serviceLibUrl;
	}
	public void setServiceLibUrl(String serviceLibUrl) {
		this.serviceLibUrl = serviceLibUrl;
	}

	public String getPathToServices() {
		return pathToServices;
	}

	public void setPathToServices(String pathToServices) {
		this.pathToServices = pathToServices;
	}
	public String getPathToProcesses() {
		return pathToProcesses;
	}
	public void setPathToProcesses(String pathToProcesses) {
		this.pathToProcesses = pathToProcesses;
	}
	
}
