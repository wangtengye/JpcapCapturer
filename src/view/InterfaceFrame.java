package view;

import model.Capturer;
import model.InterfaceAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class InterfaceFrame extends JFrame {
    public InterfaceFrame() {
        super("网络接口");

        Vector<String> colunmNames = new Vector<>();
        colunmNames.add("名称");
        colunmNames.add("描述");
        colunmNames.add("mac地址");
        JTable interfaceTable = new JTable(InterfaceAnalyzer.getMessage(), colunmNames);
        JScrollPane coursePane = new JScrollPane(interfaceTable);
        add(coursePane);

        JButton button = new JButton("go");
        add(button, BorderLayout.SOUTH);
        button.addActionListener(event -> {
            Capturer.selectedIndex = interfaceTable.getSelectedRow();
            setVisible(false);
        });

        setBounds(500, 200, 700, 350);

        setVisible(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public static void main(String[] args) {
        new InterfaceFrame();
    }
}
