package SmasherServer;
//Contributors:
//Ben Smolin
//Dat Nguyen.
//Mary Kate Durnan
//Robert Stachurski

//import SmasherClient.Frame;

public class ServerMain
{

    public static void main(String[] args)
    {
        Server server = new Server(3000);
        final int MAXTHREADS = 6;
        Solver solvers[] = new Solver[MAXTHREADS];
//        Frame.createAndShowGUI();
//        SafeQueue connections = new SafeQueue(MAXTHREADS);


//        for(int i = 0 ; i < MAXTHREADS ; i++) {
//            solvers[i] = new Solver(connections);
//            System.out.println("Hello");
//           // solvers[i].run();
//        }


//        int[][] testBoard = new int[][] {
//                { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
//                { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
//                { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
//                { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
//                { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
//                { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
//                { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
//                { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
//                { 0, 0, 5, 2, 0, 6, 3, 0, 0 }
//        };
//        int[][] badTestBoard = new int[][] {
//                { 3, 3, 6, 5, 0, 8, 4, 0, 0 },
//                { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
//                { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
//                { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
//                { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
//                { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
//                { 1, 3, 1, 0, 0, 0, 2, 5, 0 },
//                { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
//                { 0, 0, 5, 2, 0, 6, 3, 0, 2 }
//        };
        //server accept loop
        while(true)
        {
            System.out.println("placeholder...");
            break;
        }
//        Runnable a = new Solver(testBoard);
//        Runnable b = new Solver(badTestBoard);
//        a.run();
//        b.run();
//        Debugger.showMatrix(testBoard);

    }

}
