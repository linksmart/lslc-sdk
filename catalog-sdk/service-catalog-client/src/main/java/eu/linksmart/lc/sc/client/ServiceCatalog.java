package eu.linksmart.lc.sc.client;

import com.google.gson.Gson;

import eu.linksmart.lc.sc.types.Registration;
import eu.linksmart.lc.sc.types.SCatalog;
import eu.linksmart.lc.sc.types.Service;

public class ServiceCatalog {
	
	private static String BASE_URL = "http://gando.fit.fraunhofer.de:8090/sc";
	
	public static void setURL(String url) {
		BASE_URL = url;
	}
	
	public static boolean add(Registration registration) {
		return ServiceCatalogClient.getInstance(BASE_URL).add(new Gson().toJson(registration));
	}
	
	public static Service get(String serviceID) {
		String serviceJsonString = ServiceCatalogClient.getInstance(BASE_URL).get(serviceID);
		return new Gson().fromJson(serviceJsonString, Service.class);
	}
	
	public static boolean update(String serviceID, Registration updatedServiceJson) {
		return ServiceCatalogClient.getInstance(BASE_URL).update(serviceID, new Gson().toJson(updatedServiceJson));
	}
	
	public static boolean delete(String serviceID) {
		return ServiceCatalogClient.getInstance(BASE_URL).delete(serviceID);
	}
	
	public static SCatalog getServices(int page, int perPage) {
		String catalogJsonString = ServiceCatalogClient.getInstance(BASE_URL).getServices(page, perPage);
		return new Gson().fromJson(catalogJsonString, SCatalog.class);
	}
	
	public static Service findService(String path, String criteria, String value) {
		String result_fs = ServiceCatalogClient.getInstance(BASE_URL).findService(path, criteria, value);
		return new Gson().fromJson(result_fs, Service.class);	
	}
	
	public static SCatalog findServices(String path, String criteria, String value, int page, int perPage) {
		String result_fss = ServiceCatalogClient.getInstance(BASE_URL).findServices(path, criteria, value, page, perPage);
		return new Gson().fromJson(result_fss, SCatalog.class);	
	}
}
