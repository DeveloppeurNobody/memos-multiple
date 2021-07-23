package javafx_multiple.react_example

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class LikeReactApp : Application() {

    override fun start(primaryStage: Stage?) {
        println("path: ${javaClass.getResource("")}")
        var scene = Scene(VBox(), 300.0, 300.0);

        primaryStage?.scene = scene;
        primaryStage?.show();
    }
}