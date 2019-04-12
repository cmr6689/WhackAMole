package client.gui;

import common.WAMProtocol;
import server.WAMGame;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class WAMNetworkClient {
    private Socket clientSocket;
    private Scanner networkIn;
    private PrintStream networkOut;
    private WAMBoard board;
    private boolean go;

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

    public void stop(){
        this.go = false;
    }

    public void close(){
        try{
            this.clientSocket.close();
        }
        catch(IOException e){

        }
        //this.board.close;
    }

    private void run(){
        while (this.go){
            try{
                String request = this.networkIn.next();
                String arguments = this.networkIn.nextLine().trim();
                //networkOut.println("");
                switch (request) {
                    case WAMProtocol.WELCOME:
                        continue;
                    case WAMProtocol.ERROR:
                        continue;
                    case WAMProtocol.GAME_LOST:
                        continue;
                    case WAMProtocol.GAME_TIED:
                        continue;
                    case WAMProtocol.GAME_WON:
                        continue;
                    case WAMProtocol.MOLE_DOWN:
                        continue;
                    case WAMProtocol.MOLE_UP:
                        continue;
                    case WAMProtocol.SCORE:
                        continue;
                    case WAMProtocol.WHACK:
                        continue;
                    default:
                        System.err.println("unknown request");
                        this.stop();
                        break;
                }
            }
            catch(NoSuchElementException nse){
                this.stop();
            }
            catch(Exception e){
                this.stop();
            }
        }
        this.close();
    }
}
