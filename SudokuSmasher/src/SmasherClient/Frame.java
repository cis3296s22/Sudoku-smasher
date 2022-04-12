package SmasherClient;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Frame {
    private final int[][] sudokuMatrix = new int[9][9];
    private final Client client;
    public Frame(Client client){
        this.client = client;
    }
    public void createAndShowGUI() {

        ArrayList<JTextField> boxes = new ArrayList<>();
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

        final JButton submitBut = new JButton("Submit");
        submitBut.setBackground(Color.pink);
        submitBut.setPreferredSize(new Dimension(100,10));
        buttonSection.add(submitBut);

        centeredGrid.add(buttonSection);
        submitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowOffset = 0;
                int colOffset= 0;
                int row = 0;

                for(int i = 0; i < boxes.size(); i++){
                    JTextField box = boxes.get(i);
                    int num;
                    if(box.getText().equals("")){
                        num = 0;
                    }else{
                        num = Integer.parseInt(box.getText());
                    }

                    if(i % 27 == 0 && i != 0){
                        rowOffset += 3;
                        row = 0;
                        colOffset = 0;
                    }
                    else if(i % 9 == 0 && i != 0){
                        colOffset += 3;
                        row = 0;
                    }
                    else if((i % 3) == 0 && i != 0){
                        row += 1;
                    }
                    sudokuMatrix[row + rowOffset][(i % 3) + colOffset] = num;
                }
                client.sendPuzzle(sudokuMatrix);
                Debugger.showMatrix(sudokuMatrix);
            }
        });

        final JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(centeredGrid);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public int[][] getSudokuMatrix() {
        return sudokuMatrix;
    }
}
