package model;

import jpcap.packet.Packet;

import java.io.*;
import java.util.List;

public class FileHandler {
    public static void saveToFile(File file) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            System.out.println("文件写入中");
            objectOutputStream.writeObject(Capturer.packetList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("文件写入结束");
    }

    public static void openFile(File file) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("文件读取中");
            Capturer.packetList = (List<Packet>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("文件读取结束");
    }
}
