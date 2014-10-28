package eu.linksmart.lc.sc.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServiceCatalogClient
{
	
	private String BASE_URL = "http://gando.fit.fraunhofer.de:8091";
	
	private final String PATH = "sc";
	
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
        return this.BASE_URL + "/" + interface_option;
    }
    
}
