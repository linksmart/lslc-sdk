package eu.linksmart.lc.sc.client;

import com.google.gson.Gson;

import eu.linksmart.lc.sc.types.Registration;
import eu.linksmart.lc.sc.types.SCatalog;
import eu.linksmart.lc.sc.types.Service;

public class ServiceCatalog {
	
	private static String BASE_URL = "http://gando.fit.fraunhofer.de:8090";
	
	public static void setURL(String url) {
		BASE_URL = url;
	}
	
	public static boolean registerService(Registration registration) {
		return ServiceCatalogClient.getInstance(BASE_URL).registerService(new Gson().toJson(registration));
	}
	
	public static SCatalog getAllServices() {
		String catalogJsonString = ServiceCatalogClient.getInstance(BASE_URL).getAllServices();
		return new Gson().fromJson(catalogJsonString, SCatalog.class);
	}
	
	public static Service getService(String serviceID) {
		String serviceJsonString = ServiceCatalogClient.getInstance(BASE_URL).getService(serviceID);
		return new Gson().fromJson(serviceJsonString, Service.class);
	}
	
	public static boolean updateService(String serviceID, Registration updatedServiceJson) {
		return ServiceCatalogClient.getInstance(BASE_URL).updateService(serviceID, new Gson().toJson(updatedServiceJson));
	}
	
	public static boolean deleteService(String serviceID) {
		return ServiceCatalogClient.getInstance(BASE_URL).deleteService(serviceID);
	}
	
	public static Service search(String type, String path, String criteria, String value) {
		String result_updated_sr = ServiceCatalogClient.getInstance(BASE_URL).search(type, path, criteria, value);
		return new Gson().fromJson(result_updated_sr, Service.class);	
	}
}
