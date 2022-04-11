package SmasherClient;

public class Debugger {

    public static void showMatrix(int[][] board)
    {
        int size = board.length;
        for(int r = 0 ; r < size ; r++) {
            for (int c = 0; c < size; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }
}
