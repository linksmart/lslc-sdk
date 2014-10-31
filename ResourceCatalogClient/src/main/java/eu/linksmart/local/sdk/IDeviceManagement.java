package eu.linksmart.local.sdk;

import java.util.List;

/**
 * Created by carlos on 24.10.14.
 */
public interface IDeviceManagement {

    public boolean registerDevice(LSLCDevice aDevice);
    public boolean removeDevice(String deviceID);
    public LSLCDevice getDevice(String deviceID);
<<<<<<< HEAD
    public <List> java.util.ArrayList<LSLCDevice> getAllDevices();
=======
    public List<LSLCDevice> getAllDevices();
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1
}
