package model;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

import java.util.ArrayList;
import java.util.List;

public class Capturer {
    static NetworkInterface[] devices = JpcapCaptor.getDeviceList();
    public static int selectedIndex = 3;
    static int total = 0;
    static List<Packet> packetList = new ArrayList<>();
    public static boolean openFromFile = false;

    public static void init() {
        total = 0;
        if (!openFromFile)
            packetList.clear();

    }
}
