package view;

import model.CaptureThread;
import model.Capturer;
import model.FileHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CapturerFrame extends JFrame implements ActionListener {
    JMenuBar bar;

    JMenu captureMenu;
    JMenu fileMenu;
    JMenu statMenu;
    JMenu chartMenu;

    JMenuItem interfaceItem;
    JMenuItem startItem;
    JMenuItem stopItem;
    JMenuItem openFileItem;
    JMenuItem saveFileItem;
    JMenuItem chartItem;

    Thread runningThread;

    public CapturerFrame() {
        super("抓包工具");
        bar = new JMenuBar();
        captureMenu = new JMenu("捕获");
        fileMenu = new JMenu("文件");
        statMenu = new JMenu("统计");
        chartMenu=new JMenu("查看图表");

        interfaceItem = new JMenuItem("查看接口");
        startItem = new JMenuItem("开始");
        stopItem = new JMenuItem("停止");
        openFileItem = new JMenuItem("打开");
        saveFileItem = new JMenuItem("保存");
        chartItem = new JMenuItem("查看图表");

        captureMenu.add(interfaceItem);
        captureMenu.add(startItem);
        captureMenu.add(stopItem);
        fileMenu.add(openFileItem);
        fileMenu.add(saveFileItem);
        bar.add(captureMenu);
        bar.add(fileMenu);
        bar.add(statMenu);
        setJMenuBar(bar);

        interfaceItem.addActionListener(this);
        startItem.addActionListener(this);
        stopItem.addActionListener(this);
        saveFileItem.addActionListener(this);
        openFileItem.addActionListener(this);

        setBounds(400, 100, 850, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CapturerFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interfaceItem) {
            new InterfaceFrame();
        } else if (e.getSource() == startItem) {
            Capturer.init();
            if (null != runningThread && runningThread.isAlive())
                runningThread.stop();
            getContentPane().removeAll();
            PacketsPanel packetsPanel = new PacketsPanel();
            getContentPane().add(packetsPanel);
            getContentPane().repaint();
            getContentPane().validate();

            CaptureThread captureThread = new CaptureThread();
            runningThread = captureThread;
            captureThread.setTableModel(packetsPanel.packetModel);
            captureThread.start();
        } else if (e.getSource() == stopItem) {
            if (null != runningThread && runningThread.isAlive())
                runningThread.stop();
        } else if (e.getSource() == saveFileItem) {
            JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(this);
            FileHandler.saveToFile(chooser.getSelectedFile());

        } else if (e.getSource() == openFileItem) {
            Capturer.openFromFile=true;
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(this);
            FileHandler.openFile(chooser.getSelectedFile());

        }
    }
}
