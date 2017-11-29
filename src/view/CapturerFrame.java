package view;

import model.CaptureThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CapturerFrame extends JFrame implements ActionListener {
    JMenuBar bar;

    JMenu captureMenu;
    JMenu fileMenu;
    JMenu statMenu;

    JMenuItem interfaceItem;
    JMenuItem startItem;
    JMenuItem stopItem;
    JMenuItem openFileItem;
    JMenuItem saveFileItem;

    Thread runningThread;

    public CapturerFrame() {
        super("抓包工具");
        bar = new JMenuBar();
        captureMenu = new JMenu("捕获");
        fileMenu = new JMenu("文件");
        statMenu = new JMenu("统计");
        interfaceItem = new JMenuItem("查看接口");
        startItem = new JMenuItem("开始");
        stopItem = new JMenuItem("停止");
        openFileItem = new JMenuItem("打开文件");
        saveFileItem = new JMenuItem("保存");

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
        }
    }
}
