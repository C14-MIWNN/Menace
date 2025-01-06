package tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInitialBoardState() {
        List<Integer> expectedMoves = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertEquals(expectedMoves, board.availableMoves());
        assertEquals(0, board.getTurn());
        assertEquals(TicTacToeLauncher.EMPTY, board.winner());
    }

    @Test
    public void testMakeMove() {
        board.makeMove(1, 'X');
        assertEquals('X', board.getCoordinate(0, 0));
        assertEquals(1, board.getTurn());
        assertFalse(board.availableMoves().contains(1));
    }

    @Test
    public void testInvalidMove() {
        board.makeMove(1, 'X');
        Exception exception = assertThrows(IllegalArgumentException.class, () -> board.makeMove(1, 'O'));
        assertEquals("1 is not a legal move", exception.getMessage());
    }

    @Test
    public void testWinnerRows() {
        board.makeMove(1, 'X');
        board.makeMove(2, 'X');
        board.makeMove(3, 'X');
        assertEquals('X', board.winner());
    }

    @Test
    public void testWinnerColumns() {
        board.makeMove(1, 'O');
        board.makeMove(4, 'O');
        board.makeMove(7, 'O');
        assertEquals('O', board.winner());
    }

    @Test
    public void testWinnerDiagonals() {
        board.makeMove(1, 'X');
        board.makeMove(5, 'X');
        board.makeMove(9, 'X');
        assertEquals('X', board.winner());
    }

    @Test
    public void testDraw() {
        board.makeMove(1, 'X');
        board.makeMove(2, 'O');
        board.makeMove(3, 'X');
        board.makeMove(4, 'O');
        board.makeMove(5, 'O');
        board.makeMove(6, 'X');
        board.makeMove(7, 'X');
        board.makeMove(8, 'X');
        board.makeMove(9, 'O');
        assertEquals(TicTacToeLauncher.DRAW, board.winner());
    }

    @Test
    public void testEqualsWithRotations() {
        Board rotatedBoard = new Board();
        board.makeMove(1, 'X');
        board.makeMove(2, 'O');
        board.makeMove(3, 'X');
        rotatedBoard.makeMove(3, 'X');
        rotatedBoard.makeMove(6, 'O');
        rotatedBoard.makeMove(9, 'X');

        assertTrue(board.equals(rotatedBoard)); // Assuming equals considers rotations.
    }

    @Test
    public void testEqualsWithMirroring() {
        Board mirroredBoard = new Board();
        board.makeMove(1, 'X');
        board.makeMove(2, 'O');
        board.makeMove(3, 'X');
        mirroredBoard.makeMove(3, 'X');
        mirroredBoard.makeMove(2, 'O');
        mirroredBoard.makeMove(1, 'X');

        assertTrue(board.equals(mirroredBoard)); // Assuming equals considers mirroring.
    }

    @Test
    public void testToString() {
        board.makeMove(1, 'X');
        String expected =
                "-------------\n" +
                        "| X |   |   |\n" +
                        "-------------\n" +
                        "|   |   |   |\n" +
                        "-------------\n" +
                        "|   |   |   |\n" +
                        "-------------\n";
        assertEquals(expected, board.toString());
    }
}
