package client.gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import server.WAMGame;

import java.util.List;

public class WAMGUI extends Application {

    private GridPane boardpane = new GridPane();
    private WAMGame game;
    private Scene scene;
    private WAMBoard board;
    private WAMNetworkClient client;


    public void start(Stage stage) throws Exception {
        Button[][] boardarr = new Button[7][6];
        for (int i = 0; i < boardarr.length; i++) {
            for (int x = 0; x < boardarr[i].length; x++) {
                boardarr[i][x] = new Button();
                Image image = new Image(getClass().getResourceAsStream("empty.png"));
                boardarr[i][x].setGraphic(new ImageView(image));
                //boardarr[i][x].setOnAction(newSendMove(i));
                //boardarr[i][x].
                boardpane.add(boardarr[i][x], i, x);
            }
        }
    }

    @Override
    public void init() throws Exception {
        try {
            List<String> args = getParameters().getRaw();

            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            board = new WAMBoard();

            client = new WAMNetworkClient(host, port, board);
        }
        catch(NumberFormatException e){
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        if (args.length != 2){
            System.out.println("Usage java WAMGUI host port");
            System.exit(-1);
        }
        else {
            Application.launch(args);
        }
    }
}
