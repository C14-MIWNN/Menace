package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private static final char EMPTY_CELL = ' ';
    private static final int BOARD_SIZE = 3;
    private final char[][] board;
    private int turn;

    public Board(){
        board = new char[BOARD_SIZE][BOARD_SIZE];

        // initialise the board (can be done more easily, this is also to illustrate the double for loop)
        for (int row = 0; row < board.length; row++) {
            Arrays.fill(board[row], TicTacToeLauncher.EMPTY);
        }

        turn = 0;
    }

    public Board(Board board) {
        this.board = new char[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board[row].length; column++) {
                this.board[row][column] = board.getCoordinate(row, column);
            }
        }

        turn = board.getTurn();
    }

    public List<Integer> availableMoves() {
        // TODO: recalculating the available moves everytime gives a large overhead
        List<Integer> availableMoves = new ArrayList<>();
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board[row].length; column++) {
                if (this.board[row][column] == EMPTY_CELL) {
                    availableMoves.add((row * BOARD_SIZE) + column + 1);
                }
            }
        }
        return availableMoves;
    }

    public void makeMove(int move, char player) {
        // translate single number to coordinate
        if (!availableMoves().contains(move)) {
            throw new IllegalArgumentException(String.format("%s is not a legal move", move));
        }

        int moveX = (move - 1) / BOARD_SIZE;
        int moveY = (move - 1) % BOARD_SIZE;
        makeMove(moveX, moveY, player);
    }

    public void makeMove(int x, int y, char player) {
        if (board[x][y] == EMPTY_CELL) {
            board[x][y] = player;
            turn++;
        } else {
            throw new IllegalArgumentException(String.format("(%s, %s) is not a legal move", x, y));
        }
    }

    public int getTurn() {
        return turn;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(generateBorder());
        for (char[] row : board) {
            for (char cell : row) {
                stringBuilder.append("| ").append(cell).append(" ");
            }
            stringBuilder.append("|\n").append(generateBorder());
        }
        return stringBuilder.toString();
    }

    private String generateBorder() {
        return "-".repeat((BOARD_SIZE * 4) + 1) + "\n";
    }

    public char winner() {
        // Dynamically check rows and columns
        for (int i = 0; i < board.length; i++) {
            if (allEqual(board[i])) return board[i][0]; // Check row i
            if (allEqual(getColumn(board, i))) return board[0][i]; // Check column i
        }

        // Dynamically check diagonals
        if (allEqual(getDiagonal(board, true))) return board[0][0]; // Main diagonal
        if (allEqual(getDiagonal(board, false))) return board[0][board.length - 1]; // Anti-diagonal

        // no winner, if there are no more moves it is a draw.
        if (availableMoves().isEmpty()) {
            return TicTacToeLauncher.DRAW;
        }

        // no winner yet
        return TicTacToeLauncher.EMPTY;
    }

    private boolean allEqual(char... cells) {
        char first = cells[0];
        if (first == TicTacToeLauncher.EMPTY) return false;
        for (char cell : cells) {
            if (cell != first) return false;
        }
        return true;
    }

    private char[] getColumn(char[][] board, int col) {
        char[] column = new char[board.length];
        for (int i = 0; i < board.length; i++) {
            column[i] = board[i][col];
        }
        return column;
    }

    private char[] getDiagonal(char[][] board, boolean mainDiagonal) {
        char[] diagonal = new char[board.length];
        for (int i = 0; i < board.length; i++) {
            diagonal[i] = mainDiagonal ? board[i][i] : board[i][board.length - 1 - i];
        }
        return diagonal;
    }


    public char getCoordinate(int x, int y) {
        return board[x][y];
    }

    @Override
    public boolean equals(Object o) {
        // TODO: detect rotated or mirrored board and also mark them as equal
        // make sure to also update the hashCode (remember: equal objects must have the same hashCode
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Arrays.deepEquals(this.board, ((Board) o).board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
