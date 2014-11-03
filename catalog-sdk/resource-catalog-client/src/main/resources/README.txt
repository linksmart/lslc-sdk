- add following maven artifact into your project:

<dependency>
   	<groupId>eu.linksmart.lc</groupId>
   	<artifactId>resource-catalog-client</artifactId>
   	<version>0.0.1-SNAPSHOT</version>
</dependency>

- set URL of the catalog service as follows:

	ResourceCatalog.setURL("http://hostname:port/base_path");

- use DeviceBuilder class to create Registration object that will be used to add device registration into resource catalog:

	create registration object from a json file
		Registration registration = DeviceBuilder.createRegistration(String jsonFileName)
	or
	create a device registration for given device connectorID & device name with one resource of protocol type REST
		Registration registration = DeviceBuilder.createRegistration(String deviceConnectorID, String deviceName, String resourceName, String URL)
		
- to add a device registration
	ResourceCatalog.add(registration)
				
- to get a given device
	Device device = ResourceCatalog.get(deviceID)
		
- to update a given device
	ResourceCatalog.update(deviceID, registration)
	
- to delete a given device
	ResourceCatalog.delete(deviceID)

- to get a given resource
	Resource resource = ResourceCatalog.getResource(resourceID)
		
- to get registered devices
	Catalog catalog = ResourceCatalog.getDevices(page, perPage)
	
- to find a device for a given criteria
	Device device = ResourceCatalog.findDevice(path, criteria, value)
	
- to find devices for a given criteria
	Catalog catalog = ResourceCatalog.findDevices(path, criteria, value, page, perPage)
	
- to find a resource for a given criteria
	Resource resource = ResourceCatalog.findResource(path, criteria, value)

- to find resources for a given criteria
	Catalog catalog = ResourceCatalog.findResources(path, criteria, value, page, perPage)	