package ui;

import database.DatabaseConnector;
import database.MongoAsyncDBConnector;
import database.MongoDBConnector;
import database.MySQLConnector;
import generators.SampleDataFactory;
import generators.SampleDataGenerator;
import sampledata.SampleDataLevelOne;
import sampledata.SampleDataLevelThree;
import sampledata.SampleDataLevelTwo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Demo extends JFrame {

    private JFrame frame;

    private JPanel dataLayout, controlLayout;
    private JLabel mysqlLabel, mongoLabel;
    private JTextArea mysqlLog, mongoLog;
    private JLabel mysqlSpeedLabel, mongoSpeedLabel;

    private JSlider complexitySlider;
    private JButton start, stop;

    private SampleDataGenerator generator;

    private double mysqlSpeed, mongoSpeed;

    public Demo() {
        super("SQL/Document-Oriented Benchmark Test");

        // Add our data prototypes
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelOne.class);
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelTwo.class);
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelThree.class);


        // Create our generator
        generator = new SampleDataGenerator();

        // Add our InsertionThreads with their respective connectors
        DatabaseConnector mySQLConnector = new MySQLConnector();
        DatabaseConnector mongoConnector = new MongoAsyncDBConnector();

        generator.addInsertionThread("mysql", mySQLConnector);
        generator.addInsertionThread("mongodb", mongoConnector);

        generator.setComplexity(1);


        setupGui();


        Thread logger = new Thread(new Runnable() {

            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mysqlLog.setText(mySQLConnector.getLog());
                    mysqlLog.setCaretPosition(mysqlLog.getDocument().getLength());
                    mongoLog.setText(mongoConnector.getLog());
                    mongoLog.setCaretPosition(mongoLog.getDocument().getLength());

                    mysqlSpeed = generator.getInsertionThread("mysql").getInsertionsPerSecond();
                    mongoSpeed = generator.getInsertionThread("mongodb").getInsertionsPerSecond();

                    mysqlSpeedLabel.setText(String.valueOf(mysqlSpeed) + " insertions/second");
                    mongoSpeedLabel.setText(String.valueOf(mongoSpeed) + " insertions/second");
                }
            }
        });

        logger.start();


    }

    private void setupGui() {

        frame = new JFrame("SQL/Document-Oriented Benchmark Test");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        dataLayout = new JPanel(new GridLayout(0, 2, 5, 5));

        mysqlLabel = new JLabel("MySQL");
        mongoLabel = new JLabel("MongoDB");

        mysqlLog = new JTextArea();
        mysqlLog.setRows(20);
        mysqlLog.setLineWrap(true);
        JScrollPane mysqlScrollPane = new JScrollPane( mysqlLog );
        mysqlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mongoLog = new JTextArea();
        mongoLog.setRows(20);
        mongoLog.setLineWrap(true);
        JScrollPane mongoLogScrollPane = new JScrollPane( mongoLog );
        mongoLogScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        mysqlSpeedLabel = new JLabel("__ insertions/second");
        mongoSpeedLabel = new JLabel("__ insertions/second");

        dataLayout.add(mysqlLabel);
        dataLayout.add(mongoLabel);
        //dataLayout.add(mysqlLog);
        dataLayout.add(mysqlScrollPane);
        dataLayout.add(mongoLogScrollPane);
        //dataLayout.add(mongoLog);
        dataLayout.add(mysqlSpeedLabel);
        dataLayout.add(mongoSpeedLabel);


        controlLayout = new JPanel();
        complexitySlider = new JSlider();
        complexitySlider.setMaximum(3);
        complexitySlider.setMinimum(1);
        complexitySlider.setPaintTicks(true);
        complexitySlider.setPaintLabels(true);
        complexitySlider.setValue(1);
        complexitySlider.setMajorTickSpacing(1);
        complexitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                generator.setComplexity(complexitySlider.getValue());
            }
        });


        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                generator.start();
            }
        });

        stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                generator.stop();
            }
        });


        controlLayout.add(complexitySlider);
        controlLayout.add(start);
        controlLayout.add(stop);


        frame.add(dataLayout, BorderLayout.CENTER);
        frame.add(controlLayout, BorderLayout.SOUTH);
        frame.setVisible(true);



    }

    public static void main(String[] args) {
        new Demo();
    }

}
