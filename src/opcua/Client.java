package opcua;
// 55
import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

import java.awt.HeadlessException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.structured.CallMethodRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.CallMethodResult;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

public class Client {
	
	
	private static OpcUaClient client;
	private static String serverUrl;
	
	public Client() {
		
		client = null;
		
	}
	// 45
	public static void createAndConnectClient(String url) {
		
		serverUrl = url;
		
		try {
			SecurityPolicy securityPolicy = SecurityPolicy.None;
			
			EndpointDescription[] endpoints = UaTcpStackClient.getEndpoints(serverUrl).get();
			EndpointDescription endpoint;
			
			endpoint = Arrays.stream(endpoints)
					.filter(e -> e.getSecurityPolicyUri().equals(securityPolicy.getSecurityPolicyUri())).findFirst()
					.orElseThrow(() -> new Exception("no desired endpoints returned"));


			OpcUaClientConfig config = OpcUaClientConfig
					.builder()
					.setApplicationName(LocalizedText.english("OPC UA Client"))
					.setApplicationUri("urn:/urbanTsnGui")
					.setEndpoint(endpoint)
					.setIdentityProvider(new AnonymousProvider())
					.setRequestTimeout(uint(5000)).build();
			
			client = new OpcUaClient(config);
			
			client.connect().get();
			
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		
	}
	
	static EndpointDescription updateEndpointUrl(EndpointDescription original, String hostname) {

		URI uri = null;
		try {
			uri = new URI(original.getEndpointUrl()).parseServerAuthority();
		} catch (URISyntaxException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		String endpointUrl = String.format("%s://%s:%s%s", uri.getScheme(), hostname, uri.getPort(), uri.getPath());
		return new EndpointDescription(endpointUrl, original.getServer(), original.getServerCertificate(),
				original.getSecurityMode(), original.getSecurityPolicyUri(), original.getUserIdentityTokens(),
				original.getTransportProfileUri(), original.getSecurityLevel());
	}
	// 31
	public static String callMethodWithArgument(String url, NodeId objectNodeId, NodeId methodNodeId, String argument) {
		
		createAndConnectClient(url);
		
		String result = "";
		
		try {
			
			Variant[] arguments = new Variant[]{new Variant(argument)};
			
			CallMethodRequest request = new CallMethodRequest(objectNodeId, methodNodeId, arguments);			
			CompletableFuture<CallMethodResult> callResult = client.call(request);
			
			result = callResult.get().getOutputArguments()[0].getValue().toString();
			
			} catch (HeadlessException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		
		return result;
		
	}
	// 21
	public static String callMethodWithTwoArguments(String url, NodeId objectNodeId, NodeId methodNodeId, String[] arguments) {
		
		createAndConnectClient(url);
		
		String result = "";
		
		try {
			
			Variant[] variants = new Variant[]{new Variant(arguments[0]), new Variant(arguments[1])};
			
			CallMethodRequest request = new CallMethodRequest(objectNodeId, methodNodeId, variants);			
			CompletableFuture<CallMethodResult> callResult = client.call(request);
			
			result = callResult.get().getStatusCode().toString();
			
			} catch (HeadlessException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		
		return result;
		
	}
	// 11
	public static String callMethodWithThreeArguments(String url, NodeId objectNodeId, NodeId methodNodeId, String[] arguments) {
		
		createAndConnectClient(url);
		
		String result = "";
		
		try {
			
			Variant[] variants = new Variant[]{new Variant(arguments[0]), new Variant(arguments[1]), new Variant(arguments[2])};
			
			CallMethodRequest request = new CallMethodRequest(objectNodeId, methodNodeId, variants);			
			CompletableFuture<CallMethodResult> callResult = client.call(request);
			
			result = callResult.get().getStatusCode().toString();
			
			} catch (HeadlessException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		
		return result;
		
	}
	
}
