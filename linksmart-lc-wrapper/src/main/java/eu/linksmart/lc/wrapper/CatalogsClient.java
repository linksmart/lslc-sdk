package eu.linksmart.lc.wrapper;

import eu.linksmart.lc.rc.client.Comparison;
import eu.linksmart.lc.rc.client.DeviceBuilder;
import eu.linksmart.lc.rc.client.ResourceCatalog;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resources;
import eu.linksmart.lc.sc.client.ServiceBuilder;
import eu.linksmart.lc.sc.client.ServiceCatalog;
import eu.linksmart.lc.sc.types.SCatalog;

public class CatalogsClient {
	
	private String BASE_RC_URL = "http://localhost:8081/rc";
	
	private String BASE_SC_URL = "http://localhost:8082/sc";

	public CatalogsClient(String rcURL, String scURL) {
		this.BASE_RC_URL = rcURL;
		this.BASE_SC_URL = scURL;
		ResourceCatalog.setURL(BASE_RC_URL);
		ServiceCatalog.setURL(BASE_SC_URL);
	}
	
	public Registration createResource(String deviceName, String resourceName, String brokerUri, String topic) {
		return DeviceBuilder.createRegistration(deviceName, resourceName, brokerUri, topic);
	}
	
	public Registration createResource(String deviceName, String resourceName, String URL) {
		return DeviceBuilder.createRegistration(deviceName, resourceName, URL);
	}
	
	public String registerResource(Registration registration) {
		return ResourceCatalog.add(registration);
	}
	
	public Resources getResource(String resourceName) {
		return ResourceCatalog.findResources("name", Comparison.EQUALS.getCriteria(), resourceName);
	}
	
	public boolean deleteResource(String resourceUrl) {
		return ResourceCatalog.delete(resourceUrl);
	}
	
	public eu.linksmart.lc.sc.types.Registration createService(String serviceName, String URL) {
		eu.linksmart.lc.sc.types.Registration registration = ServiceBuilder.createRegistration(serviceName, URL);
		return registration;
	}
	
	public eu.linksmart.lc.sc.types.Registration createMQTTService(String serviceName, String brokerUri) {
		eu.linksmart.lc.sc.types.Registration registration = ServiceBuilder.createMqttRegistration(serviceName, brokerUri);
		return registration;
	}
	
	public String registerService(eu.linksmart.lc.sc.types.Registration sregistration) {
		return ServiceCatalog.add(sregistration);
	}
	
	public SCatalog getService(String serviceName) {
		return ServiceCatalog.findServices("name", Comparison.EQUALS.getCriteria(), serviceName);
	}
	
	public boolean deleteService(String serviceId) {
		return ServiceCatalog.delete(serviceId);
	}
	
	public Resources getAllResources() {
		return ResourceCatalog.getResources();
	}
	
	public SCatalog getAllServices() {
		return ServiceCatalog.getServices();
	}
	
	public Resources findResources(String path, String operation, String value) {
		return ResourceCatalog.findResources(path, operation, value, 1, 100);
	}
	
	public SCatalog findServices(String path, String criteria, String value) {
		return ServiceCatalog.findServices(path, criteria, value, 1, 100);
	}
}
