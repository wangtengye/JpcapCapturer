package view;

import model.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PacketsPanel extends JPanel {
    DefaultTableModel packetModel = null;

    public PacketsPanel() {
        super(new BorderLayout());
        packetModel = new DefaultTableModel();
        JTable packetTable = new JTable(packetModel);
        JTableUtils.setColumnName(packetModel, new String[]{"no", "source", "destination", " type", "length"});
        JScrollPane pane = new JScrollPane(packetTable);
        add(pane);

        packetTable.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                packetTable.scrollRectToVisible(packetTable.getCellRect(packetTable.getRowCount() - 1, 0, true));
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("db");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacketsPanel pane = new PacketsPanel();
        frame.setContentPane(pane);

        frame.setSize(800, 300);

        frame.setVisible(true);
    }
}
