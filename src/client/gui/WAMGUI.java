package client.gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    /**
     * start the gui
     *
     * @param stage - stage to use to start the gui
     */
    public void start(Stage stage) {
        //client.startListener();
        while (!client.isWelcomed()) {
            System.out.println("not welcome");
            try {
                Thread.sleep(500);
                //System.out.println("slept");
            } catch (InterruptedException e) {
            }
        }
        System.out.println("past not welcome");

        if (client.isWelcomed()) {
            this.board = client.getBoard();
            //this.board = new WAMBoard(col, row);
            this.board.addObserver(this);
            this.boardarr = new Button[client.getColumns()][client.getRows()];
            int count = 0;
            for (int i = 0; i < client.getColumns(); i++) {
                for (int x = 0; x < client.getRows(); x++) {
                    boardarr[i][x] = new Button();
                    boardarr[i][x].setOnAction(whack(count));
                    count++;
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


        }

    }

    /**
     * init the gui and start a client instance
     */
    @Override
    public void init() throws Exception {
        try {
            List<String> args = getParameters().getRaw();

            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            this.client = new WAMNetworkClient(host, port);
            int col = this.client.getColumns();
            int row = this.client.getRows();
            client.startListener();

            //if (client.isWelcomed()) {
            //  board.addObserver(this);
            //this.board = client.getBoard();
            //}
            //this.board = new WAMBoard(col, row);
            //this.board.addObserver(this);
        } catch (NumberFormatException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * refresh the gui and update image graphics if need be for each mole
     */
    public void refresh() {
        for (int i = 0; i < client.getColumns(); i++) {
            for (int x = 0; x < client.getRows(); x++) {
                if (this.board.getContents(i, x).isUp()) {
                    Image up = new Image(getClass().getResourceAsStream("/common/mole_64x64.jpg"));
                    boardarr[i][x].setGraphic(new ImageView(up));
                } else if (!(this.board.getContents(i, x).isUp())) {
                    Image down = new Image(getClass().getResourceAsStream("/common/empty_mole.jpg"));
                    boardarr[i][x].setGraphic(new ImageView(down));
                }
            }
        }
    }

    private EventHandler<ActionEvent> whack(int mole) {
        return event -> {
            if (board.moleStatus(mole).isUp()) {
                client.whack(String.valueOf(mole));
            }
        };
    }

    /**
     * update the gui game board.
     *
     * @param wamBoard - the board instance that will be referenced during an update
     */
    @Override
    public void update(WAMBoard wamBoard) {
        if (Platform.isFxApplicationThread()) {
            this.refresh();
        } else {
            Platform.runLater(() -> this.refresh());
        }
    }

    @Override
    public void stop() {
        client.close();
    }

    /**
     * main entry point for the gui
     *
     * @param args - command line args
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage java WAMGUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
