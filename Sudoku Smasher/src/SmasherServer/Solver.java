package SmasherServer;

import  java.net.*;
import  java.io.*;

public class Solver implements Runnable{
    private int[][] board;
    private Socket  socket;
    private ObjectInputStream input_stream;
    private ObjectOutputStream output_stream;

    public static final int board_size = 9;

    //in the final version this should probably accept a socket connection rather than a board

    /**
     * constructor giving access to the board since run() has no arguments
     * @param socket Socket connection taken from connection distributor queue
     */
    public Solver(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.input_stream = new ObjectInputStream(socket.getInputStream());
            this.output_stream = new ObjectOutputStream(socket.getOutputStream());
            output_stream.writeObject(board);
        }
        catch (IOException i) { System.out.println(i); }

    }

    @Override
    public void run()
    {
        if(solveSudoku(board)){
            System.out.println("we did it!");
        }else{
            System.out.println("uh oh...");
        }
    }

    /**
     * Checks whether the guess 'entry' at position [row][col] violates the rules of sudoku or not
     * @param board a 2d array representing the current soduku board
     * @param row the row position of the guessed entry
     * @param col the column position of the guessed entry
     * @param entry an int representing the guessed number
     *
     * @return true if a possibly correct entry, false if not
     */
    private static boolean isValidGuess(int[][] board,
                                     int row, int col,
                                     int entry){

        //check for any clashes in the row
        for (int i = 0 ; i < board_size ; i++)
        {
            if(board[row][i] == entry)
                return false;
        }

        //check for any clashes in the column
        for(int i = 0 ; i < board_size ; i++)
        {
            if(board[i][col] == entry)
                return false;
        }

        //check for any clashes in the 3x3 box
        int boxRow = row - row%3;
        int boxCol = col - col%3;

        for(int r = boxRow ; r < (boxRow+3) ; r++)
        {
            for (int c = boxCol ; c < boxCol+3 ; c++)
            {
                if(board[r][c] == entry)
                    return false;
            }
        }
        //if we get here then there are no clashes, return true
        return true;
    }

    /**
     *
     * @param board a 2d array representing the sudoku board
     * @return true if solved, false if impossible
     */
    private static boolean solveSudoku(int[][] board)
    {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for(int i = 0 ; i < board_size ; i++)
        {
            for (int j = 0 ; j < board_size ; j++)
            {
                //there's still more to solve in the sudoku puzzle
                if(board[i][j] == 0)
                {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if(!isEmpty)
                break;
        }

        //no empty space left
        if(isEmpty)
            return true;

        //else for each-row backtrack
        for (int guess = 1; guess<= board_size ; guess++)
        {
            if(isValidGuess(board, row, col, guess))
            {
                board[row][col] = guess;
                if(solveSudoku(board))
                    return true;
                else
                    board[row][col] = 0;
            }
        }
        return false;
    }
}
