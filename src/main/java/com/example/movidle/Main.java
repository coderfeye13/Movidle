package com.example.movidle;

import com.example.movidle.Controller.GameController;
import com.example.movidle.Helpers.DataAccess;
import com.example.movidle.Helpers.Movie;
import com.example.movidle.Model.MovidleModel;
import com.example.movidle.Model.MovidleView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //eğer fxml de scene builderdan bişeler yaparsak onu çağırma kodu 🙂
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));

        DataAccess dataAccess = new DataAccess();
        //bu 2 satır normalde veri çekmeyle ilgili
        //Movie randomMovie = dataAccess.getRandomMovie();
        //System.out.println("Random Movie: " + randomMovie.getTitle());

        MovidleModel model = new MovidleModel(dataAccess);
        MovidleView view = new MovidleView();
        GameController controller = new GameController(model, view);

        view.setController(controller);
        view.start(primaryStage);
        primaryStage.setTitle("Movidle");
        primaryStage.show();
    }
}