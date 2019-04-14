package client.gui;

import java.util.LinkedList;
import java.util.List;

public class WAMBoard {

    private Mole[][] board;
    private List<Observer<WAMBoard>> observers;

    public WAMBoard(int cols, int rows){
        this.observers = new LinkedList<>();
        this.board = new Mole[cols][rows];
        int count = 0;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                board[col][row] = new Mole(count, false);
            }
        }
    }

    public void addObserver(Observer<WAMBoard> observer){
        this.observers.add(observer);
    }

    private void alertObservers(){
        for(Observer<WAMBoard> obS: this.observers){
            obS.update(this);
        }
    }

    public void close() {
        alertObservers();
    }

    public Mole getContents(int col, int row) {
        return this.board[col][row];
    }

    public void moleUp(int col, int row) {
        this.board[col][row].moleUp();
        alertObservers();
    }

    public void moleDown(int col, int row) {
        this.board[col][row].moleDown();
        alertObservers();
    }
}
