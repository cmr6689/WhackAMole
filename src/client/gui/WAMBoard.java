package client.gui;

import java.util.LinkedList;
import java.util.List;

public class WAMBoard {

    private boolean[] board;
    private List<Observer<WAMBoard>> observers;

    public WAMBoard(int numMoles){
        this.observers = new LinkedList<>();
        this.board = new boolean[numMoles];
        for (int num = 0; num < numMoles; num++) {
            board[num] = false;
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

    public boolean getContents(int mole) {
        return this.board[mole];
    }

    public void moleUp(int mole) {
        this.board[mole] = true;
    }

    public void moleDown(int mole) {
        this.board[mole] = false;
    }
}
