package server;

import common.WAMProtocol;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class WAMPlayer implements WAMProtocol {
    private Socket socket;
    private Scanner in;
    private PrintStream out;

    public WAMPlayer(Socket socket) {
        this.socket = socket;
        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        out.println("CONNECT");
    }


}
