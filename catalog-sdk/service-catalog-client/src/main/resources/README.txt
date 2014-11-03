- add following maven artifact into your project:

<dependency>
   	<groupId>eu.linksmart.lc</groupId>
   	<artifactId>service-catalog-client</artifactId>
   	<version>0.0.1-SNAPSHOT</version>
</dependency>

- set URL of the catalog service as follows:

	ServiceCatalog.setURL("http://hostname:port/base_path");

- use ServiceBuilder class to create Registration object that will be used to add service registration into service catalog:

	create registration object from a json file
		Registration registration = ServiceBuilder.createRegistration(String jsonFileName)
	or
	create a service registration for given ID & Name for protocol type REST
		Registration registration = ServiceBuilder.createRegistration(String serverID, String serviceName, String URL)

- to add a service registration
	Service service = ServiceCatalog.add(registration)
	
- to get a given service
	Service service = ServiceCatalog.get(serviceID)
		
- to update a given service
	ServiceCatalog.update(serviceID, registration)

- to delete a given service
	ServiceCatalog.delete(serviceID)

- to get registered services
	SCatalog catalog = ServiceCatalog.getServices(page, perPage)
			
- to find a service for given criteria
	Service service = ServiceCatalog.findService(path, criteria, value)
	
- to find services for a given criteria
	SCatalog catalog = ServiceCatalog.findServices(path, criteria, value, page, perPage)