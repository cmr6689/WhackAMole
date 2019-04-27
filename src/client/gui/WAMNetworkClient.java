package client.gui;

import common.WAMProtocol;
import server.WAMGame;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class connects to the server and creates a WAMBoard
 * based off of the parameters of the welcome message from
 * the server.
 * @author Cameron Riu
 * @author Michael Madden
 */
public class WAMNetworkClient {
    private Socket clientSocket;
    private Scanner networkIn;
    private PrintStream networkOut;
    private WAMBoard board;
    private boolean go;
    private boolean welcomed;
    private int rows;
    private int columns;

    /**
     * Creates a network client connected to the server
     * @param host - hostname
     * @param port - port number
     * @throws Exception
     */
    public WAMNetworkClient(String host, int port) throws Exception {
        try {
            this.clientSocket = new Socket(host, port);
            System.out.println(clientSocket);
            this.networkIn = new Scanner(clientSocket.getInputStream());
            this.networkOut = new PrintStream(clientSocket.getOutputStream());
            this.go=true;
            this.welcomed= false;
            //String request = this.networkIn.next();
            //String arguments = this.networkIn.nextLine();

        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    /**
     * Called by the gui to listen for updates
     */
    public void startListener(){
        new Thread(() -> this.run()).start();
    }

    /**
     * Stops the network client
     */
    public void stop(){
        this.go = false;
    }

    /**
     * Closes the socket
     */
    public void close(){
        try{
            this.clientSocket.close();
        }
        catch(IOException e){

        }
        this.welcomed=false;
        this.go=false;
        //this.board.close;
    }

    /**
     * Called by the GUI to get the board
     * @return the WAMBoard
     */
    public WAMBoard getBoard() {
        return this.board;
    }

    /**
     * Called after the welcome message is recieved
     * Creates the WAMBoard based off of arguments
     * @param arguments - sent by the server
     */
    public void welcome(String arguments) {
        System.out.println("got welcome");
        String[] fields = arguments.trim().split(" ");
        this.rows = Integer.parseInt(fields[0]);
        this.columns = Integer.parseInt(fields[1]);
        this.board = new WAMBoard(columns, rows);
        //this.wait(500);
        //this.notifyAll();

        this.welcomed=true;
        this.go=true;


    }

    /**
     * Lets the GUI know if the welcome message has
     * been received
     * @return if welcome message received
     */
    public boolean isWelcomed(){
        return this.welcomed;
    }

    /**
     * Called by the GUI
     * @return WAMBoard columns
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * Called by the GUI
     * @return WAMBoard rows
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Update the board when MOLE_UP is received
     * @param mole - mole number
     */
    public void moleUp(String mole){
        // toggle the mole in the array to up/true
        System.out.println('!' + WAMProtocol.MOLE_UP + " , "+ mole);

        String[] fields = mole.split(" ");
        int update = Integer.parseInt(fields[0]);
        board.moleUp(update);
    }

    /**
     * Update the board when MOLE_DOWN is received
     * @param mole - mole number
     */
    public void moleDown(String mole){
        System.out.println('!' + WAMProtocol.MOLE_DOWN + " , "+ mole);

        //String[] fields = mole.split(" ");
        //int update = Integer.parseInt(fields[0]);
        board.moleDown(Integer.parseInt(mole));
    }

    /**
     * While the server is running get the messages
     */
    private void run(){
        while (this.go){
            System.out.println("read message");
            try{/*
                String request = this.networkIn.next();
                System.out.println("Request "+request);
                String arguments = this.networkIn.nextLine().trim();
                */
                String value = this.networkIn.nextLine();
                System.out.println(value);
                int space = value.indexOf(" ");
                String request = value.substring(0, space);
                String arguments = value.substring(space).trim();
                //networkOut.println("");
                switch (request) {
                    case WAMProtocol.WELCOME:
                        welcome(arguments);
                        break;
                    case WAMProtocol.ERROR:
                        throw new Exception("An error occured");
                    case WAMProtocol.GAME_LOST:
                        break;
                    case WAMProtocol.GAME_TIED:
                        break;
                    case WAMProtocol.GAME_WON:
                        break;
                    case WAMProtocol.MOLE_DOWN:
                        moleDown(arguments);
                        break;
                    case WAMProtocol.MOLE_UP:
                        moleUp(arguments);
                        break;
                    case WAMProtocol.SCORE:
                        break;
                    case WAMProtocol.WHACK:
                        break;
                    default:
                        System.err.println("unknown request");
                        this.stop();
                        break;
                }
            }
            catch(NoSuchElementException nse){
                nse.printStackTrace();
                this.stop();
            }
            catch(Exception e){
                e.printStackTrace();
                this.stop();
            }
        }
        this.close();
    }
}
