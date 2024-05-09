package hu.bme.mit.RescueFramework;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JComboBox<String> jComboBox1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JSlider jSlider1;
    private JSpinner jSpinner1;
    private JSpinner jSpinner2;
    private PaintPanel paintPanel;

    public MainFrame() {
        // Init auto generated components
        initComponents();
        paintPanel.repaint();



    }

    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jComboBox1 = new JComboBox<>();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jLabel2 = new JLabel();
        jButton3 = new JButton();
        jButton4 = new JButton();
        jButton5 = new JButton();
        jSlider1 = new JSlider();
        jSpinner1 = new JSpinner();
        jSpinner2 = new JSpinner();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        paintPanel = new PaintPanel();


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MountainRescue");
        setMinimumSize(new Dimension(1200, 800));
        setSize(new Dimension(1200, 800));

        jPanel1.setPreferredSize(new Dimension(924, 33));

        jLabel1.setText("Map:");

        jComboBox1.setModel(new DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        jComboBox1.setFocusable(false);

        jButton1.setText("Start");
        //jButton1.setFocusCycleRoot(true);

        jButton2.setText("Stop");
        jButton3.setText("Step");

        jButton4.setText("Auto");
        jButton5.setText("Pause");

        jLabel2.setText("Saved:");
        jLabel7.setText("0");
        jLabel6.setText("Dead:");
        jLabel8.setText("0");





        jSlider1.setToolTipText("");
        jSlider1.setValue(20);


        jSpinner1.setModel(new SpinnerNumberModel(1, 1, null, 1));
        jSpinner1.setFocusable(false);

        jSpinner2.setModel(new SpinnerNumberModel(1, 1, null, 1));
        jSpinner2.setFocusable(false);

        jLabel3.setText("Helicopters:");

        jLabel5.setText("Troops:");

        jLabel4.setText("Autostep speed:");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSlider1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel7)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel8))
                                        .addComponent(jSlider1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton4)
                                                .addComponent(jLabel5)
                                                .addComponent(jSpinner2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton1)
                                                .addComponent(jButton2)
                                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel1)
                                                .addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel3)
                                                .addComponent(jButton3)
                                                .addComponent(jButton5)
                                                .addComponent(jLabel4)))
                                .addGap(13, 13, 13))
        );

        getContentPane().add(jPanel1, BorderLayout.PAGE_START);

        paintPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paintPanelMouseClicked(evt);
            }
        });
        getContentPane().add(paintPanel, BorderLayout.CENTER);

        pack();

    }
    private void paintPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paintPanelMouseClicked
        // Make the paintPanel handle the click event
        paintPanel.mouseClicked(evt.getX(), evt.getY());
    }
    public void refresh() {
        // Repaint cells and world objects
        paintPanel.repaint();
    }
}