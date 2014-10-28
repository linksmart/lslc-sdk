package eu.linksmart.lc.rc.client;

import com.google.gson.Gson;

import eu.linksmart.lc.rc.types.Catalog;
import eu.linksmart.lc.rc.types.Device;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;

public class ResourceCatalog {
	
	public static final String TYPE_DEVICE = "device";
	
	public static final String TYPE_RESOURCE = "resource";
	
	private static String BASE_URL = "http://gando.fit.fraunhofer.de:8091";
	
	static {
		BASE_URL = ConfigurationProvider.getString( "eu.linksmart.lc.rc.url" ); //$NON-NLS-1$;
	}
	
	public static void setURL(String url) {
		BASE_URL = url;
	}
	
	public static boolean registerDevice(Registration registration) {
		return ResourceCatalogClient.getInstance(BASE_URL).registerDevice(new Gson().toJson(registration));
	}
	
	public static Catalog getAllDevices() {
		String catalogJsonString = ResourceCatalogClient.getInstance(BASE_URL).getAllDevices();
		return new Gson().fromJson(catalogJsonString, Catalog.class);
	}
	
	public static Device getDevice(String deviceID) {
		String deviceJsonString = ResourceCatalogClient.getInstance(BASE_URL).getDevice(deviceID);
		return new Gson().fromJson(deviceJsonString, Device.class);
	}
	
	public static boolean updateDevice(String deviceID, Registration updatedDeviceJson) {
		return ResourceCatalogClient.getInstance(BASE_URL).updateDevice(deviceID, new Gson().toJson(updatedDeviceJson));
	}
	
	public static boolean deleteDevice(String deviceID) {
		return ResourceCatalogClient.getInstance(BASE_URL).deleteDevice(deviceID);
	}
	
	public static Resource getResource(String resourceID) {
		String resourceJsonString = ResourceCatalogClient.getInstance(BASE_URL).getResource(resourceID);
		return new Gson().fromJson(resourceJsonString, Resource.class);
	}
	
	public static Resource search(String type, String path, String criteria, String value) {
		String result_updated_sr = ResourceCatalogClient.getInstance(BASE_URL).search(type, path, criteria, value);
		return new Gson().fromJson(result_updated_sr, Resource.class);	
	}
}
