package SmasherClient;

/**
 * used for help with debugging
 */
public class Debugger {
    /**
     * example good test board
     */
    private static int[][] testBoard = new int[][] {
            { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
            { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
            { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
            { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
            { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
            { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
            { 0, 0, 5, 2, 0, 6, 3, 0, 0 }
    };

    /**
     * example invalid test board
     */
    private static int[][] badTestBoard = new int[][] {
            { 3, 3, 6, 5, 0, 8, 4, 0, 0 },
            { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
            { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
            { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
            { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
            { 1, 3, 1, 0, 0, 0, 2, 5, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
            { 0, 0, 5, 2, 0, 6, 3, 0, 2 }
    };

    /**
     *
     * @return returns example of good test
     */
    public static int[][]getGoodTest(){ return testBoard;}

    /**
     * @return returns example of bad test
     */
    public static int[][]getBadTest(){return badTestBoard;}

    /**
     * print the matrix to the terminal
     * @param board 9x9 int array representing sudoku board to be printed
     */
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
