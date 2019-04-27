package server;

import client.gui.WAMBoard;

public class WAMGame implements Runnable {
    private WAMPlayer[] players;
    private int runTime;
    private WAMServer server;
    private WAMBoard board;

    public WAMGame(WAMPlayer[] players, int runTime, WAMServer server) {
        this.players = players;
        this.runTime = runTime;
        this.server = server;
        this.board = server.getBoard();
    }

    @Override
    public void run() {
        boolean go = true;
        while (true) {
            // lets pick some random mole and toggle it
            //server.ge

        }
    }
}
