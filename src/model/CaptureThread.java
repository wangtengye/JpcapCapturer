package model;

import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

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

    public void setTableModel(DefaultTableModel defaultTableModel) {
        this.tableModel = defaultTableModel;
    }

    @Override
    public void run() {
        while (true) {
            captor.processPacket(-1, new Receiver());
        }
    }

    public static void main(String[] args) {
        new CaptureThread().start();
    }

    class Receiver implements PacketReceiver {

        @Override
        public void receivePacket(Packet packet) {
            tableModel.addRow(new String[]{packet.toString(), "3"});

            System.out.println("-------------");
            if (packet instanceof IPPacket) {

                System.out.println(((IPPacket) packet).src_ip);
                System.out.println(((IPPacket) packet).dst_ip);

            } else
                System.out.println(packet);
        }
    }
}



