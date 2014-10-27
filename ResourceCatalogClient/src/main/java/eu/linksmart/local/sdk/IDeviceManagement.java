package eu.linksmart.local.sdk;

/**
 * Created by carlos on 24.10.14.
 */
public interface IDeviceManagement {

    public boolean registerDevice(LSLCDevice aDevice);
    public boolean removeDevice(String deviceID);
    public LSLCDevice getDevice(String deviceID);
}