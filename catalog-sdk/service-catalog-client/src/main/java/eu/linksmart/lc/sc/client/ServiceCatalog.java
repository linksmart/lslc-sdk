package eu.linksmart.lc.sc.client;

import com.google.gson.Gson;

import eu.linksmart.lc.sc.types.Registration;
import eu.linksmart.lc.sc.types.SCatalog;
import eu.linksmart.lc.sc.types.Service;

public class ServiceCatalog {
	
	private static String BASE_URL = "http://localhost:8082/sc";
	
	public static void setURL(String url) {
		BASE_URL = url;
	}
	
	public static String add(Registration registration) {
		return getServiceId(ServiceCatalogClient.getInstance(BASE_URL).add(new Gson().toJson(registration)));
	}
	
	public static Service get(String serviceId) {
		return new Gson().fromJson(ServiceCatalogClient.getInstance(BASE_URL).get(serviceId), Service.class);
	}
	
	public static boolean update(String serviceId, Registration updatedServiceJson) {
		return ServiceCatalogClient.getInstance(BASE_URL).update(serviceId, new Gson().toJson(updatedServiceJson));
	}
	
	public static boolean delete(String serviceId) {
		return ServiceCatalogClient.getInstance(BASE_URL).delete(serviceId);
	}
	
	public static SCatalog getServices() {
		return new Gson().fromJson(ServiceCatalogClient.getInstance(BASE_URL).getServices(), SCatalog.class);
	}
	
	public static SCatalog getServices(int page, int perPage) {
		return new Gson().fromJson(ServiceCatalogClient.getInstance(BASE_URL).getServices(page, perPage), SCatalog.class);
	}
	
	public static SCatalog findServices(String path, String criteria, String value) {
		return new Gson().fromJson(ServiceCatalogClient.getInstance(BASE_URL).findServices(path, criteria, value), SCatalog.class);	
	}
	
	public static SCatalog findServices(String path, String criteria, String value, int page, int perPage) {
		return new Gson().fromJson(ServiceCatalogClient.getInstance(BASE_URL).findServices(path, criteria, value, page, perPage), SCatalog.class);	
	}
	
	private static String getServiceId(String serviceId) {
		StringBuilder sb = new StringBuilder(serviceId);
        sb.delete(0, (sb.indexOf("/", 1)) + 1);
        return sb.toString();
	}
}
