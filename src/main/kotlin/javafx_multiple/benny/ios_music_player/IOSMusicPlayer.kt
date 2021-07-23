package javafx_multiple.benny.ios_music_player

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class IOSMusicPlayer : Application() {
    override fun start(primaryStage: Stage?) {
        val root = FXMLLoader.load<Pane>(javaClass.getResource("/fxml/UI.fxml"));

        var scene = Scene(root);

        primaryStage?.scene = scene;
        primaryStage?.show();
    }
}