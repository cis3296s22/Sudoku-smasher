package SmasherServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * used for logging solve attempts
 * reports is a thread-safe queue of solved boards
 * file_name is the name of the logfile to be written to
 * BOARDSIZE is size of Suduko side
 * writer is a BufferedWriter that writes to log file
 */
public class Logger extends Thread{
    private SafeQueue<int[][]> reports;
    private final String file_name = "log.txt";
    private final int BOARDSIZE = 9;
    private BufferedWriter writer;

    /**
     *
     * @param reports thread-safe queue of boards to write
     * @throws IOException when IO error occurs
     */
    public Logger(SafeQueue<int[][]> reports) throws IOException {
        this.reports = reports;
        writer = new BufferedWriter(new FileWriter(file_name));
    }


    /**
     * Uses a bufferedWriter to write to a log file
     * @param board board to be written to file
     * @throws IOException when IO error occurs
     */
    private void writeBoard(int[][] board) throws IOException{
        for(int i = 0; i < BOARDSIZE ; i++){
            for(int j = 0 ; j <BOARDSIZE ; j++){
                writer.write(String.valueOf(board[i][j]) + " ");
            }
            writer.write("\n");
        }
        writer.flush();
    }

    /**
     * continually tries to take a board from the queue and write it to the log file
     */
    public void run() {
        while(true) {
            try {
                int[][] current = reports.take();
                System.out.println("report taken");
                writer.write("report taken\n");
                writer.flush();
                writeBoard(current);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
