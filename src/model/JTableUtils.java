package model;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class JTableUtils {
    public static void setColumnName(DefaultTableModel defaultTableModel, String[] columnNames) {
        for (String name : columnNames)
            defaultTableModel.addColumn(name);
    }

    public static void setContent(DefaultTableModel defaultTableModel, Vector<Vector<String>> vector) {
        System.out.println(vector);
        vector.forEach(defaultTableModel::addRow);
    }
}
