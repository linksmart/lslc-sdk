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
    public List<LSLCDevice> getAllDevices();
}
