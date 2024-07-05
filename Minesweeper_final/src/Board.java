import java.util.*;

/**
 * This class represent the board used in a minesweeper game.
 */
public class Board  {
    /**
     * The number of mines in the board.
     */
    private int numberOfMines;
    /**
     * The number of rows in the board.
     */
    private int rows;
    /**
     * The number of columns in the board.
     */
    private int columns;
    /**
     * Is the board in a winning state.
     */
    private boolean isWon;
    /**
     * Is the board in a losing state. If the board neither in the winning or losing state, it means
     * that the game session is still going.
     */
    private boolean isLost;
    /**
     * A 2D array of boardCell objects. All together they make up the board.
     */
    private BoardCell[][] board;
    /**
     * The number of revealed cells.
     */
    private int revealedCells;

    /**
     * Random object used to generate numbers randomly.
     */
    Random random = new Random();

    public Board(difficulty d) {
        if (d == difficulty.easy)
        {
            this.rows = 8;
            this.columns = 8;
            this.numberOfMines = 10;
        }
        if (d == difficulty.medium)
        {
            this.rows = 16;
            this.columns = 16;
            this.numberOfMines = 40;
        }
        if (d == difficulty.hard) {
            this.rows = 16;
            this.columns = 30;
            this.numberOfMines = 99;
        }
        // Instantiating an array board cell
        this.board = new BoardCell[rows][columns];
        // Instantiating each cell
        for (int i = 0; i < this.rows; i ++) {
            for (int j = 0; j < this.columns; j++) {
                this.board[i][j] = new BoardCell(i, j);
            }
        }
        // Placing the mines
        this.placeMines();
        // Setting each adjacent mines to each cell
        for (int i = 0; i < this.rows; i ++) {
            for (int j = 0; j < this.columns; j++) {
                this.setAdjacent(i,j);
            }
        }
        this.revealedCells = 0;
        this.isLost = false;
        this.isWon = false;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNumberOfMines()
    {
        return numberOfMines;
    }

    public void placeMines()
    {
        int count = 0;
        int row1 = 0;
        int col1 = 0;
        while (count < numberOfMines) {
            row1 = random.nextInt(this.rows - 1);
            col1 = random.nextInt(this.columns - 1);
            if (!board[row1][col1].isMine())
            {
                board[row1][col1].updateMine(true);
                count = count + 1;
            }
        }
    }

    /**
     * Computes the number of adjacent mines for a cell and then saves that number in the cell.
     * @param row the row of the cell for which we compute.
     * @param col the column of the cell for which we compute.
     */
    public void setAdjacent(int row, int col){
        int adjMines = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < this.rows && j >= 0 && j < this.columns && this.board[i][j].isMine()) {
                    adjMines=adjMines+1;
                }
            }
        }
        this.board[row][col].updateAdjacent(adjMines);
    }

    /**
     * Prints the board in the terminal. An X is printed if the cell has not been revealed, an F is printed
     * if the cell has been flagged and the number of adjacent mines is printed for revealed cells. If the game
     * session has ended, we also show the mines.
     * @param endOfGame boolean variable to tell if the game session has ended.
     */
    public void printBoard(boolean endOfGame) {
        if (!endOfGame) {
            for (BoardCell[] boardCells : board) {
                for (BoardCell boardCell : boardCells) {
                    if (!boardCell.isRevealed() && !boardCell.isFlagged()) {
                        System.out.format("%-3s", "\uD83D\uDD32");
                    }
                    else if (!boardCell.isRevealed() && boardCell.isFlagged())
                    {
                        System.out.print("\uD83D\uDEA9 ");
                    }
                    else
                    {
                        System.out.format("%-3d", boardCell.getAdjacent());
                    }
                }
                System.out.println();
            }
        }
        else
        {
            for (BoardCell[] boardCells : board) {
                for (BoardCell boardCell : boardCells) {
                    if (boardCell.isMine()) {
                        System.out.print("\uD83D\uDCA3 ");
                    }
                    else
                    {
                        System.out.format("%-3d", boardCell.getAdjacent());
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * Flags the cell of coordinates (row,col).
     * @param row the row of the cell to be flagged.
     * @param col the column of the cell to be flagged.
     */
    public void setFlag(int row, int col){
        this.board[row][col].flag();
    }

    /**
     * Reveals the cell of coordinates (row,col). If there is a mine there, it is game over.
     * @param row the row of the cell to be revealed.
     * @param col the column of the cell to be revealed.
     */
    public void revealBoard(int row, int col)
    {
        if (row < 0 || row >= rows || col < 0 || col >= columns)
        {
            return;
        }

        BoardCell cell = board[row][col];

        if (cell.isMine() || cell.isRevealed())
        {
            return;
        }

        cell.cellReveal();
        this.revealedCells++;

        if (cell.getAdjacent() > 0) //adj mines around the cell
        {
            return;
        }
        revealBoard(row - 1, col - 1);
        revealBoard(row - 1, col);
        revealBoard(row - 1, col + 1);
        revealBoard(row, col - 1);
        revealBoard(row, col + 1);
        revealBoard(row + 1, col - 1);
        revealBoard(row + 1, col);
        revealBoard(row + 1, col + 1);
    }


    /**
     * Determines if the cell of coordinates (row,col) has been revealed.
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return true if it has been revealed, false otherwise.
     */
    public boolean isRevealed(int row, int col){
        return this.board[row][col].isRevealed();
    }

    /**
     * Determines if the cell of coordinates (row,col) contains a mine.
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return true if it contains a mine, false otherwise.
     */
    public boolean isMine(int row, int col){
        return this.board[row][col].isMine();
    }

    /**
     * Determines if the cell of coordinates (row,col) is flagged
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return true if it is flagged, false otherwise.
     */
    public boolean isFlagged(int row, int col){
        return this.board[row][col].isFlagged();
    }

    /**
     * Determines if the game has been won.
     * @return true if the game has been won, false otherwise.
     */
    public boolean checkWin(){
        return this.rows*this.columns == this.revealedCells + this.numberOfMines;
    }

    /**
     * Gives the number of revealed cells.
     * @return the number of revealed cells.
     */
    public int getRevealedCells(){return this.revealedCells;}

    /**
     * Reposition the mine contained in the cell (row,col).
     * @param row the row of the cell.
     * @param col the column of the cell.
     */
    public void repositionMine(int row, int col){
        this.board[row][col].updateMine(false);
        while (true) {
            int row1 = random.nextInt(this.rows - 1);
            int col1 = random.nextInt(this.columns - 1);
            if (!board[row1][col1].isMine())
            {
                board[row1][col1].updateMine(true);
                return;
            }
        }
    }

    /**
     * Sets the board state to lost.
     */
    public void setLost(){
        this.isLost = true;
    }

    /**
     * Sets the board state to win.
     */
    public void setWon(){
        this.isWon= true;
    }

    /**
     * Gives the value of the isLost attribute of the board.
     */
    public boolean isLost(){return this.isLost;}

    /**
     * Gives the value of the isWon attribute of the board.
     */
    public boolean isWon(){return this.isWon;}

    public void generateBoard(boolean[][] givenBoard){
        if(givenBoard.length != this.rows || givenBoard[0].length != this.columns){
            System.out.println("ERROR : The given board does not have the correct dimensions !");
            System.out.println("Your board has NOT been generated.");
        }else {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this .columns; j++) {
                    this.board[i][j].updateMine(givenBoard[i][j]);
                }
            }
            // Setting each adjacent mines to each cell
            for (int i = 0; i < this.rows; i ++) {
                for (int j = 0; j < this.columns; j++) {
                    this.setAdjacent(i,j);
                }
            }
            this.revealedCells = 0;
            this.isLost = false;
            this.isWon = false;
        }
    }
}