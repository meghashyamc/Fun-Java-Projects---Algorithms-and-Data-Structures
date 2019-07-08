package priorityQueues;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;


// solves the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.
public class Solver {

    private int moves;
    private boolean solvable;
    private SearchNode goalNode;

    private static Comparator<SearchNode> priorityComparator = new Comparator<SearchNode>() {

        public int compare(SearchNode node1, SearchNode node2) {
            int diff = (node1.manhattan + node1.moves) - (node2.manhattan + node2.moves);
            if (diff == 0) return (node2.moves - node1.moves);
            else return diff;
        }
    };

    // // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) throw new java.lang.IllegalArgumentException();

        // there are two priority queues - one to store this board's neighbours,
        // the other to store this board's twin's neighbours
        // we do this because for any board, either the board or its twin has a solution but not both
        MinPQ<SearchNode> minPQ = new MinPQ<>(Solver.priorityComparator);
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>(Solver.priorityComparator);

        minPQ.insert(new SearchNode(initial, 0, null));
        twinMinPQ.insert(new SearchNode(initial.twin(), 0, null));

        process(minPQ, twinMinPQ);

    }

    // is the initial board solvable?
    public boolean isSolvable()   {

        return solvable;

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()    {

        if (isSolvable())
            return this.moves;
        else
            return -1;



    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (isSolvable()) {
            Stack<Board> solStack = new Stack<>();

        SearchNode currNode = goalNode;
        solStack.push(currNode.bd);

        while (currNode.prevNode != null) {
            solStack.push(currNode.prevNode.bd);
            currNode = currNode.prevNode;
        }

        return solStack;
    }

    else return null;



    }


    private class SearchNode{

        private Board bd;
        private SearchNode prevNode;
        private int moves;
        private int manhattan;



        private SearchNode(Board bd, int moves, SearchNode prevNode){

            this.bd = bd;
            this.moves = moves;
            this.prevNode = prevNode;
            this.manhattan = bd.manhattan();

        }

    }

    // processes the given board and its twin till a goal board is reached
    // updates solvable, moves and goalNode variables
    private void process(MinPQ<SearchNode> minPQ, MinPQ<SearchNode> twinMinPQ){


        while(true){

            SearchNode minNode = minPQ.delMin();
            SearchNode twinMinNode = twinMinPQ.delMin();


            if (minNode.bd.isGoal()) {
                solvable = true;
                this.moves = minNode.moves;
                this.goalNode = minNode;
                break;
            }

            else if (twinMinNode.bd.isGoal()){
                solvable = false;
                break;
            }
            else {
                for(Board board: minNode.bd.neighbors()){

                    // a board is not added to the queue if it is the same as the current board's predecessor
                    if ((minNode.prevNode == null) || !(board.equals(minNode.prevNode.bd)))
                        minPQ.insert(new SearchNode(board, (minNode.moves+1), minNode));
                }

                for(Board board: twinMinNode.bd.neighbors()){

                    if ((twinMinNode.prevNode == null) || !(board.equals(twinMinNode.prevNode.bd)))
                        twinMinPQ.insert(new SearchNode(board, (twinMinNode.moves+1), twinMinNode));
                }
            }
        }
    }


    // solve a slider puzzle
    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    }
