package tictactoe.player;

import tictactoe.Board;
import tictactoe.Result;

public abstract class Player {

    private char myChar;

    public Player(char myChar){
        this.myChar = myChar;
    }

    public abstract Board makeMove(Board board);

    public void winner(Result result){};

    public char getMyChar(){
        return myChar;
    }

}
