package server;

import com.sun.javafx.scene.PointLightHelper;
import common.WAMProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WAMServer implements WAMProtocol {
    private ServerSocket serverSocket;
    private int rows;
    private int columns;
    private int numPlayers = 1;
    private int runTime;
    private WAMPlayer[] players;

    public WAMServer(int port, int rows, int columns, int numPlayers, int runTime) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rows = rows;
        this.columns = columns;
        if (numPlayers > 1) this.numPlayers = numPlayers;
        this.runTime = runTime;
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java WAMServer <port> <rows> <columns> <numPlayers> <runTime>");
            System.exit(1);
        }
        WAMServer server = new WAMServer(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        server.run();
    }

    public void run() {
        try {
            System.out.println("Waiting for players...");
            Socket playerOneSocket = serverSocket.accept();
            for (int i = 0; i < numPlayers; i++) {
                WAMPlayer player = new WAMPlayer(playerOneSocket);
                this.players[i] = player;
                System.out.println("Player " + i + " connected!");
                new Thread().run();
            }
            System.out.println("Starting game!");
            WAMGame game = new WAMGame(this.players, this.runTime);
            new Thread().run();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
