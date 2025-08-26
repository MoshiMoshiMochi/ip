package bob;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Bob bob = new Bob("../savedtasks/task.txt"); // reuse your backend

    @Override
    public void start(Stage stage) {
        Label response = new Label("Hello! I'm Bob \nWhat can I do for you?");
        TextField userInput = new TextField();
        Button sendButton = new Button("Send");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(response, userInput, sendButton);

        sendButton.setOnAction(event -> {
            String input = userInput.getText();
            if (input.isBlank()) return;

            String reply = bob.getResponse(input); // you’ll add this method
            response.setText(reply);
            userInput.clear();
        });

        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("Bob");
        stage.setScene(scene);
        stage.show();
    }
}
