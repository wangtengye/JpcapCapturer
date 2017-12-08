package model.packetAnalyzer;

import jpcap.packet.IPPacket;

public class IPAnalyzer {
    public static int totalOfIPv4 = 0;
    public static int totalOfIPv6 = 0;

    public static void judgeIPVersion(IPPacket ipPacket) {
        if(ipPacket.version==4)
            totalOfIPv4++;
        else
            totalOfIPv6++;
    }
}
