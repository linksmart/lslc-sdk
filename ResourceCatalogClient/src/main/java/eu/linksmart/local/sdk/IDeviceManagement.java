package eu.linksmart.local.sdk;

import java.util.List;

/**
 * Created by carlos on 24.10.14.
 */
public interface IDeviceManagement {

    /**
     * Returns an unique device id from Resource Catalog (RC) after a device
     * was registered (added)
     *
     * @param  aDevice  representation of a device
     * @return      String representation of an registration ID. Returns null if registration was not successful
     * @see         eu.linksmart.local.sdk.LSLCDevice
     */
    public String add(LSLCDevice aDevice);
    /**
     * Deletes a given device from Resource Catalog
     *
     * @param   deviceID A String representation of registration ID
     * @return      Returns true of deletion was successful
     */
    public boolean delete(String deviceID);
    /**
     * Updates an already registered device.
     *
     * @param  aDevice  Representation of a device
     * @param  deviceID Unique ID of the device. The id was generated during the registration.
     * @return      Returns true of the update was successful
     * @see         eu.linksmart.local.sdk.LSLCDevice
     */
    public boolean update(LSLCDevice aDevice,String deviceID);
    /**
     * Retrieves a given device from Resource Catalog
     *
     * @param   deviceID A String representation of registration ID
     * @return      Returns a device representation
     * @see         eu.linksmart.local.sdk.LSLCDevice
     */
    public LSLCDevice get(String deviceID);
    /**
     * Retrieves devices from Resource Catalog.
     * Since the RC supports pagination
     * the retrieval can be split up by parameters. Good inital parametes are
     * 1 for page and 100 for perPage parameters. For specific information check
     * https://linksmart.eu/redmine/projects/linksmart-local-connect/wiki/Resource_Catalog_APIhoh
     *
     * @param   page retrive this page
     * @param   perPage elements per page
     * @return      Returns a device representation
     * @see         eu.linksmart.local.sdk.LSLCDevice
     */
    public List<LSLCDevice> getDevices(int page, int perPage);
    /**
     * Retrieves specific device using filtering functionality of the Resource Catalog.
     *
     * @param   path JSON path for narrowing the search
     * @param   op  An Enum which specifies how the string comparison should be executed
     * @param   value You are looking for this value
     * @return      Returns a device representation
     * @see         eu.linksmart.local.sdk.LSLCDevice
     * @see         eu.linksmart.local.sdk.RCOperation
     */
    public LSLCDevice findDevice(String path, RCOperation op, String value);
    /**
     * Retrieves specific devices using filtering functionality of the Resource Catalog.
     * Similar to findDevice but with pagination support.
     *
     * @param   path JSON path for narrowing the search
     * @param   op  An Enum which specifies how the string comparison should be executed
     * @param   value You are looking for this value
     * @param   page retrive this page
     * @param   perPage elements per page

     * @return      Returns a device representation
     * @see         eu.linksmart.local.sdk.LSLCDevice
     * @see         eu.linksmart.local.sdk.RCOperation
     */
    public List<LSLCDevice> findDevices(String path,RCOperation op, String value,int page, int perPage);
    /**
     * Retrieves specific resource using filtering functionality of the Resource Catalog.
     *
     * @param   path JSON path for narrowing the search
     * @param   op  An Enum which specifies how the string comparison should be executed
     * @param   value You are looking for this value
     * @return      Returns a resource representation
     * @see         eu.linksmart.local.sdk.LSLCResource
     * @see         eu.linksmart.local.sdk.RCOperation
     */
    public LSLCResource findResource(String path, RCOperation op, String value);
    /**
     * Retrieves specific resources using filtering functionality of the Resource Catalog.
     * Similar to findResource, but with pagination support
     *
     * @param   path JSON path for narrowing the search
     * @param   op  An Enum which specifies how the string comparison should be executed
     * @param   value You are looking for this value
     * @param   page retrive this page
     * @param   perPage elements per page
     *
     * @return      Returns a resource representation
     * @see         eu.linksmart.local.sdk.LSLCResource
     * @see         eu.linksmart.local.sdk.RCOperation
     */
    public List<LSLCResource> findResources(String path, RCOperation op, String value,int page, int perPage);
}
