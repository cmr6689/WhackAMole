package client.gui;

import java.util.List;

public class WAMBoard {

    public static int ROWS;
    public static int COLS;

    private List<Observer<WAMBoard>> observers;

    public void addObserver(Observer<WAMBoard> observer){
        this.observers.add(observer);
    }

    private void alertObservers(){
        for(Observer<WAMBoard> obS: this.observers){
            obS.update(this);
        }
    }

    public void close(){alertObservers();}

}
