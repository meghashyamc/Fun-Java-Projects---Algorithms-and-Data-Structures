package priorityQueues;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

// depicts a board in the n-puzzle
public class Board {

    private int[][] boardArray;

    private int n;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)

    public Board(int[][] blocks) {

        if (blocks.length != blocks[0].length) throw new IllegalArgumentException("Number of rows and columns should be the same.");

        this.n = blocks.length;
        boardArray = new int[n][n];
        for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            boardArray[i][j] = blocks[i][j];

        }

    // board dimension n
        public int dimension(){

        return n;

        }

    // number of blocks out of place
        public int hamming()   {

        int counter = 0;

            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {

                    int val = boardArray[i][j];
                    int pos = i * n + j + 1;
                    if (val != 0) {
                        if (val != pos) counter++;
                    }

                }

                    return counter;

        }


    // sum of Manhattan distances between blocks and goal
    // this is the sum of each block's distance (vert+horizontal) from
    // its ideal position
        public int manhattan()     {

        int counter = 0;

            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {

                    int val = boardArray[i][j];
                    if (val != 0) {
                        counter += positive(row(val) - i) + positive(col(val) - j);
                    }

                }
                   return counter;
        }

    // is this board the goal board?
        public boolean isGoal(){

        return (this.hamming() == 0);
        }

    // a board that is obtained by exchanging any pair of blocks
    // we need this because either a board or its twin has a solution but
    // not both
        public Board twin() {

        int[][] twinBoardArray = new int[n][n];

        for(int i = 0; i < n; i++)
                System.arraycopy(boardArray[i], 0, twinBoardArray[i], 0, boardArray[i].length);


            if (twinBoardArray[0][0] == 0)
                exchange(twinBoardArray, 1, 0, 1, 1);

             else if (twinBoardArray[1][0] == 0)
                exchange(twinBoardArray, 0, 0, 0, 1);

             else if (twinBoardArray[0][1] == 0)
                exchange(twinBoardArray, 0, 0, 1, 0);

            else
                exchange(twinBoardArray, 0, 0, 1, 0);

             return new Board(twinBoardArray);


        }



    // does this board equal y?
        public boolean equals(Object y) {
            if (y == this) return true;
            if (y == null) return false;
            if (y.getClass() != this.getClass()) return false;
            Board that = (Board) y;
            if (that.dimension() != this.dimension()) return false;

            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {

                    if (that.boardArray[i][j] != this.boardArray[i][j]) return false;

                }

                return true;


        }


    // all neighboring boards
        public Iterable<Board> neighbors() {

            Stack<Board> neighbours = new Stack<>();

            int zeroI = findZeroI(boardArray);
            int zeroJ = findZeroJ(boardArray);

            if (zeroI > 0) {

                Board upperBoard = new Board(boardArray.clone());
                exchange(upperBoard.boardArray,zeroI, zeroJ, zeroI-1, zeroJ);
                neighbours.push(upperBoard);

            }

            if (zeroI < n-1) {

                Board lowerBoard = new Board(boardArray.clone());
                exchange(lowerBoard.boardArray,zeroI, zeroJ, zeroI+1, zeroJ);
                neighbours.push(lowerBoard);

            }


            if (zeroJ > 0) {

                Board leftBoard = new Board(boardArray.clone());
                exchange(leftBoard.boardArray,zeroI, zeroJ, zeroI, zeroJ-1);
                neighbours.push(leftBoard);

            }

            if (zeroJ < n-1) {

                Board rightBoard = new Board(boardArray.clone());
                exchange(rightBoard.boardArray,zeroI, zeroJ, zeroI, zeroJ+1);
                neighbours.push(rightBoard);

            }

            return neighbours;

        }

    // string representation of this board (in the output format specified below)
        public String toString(){

            StringBuilder s = new StringBuilder();
            s.append(n + "\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    s.append(String.format("%2d ", boardArray[i][j]));
                }
                s.append("\n");
            }
            return s.toString();



    }

        private int positive(int val){

        if (val >= 0) return val;
        else return (val*-1);
        }

        private void exchange(int[][] a, int i1, int j1, int i2, int j2){

        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
        }

        private int findZeroI(int[][] a){

        for(int i = 0; i < a.length; i++)
            for(int j = 0; j < a[0].length; j++)
                if (a[i][j] == 0) return i;

        return -1;
        }

    private int findZeroJ(int[][] a){

        for(int i = 0; i < a.length; i++)
            for(int j = 0; j < a[0].length; j++)
                if (a[i][j] == 0) return j;

        return -1;
    }

    // finds zero based row of an ideally placed value
    private int row(int val){

        if (val%n == 0) return (val/n - 1);
        else return val/n;
    }

    // finds zero based column of an ideally placed value
    private int col(int val){

        if (val%n == 0) return n-1;
        else return (val%n - 1);
    }



        public static void main(String[] args){

        int[][] a = new int[3][3];

        a[0][0] = 1;
        a[0][1] = 2;
        a[0][2] = 3;
        a[1][0] = 0;
        a[1][1] = 7;
        a[1][2] = 6;
        a[2][0] = 5;
        a[2][1] = 4;
        a[2][2] = 8;

            Board board = new Board(a);
            StdOut.println(board);
            StdOut.println();

            StdOut.println("The twin board is: " + board.twin());

            StdOut.println("The twin board is: " + board.twin());

            StdOut.println("The twin board is: " + board.twin());

            StdOut.println("This board's hamming is " + board.hamming());
            StdOut.println("This board's manhatten is " + board.manhattan());
            StdOut.println("Is this a goal board?" + board.isGoal());
            StdOut.println("This board's twin board is " + '\n' + board.twin());
            StdOut.println();
            StdOut.println("Does this board equal its twin? " + board.equals(board.twin()));
            Board board2 = new Board(a);

            StdOut.println("Does this board equal board2? " + board.equals(board2));
            StdOut.println("Here are this board's neighbours: ");
            StdOut.println();
            for(Board bd: board.neighbors()){
                StdOut.println(bd);
                StdOut.println();
            }
        }
}
