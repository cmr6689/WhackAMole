package server;

import client.gui.WAMBoard;

import java.util.Random;

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
        long startTime = System.currentTimeMillis();
        while (server.isRunning() && (System.currentTimeMillis()-startTime) < (runTime*1000)+5000) {
            if(go){
                try {
                    Thread.sleep(5000);
                }
                catch(InterruptedException e) {//
                }
            }
            go=false;
            int max = server.getColumns() * server.getRows();
            Random rand = new Random();
            int temp = rand.nextInt(max - 1);
            if (board.moleStatus(temp).isUp()) {
                for (WAMPlayer player : players) {
                    player.moleDown(temp);
                }
                board.moleDown(temp);
            } else {
                for (WAMPlayer player : players) {
                    player.moleUp(temp);
                }
                board.moleUp(temp);

            }
            // 500 2000
            try {
                Thread.sleep(rand.nextInt(1250 - 500 + 1) + 500);
            } catch (InterruptedException kyle) {//
            }
        }
        for (WAMPlayer player : players) {
            player.close();
            server.close();
        }
    }
}
