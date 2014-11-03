package eu.linksmart.local.sdk;

import java.util.List;

/**
 * Created by carlos on 24.10.14.
 */
public interface IDeviceManagement {

    public String add(LSLCDevice aDevice);
    public boolean delete(String deviceID);
    public boolean update(LSLCDevice aDevice,String deviceID);
    public LSLCDevice get(String deviceID);
    public List<LSLCDevice> getDevices(int page, int perPage);
    public LSLCDevice findDevice(String path, RCOperation op, String value);
    public List<LSLCDevice> findDevices(String path,RCOperation op, String value,int page, int perPage);
    public LSLCResource findResource(String path, RCOperation op, String value);
    public List<LSLCResource> findResources(String path, RCOperation op, String value,int page, int perPage);
}
