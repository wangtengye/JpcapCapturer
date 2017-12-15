package model;

import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.*;
import model.packetAnalyzer.ARPAnalyzer;
import model.packetAnalyzer.IPAnalyzer;
import model.packetAnalyzer.TCPAnalyzer;
import model.packetAnalyzer.UDPAnalyzer;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class CaptureThread extends Thread {
    JpcapCaptor captor;
    DefaultTableModel tableModel;

    public CaptureThread() {
        try {
            captor = JpcapCaptor.openDevice(Capturer.devices[Capturer.selectedIndex], 65535, Capturer.promisc, 20);
            captor.setFilter(Capturer.filter, true);
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
        String src = "", dst = "", type = "other";
        int length = packet.len;
        if (packet instanceof ARPPacket) {
            ARPPacket arpPacket = (ARPPacket) packet;
            src = arpPacket.getSenderProtocolAddress().toString().substring(1);
            dst = arpPacket.getTargetProtocolAddress().toString().substring(1);
            ARPAnalyzer.total++;
            type = "ARP";
        } else if (packet instanceof IPPacket) {
            IPPacket ipPacket = (IPPacket) packet;
            IPAnalyzer.judgeIPVersion(ipPacket);
            if (ipPacket.src_ip != null)
                src = ipPacket.src_ip.toString().substring(1);
            if (ipPacket.dst_ip != null)
                dst = ipPacket.dst_ip.toString().substring(1);
            if (packet instanceof TCPPacket) {
                TCPAnalyzer.total++;
                type = "TCP";
            } else if (packet instanceof UDPPacket) {
                UDPAnalyzer.total++;
                type = "UDP";
            } else
                type = "ICMP";
        }
        tableModel.addRow(new Object[]{Capturer.total, src, dst, type, length});

    }

    class Receiver implements PacketReceiver {
        @Override
        public void receivePacket(Packet packet) {
            Capturer.packetList.add(packet);
            dealPacket(packet);
        }

    }
}



