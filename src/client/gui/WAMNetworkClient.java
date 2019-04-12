package client.gui;

import common.WAMProtocol;
import server.WAMGame;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class WAMNetworkClient {
    private Socket clientSocket;
    private Scanner networkIn;
    private PrintStream networkOut;
    private WAMBoard board;

    public WAMNetworkClient(String host, int port, WAMBoard board) throws Exception {
        try {
            this.clientSocket = new Socket(host, port);
            this.networkIn = new Scanner(clientSocket.getInputStream());
            this.networkOut = new PrintStream(clientSocket.getOutputStream());
            this.board = board;

            String request = this.networkIn.next();
            String arguments = this.networkIn.nextLine();

        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public void startListener(){
        //new Thread(() -> this.run()).start();
    }
}
