package view;

import jpcap.packet.Packet;
import model.Capturer;
import model.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PacketsPanel extends JPanel {
    DefaultTableModel packetModel = null;

    public PacketsPanel() {
        super(new BorderLayout());

        packetModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                if ((column >= 0) && (column < getColumnCount()))
                    if (getRowCount() > 0)
                        return getValueAt(0, column).getClass();
                return Object.class;
            }
        };
        JTable packetTable = new JTable(packetModel);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(packetModel);
        packetTable.setRowSorter(sorter);
        JTableUtils.setColumnName(packetModel, new String[]{"no", "source", "destination", " type", "length"});
        JScrollPane pane = new JScrollPane(packetTable);
        add(pane);

        JPanel filterPanel = new JPanel();
        JTextField filterText = new JTextField(57);
        JButton button = new JButton("输入过滤条件过滤");
        filterPanel.add(filterText);
        filterPanel.add(button, BorderLayout.EAST);
        button.addActionListener(e -> {
            sorter.setRowFilter(RowFilter.regexFilter(filterText.getText().toUpperCase()));
        });

        add(filterPanel, BorderLayout.NORTH);
        packetTable.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                packetTable.scrollRectToVisible(packetTable.getCellRect(packetTable.getRowCount() - 1, 0, true));
            }
        });

        packetTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index=(int)packetTable.getValueAt(packetTable.getSelectedRow(),0)-1;
                System.out.println("==index=="+index);
                Packet selectedPacket = Capturer.packetList.get(index);
                System.out.println(selectedPacket);
                ExtendPanel.analyze(selectedPacket);
            }
        });
        setPreferredSize(new Dimension(0, 250));

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
