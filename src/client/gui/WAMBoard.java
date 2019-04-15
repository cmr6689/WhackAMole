package client.gui;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents the board of moles as a 2D
 * array
 * @author Cameron Riu
 * @author Michael Madden
 */
public class WAMBoard {

    private Mole[][] board;
    private List<Observer<WAMBoard>> observers;
    private final int cols;
    private final int rows;

    /**
     * WAMBoard constructor, organizes rows into Mole's via a 2d array for the board
     * @param cols - number of columns to create, called from welcome method
     * @param rows - number of rows to create, gets info from welcome method
     */
    public WAMBoard(int cols, int rows){
        this.cols = cols;
        this.rows = rows;
        this.observers = new LinkedList<>();
        this.board = new Mole[cols][rows];
        int count = 0;
        for (int col = 0; col < this.cols; col++) {
            for (int row = 0; row < this.rows; row++) {
                board[col][row] = new Mole(count++, false);
            }
        }
    }

    /**
     * add an observer to the board
     * @param observer - observer to add
     */
    public void addObserver(Observer<WAMBoard> observer){
        this.observers.add(observer);
    }

    /**
     * alert observers about an update. loop through each of the observers in the list
     */
    private void alertObservers(){
        for(Observer<WAMBoard> obS: this.observers){
            obS.update(this);
        }
    }

    /**
     * close connection and alert observers of the closed connection
     */
    public void close() {
        alertObservers();
    }

    /**
     * get the mole from a specific row/col and return it
     * @param col - col #
     * @param row - row #
     * @return the mole that was located
     */
    public Mole getContents(int col, int row) {
        return this.board[col][row];
    }

    /**
     * toggle the mole up, based on the mole number thats passed into the method
     * @param numMole - mole number
     */
    public void moleUp(int numMole) {
        for (int col = 0; col < this.cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (board[col][row].getId() == numMole) {
                    board[col][row].moleUp();
                }
            }
        }
        alertObservers();
    }

    /**
     * toggle the mole down, based on the mole number thats passed into the method
     * @param numMole - mole number
     */
    public void moleDown(int numMole) {
        for (int col = 0; col < this.cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (board[col][row].getId() == numMole) {
                    board[col][row].moleDown();
                }
            }
        }
        alertObservers();
    }
}
