package javafx_multiple.event_ftp


import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

class FtpEventFXApp : Application() {
    var eventBus = ServiceLocatorFtp.getService(EventBus::class.java);

    var commandService = ServiceLocatorFtp.getService(CommandService::class.java);

    override fun start(primaryStage: Stage?) {
        // should be in main function




        var scene = Scene(VBox(), 600.0, 600.0);

        scene.setOnMouseClicked {
            eventBus.fireEvent(CommandEvent(CommandEvent.PWD))
        }

        primaryStage!!.scene = scene;
        primaryStage.show();
    }
}