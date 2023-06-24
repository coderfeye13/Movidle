package com.example.movidle.Controller;

import com.example.movidle.Model.MovidleModel;
import com.example.movidle.Model.MovidleView;



public class GameController {

    public GameController(MovidleModel model, MovidleView view) {
        view.setController(this);
    }

}