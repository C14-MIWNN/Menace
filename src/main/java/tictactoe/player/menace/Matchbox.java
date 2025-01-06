package tictactoe.player.menace;

import tictactoe.Board;
import tictactoe.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Matchbox {
    private static final int LOSE = -3;
    private static final int WIN = 5;
    private static final int DRAW = -1;

    private final int INITIAL_KRAALTJES = 7;

    private Board board;
    private List<Integer> moveBeads;
    List<Integer> availableMoves;

    Random rng;

    public Matchbox(Board board){
        this.board = new Board(board);
        availableMoves = board.availableMoves();
        moveBeads = new ArrayList<>();
        for (Integer ignored : availableMoves) {
            moveBeads.add(INITIAL_KRAALTJES);
        }

        rng = new Random();
    }

    public int getMove(){
        int totalBeads = 0;
        for (Integer moveBead : moveBeads) {
            totalBeads += moveBead;
        }

        if (totalBeads == 0) {
            // if the box is empty, make a random move (it doesn't matter you should quit anyway)
            int move = availableMoves.get(rng.nextInt(availableMoves.size()));
            return move;
        }

        int randomSelection = rng.nextInt(totalBeads);
        int moveIndex = -1;

        do {
            moveIndex++;
            randomSelection -= moveBeads.get(moveIndex);
        } while (randomSelection > 0);

        return availableMoves.get(moveIndex);
    }

    public void learn(int move, Result result){
        // learn from the result i.e. add or remove beads for the move

        int moveIndex = availableMoves.indexOf(move);
        switch (result) {
            case LOSE:
                moveBeads.set(moveIndex, moveBeads.get(moveIndex) + LOSE);
                break;
            case WIN:
                moveBeads.set(moveIndex, moveBeads.get(moveIndex) + WIN);
                break;
            case DRAW:
                moveBeads.set(moveIndex, moveBeads.get(moveIndex) + DRAW);
                break;
        }

        // make sure the changed move doesn't go negative
        if (moveBeads.get(moveIndex) < 0) {
            moveBeads.set(moveIndex, 0);
        }
    }

    public Board getBoard() {
        return board;
    }
}
