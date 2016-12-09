package eu.linksmart.lc.rc.client;

import com.google.gson.Gson;

import eu.linksmart.lc.rc.types.Catalog;
import eu.linksmart.lc.rc.types.Device;
import eu.linksmart.lc.rc.types.Devices;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;
import eu.linksmart.lc.rc.types.Resources;

public class ResourceCatalog {
	
	private static String BASE_URL = "http://localhost:8081/rc";
	
	public static void setURL(String url) {
		BASE_URL = url;
	}
	
	// by Device URL
	public static String add(Registration registration) {
		return ResourceCatalogClient.getInstance(BASE_URL).add(new Gson().toJson(registration));
	}
	
	public static Device get(String deviceUrl) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).get(deviceUrl), Device.class);
	}
	
	public static boolean update(String deviceUrl, Registration updatedDeviceJson) {
		return ResourceCatalogClient.getInstance(BASE_URL).update(deviceUrl, new Gson().toJson(updatedDeviceJson));
	}
	
	public static boolean delete(String deviceUrl) {
		return ResourceCatalogClient.getInstance(BASE_URL).delete(deviceUrl);
	}
	
	// by Device ID
	public static boolean add(String id, Registration registration) {
		return ResourceCatalogClient.getInstance(BASE_URL).add(id, new Gson().toJson(registration));
	}
	
	public static Device getById(String deviceId) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).getById(deviceId), Device.class);
	}
	
	public static boolean updateById(String deviceId, Registration updatedDeviceJson) {
		return ResourceCatalogClient.getInstance(BASE_URL).updateById(deviceId, new Gson().toJson(updatedDeviceJson));
	}
	
	public static boolean deleteById(String deviceId) {
		return ResourceCatalogClient.getInstance(BASE_URL).deleteById(deviceId);
	}
	
	// get/find Devices
	public static Catalog getCatalogInfo() {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).getCatalogInfo(), Catalog.class);
	}
	
	public static Devices getDevices() {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).getDevices(), Devices.class);
	}
	
	public static Devices getDevices(int page, int perPage) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).getDevices(page, perPage), Devices.class);
	}
	
	public static Devices findDevices(String path, String operation, String value) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).findDevices(path, operation, value), Devices.class);	
	}
	
	public static Devices findDevices(String path, String operation, String value, int page, int perPage) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).findDevices(path, operation, value, page, perPage), Devices.class);	
	}
	
	// get/find Resources
	public static Resources getResources() {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).getResources(), Resources.class);
	}
	
	public static Resources getResources(int page, int perPage) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).getResources(page, perPage), Resources.class);
	}
	
	public static Resource getResource(String resourceUrl) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).getResource(resourceUrl), Resource.class);
	}
	
	public static Resource getResourceById(String resourceID) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).getResourceById(resourceID), Resource.class);
	}
	
	public static Resources findResources(String path, String operation, String value) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).findResources(path, operation, value), Resources.class);	
	}
	
	public static Resources findResources(String path, String operation, String value, int page, int perPage) {
		return new Gson().fromJson(ResourceCatalogClient.getInstance(BASE_URL).findResources(path, operation, value, page, perPage), Resources.class);	
	}
}
