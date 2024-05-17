package hu.bme.mit.RescueFramework;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JButton startButton;
    private JButton stopButton;
    private JButton stepButton;
    private JButton autoButton;
    private JButton pauseButton;
    //private JComboBox<String> jComboBox1;
    //private JLabel jLabel1;
    private JLabel savedCounterText;
    private JLabel helicopterText;
    private JLabel speedText;
    private JLabel troopsText;
    private JLabel deadCounterText;
    private JLabel savedCounterValue;
    private JLabel deadCounterValue;
    private JPanel jPanel1;
    private JSlider speedSlider;
    private JSpinner helicopterCount;
    private JSpinner troopsCount;
    private PaintPanel paintPanel;

    public MainFrame() {
        // Init auto generated components
        initComponents();
        paintPanel.repaint();



    }

    private void initComponents() {

        jPanel1 = new JPanel();
        //jLabel1 = new JLabel();
        //jComboBox1 = new JComboBox<>();
        startButton = new JButton();
        stopButton = new JButton();
        savedCounterText = new JLabel();
        stepButton = new JButton();
        autoButton = new JButton();
        pauseButton = new JButton();
        speedSlider = new JSlider();
        helicopterCount = new JSpinner();
        troopsCount = new JSpinner();
        helicopterText = new JLabel();
        speedText = new JLabel();
        troopsText = new JLabel();
        deadCounterText = new JLabel();
        savedCounterValue = new JLabel();
        deadCounterValue = new JLabel();
        paintPanel = new PaintPanel();


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MountainRescue");
        setMinimumSize(new Dimension(1400, 800));
        setSize(new Dimension(1400, 800));

        jPanel1.setPreferredSize(new Dimension(924, 33));

        //jLabel1.setText("Map:");

        //jComboBox1.setModel(new DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        //jComboBox1.setFocusable(false);

        startButton.setText("Start");
        startButton.addActionListener(event -> initSimulation());

        stopButton.setText("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(event -> stopSimulation());
        stepButton.setText("Step");
        stepButton.setEnabled(false);
        stepButton.addActionListener(event -> RescueFramework.getSimulator().step());

        autoButton.setText("Auto");
        autoButton.setEnabled(false);
        autoButton.addActionListener(event -> {
            RescueFramework.getSimulator().start();
            pauseButton.setEnabled(true);
            autoButton.setEnabled(false);
        });
        pauseButton.setText("Pause");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(event -> {
            RescueFramework.getSimulator().stop();
            pauseButton.setEnabled(false);
            autoButton.setEnabled(true);
        });

        savedCounterText.setText("Saved:");
        savedCounterValue.setText("0");
        deadCounterText.setText("Dead:");
        deadCounterValue.setText("0");

        speedSlider.setMinimum(0);
        speedSlider.setMajorTickSpacing(250);
        speedSlider.setMaximum(3500);
        speedSlider.setMinorTickSpacing(250);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(3000);
        speedSlider.addChangeListener(evt -> speedSliderStateChanged());

        helicopterCount.setModel(new SpinnerNumberModel(0, 0, null, 1));
        helicopterCount.setFocusable(false);

        troopsCount.setModel(new SpinnerNumberModel(0, 0, null, 1));
        troopsCount.setFocusable(false);

        helicopterText.setText("Helicopters:");

        troopsText.setText("Troops:");

        speedText.setText("Autostep speed:");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                //.addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                //.addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

                                .addComponent(helicopterText)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(helicopterCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(troopsText)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(troopsCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stepButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(autoButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pauseButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(speedText)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(speedSlider, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(savedCounterText)
                                .addComponent(savedCounterValue)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(deadCounterText)
                                .addComponent(deadCounterValue)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(savedCounterText)
                                                .addComponent(savedCounterValue)
                                                .addComponent(deadCounterText)
                                                .addComponent(deadCounterValue))
                                        .addComponent(speedSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(autoButton)
                                                .addComponent(troopsText)
                                                .addComponent(troopsCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(startButton)
                                                .addComponent(stopButton)
                                                //.addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                //.addComponent(jLabel1)
                                                .addComponent(helicopterCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(helicopterText)
                                                .addComponent(stepButton)
                                                .addComponent(pauseButton)
                                                .addComponent(speedText)))
                                .addGap(13, 13, 13))
        );

        getContentPane().add(jPanel1, BorderLayout.PAGE_START);

        paintPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paintPanelMouseClicked(evt);
                refresh();
            }
        });
        getContentPane().add(paintPanel, BorderLayout.CENTER);

        pack();

    }

    private void speedSliderStateChanged() {
        RescueFramework.getSimulator().setSpeed(4000-speedSlider.getValue());
    }

    private void stopSimulation() {
        RescueFramework.endSimulation();
        helicopterCount.setEnabled(true);
        troopsCount.setEnabled(true);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        stepButton.setEnabled(false);
        autoButton.setEnabled(false);
        pauseButton.setEnabled(false);
    }

    private void initSimulation() {
        if((int) troopsCount.getValue() == 0 && (int) helicopterCount.getValue() == 0){
            JOptionPane.showMessageDialog(this, "Please set the number of troops and helicopters!");
        }
        else
        {
            RescueFramework.newSimulation((int) troopsCount.getValue(), (int) helicopterCount.getValue());
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            stepButton.setEnabled(true);
            autoButton.setEnabled(true);
            troopsCount.setEnabled(false);
            helicopterCount.setEnabled(false);
        }
    }

    private void paintPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paintPanelMouseClicked
        // Make the paintPanel handle the click event
        paintPanel.mouseClicked(evt.getX(), evt.getY());
    }
    public void refresh() {
        // Repaint cells and world objects
        savedCounterValue.setText(Integer.toString(RescueFramework.getSimulator().getSavedCount()));
        deadCounterValue.setText(Integer.toString(RescueFramework.getSimulator().getDeadCount()));

        this.revalidate();
        this.repaint();
    }
}