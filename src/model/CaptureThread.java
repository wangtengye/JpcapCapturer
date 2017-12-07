package model;

import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.*;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class CaptureThread extends Thread {
    JpcapCaptor captor;
    DefaultTableModel tableModel;

    public CaptureThread() {
        try {
            captor = JpcapCaptor.openDevice(Capturer.devices[Capturer.selectedIndex], 65535, true, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CaptureThread().start();
    }

    public void setTableModel(DefaultTableModel defaultTableModel) {
        this.tableModel = defaultTableModel;
    }


    @Override
    public void run() {
        if (Capturer.openFromFile) {
            Capturer.packetList.forEach(this::dealPacket);
            Capturer.openFromFile = false;
        } else {
            while (true) {
                captor.processPacket(-1, new Receiver());
            }
        }
    }

    void dealPacket(Packet packet) {
        Capturer.total++;
        System.out.println(Capturer.total + "<-->" + packet);
        String src = "", dst = "", type = "other", length = packet.len + "";
        if (packet instanceof ARPPacket) {
            ARPPacket arpPacket = (ARPPacket) packet;
            src = arpPacket.getSenderProtocolAddress().toString();
            dst = arpPacket.getTargetProtocolAddress().toString();
            type = "ARP";
        } else if (packet instanceof IPPacket) {
            IPPacket ipPacket = (IPPacket) packet;
            src = ipPacket.src_ip.toString();
            dst = ipPacket.dst_ip.toString();
            if (packet instanceof TCPPacket) {
                type = "TCP";
            } else if (packet instanceof UDPPacket) {

                type = "UDP";
            } else if (packet instanceof ICMPPacket) {
                type = "ICMP";
            }
        }
        tableModel.addRow(new String[]{Capturer.total + "", src.substring(1), dst.substring(1), type, length});

    }

    class Receiver implements PacketReceiver {
        @Override
        public void receivePacket(Packet packet) {
            Capturer.packetList.add(packet);
            dealPacket(packet);
        }

    }
}



