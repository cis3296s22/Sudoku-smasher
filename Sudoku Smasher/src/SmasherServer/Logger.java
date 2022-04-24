package SmasherServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Logger extends Thread{
    private SafeQueue<int[][]> reports;
    private final String file_name = "log.txt";
    private final int BOARDSIZE = 9;
    private BufferedWriter writer;

    public Logger(SafeQueue<int[][]> reports) throws IOException {
        this.reports = reports;
        writer = new BufferedWriter(new FileWriter(file_name));
    }


    private void writeBoard(int[][] board) throws IOException{
        for(int i = 0; i < BOARDSIZE ; i++){
            for(int j = 0 ; j <BOARDSIZE ; j++){
                writer.write(String.valueOf(board[i][j]) + " ");
            }
            writer.write("\n");
        }
        writer.flush();
    }

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
