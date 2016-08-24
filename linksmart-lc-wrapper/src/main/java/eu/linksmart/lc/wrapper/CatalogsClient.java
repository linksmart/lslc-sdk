package eu.linksmart.lc.wrapper;

import java.util.UUID;

import eu.linksmart.lc.rc.client.Comparison;
import eu.linksmart.lc.rc.client.DeviceBuilder;
import eu.linksmart.lc.rc.client.ResourceCatalog;
import eu.linksmart.lc.rc.types.Catalog;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;
import eu.linksmart.lc.sc.client.ServiceBuilder;
import eu.linksmart.lc.sc.client.ServiceCatalog;
import eu.linksmart.lc.sc.types.SCatalog;
import eu.linksmart.lc.sc.types.Service;

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
		Registration registration = DeviceBuilder.createRegistration(UUID.randomUUID().toString(), deviceName, resourceName, brokerUri, topic);
		registration.setTtl(-1);
		return registration;
	}
	
	public Registration createResource(String deviceName, String resourceName, String URL) {
		Registration registration = DeviceBuilder.createRegistration(UUID.randomUUID().toString(), deviceName, resourceName, URL);
		registration.setTtl(-1);
		return registration;
	}
	
	public boolean registerResource(Registration registration) {
		return ResourceCatalog.add(registration);
	}
	
	public Resource getResource(String resourceName) {
		return ResourceCatalog.findResource("name", Comparison.EQUALS.getCriteria(), resourceName);
	}
	
	public boolean deleteResource(Registration registration) {
		return ResourceCatalog.delete(registration.getId());
	}
	
	public eu.linksmart.lc.sc.types.Registration createService(String serviceName, String URL) {
		eu.linksmart.lc.sc.types.Registration registration = ServiceBuilder.createRegistration(UUID.randomUUID().toString(), serviceName, URL);
		registration.setTtl(-1);
		return registration;
	}
	
	public eu.linksmart.lc.sc.types.Registration createMQTTService(String serviceName, String brokerUri) {
		eu.linksmart.lc.sc.types.Registration registration = ServiceBuilder.createMqttRegistration(UUID.randomUUID().toString(), serviceName, brokerUri);
		registration.setTtl(-1);
		return registration;
	}
	
	public boolean registerService(eu.linksmart.lc.sc.types.Registration sregistration) {
		return ServiceCatalog.add(sregistration);
	}
	
	public Service getService(String serviceName) {
		return ServiceCatalog.findService("name", Comparison.EQUALS.getCriteria(), serviceName);
	}
	
	public boolean deleteService(eu.linksmart.lc.sc.types.Registration sregistration) {
		return ServiceCatalog.delete(sregistration.getId());
	}
	
	public Catalog getAllResources(String path, String operation, String value) {
		return ResourceCatalog.findResources(path, operation, value, 1, 100);
	}
	
	public SCatalog getAllServices(String path, String criteria, String value) {
		return ServiceCatalog.findServices(path, criteria, value, 1, 100);
	}
}
