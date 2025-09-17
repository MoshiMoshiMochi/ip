package bob.ui;

import bob.Bob;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
//    @FXML
//    private Button sendButton;

    private Bob bob;

    private final String userImagePath = "/images/typing-on-computer-mark-grayson.gif";
    private final String bobImagePath = "/images/bob-the-builder-fix-it.gif";
    private Image userImage = new Image(this.getClass().getResourceAsStream(userImagePath));
    private Image bobImage = new Image(this.getClass().getResourceAsStream(bobImagePath));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Duke instance
     */
    public void setBob(Bob b) {
        bob = b;
        dialogContainer.getChildren().addAll(
                DialogBox.getBobDialog(bob.getIntro(), bobImage, "")
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bob.getResponse(input);
        String commandType = bob.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBobDialog(response, bobImage, commandType)
        );
        userInput.clear();
        bob.handleExitIfNeeded();
    }
}
