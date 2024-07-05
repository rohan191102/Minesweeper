import java.util.Objects;
import java.util.Scanner;
/**
 * This class represent a game session.
 */
public class Game
{
    //private difficulty level;
    private Board board;
    private int rows;
    private int columns;

    public Game(difficulty d)
    {
        //this.level = d;

        if(d == difficulty.easy)
        {
            this.board = new Board(d);
            this.rows = 8;
            this.columns = 8;
        }
        if(d == difficulty.medium)
        {
            this.board = new Board(d);
            this.rows = 16;
            this.columns = 16;
        }
        if(d == difficulty.hard)
        {
            this.board = new Board(d);
            this.rows = 16;
            this.columns = 30;
        }
    }

    public void getInput(String input)
    {
        // Split input string into letter and two numbers
        char letter = input.charAt(0);

        if(input.length() !=1)
        {
            String[] numbers = input.split(" ");
            int row = Integer.parseInt(numbers[1]);
            int col = Integer.parseInt(numbers[2]);
            if(row >= this.rows || col >= this.columns)
            {
                System.out.println("The coordinates entered are outside of the board !");
            }
            else
            {
                switch (letter)
                {
                    case 'F':
                    case 'f':
                        if(this.board.isRevealed(row, col))
                        {
                            System.out.println("This cell is revealed. You cannot flag it.");
                        }
                        else
                        {
                            this.board.setFlag(row, col);
                        }
                        break;
                    case 'r':
                    case 'R':
                        if(this.board.isMine(row, col))
                        {
                            if(this.board.getRevealedCells() == 0){
                                this.board.repositionMine(row, col);
                                this.board.revealBoard(row, col);
                            }
                            else
                            {
                                this.board.setLost();
                            }
                        }
                        else
                        {
                            this.board.revealBoard(row, col);
                            if(this.board.checkWin())
                            {
                                this.board.setWon();
                            }
                        }
                        break;
                    default:
                        System.out.println("Please type a valid command. Type h for help.");
                }
            }
        }

        // Perform the corresponding operation
        else{
            switch (letter) {
                case 'H':
                case 'h':
                    System.out.println("You can perform the following operations :");
                    System.out.println("1) R x y OR r x y to reveal the cell in the (x,y) position.");
                    System.out.println("2) F x y OR f x y to flag the cell in the (x,y) position.");
                    System.out.println("3) Q OR q to quit the game. The game is then lost.");
                    break;
                case 'q':
                case 'Q':
                    this.board.setLost();
                    break;
                default:
                    System.out.println("Please type a valid command. Type h for help.");
            }
        }
    }
    /**
     * Checks the state of the board. The board can be in winning state, losing state or can be still going.
     * @return 0 if the game is still going, 1 if it is a won and 2 if it is lost.
     */
    public int checkState()
    {
        if(!this.board.isWon() && !this.board.isLost())
        {
            return 0; // Game is still going.
        }
        else if (this.board.isWon() && !this.board.isLost())
        {
            return 1; // Game Won !
        }
        else
        {
            return 2;// Game is lost.
        }
    }
    /**
     * Prints the board on the terminal.
     * @param gameOver boolean value that says if the game is still going.
     */
    public void printBoard(boolean gameOver)
    {
        this.board.printBoard(gameOver);
    }

    public boolean isFlagged(int row, int col){return this.board.isFlagged(row, col);}
}
