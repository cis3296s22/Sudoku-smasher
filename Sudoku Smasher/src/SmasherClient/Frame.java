package SmasherClient;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Frame {

    public static void createAndShowGUI() {
        int[][] sudokuMatrix = new int[9][9];
        ArrayList<JTextField> boxes = new ArrayList<JTextField>();
        final Border fieldBorder = BorderFactory.createLineBorder(Color.BLACK);

        final JPanel mainGrid = new JPanel(new GridLayout(3, 0));
        for (int i = 0; i < 9; ++i) {
            final JPanel grid = new JPanel(new GridLayout(3, 0));
            for (int j = 0; j < 9; j++) {
                final JTextField field = new JTextField(2);
                boxes.add(field);
                field.setHorizontalAlignment(JTextField.CENTER); //Center text horizontally in the text field.
                field.setBorder(fieldBorder); //Add the colored border.
                grid.add(field);
            }
            grid.setBorder(new EmptyBorder(0,0,5,5));
            mainGrid.add(grid);
        }

        final JPanel centeredGrid = new JPanel(new GridBagLayout());
        centeredGrid.add(mainGrid);

        final JButton submitBut = new JButton("Submit");
        centeredGrid.add(submitBut);
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
                int size = sudokuMatrix.length;
                for(int r = 0 ; r < size ; r++) {
                    for (int c = 0; c < size; c++) {
                        System.out.print(sudokuMatrix[r][c] + " ");
                    }
                    System.out.println();
                }
            }
        });

        final JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(centeredGrid);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
