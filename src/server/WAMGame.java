package server;

public class WAMGame implements Runnable {
    private WAMPlayer[] players;
    private int runTime;

    public WAMGame(WAMPlayer[] players, int runTime) {
        this.players = players;
        this.runTime = runTime;
    }

    @Override
    public void run() {
        boolean go = true;
        while (true) {

        }
    }
}
