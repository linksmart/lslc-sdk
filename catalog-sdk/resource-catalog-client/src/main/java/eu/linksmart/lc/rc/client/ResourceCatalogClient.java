package eu.linksmart.lc.rc.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class ResourceCatalogClient
{
	
	private String BASE_URL = "http://localhost:8081";
	
	private String PATH = "/rc";
	
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
        return this.BASE_URL + interface_option;
    }
    
    public String add(String deviceJson) {
    	
    	try {
    		
    		String interface_option = PATH + "/devices/";
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, deviceJson);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			MultivaluedMap<String, String> entries = response.getHeaders();
    			return entries.get("Location").get(0);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to register a device : status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String get(String deviceUrl) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = deviceUrl;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get a device : " + deviceUrl + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public boolean update(String deviceUrl, String deviceJson) {
    	
    	try {
    		
    		String interface_option = deviceUrl;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, deviceJson);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			return success;
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to update a device: " + deviceUrl + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return false;
		}	
    }
    
    public boolean delete(String deviceUrl) {
    	
    	try {
    		
    		String interface_option = deviceUrl;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			return success;
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to delete a device: " + deviceUrl + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return false;
		}	
    }
    
    public boolean add(String id, String deviceJson) {
    	
    	try {
    		
    		String interface_option = PATH + "/devices/" + id;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, deviceJson);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			return success;
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to add a device: " + id + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return false;
		}	
    }
    
    public String getById(String deviceId) {
    	return get(PATH + "/devices/" + deviceId);	
    }
    
    public boolean updateById(String deviceId, String deviceJson) {
    	return update(PATH + "/devices/" + deviceId, deviceJson);	
    }
    
    public boolean deleteById(String deviceId) {
    	return delete(PATH + "/devices/" + deviceId);	
    }
    
    public String getCatalogInfo() {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/";
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get catalog info : - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());	
        	return null;
		}	
    }
    
    public String getDevices() {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/devices";
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get devices : - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());	
        	return null;
		}	
    }
    
    public String getDevices(int page, int perPage) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/devices" + "?page=" + page + "&per_page=" + perPage;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            
    		boolean success = (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL);
    		           
    		if (success) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get devices : - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());	
        	return null;
		}	
    }
    
    public String findDevices(String path, String operation, String value) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/devices" + "/" + path + "/" + operation + "/" + value;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to find devices for a search criteria: " + interface_option + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String findDevices(String path, String operation, String value, int page, int perPage) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/devices" + "/" + path + "/" + operation + "/" + value + "?page=" + page + "&per_page=" + perPage;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to find devices for a search criteria: " + interface_option + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String getResources() {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/resources";
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get resources - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String getResources(int page, int perPage) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/resources" + "?page=" + page + "&per_page=" + perPage;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get resources - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String getResource(String resourceUrl) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = resourceUrl;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to get resource with resource-url: " + resourceUrl + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String getResourceById(String resourceID) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/resources/" + resourceID;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
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
    
    public String findResources(String path, String operation, String value) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/resources/" + path + "/" + operation + "/" + value;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to find resources for a search criteria: " + interface_option + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
    
    public String findResources(String path, String operation, String value, int page, int perPage) {
    	
    	try {
    		
    		String responseString;
    		
    		String interface_option = PATH + "/resources/" + path + "/" + operation + "/" + value + "?page=" + page + "&per_page=" + perPage;
    		
            WebResource webResourceClient = client.resource(this.getAddress(interface_option));
            
            ClientResponse response = webResourceClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                       
    		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
    			responseString = response.getEntity(String.class);
    		} else {
    			String error = response.getStatusInfo().getReasonPhrase();
    			throw new Exception("failed to find resources for a search criteria: " + interface_option + "  - status code: " + response.getStatusInfo().getStatusCode() + " - reason: " + error);
    		}
    		
    		return responseString;
    		  
        } catch (Exception e) {
        	System.err.println("resource catalog error: reason: " + e.getMessage());
        	return null;
		}	
    }
}
