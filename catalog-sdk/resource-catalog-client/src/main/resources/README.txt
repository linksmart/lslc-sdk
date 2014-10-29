- add following maven artifact into your project:

<dependency>
   	<groupId>eu.linksmart.lc</groupId>
   	<artifactId>resource-catalog-client</artifactId>
   	<version>0.0.1-SNAPSHOT</version>
</dependency>

- set URL of the catalog service as follows:

	ResourceCatalog.setURL("http://hostname:port");

- use DeviceBuilder class to get Registration object that will be used to create device registration into resource catalog:

	create registration object from a json file
		Registration registration = DeviceBuilder.createRegistration(String jsonFileName)
	or
	create a device registration for given ID & Name with one resource for protocol type REST
		Registration registration = DeviceBuilder.createRegistration(String deviceID, String deviceName, String resourceID, String resourceName, String URL)
		
- to get all registered devices
	Catalog catalog = ResourceCatalog.getAllDevices()
	
- to get a given device
	Device device = ResourceCatalog.getDevice(deviceID)
	
- to get a given resource
	Resource resource = ResourceCatalog.getResource(resourceID)
	
- to update a given device
	ResourceCatalog.updateDevice(deviceID, registration)
	
- to search a device for given criteria
	Device device = ResourceCatalog.searchDevice(path, criteria, value)
	
- to search a resource for a given criteria
	Resource resource = ResourceCatalog.search(path, criteria, value)	