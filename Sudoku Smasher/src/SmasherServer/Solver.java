package SmasherServer;

import  java.net.*;
import  java.io.*;

/**
 * Solver thread that continually takes sockets, reads a puzzles, solves it, logs it and sends it back
 * BAD_BOARD is a flag indicating the puzzle was unsolvable
 * board is a 9x9 holding a sudoku board
 * socket is the current socket
 * input_stream is used to read in sudoku boards
 * output_stream is used to write to clients
 * connection_queue is a thread-safe FIFO queue for socket distribution
 * reports is a thread-safe FIFO queue for solve attempt distribution
 */
public class Solver extends Thread{
    private final int[][] BAD_BOARD; //need to flag bad puzzles somehow maybe send board of all -1?
    private int[][] board;
    private Socket socket;
    private ObjectInputStream input_stream;
    private ObjectOutputStream output_stream;
    private SafeQueue connection_queue;
    private SafeQueue reports;

    public static final int board_size = 9;

    //needs to be initialized with connection_queue to continuously consume from

    /**
     * constructor giving access to the board since run() has no arguments
     * @param buffer thread-safe FIFO queue of sockets
     * @param reports thread-safe FIFO queue of solved boards
     */
    public Solver(SafeQueue buffer, SafeQueue reports)
    {
        this.connection_queue = buffer;
        this.reports = reports;
        this.BAD_BOARD = new int[][] {
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 },
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 },
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 },
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 },
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 },
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 },
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 },
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 },
                {-1,-1,-1,-1,-1,-1,-1,-1,-1 }
        };
    }

    /**
     * Main process of thread, runs when called
     * Continually tries to take a socket from the connection_queue, solve it, log it and send it back over the socket
     */
    @Override
    public void run() {
        while (true) {
            try {
                socket = (Socket) connection_queue.take();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                input_stream = new ObjectInputStream(socket.getInputStream());
                output_stream = new ObjectOutputStream(socket.getOutputStream());
            }
            catch (IOException e) {
                System.out.println(e);
            }

            try {
                board = (int[][]) input_stream.readObject();
                System.out.println("read board");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (isValidBoard(board) && solveSudoku(board)) {
                System.out.println("Successful solve...");
                Debugger.showMatrix(board);
                try {
                    output_stream.writeObject(board);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                System.out.println("failed solve...");
            try {
                reports.add(board);
                output_stream.writeObject(BAD_BOARD);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
                input_stream.close();
                output_stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check to see if the board is allowed or not
     * @param board a 9x9 sudoku board as 2d int array
     * @return true if valid, false if not valid
     */
    private static boolean isValidBoard(int[][] board){
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (board[i][j] != 0){
                    if(!isValidBoardEntry(board, i,j,board[i][j])){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Private helper to see if a specific guess is valid or not
     * @param board a 9x9 sudoku board as a 2d int array
     * @param row row of guess
     * @param col column of guess
     * @param entry number guess
     * @return true if valid, false if not
     */
    private static boolean isValidBoardEntry(int[][] board,
                                        int row, int col,
                                        int entry){

        //check for any clashes in the row
        for (int i = 0 ; i < board_size ; i++)
        {
            if(board[row][i] == entry && i != col)
                return false;
        }

        //check for any clashes in the column
        for(int i = 0 ; i < board_size ; i++)
        {
            if(board[i][col] == entry && i != row)
                return false;
        }

        //check for any clashes in the 3x3 box
        int boxRow = row - row%3;
        int boxCol = col - col%3;

        for(int r = boxRow ; r < (boxRow+3) ; r++)
        {
            for (int c = boxCol ; c < boxCol+3 ; c++)
            {
                if(board[r][c] == entry && r != row && c != col)
                    return false;
            }
        }
        //if we get here then there are no clashes, return true
        return true;
    }
    /**
     * Checks whether the guess 'entry' at position [row][col] violates the rules of sudoku or not
     * @param board a 2d array representing the current sudoku board
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
        //int c = 0;
        for (int guess = 1; guess<= board_size ; guess++)
        {
            if(isValidGuess(board, row, col, guess))
            {
                //System.out.println(c++);
                board[row][col] = guess;
                if(solveSudoku(board)) {
                    return true;
                }
                else {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }
}