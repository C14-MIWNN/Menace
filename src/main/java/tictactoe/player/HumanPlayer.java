package tictactoe.player;

import tictactoe.Board;
import tictactoe.Result;

import java.util.Scanner;

public class HumanPlayer extends Player {

    Scanner keyboard;

    public HumanPlayer(char myChar){
        super(myChar);
        keyboard = new Scanner(System.in);
    }

    @Override
    public Board makeMove(Board board) {
        System.out.println(board);

        System.out.println("Welk vakje wil je een zet maken?");
        int move = keyboard.nextInt();

        board.makeMove(move, getMyChar());
        return board;
    }

    @Override
    public void winner(Result result) {
        switch (result) {
            case LOSE:
                System.out.printf("Helaas %c, je hebt verloren.\n", getMyChar());
                break;
            case WIN:
                System.out.printf("Gefeliciteerd %c, je hebt gewonnen!\n", getMyChar());
                break;
            case DRAW:
                System.out.println("Jullie hebben gelijk gespeeld.");
                break;
        }

    }
}
