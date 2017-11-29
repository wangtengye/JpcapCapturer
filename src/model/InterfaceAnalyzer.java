package model;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;

import java.util.Vector;

public class InterfaceAnalyzer {
    public static Vector<Vector<String>> getMessage() {
        NetworkInterface[] devices = Capturer.devices;
        Vector<Vector<String>> data = new Vector<>();

        for (NetworkInterface device : devices) {
            Vector<String> row = new Vector<>();
            row.add(device.name);
            row.add(device.description);
            StringBuilder macAddress = new StringBuilder();
            for (byte b : device.mac_address) {
                macAddress.append(Integer.toHexString(b & 0xff)).append(":");
            }
            row.add(macAddress.substring(0, macAddress.length() - 1));
            data.add(row);
        }

        return data;
    }

    public static void main(String[] args) {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        for (int i = 0; i < devices.length; i++) {
            //print out its name and description
            System.out.println(i + ": " + devices[i].name + "(" + devices[i].description + ")");

            //print out its datalink name and description
            System.out.println(" datalink: " + devices[i].datalink_name + "(" + devices[i].datalink_description + ")");

            //print out its MAC address
            System.out.print(" MAC address:");
            for (byte b : devices[i].mac_address)
                System.out.print(Integer.toHexString(b & 0xff) + ":");
            System.out.println();

            //print out its IP address, subnet mask and broadcast address
            for (NetworkInterfaceAddress a : devices[i].addresses)
                System.out.println(" address:" + a.address + " " + a.subnet + " " + a.broadcast);
        }

    }
}
