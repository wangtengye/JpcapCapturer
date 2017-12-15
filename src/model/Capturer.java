package model;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import model.packetAnalyzer.ARPAnalyzer;
import model.packetAnalyzer.IPAnalyzer;
import model.packetAnalyzer.TCPAnalyzer;
import model.packetAnalyzer.UDPAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class Capturer {
    public static int selectedIndex = 0;
    public static boolean promisc=true;
    public static String filter="";
    public static boolean openFromFile = false;
    public static NetworkInterface[] devices = JpcapCaptor.getDeviceList();
    static int total = 0;
    public static List<Packet> packetList = new ArrayList<>();

    public static void init() {
        total = 0;
        if (!openFromFile)
            packetList.clear();
        IPAnalyzer.totalOfIPv4=0;
        IPAnalyzer.totalOfIPv6=0;
        TCPAnalyzer.total=0;
        UDPAnalyzer.total=0;
        ARPAnalyzer.total=0;
    }
}
