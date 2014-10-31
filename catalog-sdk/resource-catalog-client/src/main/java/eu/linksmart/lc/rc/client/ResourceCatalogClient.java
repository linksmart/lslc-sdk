package eu.linksmart.lc.rc.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceCatalogClient
{
	
	private String BASE_URL = "http://localhost:8080/rc";
	
    private Client client;
    
    private static ResourceCatalogClient resourceCatalogClient = null;
    
    public ResourceCatalogClient(String baseURL) {
    	this.BASE_URL = baseURL;
        this.client = Client.create();
    }
    
    public static synchronized ResourceCatalogClient getInstance(String baseURL) {
        if ( resourceCatalogClient == null ) {
        	resourceCatalogClient = new ResourceCatalogClient(baseURL);
        }
        return resourceCatalogClient;
    }
    
    private String getAddress(String interface_option) {
        return this.BASE_URL + "/" + interface_option;
    }
    
    public String getAllDevices() {
    	
    	try {
    		
    		String responseString;
    		
            WebResource webResourceClient = client.resource(this.BASE_URL);
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get catalog devices : - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());	
        	return null;
		}	
    }
    
    public boolean registerDevice(String deviceJson) {
    	
    	try {
    		
    		String interface_option = "";
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, deviceJson);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			return success;
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to register a device : status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return false;
		}	
    }
    
    public String getDevice(String deviceID) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = deviceID;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get a device : " + deviceID + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public boolean updateDevice(String deviceID, String deviceJson) {
    	
    	try {
    		
    		String interface_option = deviceID;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, deviceJson);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			return success;
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to update a device: " + deviceID + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return false;
		}	
    }
    
    public boolean deleteDevice(String deviceID) {
    	
    	try {
    		
    		String interface_option = deviceID;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			return success;
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to delete a device: " + deviceID + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return false;
		}	
    }
    
    public String getResource(String resourceID) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = resourceID;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get resource with resource-id: " + resourceID + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String search(String type, String path, String comparisonCriteria, String value) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = type + "/" + path + "/" + comparisonCriteria + "/" + value;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get device/resource for a search criteria: " + interface_option + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }

}
