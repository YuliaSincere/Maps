
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class MainForm {
    private JButton DeleteButton;
    private JButton calculateButton;
    private JButton addButton;
    private JPanel MainForm;
    private JTable TableOfPoints;

    double lon1=0,lon2=0,lat1=0,lat2=0,result=0;

    public MainForm() {
        Object[][] array =
                {{"Шанхай", 110.0, -80.0},
                        {"Москва", 130.0, -40.0},
                        {"Лос Анжелес", 40.0, 50.0},
                };

        Object[] columnsHeader = new String[]{"Имя", "Долгота",
                "Широта"};
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Double.class;
                    case 2:
                        return Double.class;
                    default:
                        return Object.class;
                }
            }
        };


        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = TableOfPoints.getSelectedRow();
                model.removeRow(idx);

            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame form2 = new JFrame("Calculate");
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension dimension = toolkit.getScreenSize();
                form2.setBounds(dimension.width / 2 - 250, dimension.height / 2 - 150, 600, 400);
                form2.setVisible(true);
                String[][] items = {};
                Container content = form2.getContentPane();
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
                JComboBox PointComboBox1 = new JComboBox();
                JComboBox PointComboBox2 = new JComboBox();
                for (int i = 0; i < TableOfPoints.getRowCount(); i++) {
                    PointComboBox1.addItem(model.getValueAt(i, 0));
                }
                for (int i = 0; i < TableOfPoints.getRowCount(); i++) {
                    PointComboBox2.addItem(model.getValueAt(i, 0));
                }
                PointComboBox1.setSize(100,100);
                content.add(PointComboBox1);
                content.add(PointComboBox2);
                JRadioButton euclidean = new JRadioButton();
                JRadioButton manhattan = new JRadioButton();
                ButtonGroup calcMode = new ButtonGroup();
                calcMode.add(euclidean);
                calcMode.add(manhattan);
                euclidean.setText("Euclidean");
                manhattan.setText("Manhattan");
                content.add(euclidean);
                content.add(manhattan);

                JButton calcBtn = new JButton();
                calcBtn.setText("Calc");
                content.add(calcBtn);
                calcBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (euclidean.isSelected()) {
                            String selectItem1 = (String) PointComboBox1.getSelectedItem();
                            String selectItem2 = (String) PointComboBox2.getSelectedItem();
                            for (int i = 0; i < TableOfPoints.getRowCount(); i++) {
                                if (TableOfPoints.getValueAt(i, 0) == selectItem1) {
                                    lat1 = (double) model.getValueAt(i, 1);
                                    lon1 = (double) model.getValueAt(i, 2);
                                }
                                if (TableOfPoints.getValueAt(i, 0) == selectItem2) {
                                    lat2 = (double) model.getValueAt(i, 1);
                                    lon2 = (double) model.getValueAt(i, 2);
                                }
                            }
                            result = Math.sqrt(Math.pow(lon2 - lon1, 2) + Math.pow(lat2 - lat1, 2));
                            if (result != 0) {
                                JOptionPane.showMessageDialog(null, "Result is:" + result, "Result", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error404", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } else if (manhattan.isSelected()) {
                            String selectItem1 = (String) PointComboBox1.getSelectedItem();
                            String selectItem2 = (String) PointComboBox2.getSelectedItem();
                            for (int i = 0; i < TableOfPoints.getRowCount(); i++) {
                                if (TableOfPoints.getValueAt(i, 0) == selectItem1) {
                                    lat1 = (double) model.getValueAt(i, 1);
                                    lon1 = (double) model.getValueAt(i, 2);
                                }
                                if (TableOfPoints.getValueAt(i, 0) == selectItem2) {
                                    lat2 = (double) model.getValueAt(i, 1);
                                    lon2 = (double) model.getValueAt(i, 2);
                                }
                            }
                            result = Math.abs(lon2 - lon1) + Math.abs(lat2 - lat1);
                            if (result != 0) {
                                JOptionPane.showMessageDialog(null, "Result is:" + result, "Result", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error404", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            /*throw new IllegalArgumentException();*/
                        }

                    }
                });
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = TableOfPoints.getSelectedRow();
                model.insertRow(idx + 1, new Object[]{
                        "Имя" + String.valueOf(TableOfPoints.getRowCount()),
                        0.0, 0.0});
            }
        });


        model.setColumnIdentifiers(columnsHeader);
        for (int i = 0; i < array.length; i++)
            model.addRow(array[i]);

        TableOfPoints.setModel(model);
        TableOfPoints.setColumnSelectionAllowed(true);
        TableOfPoints.setVisible(true);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new MainForm().MainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width/2 - 250,dimension.height/2 - 150,600,400);
        frame.setVisible(true);

    }


}

