package main;

import java.net.URL;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import netscape.javascript.JSObject;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author luisr
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Hey");
        Person luis = new Person();

        WebView browser = new WebView();

        WebEngine webEngine = browser.getEngine();

        webEngine.getLoadWorker()
                .stateProperty()
                .addListener((obs, old, neww)
                        -> {
                    if (neww == Worker.State.SUCCEEDED) {
                        // Let JavaScript make calls to adder object,
                        //so we need to inject an [Adder] object into the JS code
                        // this will execute once the file is loaded
                        JSObject bridge = (JSObject) webEngine.executeScript("window");
                        bridge.setMember("Luis", luis);
                        webEngine.executeScript("load()");
                        webEngine.executeScript("fileHi()");
                    }
                });
        URL url = this.getClass().getResource("/main/main.html");
        webEngine.load(url.toString());

        StackPane root = new StackPane();
        root.getChildren().add(browser);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
