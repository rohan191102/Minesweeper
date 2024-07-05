/**
 * This class represent a cell of the minesweeper board.
 */
public class BoardCell {
    /**
     * The row of the cell.
     */
    private int row;
    /**
     * The column of the cell.
     */
    private int col;
    /**
     * Boolean variable that is true if there is a bomb in the cell.
     */
    private boolean isMine;
    /**
     * Integer that represent the number of adjacent bombs
     */
    private int adjacentMines;
    /**
     * Boolean variable that is true if the cell has been revealed.
     */
    private boolean isRevealed;
    /**
     * Boolean variable that is true if the cell has been flagged.
     */
    private boolean isFlagged;

    /**
     * Creates a new BoardCell object with the given initial values.
     * @param r the row.
     * @param c the column.
     */
    public BoardCell(int r, int c){
        this.row = r;
        this.col = c;
        this.isMine = false;
        this.adjacentMines = 0;
        this.isFlagged = false;
        this.isRevealed = false;
    }

    /**
     * Determines if there is a mine in the cell.
     * @return true if there is a mine, false otherwise.
     */
    public boolean isMine(){return this.isMine;}

    /**
     * Determines if the cell has been flagged.
     * @return true if the cell has been flagged.
     */
    public boolean isFlagged(){return this.isFlagged;}
    /**
     * Determines if the cell has been revealed.
     * @return true if the cell has been revealed.
     */
    public boolean isRevealed(){return this.isRevealed;}

    /**
     * Reveals the cell aka sets the isRevealed attribute to true.
     */
    public void cellReveal(){
        this.isRevealed = true;
    }

    /**
     * Flags the cell aka sets the isFlagged attribute to true.
     */
    public void flag(){
        this.isFlagged = !this.isFlagged;
    }

    /**
     * Sets the mine state of a cell. Is used if we need to reposition a mine after the first move.
     * @param m the new mine state of the cell.
     */
    public void updateMine(boolean m){
        this.isMine = m;
    }

    /**
     * Sets the number of adjacent mines to the cell. The value given is computed by the board object.
     * @param adj the number of adjacent mines.
     */
    public void updateAdjacent(int adj){
        this.adjacentMines = adj;
    }

    /**
     * Gives the number of adjacent mines to the cell that was lastly saved.
     * @return the number of adjacent mines.
     */
    public int getAdjacent(){
        return this.adjacentMines;
    }
}
