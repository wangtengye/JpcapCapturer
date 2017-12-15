package view;

import jpcap.packet.*;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;

public class ExtendPanel extends JPanel {
    public JSplitPane mainPanel2;
    public JPanel panel3 = new JPanel();//new
    public JButton button1;
    public JButton button2;
    public static boolean expend = false;//new
    public static boolean expend2 = false;//new

    public static JTextArea Iarea = new JTextArea();
    public static JTextArea Tarea = new JTextArea();
    public static JTextArea Parea = new JTextArea();
    public static StringBuffer sb = new StringBuffer();
    public static StringBuffer Ehex = new StringBuffer();//new
    public static StringBuffer Thex = new StringBuffer();
    public static StringBuffer Ahex = new StringBuffer();
    public static StringBuffer Ihex = new StringBuffer();
    public static StringBuffer Fhex = new StringBuffer();
    public static StringBuffer Headerhex = new StringBuffer();
    public static StringBuffer Datahex = new StringBuffer();
    public static StringBuffer Packethex = new StringBuffer();

    public ExtendPanel() {
        super(new BorderLayout());
        JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        jsp.setDividerLocation(150);
        jsp.setDividerSize(8);
        jsp.setOneTouchExpandable(true);
        jsp.setContinuousLayout(true);

        Ihex.append("null");
        Thex.append("null");
        Packethex.append("null");
        Iarea.setText(Ihex.toString());
        //Iarea.setEditable(false);
        //Iarea.setLineWrap(true);
        Tarea.setText(Thex.toString());
        //Tarea.setEditable(false);
        //Tarea.setLineWrap(true);
        Parea.setText(Packethex.toString());
        Parea.setLineWrap(true);


        jsp.setLeftComponent(panel3);
        button1 = new JButton("  IP  ");
        button2 = new JButton("TCP");


        button1.addActionListener(e -> {

            if (expend) {//折叠
                panel3.removeAll();
                button1.setMaximumSize(new Dimension(2000, 30));
                button2.setMaximumSize(new Dimension(2000, 30));
                BoxLayout layout = new BoxLayout(panel3, BoxLayout.Y_AXIS);
                panel3.setLayout(layout);
                panel3.add(button1);
                panel3.add(button2);
                if (expend2)
                    panel3.add(new JScrollPane(Tarea));//tcp信息

                //panel3.add()
                validate();
                panel3.repaint();
                expend = false;

            } else {//展开
                panel3.removeAll();
                button1.setMaximumSize(new Dimension(2000, 30));
                button2.setMaximumSize(new Dimension(2000, 30));
                panel3.add(button1);
                panel3.add(new JScrollPane(Iarea));//IP层信息
                panel3.add(button2);
                if (expend2)
                    panel3.add(new JScrollPane(Tarea));//TCP层信息

                //panel3.add()
                validate();
                panel3.repaint();
                expend = true;
            }

        });
        button2.addActionListener(e -> {

            if (expend2) {//展开时折叠
                panel3.removeAll();
                button1.setMaximumSize(new Dimension(2000, 30));
                button2.setMaximumSize(new Dimension(2000, 30));
                BoxLayout layout = new BoxLayout(panel3, BoxLayout.Y_AXIS);
                panel3.setLayout(layout);
                panel3.add(button1);
                if (expend)
                    panel3.add(new JScrollPane(Iarea));//IP层信息
                panel3.add(button2);


                //panel3.add()
                validate();
                panel3.repaint();
                expend2 = false;

            } else {//折叠时展开
                panel3.removeAll();
                button1.setMaximumSize(new Dimension(2000, 30));
                button2.setMaximumSize(new Dimension(2000, 30));
                panel3.add(button1);
                if (expend)
                    panel3.add(new JScrollPane(Iarea));//IP层信息
                panel3.add(button2);
                panel3.add(new JScrollPane(Tarea));//TCP层信息

                //panel3.add()
                validate();
                panel3.repaint();
                expend2 = true;
            }

        });
        button1.setMaximumSize(new Dimension(1000, 30));
        button2.setMaximumSize(new Dimension(1000, 30));
        panel3.add(button1);
        panel3.add(button2);
        //panel3.add(new JScrollPane(area));
        BoxLayout layout = new BoxLayout(panel3, BoxLayout.Y_AXIS);
        panel3.setLayout(layout);
        jsp.setRightComponent(new JScrollPane(Parea));
        add(jsp);

    }

    static void analyze(Packet packet) {
        Ihex.delete(0, Ihex.length());
        Thex.delete(0, Thex.length());
        Headerhex.delete(0, Headerhex.length());
        Datahex.delete(0, Datahex.length());
        Packethex.delete(0, Packethex.length());

        sb.append("------包分析-------\n");
        sb.append("Captured Length:" + packet.caplen + " byte\n");
        sb.append("Length of this Packet:" + packet.len + " byte\n");
        sb.append("Header:" + packet.header + "\n");
        sb.append("Length of Header:" + packet.header.length + " byte\n");
        sb.append("Data:" + packet.data + "\n");
        sb.append("Length of Data:" + packet.data.length + " byte\n");
        sb.append("---Ethernet头部信息---\n");
        //Ehex.append("---Ethernet头部信息---\n");
        Fhex.append(packet.toString() + "\n");
        DatalinkPacket dPacket = packet.datalink;

        int fg1 = 0;
        for (byte d : packet.header) {
            fg1++;
            if (fg1 < packet.header.length) {
                Headerhex.append(Integer.toHexString(d & 0xff) + " ");
            } else {
                Headerhex.append(Integer.toHexString(d & 0xff) + " ");
            }
        }

        int fg = 0;
        for (byte d : packet.data) {
            fg++;
            if (fg < packet.data.length) {
                Datahex.append(Integer.toHexString(d & 0xff) + " ");
            } else {
                Datahex.append(Integer.toHexString(d & 0xff) + " ");
            }
        }

        if (dPacket instanceof EthernetPacket) {                //分析以太网帧
            EthernetPacket ePacket = (EthernetPacket) dPacket;
            sb.append("src_mac:");
            int flag1 = 0;
            for (byte b : ePacket.src_mac) {
                flag1++;
                if (flag1 < ePacket.src_mac.length) {
                    sb.append(Integer.toHexString(b & 0xff) + ":");
                    Ehex.append(Integer.toHexString(b & 0xff));
                } else {
                    sb.append(Integer.toHexString(b & 0xff) + "\n");
                    Ehex.append(Integer.toHexString(b & 0xff));
                }
            }
            sb.append("dst_mac:");
            int flag2 = 0;
            for (byte b : ePacket.dst_mac) {
                flag2++;
                if (flag2 < ePacket.dst_mac.length) {
                    sb.append(Integer.toHexString(b & 0xff) + ":");
                    Ehex.append(Integer.toHexString(b & 0xff));
                } else {
                    sb.append(Integer.toHexString(b & 0xff) + "\n");
                    Ehex.append(Integer.toHexString(b & 0xff));
                }
            }
            sb.append("frametype:" + Integer.toHexString(ePacket.frametype & 0xffff) + "\n");
            Ehex.append(Integer.toHexString(ePacket.frametype & 0xffff));
            Ehex.append("\n");
            sb.append("------------------\n");
        } else {
            sb.append(dPacket + "\n");
            sb.append("------------------\n");
        }
        if (packet instanceof ARPPacket) {               //分析ARP协议
            sb.append("---ARP---\n");
            ARPPacket aPacket = (ARPPacket) packet;
            sb.append("硬件类型：" + aPacket.hardtype + "\n");
            sb.append("协议类型：" + aPacket.prototype + "\n");
            sb.append("硬件地址长度：" + aPacket.hlen + "\n");
            sb.append("协议地址长度：" + aPacket.plen + "\n");
            sb.append("Operation：" + aPacket.operation + "\n");
            sb.append("发送者硬件地址：" + aPacket.sender_hardaddr + "\n");
            sb.append("发送者协议地址：" + aPacket.sender_protoaddr + "\n");
            sb.append("目标硬件地址：" + aPacket.target_hardaddr + "\n");
            sb.append("目标协议地址：" + aPacket.target_protoaddr + "\n");
            sb.append("------------------\n");
        }
        if (packet instanceof ICMPPacket) {          //分析ICMP协议
            sb.append("---ICMP---\n");
            ICMPPacket iPacket = (ICMPPacket) packet;
            sb.append("ICMP_TYPE:" + iPacket.type + "\n");
            sb.append("由于ICMP格式种类繁多，故省去不分析\n");
            sb.append("------------------\n");
        }
        if (packet instanceof IPPacket) {        //分析IP

            IPPacket iPacket = (IPPacket) packet;
            Ihex.append("---IP版本: " + iPacket.version + " ---\n");
            Ihex.append(Integer.toHexString(iPacket.version & 0xf));
            if (iPacket.version == 4) {                //分析IPv4协议
                Ihex.append("Type of service:" + iPacket.rsv_tos + "\n");
                Ihex.append("Priprity:" + iPacket.priority + "\n");
                Ihex.append("Packet Length:" + iPacket.length + "\n");
                Ihex.append("Identification:" + iPacket.ident + "\n");
                Ihex.append("Don't Frag? " + iPacket.dont_frag + "\n");
                Ihex.append("More Frag? " + iPacket.more_frag + "\n");
                Ihex.append("Frag Offset:" + iPacket.offset + "\n");
                Ihex.append("Time to Live:" + iPacket.hop_limit + "\n");
                Ihex.append("Protocol:" + iPacket.protocol + "        (TCP = 6; UDP = 17)\n");
                Ihex.append("Source address:" + iPacket.src_ip.toString() + "\n");
                Ihex.append("Destination address:" + iPacket.dst_ip.toString() + "\n");
                Ihex.append("Options:" + iPacket.option + "\n");
                //sb.append("------------------\n");
            }
        } else {
            Ihex.append("null");
        }
        if (packet instanceof UDPPacket) {      //分析UDP协议
            sb.append("---UDP---\n");
            UDPPacket uPacket = (UDPPacket) packet;
            sb.append("Source Port:" + uPacket.src_port + "\n");
            sb.append("Destination Port:" + uPacket.dst_port + "\n");
            sb.append("Length:" + uPacket.length + "\n");
            sb.append("------------------\n");
            if (uPacket.src_port == 53 || uPacket.dst_port == 53) {  //分析DNS协议
                sb.append("---DNS---\n");
                sb.append("此包已抓获，分析略...\n");
                sb.append("------------------\n");
            }
        }
        if (packet instanceof TCPPacket) {//分析TCP协议

            Thex.append("---TCP---\n");
            TCPPacket tPacket = (TCPPacket) packet;
            Thex.append("Source Port:" + tPacket.src_port + "\n");
            Thex.append("Destination Port:" + tPacket.dst_port + "\n");
            Thex.append("Sequence Number:" + tPacket.sequence + "\n");
            Thex.append("Acknowledge Number:" + tPacket.ack_num + "\n");
            Thex.append("URG:" + tPacket.urg + "\n");
            Thex.append("ACK:" + tPacket.ack + "\n");
            Thex.append("PSH:" + tPacket.psh + "\n");
            Thex.append("RST:" + tPacket.rst + "\n");
            Thex.append("SYN:" + tPacket.syn + "\n");
            Thex.append("FIN:" + tPacket.fin + "\n");
            Thex.append("Window Size:" + tPacket.window + "\n");
            Thex.append("Urgent Pointer:" + tPacket.urgent_pointer + "\n");
            Thex.append("Option:" + tPacket.option + "\n");
            //Thex.append("------------------\n");
            if (tPacket.src_port == 80 || tPacket.dst_port == 80) {     //分析HTTP协议
                sb.append("---HTTP---\n");
                byte[] data = tPacket.data;
                if (data.length == 0) {
                    sb.append("此为不带数据的应答报文！\n");
                } else {
                    if (tPacket.src_port == 80) {                 //接受HTTP回应
                        String str = null;
                        try {
                            String str1 = new String(data, "UTF-8");
                            if (str1.contains("HTTP/1.1")) {
                                str = str1;
                            } else {
                                String str2 = new String(data, "GB2312");
                                if (str2.contains("HTTP/1.1")) {
                                    str = str2;
                                } else {
                                    String str3 = new String(data, "GBK");
                                    if (str3.contains("HTTP/1.1")) {
                                        str = str3;
                                    } else {
                                        str = new String(data, "Unicode");
                                    }
                                }
                            }
                            sb.append(str + "\n");
                        } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (tPacket.dst_port == 80) {
                        try {
                            String str = new String(data, "ASCII");
                            sb.append(str);
                        } catch (Exception e) {
// TODO: handle exception
                        }
                    }
                }
            }
        } else {
            Thex.append("null");
        }

        System.out.println(Ihex);
        System.out.println(Thex);
        Packethex.append(Headerhex);
        Packethex.append(Datahex);
        ExtendPanel.Iarea.setText(Ihex.toString());
        ExtendPanel.Tarea.setText(Thex.toString());
        ExtendPanel.Parea.setText(Packethex.toString());


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("db");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ExtendPanel pane = new ExtendPanel();
        frame.setContentPane(pane);

        frame.setSize(800, 300);

        frame.setVisible(true);
    }
}
