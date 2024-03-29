package eu.linksmart.lc.sc.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class ServiceCatalogClient
{
	
	private String BASE_URL = "http://localhost:8082";
	
	private String PATH = "/sc";
	
    private Client client;
    
    private static ServiceCatalogClient serviceCatalogClient = null;
    
    public ServiceCatalogClient(String baseURL) {
    	this.BASE_URL = baseURL;
        this.client = Client.create();
    }
    
    public static synchronized ServiceCatalogClient getInstance(String baseURL) {
        if ( serviceCatalogClient == null ) {
        	serviceCatalogClient = new ServiceCatalogClient(baseURL);
        }
        return serviceCatalogClient;
    }
    
    private String getAddress(String interface_option) {
        return this.BASE_URL + interface_option;
    }
    
    public String add(String serviceJson) {
    	
    	try {
    		
    		String interface_option = PATH + "/";
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, serviceJson);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			MultivaluedMap<String, String> entries = response.getHeaders();
    			return entries.get("Location").get(0);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to add a service : status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("service catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String get(String serviceID) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/" + serviceID;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get a service : " + serviceID + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("service catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public boolean update(String serviceID, String serviceJson) {
    	
    	try {
    		
    		String interface_option = PATH + "/" + serviceID;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, serviceJson);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			return success;
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to update a service: " + serviceID + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("service catalog error: reason: " + e.getMessage());
        	return false;
		}	
    }
    
    public boolean delete(String serviceID) {
    	
    	try {
    		
    		String interface_option = PATH + "/" + serviceID;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			return success;
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to delete a service: " + serviceID + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("service catalog error: reason: " + e.getMessage());
        	return false;
		}	
    }
    
    public String getServices() {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/";
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            		           
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get catalog services : - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("service catalog error: reason: " + e.getMessage());	
        	return null;
		}	
    }
    
    public String getServices(int page, int perPage) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "?page=" + page + "&per_page=" + perPage;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get catalog services : - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("service catalog error: reason: " + e.getMessage());	
        	return null;
		}	
    }
    
    public String findServices(String path, String operation, String value) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/" + path + "/" + operation + "/" + value;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to find service for a search criteria: " + interface_option + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("service catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String findServices(String path, String operation, String value, int page, int perPage) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/" + path + "/" + operation + "/" + value + "?page=" + page + "&per_page=" + perPage;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to find services for a search criteria: " + interface_option + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("service catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
}
