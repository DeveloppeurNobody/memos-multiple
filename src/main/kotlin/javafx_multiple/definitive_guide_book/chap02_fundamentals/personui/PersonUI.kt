package javafx_multiple.definitive_guide_book.chap02_fundamentals.personui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class PersonUI : Application() {
    override fun start(primaryStage: Stage?) {
        val root: Parent = FXMLLoader.load(javaClass.getResource("/fxml/PersonUIScene.fxml"));

        var scene = Scene(root)
        with (primaryStage!!) {
            title = "Person UI Example";
            scene = scene;
            show();
        }
    }
}