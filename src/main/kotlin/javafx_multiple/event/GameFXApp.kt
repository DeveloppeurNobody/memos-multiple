package javafx_multiple.event

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage
import tornadofx.add

class GameFXApp : Application() {

    override fun start(primaryStage: Stage?) {
        println("GameFXApp.start()");

        val eventBus = ServiceLocator.getService(EventBus::class.java);
        val audioPlayerService = ServiceLocator.getService(AudioPlayer::class.java);

        var root = VBox();
        root.children.add(Label("Hello"));

        var scene = Scene(root,600.0, 600.0);

        scene.setOnMouseClicked {
            println("MouseEvent");
            eventBus.fireEvent(GameEvent(GameEvent.PLAYER_DIED))
        }

        primaryStage?.scene = scene;
        primaryStage?.show();
    }
}