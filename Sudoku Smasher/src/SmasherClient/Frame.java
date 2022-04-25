package SmasherClient;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.*;

public class Frame {
    private int[][] board = new int[9][9];
    private final Client client;
    private final ArrayList<JTextField> boxes = new ArrayList<>();
    public Frame(Client client){
        this.client = client;
    }

    /**
     * Creates the entire GUI and adds all needed components for the Sudoku Puzzle.
     */
    public void createAndShowGUI() {
        final JFrame frame = new JFrame("Sudoku");

        final Border fieldBorder = BorderFactory.createLineBorder(Color.BLACK);

        Font font1 = new Font("SansSerif", Font.BOLD, 30);
        final JPanel mainGrid = new JPanel(new GridLayout(3, 0));
        mainGrid.setPreferredSize(new Dimension(700, 700));
        mainGrid.setBorder(new EmptyBorder(5,5,0,0));
        mainGrid.setBackground(Color.black);
        for (int i = 0; i < 9; ++i) {
            final JPanel grid = new JPanel(new GridLayout(3, 0));
            for (int j = 0; j < 9; j++) {
                final JTextField field = new JTextField(2);
                field.setDocument(new JTextFieldLimit(1));
                field.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if ( ((c < '1') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                            e.consume();  // if it's not a number, ignore the event
                        }
                    }
                });
                field.setFont(font1);
                boxes.add(field);
                field.setHorizontalAlignment(JTextField.CENTER); //Center text horizontally in the text field.
                field.setBorder(fieldBorder); //Add the colored border.
                grid.add(field);
            }
            grid.setBorder(new EmptyBorder(0,0,5,5));
            grid.setBackground(Color.black);
            mainGrid.add(grid);
        }

        final JPanel centeredGrid = new JPanel();
        centeredGrid.setLayout(new BoxLayout(centeredGrid, BoxLayout.PAGE_AXIS));
        centeredGrid.setBackground(new Color(0,120,0));
        centeredGrid.add(mainGrid);
        centeredGrid.setBorder(new EmptyBorder(10,10,10,10));

        final JPanel buttonSection = new JPanel(new GridLayout(1,3));
//        buttonSection.setLayout(new BoxLayout(buttonSection, BoxLayout.X_AXIS));
        buttonSection.setBorder(new EmptyBorder(5,5,0,5));
        buttonSection.setBackground(new Color(0,120,0));
        buttonSection.setPreferredSize(new Dimension(300, 100));

        final JButton uploadCsvButton = new UpLoadButton(boxes).getUploadCsvButton();
        uploadCsvButton.setBackground(Color.lightGray);
        uploadCsvButton.setPreferredSize(new Dimension(20,10));
        buttonSection.add(uploadCsvButton);

        final JButton submitBut = new SubmitButton(boxes, board, frame, client ).getSubmitBut();
        submitBut.setBackground(Color.pink);
        submitBut.setPreferredSize(new Dimension(100,10));
        buttonSection.add(submitBut);

        final JButton validBut = new ValidateButton(boxes, board, frame, client ).getSubmitBut();
        validBut.setBackground(new Color(173 ,216,230));
        validBut.setPreferredSize(new Dimension(100,10));
        buttonSection.add(validBut);

        centeredGrid.add(buttonSection);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(centeredGrid);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }



    public int[][] getSudokuMatrix() {
        return board;
    }
}