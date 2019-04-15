package client.gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import server.WAMGame;

import java.util.List;

public class WAMGUI extends Application implements Observer<WAMBoard> {

    private GridPane boardpane = new GridPane();
    private WAMGame game;
    private WAMBoard board;
    private WAMNetworkClient client;
    private BorderPane window = new BorderPane();
    private Image mole;
    Button[][] boardarr;

    public void start(Stage stage) throws Exception {
        client.startListener();
        while(true) {
            if (client.isWelcomed()) {
                this.board = client.getBoard();
                //this.board = new WAMBoard(col, row);
                this.board.addObserver(this);
                this.boardarr = new Button[client.getColumns()][client.getRows()];
                for (int i = 0; i < client.getColumns(); i++) {
                    for (int x = 0; x < client.getRows(); x++) {
                        boardarr[i][x] = new Button();
                        Image image = new Image(getClass().getResourceAsStream("/common/empty_mole.jpg"));
                        boardarr[i][x].setGraphic(new ImageView(image));
                        //boardarr[i][x].setOnAction(newSendMove(i));
                        boardpane.add(boardarr[i][x], i, x);
                    }
                }

                window.setCenter(boardpane);
                Scene scene = new Scene(window);
                stage.setScene(scene);
                stage.setTitle("WackAMole Game");
                stage.sizeToScene();
                stage.setResizable(false);
                stage.show();
                break;
            }
        }

    }

    @Override
    public void init() throws Exception {
        try {
            List<String> args = getParameters().getRaw();

            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            this.client = new WAMNetworkClient(host, port);
            int col = this.client.getColumns();
            int row = this.client.getRows();
            //this.board = client.getBoard();
            //this.board = new WAMBoard(col, row);
            //this.board.addObserver(this);
        }
        catch(NumberFormatException e){
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    public void refresh() {
        for (int i = 0; i < client.getColumns()-1; i++) {
            for (int x = 0; x < client.getRows()-1; x++) {
                if (this.board.getContents(i, x).isUp()) {
                    Image image = new Image(getClass().getResourceAsStream("/common/mole_64x64.jpg"));
                    boardarr[i][x].setGraphic(new ImageView(image));
                } else {
                    Image image = new Image(getClass().getResourceAsStream("/common/empty_mole.jpg"));
                    boardarr[i][x].setGraphic(new ImageView(image));
                }
            }
        }
    }

    @Override
    public void update(WAMBoard wamBoard) {
        if (Platform.isFxApplicationThread()) {
            this.refresh();
        } else {
            Platform.runLater(() -> this.refresh());
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
