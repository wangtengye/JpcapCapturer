package view;

import model.packetAnalyzer.ARPAnalyzer;
import model.packetAnalyzer.IPAnalyzer;
import model.packetAnalyzer.TCPAnalyzer;
import model.packetAnalyzer.UDPAnalyzer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class ChartFrame extends JFrame {
    public ChartFrame() {
        super("图表分析");

        getContentPane().add(getPiePanel(), BorderLayout.WEST);
        getContentPane().add(getBarPanel(), BorderLayout.CENTER);
        setBounds(300, 100, 1000, 550);

        setVisible(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public static void main(String[] args) {
        new ChartFrame();
    }

    Container getPiePanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("ipv4", IPAnalyzer.totalOfIPv4);
        dataset.setValue("ipv6", IPAnalyzer.totalOfIPv6);

        JFreeChart chart = ChartFactory.createPieChart(
                "IPv4&IPv6",  // chart title
                dataset,        // data
                true,           // include legend
                true,
                false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(360, 0));
        return chartPanel;

    }

    Container getBarPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(TCPAnalyzer.total, "Packet", "TCP");
        dataset.addValue(UDPAnalyzer.total, "Packet", "UDP");
        dataset.addValue(ARPAnalyzer.total, "Packet", "ARP");

        JFreeChart barChart = ChartFactory.createBarChart(
                "TCP&UDP&ARP",
                "Packet",
                "number",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        return chartPanel;
    }
}
