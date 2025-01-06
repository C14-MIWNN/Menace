package tictactoe.player.menace;

import tictactoe.Board;
import tictactoe.Result;
import tictactoe.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MenacePlayer extends Player {

    List<ArrayList<Matchbox>> matchboxes;

    List<Matchbox> currentGameMatchboxes;
    List<Integer> currentGameMoves;

    public MenacePlayer(char myChar) {
        super(myChar);

        matchboxes = new ArrayList<>();
        for (int turn = 0; turn < 9; turn++) { // there are at most 9 turns, (0-8)
            matchboxes.add(new ArrayList<>());
        }

        currentGameMatchboxes = new ArrayList<>();
        currentGameMoves = new ArrayList<>();
    }

    @Override
    public Board makeMove(Board board) {
        Matchbox currentRoundMatchbox = null;

        // search for the matchbox belonging to this board
        for (Matchbox matchbox : matchboxes.get(board.getTurn())) {
            if (matchbox.getBoard().equals(board)) {
                currentRoundMatchbox = matchbox;
            }
        }

        // create the matchbox if it can't be found
        if (currentRoundMatchbox == null) {
            currentRoundMatchbox = new Matchbox(board);
            matchboxes.get(board.getTurn()).add(currentRoundMatchbox);
        }

        // let the matchbox determine the next move
        currentGameMatchboxes.add(currentRoundMatchbox);
        int move = currentRoundMatchbox.getMove();
        currentGameMoves.add(move);

        board.makeMove(move, getMyChar());
        return board;
    }

    @Override
    public void winner(Result result) {
        for (int turn = 0; turn < currentGameMoves.size(); turn++) {
            // for every matchbox in this game learn from the move that lead to result
            currentGameMatchboxes.get(turn).learn(currentGameMoves.get(turn), result);
        }

        // reset for next game
        currentGameMatchboxes = new ArrayList<>();
        currentGameMoves = new ArrayList<>();

        // count matchboxes
        int count = 0;
        for (ArrayList<Matchbox> matchbox : matchboxes) {
            for (Matchbox matchbox1 : matchbox) {
                count++;
            }
        }
    }
}
