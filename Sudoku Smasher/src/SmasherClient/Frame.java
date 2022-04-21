package SmasherClient;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.*;

public class Frame {
    private final int[][] sudokuMatrix = new int[9][9];
    private final Client client;

    private final String MEDIA_ROOT = "data/";
    private final String MEDIA_INPUT_ROOT = MEDIA_ROOT + "input/";
    private final String MEDIA_OUTPUT_ROOT = MEDIA_ROOT + "output/";

    private final ArrayList<JTextField> boxes = new ArrayList<>();
    public Frame(Client client){
        this.client = client;
    }
    public void createAndShowGUI() {

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

        final JButton uploadCsvButton = new JButton("Upload CSV");
        uploadCsvButton.setBackground(Color.gray);
        uploadCsvButton.setPreferredSize(new Dimension(20,10));
        buttonSection.add(uploadCsvButton);

        final JButton submitBut = new JButton("Submit");
        submitBut.setBackground(Color.pink);
        submitBut.setPreferredSize(new Dimension(100,10));
        buttonSection.add(submitBut);

        centeredGrid.add(buttonSection);

        final JFrame frame = new JFrame("Sudoku");

        submitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowOffset = 0;
                int colOffset= 0;
                int row = 0;

                for(int i = 0; i < boxes.size(); i++){
                    JTextField box = boxes.get(i);
                    int num;
                    if(box.getText().equals("") || box.getText().equals("0")) {
                        num = 0; // Server expects empty spaces to  be represented by 0s
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
                int[][] board = client.getPuzzle();

                boolean sudoku_is_valid = true; // Initialize to true.
                for(int i = 0 ; i < 9 ; i++){
                    for(int j = 0 ; j < 9; j++)
                    {
                        //should check to see if =-1, break and show dialog popup box that the puzzle is wrong
                        int calculated_box_value = board[i][j];
                        if(calculated_box_value == -1) {
                            sudoku_is_valid = false;
                        }
                        boxes.get((i*9)+j).setText(String.valueOf(board[i][j]));
                    }
                }

                if(!sudoku_is_valid){
                    JOptionPane.showMessageDialog(frame, "Sudoku is not properly formatted");
                }
            }
        });

        uploadCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String input_filename = JOptionPane.showInputDialog("Enter the filename (Ensure your file is uploaded to the data/input folder");
                File file = new File(MEDIA_INPUT_ROOT + input_filename);

                System.out.println(file.getAbsolutePath());

                if(file.exists()){
                    // Continue with file input stream
                    System.out.println("File exists!");

                    try {
                        FileReader fr = new FileReader(file);
                        BufferedReader br =  new BufferedReader(fr);
                        int i = 0;
                        String tmp_line;
                        while((tmp_line = br.readLine()) != null ){

                            // Split the first line on commas
                            String[] lineList = tmp_line.split(",");

                            if(lineList.length != 9){
                                throw new IllegalArgumentException("CSV is mal-formatted");
                            }

                            for(int j = 0; j < lineList.length; j++){
                                JTextField jtf = boxes.get((i*9)+j);
                                jtf.setText(lineList[j]);
                            }
                            // Increment row
                            i = i + 1;

                        }

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {


//                    for(int i = 0 ; i < 9 ; i++){
//                        for(int j = 0 ; j < 9; j++)
//                        {
//                            //should check to see if =-1, break and show dialog popup box that the puzzle is wrong
//                            int calculated_box_value = board[i][j];
//                            if(calculated_box_value == -1) {
//                                sudoku_is_valid = false;
//                            }
//                            boxes.get((i*9)+j).setText(String.valueOf(board[i][j]));
//                        }
//                    }


                    System.out.println("File does not exist");
                }

            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(centeredGrid);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public void updateBoard (int[][] board)
    {

    }

    public int[][] getSudokuMatrix() {
        return sudokuMatrix;
    }
}