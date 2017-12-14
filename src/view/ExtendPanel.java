package view;

import javax.swing.*;
import java.awt.*;

public class ExtendPanel extends JPanel {
    public JSplitPane mainPanel2;
    public JPanel panel3 = new JPanel();//new
    public JButton button1;
    public JButton button2;
    public static boolean expend = false;//new
    public static boolean expend2 = false;//new

    public ExtendPanel() {
        super(new BorderLayout());
        JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        jsp.setDividerLocation(150);
        jsp.setDividerSize(8);
        jsp.setOneTouchExpandable(true);
        jsp.setContinuousLayout(true);


        jsp.setLeftComponent(panel3);
        button1 = new JButton("IP");
        button2 = new JButton("TCP");


        button1.addActionListener(e -> {
            if (expend) {//折叠
                panel3.removeAll();
                BoxLayout layout = new BoxLayout(panel3, BoxLayout.Y_AXIS);
                panel3.setLayout(layout);
                panel3.add(button1);
                panel3.add(button2);
                if (expend2)
                    panel3.add(new JScrollPane());//packet信息

                //panel3.add()
                validate();
                panel3.repaint();
                expend = false;

            } else {//展开
                panel3.removeAll();
                panel3.add(button1);
                panel3.add(new JScrollPane());//IP层信息
                panel3.add(button2);
                if (expend2)
                    panel3.add(new JScrollPane());//TCP层信息

                //panel3.add()
                validate();
                panel3.repaint();
                expend = true;
            }
        });
        button2.addActionListener(e -> {
            if (expend2) {//展开时折叠
                panel3.removeAll();
                BoxLayout layout = new BoxLayout(panel3, BoxLayout.Y_AXIS);
                panel3.setLayout(layout);
                panel3.add(button1);
                if (expend)
                    panel3.add(new JScrollPane());//IP层信息
                panel3.add(button2);


                //panel3.add()
                validate();
                panel3.repaint();
                expend2 = false;

            } else {//折叠时展开
                panel3.removeAll();
                panel3.add(button1);
                if (expend)
                    panel3.add(new JScrollPane());//IP层信息
                panel3.add(button2);
                panel3.add(new JScrollPane());//TCP层信息

                //panel3.add()
                validate();
                panel3.repaint();
                expend2 = true;
            }

        });

        panel3.add(button1);
        panel3.add(button2);
        //panel3.add(new JScrollPane(area));
        BoxLayout layout = new BoxLayout(panel3, BoxLayout.Y_AXIS);
        panel3.setLayout(layout);
        jsp.setRightComponent(new JScrollPane());
        add(jsp);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("db");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ExtendPanel pane = new ExtendPanel();
        frame.setContentPane(pane);

        frame.setSize(800, 300);

        frame.setVisible(true);
    }
}
