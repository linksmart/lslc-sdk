package eu.linksmart.lc.rc.client;

import java.util.List;

import com.google.gson.Gson;

import eu.linksmart.lc.rc.types.Catalog;
import eu.linksmart.lc.rc.types.Device;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;

public class ResourceCatalog {
	
	private static String BASE_URL = "http://gando.fit.fraunhofer.de:8091/rc";
	
	public static void setURL(String url) {
		BASE_URL = url;
	}
	
	public static boolean add(Registration registration) {
		return ResourceCatalogClient.getInstance(BASE_URL).add(new Gson().toJson(registration));
	}
	
	public static Device get(String deviceID) {
		String deviceJsonString = ResourceCatalogClient.getInstance(BASE_URL).get(deviceID);
		return new Gson().fromJson(deviceJsonString, Device.class);
	}
	
	public static boolean update(String deviceID, Registration updatedDeviceJson) {
		return ResourceCatalogClient.getInstance(BASE_URL).update(deviceID, new Gson().toJson(updatedDeviceJson));
	}
	
	public static boolean delete(String deviceID) {
		return ResourceCatalogClient.getInstance(BASE_URL).delete(deviceID);
	}
	
	public static Resource getResource(String resourceID) {
		String resourceJsonString = ResourceCatalogClient.getInstance(BASE_URL).getResource(resourceID);
		Resource resource = new Gson().fromJson(resourceJsonString, Resource.class);
		resource.setId(adjustDeviceId(resource.getId()));
		resource.setDevice(adjustDeviceId(resource.getDevice()));
		return resource;
	}
	
	public static Catalog getDevices(int page, int perPage) {
		String catalogJsonString = ResourceCatalogClient.getInstance(BASE_URL).getDevices(page, perPage);
		Catalog catalog = new Gson().fromJson(catalogJsonString, Catalog.class);
		adjustDevicesId(catalog);
		return catalog;
	}
	
	public static Device findDevice(String path, String operation, String value) {
		String result_fd = ResourceCatalogClient.getInstance(BASE_URL).findDevice(path, operation, value);
		return new Gson().fromJson(result_fd, Device.class);	
	}
	
	public static Catalog findDevices(String path, String operation, String value, int page, int perPage) {
		String result_fds = ResourceCatalogClient.getInstance(BASE_URL).findDevices(path, operation, value, page, perPage);
		Catalog catalog = new Gson().fromJson(result_fds, Catalog.class); 
		adjustDevicesId(catalog);
		return catalog;	
	}
	
	public static Resource findResource(String path, String operation, String value) {
		String result_fr = ResourceCatalogClient.getInstance(BASE_URL).findResource(path, operation, value);
		Resource resource = new Gson().fromJson(result_fr, Resource.class);
		resource.setId(adjustDeviceId(resource.getId()));
		resource.setDevice(adjustDeviceId(resource.getDevice()));
		return resource;	
	}
	
	public static Catalog findResources(String path, String operation, String value, int page, int perPage) {
		String result_frs = ResourceCatalogClient.getInstance(BASE_URL).findResources(path, operation, value, page, perPage);
		Catalog catalog = new Gson().fromJson(result_frs, Catalog.class); 
		adjustDevicesId(catalog);
		return catalog;	
	}
	
	private static void adjustDevicesId(Catalog catalog) {
		for (String key : catalog.getDevices().keySet()) {
            Device device = (Device) (catalog.getDevices().get(key));
            device.setId(adjustDeviceId(device.getId()));
		}
		List<Resource> resources = catalog.getResources();
		for (int i = 0; i < resources.size(); i++) {
            Resource resource = (resources.get(i));
            resource.setId(adjustDeviceId(resource.getId()));
            resource.setDevice(adjustDeviceId(resource.getDevice()));
		}
	}
	
	private static String adjustDeviceId(String deviceId) {
		StringBuilder sb = new StringBuilder(deviceId);
        sb.delete(0, (sb.indexOf("/", 1)) + 1);
        return sb.toString();
	}
	
}
