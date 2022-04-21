package SmasherClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SubmitButton{
    private final JButton submitBut;
    private final ArrayList<JTextField> boxes;
    private int[][] board;
    private final JFrame frame;
    private final Client client;
    public SubmitButton(ArrayList<JTextField> boxes, int [][] board, JFrame frame, Client client){
        submitBut = new JButton("Submit");
        this.board = board;
        this.frame = frame;
        this.client = client;
        this.boxes = boxes;
        setAction(submitBut);
    }
    public JButton getSubmitBut(){
        return submitBut;
    }
    private void setAction(JButton submitBut){
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
                    board[row + rowOffset][(i % 3) + colOffset] = num;
                }
                client.sendPuzzle(board);
                Debugger.showMatrix(board);
                board = client.getPuzzle();
//                updateBoard(board);
                if(!updateBoard(board)){
                    JOptionPane.showMessageDialog(frame, "Sudoku is not properly formatted");
                }
            }
        });
    }
    private boolean updateBoard (int[][] board)
    {
        int rowOffset = 0;
        int colOffset= 0;
        int row = 0;

        for(int i = 0; i < boxes.size(); i++){
            JTextField box = boxes.get(i);
            if(board[0][0] == -1){
                return false;
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
            box.setText(String.valueOf(board[row + rowOffset][(i % 3) + colOffset]));
        }
        return true;
    }
}
