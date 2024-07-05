import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class testClass {

    private Board board1;

    private Board board;
    private BoardCell[][] cells;
    private Game game;
    private BoardCell BoardCell;

    @Test
    public void testBoard()
    {
        this.board1=new Board(difficulty.easy);
        assertEquals(board1.getRows(),8);
        assertEquals(board1.getColumns(),8);
        assertEquals(board1.getNumberOfMines(),10);
        this.board1=new Board(difficulty.medium);
        assertEquals(board1.getRows(),16);
        assertEquals(board1.getColumns(),16);
        assertEquals(board1.getNumberOfMines(),40);
        this.board1=new Board(difficulty.hard);
        assertEquals(board1.getRows(),16);
        assertEquals(board1.getColumns(),30);
        assertEquals(board1.getNumberOfMines(),99);

    }

    @Test
    public void testPlaceMines() {
        Board board = new Board(difficulty.medium);
        board.placeMines();
        int count = 0;

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                if (board.isMine(i,j))
                {
                    count=count+1;
                }
            }
        }
        assertEquals(count/2, 40);
    }

    @Test
    public void testFlagUnopened()
    {
        game = new Game(difficulty.easy);
        game.getInput("F 1 1");
        assertTrue(game.isFlagged(1, 1));
        game.getInput("F 1 1");
        assertFalse(game.isFlagged(1, 1));
    }

    @Test
    public void testOutput()
    {
        this.board = new Board(difficulty.easy);
        board.placeMines();
        board.printBoard(false);
        this.board = new Board(difficulty.medium);
        board.placeMines();
        board.printBoard(false);
        this.board = new Board(difficulty.hard);
        board.placeMines();
        board.printBoard(false);
    }

    @Test
    public void testopenMine()
    {
        Board board = new Board(difficulty.medium);
        board.placeMines();

        for (int i = 0; i < board.getRows(); i++)
        {
            for (int j = 0; j < board.getColumns(); j++)
            {
                 if (board.isMine(i,j))
                 {
                     board.setLost();
                 }
            }
        }
        assertTrue(board.isLost());
    }

    @Test
    public void testWin()
    {
        Board board = new Board(difficulty.easy);
        for (int i = 0; i < board.getRows(); i++)
        {
            for (int j = 0; j < board.getColumns(); j++)
            {
                if (!board.isMine(i,j))
                {
                    board.revealBoard(i,j);
                }
            }
        }
        if(board.checkWin())
        {
            board.setWon();
        }
        assertTrue(board.isWon());
    }

    @Test
    public void testFirstRule()
    {
        Board board = new Board(difficulty.easy);
        for (int i = 0; i < board.getRows(); i++)
        {
            for (int j = 0; j < board.getColumns(); j++)
            {
                if (board.isMine(i,j))
                {
                    board.repositionMine(i, j);
                    board.revealBoard(i, j);
                    break;
                }
            }
        }
        assertFalse(board.isLost());
    }

    @Test
    public void testNeighbours()
    {
        Board board = new Board(difficulty.easy);
        /**
         * Creating the following board :
         * B B B 0 0 0 0 0
         * B 0 B 0 0 0 0 0
         * B B B 0 0 0 0 0
         * 0 0 0 0 0 0 B B
         * 0 0 0 0 0 0 0 0
         * 0 0 0 0 0 0 0 0
         * 0 0 0 0 0 0 0 0
         * 0 0 0 0 0 0 0 0
         */
        boolean[][] givenBoard = {
            {true, true, true, false, false, false, false,false},
            {true, false, true, false, false, false, false, false},
            {true, true, true, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, true, true},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false}
        };
        /**
         * Revealing (1,1) and checking that the bombs around it do not reveal
         */
        board.generateBoard(givenBoard);
        board.revealBoard(1,1);
        assertFalse(board.isRevealed(0,0));
        assertFalse(board.isRevealed(0,1));
        assertFalse(board.isRevealed(0,2));
        assertFalse(board.isRevealed(1,0));
        assertFalse(board.isRevealed(1,2));
        assertFalse(board.isRevealed(2,0));
        assertFalse(board.isRevealed(2,1));
        assertFalse(board.isRevealed(2,2));

        /**
         * Revealing (7,0) and checking that the neighboors without bombs have revealed
         */
        board.revealBoard(7,0);
        assertTrue(board.isRevealed(5,0));
        assertTrue(board.isRevealed(5,1));
        assertTrue(board.isRevealed(5,2));
        assertTrue(board.isRevealed(6,0));
        assertTrue(board.isRevealed(6,1));
        assertTrue(board.isRevealed(6,2));
        assertTrue(board.isRevealed(7,1));
        assertTrue(board.isRevealed(7,2));
    }

}