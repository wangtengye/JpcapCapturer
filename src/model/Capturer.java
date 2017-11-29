package model;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class Capturer {
    static NetworkInterface[] devices = JpcapCaptor.getDeviceList();
    public static int selectedIndex=5;
}
