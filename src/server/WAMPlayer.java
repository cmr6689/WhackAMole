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

    public WAMPlayer(Socket socket) {
        this.socket = socket;
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

    public void welcome(int rows, int columns, int id) {
        out.println(WELCOME + " " + rows + " " + columns);
        this.id = id;
    }

    public void moleUp(int mole) {
        out.println(MOLE_UP + " " + mole);
    }

    public void moleDown(int mole) {
        out.println(MOLE_DOWN + " " + mole);
    }

    public void whack(int mole) {
        out.println(WHACK + " " + mole);
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
