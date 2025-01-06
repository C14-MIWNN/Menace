package tictactoe.player;

import tictactoe.Board;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player {
    Random rng;
    public RandomPlayer(char myChar) {
        super(myChar);
        rng = new Random();
    }

    @Override
    public Board makeMove(Board board) {

        List<Integer> availableMoves = board.availableMoves();
        int move = availableMoves.get(rng.nextInt(availableMoves.size()));

        board.makeMove(move, getMyChar());
        return board;
    }

}
