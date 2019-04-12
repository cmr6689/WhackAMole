package client.gui;


import javafx.application.Application;
import javafx.stage.Stage;

public class WAMGUI extends Application {

    public void start(Stage stage) throws Exception {

    }

    @Override
    public void init() throws Exception {

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
