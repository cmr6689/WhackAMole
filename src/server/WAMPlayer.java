package server;

import common.WAMProtocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class WAMPlayer extends Thread implements WAMProtocol, Closeable {
    private Socket socket;
    private Scanner in;
    private PrintStream out;
    private int id;

    public WAMPlayer(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return this.id;
    }

    public void connect() {
        out.println(WELCOME);
    }

    public void gameWon() {
        out.println(GAME_WON);
    }

    public void gameLost() {
        out.println(GAME_LOST);
    }

    public void gameTied() {
        out.println(GAME_TIED);
    }

    public void error(String message) {
        out.println(ERROR + message);
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ioe) {
            // fin
        }
    }


}
