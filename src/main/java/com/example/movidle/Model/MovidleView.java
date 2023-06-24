package com.example.movidle.Model;
import com.example.movidle.Controller.GameController;
import com.example.movidle.Helpers.Movie;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class MovidleView extends VBox{
    private ListView<Movie> listView;
    private TextField textField;
    private VBox redBoxContainer;
    private int remainingAttempts = 5;
    private boolean isFirstInput = true;
    private String previousText = "";


    public void start(Stage primaryStage) {

        textField = createTextField();
        Button enterButton = createEnterButton();
        Button newGameButton = createNewGameButton(primaryStage);

        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(enterButton, newGameButton);

        redBoxContainer = createRedBoxContainer();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(textField, buttonBox, redBoxContainer);

        Scene scene = new Scene(layout, 720, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        updateRemainingAttemptsLabel(textField);
        return textField;
    }

    private Button createEnterButton() {
        Button enterButton = new Button("Enter");
        enterButton.setDefaultButton(true);
        enterButton.setOnAction(event -> handleTextFieldInput());
        return enterButton;
    }

    private Button createNewGameButton(Stage primaryStage) {
        Button newGameButton = new Button("Yeni Oyun");
        newGameButton.setOnAction(event -> {
            remainingAttempts = 5;
            isFirstInput = true;
            previousText = "";
            clearTextField();
            redBoxContainer.getChildren().clear();
            start(primaryStage);
        });
        return newGameButton;
    }

    private void handleTextFieldInput() {
        String inputText = textField.getText();

        if (remainingAttempts <= 0) {
            disableTextField();
            showAlert("Maksimum deneme sayısına ulaşıldı!");
        } else {
            if (!isFirstInput && inputText.equalsIgnoreCase(previousText)) {
                showAlert("Bu filmi daha önce girdiniz. Lütfen farklı bir film girin!");
            } else {
                previousText = inputText;
                isFirstInput = false;
            }

            clearTextField();
            remainingAttempts--;
            updateRemainingAttemptsLabel(textField);

            if (remainingAttempts <= 0) {
                disableTextField();
                showAlert("Maksimum deneme sayısına ulaşıldı!");
            }

            HBox hbox = new HBox(10); // Boşluk genişliğini ayarlayabilirsiniz
            for (int i = 0; i < 6; i++) {
                Rectangle redBox = createRedBox();
                hbox.getChildren().add(redBox);
            }
            redBoxContainer.getChildren().add(hbox);
        }
    }

    private VBox createRedBoxContainer() {
        VBox redBoxContainer = new VBox(20);
        redBoxContainer.setPadding(new Insets(20));
        return redBoxContainer;
    }

    private Rectangle createRedBox() {
        Rectangle redBox = new Rectangle(90,90 );
        redBox.setFill(Color.RED);
        return redBox;
    }

    private void updateRemainingAttemptsLabel(TextField textField) {
        String hintText = (remainingAttempts > 0) ? "Kalan tahmin hakkı: " + remainingAttempts : "";
        textField.setPromptText(hintText);
        textField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        textField.setFont(Font.font("System", 12));
    }

    private void disableTextField() {
        textField.setDisable(true);
    }

    private void clearTextField() {
        textField.clear();
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bildirim");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setController(GameController controller) {

    }
    public MovidleView() {
        listView = new ListView<>();
        TextField titleTextField = new TextField();
        titleTextField.setPromptText("Title girin");

        titleTextField.setOnKeyReleased(event -> {
            String searchText = titleTextField.getText();

            if (!searchText.isEmpty()) {
                List<Movie> matchingMovies = MovidleModel.getInstance().searchMoviesByTitle(searchText);
                listView.getItems().setAll(matchingMovies);
            } else {
                listView.getItems().setAll(MovidleModel.getInstance().getMovies());
            }
        });

        setSpacing(10);
        setPadding(new Insets(10));
        getChildren().addAll(titleTextField, listView);
    }
}
