package view;

import model.Capturer;

import javax.swing.*;
import java.awt.*;

public class ConfigFrame extends JFrame {
    public ConfigFrame() {
        super("配置");

        JLabel deviceLable = new JLabel("选择接口");
        JLabel filterLable = new JLabel("设置过滤器");
        JRadioButton modelButton = new JRadioButton("使用混杂模式");
        modelButton.setSelected(true);
        JTextField textField = new JTextField(15);
        JButton okButton = new JButton("确定");

        String[] devices = new String[Capturer.devices.length];
        for (int i = 0; i < Capturer.devices.length; i++) {
            devices[i] = Capturer.devices[i].description;
        }

        JComboBox<String> deviceBox = new JComboBox<>(devices);

        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.add(deviceLable);
        northPanel.add(deviceBox);

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(modelButton);
        centerPanel.add(filterLable);
        centerPanel.add(textField);

        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(okButton);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            Capturer.selectedIndex = deviceBox.getSelectedIndex();
            Capturer.promisc = modelButton.isSelected();
            Capturer.filter = textField.getText();
            setVisible(false);
        });
        setBounds(500, 200, 400, 150);
        setVisible(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public static void main(String[] args) {
        new ConfigFrame();
    }
}
