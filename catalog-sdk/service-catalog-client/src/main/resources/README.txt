- add following maven artifact into your project:

<dependency>
   	<groupId>eu.linksmart.lc</groupId>
   	<artifactId>service-catalog-client</artifactId>
   	<version>0.0.1-SNAPSHOT</version>
</dependency>

- set URL of the catalog service as follows:

	ServiceCatalog.setURL("http://hostname:port/base_path");

- use ServiceBuilder class to get Registration object that will be used to create service registration into service catalog:

	create registration object from a json file
		Registration registration = ServiceBuilder.createRegistration(String jsonFileName)
	or
	create a service registration for given ID & Name for protocol type REST
		Registration registration = ServiceBuilder.createRegistration(String serverID, String serviceName, String URL)
		
- to get all registered services
	SCatalog catalog = ServiceCatalog.getAllServices()
	
- to get a given service
	Service service = ServiceCatalog.getService(serviceID)
		
- to update a given service
	ServiceCatalog.updateService(serviceID, registration)

- to delete a given service
	ServiceCatalog.deleteService(serviceID)
		
- to search a service for given criteria
	Service service = ServiceCatalog.search(path, criteria, value)