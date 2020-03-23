package templateFx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Button btn = new Button();
        btn.setText("Say Hello World !!");
        //
        btn.setOnAction((ActionEvent event) -> {
            stage.setTitle("Button Pressed at " + new Date());
        });
        // container element for the button
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        // main scene
        Scene scene = new Scene(root, 450, 250);
        // configure & display stage
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }
}
