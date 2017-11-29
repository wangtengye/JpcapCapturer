package view;

import model.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PacketsPanel extends JPanel{
    DefaultTableModel packetModel=null;
    public PacketsPanel(){
        super(new BorderLayout());
        packetModel=new DefaultTableModel();
        JTable packetTable=new JTable(packetModel);
        JTableUtils.setColumnName(packetModel,new String[]{"rer","er"});
        JScrollPane pane=new JScrollPane(packetTable);
        add(pane);

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
